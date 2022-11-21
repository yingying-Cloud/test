package com.jinpinghu.logic.applicantCompany;

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

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import com.jinpinghu.common.tools.PoiUtil;
import com.jinpinghu.db.dao.TbApplicantCompanyDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.applicantCompany.param.ExportByCompanyParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ExportByCompanyLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		ExportByCompanyParam myParam = (ExportByCompanyParam)logicParam;
        String companyId = myParam.getCompanyId();
        
        TbApplicantCompanyDao companyDao = new TbApplicantCompanyDao(em);
        Map<String, Object> map = companyDao.getApplicantCompanyInfoById(Integer.valueOf(companyId));
        List<Map<String, Object>> companyProduct = companyDao.findApplicantCompanyProductByApplicantCompanyId(Integer.valueOf(companyId));
        List<Map<String, Object>> companyTrademark = companyDao.findApplicantCompanyTrademarkByApplicantCompanyId(Integer.valueOf(companyId));
        map.put("companyProduct", companyProduct);
        map.put("companyTrademark", companyTrademark);
        String fileName = "";
		fileName = export(map);
		res.add("status", 1).add("msg", "操作成功").add("result", map).add("path", "/export/"+fileName);
		return true;
	}


	@SuppressWarnings("unchecked")
	public String export(Map<String, Object> map) throws Exception {
		String docsPath = this.getClass().getResource("/").getPath() + "../../export";
		docsPath = URLDecoder.decode(docsPath,"utf-8");
		String TemplateFilePath = docsPath;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String fileName = sdf.format(new Date()) + ".xls";
		String filePath = TemplateFilePath + "/" + fileName;

		// 创建新表格并写入内容
		HSSFWorkbook targetWork = new HSSFWorkbook();
		Map<String, HSSFCellStyle> styleMap = new HashMap<String, HSSFCellStyle>();
		//导出模块
		HSSFSheet targetSheet = targetWork.createSheet("基本信息");

		//创建输入流，读取模板文件
		InputStream in = new FileInputStream(TemplateFilePath + "/applicantCompany.xls");
        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
		HSSFSheet sourceSheet = sourceWork.getSheet("基本信息");

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

		if(map == null ){
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

		int beginRow = 0;
		
		List<Map<String, Object>> companyProduct = (List<Map<String, Object>>) map.get("companyProduct");
		List<Map<String, Object>> companyTrademark = (List<Map<String, Object>>) map.get("companyTrademark");
		
		for(int j = beginRow+17;j <= beginRow+17+(companyProduct==null||companyProduct.isEmpty() ? 0 : (companyProduct.size()-1)) +(companyTrademark==null||companyTrademark.isEmpty() ?0: companyTrademark.size()-1 );j++) {
			HSSFRow r = targetSheet.createRow(j);
			for (int j2 = 0; j2 < 7; j2++) {
				r.createCell(j2).setCellStyle(cs);;
			}
		}
		HSSFRow tempRow = targetSheet.getRow(beginRow++);
		HSSFCell name = tempRow.getCell(1);
		name.setCellValue((String)map.get("name"));
//		CellRangeAddress nameCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),0,0);
//		targetSheet.addMergedRegion(nameCra);
		name.setCellStyle(cs);

		HSSFRow tempRow1 = targetSheet.getRow(beginRow++);
		HSSFCell profile = tempRow1.getCell(1);
		profile.setCellValue((String)map.get("profile"));
//		CellRangeAddress timeCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),1,1);
//		targetSheet.addMergedRegion(timeCra);
		profile.setCellStyle(cs);

		HSSFRow tempRow2 = targetSheet.getRow(beginRow++);
		HSSFCell cell2 = tempRow2.getCell(1);
		cell2.setCellValue((String)map.get("address"));
//		CellRangeAddress orderNumberCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),2,2);
//		targetSheet.addMergedRegion(orderNumberCra);
		cell2.setCellStyle(cs);

		HSSFRow tempRow3 = targetSheet.getRow(beginRow++);
		HSSFCell cell3 = tempRow3.getCell(1);
		cell3.setCellValue((String)map.get("zipCode"));
//		CellRangeAddress nicknameCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),3,3);
//		targetSheet.addMergedRegion(nicknameCra);
		cell3.setCellStyle(cs);
		
		HSSFRow tempRow4 = targetSheet.getRow(beginRow++);
		HSSFCell cell4 = tempRow4.getCell(2);
		cell4.setCellValue((String)map.get("legalContact"));
		cell4.setCellStyle(cs);
		
		HSSFCell cell5 = tempRow4.getCell(5);
		cell5.setCellValue((String)map.get("legalMobile"));
		cell5.setCellStyle(cs);
		
		HSSFRow tempRow5 = targetSheet.getRow(beginRow++);
		HSSFCell cell45 = tempRow5.getCell(2);
		cell45.setCellValue((String)map.get("agentContact"));
		cell45.setCellStyle(cs);
		
		HSSFCell cell55 = tempRow5.getCell(5);
		cell55.setCellValue((String)map.get("agentMobile"));
		cell55.setCellStyle(cs);
		
		HSSFRow tempRo6 = targetSheet.getRow(beginRow++);
		HSSFCell cell6 = tempRo6.getCell(1);
		cell6.setCellValue((String)map.get("fax"));
		cell6.setCellStyle(cs);
		
		HSSFRow tempRo7 = targetSheet.getRow(beginRow++);
		HSSFCell cell7 = tempRo7.getCell(1);
		cell7.setCellValue((String)map.get("email"));
		cell7.setCellStyle(cs);
		
		HSSFRow tempRow8 = targetSheet.getRow(beginRow++);
		HSSFCell cell8 = tempRow8.getCell(2);
		cell8.setCellValue((String)map.get("uniformSocialCreditCode"));
		cell8.setCellStyle(cs);
		
		HSSFCell cell9 = tempRow8.getCell(4);
		cell9.setCellValue((String)map.get("operationPeriodStartTime")+"至"+map.get("operationPeriodEndTime"));
		cell9.setCellStyle(cs);
		
		HSSFRow tempRow9 = targetSheet.getRow(beginRow++);
		HSSFCell cell10 = tempRow9.getCell(2);
		cell10.setCellValue((String)map.get("spybName"));
		cell10.setCellStyle(cs);
		
		HSSFCell cell11 = tempRow9.getCell(4);
		cell11.setCellValue((String)map.get("spybProduct"));
		cell11.setCellStyle(cs);
		
		HSSFCell cell12 = tempRow9.getCell(6);
		cell12.setCellValue((String)map.get("spybStartTime")+"至"+map.get("spybEndTime"));
		cell12.setCellStyle(cs);
		
		HSSFRow tempRow10 = targetSheet.getRow(beginRow++);
		HSSFCell cell13 = tempRow10.getCell(1);
		cell13.setCellValue((String)map.get("type"));
		cell13.setCellStyle(cs);
		
		HSSFRow tempRow11 = targetSheet.getRow(beginRow++);
		HSSFCell cell14 = tempRow11.getCell(1);
		cell14.setCellValue((String)map.get("productionSituation"));
		cell14.setCellStyle(cs);
		
		HSSFRow tempRow12 = targetSheet.getRow(beginRow++);
		HSSFCell cell15 = tempRow12.getCell(1);
		cell15.setCellValue((String)map.get("machiningInfo"));
		cell15.setCellStyle(cs);
		
		HSSFRow tempRow13 = targetSheet.getRow(beginRow++);
		HSSFCell cell16 = tempRow13.getCell(1);
		cell16.setCellValue((String)map.get("productSales"));
		cell16.setCellStyle(cs);
		
		HSSFRow tempRow14 = targetSheet.getRow(beginRow++);
		HSSFCell cell17 = tempRow14.getCell(1);
		cell17.setCellValue((String)map.get("packageSourceManager"));
		cell17.setCellStyle(cs);
		if(companyProduct!=null) {
			for (int j = 0; j < companyProduct.size(); j++) {
				Map<String, Object>	orderInfoMap = companyProduct.get(j);
				HSSFRow row = targetSheet.getRow(++beginRow);
	
				HSSFCell cell18 = row.getCell(1);
				cell18.setCellValue((String)orderInfoMap.get("productName"));
				cell18.setCellStyle(cs);
	
				HSSFCell cell19 = row.getCell(2);
				cell19.setCellValue((String)orderInfoMap.get("area"));
				cell19.setCellStyle(cs);
	
	            HSSFCell cell20 = row.getCell(3);
	            cell20.setCellValue((String)orderInfoMap.get("yield"));
	            cell20.setCellStyle(cs);
				
				HSSFCell cell21 = row.getCell(4);
				cell21.setCellValue((String)orderInfoMap.get("producer"));
				cell21.setCellStyle(cs);
			}
		}
		
		
		HSSFFont font = targetWork.createFont(); 
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("等线");
		font.setFontHeightInPoints((short) 11);
		HSSFCellStyle cellStyle= targetWork.createCellStyle(); 
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		 /*设置边框*/
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);//下边框    
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框    
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框    
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框 
		cellStyle.setWrapText(true);
		cellStyle.setFont(font); 
		
		HSSFRow row = targetSheet.getRow(++beginRow);
		
		HSSFCell cell22 = row.getCell(0);
		cell22.setCellValue("注册商标");
		cell22.setCellStyle(cellStyle); 

		HSSFCell cell23 = row.getCell(1);
		cell23.setCellValue("商标名称");
		cell23.setCellStyle(cellStyle);

        HSSFCell cell24 = row.getCell(2);
        cell24.setCellValue("核定使用类别");
        cell24.setCellStyle(cellStyle);
		
		HSSFCell cell25 = row.getCell(3);
		cell25.setCellValue("有效期");
		cell25.setCellStyle(cellStyle);
		++beginRow;
		
		
		
		if(companyTrademark!=null) {
			for (int j = 0; j < companyTrademark.size(); j++) {
				Map<String, Object>	orderInfoMap = companyTrademark.get(j);
				HSSFRow rows = targetSheet.getRow(beginRow);
	
				HSSFCell cell18 = rows.getCell(1);
				cell18.setCellValue((String)orderInfoMap.get("name"));
				cell18.setCellStyle(cs);
	
				HSSFCell cell19 = rows.getCell(2);
				cell19.setCellValue((String)orderInfoMap.get("type"));
				cell19.setCellStyle(cs);
	
	            HSSFCell cell20 = rows.getCell(3);
	            cell20.setCellValue((String)orderInfoMap.get("startTime")+"至"+(String)orderInfoMap.get("endTime"));
	            cell20.setCellStyle(cs);
				
				++beginRow;
				
			}
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
	
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
	
}
