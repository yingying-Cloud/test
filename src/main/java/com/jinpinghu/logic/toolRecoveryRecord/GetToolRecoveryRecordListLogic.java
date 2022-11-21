package com.jinpinghu.logic.toolRecoveryRecord;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbToolRecoveryRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecoveryRecord.param.GetToolRecoveryRecordListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetToolRecoveryRecordListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolRecoveryRecordListParam myParam = (GetToolRecoveryRecordListParam)logicParam;
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String name =myParam.getName();
		//Integer toolRecoveryId = StringUtils.isEmpty(myParam.getToolRecoveryId())?null:Integer.valueOf(myParam.getToolRecoveryId());
		List<String> toolRecoveryIdList = StringUtil.isNullOrEmpty(myParam.getToolRecoveryId()) ? null : Arrays.asList(myParam.getToolRecoveryId().split(","));
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String useName = myParam.getUseName();
		String enterpriseName = myParam.getEnterpriseName();
		String toolName = myParam.getToolName();
		Integer linkOrderInfoId =  StringUtils.isEmpty(myParam.getLinkOrderInfoId())?null:Integer.valueOf(myParam.getLinkOrderInfoId());
		String recordNumber = myParam.getRecordNumber();
		String selectAll = myParam.getSelectAll();
		String dscd = myParam.getDscd();
		if(!StringUtil.isNullOrEmpty(dscd)){
			if(dscd.substring(2, 4).equals("00")){
				dscd = dscd.substring(0,2)+"%";
			}else if(dscd.substring(4, 6).equals("00")){
				dscd = dscd.substring(0,4)+"%";
			}else if(dscd.substring(6, 9).equals("000")){
				dscd = dscd.substring(0,6)+"%";
			}else if(dscd.substring(9, 12).equals("000")){
				dscd = dscd.substring(0,9)+"%";
			}
		}
		
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		
		JSONArray ja = new JSONArray();
		TbToolRecoveryRecordDao recordDao2 = new TbToolRecoveryRecordDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		
		Integer count = recordDao2.getToolRecoveryRecordListCount(enterpriseId, toolRecoveryIdList, name,startTime,endTime,
				useName,enterpriseName,toolName,linkOrderInfoId,recordNumber,selectAll,permissionEnterpriseIdList,dscd);
//		if(count==0||count==null){
//			res.add("status", 1);
//			res.add("msg", "无数据！");
//			return true;
//		}
		List<Object[]> list = recordDao2.getToolRecoveryRecordList( enterpriseId, toolRecoveryIdList, name, nowPage, pageCount,startTime,
				endTime,useName,enterpriseName,toolName,linkOrderInfoId,recordNumber,selectAll,permissionEnterpriseIdList,dscd);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo = new JSONObject();
				jo.put("id", o[0]);
				jo.put("enterpriseName", o[1]);
				jo.put("toolRecoveryName", o[2]);
				jo.put("allNumber", o[3]);
				jo.put("number", o[4]);
				jo.put("fileUrl", o[5]);
				jo.put("useName", o[6]);
				jo.put("inputTime", o[7]);
				jo.put("unit", o[8]);
				jo.put("useMobile", o[9]);
				jo.put("typeName", o[10]);
				jo.put("totalPrice", o[11]);
				jo.put("toolName", o[12]);
				jo.put("price", o[13]);
				jo.put("recordNumber", o[14]);
				jo.put("linkOrderInfoId", o[15]);
				jo.put("idcard", o[16]);
				jo.put("idcardPic", o[17]);
				jo.put("recoveryEnterpriseName", o[18]);
				jo.put("money", o[19]);
				List<Map<String, Object>> file = tfDao.findMapByToolRecoveryRecordId(Integer.valueOf(o[0].toString()));
				jo.put("file", file);
				ja.add(jo);
			}
		}
		String isExport=myParam.getIsExport();
		String fileName = "";
		if(!StringUtils.isEmpty(isExport)&&isExport.equals("1")) {
			List<Map<String,Object>> allToolRecovery = recordDao2.statisticAllToolRecovery(enterpriseId, toolRecoveryIdList, name, startTime, endTime, 
					useName, enterpriseName, toolName, linkOrderInfoId, recordNumber, selectAll, permissionEnterpriseIdList,dscd);
			
			fileName= export(ja, allToolRecovery, startTime, endTime);
		}
		res.add("status", 1).add("msg", "操作成功").add("result", ja).add("allCounts", count);
		res.add("path", "/export/"+fileName);
		return true;
	}
	
	public String export(JSONArray dataList, List<Map<String,Object>> allToolRecovery, String startTime ,String endTime) throws Exception {
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
		HSSFSheet targetSheet = targetWork.createSheet("回收流水");
		
		//创建输入流，读取模板文件
		InputStream in = new FileInputStream(TemplateFilePath + "/recoveryRecord.xls");
        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
		HSSFSheet sourceSheet = sourceWork.getSheet("回收流水");
		
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
		
		HSSFCell headCell = targetSheet.getRow(0).getCell(0);
		headCell.setCellValue(startTime+"至"+endTime+headCell.getStringCellValue());

		
		int beginRow = 2;
		targetSheet.createRow(beginRow);
		
		for(int i = 0; i < allToolRecovery.size(); i++) {
			Map<String,Object> orderMap = (Map<String,Object>) allToolRecovery.get(i);
			HSSFRow row = targetSheet.createRow(beginRow++);
			
			HSSFCell cell6 = row.createCell(0);
			cell6.setCellValue((String)orderMap.get("name"));
			cell6.setCellStyle(cs);
			
			HSSFCell cell0 = row.createCell(1);
			cell0.setCellValue(orderMap.get("number")+""+orderMap.get("unit"));
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(2);
			cell1.setCellValue(orderMap.get("price")+"");
			cell1.setCellStyle(cs);
			
//			targetSheet.createRow(++beginRow);
		}

		HSSFCell head2Cell = targetSheet.getRow(beginRow).getCell(0);
		head2Cell.setCellValue(startTime+"至"+endTime+head2Cell.getStringCellValue());
		
		beginRow+=2;
		targetSheet.createRow(beginRow);
		for (int i = 0; i < dataList.size(); i++) {
			JSONObject orderMap = (JSONObject) dataList.get(i);
			HSSFRow row = targetSheet.getRow(beginRow);
			
			HSSFCell cell7 = row.createCell(2);
			cell7.setCellValue((String)orderMap.get("recoveryEnterpriseName"));
			cell7.setCellStyle(cs);
			
			HSSFCell cell6 = row.createCell(0);
			cell6.setCellValue((String)orderMap.get("recordNumber"));
			cell6.setCellStyle(cs);
			
			HSSFCell cell8 = row.createCell(1);
			cell8.setCellValue((String)orderMap.get("enterpriseName"));
			cell8.setCellStyle(cs);
			
			HSSFCell cell0 = row.createCell(3);
			cell0.setCellValue((String)orderMap.get("useName"));
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(4);
			cell1.setCellValue((String)orderMap.get("toolRecoveryName"));
			cell1.setCellStyle(cs);
			
			HSSFCell cell2 = row.createCell(5);
			cell2.setCellValue((String)orderMap.get("number"));
			cell2.setCellStyle(cs);
			
			HSSFCell cell3 = row.createCell(6);
			cell3.setCellValue((String)orderMap.get("price"));
			cell3.setCellStyle(cs);
			
			HSSFCell cell4 = row.createCell(7);
			cell4.setCellValue(orderMap.get("money")+"");
			cell4.setCellStyle(cs);
			
			HSSFCell cell5 = row.createCell(8);
			cell5.setCellValue((String)orderMap.get("inputTime"));
			cell5.setCellStyle(cs);
			
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
