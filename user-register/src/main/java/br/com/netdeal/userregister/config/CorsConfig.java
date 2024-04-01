package br.com.netdeal.userregister.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class CorsConfig implements WebMvcConfigurer{
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/user/**")
            .allowedOrigins("http://localhost")
            .allowedMethods("GET","POST","PUT", "DELETE")
            .allowCredentials(false).maxAge(3600);
    }
}
