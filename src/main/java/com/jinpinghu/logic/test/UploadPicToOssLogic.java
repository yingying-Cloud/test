package com.jinpinghu.logic.test;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.common.tools.AliyunOSSClientUtil;
import com.jinpinghu.common.tools.OSSClientConstants;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class UploadPicToOssLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		
		String sql = "SELECT distinct file_url FROM `tb_file` ";
		List<String> fileUrlList = em.createNativeQuery(sql).getResultList();
		String fileName = "";
		int i=0;
		for (String fileUrl : fileUrlList) {
			System.out.println(++i);
			try {
				String[] fileUrlArray = fileUrl.split("/");
				fileName = fileUrlArray[fileUrlArray.length-1];
				CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
		        HttpGet httpGet = new HttpGet(fileUrl);
		        HttpResponse response = httpClient.execute(httpGet);
		        if(response.getStatusLine().getStatusCode() != 200) {
		        	String updateSql = "delete from tb_file where file_url = '"+fileUrl+"'";
					em.createNativeQuery(updateSql).executeUpdate();
		        }
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		String fileUrl = "http://220.189.248.54:8080/upload/2015/08/04/1438667608908.jpg";
		String[] fileUrlArray = fileUrl.split("/");
		String fileName = fileUrlArray[fileUrlArray.length-1];
		System.out.println(fileName);
		CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(fileUrl);
        HttpResponse response = httpClient.execute(httpGet);
		String fileObjectId = AliyunOSSClientUtil.uploadObject2OSS2(AliyunOSSClientUtil.getOSSClient(),
        		response.getEntity().getContent(),fileName,response.getEntity().getContentLength(),
        		OSSClientConstants.BACKET_NAME);
		String newFileUrl = "https://shabang.oss-cn-beijing.aliyuncs.com/"+fileObjectId;
		System.out.println(newFileUrl);
	}

}
