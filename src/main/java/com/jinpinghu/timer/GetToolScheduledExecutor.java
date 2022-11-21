package com.jinpinghu.timer;

import javax.persistence.EntityManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.jinpinghu.common.tools.AliyunOSSClientUtil;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.common.tools.OSSClientConstants;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResToolCatalogFile;
import com.jinpinghu.db.bean.TbResToolFile;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResToolFileDao;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.jpa.ZJpaHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetToolScheduledExecutor implements Runnable{

	
	TbToolCatalogDao toolDao;
	TbEnterpriseDao enterpriseDao;
	public GetToolScheduledExecutor() {
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		EntityManager em = ZJpaHelper.getEntityManager(ZSystemConfig.getProperty("wisdom_logistics_jar"));
		try {
			
			ZJpaHelper.beginTransaction(em);
			enterpriseDao = new TbEnterpriseDao(em);
			
			TbToolDao tbToolDao = new TbToolDao(em);
			TbToolCatalogDao toolCatalogDao = new TbToolCatalogDao(em);
			TbFileDao tfDao = new TbFileDao(em);
			TbResToolFileDao trfgDao = new TbResToolFileDao(em);
			
			String url ="/agricultural/api/product/queryListBySource";
			JSONObject jos =new JSONObject();
//			jos.put("enterpriseId", "15889195717897500");
			jos.put("startDate", DateTimeUtil.getTodaySTR());
			jos.put("endDate", DateTimeUtil.getTodaySTR());
			String sendPost = HttpRequestUtil.sendPostToken(url, jos, "utf-8");
			JSONObject jo = JSONObject.fromObject(sendPost);
			if(jo.getInt("code")==200) {
				JSONArray data = jo.getJSONArray("data");
				
				for(int i=0;i<data.size();i++) {
					JSONObject toolJo = data.getJSONObject(i);
					String number = toolJo.getString("number");
					String enterpriseId = toolJo.getString("enterpriseId");
					TbEnterprise enterprise = enterpriseDao.findBySyncNumber(enterpriseId);
					TbTool tool = tbToolDao.findBySyncNumber(number, enterprise.getId());
					
					if(tool!=null) {continue;}
					
					tool = new TbTool();
					tool.setDelFlag(0);
					tool.setEnterpriseId(enterprise.getId());
					tool.setName(toolJo.getString("name"));
					tool.setPrice(toolJo.getString("price"));
					tool.setSpecification(toolJo.getString("norms"));
					tool.setType(typeToCode(toolJo.getInt("code")));
					tool.setEffectiveIngredients(toolJo.getString("component"));
					tool.setDescribe(toolJo.getString("explains"));
					tool.setDosageForms(toolJo.getString("form"));
					tool.setProductionUnits(toolJo.getString("productEnterprise"));
					tool.setRegistrationCertificateNumber(toolJo.getString("regNo"));
					tool.setImplementationStandards(toolJo.getString("standards"));
					tool.setProductAttributes(toolJo.getString("attribute"));
					tool.setHfzc(toolJo.getString("foldPurity"));
					tool.setApprovalEndDate(DateTimeUtil.formatTime(toolJo.getString("approvalEndDate")));
					tool.setToxicity(toolJo.getString("toxicity"));
					tool.setApproveNo(toolJo.getString("approveNo"));
					tool.setCertificateNo(toolJo.getString("certificateNo"));
					tool.setCheckHealthNo(toolJo.getString("checkHealthNo"));
					tool.setHealthNo(toolJo.getString("healthNo"));
					tool.setLimitDate(DateTimeUtil.formatTime(toolJo.getString("limitDate")));
					tool.setProduced(toolJo.getString("produced"));
					tool.setProductionNo(toolJo.getString("productionNo"));
					tool.setIsSync(1);
					tool.setSyncNumber(number);
					tbToolDao.save(tool);
					String fileUrl = toolJo.getString("picture");
					
					CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
			        HttpGet httpGet = new HttpGet("http://"+fileUrl);
			        HttpResponse response = httpClient.execute(httpGet);
					String fileObjectId = AliyunOSSClientUtil.uploadObject2OSS2(AliyunOSSClientUtil.getOSSClient(),
		            		response.getEntity().getContent(),fileUrl.substring(fileUrl.lastIndexOf("/")+1),response.getEntity().getContentLength(),
		            		OSSClientConstants.BACKET_NAME);
					String newFileUrl = "https://shabang.oss-cn-beijing.aliyuncs.com/"+fileObjectId;
					if(!StringUtils.isNullOrEmpty(newFileUrl)) {
						TbFile tfe  = new TbFile();
						tfe.setFileName(fileUrl.substring(fileUrl.lastIndexOf("/")+1));
						tfe.setFileType(1);
						tfe.setFileUrl(newFileUrl);
						tfDao.save(tfe);
						TbResToolFile trpf=new TbResToolFile();
						trpf.setFileId(tfe.getId());
						trpf.setToolId(Integer.valueOf(tool.getId()));
						trpf.setDelFlag(0);
						trfgDao.save(trpf);
					}
					TbToolCatalog toolCatelog = new TbToolCatalog();
						toolCatelog.setDelFlag(0);
						toolCatelog.setName(toolJo.getString("name"));
						toolCatelog.setPrice(toolJo.getString("price"));
						toolCatelog.setSpecification(toolJo.getString("norms"));
						toolCatelog.setType(typeToCode(toolJo.getInt("code")));
						toolCatelog.setEffectiveIngredients(toolJo.getString("component"));
						toolCatelog.setDescribe(toolJo.getString("explains"));
						toolCatelog.setDosageForms(toolJo.getString("form"));
						toolCatelog.setProductionUnits(toolJo.getString("productEnterprise"));
						toolCatelog.setRegistrationCertificateNumber(toolJo.getString("regNo"));
						toolCatelog.setImplementationStandards(toolJo.getString("standards"));
						toolCatelog.setProductAttributes(toolJo.getString("attribute"));
						toolCatelog.setHfzc(toolJo.getString("foldPurity"));
						toolCatelog.setApprovalEndDate(DateTimeUtil.formatTime(toolJo.getString("approvalEndDate")));
						toolCatelog.setToxicity(toolJo.getString("toxicity"));
						toolCatelog.setApproveNo(toolJo.getString("approveNo"));
						toolCatelog.setCertificateNo(toolJo.getString("certificateNo"));
						toolCatelog.setCheckHealthNo(toolJo.getString("checkHealthNo"));
						toolCatelog.setHealthNo(toolJo.getString("healthNo"));
						toolCatelog.setLimitDate(DateTimeUtil.formatTime(toolJo.getString("limitDate")));
						toolCatelog.setProduced(toolJo.getString("produced"));
						toolCatelog.setProductionNo(toolJo.getString("productionNo"));
						toolCatalogDao.save(toolCatelog);
						
						if(!StringUtils.isNullOrEmpty(newFileUrl)) {
							TbFile tfes  = new TbFile();
							tfes.setFileName(newFileUrl.substring(newFileUrl.lastIndexOf("/")+1));
							tfes.setFileType(1);
							tfes.setFileUrl(newFileUrl);
							tfDao.save(tfes);
							TbResToolCatalogFile trpfs=new TbResToolCatalogFile();
							trpfs.setFileId(tfes.getId());
							trpfs.setToolCatalogId(Integer.valueOf(toolCatelog.getId()));
							trpfs.setDelFlag(0);
							trfgDao.save(trpfs);
						}
					while(true) {
						TbToolCatalog findByCode = toolCatalogDao.findByCode(toolCatelog.getType()+String.format("%06d",toolCatelog.getId()+i),null);
							if(findByCode==null) {
								toolCatelog.setCode(toolCatelog.getType()+String.format("%06d",toolCatelog.getId()));
								tool.setCode(toolCatelog.getCode());
								break;
							}
							i++;
						}
					toolCatalogDao.update(toolCatelog);
					tbToolDao.update(tool);
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
		case 102:
			back=12;
			break;
		case 101:
			back=13;
			break;
		case 100:
			back=14;
			break;
		case 99:
			back=17;
			break;
		case 104:
			back=15;
			break;
		case 103:
			back=16;
			break;
		default:
			break;
		}
		return back;
	}
}
