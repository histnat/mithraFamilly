package net.mithra.familly.ws.manager;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.mithra.familly.utils.TestDBHelper;
import net.mithra.familly.wf.model.ParameterId;
import net.mithra.familly.wf.model.ParameterTypes;
import net.mithra.familly.ws.controller.model.TaskParameterModel;

public class ParametersManagerTest extends TestDBHelper{
	
	@Autowired
	protected ParametersManager parametersManager;
	

    @BeforeMethod
    public void init() {
    	parametersManager.clean();
    }

  @Test(dependsOnMethods ={"updateParams"})
  public void addOrUpdate() {
	  List<TaskParameterModel> listTaskParameterModel=getListTaskParameterModel();
	  parametersManager.update(listTaskParameterModel);
	  assertEquals(parametersManager.getAllParams().size(),5);
	  parametersManager.addOrUpdate(getTaskParameterModel(6));
	  assertEquals(parametersManager.getAllParams().size(),6);
	  TaskParameterModel taskParameterModel3=listTaskParameterModel.get(3);
	  taskParameterModel3.setLabel("coco");
	  parametersManager.addOrUpdate(taskParameterModel3);
	  assertEquals(parametersManager.getAllParams().size(),6);
	  assertEquals(parametersManager.getParam(taskParameterModel3.getId()).getLabel(),"coco");
	  
  }


  @Test
  public void getAllParams() {
	  assertEquals(parametersManager.getAllParams().size(),0);
	  List<TaskParameterModel> listTaskParameterModel=getListTaskParameterModel();
	  parametersManager.update(listTaskParameterModel);
	  assertEquals(parametersManager.getAllParams().size(),5);
  }

  @Test(dependsOnMethods ={"updateParams"})
  public void getParam() {
	  List<TaskParameterModel> listTaskParameterModel=getListTaskParameterModel();
	  parametersManager.update(listTaskParameterModel);
	  assertEquals(parametersManager.getParam(listTaskParameterModel.get(0).getId()),listTaskParameterModel.get(0));
  }

  @Test(dependsOnMethods ={"getAllParams"})
  public void updateParams() {
	  List<TaskParameterModel> listTaskParameterModel=getListTaskParameterModel();
	  parametersManager.update(listTaskParameterModel);
	  assertEquals(parametersManager.getAllParams().size(),5);

  }
  
  private TaskParameterModel getTaskParameterModel(int refId){
	  TaskParameterModel taskParameterModel=new TaskParameterModel();
	  ParameterId parameterId=new ParameterId();
	  parameterId.setParameterName("parameterName"+refId);
	  parameterId.setTaskId("task"+refId);
	  taskParameterModel.setId(parameterId);
	  taskParameterModel.setCode("code"+refId);
	  taskParameterModel.setDescr("descr"+refId);
	  taskParameterModel.setLabel("label"+refId);
	  taskParameterModel.setValue("value"+refId);
	  taskParameterModel.setType(ParameterTypes.TXT);
	  return taskParameterModel;
  }
  
  private List<TaskParameterModel> getListTaskParameterModel(){
	  List<TaskParameterModel> listTaskParameterModel=new ArrayList<>();
	  for(int i=0;i<5;i++)
		  listTaskParameterModel.add(getTaskParameterModel(i));
	  return listTaskParameterModel;
		 
  }
  
}
