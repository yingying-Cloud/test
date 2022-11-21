package com.jinpinghu.logic.device;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.dao.TbDeciveDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.device.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetDeviceInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam simpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)simpleParam;
		Integer id=Integer.valueOf(myParam.getIds());
		
		Map<String,Object> map;
		TbDeciveDao ddao=new TbDeciveDao(em);
		Object[] o=ddao.getInfo(id);

			map=new HashMap<String,Object>();
			map.put("id", o[0]);
			map.put("deviceName", o[1]);
			map.put("type", o[2]);
			map.put("inputTime", DateTimeUtil.formatTime((Date)o[3]));
			if(o[4]!=null&&o[8]!=null){
				map.put("greenhousesName", o[4]);
				map.put("greenhousesId", o[8]);
			}
			map.put("baseName", o[5]);
			map.put("deviceSn", o[6]);
			map.put("describe", o[7]);
			
			map.put("installTime", o[9]==null?null:DateTimeUtil.formatTime((Date)o[9]));
			map.put("installAddress", o[10]);
			map.put("factory", o[11]);
			map.put("equipmentType", o[12]);
			
			map.put("closeInstruction", o[13]);
			map.put("openInstruction", o[14]);
			map.put("searchInstruction", o[15]);
			map.put("classify", o[16]);
			map.put("landName", o[17]);
			map.put("area", o[18] == null ? "" : o[18].toString());
			if(o[0]!=null) {
				TbFileDao tfDao = new TbFileDao(em);
				List<TbFile> tfs =tfDao.findByDeviceId(Integer.valueOf(o[0].toString()));
				JSONArray ja = new JSONArray();
				if(tfs!=null) {
					for(TbFile tf:tfs) {
						JSONObject file = new JSONObject();
						file.put("id",tf.getId());
						file.put("fileName", tf.getFileName());
						file.put("fileSize", tf.getFileSize());
						file.put("fileType", tf.getFileType());
						file.put("fileUrl",  tf.getFileUrl());
						ja.add(file);
					}
					map.put("file", ja);
				}
			}
	
		res.add("status", 1)
		.add("msg", "查询成功！")
		.add("result", map);
		return true;
	}

}
