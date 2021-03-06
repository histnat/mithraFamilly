package net.mithra.familly.ws.controller.conv;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/rest")
@CrossOrigin
@RestController
public class DemoController {
	

	
	@Autowired
	private ApplicationContext appContext;
	
	

	@RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HelloweenResponse> hello(Principal principal) {
		System.out.println("passe");

		return new ResponseEntity<HelloweenResponse>(new HelloweenResponse("Happy Halloween, fred!"), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/run", method = RequestMethod.GET)
	public @ResponseBody String runConv() {
		System.out.println("passe run : ");
		//new SpringBeanProvider(appContext);
//		RestApi convApi = (RestApi) SpringBeanProvider.getBean("ConvRestApi");
//		convApi.run();
		//restApi.run();
		return "ok";
	}
	
	@RequestMapping(value = "/run1", method = RequestMethod.GET)
	public ResponseEntity getCustomer(@PathVariable("id") Long id) {

		

		return new ResponseEntity("qsdfqsdf", HttpStatus.OK);
	}
	
	

	public static class HelloweenResponse {
		private String message;

		public HelloweenResponse(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}



	

	/**
	 * @return the appContext
	 */
	public ApplicationContext getAppContext() {
		return appContext;
	}

	/**
	 * @param appContext the appContext to set
	 */
	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	
	
	
}
