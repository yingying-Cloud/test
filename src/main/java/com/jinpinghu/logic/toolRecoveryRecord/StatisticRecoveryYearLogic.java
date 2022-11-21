package com.jinpinghu.logic.toolRecoveryRecord;

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

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.common.tools.PoiUtil;
import com.jinpinghu.db.bean.TbArea;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.dao.TbAreaDao;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbToolRecoveryRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecoveryRecord.param.StatisticBuyZeroYearParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StatisticRecoveryYearLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		StatisticBuyZeroYearParam myParam = (StatisticBuyZeroYearParam)logicParam;
		String enterpriseName = myParam.getEnterpriseName();
		Integer year= StringUtils.isEmpty(myParam.getYear())?null:Integer.valueOf(myParam.getYear());
		String dscd = myParam.getDscd();
		String selectAll = myParam.getSelectAll();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		Date nowDate = new Date();
		TbToolRecoveryRecordDao recordDao2 = new TbToolRecoveryRecordDao(em);
		JSONArray ja = new JSONArray();
		TbAreaDao tbAreaDao = new TbAreaDao(em);
		TbEnterpriseDao tbEnterpriseDao = new TbEnterpriseDao(em);
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		List<Map<String, Object>> list = recordDao2.getYear(enterpriseName,dscd,selectAll,permissionEnterpriseIdList,year+"",nowPage,pageCount);
		Integer count = tbEnterpriseDao.findAllZeroCount(enterpriseName, dscd, selectAll, permissionEnterpriseIdList);
		
		
		List<Map<String, Object>> sellMonth = recordDao2.getRecoveryMonth(year+"", year+"");
    	Map<String, Object> dataMap = new HashMap<String, Object>(sellMonth == null?0:sellMonth.size());
		if(sellMonth!=null) {
	    	for (Map<String, Object> data : sellMonth) {
				dataMap.put((data.get("inputTime") == null ? "" : data.get("inputTime"))+"-"+(data.get("enterpriseId") == null ? "" : data.get("enterpriseId")), data);
			}
		}
		
		if(list!=null) {
			for(Map<String, Object> te:list) {
				JSONObject jo = new JSONObject();
				jo.put("enterpriseName", te.get("enterpriseName"));
				jo.put("dscdName",te.get("dscdName"));
				jo.put("recoveryZeroNum",  te.get("recoveryZeroNum"));
				jo.put("recoveryZeroMoney", te.get("recoveryZeroMoney"));
				jo.put("recoveryNum",te.get("recoveryNum"));
				jo.put("recoveryMoney", te.get("recoveryMoney"));
				String month = year+"-01";
				String userYear = "";
				if(year<DateTimeUtil.getYear(nowDate)) {
					userYear =year+"-12";
				}else {
					userYear = DateTimeUtil.formatTime6(nowDate);
				}
				
				for(int i=1;(DateTimeUtil.formatTime6(month)).getTime()<=(DateTimeUtil.formatTime6(userYear)).getTime();) {
//					jo.put("value"+i, dataMap.get(month+"-"+te.getId()));
//					jo.put(month+"", dataMap.containsKey(month+"-"+te.getId())?dataMap.get(month+"-"+te.getId()):0);
					
					Map<String, Object> data = dataMap.containsKey(month+"-"+Integer.valueOf(te.get("id").toString()))?(Map<String, Object>)dataMap.get(month+"-"+Integer.valueOf(te.get("id").toString())):null;
					jo.put("recoveryZeroNum"+i,  data==null?"":data.get("recoveryZeroNum"));
					jo.put("recoveryZeroMoney"+i, data==null?"":data.get("recoveryZeroMoney"));
					jo.put("recoveryNum"+i,data==null?"":data.get("recoveryNum"));
					jo.put("recoveryMoney"+i, data==null?"":data.get("recoveryMoney"));
					
					month = DateTimeUtil.addMonth(month, 1);
					i++;
				}
//				JSONArray inJa = new JSONArray();
//				for(int i=1;(DateTimeUtil.formatTime6(month)).getTime()<=(DateTimeUtil.formatTime6(userYear)).getTime();) {
//					JSONObject inJo = new JSONObject();
//					
////					inJo.putAll(sellMonth);
////					inJo.put("name", month);
////					inJa.add(inJo);
//					jo.put("recoveryZeroNum"+i, sellMonth[0]);
//					jo.put("recoveryZeroMoney"+i, sellMonth[1]);
//					jo.put("recoveryNum"+i, sellMonth[2]);
//					jo.put("recoveryMoney"+i, sellMonth[3]);
//					month = DateTimeUtil.addMonth(month, 1);
//					i++;
//				}
//				jo.put("year", inJa);
				ja.add(jo);
			}
		}
		String isExport = myParam.getIsExport();
		String fileName = "";
		if(!StringUtils.isEmpty(isExport)&&"1".equals(isExport)) {
			fileName = export(ja,year,list);
		}
		res.add("path", "/export/"+fileName);
		res.add("status", 1).add("msg", "操作成功");res.add("result", ja).add("allCounts", count);
		return true;
	}

	
	private String export(JSONArray dataList,Integer year,List<Map<String, Object>> list)  throws Exception{
		
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
		HSSFSheet targetSheet = targetWork.createSheet(year+"补贴统计-废弃物回收统计");
		
		//创建输入流，读取模板文件
		InputStream in = new FileInputStream(TemplateFilePath + "/zeroStatistic.xls");
        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
		HSSFSheet sourceSheet = sourceWork.getSheet("补贴统计-废弃物回收统计");
		
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
//		cs.setWrapText(true);
		
		targetSheet.setColumnWidth(0, 40 * 256);
		targetSheet.setColumnWidth(1, 20 * 256);
		
		int beginRow = 3;
		targetSheet.createRow(beginRow);
		
		for (int j = 0; j < dataList.size(); j++) {
			JSONObject enterprise = (JSONObject) dataList.get(j);
			HSSFRow row = targetSheet.getRow(beginRow);
			
			
			Date nowDate = new Date();
			String month = year+"-01";
			String userYear = "";
			if(year<DateTimeUtil.getYear(nowDate)) {
				userYear =year+"-12";
			}else {
				userYear = DateTimeUtil.formatTime6(nowDate);
			}
			Integer cellCount = 0;
			
			HSSFCell createCel = row.createCell(cellCount++);
			createCel.setCellValue(enterprise.containsKey("enterpriseName")?enterprise.get("enterpriseName")+"":"");
			createCel.setCellStyle(cs);
			HSSFCell createCell0 = row.createCell(cellCount++);
			createCell0.setCellValue(enterprise.containsKey("dscdName")?enterprise.get("dscdName")+"":"");
			createCell0.setCellStyle(cs);
			
			HSSFCell createCell = row.createCell(cellCount++);
			HSSFCell createCell2 = row.createCell(cellCount++);
			HSSFCell createCell3 = row.createCell(cellCount++);
			HSSFCell createCell4 = row.createCell(cellCount++);
			createCell.setCellValue(enterprise.containsKey("recoveryZeroNum")?enterprise.get("recoveryZeroNum")+"":"0");
			createCell2.setCellValue(enterprise.containsKey("recoveryZeroMoney")?enterprise.get("recoveryZeroMoney")+"":"0");
			createCell3.setCellValue(enterprise.containsKey("recoveryNum")?enterprise.get("recoveryNum")+"":"0");
			createCell4.setCellValue(enterprise.containsKey("recoveryMoney")?enterprise.get("recoveryMoney")+"":"0");
			createCell.setCellStyle(cs);
			createCell2.setCellStyle(cs);
			createCell3.setCellStyle(cs);
			createCell4.setCellStyle(cs);
			
			for(int i=1;(DateTimeUtil.formatTime6(month)).getTime()<=(DateTimeUtil.formatTime6(userYear)).getTime();) {
				HSSFCell createCell5 = row.createCell(cellCount++);
				HSSFCell createCell6 = row.createCell(cellCount++);
				HSSFCell createCell7 = row.createCell(cellCount++);
				HSSFCell createCell8 = row.createCell(cellCount++);
				createCell5.setCellValue(enterprise.containsKey("recoveryZeroNum"+i)?enterprise.get("recoveryZeroNum"+i)+"":"0");
				createCell6.setCellValue(enterprise.containsKey("recoveryZeroMoney"+i)?enterprise.get("recoveryZeroMoney"+i)+"":"0");
				createCell7.setCellValue(enterprise.containsKey("recoveryNum"+i)?enterprise.get("recoveryNum"+i)+"":"0");
				createCell8.setCellValue(enterprise.containsKey("recoveryMoney"+i)?enterprise.get("recoveryMoney"+i)+"":"0");
				
				createCell5.setCellStyle(cs);
				createCell6.setCellStyle(cs);
				createCell7.setCellStyle(cs);
				createCell8.setCellStyle(cs);
				
				
				month = DateTimeUtil.addMonth(month, 1);
				i++;
			}
			
			
			targetSheet.createRow(++beginRow);
			
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
