package com.jinpinghu.logic.brandOrder;

import com.jinpinghu.db.bean.TbBrand;
import com.jinpinghu.db.bean.TbBrandOrder;
import com.jinpinghu.db.bean.TbBrandShoppingCar;
import com.jinpinghu.db.bean.TbResBrandCarNum;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbBrandOrderDao;
import com.jinpinghu.db.dao.TbBrandShoppingCarDao;
import com.jinpinghu.db.dao.TbResBrandCarNumDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandOrder.param.AddShoppingCarNumParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class AddShoppingCarNumLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        AddShoppingCarNumParam myParam = (AddShoppingCarNumParam)logicParam;
        Integer carId = StringUtils.isNullOrEmpty(myParam.getCarId())?null:Integer.valueOf(myParam.getCarId());
        String numJa = myParam.getNumJa();
        TbResBrandCarNumDao brandCarNumDao = new TbResBrandCarNumDao(em);
        TbBrandShoppingCarDao brandShoppingCarDao = new TbBrandShoppingCarDao(em);
        TbBrandShoppingCar car = brandShoppingCarDao.findById(carId);
        if(car==null){
            res.add("status",1).add("msg","请选择商品");
            return false;
        }
        BigDecimal allNum = BigDecimal.ZERO;
        if(!StringUtils.isNullOrEmpty(numJa)){
            JSONArray ja = JSONArray.fromObject(numJa);
            for(int i =0;i<ja.size();i++){
                JSONObject jo = (JSONObject) ja.get(i);
                if(jo.containsKey("trademarkId")){
                    Integer enterpriseId = jo.getInt("trademarkId");
                    TbResBrandCarNum resBrandCarNum = brandCarNumDao.findByCarIdAndEnterpriseId(carId, enterpriseId);
                    if(resBrandCarNum!=null) {  //非空更新数量
                        resBrandCarNum.setEnterpriseId(enterpriseId);
                        if(jo.containsKey("num")){
                            resBrandCarNum.setNum(jo.getString("num"));
                        }
                        resBrandCarNum.setBrandCarId(carId);
                        brandCarNumDao.update(resBrandCarNum);
                    }else{ //为空添加记录
                        resBrandCarNum = new TbResBrandCarNum();
                        resBrandCarNum.setEnterpriseId(enterpriseId);
                        if(jo.containsKey("num")){
                            resBrandCarNum.setNum(jo.getString("num"));
                        }
                        resBrandCarNum.setBrandCarId(carId);
                        resBrandCarNum.setDelFlag(0);
                        brandCarNumDao.save(resBrandCarNum);
                    }
                    allNum = allNum.add(new BigDecimal(resBrandCarNum.getNum()));
                }
            }
        }
        if(allNum.compareTo(new BigDecimal(car.getNum()))==1){
            res.add("status",1).add("msg","超出购物车数量！");
            return false;
        }
//        else {
//        	if(car.getType()!=null&&car.getType()==1) {
//	        	TbBrandDao brandDao = new TbBrandDao(em);
//	    		TbBrand brand = brandDao.findById(car.getBrandId());
//	        	TbBrandOrderDao tbBrandOrderDao = new TbBrandOrderDao(em);
//	        	TbBrandOrder tbBrandOrder = tbBrandOrderDao.findByCarId(car.getId());
//	        	BigDecimal oldOrderPrice = new BigDecimal(tbBrandOrder.getPrice());
//	        	BigDecimal oldCarPrice = new BigDecimal(car.getPrice());
//	        	car.setNum(allNum.toString());
//	        	BigDecimal newCarPrice = new BigDecimal(brand.getPrice()).multiply(new BigDecimal(car.getNum())).setScale(2, BigDecimal.ROUND_HALF_UP);
//	        	BigDecimal newOrderPrice = oldOrderPrice.subtract(oldCarPrice).add(newCarPrice);
//	        	car.setPrice(newCarPrice.toString());
//	        	brandShoppingCarDao.update(car);
//	        	tbBrandOrder.setPrice(newOrderPrice.toString());
//	        	tbBrandOrderDao.update(tbBrandOrder);
//        	}
//        }
        res.add("status",1).add("msg","操作成功");
        return true;
    }
}
