/*
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.mera.samples.infrastructure.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mera.samples.domain.entities.ImageEntity;
import ru.mera.samples.infrastructure.services.ImageCacheService;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Simple client that calls the <code>GetFlights</code> and <code>BookFlight</code> operations using JAX-WS.
 *
 * @author
 */
@Aspect
public class AroundCacheValidationAdvice {

  private static final Log logger = LogFactory.getLog(AroundCacheValidationAdvice.class);

  @Autowired
  private ImageCacheService imageCacheService;


  @Pointcut( "execution(* ru.mera.samples.domain.dao.ImageRepositoryImpl.findByName(..)) && args(name)" )
  public void readFromCacheAndValidationByName(String name) {
  }

  @Pointcut( "execution(* ru.mera.samples.domain.dao.ImageRepositoryImpl.findById(..)) && args(id)" )
  public void readFromCacheAndValidationByID(Long id) {
  }

  @Pointcut( "execution(* ru.mera.samples.domain.dao.ImageRepositoryImpl.save(..)) && args(entity)" )
  public void loadToCacheAndValidation(ImageEntity entity) {
  }

  @Pointcut( "execution(* ru.mera.samples.domain.dao.ImageRepositoryImpl.update(..)) && args(entity)" )
  public void updateToCacheAndValidation(ImageEntity entity) {
  }

  @Around( "readFromCacheAndValidationByName(name)" )
  public Object invoke(ProceedingJoinPoint pjp, String name) throws Throwable {
    return getCachedObject(pjp, name, () -> imageCacheService.getImage(name));
  }

  @Around( "readFromCacheAndValidationByID(id)" )
  public Object invoke(ProceedingJoinPoint pjp, Long id) throws Throwable {
    return getCachedObject(pjp, String.valueOf(id), () -> imageCacheService.getImage(id) );
  }

  protected Object getCachedObject(ProceedingJoinPoint pjp, String key, Supplier<Optional<ImageEntity>> suplier) {
    String funcName = pjp.getSignature().getName();
    logger.info("Around method: " + funcName);
    Optional<ImageEntity> image = suplier.get();
    ImageEntity bufferedImage = image.orElseGet(() -> {
      try {
        logger.info("Around method: not found cached image " + key);
        return (ImageEntity) pjp.proceed();
      } catch( Throwable throwable ) {
        throwable.printStackTrace();
        return null;
      }
    });
    return bufferedImage;
  }

  @Around( "loadToCacheAndValidation(entity) || updateToCacheAndValidation(entity)" )
  public Object invoke(ProceedingJoinPoint pjp, ImageEntity entity) throws Throwable {
    String funcName = pjp.getSignature().getName();
    logger.info("Around method: " + funcName);
    imageCacheService.addImage(entity.getId(), entity.getName(), entity);
    logger.info("Around method: create image in cache");
    return pjp.proceed();
  }
}
