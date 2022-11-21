package com.jinpinghu.logic.agriculturalGreenhouses;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbAgriculturalGreenhouses;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResFileGreenhouses;
import com.jinpinghu.db.dao.TbAgriculturalGreenhousesDao2;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResFileGreenhousesDao2;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalGreenhouses.param.AddAgriculturalGreenhousesParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddAgriculturalGreenhousesLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddAgriculturalGreenhousesParam myParam = (AddAgriculturalGreenhousesParam)logicParam;
		Integer enterpriseId = myParam.getEnterpriseId();
		String greenhousesName = myParam.getGreenhousesName();
		String mu = myParam.getMu();
		String area = myParam.getArea();
		String file = myParam.getFile();
		Integer classify = StringUtils.isEmpty(myParam.getClassify())?1:Integer.valueOf(myParam.getClassify());
		TbAgriculturalGreenhousesDao2 tbAgriculturalGreenhousesDao2 = new TbAgriculturalGreenhousesDao2(em);
		
//		Integer userCreateCount = tbAgriculturalGreenhousesDao2.getUserCreateGreenhouseCount(baseId);
//		if (loginUser.getGrade() != null && !StringUtils.isEmpty(loginUser.getGrade().getGreenhouseManagerCount()) 
//				&& Integer.valueOf(loginUser.getGrade().getGreenhouseManagerCount()) <= userCreateCount) {
//			res.add("status", -1).add("msg", "创建地块数量超额！");
//			return false;
//		}
		
		TbAgriculturalGreenhouses tbAgriculturalGreenhouses = new TbAgriculturalGreenhouses();
		tbAgriculturalGreenhouses.setEnterpriseId(enterpriseId);
		tbAgriculturalGreenhouses.setDelFlag(0);
		tbAgriculturalGreenhouses.setGreenhousesName(greenhousesName);
		tbAgriculturalGreenhouses.setInputTime(new Date());
		tbAgriculturalGreenhouses.setMu(mu);
		tbAgriculturalGreenhouses.setListOrder(0);
		tbAgriculturalGreenhouses.setClassify(classify);
		Object backId = tbAgriculturalGreenhousesDao2.save(tbAgriculturalGreenhouses,area);
		TbFileDao tfDao = new TbFileDao(em);
		if(backId!=null&&!StringUtils.isEmpty(backId.toString())) {
			if(!StringUtils.isEmpty(file)){
				TbResFileGreenhousesDao2 trfgDao =new TbResFileGreenhousesDao2(em);
				JSONArray arrayF= JSONArray.fromObject(file);
				if(arrayF.size()>0){
					for(int i=0;i<arrayF.size();i++){
						TbFile tfe =null;
						JSONObject jsonObj=(JSONObject) arrayF.get(i);
						tfe = new TbFile();
						if(jsonObj.containsKey("fileName"))
							tfe.setFileName(jsonObj.getString("fileName"));
						if(jsonObj.containsKey("fileSize"))
							tfe.setFileSize(jsonObj.getString("fileSize"));
						if(jsonObj.containsKey("fileType"))
							tfe.setFileType(jsonObj.getInt("fileType"));
						if(jsonObj.containsKey("fileUrl"))
							tfe.setFileUrl(jsonObj.getString("fileUrl"));
						tfDao.save(tfe);
						TbResFileGreenhouses trpf=new TbResFileGreenhouses();
						trpf.setFileId(tfe.getId());
						trpf.setGreenhousesId(Integer.valueOf(backId.toString()));
						trpf.setDelFlag(0);
						trfgDao.save(trpf);
					}
				}
			}
		}
		
		if(backId!=null&&!StringUtils.isEmpty(backId.toString())) {
			res.add("status", 1).add("msg", "操作成功");
			res.add("id",backId);
			return true;
		}else {
			res.add("status", -1).add("msg", "未知错误");
			return false;
		}
	}

}
