package io.nebulas.dapp.pay.config.data;

import com.alibaba.druid.pool.DruidDataSource;
import io.nebulas.dapp.pay.common.mybatis.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Desc:
 * User: nathan
 * Date: 2018-04-26
 */
@Configuration
@Import(DataSourceProperties.class)
@MapperScan(basePackages = {"io.nebulas.dapp.pay.domain.mapper"}, sqlSessionTemplateRef = "payCenterSqlSessionTemplate")
public class DappPayCenterDsConfig {

    @Autowired
    private DataSourceProperties properties;

    @Primary
    @Bean(name = "payCenterDataSource")
    public DataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(properties.getPayCenterJdbcUrl());
        ds.setDriverClassName(properties.getDriverClass());
        ds.setUsername(properties.getPayCenterUsername());
        ds.setPassword(properties.getPayCenterPassword());
        ds.setInitialSize(properties.getMinPoolSize());
        ds.setMinIdle(properties.getMinPoolSize());
        ds.setMaxActive(properties.getMaxPoolSize());
        ds.setMaxWait(60000);
        ds.setMinEvictableIdleTimeMillis(300000);
        return ds;
    }

    @Primary
    @Bean(name = "payCenterSqlSessionFactory")
    public MybatisSqlSessionFactoryBean sqlSessionFactory(@Qualifier("payCenterDataSource") DataSource dataSource) {
        MybatisSqlSessionFactoryBean fb = new MybatisSqlSessionFactoryBean();
        fb.setDataSource(dataSource);
        fb.setEnumBasePackages("io.nebulas.dapp.pay.domain.enums");
        return fb;
    }

    @Primary
    @Bean(name = "payCenterTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("payCenterDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "payCenterSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("payCenterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
