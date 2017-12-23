package net.mithra.familly.ws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@ComponentScan(basePackages = {"com.sonovision","com.sedoc"})
//@Configuration
//@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/index.**").addResourceLocations("/index.html");
//        registry.addResourceHandler("/static/**").addResourceLocations("/static/"); 
//    }
//    
//    
//    
//    @Bean
//    public CommonsMultipartResolver multipartResolver(){
//
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        return resolver;
//    }
}
