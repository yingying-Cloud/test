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
import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
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

public class ExportByToolOrderLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		ListAllOrderParam myParam =(ListAllOrderParam)logicParam;
		String toolName=myParam.getToolName();
		String orderNumber=myParam.getOrderNumber();
		String enterpriseName=myParam.getEnterpriseName();
		String beginCreateTime=myParam.getBeginCreateTime();
		String endCreateTime=myParam.getEndCreateTime();
		String beginPayTime=myParam.getBeginPayTime();
		String endPayTime=myParam.getEndPayTime();
		Integer status=StringUtils.isNullOrEmpty(myParam.getStatus())?null:Integer.valueOf(myParam.getStatus());
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer check = StringUtils.isNullOrEmpty(myParam.getCheck())?null:Integer.valueOf(myParam.getCheck());
		String name = myParam.getName();
		String isValidation = myParam.getIsValidation();
		String selectAll = myParam.getSelectAll();
		String uniformPrice = myParam.getUniformPrice();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		TbLinkOrderInfoDao orderInfoDao = new TbLinkOrderInfoDao(em);
		TbToolOrderDao toolOrderDao = new TbToolOrderDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbShoppingCarDao shoppingCarDao = new TbShoppingCarDao(em);
		JSONArray ja = new JSONArray();
		
		List<Object[]> list = toolOrderDao.findByAll(toolName, orderNumber, enterpriseName, beginCreateTime, endCreateTime, beginPayTime, endPayTime, status, null, null,enterpriseId,check,name,isValidation,selectAll,permissionEnterpriseIdList,uniformPrice);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo = new JSONObject();
				jo.put("id",o[0] );
				jo.put("toolEnterpriseId",o[1] );
				jo.put("plantEnterpriseId",o[2] );
				jo.put("addPeople",o[3] );
				jo.put("orderNumber",o[4] );
				jo.put("price",(o[5] == null || "".equals(o[5])) ? "" : new BigDecimal(o[5].toString()) );
				jo.put("status",o[6] );
				jo.put("cancleInfo",o[7] );
				jo.put("rejectedInfo",o[8] );
				jo.put("inputTime",o[9] );
				jo.put("timeAudit",o[10] );
				jo.put("timePay",o[11] );
				jo.put("timeComplete",o[12] );
				jo.put("timeCancle",o[13] );
				jo.put("timeRejected",o[14] );
				jo.put("check",o[15] );
				jo.put("checkMoney",o[16] );
				List<Map<String,Object>> carInfo = shoppingCarDao.findInOrderId(Integer.valueOf(o[0].toString()),uniformPrice);
				jo.put("carInfo", carInfo);
				if(Integer.valueOf(o[17].toString())==1) {
					TbEnterprise enterpriseP = enterpriseDao.findById(Integer.valueOf(o[2].toString()));
					jo.put("plantEnterpriseName",enterpriseP.getName() );
				}else if(Integer.valueOf(o[17].toString())==2){
					TbLinkOrderInfo orderInfo = null;
					if(o[2]!= null)
						orderInfo = orderInfoDao.findById(Integer.valueOf(o[2].toString()));
					if(orderInfo!=null) {
						jo.put("name",orderInfo.getName() );
						jo.put("linkMobile",orderInfo.getLinkMobile() );
						jo.put("linkPeople",orderInfo.getLinkPeople() );
						jo.put("infoType",orderInfo.getType() );
					}else {
						jo.put("name","" );
						jo.put("infoType","");
					}
				}
				TbEnterprise enterpriseT = enterpriseDao.findById(Integer.valueOf(o[1].toString()));
				jo.put("toolEnterpriseName",enterpriseT.getName() );
				jo.put("toolEnterpriseLinkMobile",enterpriseT.getEnterpriseLinkMobile() );
				jo.put("toolEnterpriseLinkPeople",enterpriseT.getEnterpriseLinkPeople() );
				ja.add(jo);
			}
		}
		List<Map<String, Object>> toolTypeDataList = toolOrderDao.orderDataGroupByToolType(toolName, orderNumber, enterpriseName, beginCreateTime, endCreateTime, beginPayTime, endPayTime, status, enterpriseId, check, name, isValidation, selectAll, permissionEnterpriseIdList, uniformPrice);
		String fileName = "";
//		if("1".equals(isExport))
		fileName = export(ja,toolTypeDataList);
		res.add("status", 1).add("msg", "操作成功").add("path", "/export/"+fileName);
		return true;
	}



	public String export(JSONArray dataList,List<Map<String, Object>> toolTypeDataList) throws Exception {
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
		
		for(int i = 0; i < toolTypeDataList.size(); i++) {
			Map<String,Object> orderMap = (Map<String,Object>) toolTypeDataList.get(i);
			HSSFRow row = targetSheet.createRow(beginRow++);
			
			HSSFCell cell6 = row.createCell(0);
			cell6.setCellValue((String)orderMap.get("name"));
			cell6.setCellStyle(cs);
			
			HSSFCell cell0 = row.createCell(1);
			cell0.setCellValue(orderMap.get("number")+"");
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(2);
			cell1.setCellValue(orderMap.get("price")+"");
			cell1.setCellStyle(cs);
		}
		
		beginRow+=2;
		targetSheet.createRow(beginRow);
		for (int i = 0; i < dataList.size(); i++) {
			JSONObject orderMap = (JSONObject) dataList.get(i);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> orderInfoList = (List<Map<String, Object>>) orderMap.get("carInfo");
			for(int j = beginRow;j <= beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1));j++) {
				HSSFRow r = targetSheet.createRow(j);
				for (int j2 = 0; j2 < 9; j2++) {
					r.createCell(j2).setCellStyle(cs);;

				}
			}
			HSSFRow tempRow = targetSheet.getRow(beginRow);
			HSSFCell cell0 = tempRow.getCell(0);
			cell0.setCellValue((String)orderMap.get("toolEnterpriseName"));
			CellRangeAddress nameCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),0,0);
			targetSheet.addMergedRegion(nameCra);
			cell0.setCellStyle(cs);

			HSSFCell cell1 = tempRow.getCell(1);
			cell1.setCellValue((String)orderMap.get("inputTime"));
			CellRangeAddress timeCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),1,1);
			targetSheet.addMergedRegion(timeCra);
			cell1.setCellStyle(cs);

			HSSFCell cell2 = tempRow.getCell(2);
			cell2.setCellValue((String)orderMap.get("orderNumber"));
			CellRangeAddress orderNumberCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),2,2);
			targetSheet.addMergedRegion(orderNumberCra);
			cell2.setCellStyle(cs);

			HSSFCell cell3 = tempRow.getCell(3);
			cell3.setCellValue(orderMap.containsKey("linkPeople")?(String)orderMap.get("linkPeople"):"");
			CellRangeAddress nicknameCra=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),3,3);
			targetSheet.addMergedRegion(nicknameCra);
			cell3.setCellStyle(cs);

			HSSFCell cell4 = tempRow.getCell(4);
			cell4.setCellValue(orderMap.containsKey("linkMobile")?(String)orderMap.get("linkMobile"):"");
			CellRangeAddress cra4=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),4,4);
			targetSheet.addMergedRegion(cra4);
			cell4.setCellStyle(cs);

			HSSFCell cell5 = tempRow.getCell(5);
			cell5.setCellValue(orderMap.get("price")+"");
			CellRangeAddress cra5=new CellRangeAddress(beginRow,beginRow+(orderInfoList.isEmpty() ? 0 : (orderInfoList.size()-1)),5,5);
			targetSheet.addMergedRegion(cra5);
			cell5.setCellStyle(cs);

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

				HSSFCell cell8 = row.getCell(6);
				cell8.setCellValue(orderInfoMap.containsKey("toolName")?(String) orderInfoMap.get("toolName"):"");
				cell8.setCellStyle(cs);

				HSSFCell cell9 = row.getCell(7);
				cell9.setCellValue(orderInfoMap.containsKey("num")?orderInfoMap.get("num")+"":"");
				cell9.setCellStyle(cs);

                HSSFCell cell10 = row.getCell(8);
                cell10.setCellValue(orderInfoMap.containsKey("specification")?orderInfoMap.get("specification")+"":"");
                cell10.setCellStyle(cs);
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
