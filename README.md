resteasy-view
=============

for resteasy mvc

just a template render ,freemarker ,jsp and velocity
for fun

configuration：

	<context-param>
		<param-name>rest-prefix-path</param-name>
		<param-value>/WEB-INF</param-value>
	</context-param>
	<context-param>
		<param-name>template-render</param-name>
		<param-value>cn.azel.resteasy.render.impl.FreeMarkerRender</param-value>
	</context-param>

use in code:
	
	@GET
	@Path("/test")
	public ModelAndView get() {
		ModelAndView mv = new ModelAndView("/index.ftl");
		return mv;
	}
