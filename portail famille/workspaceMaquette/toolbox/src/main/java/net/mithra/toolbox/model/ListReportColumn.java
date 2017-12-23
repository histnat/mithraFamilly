/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mithra.toolbox.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author dragos.buzdugan@sonovisiongroup.com
 * 03.11.2014
 */
public abstract class ListReportColumn {
	
	public static final Set<String> labels = new HashSet<String>();
	
	abstract public Set<String> getColumnNames();
	abstract public Map<String, String> getValues();
}
