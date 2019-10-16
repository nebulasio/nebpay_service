package io.nebulas.dapp.pay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Desc:
 * User: nathan
 * Date: 2018-04-25
 */
@PropertySource({
        "classpath:application.properties"
})
@ComponentScan(basePackages = {"io.nebulas.dapp.pay"})
@EnableAutoConfiguration
@Configuration
@ServletComponentScan
@SpringBootApplication
@Slf4j
public class DappPayCenterBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(DappPayCenterBootstrap.class, args);
        log.info("Server Started");
    }
}
