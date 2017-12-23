package net.mithra.familly.ws.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import net.mithra.familly.ws.controller.model.UserInfoModel;

@RequestMapping("/rest")
//@CrossOrigin
@Scope(value = WebApplicationContext.SCOPE_SESSION)
@RestController
public class ScenarioControllerGET extends ControllerHelper{

	/*
	 * 
	 *  LISTE DES SCENARIOS
	 * 
	 * */
	
//	/rest/conv/scenario	-> Liste des scénario  OK
   /* @RequestMapping(value="/conv/scenario", method=RequestMethod.GET)
    public ResponseEntity<List<ScenarioMiniModel>> provideScenarioList() {
    	List<ScenarioMiniModel> scenarioList= serviceMapper.getScenarioListModel(dbExecutionService.listScenario());
         if(scenarioList==null || scenarioList.isEmpty())
        	 return new ResponseEntity("No Scenario found ", HttpStatus.NOT_FOUND);
         return  new ResponseEntity< List<ScenarioMiniModel>>(scenarioList, HttpStatus.OK);
    }*/
    

	/*
	 * 
	 *  DETAIL D'UN SCENARIO
	 * 
	 * */
    
//  /rest/conv/scenario/scenarioId	->	Detail du senario  OK
  /*@RequestMapping(value="/conv/scenario/{id}", method=RequestMethod.GET)
	public ResponseEntity<ScenarioDetailledModel> getScenario(@PathVariable("id") String id) {
	  ScenarioDetailledModel scenarioModel= serviceMapper.getScenarioDetailledModel(dbExecutionService.findById(id));
		if (scenarioModel == null) {
			return new ResponseEntity("No Scenario found for ID " + id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(scenarioModel, HttpStatus.OK);
	}*/
  
  
    
	/*
	 * 
	 *  RESUME DE L'EXECUTION
	 * 
	 * */
  
//  /rest/conv/scenario/scenarioId/{idexecution}	->	Resume de l'execution OK
  /*@RequestMapping(value="/conv/scenario/{scenarioId}/{idexecution}", method=RequestMethod.GET)
	public ResponseEntity<HistoryDetailedModel>getResumeExec(@PathVariable("scenarioId") String scenarioId,@PathVariable("idexecution") String idexecution) {
	  HistoryDetailedModel historyDetailedModel= serviceMapper.getHistoryDetailedModel(dbExecutionService.get(scenarioId, idexecution));
	  if(historyDetailedModel==null)
     	 return new ResponseEntity("No historyList found ", HttpStatus.NOT_FOUND);	  
	  return new ResponseEntity(historyDetailedModel, HttpStatus.OK);

	}*/
    
  
	/*
	 * 
	 *  USERINFO
	 * 
	 * */

// /rest/userinfo	->	Renvoie tout l'historique d'execution OK
@RequestMapping(value="/userinfo", method=RequestMethod.GET)
	public ResponseEntity<UserInfoModel> getUserInfo(Authentication authentication) {
	
	UserInfoModel userInfo= serviceMapper.getUserInfo(dbUserService.findUserByLogin(authentication.getName()));

		  if(userInfo==null)
	     	 return new ResponseEntity("No user found "+authentication.getName(), HttpStatus.NOT_FOUND);	  
		  return new ResponseEntity< UserInfoModel>(userInfo, HttpStatus.OK);
	}
  
	/*
	 * 
	 *  HISTORIQUE
	 * 
	 * */
  
  // /rest/conv/history	->	Renvoie tout l'historique d'execution OK
 /* @RequestMapping(value="/conv/history", method=RequestMethod.GET)
	public ResponseEntity<List<HistoryDetailedModel>>getHistory() {
	  List<HistoryDetailedModel> historyDetailedModelList= serviceMapper.getHistoryDetailedModelList(dbExecutionService.listHistory());

		  if(historyDetailedModelList==null || historyDetailedModelList.isEmpty())
	     	 return new ResponseEntity("No historyList found ", HttpStatus.NOT_FOUND);	  
		  return new ResponseEntity< List<HistoryDetailedModel>>(historyDetailedModelList, HttpStatus.OK);
	}*/
  
	/*
	 * 
	 *  LISTE DES SCENARIOS DE USERNAME
	 * 
	 * */
  
//rest/conv/scenario?username=jlee	->	Liste des scenarios de username
/*@RequestMapping(value="/conv/scenario", method=RequestMethod.GET,params = {"username"})
	public ResponseEntity<List<ScenarioMiniModel>> getScenarioByUser(@RequestParam("username") String username) {
	
	FaUser user=dbUserService.findUserByLogin(username);
	if(user==null)
		 return new ResponseEntity("No user found "+username, HttpStatus.NOT_FOUND);
	
	List<ScenarioMiniModel> scenarioList= serviceMapper.getScenarioListModel(dbExecutionService.list(user));
    if(scenarioList==null || scenarioList.isEmpty())
   	 	return new ResponseEntity("No Scenario found for "+user.getLogin(), HttpStatus.NOT_FOUND);
    return  new ResponseEntity< List<ScenarioMiniModel>>(scenarioList, HttpStatus.OK);
	}*/
  

/*
 * SCENARIO SETUP
 *  DETAIL DU SCENARIO, AVEC SEULEMENT LES TACHES PARAMETRABLES
 * 
 * */
  
///rest/conv/scenario/scenarioId/setup ->	Detail du senario
/*@RequestMapping(value="/conv/scenario/{id}/setup", method=RequestMethod.GET)
public ResponseEntity<ScenarioDetailledModel> getScenarioSetup(@PathVariable("id") String id) {
	CoScenario coScenarioDb=dbExecutionService.findById(id);
	if (coScenarioDb == null) {
		return new ResponseEntity("No Scenario found for ID " + id, HttpStatus.NOT_FOUND);
	}
	ScenarioDetailledModel scenarioModel= serviceMapper.getScenarioDetailledModel(coScenarioDb);
	try {		
		Workflow workflow=workFlowBo.getWorkflow(new File(coScenarioDb.getFileName()));
		scenarioModel.setTaskList(serviceMapper.getListTaskModel(workflow,true));
	} catch (WorkflowException e) {
		return new ResponseEntity("Problem workfow :" + e, HttpStatus.NO_CONTENT);
	}

	return new ResponseEntity(scenarioModel, HttpStatus.OK);
	}*/

/*
 * SCENARIO EDIT
 *  DETAIL DU SCENARIO, AVEC TOUTES LES TACHES
 * 
 * */
  
///rest/conv/scenario/scenarioId/edit	->	Detail du senario
/*@RequestMapping(value="/conv/scenario/{id}/edit", method=RequestMethod.GET)
public ResponseEntity<ScenarioDetailledModel> getScenarioEdit(@PathVariable("id") String id) {
	CoScenario coScenarioDb=dbExecutionService.findById(id);
	if (coScenarioDb == null) {
		return new ResponseEntity("No Scenario found for ID " + id, HttpStatus.NOT_FOUND);
	}
	ScenarioDetailledModel scenarioModel= serviceMapper.getScenarioDetailledModel(coScenarioDb);
	try {
		Workflow workflow=dbExecutionService.loadWorkflow(new File(coScenarioDb.getFileName()));
		scenarioModel.setTaskList(serviceMapper.getListTaskModel(workflow,false));
	} catch (WorkflowException e) {
		return new ResponseEntity("Problem workfow :" + e, HttpStatus.NO_CONTENT);
	}

	return new ResponseEntity(scenarioModel, HttpStatus.OK);
	}*/


/*
 * 
 *  LISTE DES SCENARIOS EXECUTES PAR USERNAME
 * 
 * */

//rest/conv/execution/list?username=jlee	->	Liste des scenarios par username
/*@RequestMapping(value="/conv/execution/list", method=RequestMethod.GET,params = {"username"})
public ResponseEntity<List<ScenarioExecutionHistoryDetailedModel>> getScenarioExecutedByUser(@RequestParam("username") String username) {

FaUser user=dbUserService.findUserByLogin(username);
if(user==null)
	 return new ResponseEntity("No user found "+username, HttpStatus.NOT_FOUND);

List<ScenarioExecutionHistoryDetailedModel> scenarioList= serviceMapper.getScenarioExecutionHistoryDetailedModeList(dbExecutionService.list(user));
if(scenarioList==null || scenarioList.isEmpty())
	 return new ResponseEntity("No Scenario found for "+user.getLogin(), HttpStatus.NOT_FOUND);
return  new ResponseEntity< List<ScenarioExecutionHistoryDetailedModel>>(scenarioList, HttpStatus.OK);
}*/



/*
 * 
 * EXECUTION PROGRESS
 * 
 * */
  
///rest/conv/execution/{id}/progress	->	Detail du senario
/*@RequestMapping(value="/conv/execution/{id}/progress", method=RequestMethod.GET)
public ResponseEntity<HistoryProgressDetailedModel> getExecutionProgress(@PathVariable("id") String id) {
	CoHistoryExe coHistoryExeDb=dbExecutionService.findByIdExe(id);
	if (coHistoryExeDb == null) {
		return new ResponseEntity("No Scenario found for ID " + id, HttpStatus.NOT_FOUND);
	}
	HistoryProgressDetailedModel historyProgressDetailedModel= serviceMapper.getHistoryProgressDetailedModel(coHistoryExeDb);
	return new ResponseEntity(historyProgressDetailedModel, HttpStatus.OK);
}*/


/*
 * 
 * GET LOG PDF
 * 
 * */
  
///rest/conv/execution/{execId}/logPDF	->	téléchargement logPDF
/*@RequestMapping(value = "/conv/execution/{id}/logPDF", method = RequestMethod.GET)
public ResponseEntity<Resource> getLogPDF(@PathVariable("id") String id, 
    HttpServletResponse response) {
//	File file=new File(RESULT_PATH+"\\"+id+"\\"+LOG_FILE_PATH+LOGPDF_FILENAME);
	File file=new File(fileService.getLogPdfFilePath(id));
	if(!file.exists())
		return new ResponseEntity("No file found "+file.getAbsolutePath(), HttpStatus.NOT_FOUND);
    try {
        return ResponseEntity.ok()
                .headers(serviceMapper.getResponseHeaderFileDownload(file))
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(serviceMapper.getInputStreamResourceFileDownload(file));
    	} catch (IOException ex) {
      return new ResponseEntity("IOError writing file to output stream"+file.getAbsolutePath(), HttpStatus.NOT_FOUND);
    }

}*/


/*
 * 
 * GET OUTPUT ZIP
 * (TEMP_DIR, ZIP_TEMP_NAME + ".zip");
 * */
  
///rest/conv/execution/{execId}/result	->	téléchargement logPDF
/*@RequestMapping(value = "/conv/execution/{id}/result", method = RequestMethod.GET)
public ResponseEntity<Resource> getResult(@PathVariable("id") String id, 
    HttpServletResponse response) {
	File tempFile=null;

    try {
    	 tempFile=fileService.getResultZip(id);

        return ResponseEntity.ok()
                .headers(serviceMapper.getResponseHeaderFileDownload(tempFile))
                .contentLength(tempFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(serviceMapper.getInputStreamResourceFileDownload(tempFile));
    } catch (IOException ex) {
//      log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
//      throw new RuntimeException("IOError writing file to output stream");
      return new ResponseEntity("IOError writing file to output stream "+ id, HttpStatus.NOT_FOUND);
    }

}*/



/*
 * 
 * EXECUTION PROGRESS LOG
 * 
 * */
  
///rest/conv/execution/{id}/log->	Log temps réel d'execution
/*@RequestMapping(value = "/conv/execution/{id}/log", method = RequestMethod.GET)
public ResponseEntity<List<TaskLogModel>> getExecutionLog(@PathVariable("id") String id) {
	List<TaskLogModel> toReturn;
	CoHistoryExe coHistoryExeDb=dbExecutionService.findByIdExe(id);
	if (coHistoryExeDb == null) {
		return new ResponseEntity("No Scenario found for ID " + id, HttpStatus.NOT_FOUND);
	}
	CoScenario coScenarioDb=dbExecutionService.findById(coHistoryExeDb.getScenarioId());
	if (coScenarioDb == null) {
		return new ResponseEntity("No Scenario found for ID " + id, HttpStatus.NOT_FOUND);
	}
	try {
		Workflow workflow=dbExecutionService.loadWorkflow(new File(coScenarioDb.getFileName()));
		toReturn=serviceMapper.getListTaskLogModel(workflow);
		return new ResponseEntity(toReturn, HttpStatus.OK);
	} catch (WorkflowException e) {
		return new ResponseEntity("Problem workfow :" + e, HttpStatus.NO_CONTENT);
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		return new ResponseEntity("Problem workfow :" + e, HttpStatus.NO_CONTENT);
	}
}*/


/*
 * ASK PARAMETERS
 *  DETAIL DES PARAMETRES DEMANDES EN COURS D'EXECUTION
 * 
 * */
  
///rest/conv/askparams	->	asked parameters
/*@RequestMapping(value="/conv/askparams", method=RequestMethod.GET)
public ResponseEntity<List<TaskParameterModel>> getAskParams() {

	List<TaskParameterModel> paramsList= serviceMapper.getAllAskParameters();
    if(paramsList==null || paramsList.isEmpty())
   	 return new ResponseEntity("No Scenario found ", HttpStatus.NOT_FOUND);
    return  new ResponseEntity<List<TaskParameterModel>>(paramsList, HttpStatus.OK);

	}*/

/*
 * WAITING ASK PARAMETERS
 *  DETAIL DES PARAMETRES DEMANDES EN COURS D'EXECUTION
 * 
 * */
  
///rest/conv/askparams	->	asked parameters
/*@RequestMapping(value="/conv/waitaskparams", method=RequestMethod.GET)
public ResponseEntity<String> getIsWaitingAskParams() {

    return  new ResponseEntity<String>(Boolean.valueOf(serviceMapper.getWaitingAskParameters()).toString(), HttpStatus.OK);

	}
*/
}
