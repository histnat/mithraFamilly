package net.mithra.familly.initdb;

import net.mithra.familly.db.dao.OpRoleDao;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.user.FaRole;
import net.mithra.familly.utils.TestDBHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("MakeRole")
public class MakeRole extends TestDBHelper{

    @Autowired
    OpRoleDao roleDao;

    public void clean() {
    	userDBHelper.dropRole();
    }

    public void make() throws DBNONullException, DBNOUniqueException {
        clean();
        makeRole("ROLE_OPENCONV_ADMIN", "Permet l'accès à la partie administration. Ce role est indispensable pour pouvoir utiliser n'importe qu'elle fonction de l'administration", "Administration");
        makeRole("ROLE_OPENCONV_USER", "Permet l'accès à l'application sysdoc.", "Administration");
        makeRole("ROLE_OPENCONV_ADMIN_USER", "Permet l'accès aux fonctions d'administration des utilisateurs.", "Administration utilisateurs");
        makeRole("ROLE_OPENCONV_ADMIN_USER_MODIF", "Permet l'accès aux fonctions de modification des utilisateurs.", "Administration utilisateurs (modif)");
        }

    private FaRole makeRole(String code, String descr, String name) throws DBNONullException, DBNOUniqueException {
    	FaRole role = new FaRole();
        role.setCode(code);
        role.addDescr("FR-fr", descr + " fr");
        role.addDescr("EN-us", descr + " en");
        role.addName("FR-fr", name + " fr");
        role.addName("EN-us", name + " en");
        userDBHelper.save(role);
        return roleDao.findByCode(code);
    }
}