/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.mithra.toolbox.bean.impl;


import net.mithra.toolbox.LogMes;
import net.mithra.toolbox.bean.ExcelService;
import net.mithra.toolbox.bean.impl.utils.ReportColumn;
import net.mithra.toolbox.model.ListReportColumn;
import net.mithra.toolbox.tools.annotation.ExcelCell;
import net.mithra.toolbox.tools.annotation.ExcelObject;
import net.mithra.toolbox.tools.annotation.Info;
import net.mithra.toolbox.tools.annotation.InfoList;

import net.sf.juffrou.reflect.JuffrouBeanWrapper;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;

import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author SHordoir
 */
@Service("ExcelService")
public class ExcelServiceImpl implements ExcelService {

    private HSSFWorkbook workbook;
    private HSSFFont boldFont;
    private HSSFDataFormat format;

    public ExcelServiceImpl() {
        workbook = new HSSFWorkbook();
        boldFont = workbook.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        format = workbook.createDataFormat();
    }


    @Override
    public void writeReportToExcel(HashMap datas, String path) {

        OutputStream output = null;
        try {
            File file = new File(path);
            output = new FileOutputStream(file);
            export(datas, output);
        } catch (FileNotFoundException ex) {
            LogMes.log(ExcelServiceImpl.class, LogMes.ERROR, " probleme writeReportToExcel :" + ex);
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
                LogMes.log(ExcelServiceImpl.class, LogMes.ERROR, " probleme writeReportToExcel :" + ex);
            }
        }
    }

    public void initBook() {
        workbook = new HSSFWorkbook();
        boldFont = workbook.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        format = workbook.createDataFormat();
    }

    @Override
    public void export(HashMap datas, OutputStream output) {
        initBook();
        try {

            Iterator it = datas.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();

                Class classSheetName = (Class) entry.getKey();

                List data = (List) entry.getValue();

                ReportColumn[] reportColumns = setupFieldsForClass(classSheetName);

                // Create a worksheet with our  data and report columns
                addSheet(data, reportColumns, classSheetName.getSimpleName());
            }

            // Write the report to the output stream
            write(output);

            // Finally, save the report
            output.close();

        } catch (Exception ex) {
            LogMes.log(ExcelServiceImpl.class, LogMes.ERROR, " probleme export :" + ex);
            ex.printStackTrace();
        }

    }

    private ReportColumn[] setupFieldsForClass(Class<?> clazz) throws Exception {

        Field[] fields = clazz.getDeclaredFields();

        ReportColumn[] rcs = new ReportColumn[clazz.getDeclaredFields().length];


        for (int i = 0, j=i; i < fields.length; i++, j++) {
            fields[i].setAccessible(true); // suppress Java access checking

            ///////////////////////////////////////
            // THIS IS WHERE REFLECTION IS USED 
            /////////////////////////////////////////

            rcs[j] = new ReportColumn();
            rcs[j].setMethod(fields[i].getName());
            // iterates all the annotations available in the filed
            Annotation[] annotations = fields[i].getDeclaredAnnotations();

            Info info = fields[i].getAnnotation(Info.class);
            InfoList infoList = fields[i].getAnnotation(InfoList.class);
            
            if (info != null && info instanceof Info ) { // cas where there is no annotation for the field
                rcs[j].setHeader(((Info)info).header());
            } else if(fields[i].getType() == Map.class && infoList!= null && infoList instanceof InfoList){
            	//read static labels;
            	if(clazz.getSuperclass() == ListReportColumn.class ){
            		ListReportColumn listReportColumnS = (ListReportColumn) Class.forName(clazz.getName()).newInstance();
            		Set<String> columns = listReportColumnS.getColumnNames();
            		rcs = Arrays.copyOf(rcs, rcs.length + columns.size()-1);
            		Iterator<String> it = columns.iterator();
            		
            		while(it.hasNext()){
            			if(rcs[j] == null)
            				rcs[j] = new ReportColumn();
            			rcs[j].setHeader(it.next()); 
            			rcs[j].setType(FormatType.MAP); 
            			rcs[j].setMethod(fields[i].getName());
            			j++;
            		}          
            		--j;
            	}else{
            		rcs[j].setHeader(fields[i].getName());
            		rcs[j].setType(FormatType.TEXT);
            	}
            }else {
                rcs[j].setHeader(fields[i].getName());
            }


            if (fields[i].getType() == String.class) {
                rcs[j].setType(FormatType.TEXT);
            } else if (fields[i].getType() == Date.class) {
                rcs[j].setType(FormatType.DATE);
            } else if (fields[i].getType() == Double.class) {
                rcs[i].setType(FormatType.DOUBLE);
            } else if (fields[i].getType() == Float.class) {
                rcs[j].setType(FormatType.FLOAT);
            } else if (fields[i].getType() == Integer.class) {
                rcs[j].setType(FormatType.INTEGER);
            } else if (fields[i].getType() == Boolean.class) {
                rcs[j].setType(FormatType.BOOLEAN);
            }
            
            fields[i].setAccessible(false); // reactive Java access checking
        }
        return rcs;
    }


    public void addSheet(List<?> data, ReportColumn[] columns, String sheetName) {

        HSSFSheet sheet = workbook.createSheet(sheetName);
        int numCols = columns.length;
        int currentRow = 0;
        HSSFRow row;


        // Create the report header at row 0
        row = sheet.createRow(currentRow);
        // Loop over all the column beans and populate the report headers
        for (int i = 0; i < numCols; i++) {
            // Get the header text from the bean and write it to the cell
            Short headerColor = IndexedColors.AQUA.getIndex();
            try {
                writeCell(row, i, columns[i].getHeader(), FormatType.TEXT,
                        headerColor, this.boldFont, true);
            } catch (Exception e) {
                LogMes.log(ExcelServiceImpl.class, LogMes.ERROR, " probleme addSheet header :" + columns[i].getHeader() + " exception :" + e);
            }

        }

        currentRow++; // increment the spreadsheet row before we step into
        // the data

        // Write report rows
        for (int i = 0; i < data.size(); i++) {
            // create a row in the spreadsheet
            row = sheet.createRow(currentRow++);
            // get the bean for the current row
            Object bean = data.get(i);
            // For each column object, create a column on the current row
            Object value = null;
            for (int y = 0; y < numCols; y++) {
                try {
                	if(columns[y].getType() != FormatType.MAP){
                		value = PropertyUtils.getProperty(bean, columns[y].getMethod());
                		writeCell(row, y, value, columns[y].getType(),
                                columns[y].getColor(), columns[y].getFont(), false);
                	} else{
                		@SuppressWarnings("unchecked")
						Map<String, Object> map = (Map<String, Object>) PropertyUtils.getProperty(bean, columns[y].getMethod());
                		value = map.get(columns[y].getHeader());
                		writeCell(row, y, value, FormatType.TEXT,
                                columns[y].getColor(), columns[y].getFont(), false);
                	}

                    
                } catch (Exception e) {
                    LogMes.log(ExcelServiceImpl.class, LogMes.ERROR, " probleme addSheet rows :value :" + value + " columns:" + columns[y] + " exception :" + e);
                }
            }
        }

        // Autosize columns
        for (int i = 0; i < numCols; i++) {
            sheet.autoSizeColumn((short) i);
        }


    }


    private void writeCell(HSSFRow row, int col, Object value,
                           FormatType formatType, Short bgColor, HSSFFont font, boolean header)
            throws Exception {

        HSSFCell cell = HSSFCellUtil.createCell(row, col, null);

        if (value == null) {
            return;
        }

        HSSFCellStyle style = null;

        if (font != null) {
            style = workbook.createCellStyle();
            style.setFont(font);
            cell.setCellStyle(style);
        }


        switch (formatType) {
            case TEXT:
                cell.setCellValue(value.toString());
                break;
            case INTEGER:
                cell.setCellValue(((Number) value).intValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("#,##0")));
                break;
            case FLOAT:
                cell.setCellValue(((Number) value).doubleValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("#,##0.00")));
                break;
            case DOUBLE:
                cell.setCellValue(((Number) value).doubleValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("#,##0.00")));
                break;
            case DATE:
                cell.setCellValue((Date) value);
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("m/d/yy")));
                break;
            case MONEY:
                cell.setCellValue(((Number) value).intValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT,
                        format.getFormat("$#,##0.00;$#,##0.00"));
                break;
            case BOOLEAN:

                cell.setCellValue((value.toString().equals("true")) ? "VRAI" : "FAUX");
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT,
                        format.getFormat("VRAI"));
                break;
            case PERCENTAGE:
                cell.setCellValue(((Number) value).doubleValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat("0.00%"));
        }


        if (bgColor != null) {
            HSSFCellUtil.setCellStyleProperty(cell, workbook,
                    CellUtil.FILL_FOREGROUND_COLOR, bgColor);
            HSSFCellUtil.setCellStyleProperty(cell, workbook,
                    CellUtil.FILL_PATTERN, HSSFCellStyle.SOLID_FOREGROUND);
        }


    }


    public enum FormatType {
        TEXT, INTEGER, FLOAT, DATE, MONEY, PERCENTAGE, DOUBLE, BOOLEAN, MAP
    }

    public HSSFFont boldFont() {
        return boldFont;
    }

    public void write(OutputStream outputStream) throws Exception {
        workbook.write(outputStream);
    }

    protected HSSFSheet getSheet(String sheetName) {

        return workbook.getSheet(sheetName);
    }

    public Object convertRowToObject(Class<?> cl, Row row)
            throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, NoSuchFieldException, SecurityException,
            NoSuchMethodException, InvocationTargetException {
        Object obj = cl.newInstance();
        JuffrouBeanWrapper juffrouBeanWrapper = new JuffrouBeanWrapper(
                obj.getClass());
        Map<Integer, Map<String, Enum<FormatType>>> fields = new HashMap<Integer, Map<String, Enum<FormatType>>>();

        if (cl.isAnnotationPresent(ExcelObject.class)) {
            for (Field f : obj.getClass().getDeclaredFields()) {
                if (f.isAnnotationPresent(ExcelCell.class)) {
                    if (Modifier.isPrivate(f.getModifiers())) {
                        f.setAccessible(true);
                    }
                    Map<String, Enum<FormatType>> annotation = new HashMap<String, Enum<FormatType>>();
                    annotation.put(f.getName(), f
                            .getAnnotation(ExcelCell.class).type());
                    fields.put(f.getAnnotation(ExcelCell.class).position(),
                            annotation);
                }
            }
        } else {
            return null;
        }

        Iterator<Cell> cellIt = row.cellIterator();
        while (cellIt.hasNext()) {
            Cell cell = cellIt.next();
            Map<String, Enum<FormatType>> map = fields.get(cell
                    .getColumnIndex());

            if (map == null)
                continue;
            Entry<String, Enum<FormatType>> next = map.entrySet().iterator()
                    .next();
            Field excelField = obj.getClass().getDeclaredField(
                    map.keySet().iterator().next().toString());
            excelField.setAccessible(true);

            switch ((FormatType) next.getValue()) {
                case BOOLEAN:
                    juffrouBeanWrapper.setValue(excelField.getName(),
                            cell.getBooleanCellValue());
                    break;
                case DATE:
                    juffrouBeanWrapper.setValue(excelField.getName(),
                            cell.getDateCellValue());
                    break;
                case DOUBLE:
                    juffrouBeanWrapper.setValue(excelField.getName(), cell.getNumericCellValue());
                    break;
                case FLOAT:
                    juffrouBeanWrapper.setValue(excelField.getName(), cell.getNumericCellValue());
                    break;
                case INTEGER:
                    juffrouBeanWrapper.setValue(excelField.getName(),
                            new Integer(new Double(cell.getNumericCellValue()).intValue()));
                    break;
                default:
                    juffrouBeanWrapper.setValue(excelField.getName(),
                            getCellValue(row, cell));
                    break;
            }
        }

        return juffrouBeanWrapper.getBean();

    }

    private String getCellValue(Row row, Cell cell) {
        String result = null;

        if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {
                case HSSFCell.CELL_TYPE_BLANK:
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    boolean flag = cell.getBooleanCellValue();
                    result = flag + "";
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                    byte error = cell.getErrorCellValue();
                    result = error + "";
                    break;
                case HSSFCell.CELL_TYPE_FORMULA:
//				result = cell.getCellFormula();
//				getFormulaEvaluator().setCurrentRow(row);
//				HSSFFormulaEvaluator.CellValue formulaCellValue = getFormulaEvaluator()
//						.evaluate(cell);
//				result = getFormulaCellValue(formulaCellValue);
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date =

                                HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                        DateFormat format = new SimpleDateFormat("d-m-Y");
                        result = format.format(date);
                    } else {
                        double value = cell.getNumericCellValue();
                        NumberFormat format = NumberFormat.getNumberInstance();
                        format.setMaximumIntegerDigits(99);
                        format.setGroupingUsed(false);

                        result = format.format(value);
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
            }
        }

        return (result);
    }
}
