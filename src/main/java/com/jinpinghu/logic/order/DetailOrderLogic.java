	package com.jinpinghu.logic.order;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.bean.TbToolOrder;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.DetailOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class DetailOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DetailOrderParam myParam =(DetailOrderParam)logicParam;
		String id =myParam.getId();
		
		TbToolOrderDao toDao =new TbToolOrderDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbShoppingCarDao shoppingCarDao = new TbShoppingCarDao(em);
		TbLinkOrderInfoDao orderInfoDao = new TbLinkOrderInfoDao(em);
		TbFileDao fileDao = new TbFileDao(em);
		TbToolOrder toolOrder = toDao.findById(Integer.valueOf(id));
		JSONObject jo = new JSONObject();
		if(toolOrder!=null) {
			jo.put("id",toolOrder.getId() );
			jo.put("addPeople",toolOrder.getAddPeople());
			jo.put("cancleInfo",toolOrder.getCancelInfo() );
			jo.put("orderNumber",toolOrder.getOrderNumber());
			jo.put("plantEnterpriseId",toolOrder.getPlantEnterpriseId());
			jo.put("price",toolOrder.getPrice() );
			jo.put("rejectedInfo",toolOrder.getRejectedInfo() );
			jo.put("status",toolOrder.getStatus() );
			jo.put("inputTime",toolOrder.getInputTime()==null?"":DateTimeUtil.formatTime2(toolOrder.getInputTime()) );
			jo.put("timeSend",toolOrder.getInputTime()==null?"":DateTimeUtil.formatTime2(toolOrder.getTimeSend()));
			jo.put("timeAudit",toolOrder.getTimeAudit()==null?"":DateTimeUtil.formatTime2(toolOrder.getTimeAudit()));
			jo.put("timeCancel",toolOrder.getTimeCancel()==null?"":DateTimeUtil.formatTime2(toolOrder.getTimeCancel()));
			jo.put("timeComplete",toolOrder.getTimeComplete()==null?"":DateTimeUtil.formatTime2(toolOrder.getTimeComplete()));
			jo.put("timePay",toolOrder.getTimePay()==null?"":DateTimeUtil.formatTime2(toolOrder.getTimePay() ));
			jo.put("timeRejected",toolOrder.getTimeRejected()==null?"":DateTimeUtil.formatTime2(toolOrder.getTimeRejected()));
			jo.put("toolEnterpriseId",toolOrder.getToolEnterpriseId() );
			jo.put("type",toolOrder.getType());
			jo.put("pic",StringUtils.trimToEmpty(toolOrder.getPic()));
			List<Map<String,Object>> carInfo = shoppingCarDao.findInOrderId(toolOrder.getId(),null);
			jo.put("carInfo", carInfo);
			if(toolOrder.getType()==1) {//企业
				TbEnterprise enterpriseP = enterpriseDao.findById(toolOrder.getPlantEnterpriseId());
				if(enterpriseP!=null) {
					jo.put("plantEnterpriseName",enterpriseP.getName() );
					jo.put("plantEnterpriseCreditCode",enterpriseP.getEnterpriseCreditCode() );
					jo.put("plantEnterpriseLinkMobile",enterpriseP.getEnterpriseLinkMobile() );
					jo.put("plantEnterpriseLegalPersonIdcard",enterpriseP.getEnterpriseLegalPersonIdcard() );
					jo.put("plantEnterpriseLinkPeople",enterpriseP.getEnterpriseLinkPeople() );
					jo.put("plantBaseAddress",enterpriseP.getBaseAddress());
					jo.put("plantEnterpriseAddress",enterpriseP.getEnterpriseAddress());
					jo.put("plantArea",enterpriseP.getPlantScope());
					TbFile file = fileDao.findOneByEnterpriseId(toolOrder.getPlantEnterpriseId());
					if(file!=null) {
						jo.put("file1", file.getFileUrl());
					}
				}
			}else if(toolOrder.getType()==2) {//人
				TbLinkOrderInfo orderInfo = orderInfoDao.findById(toolOrder.getPlantEnterpriseId());
				if(orderInfo!=null) {
					jo.put("name",orderInfo.getName() );
					jo.put("creditCode",orderInfo.getCreditCode() );
					jo.put("linkMobile",orderInfo.getLinkMobile() );
					jo.put("linkPeople",orderInfo.getLinkPeople() );
					jo.put("address",orderInfo.getAddress());
					jo.put("area",orderInfo.getArea());
					jo.put("legalPersonIdcard",orderInfo.getLegalPersonIdcard());
					jo.put("infoType",orderInfo.getType());
					TbFile file = fileDao.findOneByOrderInfoId(toolOrder.getId());
					if(file!=null) {
						jo.put("file2", file.getFileUrl());
					}
				}
			}
			TbEnterprise enterpriseT = enterpriseDao.findById(toolOrder.getToolEnterpriseId());
			if(enterpriseT!=null) {
				jo.put("toolEnterpriseName",enterpriseT.getName() );
				jo.put("toolEnterpriseLinkMobile",enterpriseT.getEnterpriseLinkMobile() );
				jo.put("toolEnterpriseLinkPeople",enterpriseT.getEnterpriseLinkPeople() );
				jo.put("toolBaseAddress",enterpriseT.getBaseAddress());
				jo.put("toolEnterpriseCreditCode",enterpriseT.getEnterpriseCreditCode() );
				jo.put("toolEnterpriseLegalPersonIdcard",enterpriseT.getEnterpriseLegalPersonIdcard() );
				jo.put("toolEnterpriseAddress",enterpriseT.getEnterpriseAddress());
				TbFile file = fileDao.findOneByEnterpriseId(toolOrder.getToolEnterpriseId());
				if(file!=null) {
					jo.put("file3", file.getFileUrl());
				}
			}
			
			TbEnterprise buyEnterprise = enterpriseDao.findByToolOrderId(toolOrder.getId());
			if(buyEnterprise!=null) {
				jo.put("buyEnterpriseName",buyEnterprise.getName() );
			}
		}
		res.add("msg", "查询成功");
		res.add("status", 1).add("result", jo);
		return true;
	}

}
