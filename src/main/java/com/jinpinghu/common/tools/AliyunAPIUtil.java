package com.jinpinghu.common.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.jinpinghu.common.tools.HttpUtil;

import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

public class AliyunAPIUtil {

	public static BigDecimal aiFaceMatch(String imageA,String imageB) {
		if(StringUtils.isEmpty(imageA) || StringUtils.isEmpty(imageB))
			return BigDecimal.ZERO;
		//API产品路径
        String host = "http://aimatch.market.alicloudapi.com";
        String path = "/ai_market/ai_face/ai_face_match/v1";
        String method = "POST";
        //阿里云APPCODE
        String appcode = "1e9e011f407146a685006ebb8bba0125";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        //UUID采用当前程序运行时间，用于防止重放攻击，开发者可根据自己需求，自定义字符串
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String UUID = df.format(new Date());
        headers.put("X-Ca-Nonce", UUID);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();

        //内容数据类型，如：0，则表示BASE64编码；1，则表示图像文件URL链接        
        //启用BASE64编码方式进行识别
        //内容数据类型是BASE64编码
//        String imgFileA = "图片A文件路径";
//        String imgBase64A = "";
//        try {
//            File fileA = new File(imgFileA);
//            byte[] contentA = new byte[(int) fileA.length()];
//            FileInputStream finputstreamA = new FileInputStream(fileA);
//            finputstreamA.read(contentA);
//            finputstreamA.close();
//            imgBase64A = new String(encodeBase64(contentA));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//        String imgFileA = "图片B文件路径";
//        String imgBase64B = "";
//        try {
//            File fileB = new File(imgFileB);
//            byte[] contentA = new byte[(int) fileB.length()];
//            FileInputStream finputstreamB = new FileInputStream(fileB);
//            finputstreamB.read(contentB);
//            finputstreamB.close();
//            imgBase64B = new String(encodeBase64(contentA));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//        bodys.put("IMAGE_A", imgBase64A);
//        bodys.put("IMAGE_TYPE_A", "0");
//        bodys.put("IMAGE_B", imgBase64B);
//        bodys.put("IMAGE_TYPE_B", "0");
        
        //启用URL方式进行识别
        //内容数据类型是图像文件URL链接
        bodys.put("IMAGE_A", imageA);
        bodys.put("IMAGE_TYPE_A", "1");
        bodys.put("IMAGE_B", imageB);
        bodys.put("IMAGE_TYPE_B", "1");
        
        
        BigDecimal matchScore = BigDecimal.ZERO;
        try {
            /**
                      * 重要提示如下:
            * HttpUtils请从
            * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
                      * 下载
            *
                      * 相应的依赖请参照
            * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
            */
            HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
            //获取response的body
            String returnStr = EntityUtils.toString(response.getEntity());
            JSONObject returnJson = JSONObject.fromObject(returnStr);
            matchScore = returnJson.containsKey("MATCH_SCORE") ? new BigDecimal(returnJson.get("MATCH_SCORE").toString()) : BigDecimal.ZERO;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return matchScore;
	}
	
	public static BigDecimal aiFaceMatch2(String imageA,String imageB) throws Exception {
		JSONObject param = new JSONObject();
		param.put("type", "0");
		param.put("image_url_1", imageA);
		param.put("image_url_2", imageB);
		BigDecimal matchScore = BigDecimal.ZERO;
		try {
			String resultStr = sendPost("https://dtplus-cn-shanghai.data.aliyuncs.com/face/verify", param.toString(), "LTAI4FnwvHj1Bg9njzLwyioi", "TZYSJ9D6Qf6KbJV035UoxhwiiaRDfT");
			JSONObject result = JSONObject.fromObject(resultStr);
			matchScore = result.containsKey("confidence") ? new BigDecimal(result.get("confidence").toString()) : BigDecimal.ZERO;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return matchScore;
	}
	
	public static String MD5Base64(String s) {
        if (s == null)
            return null;
        String encodeStr = "";
        byte[] utfBytes = s.getBytes();
        MessageDigest mdTemp;
        try {
            mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(utfBytes);
            byte[] md5Bytes = mdTemp.digest();
            BASE64Encoder b64Encoder = new BASE64Encoder();
            encodeStr = b64Encoder.encode(md5Bytes);
        } catch (Exception e) {
            throw new Error("Failed to generate MD5 : " + e.getMessage());
        }
        return encodeStr;
    }
    /*
     * 计算 HMAC-SHA1
     */
    public static String HMACSha1(String data, String key) {
        String result;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = (new BASE64Encoder()).encode(rawHmac);
        } catch (Exception e) {
            throw new Error("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
    /*
     * 等同于javaScript中的 new Date().toUTCString();
     */
    public static String toGMTString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.UK);
        df.setTimeZone(new java.util.SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }
    /*
     * 发送POST请求
     */
    public static String sendPost(String url, String body, String ak_id, String ak_secret) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        int statusCode = 200;
        try {
            URL realUrl = new URL(url);
            /*
             * http header 参数
             */
            String method = "POST";
            String accept = "application/json";
            String content_type = "application/json";
            String path = realUrl.getFile();
            String date = toGMTString(new Date());
            // 1.对body做MD5+BASE64加密
            String bodyMd5 = MD5Base64(body);
            String stringToSign = method + "\n" + accept + "\n" + bodyMd5 + "\n" + content_type + "\n" + date + "\n"
                    + path;
            // 2.计算 HMAC-SHA1
            String signature = HMACSha1(stringToSign, ak_secret);
            // 3.得到 authorization header
            String authHeader = "Dataplus " + ak_id + ":" + signature;
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", accept);
            conn.setRequestProperty("content-type", content_type);
            conn.setRequestProperty("date", date);
            conn.setRequestProperty("Authorization", authHeader);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(body);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            statusCode = ((HttpURLConnection)conn).getResponseCode();
            if(statusCode != 200) {
                in = new BufferedReader(new InputStreamReader(((HttpURLConnection)conn).getErrorStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (statusCode != 200) {
            throw new IOException("\nHttp StatusCode: "+ statusCode + "\nErrorMessage: " + result);
        }
        return result;
    }
    
    

}
