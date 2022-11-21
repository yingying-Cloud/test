package com.jinpinghu.logic.order;

import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.FindLinkOrderInfoListParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.*;

public class FindLinkOrderInfoListLogin extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        FindLinkOrderInfoListParam myParam=(FindLinkOrderInfoListParam) logicParam;
        String name=myParam.getName();
        Integer type=myParam.getType();
        Date startTime=myParam.getStartTime();
        Date endTime=myParam.getEndTime();
        Integer nowPage=myParam.getNowPage();
        Integer pageCount=myParam.getPageCount();
        TbLinkOrderInfoDao tbLinkOrderInfoDao=new TbLinkOrderInfoDao(em);
        List<Object[]> list=tbLinkOrderInfoDao.selLinkOrderInfoList(name,type,startTime,endTime,nowPage,pageCount);
        Integer allCounts=tbLinkOrderInfoDao.selLinkOrderInfoListCount(name,type,startTime,endTime);
        List<Map<String,Object>> mapList=new ArrayList<>();
        Map<String,Object> map=null;
        if (list!=null){
            for(int i=0;i<list.size();i++){
                map=new HashMap<>();
                map.put("id",list.get(i)[0]);
                map.put("enterpriseId",list.get(i)[1]);
                map.put("zoneName",list.get(i)[2]);
                map.put("name",list.get(i)[3]);
                map.put("creditCode",list.get(i)[4]);
                map.put("legalPerson",list.get(i)[5]);
                map.put("legalPersonIdcard",list.get(i)[6]);
                map.put("linkPeople",list.get(i)[7]);
                map.put("linkMobile",list.get(i)[8]);
                map.put("address",list.get(i)[9]);
                map.put("inputTime",list.get(i)[10]);
                map.put("area",list.get(i)[11]);
                map.put("type",list.get(i)[12]);
                mapList.add(map);
            }
            res.add("result",mapList);
            res.add("status",1);
            res.add("msg","获取成功");
            res.add("allCounts", allCounts);
            if (pageCount!=null) {
                res.add("maxPage", (allCounts % pageCount) != 0 ? (allCounts / pageCount + 1) : (allCounts / pageCount));
            }else{
                res.add("maxPage", 1);
            }
        }else{
            res.add("result",new ArrayList<>());
            res.add("status",1);
            res.add("msg","获取失败");
            res.add("allCounts", 0);
            res.add("maxPage", 0);
        }
        return true;
    }
}
