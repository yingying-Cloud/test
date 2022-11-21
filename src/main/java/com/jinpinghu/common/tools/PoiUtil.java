package com.jinpinghu.common.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
 
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
/**
 * POI工具类 功能点： 
 * 1、实现excel的sheet复制，复制的内容包括单元的内容、样式、注释
 * 2、setMForeColor修改HSSFColor.YELLOW的色值，setMBorderColor修改PINK的色值
 * 
 * @author Administrator
 */
public final class PoiUtil {
 
    /**
     * 功能：拷贝sheet
     * 实际调用     copySheet(targetSheet, sourceSheet, targetWork, sourceWork, true)
     * @param targetSheet
     * @param sourceSheet
     * @param targetWork
     * @param sourceWork                                                                   
     */
    public static void copySheet(HSSFSheet targetSheet, HSSFSheet sourceSheet,
            HSSFWorkbook targetWork, HSSFWorkbook sourceWork) throws Exception{
        if(targetSheet == null || sourceSheet == null || targetWork == null || sourceWork == null){
            throw new IllegalArgumentException("调用PoiUtil.copySheet()方法时，targetSheet、sourceSheet、targetWork、sourceWork都不能为空，故抛出该异常！");
        }
        copySheet(targetSheet, sourceSheet, targetWork, sourceWork, true);
    }
 
    /**
     * 功能：拷贝sheet
     * @param targetSheet
     * @param sourceSheet
     * @param targetWork
     * @param sourceWork
     * @param copyStyle                 boolean 是否拷贝样式
     */
    private static void copySheet(HSSFSheet targetSheet, HSSFSheet sourceSheet,
            HSSFWorkbook targetWork, HSSFWorkbook sourceWork, boolean copyStyle)throws Exception {
         
        if(targetSheet == null || sourceSheet == null || targetWork == null || sourceWork == null){
            throw new IllegalArgumentException("调用PoiUtil.copySheet()方法时，targetSheet、sourceSheet、targetWork、sourceWork都不能为空，故抛出该异常！");
        }
         
        //复制源表中的行
        int maxColumnNum = 0;
 
        Map<String, HSSFCellStyle> styleMap = (copyStyle) ? new HashMap<String, HSSFCellStyle>() : null;
         
        HSSFPatriarch patriarch = targetSheet.createDrawingPatriarch(); //用于复制注释
        for (int i = sourceSheet.getFirstRowNum(); i <= sourceSheet.getLastRowNum(); i++) {
            HSSFRow sourceRow = sourceSheet.getRow(i);
            HSSFRow targetRow = targetSheet.createRow(i);
             
            if (sourceRow != null) {
                copyRow(targetRow, sourceRow,
                        targetWork, sourceWork,patriarch, styleMap);
                if (sourceRow.getLastCellNum() > maxColumnNum) {
                    maxColumnNum = sourceRow.getLastCellNum();
                }
            }
        }
         
        //复制源表中的合并单元格
        mergerRegion(targetSheet, sourceSheet);
         
        //设置目标sheet的列宽
        for (int i = 0; i <= maxColumnNum; i++) {
            targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
        }
    }
     
    /**
     * 功能：拷贝row
     * @param targetRow
     * @param sourceRow
     * @param styleMap
     * @param targetWork
     * @param sourceWork
     * @param targetPatriarch
     */
    public static void copyRow(HSSFRow targetRow, HSSFRow sourceRow,
            HSSFWorkbook targetWork, HSSFWorkbook sourceWork,HSSFPatriarch targetPatriarch, Map<String, HSSFCellStyle> styleMap) throws Exception {
        if(targetRow == null || sourceRow == null || targetWork == null || sourceWork == null || targetPatriarch == null){
            throw new IllegalArgumentException("调用PoiUtil.copyRow()方法时，targetRow、sourceRow、targetWork、sourceWork、targetPatriarch都不能为空，故抛出该异常！");
        }
         
        //设置行高
        targetRow.setHeight(sourceRow.getHeight());
         
        for (int i = sourceRow.getFirstCellNum(); i <= sourceRow.getLastCellNum(); i++) {
            HSSFCell sourceCell = sourceRow.getCell(i);
            HSSFCell targetCell = targetRow.getCell(i);
             
            if (sourceCell != null) {
                if (targetCell == null) {
                    targetCell = targetRow.createCell(i);
                }
                 
                //拷贝单元格，包括内容和样式
                copyCell(targetCell, sourceCell, targetWork, sourceWork, styleMap);
                 
                //拷贝单元格注释
                copyComment(targetCell,sourceCell,targetPatriarch);
            }
        }
    }
     
    /**
     * 功能：拷贝cell，依据styleMap是否为空判断是否拷贝单元格样式
     * @param targetCell            不能为空
     * @param sourceCell            不能为空
     * @param targetWork            不能为空
     * @param sourceWork            不能为空
     * @param styleMap              可以为空                
     */
    public static void copyCell(HSSFCell targetCell, HSSFCell sourceCell, HSSFWorkbook targetWork, HSSFWorkbook sourceWork,Map<String, HSSFCellStyle> styleMap) {
        if(targetCell == null || sourceCell == null || targetWork == null || sourceWork == null ){
            throw new IllegalArgumentException("调用PoiUtil.copyCell()方法时，targetCell、sourceCell、targetWork、sourceWork都不能为空，故抛出该异常！");
        }
         
        //处理单元格样式
        if(styleMap != null){
            if (targetWork == sourceWork) {
                targetCell.setCellStyle(sourceCell.getCellStyle());
            } else {
                String stHashCode = "" + sourceCell.getCellStyle().hashCode();
                HSSFCellStyle targetCellStyle = styleMap
                        .get(stHashCode);
                if (targetCellStyle == null) {
                    targetCellStyle = targetWork.createCellStyle();
                    targetCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
                    styleMap.put(stHashCode, targetCellStyle);
                }
                
                targetCell.setCellStyle(targetCellStyle);
            }
        }
         
        //处理单元格内容
        switch (sourceCell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            targetCell.setCellValue(sourceCell.getRichStringCellValue());
            break;
        case Cell.CELL_TYPE_NUMERIC:
            targetCell.setCellValue(sourceCell.getNumericCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            targetCell.setCellType(Cell.CELL_TYPE_BLANK);
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            targetCell.setCellValue(sourceCell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_ERROR:
            targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
            break;
        case Cell.CELL_TYPE_FORMULA:
            targetCell.setCellFormula(sourceCell.getCellFormula());
            break;
        default:
            break;
        }
    }
     
    /**
     * 功能：拷贝comment
     * @param targetCell
     * @param sourceCell
     * @param targetPatriarch
     */
    public static void copyComment(HSSFCell targetCell,HSSFCell sourceCell,HSSFPatriarch targetPatriarch)throws Exception{
        if(targetCell == null || sourceCell == null || targetPatriarch == null){
            throw new IllegalArgumentException("调用PoiUtil.copyCommentr()方法时，targetCell、sourceCell、targetPatriarch都不能为空，故抛出该异常！");
        }
         
        //处理单元格注释
        HSSFComment comment = sourceCell.getCellComment();
        if(comment != null){
            HSSFComment newComment = targetPatriarch.createComment(new HSSFClientAnchor());
            newComment.setAuthor(comment.getAuthor());
            newComment.setColumn(comment.getColumn());
            newComment.setFillColor(comment.getFillColor());
            newComment.setHorizontalAlignment(comment.getHorizontalAlignment());
            newComment.setLineStyle(comment.getLineStyle());
            newComment.setLineStyleColor(comment.getLineStyleColor());
            newComment.setLineWidth(comment.getLineWidth());
            newComment.setMarginBottom(comment.getMarginBottom());
            newComment.setMarginLeft(comment.getMarginLeft());
            newComment.setMarginTop(comment.getMarginTop());
            newComment.setMarginRight(comment.getMarginRight());
            newComment.setNoFill(comment.isNoFill());
            newComment.setRow(comment.getRow());
            newComment.setShapeType(comment.getShapeType());
            newComment.setString(comment.getString());
            newComment.setVerticalAlignment(comment.getVerticalAlignment());
            newComment.setVisible(comment.isVisible());
            targetCell.setCellComment(newComment);
        }
    }
     
    /**
     * 功能：复制原有sheet的合并单元格到新创建的sheet
     * 
     * @param sheetCreat
     * @param sourceSheet
     */
    public static void mergerRegion(HSSFSheet targetSheet, HSSFSheet sourceSheet)throws Exception {
        if(targetSheet == null || sourceSheet == null){
            throw new IllegalArgumentException("调用PoiUtil.mergerRegion()方法时，targetSheet或者sourceSheet不能为空，故抛出该异常！");
        }
         
        for (int i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
            CellRangeAddress oldRange = sourceSheet.getMergedRegion(i);
            CellRangeAddress newRange = new CellRangeAddress(
                    oldRange.getFirstRow(), oldRange.getLastRow(),
                    oldRange.getFirstColumn(), oldRange.getLastColumn());
            targetSheet.addMergedRegion(newRange);
        }
    }
    
    /**
     * 读取Excel文件
     * @param fileName
     */
    public static Workbook readExcel(String fileName){  
        boolean isE2007 = false;    //判断是否是excel2007格式  
        Workbook wb  = null;
        if(fileName.endsWith("xlsx"))  
            isE2007 = true;  
        try {  
            InputStream input = new FileInputStream(fileName);  //建立输入流  
            //根据文件格式(2003或者2007)来初始化  
            if(isE2007)  
                wb = new XSSFWorkbook(input);  
            else  
                wb = new HSSFWorkbook(input);  
            /*
            Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
            while (rows.hasNext()) {  
                Row row = rows.next();  //获得行数据  
                System.out.println("Row #" + row.getRowNum());  //获得行号从0开始  
                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
                while (cells.hasNext()) {  
                    Cell cell = cells.next();  
                    System.out.println("Cell #" + cell.getColumnIndex());  
                    switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
                    case HSSFCell.CELL_TYPE_NUMERIC:  
                        System.out.println(cell.getNumericCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_STRING:  
                        System.out.println(cell.getStringCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_BOOLEAN:  
                        System.out.println(cell.getBooleanCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_FORMULA:  
                        System.out.println(cell.getCellFormula());  
                        break;  
                    default:  
                        System.out.println("unsuported sell type");  
                    break;  
                    }  
                }  
            } 
            */
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
        return wb;
    }  
}
