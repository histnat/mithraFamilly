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
import net.mithra.familly.db.vo.Conversion;
import net.mithra.familly.db.vo.execution.CoScenario;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.utils.TestDBHelper;



@Component("MakeConversion")
public class MakeConversion extends TestDBHelper{
	
	public final static String LANGUE_FR = "FR-fr";
	public final static String LANGUE_EN = "GB-en";
	
	
	public void clean()
	{
		conversionDao.drop(Conversion.class);

	}
	
	public List<Conversion> make() throws DBNONullException, DBNOUniqueException
	{
		 List<Conversion> listConv=getConversionWithSave(2);
//		 user.setScenarioList(listSce);
//		 userDBHelper.update(user);
		 return listConv;
	}

//	
//	public List<CoScenario> getConversionWithSave(int nbr,OpUser user) throws DBNONullException, DBNOUniqueException {
//		List<CoScenario> coScenarioList = new ArrayList<>();
//		for (int i = 0; i < nbr; i++) {
//			CoScenario coScenario = getCoScenarioWithoutSave(i);
////			coScenario.setOpUser(user);
//			coScenario.setUserId(user.getId());
//			coScenario.setId("SCEN"+i+"_"+user.getId());
//			CoScenario c1 = coScenarioDao.getOrSave(coScenario);
//			coScenarioList.add(c1);
//
//		}
//		return coScenarioList;
//	}
	
}
