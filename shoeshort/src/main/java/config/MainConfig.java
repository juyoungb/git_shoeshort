package config;

import static config.DbConfig.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration

public class MainConfig {

	@Bean
	public MainDao mainDao() {
		return new MainDao(dataSource());
	}
	
	@Bean
	public MainSvc MainSvc() {
		MainSvc mainSvc = new MainSvc();
		mainSvc.setMainDao(mainDao());
		return mainSvc;
	}
	
}
