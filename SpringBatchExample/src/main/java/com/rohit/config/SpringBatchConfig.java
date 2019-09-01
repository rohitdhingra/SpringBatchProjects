package com.rohit.config;



import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.rohit.model.User;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			ItemReader<User> itemReader, ItemProcessor<User, User> itemProcessor, ItemWriter<User> itemWriter) {
		Step step = stepBuilderFactory.get("ETLFileLoadStep").<User,User>chunk(100)
				.reader(itemReader).processor(itemProcessor).writer(itemWriter)
				.build();
		Job etlJob = jobBuilderFactory.get("ETLJob").incrementer(new RunIdIncrementer()).start(step).build();
		return etlJob;
	}

	@Bean
	public FlatFileItemReader<User> itemReader(@Value("${input}") Resource resource)
	{
		FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	public LineMapper<User> lineMapper() {
		// TODO Auto-generated method stub
		DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
		
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] {"id","name","profession","age"});
		
		BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(User.class);
		
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		return defaultLineMapper;
	}
	
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

}
