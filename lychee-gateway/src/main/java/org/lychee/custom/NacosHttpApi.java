package org.lychee.custom;

import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Retry;
import org.lychee.domain.nacos.InstantceList;

import java.util.List;

public interface NacosHttpApi {

    @Get("http://127.0.0.1:8848/nacos/v1/ns/service/list?pageNo=1&pageSize=20")
    @Retry(maxRetryCount = "3", maxRetryInterval = "10")
    InstantceList getInstantce();
}
