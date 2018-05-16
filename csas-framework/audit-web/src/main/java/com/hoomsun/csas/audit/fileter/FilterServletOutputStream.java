/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.fileter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;

public class FilterServletOutputStream extends ServletOutputStream {

	private DataOutputStream stream;

	public FilterServletOutputStream(OutputStream output) {
		stream = new DataOutputStream(output);
	}

	public void write(int b) throws IOException {
		stream.write(b);
	}

	public void write(byte[] b) throws IOException {
		stream.write(b);
	}

	public void write(byte[] b, int off, int len) throws IOException {
		stream.write(b, off, len);
	}

}