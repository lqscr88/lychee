package org.lychee.swagger.config;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.aggre.nacos.NacosRoute;
import com.github.xiaoymin.knife4j.aggre.repository.NacosRepository;
import com.github.xiaoymin.knife4j.aggre.spring.support.NacosSetting;
import org.lychee.nacos.InstanceResult;
import org.lychee.nacos.NsServers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
public class Knife4jConfig{
    private static final Logger logger = LoggerFactory.getLogger(Knife4jConfig.class);
    @Value("${knife4j.nacos.serviceUrl}")
    String serverUrl;
    @Value("${knife4j.nacos.namespaceId:}")
    String namespaceId;
    @Value("${knife4j.nacos.groupName:}")
    String groupName;
    private static final long LOOP_INTERVAL = 30000;   //30s 刷新一次

    private final NacosSetting nacosSetting = new NacosSetting();
    NacosRepository nacosRepository;

    @Bean(initMethod = "start",destroyMethod = "close")
    public NacosRepository nacosRepository() {
        nacosSetting.setEnable(true);
        nacosSetting.setServiceUrl(serverUrl);
        ArrayList<NacosRoute> nacosRoutes = buildRouters();
        nacosSetting.setRoutes(nacosRoutes);
        loopFlushConfig();
        nacosRepository = new NacosRepository(nacosSetting);
        System.out.println("swagger初始化成功");
        return nacosRepository;
    }


    private NsServers loadNsService() {
        String loadNsServiceUrl = serverUrl + "/v1/ns/service/list";
        //默认聚合时只返回健康实例
        Map<String, Object> params = new HashMap<>();
//        params.put("healthyOnly", true);
        params.put("pageNo", 1);
        params.put("pageSize", 10);
//        if (StringUtils.hasLength(groupName)){
//            params.put("groupName", groupName);
//        }
//        if (StringUtils.hasLength(namespaceId)){
//            params.put("namespaceId", namespaceId);
//        }
        String result  = HttpUtil.get(loadNsServiceUrl, params);
        NsServers servers = JSONObject.parseObject(result, NsServers.class);
        return servers;
    }


    private List<InstanceResult> loadNsInstance(NsServers servers) {
        String loadNsInstanceUrl = serverUrl + "/v1/ns/instance/list";
        ExecutorService executorService = ThreadUtil.newExecutor(20);
        List<InstanceResult> instanceList = new ArrayList<>(servers.getCount());


        List<String> instanceNames = servers.getDoms();
        for (String name : instanceNames) {
            //默认聚合时只返回健康实例
            Map<String, Object> params = new HashMap<>();
            params.put("serviceName", name);
//            params.put("healthyOnly", true);
//            if (StringUtils.hasLength(groupName)){
//                params.put("groupName", groupName);
//            }
//            if (StringUtils.hasLength(namespaceId)){
//                params.put("namespaceId", namespaceId);
//            }
            executorService.submit(()->{
                String result =  HttpUtil.get(loadNsInstanceUrl, params);
                InstanceResult instanceResult = JSONUtil.toBean(result, InstanceResult.class);
                instanceList.add(instanceResult);
            });
        }


        executorService.shutdown();    //禁用提交的新任务
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();           //（重新）取消当前线程是否中断
            Thread.currentThread().interrupt();     //保持中断状态
        }

        return instanceList;
    }


    private ArrayList<NacosRoute> buildRouters() {
        ArrayList<NacosRoute> nsRoutes = new ArrayList<>();
        NsServers nsServers = loadNsService();
        List<InstanceResult> nsInstance = loadNsInstance(nsServers);
        for (InstanceResult result : nsInstance) {
            String dom = result.getDom();
            NacosRoute nacosRoute = new NacosRoute();
            nacosRoute.setNamespaceId(namespaceId);
            nacosRoute.setServiceName(dom);
            nacosRoute.setLocation(dom + "/v3/api-docs?group=default");
            nsRoutes.add(nacosRoute);
            System.out.println(nacosRoute);
        }
        return nsRoutes;
    }


    private void loopFlushConfig() {
        new Thread(()-> {
            while (true) {
                try {
                    ThreadUtil.sleep(LOOP_INTERVAL);
                    List<NacosRoute> nacosRoutes = buildRouters();
                    nacosSetting.setRoutes(nacosRoutes);
                    //此处需要修改源码  将在下方给出修改方式
                    nacosRepository.initNacos(nacosSetting);
                    nacosRepository.applyRoutes(nacosSetting);
                    logger.debug("=======knife4j 配置刷新==============");
                } catch (Exception e) {
                    logger.error("knife4j 配置刷新异常", e);
                }
            }
        }).start();
    }
}
