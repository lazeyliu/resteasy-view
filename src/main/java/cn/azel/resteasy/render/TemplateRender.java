package cn.azel.resteasy.render;

import cn.azel.resteasy.response.ModelAndView;
import org.jboss.resteasy.spi.HttpRequest;

import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;

/**
 * interface of all renders 所有渲染器的接口
 * 
 * @author liuzeyuan
 *
 */
public interface TemplateRender {
	//

	/**
	 * 初始化渲染器
	 * 
	 * @param servletContext
	 */
	void init(ServletContext servletContext);

	/**
	 * 返回内容 Produces the response.
	 *
	 * @param result
	 *            object returned by the JAX-RS method
	 * @param annotation
	 * @ResponseTemplate annotation instance
	 * @param annotations
	 *            annotations available on the JAX-RS method
	 * @param mediaType
	 *            requested media type
	 * @param httpHeaders
	 *            request HTTP headers
	 * @param os
	 *            HTTP response output stream
	 * @throws IOException
	 */
	void writeTo(HttpRequest request, String resourcePath, ModelAndView result, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream os) throws IOException;
}
