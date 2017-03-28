package ru.mera.samples.infrastructure.config;

import org.apache.log4j.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.mera.samples.application.service.EmbeddedServiceBean;
import ru.mera.samples.application.service.ImageServiceImpl;
import ru.yandex.qatools.embed.service.PostgresEmbeddedService;

import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class JPAConfig {

  private static final Logger logger = Logger.getLogger(ImageServiceImpl.class);

  public static final String HOST = "localhost";

// Native Postgre on 5432, Embedded on 5429
  public static final int PORT = 5432; 

  public static final String USERNAME = "postgres";

  public static final String PASSWORD = "123";

  public static final String DB_NAME = "mtom_repo";


  {
    logger.info("JPA Configuration loaded.");
  }

  @Bean
  public JpaTransactionManager jpaTransMan() {
    JpaTransactionManager jtManager = new JpaTransactionManager(entityManagerFactory().getObject());
    return jtManager;
  }

  @Bean
  @DependsOn( "embeddedServiceBean" )
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setPersistenceUnitName("PersistenceUnit");
    JpaVendorAdapter hibernateVendorAdapter = new HibernateJpaVendorAdapter();
    factoryBean.setJpaVendorAdapter(hibernateVendorAdapter);
    String[] packages = { "org.mera.samples.domain.entities" };
    factoryBean.setPackagesToScan(packages);
    factoryBean.setJpaProperties(getJPAProperties());
    return factoryBean;
  }

  @Bean
  public EmbeddedServiceBean embeddedServiceBean() throws IOException {
    EmbeddedServiceBean embeddedServiceBean = new EmbeddedServiceBean();
    embeddedServiceBean.setService(new PostgresEmbeddedService(getHost(), getPort(), getUsername(), getPassword(), getDbName()));
    return embeddedServiceBean;
  }

  @Bean
  public ModelMapper modelMapper() throws IOException {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper;
  }

  private Properties getJPAProperties() {
    Properties properties = new Properties();
    
	// xlitand: 
	// Drop and re-create the database schema on startup,
	// -create: every time
	// -update: if ONLY model changed!
    properties.setProperty("hibernate.hbm2ddl.auto", "create");
    properties.setProperty("hibernate.show_sql", "true");
    properties.setProperty("hibernate.format_sql", "true");
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    properties.setProperty("javax.persistence.jdbc.driver", "org.postgresql.Driver");
    properties.setProperty("javax.persistence.jdbc.url", "jdbc:postgresql://"+ getHost() +":"+ getPort() +"/"+
        getDbName());
    properties.setProperty("javax.persistence.jdbc.user", getUsername());
    properties.setProperty("javax.persistence.jdbc.password", getPassword());
    return properties;
  }

  private String getPassword() {
    return PASSWORD;
  }

  private String getUsername() {
    return USERNAME;
  }

  private String getDbName() {
    return DB_NAME;
  }

  private int getPort() {
    return PORT;
  }

  private String getHost() {
    return HOST;
  }
} 