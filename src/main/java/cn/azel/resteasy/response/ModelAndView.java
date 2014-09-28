package cn.azel.resteasy.response;

import java.util.LinkedHashMap;

import cn.azel.resteasy.render.TemplateRender;

public class ModelAndView extends LinkedHashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7072726762995424032L;

	/**
	 * 跳转页面
	 */
	private String viewPath;

	/**
	 * 响应key
	 */
	private String responseName = "response";

	/**
	 * 渲染器的类型
	 */
	public Class<? extends TemplateRender> renderClass;

	public String getResponseName() {
		return responseName;
	}

	public void setResponseName(String responseName) {
		this.responseName = responseName;
	}

	public ModelAndView(String viewpath) {
		this.viewPath = viewpath;
	}

	public String getViewPath() {
		return viewPath;
	}

	public void setViewPath(String viewPath) {
		this.viewPath = viewPath;
	}

	public Class<? extends TemplateRender> getRenderClass() {
		return renderClass;
	}

	public void setRenderClass(Class<? extends TemplateRender> renderClass) {
		this.renderClass = renderClass;
	}

}
