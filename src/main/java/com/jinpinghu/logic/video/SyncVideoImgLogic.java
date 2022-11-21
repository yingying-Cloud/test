package com.jinpinghu.logic.video;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jinpinghu.common.tools.AliyunOSSClientUtil;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.common.tools.OSSClientConstants;
import com.jinpinghu.common.tools.YSUtil;
import com.jinpinghu.db.bean.TbVideo;
import com.jinpinghu.db.bean.TbVideoImg;
import com.jinpinghu.db.dao.TbVideoDao;
import com.jinpinghu.db.dao.TbVideoImgDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.video.param.SyncVideoImgParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class SyncVideoImgLogic extends BaseZLogic{
	
	private Logger log = Logger.getLogger(SyncVideoImgLogic.class);

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		SyncVideoImgParam myParam = (SyncVideoImgParam)logicParam;
		Integer videoId = StringUtils.isEmpty(myParam.getVideoId()) ? null : Integer.valueOf(myParam.getVideoId());
		
		String table = DateTimeUtil.formatSelf(new Date(), "yyyy_MM");
		String imageUrl = "";
		try {
			if(videoId != null) {
				TbVideoDao videoDao = new TbVideoDao(em);
				TbVideoImgDao videoImgDao = new TbVideoImgDao(em);
				TbVideo video = videoDao.findById(videoId);
				
				if(video != null) {
					String deviceSerial = StringUtils.trimToEmpty(video.getDeviceSerial());
					String channelNo = StringUtils.trimToEmpty(video.getChannelNo());
					
					String accessToken = YSUtil.getAccessToken();
					if(StringUtils.isEmpty(accessToken)) {
						accessToken = YSUtil.initAccessToken();
					}
					String param = "accessToken="+URLEncoder.encode(accessToken, "utf-8");
					param += "&deviceSerial="+URLEncoder.encode(deviceSerial, "utf-8");
					param += "&channelNo="+URLEncoder.encode(channelNo, "utf-8");
					
					String returnStr = HttpRequestUtil.sendPost(YSUtil.getCaptureApiurl(), param, "utf-8");
					JSONObject returnJo = JSONObject.fromObject(returnStr);
					if(!"200".equals(returnJo.getString("code"))) {
						log.error(video.getVideoName()+"    设备抓拍图片失败！失败信息："+returnJo.getString("msg"));
						res.add("status", -1).add("msg", video.getVideoName()+"    设备抓拍图片失败！失败信息："+returnJo.getString("msg"));
						return false;
						
					}else {
						JSONObject data = returnJo.getJSONObject("data");
						String picUrl = data.getString("picUrl");
						URL url = new URL(picUrl);
						HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
						int code = urlCon.getResponseCode();
						if (code != HttpURLConnection.HTTP_OK) {
							res.add("status", -1).add("msg", "文件读取失败");
						}
						InputStream is = urlCon.getInputStream();
						String[] picUrls = picUrl.split("/");
						String fileName = picUrls[picUrls.length-1];
						String today = DateTimeUtil.formatSelf(new Date(), "yyyyMMdd");
						String filePath = AliyunOSSClientUtil.uploadObject3OSS3(AliyunOSSClientUtil.getOSSClient(), is, fileName, urlCon.getContentLengthLong(), OSSClientConstants.BACKET_NAME,"nongzhiyun/"+today+"/");
						imageUrl = "http://shabang.oss-cn-beijing.aliyuncs.com/"+filePath;
						TbVideoImg videoImg = new TbVideoImg(video.getId(),imageUrl ,new Date(), 0);
						videoImgDao.insert_data(videoImg,table);
					}
				}
			}
		}catch(Exception ex) {
			log.error(ex);
			res.add("status", 1).add("msg", ex);
			return false;
		}
		
		res.add("status", 1).add("msg", "操作成功").add("imageUrl", imageUrl);
		return true;
	}

}
