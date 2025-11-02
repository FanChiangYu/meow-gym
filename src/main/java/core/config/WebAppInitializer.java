package core.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SpringConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SpringMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		OpenSessionInViewFilter openSessionInViewFilter = new OpenSessionInViewFilter();
		return new Filter[] { characterEncodingFilter, openSessionInViewFilter };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		MultipartConfigElement multipartConfig = new MultipartConfigElement(
			null,
			536870912,
			4294967296L,
			0
		);
		registration.setMultipartConfig(multipartConfig);
	}
}
