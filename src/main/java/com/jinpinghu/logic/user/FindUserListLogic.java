package com.jinpinghu.logic.user;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.FindUserListParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yanchengjie
 * @Date: 2019/8/28 11:48
 */
public class FindUserListLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        FindUserListParam myParam=(FindUserListParam)logicParam;
        String name=myParam.getName();
        String mobile = myParam.getMobile();
        String roleId = myParam.getRoleId();
        Integer nowPage=myParam.getNowPage();
        Integer pageCount=myParam.getPageCount();
        String dscd = StringUtil.handleArea(myParam.getDscd());
        TbUserDao tbUserDao=new TbUserDao(em);
        List<String> asList = null;
        if(!StringUtils.isEmpty(roleId))
        	asList = Arrays.asList(roleId.split(","));
        
        List<Object[]> list=tbUserDao.findUserList(name,mobile,asList,nowPage,pageCount,dscd);
        BigInteger allCount=tbUserDao.findUserListCount(name,mobile,asList,dscd);
        List<Map<String,Object>> mapList=new ArrayList<>();
        Map<String,Object> map=null;
        if (list!=null){
            for(Object[] objs:list){
                map=new HashMap<>();
                map.put("id",objs[0]);
                map.put("mobile",objs[1]);
                map.put("regTime",objs[2]);
                map.put("name",objs[3]);
                map.put("expertIdCard",objs[4]);
                map.put("expertField",objs[5]);
                map.put("enterpriseName",objs[6]);
                map.put("roleName",objs[7]);
                map.put("lastTime",objs[8]);
                map.put("dscd",objs[9]);
                map.put("dsnm",objs[10]);
                map.put("roleId",objs[11]);
                mapList.add(map);
            }
            res.add("allCounts",allCount);
            res.add("status",1);
            res.add("msg","查询成功");
            res.add("result",mapList);
        }else{
            res.add("allCounts",allCount);
            res.add("status",1);
            res.add("msg","查询失败");
            res.add("result",new ArrayList<>());
        }
        return true;
    }
}
