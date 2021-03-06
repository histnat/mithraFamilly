package net.mithra.familly.ws.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import net.mithra.familly.db.bo.DBUserService;
import net.mithra.familly.security.UserDetailsOpenService;
import net.mithra.familly.ws.security.RESTAuthenticationEntryPoint;
import net.mithra.familly.ws.security.RESTAuthenticationFailureHandler;
import net.mithra.familly.ws.security.RESTAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RESTAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private RESTAuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private RESTAuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private UserDetailsOpenService userDetailsOpenService;

//	@Override
//	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//		builder.inMemoryAuthentication().withUser("user").password("user").roles("USER").and().withUser("admin").password("admin").roles("ADMIN");
//	}
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	
//        auth
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER")
//                .and().withUser("admin").password("admin").roles("ADMIN");
    	
    	
    	ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        auth.userDetailsService(userDetailsOpenService);
//        .passwordEncoder(encoder);
//    	voir
//		https://www.concretepage.com/spring/spring-security/spring-mvc-security-jdbc-authentication-example-with-custom-userdetailsservice-and-database-tables-using-java-configuration
//		UserSysdoc
//        UserDetailsCAS
//        UserDetailsSysdoc
        
        
        
        
//        auth
//        .inMemoryAuthentication()
//          .withUser("user")  // #1
//            .password("password")
//            .roles("USER")
//            .and()
//          .withUser("admin") // #2
//            .password("password")
//            .roles("ADMIN","USER");
    }
    
    
    @Override
    public void configure(WebSecurity web) throws Exception {
//      web
//        .ignoring()
//           .antMatchers("/resources/**"); // #3
    }


	@Override
	protected void configure(HttpSecurity http) throws Exception {
//	    http
//	      .authorizeUrls()
//	        .antMatchers("/signup","/about").permitAll() 	// Allow anyone (including unauthenticated users) to access to the URLs “/signup” and “/about”
//	        .antMatchers("/admin/**").hasRole("ADMIN") 		//Any URL that starts with “/admin/” must be an administrative user. For our example, that would be the user “admin”
//	        .anyRequest().authenticated() 					// All remaining URLs require that the user be successfully authenticated
//	        .and()
//	    .formLogin()  										// Setup form based authentication using the Java configuration defaults. Authentication is performed when a POST is submitted to the URL “/login” with the parameters “username” and “password”
//	        .loginUrl("/login") // #9
//	        .permitAll(); // #5
	    http
           .authorizeRequests()
               .antMatchers("/", "/home"
            		  ,"/static/**","/index.html"
            		   ).permitAll()
               .anyRequest().authenticated()
               .and()
           .formLogin()
               .loginPage("/login")
               .loginProcessingUrl("/login")
               .defaultSuccessUrl("/index.html")
               .failureUrl("/login?error")
               .passwordParameter("password")
               .usernameParameter("username")
               .permitAll()
               .and()
           .logout().logoutUrl("/logout").logoutSuccessUrl("/index.html")
               .permitAll();
		http.csrf().disable();
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
		http.formLogin().successHandler(authenticationSuccessHandler);
		http.formLogin().failureHandler(authenticationFailureHandler);
	}
}
