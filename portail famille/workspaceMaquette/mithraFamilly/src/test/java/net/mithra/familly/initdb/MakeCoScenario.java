package net.mithra.familly.initdb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.mithra.familly.db.dao.CoScenarioDao;
import net.mithra.familly.db.dao.OpUserDao;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.execution.CoScenario;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.utils.TestDBHelper;



@Component("MakeCoScenario")
public class MakeCoScenario extends TestDBHelper{
	
	public final static String LANGUE_FR = "FR-fr";
	public final static String LANGUE_EN = "GB-en";
	
	public final static String PREFIXE_ID="SCEN";
	
	public void clean()
	{
		coScenarioDao.drop(CoScenario.class);
	}
	
	public List<CoScenario> make(FaUser user) throws DBNONullException, DBNOUniqueException
	{
		 List<CoScenario> listSce=getCoScenarioWithSave(3,user);
		 user.setScenarioList(listSce);
		 userDBHelper.update(user);
		 return listSce;
	}

	
	public List<CoScenario> getCoScenarioWithSave(int nbr,FaUser user) throws DBNONullException, DBNOUniqueException {
		List<CoScenario> coScenarioList = new ArrayList<>();
		for (int i = 0; i < nbr; i++) {
			CoScenario coScenario = getCoScenarioWithoutSave(i);
//			coScenario.setOpUser(user);
			coScenario.setUserId(user.getId());
			coScenario.setId("SCEN"+i+"_"+user.getId());
			CoScenario c1 = coScenarioDao.getOrSave(coScenario);
			coScenarioList.add(c1);

		}
		return coScenarioList;
	}
	
	public List<CoScenario> getCoScenarioListWithSave(int nbr) throws DBNONullException, DBNOUniqueException {
		List<CoScenario> coScenarioList = new ArrayList<>();
		for (int i = 0; i < nbr; i++) {
			CoScenario coScenario = getCoScenarioWithoutSave(i);
//			coScenario.setOpUser(user);
			coScenario.setUserId("userId"+i);
			coScenario.setId(PREFIXE_ID+i);
			CoScenario c1 = coScenarioDao.getOrSave(coScenario);
			coScenarioList.add(c1);

		}
		return coScenarioList;
	}
	
	public void updateScenario(CoScenario coScenario){
		coScenarioDao.update(coScenario);
	}
	
	public CoScenario getOneCoScenarioWithoutSave(String scenarioId,String username){
		CoScenario coScenario = getCoScenarioWithoutSave(1);
//		coScenario.setOpUser(user);
		coScenario.setUserId(username);
		coScenario.setId(scenarioId);
		return coScenario;
	}
	
	public CoScenario getOneCoScenarioWithSave(String scenarioId,String username) throws DBNONullException, DBNOUniqueException{
		return coScenarioDao.getOrSave(getOneCoScenarioWithoutSave(scenarioId,username));
	}
}
