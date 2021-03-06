package net.mithra.familly.ws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.mithra.familly.ws.config.SecurityConfig;




@ComponentScan(basePackages = {"net.mithra.familly.*"})
@Configuration
@EnableWebMvc
@Import({ SecurityConfig.class }) 
@ImportResource("classpath:spring.xml")
public class AppConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public CommonsMultipartResolver multipartResolver(){
		 CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		    commonsMultipartResolver.setDefaultEncoding("utf-8");
		    commonsMultipartResolver.setMaxUploadSize(50000000);
		    return commonsMultipartResolver;
	}
	
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/index.**").addResourceLocations("/index.html");
//      registry.addResourceHandler("/login.**").addResourceLocations("/login.html");
      registry.addResourceHandler("/static/**").addResourceLocations("/static/"); 
  }
  
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/").setViewName("index.html");
      registry.addViewController("/rest/login").setViewName("login");
  }

}
