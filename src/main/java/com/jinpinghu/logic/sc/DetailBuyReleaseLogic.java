package com.jinpinghu.logic.sc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbBuyReleaseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.DetailBuyReleaseParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DetailBuyReleaseLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		DetailBuyReleaseParam myParam = (DetailBuyReleaseParam)logicParam;
		Integer id = myParam.getId();
		
		TbBuyReleaseDao buyReleaseDao = new TbBuyReleaseDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		
		Map<String, Object> result = buyReleaseDao.detailBuyRelease(id);
		
		if (result != null) {
			List<TbFile> tfs =tfDao.findByBuyReleaseId(id);
			List<Map<String, Object>> fileList = new ArrayList<Map<String,Object>>(tfs.size());
			Map<String, Object> file;
			for(TbFile tf:tfs) {
				file = new HashMap<String,Object>();
				file.put("id",tf.getId());
				file.put("fileName", tf.getFileName());
				file.put("fileSize", tf.getFileSize());
				file.put("fileType", tf.getFileType());
				file.put("fileUrl",  tf.getFileUrl());
				fileList.add(file);
			}
			result.put("file", fileList);
		}
		
		res.add("status", 1).add("msg", "操作成功").add("result", result);
		return true;
	}

}
