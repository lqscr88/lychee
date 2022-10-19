package  org.lychee.swagger.nacos;

import lombok.Data;

import java.util.List;

@Data
public class NsServers {
    private List<String> doms;
    private Integer count;
}
