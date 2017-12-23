package net.mithra.familly.db.bo.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.mithra.familly.db.bo.DBUserService;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.utils.TestDBHelper;

public class DBUserServiceTest extends TestDBHelper{
	
	@Autowired
	DBUserService dbUserService;
	
	@AfterMethod
    public void setAfterMethod(Method method) throws Exception {
    	deleteAll();
    }

    @BeforeMethod
    public void setUpMethod(Method method) throws Exception {
    	deleteAll();
    }
	
  @Test
  public void findUserById() {
      System.out.println("findUserById");
      FaUser user =null;
      try{
          user = getOpUserWithSave();
      }
      catch(Exception e){
          fail();
      }
      FaUser result = dbUserService.findUserById(user.getId());
      assertNotNull(result);
      assertEquals(result.getName(), user.getName());
      assertEquals(result.getEmail(), user.getEmail());
      assertEquals(result.getLastLogin(), user.getLastLogin());
      assertEquals(result.getLogin(), user.getLogin());
  }

  @Test
  public void findUserByLogin() {
      System.out.println("getUserSysdocByLogin");
      FaUser u =null;
      try{
          u = getOpUserWithSave();
      }
      catch(Exception e){
          fail();
      }
      FaUser result=dbUserService.findUserByLogin(u.getLogin());
      assertNotNull(result);
      assertEquals(result.getName(), u.getName());
      assertEquals(result.getEmail(), u.getEmail());
      assertEquals(result.getLastLogin(), u.getLastLogin());
      assertEquals(result.getLogin(), u.getLogin());
  }
  
  
}
