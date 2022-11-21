package com.jinpinghu.logic.export;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.common.tools.PoiUtil;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolCatalogYxcfDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolCatalog.param.GetToolCatalogListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ExportByToolLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetToolCatalogListParam myParam = (GetToolCatalogListParam)logicParam;
		String name = myParam.getName();
		String enterpriseName = myParam.getEnterpriseName();
		String type =myParam.getType();
		String unit = myParam.getUnit();
		String status = StringUtils.isEmpty(myParam.getStatus())?"1":myParam.getStatus();
//		if("1".equals(isExport)) {
//			String fileName = export2(enterpriseId, goodsName, startTime, endTime, em);
//			res.add("status", 1).add("msg", "操作成功").add("path", "/export/"+fileName);
//			return true;
//		}
		String uniformPrice = myParam.getUniformPrice();
		String productAttributes = myParam.getProductAttributes();
		TbBrandDao tbBrandDao = new TbBrandDao(em);
		TbToolCatalogYxcfDao tbToolCatalogYxcfDao = new TbToolCatalogYxcfDao(em);
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String, Object> resultMap;
		TbToolCatalogDao toolCatelogDao2 = new TbToolCatalogDao(em);
		if(StringUtils.isEmpty(type)||type.equals("12")) {
			List<Object[]> list = toolCatelogDao2.findByName(name,null,null,null,"12",unit,null,uniformPrice,null,status,productAttributes);
			if(list!=null) {
				for(Object[] o:list) {
					resultMap  =new HashMap<String, Object>();
					resultMap.put("id", o[0]);
					resultMap.put("toolCatelogName", o[1]);
					resultMap.put("model", o[2]);
					resultMap.put("specification", o[3]);
					resultMap.put("unit", o[4]);
					resultMap.put("price", o[5]);
					resultMap.put("number", o[6]);
					resultMap.put("describe", o[7]);
					resultMap.put("type", o[8]);
					resultMap.put("fileUrl",o[9]);
					resultMap.put("supplierName", o[10]);
					resultMap.put("productionUnits", o[11]);
					resultMap.put("uniformPrice", o[12]);
					resultMap.put("typeName", o[13]);
					resultMap.put("code", o[14]);
					resultMap.put("registrationCertificateNumber",o[15]);
					resultMap.put("explicitIngredients",o[16]);
					resultMap.put("effectiveIngredients",o[17]);
					resultMap.put("implementationStandards",o[18]);
					resultMap.put("dosageForms",o[19]);
					resultMap.put("productAttributes",o[20]);
					resultMap.put("toxicity",o[21]);
					resultMap.put("qualityGrade",o[22]);
					resultMap.put("NPK",o[30]);
					resultMap.put("NP",o[31]);
					resultMap.put("NK",o[32]);
					resultMap.put("PK",o[33]);
					resultMap.put("zjzl",o[34]);
					resultMap.put("yxcfUnit",o[35]);
					
					resultMap.put("N",o[36]);
					resultMap.put("P",o[37]);
					resultMap.put("K",o[38]);
					resultMap.put("qt",o[39]);
					
					resultList.add(resultMap);
				}
			}
		}
		List<Map<String, Object>> resultList2 = new ArrayList<Map<String,Object>>();
		if(StringUtils.isEmpty(type)||type.equals("13")) {
		List<Object[]> list2 = toolCatelogDao2.findByName(name,null,null,null,"13",unit,null,uniformPrice,null,status,productAttributes);
		if(list2!=null) {
			for(Object[] o:list2) {
				resultMap  =new HashMap<String, Object>();
				resultMap.put("id", o[0]);
				resultMap.put("toolCatelogName", o[1]);
				resultMap.put("model", o[2]);
				resultMap.put("specification", o[3]);
				resultMap.put("unit", o[4]);
				resultMap.put("price", o[5]);
				resultMap.put("number", o[6]);
				resultMap.put("describe", o[7]);
				resultMap.put("type", o[8]);
				resultMap.put("fileUrl",o[9]);
				resultMap.put("supplierName", o[10]);
				resultMap.put("productionUnits", o[11]);
				resultMap.put("uniformPrice", o[12]);
				resultMap.put("typeName", o[13]);
				resultMap.put("code", o[14]);
				resultMap.put("registrationCertificateNumber",o[15]);
				resultMap.put("explicitIngredients",o[16]);
				resultMap.put("effectiveIngredients",o[17]);
				resultMap.put("implementationStandards",o[18]);
				resultMap.put("dosageForms",o[19]);
				resultMap.put("productAttributes",o[20]);
				resultMap.put("toxicity",o[21]);
				resultMap.put("qualityGrade",o[22]);
				resultMap.put("zjzl",o[34]);
				resultMap.put("yxcfUnit",o[35]);
				String yxcf = tbToolCatalogYxcfDao.findStringByToolCatalogId(Integer.valueOf(o[0].toString()));
				resultMap.put("yxcf", yxcf);
				
				resultList2.add(resultMap);
			}
		}
		}
		
		List<Map<String, Object>> resultList3 = new ArrayList<Map<String,Object>>();
		if(StringUtils.isEmpty(type)||type.equals("14")) {
			List<Object[]> list3 = toolCatelogDao2.findByName(name,null,null,null,"14",unit,null,uniformPrice,null,status,productAttributes);
			if(list3!=null) {
				for(Object[] o:list3) {
					resultMap  =new HashMap<String, Object>();
					resultMap.put("id", o[0]);
					resultMap.put("toolCatelogName", o[1]);
					resultMap.put("model", o[2]);
					resultMap.put("specification", o[3]);
					resultMap.put("unit", o[4]);
					resultMap.put("price", o[5]);
					resultMap.put("number", o[6]);
					resultMap.put("describe", o[7]);
					resultMap.put("type", o[8]);
					resultMap.put("fileUrl",o[9]);
					resultMap.put("supplierName", o[10]);
					resultMap.put("productionUnits", o[11]);
					resultMap.put("uniformPrice", o[12]);
					resultMap.put("typeName", o[13]);
					resultMap.put("code", o[14]);
					resultMap.put("registrationCertificateNumber",o[15]);
					resultMap.put("explicitIngredients",o[16]);
					resultMap.put("effectiveIngredients",o[17]);
					resultMap.put("implementationStandards",o[18]);
					resultMap.put("dosageForms",o[19]);
					resultMap.put("productAttributes",o[20]);
					resultMap.put("toxicity",o[21]);
					resultMap.put("qualityGrade",o[22]);
					resultList3.add(resultMap);
				}
			}
		}
		
		List<Map<String, Object>> resultList4 = new ArrayList<Map<String,Object>>();
		if(StringUtils.isEmpty(type)||type.equals("15")) {
			List<Object[]> list4 = toolCatelogDao2.findByName(name,null,null,null,"15",unit,null,uniformPrice,null,status,productAttributes);
			if(list4!=null) {
				for(Object[] o:list4) {
					resultMap  =new HashMap<String, Object>();
					resultMap.put("id", o[0]);
					resultMap.put("toolCatelogName", o[1]);
					resultMap.put("model", o[2]);
					resultMap.put("specification", o[3]);
					resultMap.put("unit", o[4]);
					resultMap.put("price", o[5]);
					resultMap.put("number", o[6]);
					resultMap.put("describe", o[7]);
					resultMap.put("type", o[8]);
					resultMap.put("fileUrl",o[9]);
					resultMap.put("supplierName", o[10]);
					resultMap.put("productionUnits", o[11]);
					resultMap.put("uniformPrice", o[12]);
					resultMap.put("typeName", o[13]);
					resultMap.put("code", o[14]);
					resultMap.put("registrationCertificateNumber",o[15]);
					resultMap.put("explicitIngredients",o[16]);
					resultMap.put("effectiveIngredients",o[17]);
					resultMap.put("implementationStandards",o[18]);
					resultMap.put("dosageForms",o[19]);
					resultMap.put("productAttributes",o[20]);
					resultMap.put("toxicity",o[21]);
					resultMap.put("qualityGrade",o[22]);
					resultList4.add(resultMap);
				}
			} 
		}
		String fileName = "";
//		if("1".equals(isExport))
		fileName = export(resultList,resultList2,resultList3,resultList4);
		res.add("status", 1).add("msg", "操作成功").add("path", "/export/"+fileName);
		return true;
	}

	
	public String export(List<Map<String, Object>> dataList,List<Map<String, Object>> dataList2,List<Map<String, Object>> dataList3,List<Map<String, Object>> dataList4) throws Exception {
		String docsPath = this.getClass().getResource("/").getPath() + "../../export";
		docsPath = URLDecoder.decode(docsPath,"utf-8");  
		String TemplateFilePath = docsPath;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = sdf.format(new Date()) + ".xls";
		String filePath = TemplateFilePath + "/" + fileName;
		
		// 创建新表格并写入内容
		HSSFWorkbook targetWork = new HSSFWorkbook();
		Map<String, HSSFCellStyle> styleMap = new HashMap<String, HSSFCellStyle>();
		//导出模块
		HSSFSheet targetSheet = targetWork.createSheet("肥料");
		HSSFSheet targetSheet1 = targetWork.createSheet("农药");
		HSSFSheet targetSheet2 = targetWork.createSheet("种子");
		HSSFSheet targetSheet3 = targetWork.createSheet("其他");
		
		//创建输入流，读取模板文件
		InputStream in = new FileInputStream(TemplateFilePath + "/toolList.xls");
        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
		HSSFSheet sourceSheet = sourceWork.getSheet("肥料");
		HSSFSheet sourceSheet1 = sourceWork.getSheet("农药");
		HSSFSheet sourceSheet2 = sourceWork.getSheet("种子");
		HSSFSheet sourceSheet3 = sourceWork.getSheet("其他");
		
		//计算模板文件首位行
		int fistRowNum = sourceSheet.getFirstRowNum();
		int lastRowNum = sourceSheet.getLastRowNum();
		int maxColumnNum = 0;
		
		int fistRowNum1 = sourceSheet1.getFirstRowNum();
		int lastRowNum1 = sourceSheet1.getLastRowNum();
		int maxColumnNum1 = 0;
		
		int fistRowNum2 = sourceSheet2.getFirstRowNum();
		int lastRowNum2 = sourceSheet2.getLastRowNum();
		int maxColumnNum2 = 0;
		
		int fistRowNum3 = sourceSheet3.getFirstRowNum();
		int lastRowNum3 = sourceSheet3.getLastRowNum();
		int maxColumnNum3 = 0;
		
		
		//用于复制注释
		HSSFPatriarch patriarch = targetSheet.createDrawingPatriarch();
		HSSFPatriarch patriarch1 = targetSheet1.createDrawingPatriarch();
		HSSFPatriarch patriarch2 = targetSheet2.createDrawingPatriarch();
		HSSFPatriarch patriarch3 = targetSheet3.createDrawingPatriarch();

		//逐行复制模板文件单元格
		for(int j = fistRowNum ; j <= lastRowNum ; j++){
			HSSFRow sourceRow = sourceSheet.getRow(j);
			HSSFRow targetRow = targetSheet.createRow(j);
			PoiUtil.copyRow(targetRow, sourceRow, targetWork, sourceWork, patriarch, styleMap);
			if (targetRow.getLastCellNum() > maxColumnNum) {
                maxColumnNum = targetRow.getLastCellNum();
            }
		}
		for(int j = fistRowNum1 ; j <= lastRowNum1 ; j++){
			HSSFRow sourceRow = sourceSheet1.getRow(j);
			HSSFRow targetRow = targetSheet1.createRow(j);
			PoiUtil.copyRow(targetRow, sourceRow, targetWork, sourceWork, patriarch1, styleMap);
			if (targetRow.getLastCellNum() > maxColumnNum1) {
                maxColumnNum1 = targetRow.getLastCellNum();
            }
		}
		for(int j = fistRowNum2 ; j <= lastRowNum2 ; j++){
			HSSFRow sourceRow = sourceSheet2.getRow(j);
			HSSFRow targetRow = targetSheet2.createRow(j);
			PoiUtil.copyRow(targetRow, sourceRow, targetWork, sourceWork, patriarch2, styleMap);
			if (targetRow.getLastCellNum() > maxColumnNum2) {
                maxColumnNum2 = targetRow.getLastCellNum();
            }
		}
		for(int j = fistRowNum3 ; j <= lastRowNum3 ; j++){
			HSSFRow sourceRow = sourceSheet3.getRow(j);
			HSSFRow targetRow = targetSheet3.createRow(j);
			PoiUtil.copyRow(targetRow, sourceRow, targetWork, sourceWork, patriarch3, styleMap);
			if (targetRow.getLastCellNum() > maxColumnNum3) {
                maxColumnNum3 = targetRow.getLastCellNum();
            }
		}
		
		//复制模板文件合并单元格
		for (int k = 0; k < sourceSheet.getNumMergedRegions(); k++) {
            CellRangeAddress oldRange = sourceSheet.getMergedRegion(k);
            CellRangeAddress newRange = new CellRangeAddress(
	            oldRange.getFirstRow(), oldRange.getLastRow(),
	            oldRange.getFirstColumn(), oldRange.getLastColumn()
            );
            targetSheet.addMergedRegion(newRange);
        }
		for (int k = 0; k < sourceSheet1.getNumMergedRegions(); k++) {
            CellRangeAddress oldRange = sourceSheet1.getMergedRegion(k);
            CellRangeAddress newRange = new CellRangeAddress(
	            oldRange.getFirstRow(), oldRange.getLastRow(),
	            oldRange.getFirstColumn(), oldRange.getLastColumn()
            );
            targetSheet1.addMergedRegion(newRange);
        }
		for (int k = 0; k < sourceSheet2.getNumMergedRegions(); k++) {
            CellRangeAddress oldRange = sourceSheet2.getMergedRegion(k);
            CellRangeAddress newRange = new CellRangeAddress(
	            oldRange.getFirstRow(), oldRange.getLastRow(),
	            oldRange.getFirstColumn(), oldRange.getLastColumn()
            );
            targetSheet2.addMergedRegion(newRange);
        }
		for (int k = 0; k < sourceSheet3.getNumMergedRegions(); k++) {
            CellRangeAddress oldRange = sourceSheet3.getMergedRegion(k);
            CellRangeAddress newRange = new CellRangeAddress(
	            oldRange.getFirstRow(), oldRange.getLastRow(),
	            oldRange.getFirstColumn(), oldRange.getLastColumn()
            );
            targetSheet3.addMergedRegion(newRange);
        }
		
//		if(dataList == null || dataList.size()<=0){
//			in.close();
//			// 创建输出流，并输出文件
//			OutputStream os = new FileOutputStream(filePath);
//			//OutputStream os = new FileOutputStream("F:/test/"+fileName);
//			targetWork.write(os);
//			os.close();
//			return fileName;
//		}
		
		//设置单元格居中
		HSSFCellStyle cs = targetWork.createCellStyle();
		cs.setAlignment(CellStyle.ALIGN_CENTER);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		 /*设置边框*/
		cs.setBorderBottom(CellStyle.BORDER_THIN);//下边框    
		cs.setBorderLeft(CellStyle.BORDER_THIN);//左边框    
		cs.setBorderTop(CellStyle.BORDER_THIN);//上边框    
		cs.setBorderRight(CellStyle.BORDER_THIN);//右边框 
		cs.setWrapText(true);
		
		int beginRow = 1;
		targetSheet.createRow(beginRow);
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> orderMap = dataList.get(i);
			HSSFRow row = targetSheet.getRow(beginRow);
			
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue((String)orderMap.get("toolCatelogName"));
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue((String)orderMap.get("code"));
			cell1.setCellStyle(cs);
			
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue((String)orderMap.get("price"));
			cell2.setCellStyle(cs);
			
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue((String)orderMap.get("unit"));
			cell3.setCellStyle(cs);
			
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue((String)orderMap.get("specification"));
			cell4.setCellStyle(cs);
			
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue((String)orderMap.get("productionUnits"));
			cell5.setCellStyle(cs);
			
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue((String)orderMap.get("implementationStandards"));//执行标准
			cell6.setCellStyle(cs);
			
			HSSFCell cell7 = row.createCell(7);
			cell7.setCellValue((String)orderMap.get("explicitIngredients"));//明示成分
			cell7.setCellStyle(cs);
			
			HSSFCell cell8 = row.createCell(8);
			cell8.setCellValue((String)orderMap.get("registrationCertificateNumber"));
			cell8.setCellStyle(cs);
			
			HSSFCell cell9 = row.createCell(9);
			cell9.setCellValue((String)orderMap.get("qualityGrade"));
			cell9.setCellStyle(cs);
			
			HSSFCell cell10 = row.createCell(10);
			cell10.setCellValue((String)orderMap.get("productAttributes"));
			cell10.setCellStyle(cs);
			
			HSSFCell cell11 = row.createCell(11);
			cell11.setCellValue((orderMap.get("uniformPrice")+"").equals("1")?"是":"否");
			cell11.setCellStyle(cs);
			
			HSSFCell cell12 = row.createCell(12);
			cell12.setCellValue((String)(orderMap.get("N")+""));
			cell12.setCellStyle(cs);
			
			HSSFCell cell13 = row.createCell(13);
			cell13.setCellValue((String)(orderMap.get("P")+""));
			cell13.setCellStyle(cs);
			
			HSSFCell cell14 = row.createCell(14);
			cell14.setCellValue((String)(orderMap.get("K")+""));
			cell14.setCellStyle(cs);
			
			HSSFCell cell15 = row.createCell(15);
			cell15.setCellValue((String)(orderMap.get("NPK")+""));
			cell15.setCellStyle(cs);
			
			HSSFCell cell16 = row.createCell(16);
			cell16.setCellValue((String)(orderMap.get("NP")+""));
			cell16.setCellStyle(cs);
			
			HSSFCell cell17 = row.createCell(17);
			cell17.setCellValue((String)(orderMap.get("NK")+""));
			cell17.setCellStyle(cs);
			
			HSSFCell cell18 = row.createCell(18);
			cell18.setCellValue((String)(orderMap.get("PK")+""));
			cell18.setCellStyle(cs);
			
			HSSFCell cell19 = row.createCell(19);
			cell19.setCellValue((String)(orderMap.get("qt")+""));
			cell19.setCellStyle(cs);
			
			
			targetSheet.createRow(++beginRow);
			
		}
		//复制模板文件列宽
		for (int i = 0; i <= maxColumnNum; i++){
			targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
		}
		
		beginRow = 1;
		targetSheet1.createRow(beginRow);
		for (int i = 0; i < dataList2.size(); i++) {
			Map<String, Object> orderMap = dataList2.get(i);
			HSSFRow row = targetSheet1.getRow(beginRow);
			
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue((String)orderMap.get("toolCatelogName"));
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue((String)orderMap.get("code"));
			cell1.setCellStyle(cs);
			
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue((String)orderMap.get("price"));
			cell2.setCellStyle(cs);
			
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue((String)orderMap.get("unit"));
			cell3.setCellStyle(cs);
			
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue((String)orderMap.get("specification"));
			cell4.setCellStyle(cs);
			
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue((String)orderMap.get("productionUnits"));
			cell5.setCellStyle(cs);
			
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue((String)orderMap.get("implementationStandards"));//执行标准
			cell6.setCellStyle(cs);
			
			HSSFCell cell7 = row.createCell(7);
			cell7.setCellValue((String)orderMap.get("effectiveIngredients")+(String)orderMap.get("yxcfUnit"));//明示成分,总有效成分
			cell7.setCellStyle(cs);
			
			HSSFCell cell8 = row.createCell(8);
			cell8.setCellValue((String)orderMap.get("dosageForms"));
			cell8.setCellStyle(cs);
			
			HSSFCell cell9 = row.createCell(9);
			cell9.setCellValue((String)orderMap.get("productAttributes"));
			cell9.setCellStyle(cs);
			
			HSSFCell cell10 = row.createCell(10);
			cell10.setCellValue((String)orderMap.get("toxicity"));
			cell10.setCellStyle(cs);
			
			HSSFCell cell11 = row.createCell(11);
			cell11.setCellValue((orderMap.get("uniformPrice")+"").equals("1")?"是":"否");
			cell11.setCellStyle(cs);
			
			HSSFCell cell12 = row.createCell(12);
			cell12.setCellValue((orderMap.get("zjzl")+""));
			cell12.setCellStyle(cs);
			
			HSSFCell cell13 = row.createCell(13);
			cell13.setCellValue((orderMap.get("yxcf")+""));
			cell13.setCellStyle(cs);
			
			++beginRow;
			targetSheet1.createRow(beginRow);
			
		}
		//复制模板文件列宽
		for (int i = 0; i <= maxColumnNum; i++){
			targetSheet1.setColumnWidth(i, sourceSheet.getColumnWidth(i));
		}
		
		beginRow = 1;
		targetSheet2.createRow(beginRow);
		for (int i = 0; i < dataList3.size(); i++) {
			Map<String, Object> orderMap = dataList3.get(i);
			HSSFRow row = targetSheet2.getRow(beginRow);
			
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue((String)orderMap.get("toolCatelogName"));
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue((String)orderMap.get("code"));
			cell1.setCellStyle(cs);
			
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue((String)orderMap.get("price"));
			cell2.setCellStyle(cs);
			
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue((String)orderMap.get("unit"));
			cell3.setCellStyle(cs);
			
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue((String)orderMap.get("specification"));
			cell4.setCellStyle(cs);
			
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue((String)orderMap.get("productionUnits"));
			cell5.setCellStyle(cs);
			
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue((String)orderMap.get("registrationCertificateNumber"));//执行标准
			cell6.setCellStyle(cs);
			
			HSSFCell cell7 = row.createCell(7);
			cell7.setCellValue((String)orderMap.get("explicitIngredients"));//明示成分
			cell7.setCellStyle(cs);
			
			HSSFCell cell8 = row.createCell(8);
			cell8.setCellValue((String)orderMap.get("productAttributes"));
			cell8.setCellStyle(cs);
			
			HSSFCell cell9 = row.createCell(9);
			cell9.setCellValue((orderMap.get("uniformPrice")+"").equals("1")?"是":"否");
			cell9.setCellStyle(cs);
			
			targetSheet2.createRow(++beginRow);
			
		}
		//复制模板文件列宽
		for (int i = 0; i <= maxColumnNum; i++){
			targetSheet2.setColumnWidth(i, sourceSheet.getColumnWidth(i));
		}
		
		beginRow = 1;
		targetSheet3.createRow(beginRow);
		for (int i = 0; i < dataList4.size(); i++) {
			Map<String, Object> orderMap = dataList4.get(i);
			HSSFRow row = targetSheet3.getRow(beginRow);
			
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue((String)orderMap.get("toolCatelogName"));
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue((String)orderMap.get("code"));
			cell1.setCellStyle(cs);
			
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue((String)orderMap.get("price"));
			cell2.setCellStyle(cs);
			
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue((String)orderMap.get("unit"));
			cell3.setCellStyle(cs);
			
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue((String)orderMap.get("specification"));
			cell4.setCellStyle(cs);
			
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue((String)orderMap.get("productionUnits"));
			cell5.setCellStyle(cs);
			
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue((orderMap.get("uniformPrice")+"").equals("1")?"是":"否");
			cell6.setCellStyle(cs);
			
			targetSheet3.createRow(++beginRow);
			
		}
		//复制模板文件列宽
		for (int i = 0; i <= maxColumnNum; i++){
			targetSheet3.setColumnWidth(i, sourceSheet.getColumnWidth(i));
		}
		
		
		in.close();
		// 创建输出流，并输出文件
		OutputStream os = new FileOutputStream(filePath);
		//OutputStream os = new FileOutputStream("F:/test/"+fileName);
		targetWork.write(os);
		os.close();
		return fileName;
	}

}
