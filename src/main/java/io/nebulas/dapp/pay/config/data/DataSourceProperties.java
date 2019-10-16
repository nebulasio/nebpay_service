package io.nebulas.dapp.pay.config.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * User: nathan
 * Date: 2018-04-26
 */
@Getter
@Setter
@Configuration
public class DataSourceProperties {

    @Value("${db.driver}")
    private String driverClass;
    @Value("${db.minPoolSize}")
    private int minPoolSize;
    @Value("${db.maxPoolSize}")
    private int maxPoolSize;

    @Value("${db.pay_center.username}")
    private String payCenterUsername;
    @Value("${db.pay_center.password}")
    private String payCenterPassword;

    @Value("${db.url4pay_center}")
    private String payCenterJdbcUrl;
}
