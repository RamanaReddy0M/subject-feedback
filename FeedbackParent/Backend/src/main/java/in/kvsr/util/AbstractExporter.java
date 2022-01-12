package in.kvsr.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

public class AbstractExporter {
	
	public void setResponseHeader(HttpServletResponse httpServletResponse, 
			String contentType, String extention) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp = dateFormat.format(new Date());
		String fileName = "Faculty_"+timestamp+extention;
		httpServletResponse.setContentType(contentType);
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachent; filename=" + fileName;
		httpServletResponse.setHeader(headerKey, headerValue);
	}

}
