package com.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@Configuration
@EntityScan(basePackageClasses = {
		UnicalPhonebook.class,
		Jsr310JpaConverters.class
})
public class UnicalPhonebook implements ApplicationListener<ApplicationReadyEvent>{

	private static final Logger LOGGER = LoggerFactory.getLogger(UnicalPhonebook.class);
	private AutowireCapableBeanFactory beanFactory;
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(UnicalPhonebook.class, args);
		
	}
	
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // Add your frontend URL here
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
	

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
//		initTelegramBot();
	}

	
	@Autowired
	public void MyFactoryBean(AutowireCapableBeanFactory beanFactory) {
	    this.beanFactory = beanFactory;
	}

	

	
}
