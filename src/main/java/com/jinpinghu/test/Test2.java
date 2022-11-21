package com.jinpinghu.test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbResUserEnterprise;
import com.jinpinghu.db.bean.TbResUserRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;

import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.common.util.Blowfish;
import fw.jbiz.common.util.EncryptTool;
import fw.jbiz.db.ZDao;
import fw.jbiz.jpa.ZJpaHelper;

public class Test2 {
	protected static final Blowfish _blow = new Blowfish("patrol");
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManager em = ZJpaHelper.getEntityManager(ZSystemConfig.getProperty("wisdom_logistics_jar"));
		try {
			ZJpaHelper.beginTransaction(em);
			TbEnterpriseDao tbEnterpriseDao = new TbEnterpriseDao(em);
			TbUserDao tuDao = new TbUserDao(em);
			TbResUserRoleDao tbResUserRoleDao = new TbResUserRoleDao(em);
			TbResUserEnterpriseDao tbResUserEnterpriseDao = new TbResUserEnterpriseDao(em);
			List<Map<String,Object>> list = tbEnterpriseDao.findByAll(null, null, null, null, null, null, null, null, null, null, null, null,null,null,null);
			if(list!=null) {
				for(Map<String,Object> map:list) {
					if(map.containsKey("enterpriseLinkMobile")&&map.get("enterpriseLinkMobile")!=null) {
					TbUser checkUser = tuDao.findByPhone(map.get("enterpriseLinkMobile").toString());
					//如果联系人不存在，新建
					if(checkUser==null){
						String tbUserId = ZDao.genPkId();
						String password=EncryptTool.md5("123456");
						String apiKey = _blow.encryptString(map.get("enterpriseLinkPeople") + password+ System.currentTimeMillis()).substring(0, 100);
						TbUser user = new TbUser(null,tbUserId, apiKey, map.get("enterpriseLinkMobile").toString(), password, new Date());
						user.setDelFlag(0);
						user.setName(map.get("enterpriseLinkPeople").toString());
						tuDao.save(user);
						
						TbResUserEnterprise tbResUserEnterprice = new TbResUserEnterprise();
						tbResUserEnterprice.setDelFlag(0);
						tbResUserEnterprice.setEnterpriseId(Integer.valueOf(map.get("id").toString()));
						tbResUserEnterprice.setUserTabId(user.getId());
						tbResUserEnterpriseDao.save(tbResUserEnterprice);
						
						TbResUserRole tbResUserRole = new TbResUserRole();
						tbResUserRole.setDelFlag(0);
						tbResUserRole.setInputTime(new Date());
						tbResUserRole.setUserTabId(user.getId());
						if(Integer.valueOf(map.get("enterpriseType").toString())==1) {
							tbResUserRole.setRoleId(3);
						}else if(Integer.valueOf(map.get("enterpriseType").toString())==3) {
							tbResUserRole.setRoleId(5);
						}else if(Integer.valueOf(map.get("enterpriseType").toString())==2) {
							tbResUserRole.setRoleId(4);
						}else if(Integer.valueOf(map.get("enterpriseType").toString())==4) {
							tbResUserRole.setRoleId(7);
						}
						tbResUserRoleDao.save(tbResUserRole);
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

}
