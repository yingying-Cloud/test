package com.jinpinghu.logic.zone;

import com.jinpinghu.db.dao.TbZoneDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.zone.param.GetZoneListParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetZoneListLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        GetZoneListParam myParam=(GetZoneListParam)logicParam;
        String name=myParam.getName();
        Integer enterpriseId = myParam.getEnterpriseId();
        Integer nowPage=myParam.getNowPage();
        Integer pageCount=myParam.getPageCount();
        TbZoneDao tbZoneDao=new TbZoneDao(em);
        Integer allCounts=tbZoneDao.findTbZoneListCount(name);
        List<Object[]> list=tbZoneDao.findTbZoneList(name,nowPage,pageCount,enterpriseId);
        List<Map<String,Object>> mapList=new ArrayList<>();
        Map<String,Object> map=null;
        if (list!=null){
            for(int i=0;i<list.size();i++){
                map=new HashMap<>();
                map.put("id",list.get(i)[0]);
                map.put("code",list.get(i)[1]);
                map.put("name",list.get(i)[2]);
                map.put("area",list.get(i)[3]);
                map.put("describe",list.get(i)[4]);
                map.put("enterpriseId",list.get(i)[5]);
                map.put("enterpriseName",list.get(i)[6]);
                mapList.add(map);
            }
            res.add("msg","成功");
            res.add("status",1);
            res.add("result",mapList);
            res.add("allCounts", allCounts);
            if (pageCount!=null) {
                res.add("maxPage", (allCounts % pageCount) != 0 ? (allCounts / pageCount + 1) : (allCounts / pageCount));
            }else{
                res.add("maxPage", 1);
            }
        }
        else{
            res.add("result",new ArrayList<>());
            res.add("status",0);
            res.add("msg","失败");
            res.add("allCounts", 0);
            res.add("maxPage", 0);
        }
        return true;
    }
}
