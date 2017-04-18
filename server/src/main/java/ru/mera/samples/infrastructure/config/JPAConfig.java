package ru.mera.samples.infrastructure.config;

import org.apache.commons.dbcp2.BasicDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ru.mera.samples.application.mappings.AddressToDTOMap;
import ru.mera.samples.application.mappings.AddressToEntityMap;
import ru.mera.samples.application.mappings.ImageToDTOMap;
import ru.mera.samples.application.mappings.ImageToEntityMap;
import ru.mera.samples.application.mappings.UserToEntityMap;
import ru.mera.samples.application.service.EmbeddedServiceBean;
import ru.mera.samples.application.service.ImageServiceImpl;
import ru.yandex.qatools.embed.service.PostgresEmbeddedService;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class JPAConfig {

  private static final Logger logger = LoggerFactory.getLogger(JPAConfig.class);

  @Autowired
  private Environment environment;

  {
    logger.info("JPA Configuration loaded.");
  }

  @Bean
  public JpaTransactionManager transactionManager() {
    JpaTransactionManager jtManager = new JpaTransactionManager(entityManagerFactory().getObject());
    return jtManager;
  }

  @Bean
//  @DependsOn( "embeddedServiceBean" ) // TODO use later with Embedded PostgreSQL
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    
    factoryBean.setDataSource(dataSource());
    
    // Actually not used...
    factoryBean.setPersistenceUnitName("PersistenceUnit");
    
    // JPA features...?
    JpaVendorAdapter hibernateVendorAdapter = new HibernateJpaVendorAdapter();
    factoryBean.setJpaVendorAdapter(hibernateVendorAdapter);
    
    String[] packages = { "ru.mera.samples.domain.entities" };
    factoryBean.setPackagesToScan(packages);
    factoryBean.setJpaProperties(getJPAProperties());
    return factoryBean;
  }

//  TODO use later this Embedded PostgreSQL
/*  @Bean
  public EmbeddedServiceBean embeddedServiceBean() throws IOException {
    EmbeddedServiceBean embeddedServiceBean = new EmbeddedServiceBean();
    embeddedServiceBean.setService(new PostgresEmbeddedService(getHost(), getPort(), getUsername(), getPassword(), getDbName()));
    return embeddedServiceBean;
  }*/

  @Bean
//  @Lazy // TODO i think its useless
  public ModelMapper modelMapper() throws IOException {
    ModelMapper modelMapper = new ModelMapper();
    
    modelMapper.addMappings(new ImageToEntityMap());
    modelMapper.addMappings(new ImageToDTOMap());

    modelMapper.addMappings(new UserToEntityMap());
    
    modelMapper.addMappings(getAddressToEntityMap());
    modelMapper.addMappings(new AddressToDTOMap());
    
    return modelMapper;
  }
  
  @Bean
  public AddressToEntityMap getAddressToEntityMap() {
      return new AddressToEntityMap();
  }

  private Properties getJPAProperties() {
    final Properties properties = new Properties();
    
    properties.setProperty("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
    properties.setProperty("hibernate.show_sql", "true");
    properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false"); // from REST book    

    // xlitand: 
	// Drop and re-create the database schema on startup,
	// -create: every time
	// -update: if ONLY model changed!
    properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
    
    // from Yura: cool feature! Really makes SQL queries nice :)
    properties.setProperty("hibernate.format_sql", "true");
    
    return properties;
  }
  
  
  private DataSource dataSource() {
      // DriverManagerDataSource is also acceptable.
      BasicDataSource  ds = new BasicDataSource ();
      
      ds.setUrl(environment.getRequiredProperty("jdbc.url"));     
      ds.setUsername(getUsername());
      ds.setPassword(getPassword());
//      ds.setMaxTotal(Integer.valueOf(environment.getRequiredProperty("jdbc.maxConnections"))); // from REST book
      ds.setDriverClassName(environment.getRequiredProperty("jdbc.driver"));

      return ds;
  }

  private String getPassword() {
    return environment.getRequiredProperty("jdbc.password");
  }
  private String getUsername() {
    return environment.getRequiredProperty("jdbc.username");
  }
  private String getDbName() {
    return environment.getRequiredProperty("jdbc.dbname");
  }
  private String getPort() {
    return environment.getRequiredProperty("jdbc.port");
  }
  private String getHost() {
    return environment.getRequiredProperty("jdbc.host");
  }
} 