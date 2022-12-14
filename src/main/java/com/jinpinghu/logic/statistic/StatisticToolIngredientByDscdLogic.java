package com.jinpinghu.logic.statistic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.StatisticToolIngredientByDscdParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticToolIngredientByDscdLogic extends BaseZLogic {

	public StatisticToolIngredientByDscdLogic() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StatisticToolIngredientByDscdParam myParam = (StatisticToolIngredientByDscdParam)logicParam;
		String selectAll = myParam.getSelectAll();
		String isExport = myParam.getIsExport();
		String startMonth = myParam.getStartMonth();
		String endMonth = myParam.getEndMonth();
		Integer linkOrderInfoId = StringUtils.isEmpty(myParam.getLinkOrderInfoId())?null:Integer.valueOf(myParam.getLinkOrderInfoId());
		StatisticDao statisticDao = new StatisticDao(em);
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		List<Map<String, Object>> resultList = statisticDao.statisticToolIngredientsByLinkOrderInfoId(selectAll,permissionEnterpriseIdList,null,"1",startMonth,endMonth,linkOrderInfoId);
		List<Map<String,Object>> total = statisticDao.statisticToolIngredientsByLinkOrderInfoId(selectAll,permissionEnterpriseIdList,null,null,startMonth,endMonth,linkOrderInfoId);
		if(total!=null) {
			total.get(0).put("NAME", "??????");
		}
		if(resultList != null)
			total.addAll(resultList);
		if("1".equals(isExport)) {
			String fileName = export(total,startMonth,endMonth);
			res.add("path", "/export/"+fileName);
		}
		res.add("status", 1).add("msg", "????????????").add("result", total);
		return true;
	}
	public String export(List<Map<String, Object>> dataList,String startMonth,String endMonth) throws Exception {
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
		HSSFSheet targetSheet = targetWork.createSheet("??????????????????");
		
		//????????????????????????????????????
		InputStream in = new FileInputStream(TemplateFilePath + "/statisticToolIngredient.xls");
        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
		HSSFSheet sourceSheet = sourceWork.getSheet("??????????????????");
		
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
		
		if (!StringUtils.isEmpty(startMonth) && !StringUtils.isEmpty(endMonth)) {
			targetSheet.getRow(0).getCell(0).setCellValue(startMonth+"???"+endMonth+"?????????????????????");
		}else {
			targetSheet.getRow(0).getCell(0).setCellValue("?????????????????????");
		}
		
		int beginRow = 3;
		targetSheet.createRow(beginRow);
		BigDecimal d = new BigDecimal("1000");
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> orderMap = dataList.get(i);
			HSSFRow row = targetSheet.getRow(beginRow);
			
			String sumstring = "SUM(" + "D"+(i+3)+":J"+(i+3)+")";
			
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue(orderMap.get("NAME")+"");
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue(new BigDecimal(orderMap.get("g")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
			cell1.setCellStyle(cs);
			
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue(new BigDecimal(orderMap.get("ng")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
			cell3.setCellStyle(cs);
			
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue(new BigDecimal(orderMap.get("pg")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
			cell4.setCellStyle(cs);
			
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue(new BigDecimal(orderMap.get("kg")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
			cell5.setCellStyle(cs);
			
			HSSFCell cell9 = row.createCell(6);
			cell9.setCellValue(new BigDecimal(orderMap.get("npkg")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
			cell9.setCellStyle(cs);	
			
			HSSFCell cell6 = row.createCell(7);
			cell6.setCellValue(new BigDecimal(orderMap.get("npg")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
			cell6.setCellStyle(cs);
			
			HSSFCell cell7 = row.createCell(8);
			cell7.setCellValue(new BigDecimal(orderMap.get("nkg")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
			cell7.setCellStyle(cs);
			
			HSSFCell cell8 = row.createCell(9);
			cell8.setCellValue(new BigDecimal(orderMap.get("pkg")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
			cell8.setCellStyle(cs);	
			

			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue(new BigDecimal(orderMap.get("ng")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP)
					.add(new BigDecimal(orderMap.get("pg")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP))
					.add(new BigDecimal(orderMap.get("kg")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP))
					.add(new BigDecimal(orderMap.get("npkg")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP))
					.add(new BigDecimal(orderMap.get("npg")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP))
					.add(new BigDecimal(orderMap.get("nkg")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP))
					.add(new BigDecimal(orderMap.get("pkg")+"").divide(d).setScale(2,BigDecimal.ROUND_HALF_UP))+"");
			cell2.setCellStyle(cs);
			
			
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
