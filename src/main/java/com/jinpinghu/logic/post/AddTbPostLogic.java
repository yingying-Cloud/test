package com.jinpinghu.logic.post;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbExpert;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbKeyword;
import com.jinpinghu.db.bean.TbPost;
import com.jinpinghu.db.bean.TbResPostFile;
import com.jinpinghu.db.bean.TbResPostKeyword;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbExpertDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbKeywordDao;
import com.jinpinghu.db.dao.TbPostDao;
import com.jinpinghu.db.dao.TbResPostFileDao;
import com.jinpinghu.db.dao.TbResPostKeywordDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.post.param.AddTbPostParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddTbPostLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		//权限判断
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		TbRole role = resUserRoleDao.findByUserTabId(loginUser.getId());
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(loginUser.getId());
		if(role.getId() != 3 || enterprise == null) {
			res.add("status", -1).add("msg", "权限失败");
			return false;
		}
		AddTbPostParam myParam =(AddTbPostParam)logicParam;		
		String title=myParam.getTitle();
		String content=myParam.getContent();
		Integer type=myParam.getType();
		Integer mode=myParam.getMode();
		Integer top=myParam.getTop();
		Integer importantLevel=myParam.getImportantLevel();
		String file =myParam.getFile();
		String keyword =myParam.getKeyword();
		Integer expertId = StringUtils.isEmpty(myParam.getExpertId())?null:Integer.valueOf(myParam.getExpertId());
		String workSn = myParam.getWorkSn();
		String isStar = myParam.getIsStar();
		String level = myParam.getLevel();
		
		Date now = new Date();

		//咨询
		TbPostDao tpDao =new TbPostDao(em);
		TbPost tp=new TbPost();
		tp.setUserTabId(loginUser.getId());
		tp.setAddPeople(loginUser.getName());
		tp.setEnterpriseId(enterprise.getId());
		tp.setTitle(title);
		tp.setContent(content);
		tp.setInputTime(now);
		tp.setType(type);
		tp.setMode(mode);
		tp.setTop(top);
		tp.setImportantLevel(importantLevel);
		tp.setChangeTime(now);
		tp.setReplyCount(0);
		tp.setStatus(0);
		tp.setDelFlag(0);
		tp.setExpertId(expertId);
		tp.setWorkSn(workSn);
		tp.setIsStar(isStar);
		tp.setLevel(level);
		tpDao.save(tp);
		
		TbUserDao tbUserDao = new TbUserDao(em);
		TbExpertDao tbExpertDao = new TbExpertDao(em);
		if(expertId!=null) {
			TbExpert expert = tbExpertDao.findById(expertId);
			TbUser user = tbUserDao.findById(loginUser.getId());
			if(expert!=null&&user!=null) {
				Integer integral = StringUtil.isNullOrEmpty(user.getIntegral())?0:Integer.valueOf(user.getIntegral());
				Integer cost = StringUtil.isNullOrEmpty(expert.getCost())?0:Integer.valueOf(expert.getCost());
				if(integral>=cost) {
					user.setIntegral((integral-cost)+"");
					tbUserDao.update(enterprise);
				}else {
					res.add("status", 2);
					res.add("msg", "积分不足");
					return false;
				}
			}
		}
		
		//咨询文件
		if(!StringUtils.isEmpty(file)){
			TbFileDao tfDao =new TbFileDao(em);
			TbResPostFileDao trpfDao =new TbResPostFileDao(em);
			JSONArray array= JSONArray.fromObject(file);
			if(array.size()>0){
				for(int i=0;i<array.size();i++){
					TbFile tf =null;
					JSONObject jsonObj=(JSONObject) array.get(i);
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
					TbResPostFile trpf=new TbResPostFile();
					trpf.setFileId(tf.getId());
					trpf.setPostId(tp.getId());
					trpfDao.save(trpf);
				}
			}
		}
		
		//咨询标签
		if(!StringUtils.isEmpty(keyword)){
			TbResPostKeywordDao trpkDao = new TbResPostKeywordDao(em);
			String[] keywords=keyword.split(",");
			if(keywords!=null) {
				for(String key:keywords){
					TbKeywordDao tbKeywordDao = new TbKeywordDao(em);
					TbKeyword tbKeyword = tbKeywordDao.findById(Integer.valueOf(key));
					if(tbKeyword!=null)
					{
						TbResPostKeyword trpk=new TbResPostKeyword();
						trpk.setKeywordId(tbKeyword.getId());
						trpk.setPostId(tp.getId());
						trpkDao.save(trpk);
	
					}
				}
			}
		}
		res.add("status", 1);
		res.add("msg", "添加成功");
		return true;
	}
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) {
		AddTbPostParam myParam =(AddTbPostParam)logicParam;
		
		String title=myParam.getTitle();
		String content=myParam.getContent();
		Integer type=myParam.getType();
		Integer mode=myParam.getMode();
		Integer top=myParam.getTop();
		Integer importantLevel=myParam.getImportantLevel();
		
		if(StringUtils.isEmpty(title)
				||StringUtils.isEmpty(content)
				||type==null
				||mode==null
				||top==null
				||importantLevel==null)
		 {
			res.add("status", -2);
			res.add("msg", "必填参数不能为空！");
			return false;
		}
		return true;
	}
	
}
