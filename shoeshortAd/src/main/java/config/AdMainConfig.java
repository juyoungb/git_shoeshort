package config;

import static config.DbCon.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class AdMainConfig {
	@Bean
	public AdMainDao adMainDao() {
		return new AdMainDao(dataSource());
	}
	
	@Bean
	public AdMainSvc adMainSvc() {
		AdMainSvc adMainSvc = new AdMainSvc();
		adMainSvc.setAdMainDao(adMainDao());
		return adMainSvc;
	}
}
