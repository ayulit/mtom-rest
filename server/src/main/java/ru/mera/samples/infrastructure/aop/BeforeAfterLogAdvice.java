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
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Simple client that calls the <code>GetFlights</code> and <code>BookFlight</code> operations using JAX-WS.
 *
 * @author
 */
@Aspect
public class BeforeAfterLogAdvice {

  private static final Log logger = LogFactory.getLog(BeforeAfterLogAdvice.class);

  @Pointcut( "within(org.springframework.ws.samples.mtom.service.*)" )
  public void inImageRepositoryEndpoint() {
  }

  @Before( "inImageRepositoryEndpoint()" )
  public void before(JoinPoint joinPoint) {
    final String[] principals = new String[1];
    Optional.of(SecurityContextHolder.getContext()).ifPresent(
        securityContext -> Optional.of(securityContext.getAuthentication()).ifPresent(
            authentication -> Optional.of(authentication.getPrincipal()).ifPresent(o -> principals[0] = o.toString())));
    logger.info("Before method: " + joinPoint.getSignature().getName() + " by user " + principals);
  }

  @AfterReturning( "inImageRepositoryEndpoint()" )
  public void afterReturning(JoinPoint joinPoint) {
    final String[] principals = new String[1];
    Optional.of(SecurityContextHolder.getContext()).ifPresent(
        securityContext -> Optional.of(securityContext.getAuthentication()).ifPresent(
            authentication -> Optional.of(authentication.getPrincipal()).ifPresent(o -> principals[0] = o.toString())));
    logger.info("After method: " + joinPoint.getSignature().getName() + " by user " + principals);
  }


}
