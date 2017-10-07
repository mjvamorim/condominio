package com.pegasus.condominio.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
//@ConfigurationProperties
public class GlobalProperties {

	@Value("${spring.datasource.url}")
	@NotEmpty
	private String url;


	@Value("${spring.datasource.username}")
	@NotEmpty
	private String username;

	@Value("${spring.datasource.password}")
	@NotEmpty
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	@NotEmpty
	private String driverClassName;

	@Value("${spring.jpa.properties.hibernate.dialect}")
	@NotEmpty
	private String dialect;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	@NotEmpty
	private String dllAuto;


	@Value("${hibernate.ejb.naming_strategy}")
	@NotEmpty
	private String namingStrategy;


	public String getUrl() {
		return url;
	}
	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}


	public String getDriverClassName() {
		return driverClassName;
	}


	public String getDialect() {
		return dialect;
	}


	public String getDllAuto() {
		return dllAuto;
	}


	public String getNamingStrategy() {
		return namingStrategy;
	}



	@Override
	public String toString() {
		return "GlobalProperties{" +
				", url='" + url + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", driverClassName='" + driverClassName + '\'' +
				", dialect='" + dialect + '\'' +
				", dllAuto='" + dllAuto + '\'' +
				", namingStrategy='" + namingStrategy + '\'' +
				'}';
	}
}