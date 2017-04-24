package org.pinae.sarabi.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.pinae.nala.xb.Xml;
import org.pinae.nala.xb.exception.MarshalException;
import org.pinae.nala.xb.util.Constant;

import com.alibaba.fastjson.JSON;

public class ServiceOutputor {
	
	private static int KB = 1024;
	
	public long output(HttpServletResponse httpResponse, ServiceResponse srvResponse) throws IOException {

		httpResponse.setStatus(srvResponse.getStatus());

		Map<String, String> respHeader = srvResponse.getHeaders();
		for (String headerKey : respHeader.keySet()) {
			String headerValue = respHeader.get(headerKey);
			httpResponse.addHeader(headerKey, headerValue);
		}

		String contentType = srvResponse.getContentType();

		httpResponse.setContentType(contentType);
		if (srvResponse.getCharset() != null) {
			httpResponse.setCharacterEncoding(srvResponse.getCharset());
		}

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
				print(contentStr, httpResponse);

				contentSize = contentStr.length();
			}
		} else {
			print("", httpResponse);
		}

		return contentSize;

	}

	private void print(String content, HttpServletResponse response) throws IOException {
		int length = content.length();
		if (length > 0) {
			response.addIntHeader("Content-Length", length);
			response.addHeader("Content-MD5", DigestUtils.md5Hex(content));
		}

		PrintWriter writer = response.getWriter();
		writer.write(content);
		writer.flush();
		writer.close();
	}
}
