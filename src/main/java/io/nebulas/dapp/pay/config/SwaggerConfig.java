package io.nebulas.dapp.pay.config;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.ui.enable}")
    private boolean environmentSpecificBooleanFlag;

    @Bean
    public Docket walletApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(walletApiInfo())
                .groupName("dapp-pay-center")
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.nebulas.dapp.pay.web"))
                .paths(showControllers())
                .build()
                .useDefaultResponseMessages(false)
                .enable(environmentSpecificBooleanFlag);
    }

    private ApiInfo walletApiInfo() {
        return new ApiInfo("dapp-pay-center",
                "dapp-pay-center interface",
                "1.0",
                "dapp-pay-center",
                "nathan@nebulas.io",
                "",
                "");
    }

    private Predicate<String> showControllers() {
        Predicate<String> predicate = input -> !input.contains("/api/demo");
        return predicate;
    }

}

