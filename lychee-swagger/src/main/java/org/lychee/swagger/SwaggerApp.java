package  org.lychee.swagger;


import org.lychee.common.constant.AppConstant;
import org.lychee.core.boot.LycheeApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SwaggerApp {

    public static void main(String[] args) {
        LycheeApplication.run(AppConstant.APPLICATION_SWAGGER_NAME, SwaggerApp.class, args);
    }
}
