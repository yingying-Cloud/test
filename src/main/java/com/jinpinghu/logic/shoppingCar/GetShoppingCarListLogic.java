package com.jinpinghu.logic.shoppingCar;

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
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.shoppingCar.param.GetMyShoppingCarListParam;
import com.jinpinghu.logic.shoppingCar.param.GetShoppingCarListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetShoppingCarListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetShoppingCarListParam myParam = (GetShoppingCarListParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId()) ;
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String unit = myParam.getUnit();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String toolName = myParam.getToolName();
		String isExport=myParam.getIsExport();
		String enterpriseName = myParam.getEnterpriseName();
		String buyEnterpriseName = myParam.getBuyEnterpriseName();
		
		TbShoppingCarDao tbShoppingCarDao = new TbShoppingCarDao(em);
		Integer count = tbShoppingCarDao.findByAllCount(enterpriseId, unit, toolName, startTime, endTime,enterpriseName,buyEnterpriseName);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "?????????");
			return true;
		}
		List<Map<String, Object>> list = tbShoppingCarDao.findByAll(enterpriseId, unit, toolName, startTime, endTime, nowPage, pageCount,enterpriseName,buyEnterpriseName);
		String fileName = "";
		if(!StringUtils.isNullOrEmpty(isExport)&&isExport.equals("1")) {
			fileName= export(list);
		}
		
		res.add("allCounts", count);
		res.add("result", list);
		res.add("status", 1);
		res.add("msg", "??????");
		res.add("path", "/export/"+fileName);
		return true;
	}
	
	public String export(List<Map<String, Object>> dataList) throws Exception {
		String docsPath = this.getClass().getResource("/").getPath() + "../../export";
		docsPath = URLDecoder.decode(docsPath,"utf-8");  
		String TemplateFilePath = docsPath;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = sdf.format(new Date()) + ".xls";
		String filePath = TemplateFilePath + "/" + fileName;
		
		// ??????????????????????????????
		HSSFWorkbook targetWork = new HSSFWorkbook();
		Map<String, HSSFCellStyle> styleMap = new HashMap<String, HSSFCellStyle>();
		//????????????
		HSSFSheet targetSheet = targetWork.createSheet("????????????");
		
		//????????????????????????????????????
		InputStream in = new FileInputStream(TemplateFilePath + "/shoppingCar.xls");
        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
		HSSFSheet sourceSheet = sourceWork.getSheet("????????????");
		
		//???????????????????????????
		int fistRowNum = sourceSheet.getFirstRowNum();
		int lastRowNum = sourceSheet.getLastRowNum();
		int maxColumnNum = 0;
		
		//??????????????????
		HSSFPatriarch patriarch = targetSheet.createDrawingPatriarch();

		//?????????????????????????????????
		for(int j = fistRowNum ; j <= lastRowNum ; j++){
			HSSFRow sourceRow = sourceSheet.getRow(j);
			HSSFRow targetRow = targetSheet.createRow(j);
			PoiUtil.copyRow(targetRow, sourceRow, targetWork, sourceWork, patriarch, styleMap);
			if (targetRow.getLastCellNum() > maxColumnNum) {
                maxColumnNum = targetRow.getLastCellNum();
            }
		}
		
		//?????????????????????????????????
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
			// ?????????????????????????????????
			OutputStream os = new FileOutputStream(filePath);
			//OutputStream os = new FileOutputStream("F:/test/"+fileName);
			targetWork.write(os);
			os.close();
			return fileName;
		}
		
		//?????????????????????
		HSSFCellStyle cs = targetWork.createCellStyle();
		cs.setAlignment(CellStyle.ALIGN_CENTER);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		 /*????????????*/
		cs.setBorderBottom(CellStyle.BORDER_THIN);//?????????????????
		cs.setBorderLeft(CellStyle.BORDER_THIN);//?????????????????
		cs.setBorderTop(CellStyle.BORDER_THIN);//?????????????????
		cs.setBorderRight(CellStyle.BORDER_THIN);//???????????
		cs.setWrapText(true);
		
		int beginRow = 2;
		targetSheet.createRow(beginRow);
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> orderMap = dataList.get(i);
			HSSFRow row = targetSheet.getRow(beginRow);
			
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue((String)orderMap.get("enterpriseName"));
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue((String)orderMap.get("buyEnterpriseName"));
			cell1.setCellStyle(cs);
			
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue((String)orderMap.get("toolName"));
			cell2.setCellStyle(cs);
			
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue((String)orderMap.get("num"));
			cell3.setCellStyle(cs);
			
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue((String)orderMap.get("price"));
			cell4.setCellStyle(cs);
			
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue((String)orderMap.get("completeTime"));
			cell5.setCellStyle(cs);
			
			targetSheet.createRow(++beginRow);
			
		}
		//????????????????????????
		for (int i = 0; i <= maxColumnNum; i++){
			targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
		}
		
		in.close();
		// ?????????????????????????????????
		OutputStream os = new FileOutputStream(filePath);
		//OutputStream os = new FileOutputStream("F:/test/"+fileName);
		targetWork.write(os);
		os.close();
		return fileName;
	}
	
}
