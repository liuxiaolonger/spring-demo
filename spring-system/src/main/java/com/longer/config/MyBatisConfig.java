package com.longer.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@MapperScan(basePackages="com.longer.dao.mapper")
public class MyBatisConfig {

	@Resource(name = "myRoutingDataSource")
	private DataSource myRoutingDataSource;

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(myRoutingDataSource);
		sqlSessionFactoryBean
				.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mappers/*Mapper.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public PlatformTransactionManager platformTransactionManager() {
		return new DataSourceTransactionManager(myRoutingDataSource);
	}
}