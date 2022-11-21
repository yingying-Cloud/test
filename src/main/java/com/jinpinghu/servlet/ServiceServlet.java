package com.jinpinghu.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.jinpinghu.servlet.service.GcService;
import com.jinpinghu.servlet.service.InsertFarmingService;


public class ServiceServlet extends HttpServlet implements Servlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		/*
		GcService.start();
		InsertFarmingService.start();*/
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		
		/*GcService.destroy();*/
	}

	public static void main(String[] args) {

		InsertFarmingService.start();
	}
}
