package net.mithra.familly.ws.controller;


import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import net.mithra.familly.ws.controller.model.TaskParameterModel;

@RequestMapping("/rest")
//@CrossOrigin
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@RestController
public class ScenarioControllerPOST extends ControllerHelper{
	
	
//	@RequestMapping(value="/login", method = RequestMethod.POST)
//	public Authentication login(@RequestParam("username")String username,@RequestParam("password")String password ) {
//	    Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
////	    																		,authorities);// authorities représente une liste de SimpleGrantedAuthority
//	    boolean isAuthenticated = isAuthenticated(authentication);
//	    if (isAuthenticated) {
//	        SecurityContextHolder.getContext().setAuthentication(authentication);
//	    }
//	    return authentication;
//	}
//
//	private boolean isAuthenticated(Authentication authentication) {
//	    return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
//	}
	
	private List<TaskParameterModel> parameterList=null;
	
	/*
	 * 
	 *  SAVE scenario
	 * 
	 * */
	
/*	@RequestMapping(value="/conv/scenario/save", method = RequestMethod.POST)
	public ResponseEntity<String> save(@RequestBody ScenarioDetailledModel scenarioDetailledModel) {

		try {
			dbExecutionService.save(serviceMapper.getOrUpdateCoScenario(dbExecutionService.findById(scenarioDetailledModel.getId()), scenarioDetailledModel));
		} catch (DBNONullException|DBNOUniqueException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity("Scenario" + scenarioDetailledModel.getName() +"could not be saved", HttpStatus.NOT_MODIFIED);
		}
	    return new ResponseEntity<String>(scenarioDetailledModel.getId(), HttpStatus.CREATED);
	}
  */
	/*
	 * 
	 *  DELETE scenario
	 * 
	 * */
	
//	String user="lee"; //TODO -> JWL mettre en place le système de login user
	
	/*
	@RequestMapping(value="/conv/scenario/{id}/delete", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable String id) {
		  ScenarioDetailledModel scenarioModel= serviceMapper.getScenarioDetailledModel(dbExecutionService.findById(id));
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  String user = auth.getName();
		if(!scenarioModel.getCreatorName().equals(user))
			return new ResponseEntity("Delete scenario not possible : " + user+" not owner", HttpStatus.NOT_FOUND);
		dbExecutionService.deleteScenario(id);
		return new ResponseEntity(id, HttpStatus.OK);

	}
	
	
	
	
	
	//rest/conv/scenario/{id}/run ->	run du scenario
	@RequestMapping(value="/conv/scenario/{id}/run", method =  RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> run(@PathVariable("id") String id,
			@RequestParam("listParam")String listParamJsonString,
			@RequestParam("files") MultipartFile[] files

			){
			
		CoScenario coScenarioDb=dbExecutionService.findById(id);
		if (coScenarioDb == null) {
			return new ResponseEntity("No Scenario found for ID " + id, HttpStatus.NOT_FOUND);
		}
			
		
		try {
			//transfert downloaded file to temp directory
			File[] retrievedFiles=serviceMapper.multipartFilesToFiles(files);
			parametersCollector.setParameterList(Arrays.asList(new ObjectMapper().readValue(listParamJsonString, TaskParameterModel[].class)));
			parametersCollector.setParameterList(serviceMapper.updateTypeFileParameters(parametersCollector.getParameterList(),retrievedFiles));
		} catch (JsonParseException|JsonMappingException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity("Something wrong with parameter : " + listParamJsonString, HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity("Something wrong with parameter : " + files[0].getOriginalFilename(), HttpStatus.BAD_REQUEST);
		}
		
		if(parametersCollector.getParameterList()==null)
			return new ResponseEntity("Something wrong with parameter : " + listParamJsonString, HttpStatus.BAD_REQUEST);
		if(!openConvService.initSequence(coScenarioDb).equals(LoggerLevel.INFO))
			return new ResponseEntity("Cannot initialise conversion for scenario : " + id, HttpStatus.BAD_REQUEST);
//		new Thread()
//		{
//		    public void run() {
				openConvService.launchSequence();
//		    }
//		}.start();

//		renvoyer openConvService.getIdExe() dès le début
	    return new ResponseEntity(openConvService.getIdExe(), HttpStatus.CREATED);
	}
	
	
	
	*/
	
	
	/*
	 * 
	 *  post askparams
	 * 
	 * */
	
	/* exemple requête:
	 
	 
	 POST /openconv/rest/conv/askparams
Content-Disposition: form-data; name="listParam"

[
  {
         "id": {
           "taskId": "1",
           "parameterName": "inputFolder"
         },
         "label": "R\ufffdpertoire d'entr\ufffde",
         "value": "\/xml\/cobra",
         "code": "inputFolder",
         "type": "FILE",
         "descr": "R\ufffdpertoire des fichiers \ufffd charger"
       },
   {
         "id": {
           "taskId": "1",
           "parameterName": "extensionFilter"
         },
         "label": "Extension du filtre",
         "value": null,
         "code": "extensionFilter",
         "type": "TXT",
         "descr": "Description extension du filtre"
       }
 ]
------WebKitFormBoundary7MA4YWxkTrZu0gW--
	 

*/
	/*
	//rest/conv/scenario/{id}/run ->	run du scenario
	@RequestMapping(value="/conv/askparams", method =  RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> askparams(@RequestParam("listParam")String listParamJsonString
			){
			
		
		try {
			parametersCollector.setParameterList(Arrays.asList(new ObjectMapper().readValue(listParamJsonString, TaskParameterModel[].class)));
		} catch (JsonParseException|JsonMappingException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity("Something wrong with parameter : " + listParamJsonString, HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity("Something wrong with parameter : " + listParamJsonString, HttpStatus.BAD_REQUEST);
		}
		
		if(parametersCollector.getParameterList()==null)
			return new ResponseEntity("Something wrong with parameter : " + listParamJsonString, HttpStatus.BAD_REQUEST);

	
		
		serviceMapper.initAskParameters();

//		renvoyer openConvService.getIdExe() dès le début
	    return new ResponseEntity("", HttpStatus.CREATED);
	}*/
	
}
