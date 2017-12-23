package net.mithra.familly.initdb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.execution.CoHistoryExe;
import net.mithra.familly.db.vo.execution.CoScenario;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.utils.TestDBHelper;



@Component("MakeCoHistoryExe")
public class MakeCoHistoryExe extends TestDBHelper{
	
	public final static String LANGUE_FR = "FR-fr";
	public final static String LANGUE_EN = "GB-en";
	
	public final static String PREFIXE_ID="HIST";

	public void clean()
	{
		coHistoryExeDao.drop(CoHistoryExe.class);
	}
	
	public List<CoHistoryExe> make(FaUser user, CoScenario scenario,String idExe) throws DBNONullException, DBNOUniqueException
	{
		 List<CoHistoryExe> histList=getCoHistoryExeListWithSave(5,user,scenario,idExe);
		 user.setHistoryList(histList);
		 userDBHelper.update(user);
		 scenario.setHistoryList(histList);
		 coScenarioDao.update(scenario);
		 return histList;
	}
		

	public List<CoHistoryExe> getCoHistoryExeListWithSave(int nbr,FaUser user, CoScenario scenario,String idExe) throws DBNONullException, DBNOUniqueException {
		List<CoHistoryExe> coHistoryExeList = new ArrayList<>();
		for (int i = 0; i < nbr; i++) {
			CoHistoryExe coHistoryExe = getCoHistoryExeWithoutSave(i);
//			coHistoryExe.setOpUser(user);
			coHistoryExe.setUserId(user.getId());
//			coHistoryExe.setCoScenario(scenario);
			coHistoryExe.setScenarioId(scenario.getId());
			coHistoryExe.setId(PREFIXE_ID+i+"_"+scenario.getId()+"_"+user.getId()+"_"+idExe);
//			coHistoryExe.setExecutionId(idExe);
			CoHistoryExe c1 = coHistoryExeDao.getOrSave(coHistoryExe);
			coHistoryExeList.add(c1);

		}
		return coHistoryExeList;
	}
	

	
	public List<CoHistoryExe> getCoHistoryExeListWithSave(int nbr) throws DBNONullException, DBNOUniqueException {
		List<CoHistoryExe> coHistoryExeList = new ArrayList<>();
		for (int i = 0; i < nbr; i++) {
			CoHistoryExe coHistoryExe = getCoHistoryExeWithoutSave(i);
			coHistoryExe.setUserId("userId"+i);
			coHistoryExe.setScenarioId("SCEN"+i);
			coHistoryExe.setId(PREFIXE_ID+i);
//			coHistoryExe.setExecutionId("exeId"+i);
			CoHistoryExe c1 = coHistoryExeDao.getOrSave(coHistoryExe);
			coHistoryExeList.add(c1);

		}
		return coHistoryExeList;
	}
	
	public void updateHistory(CoHistoryExe coHistoryExe){
		coHistoryExeDao.update(coHistoryExe);
	}
}
