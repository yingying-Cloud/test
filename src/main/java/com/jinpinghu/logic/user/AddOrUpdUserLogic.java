package com.jinpinghu.logic.user;

import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbResUserRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.AddOrUpdUserParam;

import fw.jbiz.db.ZDao;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

/**
 * @Author: yanchengjie
 * @Date: 2019/8/28 10:06
 */
public class AddOrUpdUserLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        AddOrUpdUserParam myParam=(AddOrUpdUserParam)logicParam;
        Integer id=myParam.getId();
        String mobile=myParam.getMobile();
        String password=myParam.getPassword();
        String name=myParam.getName();
        Integer roleId=myParam.getRoleId();
        Integer enterpriseId=myParam.getEnterpriseId();
        String dscd = myParam.getDscd();
        TbUserDao tbUserDao=new TbUserDao(em);
        TbResUserRoleDao tbResUserRoleDao=new TbResUserRoleDao(em);
        TbResUserEnterpriseDao tbResUserEnterpriseDao=new TbResUserEnterpriseDao(em);
        TbUser tbUser=tbUserDao.findById2(id);
        if (tbUser!=null&&tbUser.getDelFlag()==0){
            //更新操作
            tbUser.setMobile(mobile);
            tbUser.setPassword(password);
            tbUser.setName(name);
            tbUser.setDscd(dscd);
            tbUserDao.update(tbUser);
            res.add("status",1);
            res.add("msg","更新成功");
        }else{
            //添加操作
            tbUser=new TbUser();
            String userId= ZDao.genPkId();
            String apiKey = _blow.encryptString(mobile + password+ System.currentTimeMillis()).substring(0, 100);
            tbUser.setUserId(userId);
            tbUser.setApiKey(apiKey);
            tbUser.setMobile(mobile);
            tbUser.setPassword(password);
            tbUser.setRegTime(new Date());
            tbUser.setName(name);
            tbUser.setDelFlag(0);
            tbUser.setDscd(dscd);
            tbUserDao.save(tbUser);
            id=tbUserDao.getNewestUserId();
            tbUser.setId(id);
            res.add("status",1);
            res.add("msg","添加成功");
        }
        if (roleId!=null) {
        	
        	tbResUserRoleDao.delResUserRoleId(tbUser.getId());
            TbResUserRole tbResUserRole = new TbResUserRole();
            tbResUserRole.setUserTabId(tbUser.getId());
            tbResUserRole.setRoleId(roleId);
            tbResUserRole.setInputTime(new Date());
            tbResUserRole.setDelFlag(0);
            tbResUserRoleDao.save(tbResUserRole);
        }
        return true;
    }
}
