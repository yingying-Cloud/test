package com.jinpinghu.logic.sellBrand;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.db.bean.TbBrand;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResSellBrandFile;
import com.jinpinghu.db.bean.TbSellBrand;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResSellBrandFileDao;
import com.jinpinghu.db.dao.TbSellBrandDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellBrand.param.AddSellBrandFromBrandParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddSellBrandFromBrandLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddSellBrandFromBrandParam myParam = (AddSellBrandFromBrandParam)logicParam;
		String ids = myParam.getIds();
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String[] split = ids.split(",");
		List<String> list = Arrays.asList(split);
		TbSellBrandDao sellBrandDao = new TbSellBrandDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		TbResSellBrandFileDao tbResToolFileDao = new TbResSellBrandFileDao(em);
		TbBrandDao brandDao = new TbBrandDao(em);
		for(String id:list) {
			TbBrand brand = brandDao.findById(Integer.valueOf(id));
			if(brand!=null) {
				TbSellBrand sellBrand = sellBrandDao.findByBrand(Integer.valueOf(id),enterpriseId);
				if(sellBrand!=null) {
					continue;
				}
				TbSellBrand tbSellBrand = new TbSellBrand();
				tbSellBrand.setDelFlag(0);
				tbSellBrand.setDescribe(brand.getDescribe());
				tbSellBrand.setEnterpriseId(enterpriseId);
				tbSellBrand.setProductName(brand.getProductName());
				tbSellBrand.setPrice(brand.getPrice());
				tbSellBrand.setUnit(brand.getUnit());
				tbSellBrand.setType(brand.getType());
				tbSellBrand.setBrandId(Integer.valueOf(id));
				tbSellBrand.setSpec(brand.getSpec());
				tbSellBrand.setStatus(1);
				tbSellBrand.setInputTime(new Date());
				tbSellBrand.setNumber("0");
				brandDao.save(tbSellBrand);
				List<TbFile> tfs =tfDao.findByBrandId(brand.getId());
				if(tfs!=null) {
					for(TbFile tf:tfs) {
						TbFile t = new TbFile();
						t.setFileName(tf.getFileName());
						t.setFileSize(tf.getFileSize());
						t.setFileType(tf.getFileType());
						t.setFileUrl(tf.getFileUrl());
						tfDao.save(t);
						TbResSellBrandFile tbResToolFile = new TbResSellBrandFile();
						tbResToolFile.setDelFlag(0);
						tbResToolFile.setFileId(t.getId());
						tbResToolFile.setSellBrandId(tbSellBrand.getId());
						tbResToolFileDao.save(tbResToolFile);
					}
				}
			}
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
