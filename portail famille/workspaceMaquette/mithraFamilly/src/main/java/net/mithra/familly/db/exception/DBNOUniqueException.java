/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mithra.familly.db.exception;

/**
 *
 * @author FRebeche
 */
public class DBNOUniqueException extends Exception {
    
  public DBNOUniqueException() { super(); }
  
  public DBNOUniqueException(String message) { super(message); }
  
  public DBNOUniqueException(String message, Throwable cause) { super(message, cause); }
  
  public DBNOUniqueException(Throwable cause) { super(cause); }
    
    
}
