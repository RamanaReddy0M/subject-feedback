package in.kvsr.admin.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		/*
		 * String dirName = "faculty-images"; Path facultyImagesDir =
		 * Paths.get(dirName); String facultyimagesPath =
		 * facultyImagesDir.toFile().getAbsolutePath();
		 */
		registry.addResourceHandler("/"+"faculty-images"+"/**")
		        .addResourceLocations("file:/"+"C:/subject-feedback/feedback/FeedbackParent/Frontend/faculty-images"+"/");
		
	}
	
	
}
