package com.jinpinghu.logic.tool;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.tool.param.ImportToolParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ImportToolNumberLogic extends BaseZLogic{
	
	private Logger log = Logger.getLogger(ImportToolNumberLogic.class);

	@SuppressWarnings("unchecked")
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ImportToolParam myParam=(ImportToolParam)logicParam;
		HttpServletRequest request = myParam.getRequest();
		Integer enterpriseId = Integer.valueOf(request.getParameter("enterpriseId"));
		String upload_file_path=request.getSession().getServletContext().getRealPath("/")+"upload/";
		File file = new File(upload_file_path);
		if(!file.exists())
			file.mkdir();
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024*1024);
		factory.setRepository(file);
		
		ServletFileUpload sfu = new ServletFileUpload(factory); // 实例化一个servletFileUpload对象
		sfu.setHeaderEncoding("utf-8");                     // 解决上传文件乱码问题
		
		String fileName = "";
		
		try {
			List<FileItem> list = sfu.parseRequest(request);
			if(list != null && list.size()>0) {
				for(int i=0;i<list.size();i++) {
					FileItem item = list.get(i);
					if (item.isFormField()) {             		// 如果上传的这个文件只是一个表单字段，而不是一个文件
					}else {
						// 得到文件名
						fileName = DateTimeUtil.formatSelf(new Date(), "yyyyMMddHHmmssSSS")+".xls";
						// 读取文件的内容
						item.write(new File(upload_file_path, fileName));
					}
				}
			}

		}catch(Exception ex) {
			res.add("status", -1);
			res.add("msg", "文件解析错误");
			return false;
		}
		String filePath = upload_file_path+fileName;
		
		HSSFWorkbook work = new HSSFWorkbook(new FileInputStream(filePath));// 得到这个excel表格对象
			
			
			TbToolDao tbToolDao = new TbToolDao(em);
					if(work != null) {
						for(int i=0;i<work.getNumberOfSheets();i++) {
							HSSFSheet sheet = work.getSheetAt(i);
							String sheetName = sheet.getSheetName();
							if(sheetName.equals("sheet1")) {
								int rowNo = sheet.getLastRowNum();
								// 得到行数
								for (int j = 1; j <= rowNo; j++) {
									HSSFRow row = sheet.getRow(j);
									
									String code = "";
									String number = "";
									
									if(row.getCell(1)!=null) {
										row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
										code = row.getCell(1).getStringCellValue();
									}
									if(row.getCell(7)!=null) {
										row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
										number = row.getCell(7).getStringCellValue();
									}

									TbTool tool = tbToolDao.findByCode(code, enterpriseId);
									
									if(tool!=null) {
										tool.setNumber(number);
										tbToolDao.update(tool);
									}
								}
							}
						}
					}
					File thisFile = new File(filePath);
					thisFile.delete();
			res.add("status", 1);
			res.add("msg", "操作成功");
			return true;
		}
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}

}
