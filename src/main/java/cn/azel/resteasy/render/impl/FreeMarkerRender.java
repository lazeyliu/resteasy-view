package cn.azel.resteasy.render.impl;

import cn.azel.resteasy.render.TemplateRender;
import cn.azel.resteasy.response.ModelAndView;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.jboss.resteasy.spi.HttpRequest;

import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;

/**
 * Converts the response object to a rendered FreeMarker template.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class FreeMarkerRender implements TemplateRender {
	//
	private Configuration configuration;

	public void init(ServletContext servletContext) {
		configuration = new Configuration();
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setServletContextForTemplateLoading(servletContext, "/");
	}

	public void writeTo(HttpRequest request, String resourcePath, ModelAndView result, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream os) throws IOException {
		try {
			Template template = configuration.getTemplate(resourcePath);
			if (!result.containsKey(result.getResponseName())) {
				result.put(result.getResponseName(), result);
			}
			OutputStreamWriter writer = new OutputStreamWriter(os);
			template.process(result, writer);
			writer.flush();
		} catch (TemplateException e) {
			throw new RuntimeException("Unable to write FreeMarker response.", e);
		}
	}

}
