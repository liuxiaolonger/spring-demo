package com.longer.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author Admin
 *
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

	@Bean
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }
 
    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

	@Bean
	public DataSource myRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
			@Qualifier("slaveDataSource") DataSource slaveDataSource) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DBTypeEnum.MASTER, masterDataSource);
		targetDataSources.put(DBTypeEnum.SLAVE, slaveDataSource);
		MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
		myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
		myRoutingDataSource.setTargetDataSources(targetDataSources);
		return myRoutingDataSource;

	}
}
