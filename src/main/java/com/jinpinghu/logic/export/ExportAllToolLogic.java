package com.jinpinghu.logic.export;

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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import com.jinpinghu.common.tools.PoiUtil;
import com.jinpinghu.db.bean.TbArea;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolRecord;
import com.jinpinghu.db.dao.TbAreaDao;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbEnterpriseZeroDao;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.db.dao.TbToolRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseListParam;
import com.jinpinghu.logic.toolCatalog.param.GetToolCatalogListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExportAllToolLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetToolCatalogListParam  myParam = (GetToolCatalogListParam)logicParam;
		TbToolCatalogDao toolDao = new TbToolCatalogDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbAreaDao saDao = new TbAreaDao(em);
		TbLinkOrderInfoDao linkOrderInfoDao = new TbLinkOrderInfoDao(em);
		List<TbEnterprise> syncList = enterpriseDao.findAllIsSync();
		JSONArray ja = new JSONArray();
		if(syncList!=null) {
			for(TbEnterprise te:syncList) {
				JSONObject enterpriseJo = new JSONObject();
				enterpriseJo.put("businessScope","100");
				enterpriseJo.put("name",te.getName());
				enterpriseJo.put("plantingSum",StringUtils.isNullOrEmpty(te.getPlantScope())?null:te.getPlantScope());
				enterpriseJo.put("legalPersonTel",StringUtils.isNullOrEmpty(te.getEnterpriseLinkMobile())?null:te.getEnterpriseLinkMobile());
				enterpriseJo.put("contract",StringUtils.isNullOrEmpty(te.getEnterpriseLinkPeople())?null:te.getEnterpriseLinkPeople());
				enterpriseJo.put("contractTel",StringUtils.isNullOrEmpty(te.getEnterpriseLinkMobile())?null:te.getEnterpriseLinkMobile());
				enterpriseJo.put("enterpriseAddress",StringUtils.isNullOrEmpty(te.getEnterpriseAddress())?null:te.getEnterpriseAddress());
				enterpriseJo.put("enterpriseCreditCode",StringUtils.isNullOrEmpty(te.getEnterpriseCreditCode())?null:te.getEnterpriseCreditCode().trim());
				enterpriseJo.put("enterpriseLatitude",StringUtils.isNullOrEmpty(te.getY())?null:te.getY());
				enterpriseJo.put("enterpriseLegalPerson",StringUtils.isNullOrEmpty(te.getEnterpriseLegalPerson())?null:te.getEnterpriseLegalPerson());
				enterpriseJo.put("enterpriseLegalPersonIdcard",StringUtils.isNullOrEmpty(te.getEnterpriseLegalPersonIdcard())?null:te.getEnterpriseLegalPersonIdcard());
				enterpriseJo.put("enterpriseLongitude",StringUtils.isNullOrEmpty(te.getX())?null:te.getX());
				enterpriseJo.put("enterpriseType",1);
				enterpriseJo.put("isChain",101);
				enterpriseJo.put("isLocal","100");
				enterpriseJo.put("cityName","?????????");
				enterpriseJo.put("countyName","?????????");
				enterpriseJo.put("town","");
				enterpriseJo.put("syncNumber",te.getSyncNumber());
				if(!StringUtils.isNullOrEmpty(te.getDscd())) {
					TbArea area = saDao.findById(te.getDscd());
					if(area!=null) {enterpriseJo.put("town",area.getName());}
				}
				System.out.println(te.getId());
				enterpriseJo.put("registeredCapital",StringUtils.isNullOrEmpty(te.getRegisteredFunds())?null:te.getRegisteredFunds());
//				enterpriseJo.put("enterprise", enterpriseJo);
//				JSONObject userJo = new JSONObject();
//				userJo.put("mobile", te.getEnterpriseLinkMobile());
//				userJo.put("name", te.getEnterpriseLinkPeople());
//				enterpriseJo.put("user", userJo);
				ja.add(enterpriseJo);
			}
		}
		
		String fileName = export(ja);
		res.add("status", 1).add("msg", "????????????").add("path", "/export/"+fileName);
		return true;
	}

	private String toxicityText(String toxicity) {
		String back = "";
		switch(toxicity) {
		case "??????":
			back="102";
			break;
		case "??????":
			back="103";
			break;
		case "?????????":
			back="103";
			break;
		case "??????":
			back="104";
			break;
		case "??????":
			back="101";
			break;
		default:
			break;
		}
		return back;
	}
	private Integer typeToCode(Integer type) {
		Integer back = null;
		switch(type) {
		case 12:
			back=102;
			break;
		case 13:
			back=101;
			break;
		case 14:
			back=100;
			break;
		case 17:
			back=99;
			break;
		case 15:
			back=104;
			break;
		case 16:
			back=103;
			break;
		default:
			break;
		}
		return back;
	}

	
	public String export(JSONArray dataList) throws Exception {
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
		HSSFSheet targetSheet = targetWork.createSheet("tool");
		
		//????????????????????????????????????
		InputStream in = new FileInputStream(TemplateFilePath + "/allTool.xls");
        HSSFWorkbook sourceWork = new HSSFWorkbook(in);
		HSSFSheet sourceSheet = sourceWork.getSheet("tool");
		
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
			JSONObject orderMap = dataList.getJSONObject(i);
			HSSFRow row = targetSheet.getRow(beginRow);
			
			
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue(orderMap.get("businessScope")+"");
			cell0.setCellStyle(cs);
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue(orderMap.get("name")+"");
			cell1.setCellStyle(cs);
			
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue(orderMap.get("plantingSum")+"");
			cell2.setCellStyle(cs);
			
			HSSFCell cell3 = row.createCell(3);
			cell3.setCellValue(orderMap.get("legalPersonTel")+"");
			cell3.setCellStyle(cs);
			
			HSSFCell cell4 = row.createCell(4);
			cell4.setCellValue(orderMap.get("contract")+"");
			cell4.setCellStyle(cs);
			
			HSSFCell cell5 = row.createCell(5);
			cell5.setCellValue(orderMap.get("contractTel")+"");
			cell5.setCellStyle(cs);
			
			HSSFCell cell6 = row.createCell(6);
			cell6.setCellValue(orderMap.get("enterpriseAddress")+"");
			cell6.setCellStyle(cs);
			
			HSSFCell cell7 = row.createCell(7);
			cell7.setCellValue(orderMap.get("enterpriseCreditCode")+"");
			cell7.setCellStyle(cs);
			
			HSSFCell cell8 = row.createCell(8);
			cell8.setCellValue(orderMap.get("enterpriseLatitude")+"");
			cell8.setCellStyle(cs);
			
			HSSFCell cell9 = row.createCell(9);
			cell9.setCellValue(orderMap.get("enterpriseLegalPerson")+"");
			cell9.setCellStyle(cs);
			
			HSSFCell cell10 = row.createCell(10);
			cell10.setCellValue(orderMap.get("enterpriseLegalPersonIdcard")+"");
			cell10.setCellStyle(cs);
			
			HSSFCell cell11 = row.createCell(11);
			cell11.setCellValue(orderMap.get("enterpriseLongitude")+"");
			cell11.setCellStyle(cs);
			
			HSSFCell cell12 = row.createCell(12);
			cell12.setCellValue(orderMap.get("enterpriseType")+"");
			cell12.setCellStyle(cs);
			
			HSSFCell cell13 = row.createCell(13);
			cell13.setCellValue(orderMap.get("isChain")+"");
			cell13.setCellStyle(cs);
			
			HSSFCell cell14 = row.createCell(14);
			cell14.setCellValue(orderMap.get("isLocal")+"");
			cell14.setCellStyle(cs);
			
			HSSFCell cell15 = row.createCell(15);
			cell15.setCellValue(orderMap.get("cityName")+"");
			cell15.setCellStyle(cs);
			
			HSSFCell cell16 = row.createCell(16);
			cell16.setCellValue(orderMap.get("countyName")+"");
			cell16.setCellStyle(cs);

			HSSFCell cell17 = row.createCell(17);
			cell17.setCellValue(orderMap.get("town")+"");
			cell17.setCellStyle(cs);
			
			HSSFCell cell18 = row.createCell(18);
			cell18.setCellValue(orderMap.get("registeredCapital")+"");
			cell18.setCellStyle(cs);
			
			HSSFCell cell19 = row.createCell(19);
			cell19.setCellValue(orderMap.get("syncNumber")+"");
			cell19.setCellStyle(cs);
			
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
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
		return true;
	}
}
