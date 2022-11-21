package com.jinpinghu.common.tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;

import net.sf.json.JSONObject;

@SuppressWarnings("unused")
public class HttpRequestUtil {
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {

		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = "";
			if(StringUtil.isNullOrEmpty(param)){
				urlNameString = url;
			}else{
				urlNameString = url + "?" + param;
			}
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			//Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			/*for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}*/
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	public static String sendGetXML(String url, String xmlInfo) {
		OutputStreamWriter out = null;
		BufferedReader br = null;
		String track = "";
		try {
            String urlStr = "https://api.newgistics.com/WebAPI/Shipment/Tracking";  //物流网站示例
            URL u = new URL(url);
            URLConnection con = u.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("connection", "Keep-Alive");
            con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            con.setRequestProperty("Content-Type", "text/xml");

            out = new OutputStreamWriter(con.getOutputStream());
            out.write(new String(xmlInfo.getBytes("UTF-8")));
            out.flush();
            out.close();
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String content = "";
            while ((content = br.readLine()) != null) {
                track += content;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	try {
        		if(out!=null){
            		out.close();
            	}
			} catch (Exception e2) { }
        	try {
        		if(br!=null){
        			br.close();
            	}
			} catch (Exception e2) { }
        }
		return track;    
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param,String charset) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
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
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			result = "发送 POST 请求出现异常！" + e;
			System.out.println(result);
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
	
	public static String sendPostData(String url,String data,String charset) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), charset));
			// 发送请求参数
			//out.write(new String(data.getBytes("UTF-8")));
			out.write(data);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),charset));
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
	
	public static HttpResponse sendPostDataForWechatCode(String url,String data) {
		try {
			CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
	        HttpPost httpPost = new HttpPost(url);
	        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
	        String body = data;
	        StringEntity entity;
	        entity = new StringEntity(body);
	        entity.setContentType("image/png");

	        httpPost.setEntity(entity);
	        HttpResponse response;
	        response = httpClient.execute(httpPost);
	        InputStream inputStream = response.getEntity().getContent();
	        return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传文件
	 * @author Ejectam719
	 * @createTime 2017年7月6日 上午10:03:26
	 * @updater Ejectam719
	 * @updateTime 2017年7月6日 上午10:25:19
	 */
	public static String uploadFile(String serviceUrl, Map<String, String> normalParam, String fileParam,
			String fileName, InputStream fileIn) {
		// PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			// 换行符
			final String newLine = "\r\n";
			final String boundaryPrefix = "--";
			// 定义数据分隔线
			String BOUNDARY = "----WebKitFormBoundarygPj3KcdiB8WqVj8V";
			// 服务器的域名
			URL url = new URL(serviceUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置为POST情
			conn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求头参数
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			OutputStream out = new DataOutputStream(conn.getOutputStream());

			// 上传文件
			StringBuilder sb = new StringBuilder();

			for (String key : normalParam.keySet()) {
				sb.append(boundaryPrefix);
				sb.append(BOUNDARY);
				sb.append(newLine);
				// 参数名
				sb.append("Content-Disposition: form-data;name=\"" + key + "\"");
				// 参数头设置完以后需要两个换行，然后才是参数内容
				sb.append(newLine);
				sb.append(newLine);
				// 参数值
				sb.append(normalParam.get(key));
				sb.append(newLine);
			}

			sb.append(boundaryPrefix);
			sb.append(BOUNDARY);
			sb.append(newLine);
			// 文件参数
			sb.append("Content-Disposition: form-data;name=\"" + fileParam + "\";filename=\"" + fileName + "\"");
			sb.append(newLine);
			sb.append("Content-Type:application/octet-stream");
			// 参数头设置完以后需要两个换行，然后才是参数内容
			sb.append(newLine);
			sb.append(newLine);

			// 将参数头的数据写入到输出流中
			out.write(sb.toString().getBytes());

			// 数据输入流,用于读取文件数据
			byte[] bufferOut = new byte[4096];
			int bytes = 0;
			// 每次读4KB数据,并且将文件数据写入到输出流中
			while ((bytes = fileIn.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			// 最后添加换行
			out.write(newLine.getBytes());
			fileIn.close();

			// 定义最后数据分隔线，即--加上BOUNDARY再加上--。
			byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine).getBytes();
			// 写上结尾标识
			out.write(end_data);
			out.flush();
			out.close();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
				// if(out!=null){
				// out.close();
				// }
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static JSONObject uploadFileToFss(String fileCategory, String fileName, InputStream fileObject) {

		String urlStr = "https://dl.bizmsg.net/nxhzzfss/api/file/uploadFile.do";

		Map<String, String> map = new HashMap<String, String>();
		map.put("fileCategory", fileCategory);
		map.put("fileAliasName", fileName);
		
		String jsonStr = HttpRequestUtil.uploadFile(urlStr, map, "fileObject", fileName, fileObject);

		if (StringUtil.isNullOrEmpty(jsonStr))
			return null;

		JSONObject jsonObejct = JSONObject.fromObject(jsonStr);

		return jsonObejct;
	}
	
	
	public static JSONObject getAccessToken(String appid, String secret) {
		String urlStr = "https://api.weixin.qq.com/cgi-bin/token";
		String paramStr = "appid=" + appid
				 + "&" + "secret=" + secret
				 + "&" + "grant_type=" + "client_credential";
		String jsonStr = HttpRequestUtil.sendGet(urlStr, paramStr);
		System.out.println(jsonStr);
        JSONObject jsonObejct = JSONObject.fromObject(jsonStr);
		
		return jsonObejct;
    }
	
	public static JSONObject getJSAPITicket(String access_token) {
		String urlStr = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
		String paramStr = "access_token=" + access_token
				 + "&" + "type=" + "jsapi";
		String jsonStr = HttpRequestUtil.sendGet(urlStr, paramStr);
        JSONObject jsonObejct = JSONObject.fromObject(jsonStr);

        return jsonObejct;
    }
	
	public static String getToken() {
		String urlStr = "http://39.104.62.243:8804/auth/login";
//		String urlStr = "http://qinweisi.free.qydev.com/auth/login";
		JSONObject paramStr = new JSONObject();
		paramStr.put("mobile" , "ytj");
		paramStr.put("password" , "123456");
		String jsonStr = HttpRequestUtil.sendPost(urlStr, paramStr,"utf-8");
        JSONObject jsonObejct = JSONObject.fromObject(jsonStr);
        if(jsonObejct!=null&&jsonObejct.getInt("code")==200) {
        	JSONObject data = (JSONObject)jsonObejct.get("data");
        	if(data!=null) {
        		return data.getString("token");
        	}
        }
        return "";
    }
	
	public static void main(String[] args) {
		System.out.println(getToken());
	}
	
	public static String sendPostToken(String url, JSONObject	 param, String charset) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL("http://39.104.62.243:8804"+url);
//			URL realUrl = new URL("http://qinweisi.free.qydev.com"+url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type","application/json"); 
//			conn.setRequestProperty("token", getToken());
			conn.addRequestProperty("token", getToken());
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
	public static String sendPost(String url, JSONObject param, String charset) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
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
	
	public static String sendPostMap(String url, Map<String, String> param, String charset) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
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
	
}