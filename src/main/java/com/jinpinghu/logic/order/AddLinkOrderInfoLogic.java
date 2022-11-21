package com.jinpinghu.logic.order;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.bean.TbResOrderInfoFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbResOrderInfoFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.AddLinkOrderInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddLinkOrderInfoLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        AddLinkOrderInfoParam myParam=(AddLinkOrderInfoParam)logicParam;
        TbLinkOrderInfo tbLinkOrderInfo = myParam.getTbLinkOrderInfo();
        TbLinkOrderInfoDao tbLinkOrderInfoDao=new TbLinkOrderInfoDao(em);
        if(tbLinkOrderInfo.getType()==1) {
        	TbLinkOrderInfo findByCreditCode = tbLinkOrderInfoDao.findByCreditCode(tbLinkOrderInfo.getCreditCode());
        	if(findByCreditCode!=null) {
	        	res.add("status",0);
	            res.add("msg","已存在");
	            return true;
        	}
        }else if(tbLinkOrderInfo.getType()==2) {
        	TbLinkOrderInfo findByCreditCode = tbLinkOrderInfoDao.findByIegalPersonIdcard(tbLinkOrderInfo.getLegalPersonIdcard());
        	if(findByCreditCode!=null) {
	        	res.add("status",0);
	            res.add("msg","已存在");
	            return true;
        	}
        }
        tbLinkOrderInfoDao.save(tbLinkOrderInfo);
        
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
        
//        Integer result=tbLinkOrderInfoDao.addLinkOrderInfo(tbLinkOrderInfo);
        res.add("status",1);
//        if (result>0){
            res.add("msg","添加成功");
//        }else{
//            res.add("msg","添加失败");
//        }
        return true;
    }
}
