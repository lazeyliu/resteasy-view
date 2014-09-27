package cn.azel.resteasy.render.impl;

import cn.azel.resteasy.render.TemplateRender;
import cn.azel.resteasy.response.ModelAndView;
import org.jboss.resteasy.spi.HttpRequest;

import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.util.Map.Entry;

/**
 * Renders response using Apache Velocity.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class JspRender implements TemplateRender {

	public void init(ServletContext servletContext) {
	}

	public void writeTo(HttpRequest request, String resourcePath, ModelAndView result, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream os) throws IOException {
		try {
			for (Entry<String, Object> modelDataPair : result.entrySet()) {
				request.setAttribute(modelDataPair.getKey(), modelDataPair.getValue());
			}
			request.forward(resourcePath);
		} catch (Exception e) {
			throw new RuntimeException("Unable to write  JSP response.", e);
		}
	}
}
