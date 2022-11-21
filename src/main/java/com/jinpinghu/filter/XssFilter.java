package com.jinpinghu.filter;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.gson.Gson;
import com.jinpinghu.db.bean.TbCallRecords;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicFilter;
import fw.jbiz.logic.ZLogicParam;

public class XssFilter extends ZLogicFilter{

	@Override
	public boolean doFilterBefore(ZLogicParam logicParam, ZSimpleJsonObject var2,EntityManager em) {
		// TODO Auto-generated method stub
		try {
			if (logicParam != null) {
				if (logicParam.getRequest().getRequestURL().toString().indexOf("rotation/getRotation.do") < 0) {
					Gson gson = new Gson();
					String parameter = gson.toJson(logicParam);
					Date now = new Date();
					TbCallRecords tbCallRecords = new TbCallRecords(logicParam.getUserId(),
								logicParam.getRequest().getRequestURL().toString(), now,
								parameter);
					em.persist((Object) tbCallRecords);
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
		
	}
	
	public static void main(String[] args) {
		System.out.println(StringEscapeUtils.escapeHtml4("<script>alert('xss')</script>"));
	}

	@Override
	public boolean doFilterAfter(ZLogicParam logicParam, ZSimpleJsonObject res,EntityManager em) {
		// TODO Auto-generated method stub
		
		return true;
	}

}
