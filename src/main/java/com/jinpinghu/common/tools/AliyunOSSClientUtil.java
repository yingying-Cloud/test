package com.jinpinghu.common.tools;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
 
/**
 * @class:AliyunOSSClientUtil
 * @descript:java使用阿里云OSS存储对象上传图片
 * @date:2017年3月16日 下午5:58:08
 * @author sang
 */
public class AliyunOSSClientUtil {
	//log日志
	private static Logger logger = Logger.getLogger(AliyunOSSClientUtil.class);
	/**
	 * 获取阿里云OSS客户端对象
	 * @return ossClient
	 */
	public static  OSSClient getOSSClient(){
		return new OSSClient(OSSClientConstants.ENDPOINT,OSSClientConstants.ACCESS_KEY_ID,
				OSSClientConstants.ACCESS_KEY_SECRET);
	}
 
	/**
	 * 创建存储空间
	 * @param ossClient      OSS连接
	 * @param bucketName 存储空间
	 * @return
	 */
	public  static String createBucketName(OSSClient ossClient,String bucketName){
		//存储空间
		final String bucketNames=bucketName;
		if(!ossClient.doesBucketExist(bucketName)){
			//创建存储空间
			Bucket bucket=ossClient.createBucket(bucketName);
			logger.info("创建存储空间成功");
			ossClient.shutdown();
			return bucket.getName();
		}
		return bucketNames;
	}
	
	/**
	 * 删除存储空间buckName
	 * @param ossClient  oss对象
	 * @param bucketName  存储空间
	 */
    public static  void deleteBucket(OSSClient ossClient, String bucketName){  
    	ossClient.deleteBucket(bucketName);   
        logger.info("删除" + bucketName + "Bucket成功");  
		ossClient.shutdown();
    }  
	
	/**
	 * 创建模拟文件夹
	 * @param ossClient oss连接
	 * @param bucketName 存储空间
	 * @param folder   模拟文件夹名如"qj_nanjing/"
	 * @return  文件夹名
	 */
	public static String createFolder(OSSClient ossClient,String bucketName,String folder){
		//文件夹名 
		final String keySuffixWithSlash =folder;
		//判断文件夹是否存在，不存在则创建
		if(!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)){
			//创建文件夹
			ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
			logger.info("创建文件夹成功");
			//得到文件夹名
			OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
			String fileDir=object.getKey();
			ossClient.shutdown();
			return fileDir;
		}
		return keySuffixWithSlash;
	}
	
	 /**  
	    * 根据key删除OSS服务器上的文件  
	    * @param ossClient  oss连接
	    * @param bucketName  存储空间 
	    * @param folder  模拟文件夹名 如"qj_nanjing/"
	    * @param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
	    */    
	   public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key){    
		    ossClient.deleteObject(bucketName, folder + key);   
	        logger.info("删除" + bucketName + "下的文件" + folder + key + "成功");  
			ossClient.shutdown();
	   } 
	
	/**
	 * 上传图片至OSS
	 * @param ossClient  oss连接
	 * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
	 * @param bucketName  存储空间
	 * @param folder 模拟文件夹名 如"qj_nanjing/"
	 * @return String 返回的唯一MD5数字签名
	 * */
	public static String uploadObject2OSS(OSSClient ossClient, File file, String bucketName) {
		String resultStr = null;
		try {
			//以输入流的形式上传文件
			InputStream is = new FileInputStream(file);
			//文件名
			String fileName = file.getName(); 
			//文件大小
            Long fileSize = file.length(); 
            //创建上传Object的Metadata  
			ObjectMetadata metadata = new ObjectMetadata();
			//上传的文件的长度
            metadata.setContentLength(is.available());  
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache"); 
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");  
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");  
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));  
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");  
            //上传文件   (上传文件流的形式)
            String key = UUID.randomUUID().toString();
            PutObjectResult putResult = ossClient.putObject(bucketName, key, is, metadata);  
			//解析结果
            if(putResult.getETag()!=null&&!putResult.getETag().equals("")){
            	resultStr = key;
            }
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);  
		} finally{
			ossClient.shutdown();
		}
		return resultStr;
	}
	
	/**
	 * 上传图片至OSS2
	 * */
	public static String uploadObject2OSS2(OSSClient ossClient, InputStream is,
			String fileName, Long fileSize,String bucketName) {
		String resultStr = null;
		try {
            //创建上传Object的Metadata  
			ObjectMetadata metadata = new ObjectMetadata();
			//上传的文件的长度
            metadata.setContentLength(fileSize);  
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache"); 
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");  
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");  
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));  
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");  
            //上传文件   (上传文件流的形式)
            String key = UUID.randomUUID().toString();
            PutObjectResult putResult = ossClient.putObject(bucketName, key, is, metadata);  
			//解析结果
            if(putResult.getETag()!=null&&!putResult.getETag().equals("")){
            	resultStr = key;
            }
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);  
		} finally{
			ossClient.shutdown();
		}
		return resultStr;
	}
	
	/**
	 * 上传图片至OSS2
	 * */
	public static String uploadObject3OSS3(OSSClient ossClient, InputStream is,
			String fileName, Long fileSize,String bucketName,String folder) {
		String resultStr = null;
		try {
            //创建上传Object的Metadata  
			ObjectMetadata metadata = new ObjectMetadata();
			//上传的文件的长度
            metadata.setContentLength(fileSize);  
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache"); 
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");  
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");  
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));  
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");  
            //上传文件   (上传文件流的形式)
            String key = folder+UUID.randomUUID().toString();
            PutObjectResult putResult = ossClient.putObject(bucketName, key, is, metadata);  
			//解析结果
            if(putResult.getETag()!=null&&!putResult.getETag().equals("")){
            	resultStr = key;
            }
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);  
		} finally{
			ossClient.shutdown();
		}
		return resultStr;
	}
	
	/**
	 * 通过文件名判断并获取OSS服务文件上传时文件的contentType
	 * @param fileName 文件名
	 * @return 文件的contentType
	 */
	public static  String getContentType(String fileName){
		//文件的后缀名
		String fileExtension = fileName.substring(fileName.lastIndexOf("."));
		if(".bmp".equalsIgnoreCase(fileExtension)) {
			return "image/bmp";
		}
		if(".gif".equalsIgnoreCase(fileExtension)) {
			return "image/gif";
		}
		if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {
			return "image/jpeg";
		}
		if(".html".equalsIgnoreCase(fileExtension)) {
			return "text/html";
		}
		if(".txt".equalsIgnoreCase(fileExtension)) {
			return "text/plain";
		}
		if(".vsd".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.visio";
		}
		if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.ms-powerpoint";
		}
		if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
			return "application/msword";
		}
		if(".xml".equalsIgnoreCase(fileExtension)) {
			return "text/xml";
		}
		//默认返回类型
		return "image/jpeg";
	}
    
	//测试
	/*public static void main(String[] args) {
		//初始化OSSClient
		OSSClient ossClient=AliyunOSSClientUtil.getOSSClient();
		//上传文件
		String files="D:\\image\\1010.jpg,D:\\image\\1111.jpg,D:\\image\\1212.jpg,D:\\image\\1313.jpg,D:\\image\\2222.jpg,D:\\image\\3333.jpg,"
				+ "D:\\image\\4444.jpg,D:\\image\\5555.jpg,D:\\image\\6666.jpg,D:\\image\\7777.jpg,D:\\image\\8888.jpg";
		String[] file=files.split(",");
		for(String filename:file){
			//System.out.println("filename:"+filename);
			File filess=new File(filename);
			String md5key = AliyunOSSClientUtil.uploadObject2OSS(ossClient, filess, BACKET_NAME, FOLDER);  
			logger.info("上传后的文件MD5数字唯一签名:" + md5key);  
			//上传后的文件MD5数字唯一签名:40F4131427068E08451D37F02021473A
		}
	}*/
	
	public static void main(String[] args) throws IOException {
		
		OSSClient ossClient = getOSSClient();
		String enterpriseNames = "嘉兴市乍浦镇为农农资经营部,嘉兴市乍浦镇外环种子经营部,嘉兴市乍浦镇马家荡农资经营部,嘉兴耕耘现代化农业服务有限公司,平湖市丰达农资连锁有限公司,平湖市众程农资有限公司,平湖市吉顺农资经营部,平湖市大兴农资经营部,平湖市宇美农资经营有限公司前港经营部,平湖市宇美农资经营有限公司大力经营部,平湖市宇美农资经营有限公司大齐塘经营部,平湖市宇美农资经营有限公司曙光经营部,平湖市宇美农资经营有限公司镇北经营部,平湖市宇美农资经营有限公司镇南路经营部,平湖市宇美农资经营有限公司韩家桥经营部,平湖市宇美农资经营有限公司韩庙经营部,平湖市家乐富农资有限公司,平湖市广陈镇振鑫农资经营部,平湖市广陈镇绿纯农资经营部,平湖市广陈镇绿萃农资经营部,平湖市广陈镇绿顺农资经营部,平湖市广陈镇陈记农资经营部,平湖市序玺农资有限公司,平湖市序玺农资有限公司前港经营部,平湖市序玺农资有限公司周圩经营部,平湖市序玺农资有限公司港中经营部,平湖市当湖街道慧农农资经营部,平湖市当湖街道金家农资经营部,平湖市当湖街道锦华农资店,平湖市当湖街道龙金农资商店,平湖市新仓农资专业合作社,平湖市新仓镇姜中农资经营部,平湖市新仓镇悯农农资经营部,平湖市新仓镇惠光农资经营部,平湖市新仓镇振农农资经营部,平湖市新仓镇新冯农资经营部,平湖市新仓镇更翔农资经营部,平湖市新仓镇照云农资经营部,平湖市新埭镇农旺农资经营部,平湖市新埭镇秀观农资经营部,平湖市新埭镇缘绿农资经营部,平湖市新埭镇西西长浜农资经营部,平湖市新埭镇金穗农资经营部,平湖市新庙兴合农资经营部,平湖市新庙振林农资经营部,平湖市曹桥供销专业合作社,平湖市曹桥供销专业合作社前进分社,平湖市曹桥街道小沈农药店,平湖市曹桥街道旭丰农资经营部,平湖市曹桥街道鼎丰农资经营部,平湖市林埭供销有限公司,平湖市林埭镇农朋农资经营部,平湖市林埭镇张水华农资店,平湖市林埭镇跃娟农资经营部,平湖市林埭镇金城农资店,平湖市林埭镇金苗农资店,平湖市灵来家庭农场,平湖市独山港镇友诚农资经营部,平湖市独山港镇小丁农资经营部,平湖市独山港镇水良农资经营部,平湖市独山港镇老宋农资经营部,平湖市独山港镇辰垒农资经营部,平湖市独山港镇金强棉花收购部,平湖市益农农资供销有限公司,平湖市福农农资经营部,平湖市补根农资有限公司,平湖市补根农资有限公司大齐塘分公司,平湖市金技农资经营部,平湖市金诚农资经营部,平湖市钟埭街道保丰农资经营部";
		String[] enterpriseNameArray = enterpriseNames.split(",");
		for (String enterpriseName : enterpriseNameArray) {
			ObjectListing objectListing = ossClient.listObjects("facexmlzip","zip/"+enterpriseName);
			List<OSSObjectSummary> list = objectListing.getObjectSummaries();
			OSSObjectSummary lastObjectSummary = null;
			for (OSSObjectSummary ossObjectSummary : list) {
				if (lastObjectSummary == null) {
					lastObjectSummary = ossObjectSummary;
				}else {
					if (!lastObjectSummary.getLastModified().after(ossObjectSummary.getLastModified())) {
						lastObjectSummary = ossObjectSummary;
					}
				}
				
			}
			System.out.println(enterpriseName+"    "+lastObjectSummary);
			if (lastObjectSummary != null) {
				System.out.println(DateTimeUtil.formatSelf(lastObjectSummary.getLastModified(),"yyyy-MM-dd HH:mm:ss"));
				GetObjectRequest request = new GetObjectRequest("facexmlzip", lastObjectSummary.getKey());
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("content-type", "application/zip");
				request.setHeaders(headers);
				OSSObject ossObject = ossClient.getObject("facexmlzip", lastObjectSummary.getKey());
				System.out.println(ossObject.getKey());
				File file = new File("D:\\"+lastObjectSummary.getKey().split("/")[0]+"\\"+lastObjectSummary.getKey().split("/")[1]+"\\"+lastObjectSummary.getKey().split("/")[3].replaceAll("[:,-]", "")+".zip");
				File parentFile = file.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				System.out.println(file.getPath());
				ossClient.getObject(request, file);
			}
		}
//		ossClient.getObject(new GetObjectRequest("shabang", "00000599-f8b3-4577-9387-72f1b44f8c3a"), new File("D:\\00000599-f8b3-4577-9387-72f1b44f8c3a.jpg"));
		ossClient.shutdown();
		
	}
	
}