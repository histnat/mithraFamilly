package net.mithra.familly.initdb;


import net.mithra.familly.db.dao.OpGroupDao;
import net.mithra.familly.db.dao.OpRoleDao;
import net.mithra.familly.db.dao.OpUserDao;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.user.FaGroup;
import net.mithra.familly.db.vo.user.FaRole;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.utils.TestDBHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component("MakeUser")
public class MakeUser extends TestDBHelper{



    @Autowired
    OpRoleDao roleDao;

    public void clean() {
    	userDBHelper.dropUser();
    	userDBHelper.dropGroup();
    }

    public FaUser make(String name) throws DBNONullException, DBNOUniqueException {
        List<FaRole> roles = roleDao.findAll(FaRole.class);
        FaGroup group = makeGroup("GROUP_ADMIN_"+name, "Group administrateur", "Administrateur", roles);
        List<FaGroup> groups = new ArrayList<>();
        groups.add(group);
        return makeUser(name,name,"pwd_"+name,name+"@sonovisiongroup.com",new Long(12554),new Date(), 3,groups);
    }

    public FaUser makeUser(String name, String login, String password, String mail,Long avatar, Date lastLogin, int nbrFail,
                         List<FaGroup> groupList) throws DBNONullException, DBNOUniqueException {
    	FaUser user = new FaUser();
    	user.setId(name);
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(mail);
        user.setAvatar(avatar);
        user.setLastLogin(lastLogin);
        user.setNbrFail(nbrFail);
        user.setBlocked(true);
        user.setGroupList(groupList);
        userDBHelper.save(user);
        return user;
    }

    public FaGroup makeGroup(String code, String descr, String name, List<FaRole> roleList) throws DBNONullException, DBNOUniqueException {
    	FaGroup group = new FaGroup();
        group.setCode(code);
        group.addDescr("FR-fr", descr + " fr");
        group.addDescr("EN-us", descr + " en");
        group.addName("FR-fr", name + " fr");
        group.addName("EN-us", name + " en");
        group.setRoleList(roleList);
        userDBHelper.save(group);
        return group;
    }
}
