package cn.azel.resteasy.provider;

import cn.azel.resteasy.render.TemplateRender;
import cn.azel.resteasy.render.impl.JspRender;
import cn.azel.resteasy.response.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.spi.HttpRequest;

import javax.servlet.ServletContext;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * TemplatingMessageBodyWriter is enabled for every JAX-RS method annotated with <code>@ResponseTemplate</code> annotation and delegates response production to the {@link TemplateRender}.
 *
 * @author <a href="http://community.jboss.org/people/jharting">Jozef Hartinger</a>
 */
@Provider
@Produces("*/*")
public class TemplateProvider implements MessageBodyWriter<ModelAndView> {
	private static Log logger = LogFactory.getLog(TemplateProvider.class);

	public static String PREFIX_PATH = "/WEB-INF";
	public static String SUFFIX_PATH = ".jsp";

	private TemplateRender render;
	private final TemplateRender defaultRender = new JspRender();
	@Context
	private ServletContext context;
	@Context
	private HttpRequest request;

	/**
	 * Returns true if and only if the templating extension is enabled and the method contains the <code>@ResponseTemplate</code> annotation.
	 */
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		if (render == null) {
			final String template_render_key = "template-render";
			String templateClass = context.getInitParameter(template_render_key);
			try {
				if (templateClass != null && !"".equals(templateClass.trim())) {
					render = (TemplateRender) Class.forName(templateClass).newInstance();
				}
			} catch (Exception e) {
				throw new RuntimeException("unable to load render impl.", e);
			}
			//
			if (render != null) {
				logger.debug("Initializing TemplateRender.");
				render.init(context);
			}
		}
		return true;
	}

	public long getSize(ModelAndView result, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	public void writeTo(ModelAndView result, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
		final String resourcePath = PREFIX_PATH + result.getViewPath() + SUFFIX_PATH;
		if (render != null) {
			render.writeTo(request, resourcePath, result, annotations, mediaType, httpHeaders, entityStream);
		} else {
			// jsp
			defaultRender.writeTo(request, resourcePath, result, annotations, mediaType, httpHeaders, entityStream);
		}
	}

}
