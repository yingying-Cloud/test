package com.jinpinghu.logic.plantServiceOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPlantServiceOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantServiceOrder.param.GetOrderInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetOrderInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetOrderInfoParam myParam = (GetOrderInfoParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		
		TbPlantServiceOrderDao plantServiceOrderDao = new TbPlantServiceOrderDao(em);
		TbFileDao fileDao = new TbFileDao(em);
		
		Map<String, Object> order = plantServiceOrderDao.getPlantServiceOrderInfo(id);
		
		if(order == null) {
			res.add("status", -1).add("msg", "订单不存在");
			return false;
		}else {
			Integer completeId = order.containsKey("completeId") ? (Integer)order.get("completeId") : null;
			List<TbFile> files = fileDao.findByPlantServiceOrderCompleteId(completeId);
			List<Map<String, Object>> fileList = new ArrayList<Map<String,Object>>();
			if(files!=null) {
				for(TbFile tf:files) {
					Map<String, Object> file = new HashMap<>();
					file.put("id",tf.getId());
					file.put("fileName", tf.getFileName());
					file.put("fileSize", tf.getFileSize());
					file.put("fileType", tf.getFileType());
					file.put("fileUrl",  tf.getFileUrl());
					fileList.add(file);
				}
				order.put("file", fileList);
			}
		}
		
		res.add("status", 1).add("msg", "操作成功").add("result", order);
		return true;
	}
	
}