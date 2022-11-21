package com.jinpinghu.timer;

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

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.common.tools.PoiUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.jpa.ZJpaHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddToolScheduledExecutor implements Runnable{

	
	TbToolCatalogDao toolDao;
	TbEnterpriseDao enterpriseDao;
	public AddToolScheduledExecutor() {
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		EntityManager em = ZJpaHelper.getEntityManager(ZSystemConfig.getProperty("wisdom_logistics_jar"));
		try {
			
			AddScheduledExecutor task3 = new AddScheduledExecutor();
			task3.run();
			
			ZJpaHelper.beginTransaction(em);
			toolDao = new TbToolCatalogDao(em);
			enterpriseDao = new TbEnterpriseDao(em);
			List<TbTool> syncList = toolDao.findAllIsSync();
			String url ="/agricultural/api/product/insert";
			JSONArray ja = new JSONArray();
			if(syncList!=null) {
				for(TbTool te:syncList) {
					JSONObject param = new JSONObject();
					param.put("code",te.getType()==null?"":typeToCode(te.getType()));
					param.put("component",StringUtils.isNullOrEmpty(te.getEffectiveIngredients())?"无":te.getEffectiveIngredients());
					param.put("explains",te.getDescribe());
					param.put("remark",te.getDescribe());
					param.put("form",te.getDosageForms()==null?"其他":FormText(te.getDosageForms()));
					param.put("fullName",te.getName());
					param.put("name",te.getName());
					param.put("norms",StringUtils.isNullOrEmpty(te.getSpecification())?"":te.getSpecification().replaceAll("/", ";"));
					param.put("price",te.getPrice());
					param.put("productEnterprise",te.getProductionUnits());
					param.put("regNo",StringUtils.isNullOrEmpty(te.getRegistrationCertificateNumber())?"无":te.getRegistrationCertificateNumber());
					param.put("standards",te.getImplementationStandards());
					param.put("attribute",te.getProductAttributes());
					param.put("toxicity",te.getToxicity()==null?"":toxicityText(te.getToxicity()));
					param.put("picture",toolDao.findToolFile(te.getId()));
					
					param.put("foldPurity",te.getHfzc());
					param.put("approvalEndDate",te.getApprovalEndDate()==null?null:DateTimeUtil.formatTime(te.getApprovalEndDate()));
					param.put("approvalNo",te.getApprovalNo());
					param.put("approveNo",te.getApproveNo());
					param.put("certificateNo",te.getCertificateNo());
					param.put("checkHealthNo",te.getCheckHealthNo());
					param.put("healthNo",te.getHealthNo());
					param.put("limitDate",te.getLimitDate()==null?null:DateTimeUtil.formatTime(te.getLimitDate()));
					param.put("produced",te.getProduced()==null?"100":producedText(te.getProduced()));
					param.put("productionNo",te.getProductionNo());
					
					TbEnterprise tbEnterprise = enterpriseDao.findById(te.getEnterpriseId());
					param.put("enterpriseId",tbEnterprise==null?null:tbEnterprise.getSyncNumber());
					ja.add(param);
					
					String sendPost = HttpRequestUtil.sendPostToken(url, param, "utf-8");
					JSONObject jo = JSONObject.fromObject(sendPost);
					if(jo.getInt("code")==200) {
						JSONObject data = jo.getJSONObject("data");
						if(data!=null) {
							String number = data.getString("number");
							te.setSyncNumber(number);
							te.setIsSync(1);
							toolDao.update(te);
						}
					}
				}
			}
			ZJpaHelper.commit(em);
    	}catch (Exception e) {
        	e.printStackTrace();
            System.out.println("-------------发生异常--------------");
            ZJpaHelper.rollback(em);
        }finally{
        	ZJpaHelper.closeEntityManager(em);
        }
    	
	}
	private String toxicityText(String toxicity) {
		String back = "";
		switch(toxicity) {
		case "微毒":
			back="102";
			break;
		case "低毒":
			back="102";
			break;
		case "中毒":
			back="103";
			break;
		case "中等毒":
			back="103";
			break;
		case "高毒":
			back="104";
			break;
		case "无毒":
			back="101";
			break;
		case "剧毒":
			back="104";
			break;
		default:
			back="101";
			break;
		}
		return back;
	}
	private String FormText(String forms) {
		String back = "";
		switch(forms) {
		case "粉剂":
			back="100";
			break;
		case "颗粒":
			back="101";
			break;
		case "液体":
			back="102";
			break;
		default:
			back="其他";
			break;
		}
		return back;
	}
	
	private String producedText(String produced) {
		String back = "";
		switch(produced) {
		case "省内":
			back="100";
			break;
		case "省外":
			back="101";
			break;
		case "进口":
			back="102";
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
}
