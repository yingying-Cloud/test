package com.jinpinghu.logic.statistic;

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
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.common.tools.PoiUtil;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.StatisticStockParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticStockLogic extends BaseZLogic {

	public StatisticStockLogic() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StatisticStockParam myParam = (StatisticStockParam)logicParam;
		String type = StringUtils.isEmpty(myParam.getType()) ? "1" : myParam.getType();
		String dscd = myParam.getDscd();
		String selectAll = myParam.getSelectAll();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		if(!StringUtils.isEmpty(dscd)) {
			if("0000000000".equals(dscd)) {
				dscd = dscd.substring(0, 2)+"%";
			}else if("00000000".equals(dscd)) {
				dscd = dscd.substring(0, 4)+"%";
			}else if("000000".equals(dscd)) {
				dscd = dscd.substring(0,6)+"%";
			}else if("000".equals(dscd)) {
				dscd = dscd.substring(0,9)+"%";
			}
		}
		TbToolDao toolDao = new TbToolDao(em);
		
		Map<String, Object> totalMap = toolDao.statisticTotalStock(dscd,selectAll,permissionEnterpriseIdList);
		List<Map<String, Object>> resultList = toolDao.statisticStock(type,dscd,selectAll,permissionEnterpriseIdList);
		resultList.add(0, totalMap);

		String isExport = myParam.getIsExport();
		String fileName = "";
		if(!StringUtils.isEmpty(isExport)&&"1".equals(isExport)) {
			fileName = export(resultList);
		}
		res.add("path", "/export/"+fileName);
		
		res.add("status", 1).add("msg", "????????????").add("result", resultList);
		return true;
	}

	
	public String export(List<Map<String, Object>> dataList) throws Exception {
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
		HSSFSheet targetSheet = targetWork.createSheet("????????????");
		
		//????????????????????????????????????
		InputStream in = new FileInputStream(TemplateFilePath + "/statisticStock.xls");
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
		
		int beginRow = 1;
		targetSheet.createRow(beginRow);
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> orderMap = dataList.get(i);
			HSSFRow row = targetSheet.getRow(beginRow);
			
			HSSFCell cell9 = row.createCell(0);
			cell9.setCellValue(i+1);
			cell9.setCellStyle(cs);
			
			HSSFCell cell0 = row.createCell(1);
			cell0.setCellValue(orderMap.get("dsnm")+"");
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(2);
			cell1.setCellValue(orderMap.get("seed")+"");
			cell1.setCellStyle(cs);
			
			HSSFCell cell2 = row.createCell(3);
			cell2.setCellValue(orderMap.get("pesticide")+"");
			cell2.setCellStyle(cs);
			
			HSSFCell cell3 = row.createCell(4);
			cell3.setCellValue(orderMap.get("fertilizer")+"");
			cell3.setCellStyle(cs);
			
			HSSFCell cell4 = row.createCell(5);
			cell4.setCellValue(orderMap.get("other")+"");
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
