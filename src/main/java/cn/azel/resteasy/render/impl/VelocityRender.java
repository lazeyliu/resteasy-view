package cn.azel.resteasy.render.impl;

import cn.azel.resteasy.render.TemplateRender;
import cn.azel.resteasy.response.ModelAndView;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jboss.resteasy.spi.HttpRequest;

import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.util.Properties;

/**
 * Renders response using Apache Velocity.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
public class VelocityRender implements TemplateRender {

	public void init(ServletContext servletContext) {
		try {
			Properties properties = new Properties();
			properties.setProperty("resource.loader", "servlet");
			properties.setProperty("servlet.resource.loader.class", "org.apache.velocity.tools.view.WebappResourceLoader");
			Velocity.setApplicationAttribute("javax.servlet.ServletContext", servletContext);
			Velocity.init(properties);
		} catch (Exception e) {
			throw new RuntimeException("Unable to init  Velocity", e);
		}
	}

	public void writeTo(HttpRequest request, String resourcePath, ModelAndView result, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream os) throws IOException {
		try {
			Template template = Velocity.getTemplate(resourcePath);
			if (!result.containsKey(result.getResponseName())) {
				result.put(result.getResponseName(), result);
			}
			OutputStreamWriter writer = new OutputStreamWriter(os);
			template.merge(new VelocityContext(result), writer);
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException("Unable to write  Velocity response.", e);
		}
	}
}
