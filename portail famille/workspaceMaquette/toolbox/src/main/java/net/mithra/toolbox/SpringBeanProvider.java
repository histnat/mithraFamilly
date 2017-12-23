/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mithra.toolbox;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 *
 * @author F.REBECHE
 */
public class SpringBeanProvider implements ApplicationContextAware {
 
	private static ApplicationContext ap = null;
    private static Environment env = null;

    static {
        if (ap == null || env == null) {
            chargeContext();
        }

    }

    private SpringBeanProvider() {
        if (ap == null || env == null) {
            chargeContext();
        }
    }
    
    public static String getProperty(String propertyName) {
        if (env == null) {
            chargeContext();
        }
        return env.getProperty(propertyName);
    }

    public static Object getBean(String sBeanName) {
        if (ap == null) {
            chargeContext();
        }
        return ap.getBean(sBeanName);
    }
    
    public static Object getBean(Class<T> sBean) {
        if (ap == null) {
            chargeContext();
        }
        return ap.getBean(sBean);
    }

    private static void chargeContext() {
        if (ap == null) {
        	try{
            ap = new ClassPathXmlApplicationContext("spring.xml");
            }
            catch (Exception e) {
				// TODO: handle exception
            	ap=null;
            	e.printStackTrace();
			}
        }
        if (env == null && ap!=null) {
            env = ap.getEnvironment();
        }
    }

    /**
     * renvoie l'applicationContext de l'application
     *
     * @return
     */
    public static ApplicationContext getAP() {
        if (ap == null) {
            chargeContext();
        }
        return ap;
    }

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		SpringBeanProvider.ap=arg0;
		
	}
	
	public static void setApplicationContext2(ApplicationContext arg0) throws BeansException {
		SpringBeanProvider.ap=arg0;
		
	}
}
