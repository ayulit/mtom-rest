package ru.mera.samples.infrastructure.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import ru.mera.samples.application.service.AddressService;
import ru.mera.samples.application.service.AddressServiceImpl;
import ru.mera.samples.application.service.UserService;
import ru.mera.samples.application.service.UserServiceImpl;
import ru.mera.samples.domain.dao.AddressRepository;
import ru.mera.samples.domain.dao.AddressRepositoryImpl;
import ru.mera.samples.domain.dao.ImageRepository;
import ru.mera.samples.domain.dao.ImageRepositoryImpl;
import ru.mera.samples.application.service.ImageService;
import ru.mera.samples.application.service.ImageServiceImpl;
import ru.mera.samples.domain.dao.UserRepository;
import ru.mera.samples.domain.dao.UserRepositoryImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableAspectJAutoProxy
@EnableSwagger2
@EnableWebMvc
@Import( { JPAConfig.class, AopConfiguration.class } )
@ComponentScan( "ru.mera.samples.domain.dao" )
public class MtomServerConfiguration {

  @Bean
  @Scope( value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public ImageService imageService() {
    return new ImageServiceImpl();
  }

  @Bean
  @Scope( value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public ImageRepository imageRepository() {
    return new ImageRepositoryImpl();
  }

  @Bean
  @Scope( value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public UserService userService() {
    return new UserServiceImpl();
  }

  @Bean
  @Scope( value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public UserRepository userRepository() {
    return new UserRepositoryImpl();
  }

  @Bean
  @Scope( value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public AddressService addressService() {
    return new AddressServiceImpl();
  }

  @Bean
  @Scope( value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public AddressRepository addressRepository() {
    return new AddressRepositoryImpl();
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }
}
