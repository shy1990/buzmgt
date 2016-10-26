package com.wangge.buzmgt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * 自定义El表达式将Object转换Json
 * ELObjectToJsonConfig
 *
 * @author ChenGuop
 * @date 2016/10/10
 */
@Configuration
public class ELObjectToJsonConfig extends SimpleTagSupport {

	private Object obj;

	@Override
	public void doTag() throws JspException, IOException {
		ObjectMapper om = new ObjectMapper();
		getJspContext().getOut().write(obj.toString());
		System.out.println(obj);
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}