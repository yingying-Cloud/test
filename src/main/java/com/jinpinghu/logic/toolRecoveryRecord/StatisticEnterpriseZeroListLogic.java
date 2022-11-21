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
import com.jinpinghu.db.dao.TbToolRecoveryRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecoveryRecord.param.GetToolRecoveryRecordListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StatisticEnterpriseZeroListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolRecoveryRecordListParam myParam = (GetToolRecoveryRecordListParam)logicParam;
		
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String enterpriseName = myParam.getEnterpriseName();
		String dscd = myParam.getDscd();
		String selectAll = myParam.getSelectAll();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String enterpriseType = myParam.getEnterpriseType();
		
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		TbToolRecoveryRecordDao recordDao2 = new TbToolRecoveryRecordDao(em);
		List<Map<String, Object>> list = recordDao2.getEnterpriseZeroMoney(enterpriseName, nowPage, pageCount, enterpriseId, selectAll, 
				permissionEnterpriseIdList,startTime,endTime,dscd,enterpriseType);
		Integer count = recordDao2.getEnterpriseZeroMoneyCount(enterpriseName, nowPage, pageCount, enterpriseId, selectAll, 
				permissionEnterpriseIdList, startTime, endTime, dscd,enterpriseType);
		String isExport = myParam.getIsExport();
		String fileName = "";
		if(!StringUtils.isEmpty(isExport)&&"1".equals(isExport)) {
			fileName = export(list);
		}
		res.add("path", "/export/"+fileName);
		res.add("status", 1).add("msg", "操作成功").add("result", list).add("allCounts", count);
		return true;
	}
private String export(List<Map<String, Object>> dataList)  throws Exception{
		
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
		HSSFSheet targetSheet = targetWork.createSheet("预警统计-零差价农药统计");
		
		//创建输入流，读取模板文件
		InputStream in = new FileInputStream(TemplateFilePath + "/zeroStatistic.xls");
        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
		HSSFSheet sourceSheet = sourceWork.getSheet("预警统计-零差价农药统计");
		
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
		
		int beginRow = 2;
		targetSheet.createRow(beginRow);
			
		for (int j = 0; j < dataList.size(); j++) {
			Map<String,Object> enterprise =  dataList.get(j);
			HSSFRow row = targetSheet.getRow(beginRow);
			
			HSSFCell createCell = row.createCell(0);
			HSSFCell createCell1 = row.createCell(1);
			HSSFCell createCell2 = row.createCell(2);
			HSSFCell createCell3 = row.createCell(3);
			HSSFCell createCell4 = row.createCell(4);
			HSSFCell createCell5 = row.createCell(5);
			createCell.setCellValue(enterprise.containsKey("name")?enterprise.get("name")+"":"");
			createCell1.setCellValue(enterprise.containsKey("dscdName")?enterprise.get("dscdName")+"":"");
			createCell2.setCellValue(enterprise.containsKey("village")?enterprise.get("village")+"":"");
			createCell3.setCellValue(enterprise.containsKey("area")?enterprise.get("area")+"":"");
			createCell4.setCellValue(enterprise.containsKey("zeroMoney")?enterprise.get("zeroMoney")+"":"");
			createCell5.setCellValue(enterprise.containsKey("buyZeroMoney")?enterprise.get("buyZeroMoney")+"":"");
			
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
