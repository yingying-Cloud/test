package com.jinpinghu.logic.export;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResToolCatalogFile;
import com.jinpinghu.db.bean.TbResToolFile;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResToolFileDao;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.tool.param.ImportToolParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ImportAllToolLogic extends BaseZLogic{
	
	private Logger log = Logger.getLogger(ImportAllToolLogic.class);

	@SuppressWarnings("unchecked")
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ImportToolParam myParam=(ImportToolParam)logicParam;
		HttpServletRequest request = myParam.getRequest();
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
							if(sheetName.equals("tool")) {
								int rowNo = sheet.getLastRowNum();
								// 得到行数
								for (int j = 1; j <= rowNo; j++) {
									HSSFRow row = sheet.getRow(j);
									String id = row.getCell(16).getStringCellValue();
									String syncNumber = row.getCell(19).getStringCellValue();
									TbTool tool = tbToolDao.findById(Integer.valueOf(id));
									if(tool!=null) {
										tool.setSyncNumber(syncNumber);
										tbToolDao.update(tool);
									}
								}
							}else {
								File thisFile = new File(filePath);
								thisFile.delete();
								res.add("status", 2);
								res.add("msg", "请选择其他模板！");
								return true;
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
