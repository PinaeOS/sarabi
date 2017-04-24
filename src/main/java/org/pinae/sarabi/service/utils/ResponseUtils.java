package org.pinae.sarabi.service.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class ResponseUtils {

	public static void output(HttpServletResponse response, int stateCode, String charset, String contentType, Map<String, Object> header,
			Object content) {

		charset = charset != null ? charset : "utf-8";
		contentType = contentType != null ? contentType : "text/plain";
		stateCode = stateCode > 200 ? stateCode : 500;

		response.setCharacterEncoding(charset);
		response.setContentType(contentType);
		response.setStatus(stateCode);

		if (header != null) {
			Set<String> headerKeySet = header.keySet();
			for (String headerKey : headerKeySet) {
				Object headerValue = header.get(headerKey);
				if (headerValue != null) {
					if (headerValue instanceof Date) {
						response.setDateHeader(headerKey, ((Date) headerValue).getTime());
					} else if (headerValue instanceof Integer) {
						response.setIntHeader(headerKey, (Integer) headerValue);
					} else {
						response.setHeader(headerKey, headerValue.toString());
					}
				}
			}
		}

		if (content != null) {
			try {
				if (content instanceof byte[]) {
					ServletOutputStream stream = response.getOutputStream();
					stream.write((byte[]) content);
					stream.flush();
				} else {
					PrintWriter writer = response.getWriter();
					writer.print(content.toString());
					writer.flush();
				}
			} catch (IOException e) {

			}
		}
	}

	public static void output(HttpServletResponse response, int stateCode, String content) {
		output(response, stateCode, "utf-8", "text/plain", null, content);
	}
	
}
