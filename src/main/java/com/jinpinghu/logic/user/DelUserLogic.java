package com.jinpinghu.logic.user;

import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.DelUserParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;

/**
 * @Author: yanchengjie
 * @Date: 2019/8/28 10:53
 */
public class DelUserLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        DelUserParam myParam=(DelUserParam)logicParam;
        Integer id=myParam.getId();
        TbUserDao tbUserDao=new TbUserDao(em);
        TbResUserRoleDao tbResUserRoleDao=new TbResUserRoleDao(em);
        TbResUserEnterpriseDao tbResUserEnterpriseDao=new TbResUserEnterpriseDao(em);
        Integer flag=tbUserDao.delUserById(id);
        if (flag>0){
            res.add("status",1);
            res.add("msg","删除成功");
        }else{
            res.add("status",0);
            res.add("msg","删除失败");
        }
        tbResUserRoleDao.delResUserRoleId(id);
        tbResUserEnterpriseDao.delResUserEnterpriseId(id);
        return true;
    }
}
