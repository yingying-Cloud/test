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

public class ImportToolLogic extends BaseZLogic{
	
	private Logger log = Logger.getLogger(ImportToolLogic.class);

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
			
			TbFileDao tfDao = new TbFileDao(em);
			TbResToolFileDao trfgDao = new TbResToolFileDao(em);
			TbToolDao tbToolDao = new TbToolDao(em);
			TbToolCatalogDao toolCatalogDao = new TbToolCatalogDao(em);
					
					if(work != null) {
						for(int i=0;i<work.getNumberOfSheets();i++) {
							HSSFSheet sheet = work.getSheetAt(i);
							String sheetName = sheet.getSheetName();
							if(sheetName.equals("product")) {
								int rowNo = sheet.getLastRowNum();
								// 得到行数
								for (int j = 1; j <= rowNo; j++) {
									HSSFRow row = sheet.getRow(j);
									
									row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
									row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
									
									String type = row.getCell(0).getStringCellValue();
									String productAttributes = row.getCell(1).getStringCellValue();
									switch(type) {
										case "27":
											type="14";
											break;
										case "28":
											type="13";
											break;
										case "29":
											type="12";
											break;
										case "30":
											type="16";
											break;
										case "31":
											type="15";
											break;
										default :
											type="17";
											break;
									}
									switch(productAttributes) {
									case "41":
										productAttributes="杀虫剂";
										break;
									case "42":
										productAttributes="杀螨剂";
										break;
									case "43":
										productAttributes="杀菌剂";
										break;
									case "44":
										productAttributes="除草剂";
										break;
									case "45":
										productAttributes="杀鼠剂";
										break;
									case "46":
										productAttributes="植物生长调节剂";
										break;
									case "47":
										productAttributes="氮肥";
										break;
									case "48":
										productAttributes="磷肥";
										break;
									case "49":
										productAttributes="钾肥";
										break;
									case "50":
										productAttributes="复混（合）肥料";
										break;
									case "51":
										productAttributes="有机肥料";
										break;
									case "52":
										productAttributes="有机无机复混肥料";
										break;
									case "53":
										productAttributes="水溶肥料";
										break;
									case "113":
										productAttributes="床土调酸剂";
										break;
									case "32":
										productAttributes="主要农作物种子";
										break;
									case "33":
										productAttributes="非主要农作物种子";
										break;
								}
									
									String code = "";
									String name = "";
									String unit= "";
									String price= "";
									String wholesalePrice= "";
									String productionUnits= "";
									String registrationCertificateNumber= "";
									
									String registrationCertificateNumber1= "";
									String registrationCertificateNumber2= "";
									
									String explicitIngredients= "";
									String dosageForms= "";
									String toxicity= "";
									String specification= "";
									String dnm = "";
									String file1 = "";
									String file2 = "";
									String file3 = "";
									
									if(row.getCell(36)!=null) {
										row.getCell(36).setCellType(Cell.CELL_TYPE_STRING);
										file1 = row.getCell(36).getStringCellValue();
									}
									if(row.getCell(37)!=null) {
										row.getCell(37).setCellType(Cell.CELL_TYPE_STRING);
										file2 = row.getCell(37).getStringCellValue();
									}
									if(row.getCell(38)!=null) {
										row.getCell(38).setCellType(Cell.CELL_TYPE_STRING);
										file3 = row.getCell(38).getStringCellValue();
									}
									if(row.getCell(39)!=null) {
										row.getCell(39).setCellType(Cell.CELL_TYPE_STRING);
										dnm = row.getCell(39).getStringCellValue();
									}
									if(row.getCell(3)!=null) {
										row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
										code = row.getCell(3).getStringCellValue();
									}
									if(row.getCell(4)!=null) {
										row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
										name = row.getCell(4).getStringCellValue();
									}
									if(row.getCell(47)!=null) {
										row.getCell(47).setCellType(Cell.CELL_TYPE_STRING);
										unit=row.getCell(47).getStringCellValue();
									}
									if(row.getCell(23)!=null) {
										row.getCell(23).setCellType(Cell.CELL_TYPE_STRING);
										price=row.getCell(23).getStringCellValue();
									}
									if(row.getCell(45)!=null) {
										row.getCell(45).setCellType(Cell.CELL_TYPE_STRING);
										wholesalePrice=row.getCell(45).getStringCellValue();
									}
									if(row.getCell(14)!=null) {
										row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
										productionUnits=row.getCell(14).getStringCellValue();
									}
									if(row.getCell(8)!=null) {
										row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
										registrationCertificateNumber1=row.getCell(8).getStringCellValue();
									}
									if(row.getCell(9)!=null) {
										row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
										registrationCertificateNumber2=row.getCell(9).getStringCellValue();
									}
									
									registrationCertificateNumber = StringUtils.isEmpty(registrationCertificateNumber1)?registrationCertificateNumber2:registrationCertificateNumber1;
									
									if(row.getCell(5)!=null) {
										row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
										explicitIngredients=row.getCell(5).getStringCellValue();
									}
									if(row.getCell(17)!=null) {
										row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
										dosageForms=row.getCell(17).getStringCellValue();
									}
									if(row.getCell(18)!=null) {
										row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
										toxicity=row.getCell(18).getStringCellValue();
									}
									if(row.getCell(10)!=null) {
										row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
										specification=row.getCell(10).getStringCellValue();
									}
									
									TbToolCatalog toolCatelog = toolCatalogDao.findByCodeUserId(code, myParam.getUserId());
									if(toolCatelog==null) {
										toolCatelog = new TbToolCatalog();
										toolCatelog.setDelFlag(0);
										toolCatelog.setName(name);
										toolCatelog.setPrice(price);
										toolCatelog.setWholesalePrice(wholesalePrice);
										toolCatelog.setSpecification(specification);
										toolCatelog.setUnit(unit);
										toolCatelog.setType(Integer.valueOf(type));
										toolCatelog.setProductAttributes(productAttributes);
										toolCatelog.setRegistrationCertificateNumber(registrationCertificateNumber);
										toolCatelog.setProductionUnits(productionUnits);
										toolCatelog.setExplicitIngredients(explicitIngredients);
										toolCatelog.setDosageForms(dosageForms);
										toolCatelog.setToxicity(toxicity);
										toolCatelog.setCode(code);
										toolCatelog.setStatus(1);
										toolCatelog.setUserId(myParam.getUserId());
										toolCatelog.setInputTime(new Date());
										toolCatelog.setUniformPrice("2");
										toolCatalogDao.save(toolCatelog);
										
										if(!StringUtils.isEmpty(file1)) {
											TbFile tfe  = new TbFile();
											tfe.setFileName(file1.substring(file1.lastIndexOf("/")+1));
											tfe.setFileType(1);
											tfe.setFileUrl("http://220.189.248.54:8080"+file1);
											tfDao.save(tfe);
											TbResToolCatalogFile trpf=new TbResToolCatalogFile();
											trpf.setFileId(tfe.getId());
											trpf.setToolCatalogId(Integer.valueOf(toolCatelog.getId()));
											trpf.setDelFlag(0);
											trfgDao.save(trpf);
										}
										if(!StringUtils.isEmpty(file2)) {
											TbFile tfe2 = new TbFile();
											tfe2.setFileName(file2.substring(file2.lastIndexOf("/")+1));
											tfe2.setFileType(1);
											tfe2.setFileUrl("http://220.189.248.54:8080"+file2);
											tfDao.save(tfe2);
											TbResToolCatalogFile trpf2=new TbResToolCatalogFile();
											trpf2.setFileId(tfe2.getId());
											trpf2.setToolCatalogId(Integer.valueOf(toolCatelog.getId()));
											trpf2.setDelFlag(0);
											trfgDao.save(trpf2);
										}
										if(!StringUtils.isEmpty(file3)) {
											TbFile tfe3 = new TbFile();
											tfe3.setFileName(file3.substring(file3.lastIndexOf("/")+1));
											tfe3.setFileType(1);
											tfe3.setFileUrl("http://220.189.248.54:8080"+file3);
											tfDao.save(tfe3);
											TbResToolCatalogFile trpf3=new TbResToolCatalogFile();
											trpf3.setFileId(tfe3.getId());
											trpf3.setToolCatalogId(Integer.valueOf(toolCatelog.getId()));
											trpf3.setDelFlag(0);
											trfgDao.save(trpf3);
										}
										
									}
									
									TbTool tool = tbToolDao.findByCode(code, enterpriseId);
									
									if(tool!=null) {
										tool.setName(name);
										tool.setPrice(price);
										tool.setSpecification(specification);
										tool.setUnit(unit);
										tool.setType(Integer.valueOf(type));
										tool.setProductAttributes(productAttributes);
										tool.setRegistrationCertificateNumber(registrationCertificateNumber);
										tool.setProductionUnits(productionUnits);
										tool.setExplicitIngredients(explicitIngredients);
										tool.setDosageForms(dosageForms);
										tool.setToxicity(toxicity);
										tool.setDnm(dnm);
										tool.setUniformPrice("2");
										tool.setWholesalePrice(wholesalePrice);
										tbToolDao.update(tool);
										
										List<TbFile> tfs =tfDao.findByToolId(tool.getId());
										List<TbResToolFile> trgfs =trfgDao.findByToolId(tool.getId());
										if(trgfs!=null){
											for(TbResToolFile trgf:trgfs){
												trfgDao.delete(trgf);
											}
										}
										if(tfs!=null){
											for(TbFile tbFile:tfs){
												tfDao.delete(tbFile);
											}
										}
										
										if(!StringUtils.isEmpty(file1)) {
											TbFile tfe  = new TbFile();
											tfe.setFileName(file1.substring(file1.lastIndexOf("/")+1));
											tfe.setFileType(1);
											tfe.setFileUrl("http://220.189.248.54:8080"+file1);
											tfDao.save(tfe);
											TbResToolFile trpf=new TbResToolFile();
											trpf.setFileId(tfe.getId());
											trpf.setToolId(Integer.valueOf(tool.getId()));
											trpf.setDelFlag(0);
											trfgDao.save(trpf);
										}
										if(!StringUtils.isEmpty(file2)) {
											TbFile tfe2 = new TbFile();
											tfe2.setFileName(file2.substring(file2.lastIndexOf("/")+1));
											tfe2.setFileType(1);
											tfe2.setFileUrl("http://220.189.248.54:8080"+file2);
											tfDao.save(tfe2);
											TbResToolFile trpf2=new TbResToolFile();
											trpf2.setFileId(tfe2.getId());
											trpf2.setToolId(Integer.valueOf(tool.getId()));
											trpf2.setDelFlag(0);
											trfgDao.save(trpf2);
										}
										if(!StringUtils.isEmpty(file3)) {
											TbFile tfe3 = new TbFile();
											tfe3.setFileName(file3.substring(file3.lastIndexOf("/")+1));
											tfe3.setFileType(1);
											tfe3.setFileUrl("http://220.189.248.54:8080"+file3);
											tfDao.save(tfe3);
											TbResToolFile trpf3=new TbResToolFile();
											trpf3.setFileId(tfe3.getId());
											trpf3.setToolId(Integer.valueOf(tool.getId()));
											trpf3.setDelFlag(0);
											trfgDao.save(trpf3);
										}
										
									}
									
									if(tool==null) {
										tool = new TbTool();
										tool.setDelFlag(0);
										tool.setEnterpriseId(enterpriseId);
										tool.setName(name);
										tool.setPrice(price);
										tool.setSpecification(specification);
										tool.setUnit(unit);
										tool.setType(Integer.valueOf(type));
										tool.setProductAttributes(productAttributes);
										tool.setRegistrationCertificateNumber(registrationCertificateNumber);
										tool.setProductionUnits(productionUnits);
										tool.setExplicitIngredients(explicitIngredients);
										tool.setDosageForms(dosageForms);
										tool.setToxicity(toxicity);
										tool.setCode(code);
										tool.setDnm(dnm);
										tool.setUniformPrice("2");
										tool.setWholesalePrice(wholesalePrice);
										tbToolDao.save(tool);
										
										if(!StringUtils.isEmpty(file1)) {
											TbFile tfe  = new TbFile();
											tfe.setFileName(file1.substring(file1.lastIndexOf("/")+1));
											tfe.setFileType(1);
											tfe.setFileUrl("http://220.189.248.54:8080"+file1);
											tfDao.save(tfe);
											TbResToolFile trpf=new TbResToolFile();
											trpf.setFileId(tfe.getId());
											trpf.setToolId(Integer.valueOf(tool.getId()));
											trpf.setDelFlag(0);
											trfgDao.save(trpf);
										}
										if(!StringUtils.isEmpty(file2)) {
											TbFile tfe2 = new TbFile();
											tfe2.setFileName(file2.substring(file2.lastIndexOf("/")+1));
											tfe2.setFileType(1);
											tfe2.setFileUrl("http://220.189.248.54:8080"+file2);
											tfDao.save(tfe2);
											TbResToolFile trpf2=new TbResToolFile();
											trpf2.setFileId(tfe2.getId());
											trpf2.setToolId(Integer.valueOf(tool.getId()));
											trpf2.setDelFlag(0);
											trfgDao.save(trpf2);
										}
										if(!StringUtils.isEmpty(file3)) {
											TbFile tfe3 = new TbFile();
											tfe3.setFileName(file3.substring(file3.lastIndexOf("/")+1));
											tfe3.setFileType(1);
											tfe3.setFileUrl("http://220.189.248.54:8080"+file3);
											tfDao.save(tfe3);
											TbResToolFile trpf3=new TbResToolFile();
											trpf3.setFileId(tfe3.getId());
											trpf3.setToolId(Integer.valueOf(tool.getId()));
											trpf3.setDelFlag(0);
											trfgDao.save(trpf3);
										}
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
