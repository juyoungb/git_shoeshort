package config;

import static config.DbConfig.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class StyleConfig {
	@Bean
	public StyleSvc styleSvc() {
		StyleSvc styleSvc = new StyleSvc();
		styleSvc.setStyleDao(styleDao());
		return styleSvc;
	}
	
	@Bean
	public StyleDao styleDao() {
		return new StyleDao(dataSource());
	}
}
