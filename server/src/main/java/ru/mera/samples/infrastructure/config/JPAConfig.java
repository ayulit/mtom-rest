package ru.mera.samples.infrastructure.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ru.mera.samples.application.mappings.AddressToDTOMap;
import ru.mera.samples.application.mappings.AddressToEntityMap;
import ru.mera.samples.application.mappings.ImageToDTOMap;
import ru.mera.samples.application.mappings.ImageToEntityMap;
import ru.mera.samples.application.mappings.UserToDTOMap;
import ru.mera.samples.application.mappings.UserToEntityMap;
import ru.mera.samples.application.service.EmbeddedServiceBean;
import ru.mera.samples.infrastructure.services.DbFillingBean;
import ru.yandex.qatools.embed.service.PostgresEmbeddedService;

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
  @DependsOn( "embeddedServiceBean" )
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    
    factoryBean.setDataSource(dataSource());
    factoryBean.setPersistenceUnitName("PersistenceUnit");
    
    JpaVendorAdapter hibernateVendorAdapter = new HibernateJpaVendorAdapter();
    factoryBean.setJpaVendorAdapter(hibernateVendorAdapter);
    
    String[] packages = { "ru.mera.samples.domain.entities" };
    factoryBean.setPackagesToScan(packages);
    factoryBean.setJpaProperties(getJPAProperties());
    return factoryBean;
  }

  @Bean
  public EmbeddedServiceBean embeddedServiceBean() throws IOException {
    EmbeddedServiceBean embeddedServiceBean = new EmbeddedServiceBean();
    embeddedServiceBean.setService(new PostgresEmbeddedService(getHost(), Integer.parseInt(getPort()), getUsername(), getPassword(), getDbName()));
    return embeddedServiceBean;
  }

  @Bean
  public ModelMapper modelMapper() throws IOException {
    ModelMapper modelMapper = new ModelMapper();
    
    modelMapper.addMappings(new ImageToEntityMap());
    modelMapper.addMappings(new ImageToDTOMap());

    modelMapper.addMappings(getUserToEntityMap());
    modelMapper.addMappings(new UserToDTOMap());
    
    modelMapper.addMappings(getAddressToEntityMap());
    modelMapper.addMappings(new AddressToDTOMap());
    
    return modelMapper;
  }
  
  @Bean
  public AddressToEntityMap getAddressToEntityMap() {
      return new AddressToEntityMap();
  }

  @Bean
  public UserToEntityMap getUserToEntityMap() {
      return new UserToEntityMap();
  }  
 
  @Bean
  @DependsOn( "transactionManager" )
  public DbFillingBean fillDB() {
      return new DbFillingBean();
  }
  
  
  private Properties getJPAProperties() {
    final Properties properties = new Properties();
    
    properties.setProperty("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
    properties.setProperty("hibernate.show_sql", "true");
    properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
    properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
    properties.setProperty("hibernate.format_sql", "true");
    
    return properties;
  }
  
  
  private DataSource dataSource() {
      BasicDataSource  ds = new BasicDataSource ();
      
      ds.setUrl(environment.getRequiredProperty("jdbc.url"));     
      ds.setUsername(getUsername());
      ds.setPassword(getPassword());
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