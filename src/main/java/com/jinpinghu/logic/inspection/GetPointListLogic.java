package com.jinpinghu.logic.inspection;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbInspectionPointDao;
import com.jinpinghu.logic.BaseZLogic;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class GetPointListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;

		String name = param.getName();
		String year = param.getYear();
		Integer nowPage = StringUtil.isNullOrEmpty(param.getNowPage()) ? null : Integer.valueOf(param.getNowPage());
		Integer pageCount = StringUtil.isNullOrEmpty(param.getPageCount()) ? null : Integer.valueOf(param.getPageCount());
		Integer isExport = param.getIsExport();
		
		
		TbInspectionPointDao dao = new TbInspectionPointDao(em);
		/*List<Map<String, Object>> result = null;
		String fileName = "";
		
		Integer count = dao.getPointListCount(name, year);
		if(count != null && count ==0){
			result = dao.getPointList(name, year, nowPage, pageCount);
			if(isExport != null && isExport.equals(1))
				fileName= export();
		}*/
		List<Map<String, Object>> result = null;
		String fileName = "";
		int count = 0;
		List<Map<String, Object>> allResult = dao.getPointList(name, year, null, null);
		if(allResult != null){
			count = allResult.size();
			if(nowPage == null || pageCount == null){
				result = allResult;
			}else if(count < nowPage * pageCount){
				result = allResult.subList((nowPage-1)*pageCount, count);
			}else{
				result = allResult.subList((nowPage-1)*pageCount, nowPage*pageCount);
			}
			if(isExport != null && isExport.equals(1) && count > 0){
				fileName= export(allResult);
				res.add("path", "/export/"+fileName);
			}
		}
		res.add("result", result);
		res.add("allCounts", count);
		res.add("status", 1);
		res.add("msg", "??????");
		
		return true;
	}
	
	public String export(List<Map<String, Object>> dataList) throws Exception{
		String docsPath = this.getClass().getResource("/").getPath() + "../../export";
		docsPath = URLDecoder.decode(docsPath,"utf-8");  
		String TemplateFilePath = docsPath;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = sdf.format(new Date()) + ".xls";
		String filePath = TemplateFilePath + "/" + fileName;
		
		// ??????????????????????????????
		HSSFWorkbook targetWork = new HSSFWorkbook();
		Map<String, HSSFCellStyle> styleMap = new HashMap<String, HSSFCellStyle>();
		//????????????
		HSSFSheet targetSheet = targetWork.createSheet("???????????????????????????");
		
		//????????????????????????????????????
		InputStream in = new FileInputStream(TemplateFilePath + "/inspectionPoint.xls");
        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
		HSSFSheet sourceSheet = sourceWork.getSheet("???????????????????????????");
		
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
		
		int beginRow = 1;
		targetSheet.createRow(beginRow);
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> orderMap = dataList.get(i);
			HSSFRow row = targetSheet.getRow(beginRow);
			
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue((String)orderMap.get("name"));
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue((String)orderMap.get("itemName"));
			cell1.setCellStyle(cs);
			
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue((String)orderMap.get("levelName"));
			cell2.setCellStyle(cs);
			
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue((String)orderMap.get("inputTime"));
			cell3.setCellStyle(cs);
			
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue(orderMap.get("point").toString());
			cell4.setCellStyle(cs);
						
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