package com.pegasus.condominio.tenant;
//package com.pegasus.condominio.tenant;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import com.pegasus.condominio.config.GlobalProperties;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.util.*;
//
//@Configuration
//@EnableJpaRepositories(
//		entityManagerFactoryRef = "entityManagerFactory", 
//		transactionManagerRef = "transactionManager",
//		basePackages = { "com.pegasus.condominio" })
//public class MultitenantConfiguration {
//
//	@Autowired
//	private DataSourceProperties properties;
//	
//    @Autowired
//    private GlobalProperties global;
//
//	/**
//	 * Defines the data source for the application
//	 * @return
//	 */
//	@Bean(name="dataSource")  
//	@ConfigurationProperties(prefix = "spring.datasource")
//	public DataSource dataSource() {
//		//File[] files = Paths.get("tenants").toFile().listFiles();
//		Map<Object,Object> resolvedDataSources = new HashMap<>();
//
//		//for(File propertyFile : files) {
//		for (Integer i=0;i<=10;i++) {
//			//Properties tenantProperties = new Properties();
//			DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader());
//
//			try {
//				//tenantProperties.load(new FileInputStream(propertyFile));
//
//				//String tenantDb = tenantProperties.getProperty("name");
//				String tenantDb = "db"+i;
//				if (i==0) {tenantDb="db";}
//				System.out.println(tenantDb);
//
////				dataSourceBuilder.driverClassName(properties.getDriverClassName())
////				.url(tenantProperties.getProperty("spring.datasource.url"))
////				.username(tenantProperties.getProperty("spring.datasource.username"))
////				.password(tenantProperties.getProperty("spring.datasource.password"));
//				
//				//String url = "jdbc:mysql://localhost:3306/"+tenantDb+"?createDatabaseIfNotExist=true";
//				String url = global.getUrl();
//				url = url.replaceAll("dbx", tenantDb);
//				System.out.println(url);
//				
//				dataSourceBuilder.driverClassName(properties.getDriverClassName())
//				.url(url)
//				.username(global.getUsername())
//				.password(global.getPassword());
//
//				if(global.getDriverClassName() != null) {
//					dataSourceBuilder.type(properties.getType());
//				}
//
//				resolvedDataSources.put(tenantDb, dataSourceBuilder.build());
//			} catch (Exception e) {
//				e.printStackTrace();
//
//				return null;
//			}
//		}
//
//		// Create the final multi-tenant source.
//		// It needs a default database to connect to.
//		// Make sure that the default database is actually an empty tenant database.
//		// Don't use that for a regular tenant if you want things to be safe!
//		MultitenantDataSource dataSource = new MultitenantDataSource();
//		dataSource.setDefaultTargetDataSource(defaultDataSource());
//		dataSource.setTargetDataSources(resolvedDataSources);
//
//		// Call this to finalize the initialization of the data source.
//		dataSource.afterPropertiesSet();
//
//		return dataSource;
//	}
//
//	/**
//	 * Creates the default data source for the application
//	 * @return
//	 */
//	private DataSource defaultDataSource() {
//		DataSourceBuilder dataSourceBuilder = new DataSourceBuilder(this.getClass().getClassLoader())
//				.driverClassName(properties.getDriverClassName())
//				.url(properties.getUrl())
//				.username(properties.getUsername())
//				.password(properties.getPassword());
//
//		if(properties.getType() != null) {
//			dataSourceBuilder.type(properties.getType());
//		}
//
//		return dataSourceBuilder.build();
//	}
//
//
//
//	@Bean(name = "entityManagerFactory")
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//		em.setDataSource(dataSource());
//		em.setPackagesToScan(new String[] { "com.pegasus.condominio" });
//
//		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		em.setJpaVendorAdapter(vendorAdapter);
//		em.setJpaProperties(additionalProperties());
//
//		return em;
//	}
//
//	@Bean(name = "transactionManager")
//	public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(emf);
//
//		return transactionManager;
//	}
//
//	@Bean
//	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
//		return new PersistenceExceptionTranslationPostProcessor();
//	}
//
//	Properties additionalProperties() {
//		Properties properties = new Properties();
//		properties.setProperty("hibernate.hbm2ddl.auto", global.getDllAuto());
//		properties.setProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
//		properties.setProperty("hibernate.dialect", global.getDialect());
//		return properties;
//	}
//
//}