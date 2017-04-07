package ru.mera.samples.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import ru.mera.samples.application.service.ImageService;
import ru.mera.samples.application.service.ImageServiceImpl;
import ru.mera.samples.domain.dao.ImageRepository;
import ru.mera.samples.domain.dao.ImageRepositoryImpl;
import ru.mera.samples.infrastructure.config.JPAConfig;
import ru.mera.samples.infrastructure.config.MtomServerConfiguration;

@Configuration
@PropertySource("classpath:conf/h2-test.properties")

@Import( {JPAConfig.class} )
//@Import( {MtomServerConfiguration.class} )
public class MtomServerTestConfiguration  {
    
    private static final Logger logger = Logger.getLogger(MtomServerTestConfiguration.class);

    {
        logger.info("Mtom Server Test Configuration loaded.");
    }
    
    @Bean
    public ImageService getImageService() {
        return new ImageServiceImpl();
    }
    
    @Bean
    public ImageRepository getImageRepository() {
        return new ImageRepositoryImpl();
    }
    
}
