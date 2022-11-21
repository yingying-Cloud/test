package com.jinpinghu.logic.export;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
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

import com.jinpinghu.common.tools.PoiUtil;
import com.jinpinghu.db.bean.TbEnterpriseZero;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbEnterpriseZeroDao;
import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ExportEnterpriseByTypeLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetEnterpriseListParam myParam = (GetEnterpriseListParam)logicParam;
		String name=myParam.getName();
		String enterpriseCreditCode=myParam.getEnterpriseCreditCode();
		String enterpriseLegalPerson=myParam.getEnterpriseLegalPerson();
		String enterpriseLegalPersonIdcard=myParam.getEnterpriseLegalPersonIdcard();
		String enterpriseLinkPeople=myParam.getEnterpriseLinkPeople();
		String enterpriseLinkMobile=myParam.getEnterpriseLinkMobile();
		String enterpriseAddress=myParam.getEnterpriseAddress();
		Integer status = StringUtils.isNullOrEmpty(myParam.getStatus())?null:Integer.valueOf(myParam.getStatus());
		Integer enterpriseType = StringUtils.isNullOrEmpty(myParam.getEnterpriseType())?null:Integer.valueOf(myParam.getEnterpriseType());
		String dscd = myParam.getDscd();
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		Integer state = StringUtils.isNullOrEmpty(myParam.getState())?null:Integer.valueOf(myParam.getState());
		
		TbToolDao tbToolDao = new TbToolDao(em);
		TbToolOrderDao toolOrderDao = new TbToolOrderDao(em);
		TbPlantProtectionOrderDao plantProtectionOrderDao = new TbPlantProtectionOrderDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbEnterpriseZeroDao enterpriseZeroDao = new TbEnterpriseZeroDao(em);
		Integer count = enterpriseDao.findByAllCount(name, enterpriseCreditCode, enterpriseLegalPerson, enterpriseLegalPersonIdcard, enterpriseLinkPeople, enterpriseLinkMobile, enterpriseAddress, enterpriseType, status,dscd,null,null,state);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String, Object>> list = enterpriseDao.findByAll(name, enterpriseCreditCode, enterpriseLegalPerson, enterpriseLegalPersonIdcard, enterpriseLinkPeople, enterpriseLinkMobile, enterpriseAddress, enterpriseType, status,nowPage,pageCount,dscd,null,null,state);
	
		if(list!=null) {
			for(Map<String, Object> map:list) {
				Integer enterpriseId = (Integer) map.get("id");
				Object[] recordNoCount = tbToolDao.exportStatisticRecordNoCount(enterpriseId);
				map.put("ybas", recordNoCount==null?"":recordNoCount[0]+"");
				map.put("wbas", recordNoCount==null?"":recordNoCount[1]+"");
			}
		}
		String fileName = "";
		if(enterpriseType!=null&&enterpriseType==1) {
			fileName = export(list);
		}
		else if(enterpriseType!=null&&enterpriseType==3){
			fileName = export3(list);
		}else {
			fileName = export2(list,enterpriseType);
		}
		res.add("status", 1).add("msg", "操作成功").add("path", "/export/"+fileName);
		return true;
	}



	public String export(List<Map<String, Object>> dataList) throws Exception {
		String docsPath = this.getClass().getResource("/").getPath() + "../../export";
		docsPath = URLDecoder.decode(docsPath,"utf-8");  
		String TemplateFilePath = docsPath;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = sdf.format(new Date()) + ".xls";
		String filePath = TemplateFilePath + "/" + fileName;
		
		// 创建新表格并写入内容
		HSSFWorkbook targetWork = new HSSFWorkbook();
		Map<String, HSSFCellStyle> styleMap = new HashMap<String, HSSFCellStyle>();
		//导出模块
		HSSFSheet targetSheet = targetWork.createSheet("种植户");
		
		//创建输入流，读取模板文件
		InputStream in = new FileInputStream(TemplateFilePath + "/plantEnterprise.xls");
        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
		HSSFSheet sourceSheet = sourceWork.getSheet("种植户");
		
		//计算模板文件首位行
		int fistRowNum = sourceSheet.getFirstRowNum();
		int lastRowNum = sourceSheet.getLastRowNum();
		int maxColumnNum = 0;
		
		//用于复制注释
		HSSFPatriarch patriarch = targetSheet.createDrawingPatriarch();

		//逐行复制模板文件单元格
		for(int j = fistRowNum ; j <= lastRowNum ; j++){
			HSSFRow sourceRow = sourceSheet.getRow(j);
			HSSFRow targetRow = targetSheet.createRow(j);
			PoiUtil.copyRow(targetRow, sourceRow, targetWork, sourceWork, patriarch, styleMap);
			if (targetRow.getLastCellNum() > maxColumnNum) {
                maxColumnNum = targetRow.getLastCellNum();
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
		
		if(dataList == null || dataList.size()<=0){
			in.close();
			// 创建输出流，并输出文件
			OutputStream os = new FileOutputStream(filePath);
			//OutputStream os = new FileOutputStream("F:/test/"+fileName);
			targetWork.write(os);
			os.close();
			return fileName;
		}
		
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
			cell0.setCellValue((String)orderMap.get("name"));
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue((String)orderMap.get("enterpriseCreditCode"));
			cell1.setCellStyle(cs);
			
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue("种植");
			cell2.setCellStyle(cs);
			
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue((String)orderMap.get("enterpriseLegalPerson"));
			cell3.setCellStyle(cs);
			
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue((String)orderMap.get("enterpriseLegalPersonIdcard"));
			cell4.setCellStyle(cs);
			
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue((String)orderMap.get("enterpriseLinkPeople"));
			cell5.setCellStyle(cs);
			
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue((String)orderMap.get("enterpriseLinkMobile"));
			cell6.setCellStyle(cs);
			
			HSSFCell cell7 = row.createCell(7);
			cell7.setCellValue((String)orderMap.get("enterpriseAddress"));
			cell7.setCellStyle(cs);
			
			HSSFCell cell8 = row.createCell(8);
			String changes =orderMap.containsKey("changes")?orderMap.get("changes").toString():"";
			if(!StringUtils.isNullOrEmpty(changes)&&changes.equals("2")) {
				cell8.setCellValue("否");
			}else if(!StringUtils.isNullOrEmpty(changes)&&changes.equals("1")) {
				cell8.setCellValue("是");
			}else {
				cell8.setCellValue("");
			}
			cell8.setCellStyle(cs);	
			
			HSSFCell cell9 = row.createCell(9);
			cell9.setCellValue((String)orderMap.get("registeredFunds"));
			cell9.setCellStyle(cs);
			
			HSSFCell cell10 = row.createCell(10);
			cell10.setCellValue((String)orderMap.get("enterpriseNature"));
			cell10.setCellStyle(cs);
			
			HSSFCell cell11 = row.createCell(11);
			cell11.setCellValue((String)orderMap.get("dsnm"));
			cell11.setCellStyle(cs);
			
			HSSFCell cell12 = row.createCell(12);
			cell12.setCellValue((String)orderMap.get("plantScope"));
			cell12.setCellStyle(cs);
			
			HSSFCell cell13 = row.createCell(13);
			cell13.setCellValue((String)orderMap.get("plantName"));
			cell13.setCellStyle(cs);
			
			HSSFCell cell14 = row.createCell(14);
			cell14.setCellValue((String)orderMap.get("plantScope"));
			cell14.setCellStyle(cs);
			
			HSSFCell cell15 = row.createCell(15);
			cell15.setCellValue((String)orderMap.get("baseAddress"));
			cell15.setCellStyle(cs);
			
			targetSheet.createRow(++beginRow);
			
		}
		//复制模板文件列宽
		for (int i = 0; i <= maxColumnNum; i++){
			targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
		}
		
		in.close();
		// 创建输出流，并输出文件
		OutputStream os = new FileOutputStream(filePath);
		//OutputStream os = new FileOutputStream("F:/test/"+fileName);
		targetWork.write(os);
		os.close();
		return fileName;
	}

	public String export2(List<Map<String, Object>> dataList,Integer enterpriseType) throws Exception {
		String docsPath = this.getClass().getResource("/").getPath() + "../../export";
		docsPath = URLDecoder.decode(docsPath,"utf-8");  
		String TemplateFilePath = docsPath;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = sdf.format(new Date()) + ".xls";
		String filePath = TemplateFilePath + "/" + fileName;
		
		// 创建新表格并写入内容
		HSSFWorkbook targetWork = new HSSFWorkbook();
		Map<String, HSSFCellStyle> styleMap = new HashMap<String, HSSFCellStyle>();
		//导出模块
		HSSFSheet targetSheet = null;
		if(enterpriseType!=null&&enterpriseType==2) {
			targetSheet = targetWork.createSheet("农服商");
		}else if(enterpriseType!=null&&enterpriseType==4) {
			targetSheet = targetWork.createSheet("采购商");
		}else {
			targetSheet = targetWork.createSheet("经销商");
		}
		//创建输入流，读取模板文件
		InputStream in = new FileInputStream(TemplateFilePath + "/otherEnterprise.xls");
        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
		HSSFSheet sourceSheet = sourceWork.getSheet("商户");
		
		//计算模板文件首位行
		int fistRowNum = sourceSheet.getFirstRowNum();
		int lastRowNum = sourceSheet.getLastRowNum();
		int maxColumnNum = 0;
		
		//用于复制注释
		HSSFPatriarch patriarch = targetSheet.createDrawingPatriarch();

		//逐行复制模板文件单元格
		for(int j = fistRowNum ; j <= lastRowNum ; j++){
			HSSFRow sourceRow = sourceSheet.getRow(j);
			HSSFRow targetRow = targetSheet.createRow(j);
			PoiUtil.copyRow(targetRow, sourceRow, targetWork, sourceWork, patriarch, styleMap);
			if (targetRow.getLastCellNum() > maxColumnNum) {
                maxColumnNum = targetRow.getLastCellNum();
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
		
		if(dataList == null || dataList.size()<=0){
			in.close();
			// 创建输出流，并输出文件
			OutputStream os = new FileOutputStream(filePath);
			//OutputStream os = new FileOutputStream("F:/test/"+fileName);
			targetWork.write(os);
			os.close();
			return fileName;
		}
		
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
			cell0.setCellValue((String)orderMap.get("name"));
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue((String)orderMap.get("enterpriseCreditCode"));
			cell1.setCellStyle(cs);
			
			HSSFCell cell3 = row.createCell(2);
			cell3.setCellValue((String)orderMap.get("enterpriseLegalPerson"));
			cell3.setCellStyle(cs);
			
			HSSFCell cell4 = row.createCell(3);
			cell4.setCellValue((String)orderMap.get("enterpriseLegalPersonIdcard"));
			cell4.setCellStyle(cs);
			
			HSSFCell cell5 = row.createCell(4);
			cell5.setCellValue((String)orderMap.get("enterpriseLinkPeople"));
			cell5.setCellStyle(cs);
			
			HSSFCell cell6 = row.createCell(5);
			cell6.setCellValue((String)orderMap.get("enterpriseLinkMobile"));
			cell6.setCellStyle(cs);
			
			HSSFCell cell7 = row.createCell(6);
			cell7.setCellValue((String)orderMap.get("enterpriseAddress"));
			cell7.setCellStyle(cs);
			
			HSSFCell cell8 = row.createCell(7);
			String changes =orderMap.containsKey("changes")?orderMap.get("changes").toString():"";
			if(!StringUtils.isNullOrEmpty(changes)&&changes.equals("2")) {
				cell8.setCellValue("否");
			}else if(!StringUtils.isNullOrEmpty(changes)&&changes.equals("1")) {
				cell8.setCellValue("是");
			}else {
				cell8.setCellValue("");
			}
			cell8.setCellStyle(cs);	
			
			HSSFCell cell9 = row.createCell(8);
			cell9.setCellValue((String)orderMap.get("registeredFunds"));
			cell9.setCellStyle(cs);
			
			HSSFCell cell10 = row.createCell(9);
			cell10.setCellValue((String)orderMap.get("enterpriseNature"));
			cell10.setCellStyle(cs);
			
			HSSFCell cell11 = row.createCell(10);
			cell11.setCellValue((String)orderMap.get("dsnm"));
			cell11.setCellStyle(cs);
			
			targetSheet.createRow(++beginRow);
			
		}
		//复制模板文件列宽
		for (int i = 0; i <= maxColumnNum; i++){
			targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
		}
		
		in.close();
		// 创建输出流，并输出文件
		OutputStream os = new FileOutputStream(filePath);
		//OutputStream os = new FileOutputStream("F:/test/"+fileName);
		targetWork.write(os);
		os.close();
		return fileName;
	}
	
	public String export3(List<Map<String, Object>> dataList) throws Exception {
		String docsPath = this.getClass().getResource("/").getPath() + "../../export";
		docsPath = URLDecoder.decode(docsPath,"utf-8");  
		String TemplateFilePath = docsPath;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = sdf.format(new Date()) + ".xls";
		String filePath = TemplateFilePath + "/" + fileName;
		
		// 创建新表格并写入内容
		HSSFWorkbook targetWork = new HSSFWorkbook();
		Map<String, HSSFCellStyle> styleMap = new HashMap<String, HSSFCellStyle>();
		//导出模块
		HSSFSheet targetSheet = targetWork.createSheet("农资店");
		
		//创建输入流，读取模板文件
		InputStream in = new FileInputStream(TemplateFilePath + "/toolEnterprise.xls");
        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
		HSSFSheet sourceSheet = sourceWork.getSheet("农资店");
		
		//计算模板文件首位行
		int fistRowNum = sourceSheet.getFirstRowNum();
		int lastRowNum = sourceSheet.getLastRowNum();
		int maxColumnNum = 0;
		
		//用于复制注释
		HSSFPatriarch patriarch = targetSheet.createDrawingPatriarch();

		//逐行复制模板文件单元格
		for(int j = fistRowNum ; j <= lastRowNum ; j++){
			HSSFRow sourceRow = sourceSheet.getRow(j);
			HSSFRow targetRow = targetSheet.createRow(j);
			PoiUtil.copyRow(targetRow, sourceRow, targetWork, sourceWork, patriarch, styleMap);
			if (targetRow.getLastCellNum() > maxColumnNum) {
                maxColumnNum = targetRow.getLastCellNum();
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
		
		if(dataList == null || dataList.size()<=0){
			in.close();
			// 创建输出流，并输出文件
			OutputStream os = new FileOutputStream(filePath);
			//OutputStream os = new FileOutputStream("F:/test/"+fileName);
			targetWork.write(os);
			os.close();
			return fileName;
		}
		
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
			cell0.setCellValue((String)orderMap.get("name"));
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue((String)orderMap.get("enterpriseCreditCode"));
			cell1.setCellStyle(cs);
			
			HSSFCell cell3 = row.createCell(2);
			cell3.setCellValue((String)orderMap.get("enterpriseLegalPerson"));
			cell3.setCellStyle(cs);
			
			HSSFCell cell4 = row.createCell(3);
			cell4.setCellValue((String)orderMap.get("enterpriseLegalPersonIdcard"));
			cell4.setCellStyle(cs);
			
			HSSFCell cell5 = row.createCell(4);
			cell5.setCellValue((String)orderMap.get("enterpriseLinkPeople"));
			cell5.setCellStyle(cs);
			
			HSSFCell cell6 = row.createCell(5);
			cell6.setCellValue((String)orderMap.get("enterpriseLinkMobile"));
			cell6.setCellStyle(cs);
			
			HSSFCell cell7 = row.createCell(6);
			cell7.setCellValue((String)orderMap.get("enterpriseAddress"));
			cell7.setCellStyle(cs);
			
//			HSSFCell cell8 = row.createCell(7);
//			String changes = orderMap.get("changes").toString();
//			if(!StringUtils.isNullOrEmpty(changes)&&changes.equals("2")) {
//				cell8.setCellValue("否");
//			}else if(!StringUtils.isNullOrEmpty(changes)&&changes.equals("1")) {
//				cell8.setCellValue("是");
//			}else {
//				cell8.setCellValue("");
//			}
//			cell8.setCellStyle(cs);	
			
			HSSFCell cell9 = row.createCell(7);
			cell9.setCellValue((String)orderMap.get("registeredFunds"));
			cell9.setCellStyle(cs);
			
			HSSFCell cell10 = row.createCell(8);
			cell10.setCellValue((String)orderMap.get("enterpriseNature"));
			cell10.setCellStyle(cs);
			
			HSSFCell cell11 = row.createCell(9);
			cell11.setCellValue((String)orderMap.get("dsnm"));
			cell11.setCellStyle(cs);
			
			HSSFCell cell12 = row.createCell(10);
			String state = orderMap.get("state").toString();
			if(!StringUtils.isNullOrEmpty(state)&&state.equals("1")) {
				cell12.setCellValue("启用");
			}else {
				cell12.setCellValue("停用");
			}
			cell12.setCellStyle(cs);
			
			HSSFCell cell13 = row.createCell(11);
			cell13.setCellValue((String)orderMap.get("ybas"));
			cell13.setCellStyle(cs);
			
			HSSFCell cell14 = row.createCell(12);
			cell14.setCellValue((String)orderMap.get("wbas"));
			cell14.setCellStyle(cs);
			
			HSSFCell cell15 = row.createCell(13);
			cell15.setCellValue((String)orderMap.get("cashRegisterVersion"));
			cell15.setCellStyle(cs);
			
			HSSFCell cell16 = row.createCell(14);
			cell16.setCellValue((String)orderMap.get("lastTime"));
			cell16.setCellStyle(cs);
			
			targetSheet.createRow(++beginRow);
			
		}
		//复制模板文件列宽
		for (int i = 0; i <= maxColumnNum; i++){
			targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
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
