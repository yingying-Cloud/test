package com.jinpinghu.common.tools;

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

import com.google.gson.Gson;

public class LoanUtil {
	
	public static final String apiUrl = "https://115.236.91.210:8910/tpibench";
	public static final String platnum = "PT201808024087";//平台编号
	public static final String channel = "TPI01";//渠道号   第三方平台 
	public static final String serviceScene = "01";//场景码
	public static final String consumerId = "002004";//消费系统ID

	/**
	 * 	提交贷款申请
	 * 
	 * @createTime:2020-03-30 16:13:03
	 * @author:harrychao
	 */
	public String commitLoanApply(LoanApplyRequestBody loanApplyRuestBody) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		
		LoanUtil loanUtil = new LoanUtil();
		
		String tranDate = now.format(dateFormatter);//发送方交易日期
		String consumerSeqNo = new StringBuilder().append("002004").append(now.format(dateTimeFormatter)).append(getRandomCodeSix()).toString();//业务流水号
		String serviceCode = "500100001";//交易码    贷款预申请推送
		String tranTimestamp = now.format(timeFormatter);//发送方交易时间
		ApiRequestHead head = loanUtil.new ApiRequestHead(tranDate, consumerSeqNo, consumerId, serviceCode, serviceScene, tranTimestamp, channel, platnum);
		
		ApiRequest apiParam = loanUtil.new ApiRequest(head, loanApplyRuestBody);
		
		Gson gson = new Gson();
		
		String param = gson.toJson(apiParam);
		System.out.println(param);
		
		String result = sendPost(apiUrl, param, "utf-8");
		System.out.println(result);
		return result;
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
	public String loanApplyReply(LoanApplyReplyRequestBody loanApplyReplyRequestBody) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		
		LoanUtil loanUtil = new LoanUtil();
		
		String tranDate = now.format(dateFormatter);//发送方交易日期
		String consumerSeqNo = new StringBuilder().append("002004").append(now.format(dateTimeFormatter)).append(getRandomCodeSix()).toString();//业务流水号
		String serviceCode = "500100002";//交易码    贷款预申请推送
		String tranTimestamp = now.format(timeFormatter);//发送方交易时间
		ApiRequestHead head = loanUtil.new ApiRequestHead(tranDate, consumerSeqNo, consumerId, serviceCode, serviceScene, tranTimestamp, channel, platnum);
		
		ApiRequest apiParam = loanUtil.new ApiRequest(head, loanApplyReplyRequestBody);
		
		Gson gson = new Gson();
		
		String param = gson.toJson(apiParam);
		
		String result = sendPost(apiUrl, param, "utf-8");
		System.out.println(result);
		return result;
	}
	
	public static void main(String[] args) {
		LoanUtil loanUtil = new LoanUtil();
		//提交申请
		LoanApplyRequestBody loanApplyBody = loanUtil.new LoanApplyRequestBody("浙江绿迹农业有限公司", platnum, "黄超", "04", "330681199704288398",
				"10000", "30", "2020-04-01", "2020-04-30", "开店启动资金", "01", "杭州览千信息科技有限公司", "801020", "3年5月",
				"10000", "10000", "10000", "10000", "10000", "10000", "10000", "10000", "10000", "", "", "");
		System.out.println(loanUtil.commitLoanApply(loanApplyBody));
	}
	
	class ApiRequestBody {}
	
	class ApiRequestHead implements java.io.Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2657390316167184699L;
		private String TRAN_DATE;
		private String CONSUMER_SEQ_NO;
		private String CONSUMER_ID;
		private String SERVICE_CODE;
		private String SERVICE_SCENE;
		private String TRAN_TIMESTAMP;
		private String CHANNEL;
		private String PLATNUM;
		
		public ApiRequestHead(String tRAN_DATE, String cONSUMER_SEQ_NO, String cONSUMER_ID, String sERVICE_CODE,
				String sERVICE_SCENE, String tRAN_TIMESTAMP, String cHANNEL, String pLATNUM) {
			super();
			TRAN_DATE = tRAN_DATE;
			CONSUMER_SEQ_NO = cONSUMER_SEQ_NO;
			CONSUMER_ID = cONSUMER_ID;
			SERVICE_CODE = sERVICE_CODE;
			SERVICE_SCENE = sERVICE_SCENE;
			TRAN_TIMESTAMP = tRAN_TIMESTAMP;
			CHANNEL = cHANNEL;
			PLATNUM = pLATNUM;
		}
		public String getTRAN_DATE() {
			return TRAN_DATE;
		}
		public void setTRAN_DATE(String tRAN_DATE) {
			TRAN_DATE = tRAN_DATE;
		}
		public String getCONSUMER_SEQ_NO() {
			return CONSUMER_SEQ_NO;
		}
		public void setCONSUMER_SEQ_NO(String cONSUMER_SEQ_NO) {
			CONSUMER_SEQ_NO = cONSUMER_SEQ_NO;
		}
		public String getCONSUMER_ID() {
			return CONSUMER_ID;
		}
		public void setCONSUMER_ID(String cONSUMER_ID) {
			CONSUMER_ID = cONSUMER_ID;
		}
		public String getSERVICE_CODE() {
			return SERVICE_CODE;
		}
		public void setSERVICE_CODE(String sERVICE_CODE) {
			SERVICE_CODE = sERVICE_CODE;
		}
		public String getSERVICE_SCENE() {
			return SERVICE_SCENE;
		}
		public void setSERVICE_SCENE(String sERVICE_SCENE) {
			SERVICE_SCENE = sERVICE_SCENE;
		}
		public String getTRAN_TIMESTAMP() {
			return TRAN_TIMESTAMP;
		}
		public void setTRAN_TIMESTAMP(String tRAN_TIMESTAMP) {
			TRAN_TIMESTAMP = tRAN_TIMESTAMP;
		}
		public String getCHANNEL() {
			return CHANNEL;
		}
		public void setCHANNEL(String cHANNEL) {
			CHANNEL = cHANNEL;
		}
		public String getPLATNUM() {
			return PLATNUM;
		}
		public void setPLATNUM(String pLATNUM) {
			PLATNUM = pLATNUM;
		}
		
	}
	
	class ApiRequest implements java.io.Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -5254563235100420815L;
		private ApiRequestHead head;
		private ApiRequestBody body;
		
		
		public ApiRequest(ApiRequestHead head, ApiRequestBody body) {
			super();
			this.head = head;
			this.body = body;
		}
		public ApiRequestHead getHead() {
			return head;
		}
		public void setHead(ApiRequestHead head) {
			this.head = head;
		}
		public ApiRequestBody getBody() {
			return body;
		}
		public void setBody(ApiRequestBody body) {
			this.body = body;
		}
		public long getSerialversionuid() {
			return serialVersionUID;
		}
		
	}
	
	
	public class LoanApplyRequestBody extends ApiRequestBody implements java.io.Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4329227538826734115L;
		private String PTNAME;
		private String PTNUM;
		private String CUSTOMER_NAME;
		private String ID_TYPE;
		private String ID_NUMBER;
		private String LOAN_AMOUNT;
		private String LOAN_TIME;
		private String START_TIME;
		private String END_TIME;
		private String LOAN_USE;
		private String GUARANTY_STYLE;
		private String GUARANTY_UNIT;
		private String HANDLE_BANK;
		private String FINANCIAL_STATEMENT;
		private String CURRENT_ASSETS;
		private String RECEIVABLES;
		private String FIXED_ASSETS;
		private String TOTAL_ASSETS;
		private String BANK_BORROW;
		private String TOTAL_INDEBTEDNESS;
		private String OWNERSHIP_INTEREST;
		private String BUSINESS_INCOME;
		private String TOTAL_PROFIT;
		private String EXT1;
		private String EXT2;
		private String EXT3;
		
		public LoanApplyRequestBody(String pTNAME, String pTNUM, String cUSTOMER_NAME, String iD_TYPE, String iD_NUMBER,
				String lOAN_AMOUNT, String lOAN_TIME, String sTART_TIME, String eND_TIME, String lOAN_USE,
				String gUARANTY_STYLE, String gUARANTY_UNIT, String hANDLE_BANK, String fINANCIAL_STATEMENT,
				String cURRENT_ASSETS, String rECEIVABLES, String fIXED_ASSETS, String tOTAL_ASSETS, String bANK_BORROW,
				String tOTAL_INDEBTEDNESS, String oWNERSHIP_INTEREST, String bUSINESS_INCOME, String tOTAL_PROFIT,
				String eXT1, String eXT2, String eXT3) {
			super();
			PTNAME = pTNAME;
			PTNUM = pTNUM;
			CUSTOMER_NAME = cUSTOMER_NAME;
			ID_TYPE = iD_TYPE;
			ID_NUMBER = iD_NUMBER;
			LOAN_AMOUNT = lOAN_AMOUNT;
			LOAN_TIME = lOAN_TIME;
			START_TIME = sTART_TIME;
			END_TIME = eND_TIME;
			LOAN_USE = lOAN_USE;
			GUARANTY_STYLE = gUARANTY_STYLE;
			GUARANTY_UNIT = gUARANTY_UNIT;
			HANDLE_BANK = hANDLE_BANK;
			FINANCIAL_STATEMENT = fINANCIAL_STATEMENT;
			CURRENT_ASSETS = cURRENT_ASSETS;
			RECEIVABLES = rECEIVABLES;
			FIXED_ASSETS = fIXED_ASSETS;
			TOTAL_ASSETS = tOTAL_ASSETS;
			BANK_BORROW = bANK_BORROW;
			TOTAL_INDEBTEDNESS = tOTAL_INDEBTEDNESS;
			OWNERSHIP_INTEREST = oWNERSHIP_INTEREST;
			BUSINESS_INCOME = bUSINESS_INCOME;
			TOTAL_PROFIT = tOTAL_PROFIT;
			EXT1 = eXT1;
			EXT2 = eXT2;
			EXT3 = eXT3;
		}
		public String getPTNAME() {
			return PTNAME;
		}
		public void setPTNAME(String pTNAME) {
			PTNAME = pTNAME;
		}
		public String getPTNUM() {
			return PTNUM;
		}
		public void setPTNUM(String pTNUM) {
			PTNUM = pTNUM;
		}
		public String getCUSTOMER_NAME() {
			return CUSTOMER_NAME;
		}
		public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
			CUSTOMER_NAME = cUSTOMER_NAME;
		}
		public String getID_TYPE() {
			return ID_TYPE;
		}
		public void setID_TYPE(String iD_TYPE) {
			ID_TYPE = iD_TYPE;
		}
		public String getID_NUMBER() {
			return ID_NUMBER;
		}
		public void setID_NUMBER(String iD_NUMBER) {
			ID_NUMBER = iD_NUMBER;
		}
		public String getLOAN_AMOUNT() {
			return LOAN_AMOUNT;
		}
		public void setLOAN_AMOUNT(String lOAN_AMOUNT) {
			LOAN_AMOUNT = lOAN_AMOUNT;
		}
		public String getLOAN_TIME() {
			return LOAN_TIME;
		}
		public void setLOAN_TIME(String lOAN_TIME) {
			LOAN_TIME = lOAN_TIME;
		}
		public String getSTART_TIME() {
			return START_TIME;
		}
		public void setSTART_TIME(String sTART_TIME) {
			START_TIME = sTART_TIME;
		}
		public String getEND_TIME() {
			return END_TIME;
		}
		public void setEND_TIME(String eND_TIME) {
			END_TIME = eND_TIME;
		}
		public String getLOAN_USE() {
			return LOAN_USE;
		}
		public void setLOAN_USE(String lOAN_USE) {
			LOAN_USE = lOAN_USE;
		}
		public String getGUARANTY_STYLE() {
			return GUARANTY_STYLE;
		}
		public void setGUARANTY_STYLE(String gUARANTY_STYLE) {
			GUARANTY_STYLE = gUARANTY_STYLE;
		}
		public String getGUARANTY_UNIT() {
			return GUARANTY_UNIT;
		}
		public void setGUARANTY_UNIT(String gUARANTY_UNIT) {
			GUARANTY_UNIT = gUARANTY_UNIT;
		}
		public String getHANDLE_BANK() {
			return HANDLE_BANK;
		}
		public void setHANDLE_BANK(String hANDLE_BANK) {
			HANDLE_BANK = hANDLE_BANK;
		}
		public String getFINANCIAL_STATEMENT() {
			return FINANCIAL_STATEMENT;
		}
		public void setFINANCIAL_STATEMENT(String fINANCIAL_STATEMENT) {
			FINANCIAL_STATEMENT = fINANCIAL_STATEMENT;
		}
		public String getCURRENT_ASSETS() {
			return CURRENT_ASSETS;
		}
		public void setCURRENT_ASSETS(String cURRENT_ASSETS) {
			CURRENT_ASSETS = cURRENT_ASSETS;
		}
		public String getRECEIVABLES() {
			return RECEIVABLES;
		}
		public void setRECEIVABLES(String rECEIVABLES) {
			RECEIVABLES = rECEIVABLES;
		}
		public String getFIXED_ASSETS() {
			return FIXED_ASSETS;
		}
		public void setFIXED_ASSETS(String fIXED_ASSETS) {
			FIXED_ASSETS = fIXED_ASSETS;
		}
		public String getTOTAL_ASSETS() {
			return TOTAL_ASSETS;
		}
		public void setTOTAL_ASSETS(String tOTAL_ASSETS) {
			TOTAL_ASSETS = tOTAL_ASSETS;
		}
		public String getBANK_BORROW() {
			return BANK_BORROW;
		}
		public void setBANK_BORROW(String bANK_BORROW) {
			BANK_BORROW = bANK_BORROW;
		}
		public String getTOTAL_INDEBTEDNESS() {
			return TOTAL_INDEBTEDNESS;
		}
		public void setTOTAL_INDEBTEDNESS(String tOTAL_INDEBTEDNESS) {
			TOTAL_INDEBTEDNESS = tOTAL_INDEBTEDNESS;
		}
		public String getOWNERSHIP_INTEREST() {
			return OWNERSHIP_INTEREST;
		}
		public void setOWNERSHIP_INTEREST(String oWNERSHIP_INTEREST) {
			OWNERSHIP_INTEREST = oWNERSHIP_INTEREST;
		}
		public String getBUSINESS_INCOME() {
			return BUSINESS_INCOME;
		}
		public void setBUSINESS_INCOME(String bUSINESS_INCOME) {
			BUSINESS_INCOME = bUSINESS_INCOME;
		}
		public String getTOTAL_PROFIT() {
			return TOTAL_PROFIT;
		}
		public void setTOTAL_PROFIT(String tOTAL_PROFIT) {
			TOTAL_PROFIT = tOTAL_PROFIT;
		}
		public String getEXT1() {
			return EXT1;
		}
		public void setEXT1(String eXT1) {
			EXT1 = eXT1;
		}
		public String getEXT2() {
			return EXT2;
		}
		public void setEXT2(String eXT2) {
			EXT2 = eXT2;
		}
		public String getEXT3() {
			return EXT3;
		}
		public void setEXT3(String eXT3) {
			EXT3 = eXT3;
		}
		public long getSerialversionuid() {
			return serialVersionUID;
		}
	}
	
	public class LoanApplyReplyRequestBody extends ApiRequestBody implements java.io.Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -7246962434338663904L;
		private String PTNAME;
		private String PTNUM;
		private String ID_TYPE;
		private String ID_NUMBER;
		private String APPLY_CODE;
		
		
		public LoanApplyReplyRequestBody(String pTNAME, String pTNUM, String iD_TYPE, String iD_NUMBER, String aPPLY_CODE) {
			super();
			PTNAME = pTNAME;
			PTNUM = pTNUM;
			ID_TYPE = iD_TYPE;
			ID_NUMBER = iD_NUMBER;
			APPLY_CODE = aPPLY_CODE;
		}
		public String getPTNAME() {
			return PTNAME;
		}
		public void setPTNAME(String pTNAME) {
			PTNAME = pTNAME;
		}
		public String getPTNUM() {
			return PTNUM;
		}
		public void setPTNUM(String pTNUM) {
			PTNUM = pTNUM;
		}
		public String getID_TYPE() {
			return ID_TYPE;
		}
		public void setID_TYPE(String iD_TYPE) {
			ID_TYPE = iD_TYPE;
		}
		public String getID_NUMBER() {
			return ID_NUMBER;
		}
		public void setID_NUMBER(String iD_NUMBER) {
			ID_NUMBER = iD_NUMBER;
		}
		public String getAPPLY_CODE() {
			return APPLY_CODE;
		}
		public void setAPPLY_CODE(String aPPLY_CODE) {
			APPLY_CODE = aPPLY_CODE;
		}
		
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
