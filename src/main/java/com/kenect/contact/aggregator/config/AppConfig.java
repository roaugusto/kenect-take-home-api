package com.kenect.contact.aggregator.config;

import com.kenect.contact.aggregator.exception.CustomResponseErrorHandler;
import com.zaxxer.hikari.HikariDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        return restTemplate;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource hikariDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:postgresql://%s/%s".formatted(applicationProperties().getDatabaseHostname(),
                applicationProperties().getDatabaseName()));
        ds.setUsername(applicationProperties().getDatabaseUsername());
        ds.setPassword(applicationProperties().getDatabasePassword());
        ds.setMaximumPoolSize(3);
        ds.addDataSourceProperty("gssEncMode", "disable");
        return ds;
    }
}
