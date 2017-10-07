package com.pegasus.condominio.config;

import java.beans.PropertyDescriptor;


import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

@Component
public class MyUtils {
	
	private static GlobalProperties global;
	
	
    @Autowired
    private GlobalProperties tGlobal;

    @PostConstruct
    public void init() {
    	    MyUtils.global = tGlobal;
    }


	public static void createDB(String tenantDb)  {
		try {
			System.out.println(tenantDb);
			String url = global.getUrl();
			url = url.replaceAll("dbx", tenantDb);
			System.out.println(url);
			
			DataSource ds = new DataSource();
			ds.setDriverClassName(global.getDriverClassName());
			ds.setUrl(url);
			ds.setUsername(global.getUsername());
			ds.setPassword(global.getPassword());

			Resource initSchema = new ClassPathResource("scripts/schema-export.sql");
			//Resource initData = new ClassPathResource("scripts/data.sql");
			DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);


			DatabasePopulatorUtils.execute(databasePopulator,ds);


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void exportDDL() {
		try {
			System.out.println(global.toString());
			
			SchemaExport schema = new SchemaExport(getConfiguration());
			schema.setDelimiter(";");
			schema.setHaltOnError(false);
			schema.setFormat(true);
			Resource fileSchema = new ClassPathResource("scripts/schema-export.sql");		

			schema.setOutputFile(fileSchema.getFile().getPath());
			schema.execute(true, false, false, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static org.hibernate.cfg.Configuration  getConfiguration() {
		org.hibernate.cfg.Configuration  cfg = new org.hibernate.cfg.Configuration();
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
		for (BeanDefinition bd : scanner.findCandidateComponents("com.pegasus.condominio.model")) {
			String name = bd.getBeanClassName();
			try {
				System.out.println("Added annotated entity class " + bd.getBeanClassName());
				cfg.addAnnotatedClass(Class.forName(name));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		cfg.setProperty("hibernate.dialect", global.getDialect());
		cfg.setProperty("hibernate.show_sql", "true");
		cfg.setProperty("hibernate.format_sql", "true");
		cfg.setProperty("org.hibernate.tool.hbm2ddl", global.getDllAuto());
		cfg.setProperty("spring.jpa.hibernate.ddl-auto", global.getDllAuto());

		cfg.setProperty("hibernate.connection.url", global.getUrl());
		cfg.setProperty("hibernate.connection.username", global.getUsername());
		cfg.setProperty("hibernate.connection.password", global.getPassword());
		cfg.setProperty("hibernate.connection.driver", global.getDriverClassName());
		cfg.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
		return cfg;
	}

	public static void copyNonNullProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	public static String[] getNullPropertyNames (Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for(java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null) emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);

	}


}
