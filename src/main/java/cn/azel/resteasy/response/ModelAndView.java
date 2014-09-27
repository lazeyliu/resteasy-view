package cn.azel.resteasy.response;

import java.util.LinkedHashMap;

public class ModelAndView extends LinkedHashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7072726762995424032L;

	private String viewPath;

	private String responseName = "response";

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

}
