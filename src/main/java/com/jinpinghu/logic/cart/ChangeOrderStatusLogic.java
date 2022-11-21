package com.jinpinghu.logic.cart;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.bean.TbResOrderCar;
import com.jinpinghu.db.bean.TbShoppingCar;
import com.jinpinghu.db.bean.TbShoppingCarBook;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolOrder;
import com.jinpinghu.db.bean.TbToolOrderBook;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbResOrderCarDao;
import com.jinpinghu.db.dao.TbShoppingCarBookDao;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbToolOrderBookDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.cart.param.AddToCartParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ChangeOrderStatusLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddToCartParam param = (AddToCartParam)logicParam;
		Integer orderId = param.getOrderId();
		Integer status = param.getStatus();
		

		TbToolOrderBookDao dao = new TbToolOrderBookDao(em);
		TbToolOrderBook tbToolOrderBook = dao.findById(orderId);
		tbToolOrderBook.setStatus(status);
		dao.update(tbToolOrderBook);
		Date time = tbToolOrderBook.getInputTime();

		String orderNumber ="1001"+String.format("%011d", Math.abs(UUID.randomUUID().hashCode()))+6;
		//将预约订单同步到订单表
		if(status.equals(2)){
			TbToolOrderDao toDao = new TbToolOrderDao(em);
			TbUserDao uDao = new TbUserDao(em);
			TbShoppingCarBookDao bookDao = new TbShoppingCarBookDao(em);
			TbShoppingCar tbShoppingCar = null;
			TbShoppingCarDao carDao = new TbShoppingCarDao(em);
			TbUser user = uDao.checkLogin2(tbToolOrderBook.getPlantEnterpriseId());
			TbLinkOrderInfoDao infoDao = new TbLinkOrderInfoDao(em);
			
			TbLinkOrderInfo info = infoDao.findByIdcard(user.getExpertIdcard());
			TbUser addPeople = uDao.checkLogin2(param.getUserId());
			
			TbToolOrder tbToolOrder = new TbToolOrder(tbToolOrderBook.getToolEnterpriseId(), info.getId(), addPeople.getName(),
					orderNumber, tbToolOrderBook.getPrice(), 4, null, null, new Date(),
					new Date(), new Date(), new Date(), new Date(), null, null,0);
			tbToolOrder.setCheck(0);
			tbToolOrder.setType(2);
			toDao.save(tbToolOrder);
			//同步订单子表
			List<TbShoppingCarBook> list = bookDao.findByUserIdAndToolId(orderId);
			for(TbShoppingCarBook book : list){
				TbToolDao toolDao = new TbToolDao(em);
				TbTool tool = toolDao.findById(book.getToolId());
				tbShoppingCar = new TbShoppingCar(tbToolOrderBook.getToolEnterpriseId(),2, book.getToolId(), book.getNum(),
						book.getPrice(),0,0,new Date(), book.getPrice(), tool.getUniformPrice());
				carDao.save(tbShoppingCar);
			}
			
			//同步关联表 tb_res_order_car
			TbResOrderCarDao resDao = new TbResOrderCarDao(em);
			TbResOrderCar orderRes = new TbResOrderCar(tbShoppingCar.getId(), tbToolOrder.getId(),0);
			resDao.save(orderRes);
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
