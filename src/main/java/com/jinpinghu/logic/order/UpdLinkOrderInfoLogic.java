package com.jinpinghu.logic.order;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.bean.TbResOrderInfoFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbResOrderInfoFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.UpdLinkOrderInfoParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

public class UpdLinkOrderInfoLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        UpdLinkOrderInfoParam myParam=(UpdLinkOrderInfoParam)logicParam;
        Integer id = myParam.getId();
        String address = myParam.getAddress();
        String area = myParam.getArea();
        String creditCode = myParam.getCreditCode();
        Integer enterpriseId = myParam.getEnterpriseId();
        String legalPerson = myParam.getLegalPerson();
        String legalPersonIdcard = myParam.getLegalPersonIdcard();
        String linkMobile = myParam.getLinkMobile();
        String linkPeople = myParam.getLinkPeople();
        String name = myParam.getName();
        Integer type = myParam.getType();
        String zoneName = myParam.getZoneName();
        
        
        TbLinkOrderInfoDao tbLinkOrderInfoDao=new TbLinkOrderInfoDao(em);
        
        if(type==1) {
        	TbLinkOrderInfo findByCreditCode = tbLinkOrderInfoDao.findByCreditCodeAndId(creditCode,id);
        	if(findByCreditCode!=null) {
	        	res.add("status",0);
	            res.add("msg","该企业代码已存在");
	            return true;
        	}
        }else if(type==2) {
        	TbLinkOrderInfo findByCreditCode = tbLinkOrderInfoDao.findByIegalPersonIdcardAndId(legalPersonIdcard,id);
        	if(findByCreditCode!=null) {
	        	res.add("status",0);
	            res.add("msg","该身份证已存在");
	            return true;
        	}
        }
        TbLinkOrderInfo tbLinkOrderInfo = tbLinkOrderInfoDao.findById(id);
        if(tbLinkOrderInfo!=null) {
	        tbLinkOrderInfo.setAddress(address);
	        tbLinkOrderInfo.setArea(area);
	        tbLinkOrderInfo.setCreditCode(creditCode);
	        tbLinkOrderInfo.setEnterpriseId(enterpriseId);
	        tbLinkOrderInfo.setLegalPerson(legalPersonIdcard);
	        tbLinkOrderInfo.setLegalPersonIdcard(legalPersonIdcard);
	        tbLinkOrderInfo.setLinkMobile(linkMobile);
	        tbLinkOrderInfo.setLinkPeople(linkPeople);
	        tbLinkOrderInfo.setName(name);
	        tbLinkOrderInfo.setType(type);
	        tbLinkOrderInfo.setZoneName(zoneName);
	        tbLinkOrderInfoDao.update(tbLinkOrderInfo);
	        String file = myParam.getFile();
	        TbFileDao tfDao = new TbFileDao(em);
			TbResOrderInfoFileDao trfgDao =new TbResOrderInfoFileDao(em);
			
			List<TbFile> tfs =tfDao.findByOrderInfoId(tbLinkOrderInfo.getId());
			List<TbResOrderInfoFile> trgfs =trfgDao.findByOrderInfoId(tbLinkOrderInfo.getId());
			if(trgfs!=null){
				for(TbResOrderInfoFile trgf:trgfs){
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
						TbResOrderInfoFile trpf=new TbResOrderInfoFile();
						trpf.setFileId(tfe.getId());
						trpf.setOrderInfoId(tbLinkOrderInfo.getId());
						trpf.setDelFlag(0);
						trfgDao.save(trpf);
					}
				}
			}
        }
        res.add("status",1);
//        if (result > 0) {
            res.add("msg","更新成功");
//        }else{
//            res.add("msg","更新失败");
//        }
        return true;
    }
}
