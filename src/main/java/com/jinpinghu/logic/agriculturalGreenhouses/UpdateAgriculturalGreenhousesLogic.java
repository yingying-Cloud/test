package com.jinpinghu.logic.agriculturalGreenhouses;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbAgriculturalGreenhouses;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResFileGreenhouses;
import com.jinpinghu.db.dao.TbAgriculturalGreenhousesDao2;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResFileGreenhousesDao2;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalGreenhouses.param.UpdateAgriculturalGreenhousesParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UpdateAgriculturalGreenhousesLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		UpdateAgriculturalGreenhousesParam myParam = (UpdateAgriculturalGreenhousesParam)logicParam;
		Integer id = myParam.getId();
		Integer enterpriseId = myParam.getEnterpriseId();
		String center = myParam.getCenter();
		String greenhousesName = myParam.getGreenhousesName();
		String mu = myParam.getMu();
		String area = myParam.getArea();
		String file = myParam.getFile();
		Integer classify = StringUtils.isEmpty(myParam.getClassify())?1:Integer.valueOf(myParam.getClassify());
		TbAgriculturalGreenhousesDao2 tbAgriculturalGreenhousesDao2 = new TbAgriculturalGreenhousesDao2(em);
		TbAgriculturalGreenhouses tbAgriculturalGreenhouses =tbAgriculturalGreenhousesDao2.findById(id);
		if(tbAgriculturalGreenhouses!=null) {
			int update = tbAgriculturalGreenhousesDao2.update(id, enterpriseId, center, greenhousesName, mu, area,classify);
			if(update<=0){
				res.add("status", -1);
				res.add("msg", "未知错误");
				return false;
			}
			
			TbFileDao tfDao = new TbFileDao(em);
			TbResFileGreenhousesDao2 trfgDao =new TbResFileGreenhousesDao2(em);
			
			List<TbFile> tfs =tfDao.findByGreenhousesId(id);
			List<TbResFileGreenhouses> trgfs =trfgDao.findByGreenhousesId(id);
			if(trgfs!=null){
				for(TbResFileGreenhouses trgf:trgfs){
					trfgDao.delete(trgf);
				}
			}
			if(tfs!=null){
				for(TbFile tbFile:tfs){
					tfDao.delete(tbFile);
				}
			}
			
			if(!StringUtils.isEmpty(file)){
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
						trpf.setGreenhousesId(tbAgriculturalGreenhouses.getId());
						trpf.setDelFlag(0);
						trfgDao.save(trpf);
					}
				}
			}
			res.add("id", id);
			res.add("status", 1);
			res.add("msg", "操作成功");
			return true;
		}else {
			res.add("status", 1);
			res.add("msg", "该大棚不存在");
			return true;
		}
		
	}
}
