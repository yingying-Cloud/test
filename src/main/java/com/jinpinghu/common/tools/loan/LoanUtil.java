package com.jinpinghu.common.tools.loan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class LoanUtil {
	
	public static Logger log  = Logger.getLogger(LoanUtil.class);
	
	public static final String apiUrl = "https://scm.zj96596.com/tpibench";
	public static final String platnum = "PT202004178010";//平台编号
	public static final String platname = "金平湖";
	public static final String channel = "TPI01";//渠道号   第三方平台 
	public static final String serviceScene = "01";//场景码
	public static final String consumerId = "002004";//消费系统ID

	/**
	 * 	提交贷款申请
	 * 
	 * @createTime:2020-03-30 16:13:03
	 * @author:harrychao
	 */
	public static LoanApplyResponse commitLoanApply(LoanApplyRequestBody loanApplyRequestBody) {
		String result = "";
		try {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			
			
			String tranDate = now.format(dateFormatter);//发送方交易日期
			String consumerSeqNo = new StringBuilder().append("002004").append(now.format(dateTimeFormatter)).append(getRandomCodeSix()).toString();//业务流水号
			String serviceCode = "500100001";//交易码    贷款预申请推送
			String tranTimestamp = now.format(timeFormatter);//发送方交易时间
			ApiRequestHead head = new ApiRequestHead(tranDate, consumerSeqNo, consumerId, serviceCode, serviceScene, tranTimestamp, channel, platnum);
			
			ApiRequest apiParam = new ApiRequest(head, loanApplyRequestBody);
			
			Gson gson = new Gson();
			
			String param = gson.toJson(apiParam);
			System.out.println(param);
			
			result = sendPost(apiUrl, param, "utf-8");
			
			LoanApplyResponse response = gson.fromJson(result, LoanApplyResponse.class);
			log.info(result);
			return response;
		} catch (Exception e) {
			log.error(e);
			log.error(result);
			// TODO: handle exception
			return null;
		}
	}
	
	public static String getRandomCodeSix() {
		char[] number = {'0','1','2','3','4','5','6','7','8','9'};
		StringBuilder randomCode = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			Random random = new Random();
			int randomIndex = random.nextInt(10);
			randomCode.append(number[randomIndex]);
		}
		return randomCode.toString();
	}
	
	/**
	 * 	贷款申请回复
	 * @createTime:2020-03-30 17:09:17
	 * @author:harrychao
	 */
	public static LoanApplyReplyResponse loanApplyReply(LoanApplyReplyRequestBody loanApplyReplyRequestBody) {
		String result = "";
		try {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			
			String tranDate = now.format(dateFormatter);//发送方交易日期
			String consumerSeqNo = new StringBuilder().append("002004").append(now.format(dateTimeFormatter)).append(getRandomCodeSix()).toString();//业务流水号
			String serviceCode = "500100002";//交易码    贷款预申请推送
			String tranTimestamp = now.format(timeFormatter);//发送方交易时间
			ApiRequestHead head = new ApiRequestHead(tranDate, consumerSeqNo, consumerId, serviceCode, serviceScene, tranTimestamp, channel, platnum);
			
			ApiRequest apiParam = new ApiRequest(head, loanApplyReplyRequestBody);
			
			Gson gson = new Gson();
			
			String param = gson.toJson(apiParam);
			System.out.println(param);
			
			result = sendPost(apiUrl, param, "utf-8");
			log.info(result);
			System.out.println(result);
			LoanApplyReplyResponse response = gson.fromJson(result, LoanApplyReplyResponse.class);
			return response;
		} catch (Exception e) {
			log.error(e);
			log.error(result);
			// TODO: handle exception
			return null;
		}
	}
	
	public static void main(String[] args) {
		//提交申请
		LoanApplyReplyRequestBody loanApplyReplybody = new LoanApplyReplyRequestBody("浙江绿迹农业有限公司", LoanUtil.platnum, "04", "330681199704288398", "ap4491585901890201");
		System.out.println(LoanUtil.loanApplyReply(loanApplyReplybody));
	}	
	
	public static String sendPost(String url, String param, String charset) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new LoanUtil().new NullHostNameVerifier());
	        SSLContext sc = SSLContext.getInstance("TLS");
	        sc.init(null, trustAllCerts, new SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpsURLConnection conn = (HttpsURLConnection)realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type","application/json"); 
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), charset));
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			if(conn.getResponseCode() == 200)
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(),charset));
			else
				in = new BufferedReader(new InputStreamReader(conn.getErrorStream(),charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
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
		return result;
	}
	
	static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    } };

    public class NullHostNameVerifier implements HostnameVerifier {
        /*
         * (non-Javadoc)
         * 
         * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String,
         * javax.net.ssl.SSLSession)
         */
        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            // TODO Auto-generated method stub
            return true;
        }
    }
}
