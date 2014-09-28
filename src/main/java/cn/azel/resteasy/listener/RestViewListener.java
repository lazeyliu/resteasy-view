package cn.azel.resteasy.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.azel.resteasy.provider.TemplateProvider;
import cn.azel.resteasy.render.TemplateRender;

public class RestViewListener implements ServletContextListener {
	private static Log logger = LogFactory.getLog(RestViewListener.class);

	protected final String PARAM_TEMPLATE_RENDER = "template-render";
	protected final String PARAM_PREFIX_PATH = "prefix-path";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		TemplateRender render = null;
		//
		String templateClass = context.getInitParameter(PARAM_TEMPLATE_RENDER);
		//
		try {
			if (templateClass != null && !"".equals(templateClass.trim())) {
				render = (TemplateRender) Class.forName(templateClass).newInstance();
			}
		} catch (Exception e) {
			throw new RuntimeException("unable to load render impl.", e);
		}
		//
		if (render != null) {
			logger.debug("Initializing TemplateRender [" + templateClass + "]");
			render.init(context);
			TemplateProvider.render = render;
		}
		String prefix_path = context.getInitParameter(PARAM_PREFIX_PATH);
		if (prefix_path != null && !"".equals(prefix_path.trim())) {
			TemplateProvider.PREFIX_PATH = prefix_path;
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
