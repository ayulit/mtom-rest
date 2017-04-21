package ru.mera.samples.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import ru.yandex.qatools.embed.service.EmbeddedService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;


public class EmbeddedServiceBean implements BeanNameAware{

  private static final Logger logger = LoggerFactory.getLogger(EmbeddedServiceBean.class);
  
  private EmbeddedService service;

  private String name;


  @PostConstruct
  public void start() throws IOException {
   // TODO use later with Embedded PostgreSQL      
    logger.info("EmbeddedService " + name + ": starting");
    try {
      service.start();
    } catch( Exception e ) {
      logger.error("Exception is occured while starting embedded service " + name + ": " + e.getMessage());
      throw e;
    }
    logger.info("EmbeddedService " + name + ": starting...OK");
  }

  @PreDestroy
  public void stop() {
   // TODO use later with Embedded PostgreSQL      
    logger.info("EmbeddedService " + name + ": stopping");
    try {
      service.stop();
    } catch( Exception e ) {
      logger.error("Exception is occured while stopping embedded service " + name + ": " + e.getMessage());
      throw e;
    }
    logger.info("EmbeddedService " + name + ": stopping...OK");
  }

  public void setService(EmbeddedService service) {
    this.service = service;
  }

  @Override
  public void setBeanName(String name) {
    this.name = name;
  }
}
