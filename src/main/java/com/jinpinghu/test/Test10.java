package com.jinpinghu.test;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.jinpinghu.common.tools.HttpUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Test10 {
	public static void main(String[] args) {
		
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
        bodys.put("IMAGE_A", "http://shabang.oss-cn-beijing.aliyuncs.com/61cf3a01-4401-4c77-ad80-b882ab800fe9.png");
        bodys.put("IMAGE_TYPE_A", "1");
        bodys.put("IMAGE_B", "http://shabang.oss-cn-beijing.aliyuncs.com/ba3ce206-34c7-40a2-9f6f-0ead81665ce9.png");
        bodys.put("IMAGE_TYPE_B", "1");
        
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
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
