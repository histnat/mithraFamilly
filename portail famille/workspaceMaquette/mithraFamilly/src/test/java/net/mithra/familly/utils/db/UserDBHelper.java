package net.mithra.familly.utils.db;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.mithra.familly.db.dao.OpGroupDao;
import net.mithra.familly.db.dao.OpRoleDao;
import net.mithra.familly.db.dao.OpUserDao;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.user.FaGroup;
import net.mithra.familly.db.vo.user.FaRole;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.utils.TestHelper;


@Service(value="UserDBHelper")
public class UserDBHelper extends TestHelper {
	

	@Autowired
	protected OpUserDao opUserDao;
	
    @Autowired
    protected OpRoleDao opRoleDao;

    @Autowired
    protected OpGroupDao opGroupDao;
	
	public void deleteAll() {
		opUserDao.deleteAll(FaUser.class);
		opRoleDao.deleteAll(FaRole.class);
		opGroupDao.deleteAll(FaGroup.class);
	}
	
	

	/**************************************************************
	 * OpUser
	 **************************************************************/

	public FaUser getOpUserWithoutSave() {
		return getOpUserWithoutSave(1);
	}

	public FaUser getOpUserWithoutSave(int nbr) {
		FaUser opUser = new FaUser();
		opUser.setEmail("test"+nbr+"@test.fr");
		opUser.setLogin("test"+nbr);
		opUser.setLastLogin(Calendar.getInstance().getTime());
		opUser.setName("test"+nbr);
		opUser.setNbrFail(0);
		opUser.setPassword("kiki");
		return opUser;
	}

	public List<FaUser> getOpUserWithSave(int nbr) throws DBNONullException, DBNOUniqueException {
		List<FaUser> opUserList = new ArrayList<>();
		for (int i = 0; i < nbr; i++) {
			FaUser opUser = getOpUserWithoutSave(i);
			FaUser c1 = dbUserService.getOrSave(opUser);
			opUserList.add(c1);

		}
		return opUserList;
	}

	public FaUser getOpUserWithSave() throws DBNONullException, DBNOUniqueException {
		FaUser opUser = getOpUserWithoutSave();
		dbUserService.getOrSave(opUser);
		return opUser;
	}
	
	public void update(FaUser user){
		 opUserDao.update(user);
	}
	
	public void save(FaUser user)throws DBNONullException, DBNOUniqueException {
		 opUserDao.save(user);
	}
	
	public void dropUser(){
		 opUserDao.drop(FaUser.class);
	}
	
	
	/**************************************************************
	 * OpGroup
	 **************************************************************/

	public FaGroup getOpGroupWithoutSave() {
		return getOpGroupWithoutSave(1);
	}

	public FaGroup getOpGroupWithoutSave(int nbr) {
		FaGroup opGroup = new FaGroup();
		opGroup.addName(IDLANGUE_FR,  MessageFormat.format("{0} fr","test"+nbr));
		opGroup.addDescr(IDLANGUE_FR, MessageFormat.format("{0} fr","descr"+nbr));
		opGroup.addName(IDLANGUE_EN, MessageFormat.format("{0} en","test"+nbr));
		opGroup.addDescr(IDLANGUE_EN,  MessageFormat.format("{0} en","descr"+nbr));
		opGroup.setCode("code"+nbr);
		return opGroup;
	}

	public List<FaGroup> getOpGroupWithSave(int nbr) throws DBNONullException, DBNOUniqueException {
		List<FaGroup> opGroupList = new ArrayList<>();
		for (int i = 0; i < nbr; i++) {
			FaGroup opGroup = getOpGroupWithoutSave(i);
			FaGroup c1 = dbUserService.getOrSave(opGroup);
			opGroupList.add(c1);

		}
		return opGroupList;
	}

	public FaGroup getOpGroupWithSave() throws DBNONullException, DBNOUniqueException {
		FaGroup opGroup = getOpGroupWithoutSave();
		dbUserService.getOrSave(opGroup);
		return opGroup;
	}
	
	public void save(FaGroup group)throws DBNONullException, DBNOUniqueException {
		 opGroupDao.save(group);
	}
	
	public void dropGroup(){
		 opGroupDao.drop(FaGroup.class);
	}


	/**************************************************************
	 * OpRole
	 **************************************************************/

	public FaRole getOpRoleWithoutSave() {
		return getOpRoleWithoutSave(1);
	}

	public FaRole getOpRoleWithoutSave(int nbr) {
		FaRole opRole = new FaRole();
		opRole.addName(IDLANGUE_FR,  MessageFormat.format("{0} fr","test"+nbr));
		opRole.addDescr(IDLANGUE_FR, MessageFormat.format("{0} fr","descr"+nbr));
		opRole.addName(IDLANGUE_EN, MessageFormat.format("{0} en","test"+nbr));
		opRole.addDescr(IDLANGUE_EN,  MessageFormat.format("{0} en","descr"+nbr));
		opRole.setCode("code"+nbr);
		return opRole;
	}

	public List<FaRole> getOpRoleWithSave(int nbr) throws DBNONullException, DBNOUniqueException {
		List<FaRole> opRoleList = new ArrayList<>();
		for (int i = 0; i < nbr; i++) {
			FaRole opRole = getOpRoleWithoutSave(i);
			FaRole c1 = dbUserService.getOrSave(opRole);
			opRoleList.add(c1);

		}
		return opRoleList;
	}

	public FaRole getOpRoleWithSave() throws DBNONullException, DBNOUniqueException {
		FaRole opRole = getOpRoleWithoutSave();
		dbUserService.getOrSave(opRole);
		return opRole;
	}
	
	public void save(FaRole opRole)throws DBNONullException, DBNOUniqueException {
		opRoleDao.save(opRole);
	}
	
	public void dropRole(){
		 opRoleDao.drop(FaRole.class);
	}

}
