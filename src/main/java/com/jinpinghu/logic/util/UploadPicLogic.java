package com.jinpinghu.logic.util;

import java.net.URL;
import java.net.URLConnection;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.AliyunOSSClientUtil;
import com.jinpinghu.common.tools.OSSClientConstants;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.util.param.UploadPicParam;

import fw.jbiz.common.helper.StringUtil;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class UploadPicLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		UploadPicParam myParam = (UploadPicParam) logicParam;
		String token = myParam.getToken();
		String mediaId = myParam.getMediaId();
		String fileName = myParam.getFileName();
		
        try {
            String urlNameString = "https://api.weixin.qq.com/cgi-bin/media/get?access_token="+token
            		+"&media_id="+mediaId;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            String fileObjectId = AliyunOSSClientUtil.uploadObject2OSS2(AliyunOSSClientUtil.getOSSClient(),
            		connection.getInputStream(),fileName,connection.getContentLengthLong(),OSSClientConstants.BACKET_NAME);
        	if(StringUtil.isNullOrEmpty(fileObjectId)){
            	res.add("status", -1);
    			res.add("msg", "未知错误,上传失败");
            }else{
        		res.add("status", 1);
    			res.add("msg", "成功");
    			res.add("fileObjectId", "https://shabang.oss-cn-beijing.aliyuncs.com/"+fileObjectId);
            }
            return true;
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
            
            res.add("status", -1);
			res.add("msg", "发送GET请求出现异常！");
			return true;
        }
	}

	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) {
		UploadPicParam myParam = (UploadPicParam) logicParam;
		String token = myParam.getToken();
		String mediaId = myParam.getMediaId();
		if(StringUtil.isNullOrEmpty(token)
				|| StringUtil.isNullOrEmpty(mediaId)) {
			res.add("status", -2);
			res.add("msg", "必填参数不能为空！");
			return false;
		}
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam objParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		return true;
	}
	

}
