package ru.mera.samples.infrastructure.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import ru.mera.samples.infrastructure.aop.AroundCacheValidationAdvice;
import ru.mera.samples.infrastructure.aop.BeforeAfterLogAdvice;
import ru.mera.samples.infrastructure.services.ImageCacheService;
import ru.mera.samples.infrastructure.services.ImageCacheServiceImpl;

@Configuration
@EnableAspectJAutoProxy
@EnableSpringConfigured
public class AopConfiguration {
	
	@Bean
	@Scope( ConfigurableBeanFactory.SCOPE_PROTOTYPE )
	public BeforeAfterLogAdvice beforeAfterLogAdvice() {
		return new BeforeAfterLogAdvice();
	}
	
	@Bean
	@Scope( ConfigurableBeanFactory.SCOPE_PROTOTYPE )
	public AroundCacheValidationAdvice aroundCacheValidationAdvice() {
		return new AroundCacheValidationAdvice();
	}

	@Bean
	public ImageCacheService imageCacheService(){
		ImageCacheService imageCacheService = new ImageCacheServiceImpl();
		return imageCacheService;
	}
}
