package ru.mera.samples.infrastructure.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

  private static final Log logger = LogFactory.getLog(ImageServiceImpl.class);

  public static final String HOST = "localhost";

  public static final int PORT = 5432;

  public static final String USERNAME = "postgres";

  public static final String PASSWORD = "postrges";

  public static final String DB_NAME = "test";


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