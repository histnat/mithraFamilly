package net.mithra.familly.initdb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.mithra.familly.db.dao.CoHistoryExeDao;
import net.mithra.familly.db.dao.CoParameterExeDao;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.execution.CoHistoryExe;
import net.mithra.familly.db.vo.execution.CoParameterExe;
import net.mithra.familly.utils.TestDBHelper;



@Component("MakeCoParameterExe")
public class MakeCoParameterExe  extends TestDBHelper{
	
	public final static String LANGUE_FR = "FR-fr";
	public final static String LANGUE_EN = "GB-en";
	
	
	public void clean()
	{
		coParameterExeDao.drop(CoParameterExe.class);
	}
	
	public List<CoParameterExe> make(CoHistoryExe coHistoryExe) throws DBNONullException, DBNOUniqueException
	{
		List<CoParameterExe> paramList=getCoParameterExeWithSave(2,coHistoryExe);
		coHistoryExe.setParameterList(paramList);
		coHistoryExeDao.update(coHistoryExe);
		return paramList;
	}


	public List<CoParameterExe> getCoParameterExeWithSave(int nbr, CoHistoryExe coHistoryExe) throws DBNONullException, DBNOUniqueException {
		List<CoParameterExe> coParameterExeList = new ArrayList<>();
		for (int i = 0; i < nbr; i++) {
			CoParameterExe coParameterExe = getCoParameterExeWithoutSave(i);
			coParameterExe.setCoHistoryExe(coHistoryExe);
			coParameterExe.setId("PARAM"+i+"_"+coHistoryExe.getId());
			CoParameterExe c1 = coParameterExeDao.getOrSave(coParameterExe);
			coParameterExeList.add(c1);

		}
		return coParameterExeList;
	}
	
}
