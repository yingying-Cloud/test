package com.jinpinghu.logic.statistic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbToolRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.StatisticInParam;

import fw.jbiz.common.util.EncryptTool;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticInLogic extends BaseZLogic {

	public StatisticInLogic() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StatisticInParam myParam = (StatisticInParam)logicParam;
		String tm = myParam.getTm();
		String name = myParam.getName();
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
		
		TbToolRecordDao toolRecordDao = new TbToolRecordDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		
		List<Object[]> dataList = toolRecordDao.statisticToolRecord(tm, name,dscd,Arrays.asList(1),selectAll,permissionEnterpriseIdList,null);
		
		List<Map<String, Object>> enterpriseList = enterpriseDao.getToolEnterpriseList(name,dscd,selectAll,permissionEnterpriseIdList);
		
		List<String> dayList = new ArrayList<String>(){{add("01");add("02");add("03");add("04");add("05");add("06");
		add("07");add("08");add("09");add("10");add("11");add("12");add("13");add("14");add("15");add("16");add("17");
		add("18");add("19");add("20");add("21");add("22");add("23");add("24");add("25");add("26");add("27");add("28");}};
    	
    	Date firstDayOfThisMonth = DateTimeUtil.formatSelf(tm+"-01", "yyyy-MM-dd");
    	Date firstDayOfNextMonth = DateTimeUtil.addMonth(firstDayOfThisMonth, 1);
    	int day = DateTimeUtil.dateSubtraction(firstDayOfNextMonth,firstDayOfThisMonth);
    	
    	switch(day) {
    		case 28:
    			break;
    		case 29:
    			dayList.add("29");
    			break;
    		case 30:
    			dayList.add("29");
    			dayList.add("30");
    			break;
    		case 31:
    			dayList.add("29");
    			dayList.add("30");
    			dayList.add("31");
    			break;
    	}
    	
    	Map<String, Object> dataMap = new HashMap<String, Object>(dataList.size());
    	for (Object[] data : dataList) {
			dataMap.put((data[0] == null ? "" : data[0])+"-"+(data[1] == null ? "" : data[1]), data[2]);
		}
		
    	Date now = new Date();
    	Integer thisMonthDayUntilNow = tm.equals(DateTimeUtil.formatSelf(now, "yyyy-MM")) ? DateTimeUtil.dateSubtraction(now,firstDayOfThisMonth)+1 : day;
		for (Map<String, Object> map : enterpriseList) {
			int i = 0;
			for (String d : dayList) {
				BigDecimal orderNum = BigDecimal.ZERO;
				try {
					orderNum = new BigDecimal(dataMap.get(map.get("id")+"-"+tm+"-"+d).toString());
				} catch (Exception e) {
					// TODO: handle exception
				}
				map.put("inNum"+d, orderNum.intValue());
				if(orderNum.compareTo(BigDecimal.ZERO) == 0) {
					i++;
				}
			}
			map.put("jhDay", day-i);
			map.put("wjhDay", thisMonthDayUntilNow-(day-i));
			map.put("utilizationRate", new BigDecimal(day-i).divide(new BigDecimal(thisMonthDayUntilNow),4,BigDecimal.ROUND_HALF_UP).
					multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP)+"%");
		}
		
		Collections.sort(enterpriseList,new Comparator<Map<String, Object>>() {

			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				// TODO Auto-generated method stub
				return new BigDecimal(o2.get("utilizationRate").toString().split("%")[0]).compareTo(new BigDecimal(o1.get("utilizationRate").toString().split("%")[0]));
			}
        });
		String isExport = myParam.getIsExport();
		String fileName = "";
		if(!StringUtils.isEmpty(isExport)&&"1".equals(isExport)) {
			fileName = export(dayList,enterpriseList,tm);
		}
		res.add("status", 1).add("msg", "操作成功").add("result", enterpriseList).add("path", "/export/"+fileName);
		dataMap = null;
		dayList = null;
		return true;
	}
	
	private String export(List<String> dayList,List<Map<String, Object>> enterpriseList,String tm)  throws Exception{
		
		String templatePath = this.getClass().getResource("/").getPath()+"../../export";
		File file = new File(templatePath);
		if (!file.exists()) 
			file.mkdir(); 
		
		String fileName = DateTimeUtil.formatSelf(new Date(), "yyyyMMddHHmmssSSS")+".xls";
		String filePath = templatePath + File.separator + fileName;
		OutputStream os = new FileOutputStream(filePath);
		
		/*创建工作簿对象*/
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFFont headerFont = workbook.createFont();    
		headerFont.setFontName("宋体");    
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体显示    
		headerFont.setFontHeightInPoints((short) 9);
		
		HSSFFont normalFont = workbook.createFont();    
		normalFont.setFontName("宋体");    
		normalFont.setFontHeightInPoints((short) 9);
		
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(headerFont);
		headerStyle.setBorderTop(CellStyle.BORDER_DOTTED);
		headerStyle.setBorderRight(CellStyle.BORDER_DOTTED);
		headerStyle.setBorderBottom(CellStyle.BORDER_DOTTED);
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setFont(normalFont);
		contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
		contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		contentStyle.setBorderBottom(CellStyle.BORDER_DOTTED);
		contentStyle.setBorderRight(CellStyle.BORDER_DOTTED);
		
		HSSFCellStyle lockStyle = workbook.createCellStyle();
		lockStyle.setFont(normalFont);
		lockStyle.setAlignment(CellStyle.ALIGN_CENTER);
		lockStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		lockStyle.setBorderBottom(CellStyle.BORDER_DOTTED);
		lockStyle.setBorderRight(CellStyle.BORDER_DOTTED);
		lockStyle.setWrapText(true);
		lockStyle.setLocked(true);
		
			/* 创建一个sheet */
			HSSFSheet sheet = workbook.createSheet(tm+"入库情况");
			sheet.setColumnWidth(1, 30*256);
			HSSFRow row0 = sheet.createRow(0);
			String[] headers = new String[] {"序号","名称"};
			for(int i=0;i<headers.length;i++) {
				HSSFCell headerCell = row0.createCell(i);
				headerCell.setCellValue(headers[i]);
				headerCell.setCellStyle(headerStyle);
			}
			for (int i=0;i<dayList.size();i++) {
				HSSFCell headerCell = row0.createCell(i+1+1);
				headerCell.setCellValue(Integer.valueOf(dayList.get(i)));
				headerCell.setCellStyle(headerStyle);
			}
			String[] ends = new String[] {"进货（天）","未进货（天）"};
			for(int i=0;i<ends.length;i++) {
				HSSFCell headerCell = row0.createCell(i+dayList.size()+1+1);
				headerCell.setCellValue(ends[i]);
				headerCell.setCellStyle(headerStyle);
				sheet.setColumnWidth(i+dayList.size()+1+1, 15*256);
			}
			for (int j=0;j<enterpriseList.size();j++) {
				Map<String, Object> enterprise = enterpriseList.get(j);
				HSSFRow row = sheet.createRow(j+1);
				
				HSSFCell endCell9 = row.createCell(0);
				endCell9.setCellValue(j+1);
				endCell9.setCellStyle(lockStyle);
				
				HSSFCell endCell0 = row.createCell(1);
				endCell0.setCellValue(enterprise.get("name")+"");
				endCell0.setCellStyle(lockStyle);
				for (int i=0;i<dayList.size();i++) {
					HSSFCell headerCell = row.createCell(i+1+1);
					headerCell.setCellValue(enterprise.get("inNum"+dayList.get(i))+"");
					headerCell.setCellStyle(lockStyle);
				}
				HSSFCell endCell1 = row.createCell(dayList.size()+1+1);
				HSSFCell endCell2 = row.createCell(dayList.size()+1+1+1);
				endCell1.setCellValue(enterprise.get("jhDay")+"");
				endCell2.setCellValue(enterprise.get("wjhDay")+"");
				endCell1.setCellStyle(lockStyle);
				endCell2.setCellStyle(lockStyle);
			}
		
			workbook.write(os);
			os.close();
		return fileName;
	}
	
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StatisticInParam myParam = (StatisticInParam)logicParam;
		String tm = myParam.getTm();
		if(StringUtils.isEmpty(tm)) {
			res.add("status", -1).add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
}
