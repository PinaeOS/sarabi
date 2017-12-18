package org.pinae.sarabi.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.pinae.nala.xb.Xml;
import org.pinae.nala.xb.exception.MarshalException;
import org.pinae.nala.xb.util.Constant;

import com.alibaba.fastjson.JSON;

public class ServiceOutputor {
	
	private Logger logger = Logger.getLogger("access");
	
	private static int KB = 1024;
	
	private ServerConfig serverCfg;
	
	public ServiceOutputor(final ServerConfig serverCfg) {
		this.serverCfg = serverCfg;
	}
	
	public long output(HttpServletRequest httpRequest, HttpServletResponse httpResponse, ServiceResponse srvResponse) throws IOException {

		httpResponse.setStatus(srvResponse.getStatus());
		
		httpResponse.addHeader("Server", serverCfg.getName());

		Map<String, String> respHeader = srvResponse.getHeaders();
		for (String headerKey : respHeader.keySet()) {
			String headerValue = respHeader.get(headerKey);
			httpResponse.addHeader(headerKey, headerValue);
		}

		String contentType = srvResponse.getContentType();

		httpResponse.setContentType(contentType);
		
		String charset = srvResponse.getCharset();
		if (StringUtils.isBlank(charset)) {
			charset = "UTF-8";
		}
		httpResponse.setCharacterEncoding(charset);

		Object content = null;

		Object respContent = srvResponse.getContent();
		if (respContent != null) {
			if (respContent instanceof String || respContent instanceof StringBuffer) {
				content = respContent.toString();
			} else {
				if (contentType.equals(Http.APPLICATION_JSON)) {
					content = JSON.toJSONString(respContent);
				} else if (contentType.equals(Http.APPLICATION_XML)) {
					try {
						if (respContent instanceof List) {
							Map<String, Object> contentMap = new HashMap<String, Object>();
							contentMap.put("item", respContent);
							contentMap.put(Constant.NODE_TAG, "root");
							content = Xml.toXML(contentMap, srvResponse.getCharset(), true);
						} else {
							content = Xml.toXML(respContent, srvResponse.getCharset(), true);
						}
					} catch (MarshalException e) {
						throw new IOException(e);
					}
				} else {
					content = respContent;
				}
			}
		}

		long contentSize = 0;

		if (content != null) {
			if (respContent instanceof InputStream) {
				InputStream is = (InputStream) respContent;
				OutputStream os = httpResponse.getOutputStream();
				byte[] bis = new byte[KB];
				while (-1 != is.read(bis)) {
					os.write(bis);
					contentSize += KB;
				}
				os.flush();

				is.close();
				os.close();
			} else {
				String contentStr = content.toString();
				print(charset, contentStr, httpResponse);

				contentSize = contentStr.length();
			}
		} else {
			print(charset, "", httpResponse);
		}
		
		log(httpRequest, httpResponse);

		return contentSize;

	}

	private void print(String charset, String content, HttpServletResponse response) throws IOException {
		int length = content.getBytes(charset).length;
		if (length > 0) {
			response.addIntHeader("Content-Length", length);
			response.addHeader("Content-MD5", DigestUtils.md5Hex(content));
		}

		PrintWriter writer = response.getWriter();
		writer.write(content);
		writer.flush();
		try {
			writer.close();
		} finally {
			
		}
	}
	
	private void log(HttpServletRequest request, HttpServletResponse response) {
		String logFmt = "client=%s, agent=%s, schema=%s, method=%s, uri=%s, status=%d, respBody=%d";
		
		String remoteAddr = request.getRemoteHost();
		String reqSchema = request.getScheme();
		String reqMethod = request.getMethod();
		String reqUri = request.getRequestURI();
		int status = response.getStatus();
		long bodySize = 0;
		try {
			bodySize = Long.parseLong(response.getHeader("Content-Length"));
		} catch (Exception e) {
			bodySize = 0;
		}
		String userAgent = request.getHeader("User-Agent");
		
		logger.info(String.format(logFmt, remoteAddr, userAgent, reqSchema, reqMethod, reqUri, status, bodySize));
	}
}
