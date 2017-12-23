package net.mithra.familly.ws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import net.mithra.familly.ws.config.SecurityConfig;




@ComponentScan(basePackages = {"net.mithra.familly.*"})
@Configuration
@EnableWebMvc
//@Import({ SecurityConfig.class }) --------->>>>> Ne fonctionne pas... à revoir comment ça fonctionne
@ImportResource("classpath:applicationContext-test.xml")
public class AppConfigTest {
	
	@Bean
	public CommonsMultipartResolver multipartResolver(){
		 CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		    commonsMultipartResolver.setDefaultEncoding("utf-8");
		    commonsMultipartResolver.setMaxUploadSize(50000000);
		    return commonsMultipartResolver;
	}
}
