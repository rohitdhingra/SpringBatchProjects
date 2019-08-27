//package com.rohit.config;
//
//import java.util.Properties;
//
//import javax.annotation.Resource;
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//@EnableBatchProcessing
//public class SpringBatchConfig {
//	@Value("${spring.datasource.url}")
//	private String url;
//	@Value("${spring.datasource.username}")
//	private String username;
//	@Value("${spring.datasource.driver-class-name}")
//	private String driverClass;
//	@Value("${spring.datasource.password}")
//	private String password;
//
//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(this.driverClass);
//		dataSource.setUsername(this.username);
//		dataSource.setPassword(this.password);
//		dataSource.setUrl(this.url);
//		return dataSource;
//	}
//
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//		em.setDataSource(dataSource());
//		em.setPackagesToScan(new String[] { "com.rohit.repositories", "com.rohit.entities" });
//
//		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		em.setJpaVendorAdapter(vendorAdapter);
//		em.setJpaProperties(additionalProperties());
//
//		return em;
//	}
//
//	@Bean
//	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(emf);
//
//		return transactionManager;
//	}
//
//	@Bean
//	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//		return new PersistenceExceptionTranslationPostProcessor();
//	}
//
//	Properties additionalProperties() {
//		Properties properties = new Properties();
//		properties.setProperty("hibernate.hbm2ddl.auto", "update");
//		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//
//		return properties;
//	}
//
//	@Autowired
//	private JobBuilderFactory jobs;
//
//	@Autowired
//	private StepBuilderFactory steps;
//
//	@Value("input/record.csv")
//	private Resource inputCsv;
//	
//	@Value("file:xml/output.xml")
//    private Resource outputXml;
//
//}
