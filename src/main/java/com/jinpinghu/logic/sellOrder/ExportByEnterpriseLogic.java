//package com.jinpinghu.logic.sellOrder;
//
//import com.jinpinghu.common.tools.DateTimeUtil;
//import com.jinpinghu.common.tools.PoiUtil;
//import com.jinpinghu.db.dao.TbBrandDao;
//import com.jinpinghu.db.dao.TbSellBrandDao;
//import com.jinpinghu.logic.BaseZLogic;
//import com.jinpinghu.logic.sellOrder.param.ExportByEnterpriseParam;
//import fw.jbiz.ext.json.ZSimpleJsonObject;
//import fw.jbiz.logic.ZLogicParam;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.util.CellRangeAddress;
//
//import javax.persistence.EntityManager;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URLDecoder;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class ExportByEnterpriseLogic extends BaseZLogic {
//
//	@Override
//	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
//		// TODO Auto-generated method stub
//        ExportByEnterpriseParam myParam = (ExportByEnterpriseParam)logicParam;
//		String enterpriseName = myParam.getEnterpriseName();
//		String startTime = myParam.getStartTime();
//		String endTime = myParam.getEndTime();
//		String isExport = myParam.getIsExport();
//
////		if("1".equals(isExport)) {
////			String fileName = export2(wholesalersId, startTime, endTime, em);
////			res.add("status", 1).add("msg", "操作成功").add("path", "/export/"+fileName);
////			return true;
////		}
//		TbSellBrandDao tbSellDao = new TbSellBrandDao(em);
//		List<Object[]> orderList = tbSellDao.findAllOrder(startTime,endTime,enterpriseName);
//		ArrayList<Map<String, Object>> orderResultList = new ArrayList<Map<String, Object>>();
//		Map<String, Object> orderResultMap;
//		ArrayList<Map<String, Object>> orderInfoResultList;
//		Map<String, Object> orderInfoResultMap;
//		if(orderList != null) {
//			for (int i = 0; i < orderList.size(); i++) {
//				orderResultMap = new HashMap<String,Object>();
//				Object[] order = orderList.get(i);
//				orderResultMap.put("enterpriseName", order[0] == null ? "" : order[0].toString());
//				orderResultMap.put("inputTime", order[1] == null ? "" : DateTimeUtil.formatSelf((Date)order[1],"yyyy-MM-dd HH:mm:ss"));
//				orderResultMap.put("orderNumber", order[2] == null ? "" : order[2].toString());
//				orderResultMap.put("enterpriseLinkMobile", order[3] == null ? "" :order[3].toString() );
//				orderResultMap.put("enterpriseLinkPeople", order[4] == null ? "" : order[4].toString());
//				orderResultMap.put("price", order[5] == null ? "" : order[5].toString());
//                orderResultMap.put("trademarkId", order[6] == null ? "" : order[6].toString());
//				orderResultMap.put("sellName", order[7] == null ? "" : order[7].toString());
//
//				List<Object[]> orderInfoList = tbSellDao.findOrderInfo(Integer.valueOf(order[6].toString()));
//				orderInfoResultList = new ArrayList<Map<String, Object>>();
//				if(orderInfoList != null) {
//					for (int j = 0; j < orderInfoList.size(); j++) {
//						Object[] orderInfo = orderInfoList.get(j);
//						orderInfoResultMap = new HashMap<String,Object>();
//						orderInfoResultMap.put("sellName", orderInfo[0]==null?"":orderInfo[0].toString());
//						orderInfoResultMap.put("productName", orderInfo[1]==null?"":orderInfo[1].toString());
//						orderInfoResultMap.put("num", orderInfo[2]==null?"":orderInfo[2].toString());
//                        orderInfoResultMap.put("unit", orderInfo[3]==null?"":orderInfo[3].toString());
//                        orderInfoResultMap.put("price", orderInfo[4]==null?"":orderInfo[4].toString());
//						orderInfoResultList.add(orderInfoResultMap);
//					}
//				}
//				orderResultMap.put("orderInfo", orderInfoResultList);
//				orderResultList.add(orderResultMap);
//			}
//		}
//
//		String fileName = "";
//		if("1".equals(isExport)) {
//			fileName = export(orderResultList);
//		}
//
//		res.add("status", 1).add("msg", "操作成功").add("result", orderResultList).add("path", "/export/"+fileName);
//		return true;
//	}
//
//
//
//	public String export(List<Map<String, Object>> dataList) throws Exception {
//		String docsPath = this.getClass().getResource("/").getPath() + "../../export";
//		docsPath = URLDecoder.decode(docsPath,"utf-8");
//		String TemplateFilePath = docsPath;
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
//		String fileName = sdf.format(new Date()) + ".xls";
//		String filePath = TemplateFilePath + "/" + fileName;
//
//		// 创建新表格并写入内容
//		HSSFWorkbook targetWork = new HSSFWorkbook();
//		Map<String, HSSFCellStyle> styleMap = new HashMap<String, HSSFCellStyle>();
//		//导出模块
//		HSSFSheet targetSheet = targetWork.createSheet("店铺订单汇总");
//
//		//创建输入流，读取模板文件
//		InputStream in = new FileInputStream(TemplateFilePath + "/enterpriseOrder.xls");
//        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
//		HSSFSheet sourceSheet = sourceWork.getSheet("店铺订单汇总");
//
//		//计算模板文件首位行
//		int fistRowNum = sourceSheet.getFirstRowNum();
//		int lastRowNum = sourceSheet.getLastRowNum();
//		int maxColumnNum = 0;
//
//		//用于复制注释
//		HSSFPatriarch patriarch = targetSheet.createDrawingPatriarch();
//
//		//逐行复制模板文件单元格
//		for(int j = fistRowNum ; j <= lastRowNum ; j++){
//			HSSFRow sourceRow = sourceSheet.getRow(j);
//			HSSFRow targetRow = targetSheet.createRow(j);
//			PoiUtil.copyRow(targetRow, sourceRow, targetWork, sourceWork, patriarch, styleMap);
//			if (targetRow.getLastCellNum() > maxColumnNum) {
//                maxColumnNum = targetRow.getLastCellNum();
//            }
//		}
//
//		//复制模板文件合并单元格
//		for (int k = 0; k < sourceSheet.getNumMergedRegions(); k++) {
//            CellRangeAddress oldRange = sourceSheet.getMergedRegion(k);
//            CellRangeAddress newRange = new CellRangeAddress(
//	            oldRange.getFirstRow(), oldRange.getLastRow(),
//	            oldRange.getFirstColumn(), oldRange.getLastColumn()
//            );
//            targetSheet.addMergedRegion(newRange);
//        }
//
//		if(dataList == null || dataList.size()<=0){
//			in.close();
//			// 创建输出流，并输出文件
//			OutputStream os = new FileOutputStream(filePath);
//			//OutputStream os = new FileOutputStream("F:/test/"+fileName);
//			targetWork.write(os);
//			os.close();
//			return fileName;
//		}
//
//		//设置单元格居中
//		HSSFCellStyle cs = targetWork.createCellStyle();
//		cs.setAlignment(CellStyle.ALIGN_CENTER);
//		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		 /*设置边框*/
//		cs.setBorderBottom(CellStyle.BORDER_THIN);//下边框    
//		cs.setBorderLeft(CellStyle.BORDER_THIN);//左边框    
//		cs.setBorderTop(CellStyle.BORDER_THIN);//上边框    
//		cs.setBorderRight(CellStyle.BORDER_THIN);//右边框 
//		cs.setWrapText(true);
//
//		int beginRow = 2;
//		for (int i = 0; i < dataList.size(); i++) {
//			Map<String, Object> orderMap = dataList.get(i);
//			@SuppressWarnings("unchecked")
//			List<Map<String, Object>> orderInfoList = (List<Map<String, Object>>) orderMap.get("orderInfo");
//			for(int j = beginRow;j <= beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1));j++) {
//				HSSFRow r = targetSheet.createRow(j);
//				for (int j2 = 0; j2 < 9; j2++) {
//					r.createCell(j2).setCellStyle(cs);;
//
//				}
//			}
//			HSSFRow tempRow = targetSheet.getRow(beginRow);
//			HSSFCell cell0 = tempRow.getCell(0);
//			cell0.setCellValue((String)orderMap.get("sellName"));
//			CellRangeAddress nameCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),0,0);
//			targetSheet.addMergedRegion(nameCra);
//			cell0.setCellStyle(cs);
//
//			HSSFCell cell1 = tempRow.getCell(3);
//			cell1.setCellValue((String)orderMap.get("enterpriseLinkPeople"));
//			CellRangeAddress timeCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),1,1);
//			targetSheet.addMergedRegion(timeCra);
//			cell1.setCellStyle(cs);
//
//			HSSFCell cell2 = tempRow.getCell(4);
//			cell2.setCellValue((String)orderMap.get("enterpriseLinkMobile"));
//			CellRangeAddress orderNumberCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),2,2);
//			targetSheet.addMergedRegion(orderNumberCra);
//			cell2.setCellStyle(cs);
//
//			HSSFCell cell3 = tempRow.getCell(1);
//			cell3.setCellValue((String)orderMap.get("inputTime"));
//			CellRangeAddress nicknameCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),3,3);
//			targetSheet.addMergedRegion(nicknameCra);
//			cell3.setCellStyle(cs);
//
//			HSSFCell cell4 = tempRow.getCell(2);
//			cell4.setCellValue((String)orderMap.get("orderNumber"));
//			CellRangeAddress cra4=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),4,4);
//			targetSheet.addMergedRegion(cra4);
//			cell4.setCellStyle(cs);
//
//			HSSFCell cell5 = tempRow.getCell(5);
//			cell5.setCellValue((String)orderMap.get("price"));
//			CellRangeAddress cra5=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),5,5);
//			targetSheet.addMergedRegion(cra5);
//			cell5.setCellStyle(cs);
//
////			HSSFCell cell6 = tempRow.getCell(6);
////			cell6.setCellValue((String)orderMap.get("price"));
////			CellRangeAddress cra6=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),6,6);
////			targetSheet.addMergedRegion(cra6);
////			cell6.setCellStyle(cs);
//			
//			for (int j = 0; j < orderInfoList.size(); j++) {
//				Map<String, Object>	orderInfoMap = orderInfoList.get(j);
//				HSSFRow row = targetSheet.getRow(beginRow);
////				HSSFCell cell7 = row.getCell(7);
////				cell7.setCellValue((String)orderInfoMap.get("sellName"));
////				cell7.setCellStyle(cs);
//
//				HSSFCell cell8 = row.getCell(6);
//				cell8.setCellValue((String)orderInfoMap.get("productName"));
//				cell8.setCellStyle(cs);
//
//				HSSFCell cell9 = row.getCell(7);
//				cell9.setCellValue((String)orderInfoMap.get("num"));
//				cell9.setCellStyle(cs);
//
//                HSSFCell cell10 = row.getCell(8);
//                cell10.setCellValue((String)orderInfoMap.get("unit"));
//                cell10.setCellStyle(cs);
//				++beginRow;
//			}
//
//		}
//		//复制模板文件列宽
//		for (int i = 0; i <= maxColumnNum; i++){
//			targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
//		}
//
//		targetSheet.setColumnWidth(2, 25*255);
//		targetSheet.setColumnWidth(3, 25*255);
//
//		in.close();
//		// 创建输出流，并输出文件
//		OutputStream os = new FileOutputStream(filePath);
//		//OutputStream os = new FileOutputStream("F:/test/"+fileName);
//		targetWork.write(os);
//		os.close();
//		return fileName;
//	}
//}
