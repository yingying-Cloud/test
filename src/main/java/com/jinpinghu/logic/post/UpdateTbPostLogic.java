package com.jinpinghu.logic.post;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbPost;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPostDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.post.param.UpdateTbPostParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class UpdateTbPostLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		UpdateTbPostParam myParam =(UpdateTbPostParam)logicParam;
		Integer id =myParam.getId();
		String  title =myParam.getTitle();
		Integer mode =StringUtils.isEmpty(myParam.getMode())?null:Integer.valueOf(myParam.getMode());
		String content =myParam.getContent();
		String file =myParam.getFile();
		
		TbPostDao tpDao =new TbPostDao(em);
		TbPost tp=tpDao.findById(Integer.valueOf(id));
		if(tp!=null){
			tp.setContent(content);
			tp.setTitle(title);
			if(mode!=null){
				tp.setMode(mode);
			}
			tp.setChangeTime(new Date());
			tpDao.update(tp);
		}else{
			res.add("msg", "对象不存在");
			res.add("status", -1);
			return true;
		}
		
		if(!StringUtils.isEmpty(file)){
		TbFileDao tfDao =new TbFileDao(em);
		JSONArray array= JSONArray.fromObject(file);
		if(array.size()>0){
			for(int i=0;i<array.size();i++){
				TbFile tf =null;
				JSONObject jsonObj=(JSONObject) array.get(i);
				if(jsonObj.containsKey("id"))
					tf = tfDao.findById(jsonObj.getInt("id"));
				if(tf!=null){
					if(jsonObj.containsKey("fileName"))
						tf.setFileName(jsonObj.getString("fileName"));
						if(jsonObj.containsKey("fileSize"))
						tf.setFileSize(jsonObj.getString("fileSize"));
						if(jsonObj.containsKey("fileType"))
						tf.setFileType(jsonObj.getInt("fileType"));
						if(jsonObj.containsKey("fileUrl"))
						tf.setFileUrl(jsonObj.getString("fileUrl"));
					tfDao.update(tf);
				}else{
					tf = new TbFile();
					if(jsonObj.containsKey("fileName"))
						tf.setFileName(jsonObj.getString("fileName"));
						if(jsonObj.containsKey("fileSize"))
						tf.setFileSize(jsonObj.getString("fileSize"));
						if(jsonObj.containsKey("fileType"))
						tf.setFileType(jsonObj.getInt("fileType"));
						if(jsonObj.containsKey("fileUrl"))
						tf.setFileUrl(jsonObj.getString("fileUrl"));
					tfDao.save(tf);
				}
			}
		}
		}
		
		res.add("msg", "更新成功");
		res.add("status", 1);
		return true;
	}
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) {
		UpdateTbPostParam myParam =(UpdateTbPostParam)logicParam;
		
		String  title =myParam.getTitle();
		String content =myParam.getContent();
		
		if(StringUtils.isEmpty(content)
				||StringUtils.isEmpty(title)
				)
		 {
			res.add("status", -2);
			res.add("msg", "必填参数不能为空！");
			return false;
		}
		return true;
	}
	
}