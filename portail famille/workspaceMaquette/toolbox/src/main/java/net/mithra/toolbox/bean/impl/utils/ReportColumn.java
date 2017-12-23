/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.mithra.toolbox.bean.impl.utils;


import net.mithra.toolbox.bean.impl.ExcelServiceImpl.FormatType;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFFont;

/**
 *
 * @author SHordoir
 */
public class ReportColumn {
    
    private String m_method;
    private String m_header;
    private FormatType m_type;
    private HSSFFont m_font;
    private Short m_color;
    
    
    public ReportColumn(String method, String header, FormatType type, HSSFFont font, Short color) {
        this.m_method = method;
        this.m_header = header;
        this.m_type = type;
        this.m_font = font;
        this.m_color = color;
    }
    public ReportColumn(String method, String header, FormatType type,
            HSSFFont font) {
        this(method, header, type, font, null);
    }
    public ReportColumn(String method, String header, FormatType type,
            Short color) {
        this(method, header, type, null, color);
    }
 
    public ReportColumn(String method, String header, FormatType type) {
        this(method, header, type, null, null);
    }

    public ReportColumn() {
    }
    
    
    
    public String getMethod() {
        return m_method;
    }
 
    public void setMethod(String method) {
        this.m_method = method;
    }
 
    public String getHeader() {
        return m_header;
    }
 
    public void setHeader(String header) {
        this.m_header = header;
    }
 
    public FormatType getType() {
        return m_type;
    }
 
    public void setType(FormatType type) {
        this.m_type = type;
    }
 
    public HSSFFont getFont() {
        return m_font;
    }
 
    public void setFont(HSSFFont m_font) {
        this.m_font = m_font;
    }
 
    public Short getColor() {
        return m_color;
    }
 
    public void setColor(Short m_color) {
        this.m_color = m_color;
    }
	@Override
	public String toString() {
		return "ReportColumn [m_method=" + m_method + ", m_header=" + m_header
				+ ", m_type=" + m_type + ", m_font=" + m_font + ", m_color="
				+ m_color + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_color == null) ? 0 : m_color.hashCode());
		result = prime * result + ((m_font == null) ? 0 : m_font.hashCode());
		result = prime * result
				+ ((m_header == null) ? 0 : m_header.hashCode());
		result = prime * result
				+ ((m_method == null) ? 0 : m_method.hashCode());
		result = prime * result + ((m_type == null) ? 0 : m_type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportColumn other = (ReportColumn) obj;
		if (m_color == null) {
			if (other.m_color != null)
				return false;
		} else if (!m_color.equals(other.m_color))
			return false;
		if (m_font == null) {
			if (other.m_font != null)
				return false;
		} else if (!m_font.equals(other.m_font))
			return false;
		if (m_header == null) {
			if (other.m_header != null)
				return false;
		} else if (!m_header.equals(other.m_header))
			return false;
		if (m_method == null) {
			if (other.m_method != null)
				return false;
		} else if (!m_method.equals(other.m_method))
			return false;
		if (m_type != other.m_type)
			return false;
		return true;
	}
    

	
    
    
    
}
