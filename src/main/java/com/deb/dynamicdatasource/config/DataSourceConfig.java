package com.deb.dynamicdatasource.config;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.deb.dynamicdatasource.enums.DatabaseContext;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.deb.dynamicdatasource", entityManagerFactoryRef = "entityManager", transactionManagerRef = "multiTransactionManager")
public class DataSourceConfig {

	@Primary
	@Bean(name = "mainDataSource")
	@ConfigurationProperties("app.datasource.main")
	public DataSource mainDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "arayabhatDataSource")
	@ConfigurationProperties("app.datasource.arayabhat")
	public DataSource aryabhatDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "saralDataSource")
	@ConfigurationProperties("app.datasource.saral")
	public DataSource saralDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "dataSource")
	public DataSource dataSource() {

		Map<Object, Object> dataSourceMap = new HashMap<>();

		dataSourceMap.put(DatabaseContext.MAIN, mainDataSource());
		dataSourceMap.put(DatabaseContext.SATELITE_SARAL, saralDataSource());
		dataSourceMap.put(DatabaseContext.SATELITE_ARYABHAT, aryabhatDataSource());

		MultiRoutingDataSource routingDataSource = new MultiRoutingDataSource();
		routingDataSource.setDefaultTargetDataSource(mainDataSource());
		routingDataSource.setTargetDataSources(dataSourceMap);
		return routingDataSource;
	}

	@Bean(name = "entityManager")
	public LocalContainerEntityManagerFactoryBean entityManager() throws PropertyVetoException {

		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactory.setPackagesToScan("com.deb.dynamicdatasource.entity");
		entityManagerFactory.setJpaProperties(hibernateProperties());
		return entityManagerFactory;
	}

	@Bean(name = "multiTransactionManager")
	    public PlatformTransactionManager multiTransactionManager() throws PropertyVetoException {
	        JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(entityManager().getObject());
	        return transactionManager;
	    }

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", true);
		properties.put("hibernate.format_sql", true);
		return properties;
	}
}
