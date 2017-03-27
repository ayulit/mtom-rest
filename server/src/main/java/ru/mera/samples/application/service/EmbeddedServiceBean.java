package ru.mera.samples.application.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import ru.yandex.qatools.embed.service.EmbeddedService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;


public class EmbeddedServiceBean implements BeanNameAware{

  private static final Log logger = LogFactory.getLog(EmbeddedServiceBean.class);

  private EmbeddedService service;

  private String name;


  @PostConstruct
  public void start() throws IOException {
    logger.info("EmbeddedService " + name + ": starting");
    try {
      service.start();
    } catch( Exception e ) {
      logger.fatal("Exception is occured while starting embedded service " + name + ": " + e.getMessage());
      throw e;
    }
    logger.info("EmbeddedService " + name + ": starting...OK");
  }

  @PreDestroy
  public void stop() {
    logger.info("EmbeddedService " + name + ": stopping");
    try {
      service.stop();
    } catch( Exception e ) {
      logger.fatal("Exception is occured while stopping embedded service " + name + ": " + e.getMessage());
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
