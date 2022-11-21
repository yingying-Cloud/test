package com.jinpinghu.logic.export;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.PoiUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandOrder.param.ExportByEnterpriseParam;
import com.jinpinghu.logic.order.param.ListAllOrderParam;
import com.mysql.cj.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExportByToolOrderLogic2 extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		ListAllOrderParam myParam =(ListAllOrderParam)logicParam;
		TbToolOrderDao toolOrderDao = new TbToolOrderDao(em);
		JSONArray ja = new JSONArray();
		
		List<Object[]> syncList = toolOrderDao.findAllIsSync();
		if(syncList!=null) {
			for(Object[] te:syncList) {
				JSONObject param = new JSONObject();
				param.put("location",te[0]+"");
				param.put("enterpriseNum",te[1]+"");
				param.put("name",te[8]+";"+te[2]+"");
				param.put("num",Integer.valueOf(te[6]+""));
				param.put("numUtil","件");
				param.put("orderNumber",te[3]+"");
				param.put("orderTime",te[5]+"");
				param.put("price",te[4]+"");
				List<JSONObject> list = toolOrderDao.findInOrderId(Integer.valueOf(te[7].toString()));
				param.put("detail",list);
				ja.add(param);
			}
		}
		String fileName = "";
//		if("1".equals(isExport))
		fileName = export(ja);
		res.add("status", 1).add("msg", "操作成功").add("path", "/export/"+fileName);
		return true;
	}



	public String export(JSONArray dataList) throws Exception {
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
		HSSFSheet targetSheet = targetWork.createSheet("订单汇总");

		//创建输入流，读取模板文件
		InputStream in = new FileInputStream(TemplateFilePath + "/allToolOrder.xls");
        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
		HSSFSheet sourceSheet = sourceWork.getSheet("订单汇总");

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
		for (int i = 0; i < dataList.size(); i++) {
			JSONObject orderMap = (JSONObject) dataList.get(i);
			@SuppressWarnings("unchecked")
			List<JSONObject> orderInfoList = (List<JSONObject>) orderMap.get("detail");
			for(int j = beginRow;j <= beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1));j++) {
				HSSFRow r = targetSheet.createRow(j);
				for (int j2 = 0; j2 < 12 ; j2++) {
					r.createCell(j2).setCellStyle(cs);;

				}
			}
			HSSFRow tempRow = targetSheet.getRow(beginRow);
			HSSFCell cell0 = tempRow.getCell(0);
			cell0.setCellValue(orderMap.get("location")+"");
			CellRangeAddress nameCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),0,0);
			targetSheet.addMergedRegion(nameCra);
			cell0.setCellStyle(cs);

			HSSFCell cell1 = tempRow.getCell(1);
			cell1.setCellValue(orderMap.get("enterpriseNum")+"");
			CellRangeAddress timeCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),1,1);
			targetSheet.addMergedRegion(timeCra);
			cell1.setCellStyle(cs);

			HSSFCell cell2 = tempRow.getCell(2);
			cell2.setCellValue(orderMap.get("name")+"");
			CellRangeAddress orderNumberCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),2,2);
			targetSheet.addMergedRegion(orderNumberCra);
			cell2.setCellStyle(cs);

			HSSFCell cell3 = tempRow.getCell(3);
			cell3.setCellValue(orderMap.containsKey("num")?orderMap.get("num")+"":"");
			CellRangeAddress nicknameCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),3,3);
			targetSheet.addMergedRegion(nicknameCra);
			cell3.setCellStyle(cs);

			HSSFCell cell4 = tempRow.getCell(4);
			cell4.setCellValue(orderMap.containsKey("numUtil")?orderMap.get("numUtil")+"":"");
			CellRangeAddress cra4=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),4,4);
			targetSheet.addMergedRegion(cra4);
			cell4.setCellStyle(cs);

			HSSFCell cell5 = tempRow.getCell(5);
			cell5.setCellValue(orderMap.get("orderNumber")+"");
			CellRangeAddress cra5=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),5,5);
			targetSheet.addMergedRegion(cra5);
			cell5.setCellStyle(cs);
			
			HSSFCell cell6 = tempRow.getCell(6);
			cell6.setCellValue(orderMap.get("orderTime")+"");
			CellRangeAddress cra6=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),6,6);
			targetSheet.addMergedRegion(cra6);
			cell6.setCellStyle(cs);
			
			HSSFCell cell7 = tempRow.getCell(7);
			cell7.setCellValue(orderMap.get("price")+"");
			CellRangeAddress cra7=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),7,7);
			targetSheet.addMergedRegion(cra7);
			cell7.setCellStyle(cs);

//			HSSFCell cell6 = tempRow.getCell(6);
//			cell6.setCellValue((String)orderMap.get("price"));
//			CellRangeAddress cra6=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),6,6);
//			targetSheet.addMergedRegion(cra6);
//			cell6.setCellStyle(cs);
			
			for (int j = 0; j < orderInfoList.size(); j++) {
				Map<String, Object>	orderInfoMap = orderInfoList.get(j);
				HSSFRow row = targetSheet.getRow(beginRow);
//				HSSFCell cell7 = row.getCell(7);
//				cell7.setCellValue((String)orderInfoMap.get("brandName"));
//				cell7.setCellStyle(cs);

				HSSFCell cell8 = row.getCell(8);
				cell8.setCellValue(orderInfoMap.containsKey("num")? orderInfoMap.get("num")+"":"");
				cell8.setCellStyle(cs);

				HSSFCell cell9 = row.getCell(9);
				cell9.setCellValue(orderInfoMap.containsKey("unitPrice")?orderInfoMap.get("unitPrice")+"":"");
				cell9.setCellStyle(cs);

                HSSFCell cell10 = row.getCell(10);
                cell10.setCellValue(orderInfoMap.containsKey("productNum")?orderInfoMap.get("productNum")+"":"");
                cell10.setCellStyle(cs);
                
                HSSFCell cell11 = row.getCell(11);
                cell11.setCellValue(orderInfoMap.containsKey("numUnit")?orderInfoMap.get("numUnit")+"":"");
                cell11.setCellStyle(cs);
                
				++beginRow;
			}

		}
		//复制模板文件列宽
		for (int i = 0; i <= maxColumnNum; i++){
			targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
		}

		targetSheet.setColumnWidth(2, 25*255);
		targetSheet.setColumnWidth(3, 25*255);

		in.close();
		// 创建输出流，并输出文件
		OutputStream os = new FileOutputStream(filePath);
		//OutputStream os = new FileOutputStream("F:/test/"+fileName);
		targetWork.write(os);
		os.close();
		return fileName;
	}
}
