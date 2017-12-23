/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mithra.familly.db.exception;

/**
 *
 * @author FRebeche
 */
public class DBNONullException extends Exception {
    
  public DBNONullException() { super(); }
  
  public DBNONullException(String message) { super(message); }
  
  public DBNONullException(String message, Throwable cause) { super(message, cause); }
  
  public DBNONullException(Throwable cause) { super(cause); }
    
    
}
