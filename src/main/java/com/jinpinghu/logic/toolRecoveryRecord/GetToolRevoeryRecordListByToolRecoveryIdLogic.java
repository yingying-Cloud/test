package com.jinpinghu.logic.toolRecoveryRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbToolRecoveryRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecoveryRecord.param.GetToolRevoeryRecordListByToolRecoveryIdParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetToolRevoeryRecordListByToolRecoveryIdLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetToolRevoeryRecordListByToolRecoveryIdParam myParam = (GetToolRevoeryRecordListByToolRecoveryIdParam)logicParam;
		String recoveryUnit = myParam.getRecoveryUnit();
		String sellUnit = myParam.getSellUnit();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		Integer toolRecoveryId = myParam.getToolRecoveryId();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		Integer enterpriseId = myParam.getEnterpriseId();
		
		TbToolRecoveryRecordDao recordDao = new TbToolRecoveryRecordDao(em);
		
		Integer maxCount = recordDao.getListCountByToolRecoveryId(toolRecoveryId, recoveryUnit, sellUnit, startTime, endTime,enterpriseId);
		
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		if(maxCount==null||maxCount==0){
			res.add("allCounts",0);
			res.add("maxPage",0);
			res.add("result", resultList);
			res.add("status", 1);
			res.add("msg", "无记录");
			return true;
		}
		int maxPage = 1;
		if(pageCount != null) {
			maxPage = maxCount/pageCount;
			if(maxCount%pageCount!=0){
				maxPage++;
			}
			if (pageCount * nowPage >= (maxCount + pageCount) && maxPage != 0) {
				nowPage = maxPage;
				res.add("allCounts",maxCount);
				res.add("maxPage",maxPage);
				res.add("result", resultList);
				res.add("status", 1);
				res.add("msg", "该页无记录");
				return true;
			}else if(maxPage == 0){
				nowPage = 1;
			}
		}
		
		resultList = recordDao.getListByToolRecoveryId(toolRecoveryId, recoveryUnit, sellUnit, startTime, endTime, nowPage, pageCount,enterpriseId);
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
