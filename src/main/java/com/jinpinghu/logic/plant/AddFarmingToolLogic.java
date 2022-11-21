package com.jinpinghu.logic.plant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import com.google.gson.Gson;
import com.jinpinghu.common.tools.ChangeToolNumberUtil;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbResCropFarmingTool;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.dao.TbResCropFarmingToolDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.AddOrUpdateFarmingToolParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class AddFarmingToolLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateFarmingToolParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateFarmingToolParam myParam=(AddOrUpdateFarmingToolParam)AddOrUpdateFarmingToolParam;
		Integer workId=myParam.getWorkId();
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());

		Date inputTime=StringUtil.isNullOrEmpty(myParam.getInputTime())?new Date():DateTimeUtil.formatTime(myParam.getInputTime());
		String json=myParam.getJson();
	
		
		
		TbResCropFarmingTool tbResCropFarmingTool=null;
		TbResCropFarmingToolDao tbResCropFarmingToolDao=new TbResCropFarmingToolDao(em);
		TbToolDao tbToolDao=new TbToolDao(em);
		//TbFileDao tbFileDao = new TbFileDao(em);
		TbUserDao tbUserDao=new TbUserDao(em);

		String addPeople= tbUserDao.checkLogin(myParam.getUserId(), myParam.getApiKey()).getName();
		
		if(!StringUtil.isNullOrEmpty(json)){
			JSONArray ja=JSONArray.fromObject(json);
			if(ja!=null){
				JSONObject jo=null;
				for(int i=0;i<ja.size();i++){
					jo=ja.getJSONObject(i);
					
					TbTool tbTool=tbToolDao.findById(Integer.valueOf(jo.getString("toolId")));
					BigDecimal takeNum=new BigDecimal(jo.getString("num"));
//					Double totalNum=Double.valueOf(tbTool.getNumber());
//					Double newNum=null;
//					if(takeNum>totalNum){
//						res.add("status", 2)
//						.add("msg", "操作数大于库存！");
//						return false;
					/*}else{
						newNum=totalNum-takeNum;
						tbTool.setNumber(newNum.toString());
						tbToolDao.update(tbTool);
					}*/
					tbResCropFarmingTool=new TbResCropFarmingTool();
					tbResCropFarmingTool.setAddPeople(addPeople);
					tbResCropFarmingTool.setEnterpriseId(Integer.valueOf(enterpriseId));
					tbResCropFarmingTool.setInputTime(inputTime);
					tbResCropFarmingTool.setWorkId(workId);
					tbResCropFarmingTool.setToolId(Integer.valueOf(jo.getString("toolId")));
					tbResCropFarmingTool.setToolName(tbTool.getName());
					if(jo.containsKey("specification"))
					tbResCropFarmingTool.setSpecification(jo.getString("specification"));
					if(jo.containsKey("unit"))
						tbResCropFarmingTool.setUnit(jo.getString("unit"));
					if(jo.containsKey("num"))
						tbResCropFarmingTool.setNum(jo.getString("num"));
					tbResCropFarmingTool.setDelFlag(0);
					tbResCropFarmingToolDao.save(tbResCropFarmingTool);
					ChangeToolNumberUtil changeToolNumberUtil=ChangeToolNumberUtil.init();
					changeToolNumberUtil.minusNumber(Integer.valueOf(jo.getString("toolId")), takeNum, em, enterpriseId, addPeople);
					
					/*List<TbFile> fileList=bean.getFile();
					if(fileList!=null){
						for(TbFile tbFile:fileList){
							tbResCropFarmingFile=new TbResCropFarmingFile();
							if(!StringUtil.isNullOrEmpty(tbFile.getFileSize()))
								tbFile.setFileSize(tbFile.getFileSize());
							if(!StringUtil.isNullOrEmpty(tbFile.getFileName()))
								tbFile.setFileName(tbFile.getFileName());
							tbFile.setFileType(tbFile.getFileType());
							tbFile.setFileUrl(tbFile.getFileUrl());
							tbFileDao.save(tbFile);
							tbResCropFarmingFile.setCropFarmingId(tbResCropFarmingTool.getId());
							tbResCropFarmingFile.setFileId(tbFile.getId());
							tbResCropFarmingFile.setDelFlag(0);
							tbResCropFarmingFileDao.save(tbResCropFarmingFile);
						}
					}*/
				}
			}
		}
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}
}