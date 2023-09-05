package config;

import static config.DbCon.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class AdWcupConfig {
	@Bean
	public AdWcupDao adWcupDao() {
		return new AdWcupDao(dataSource());
	}
	
	@Bean
	public AdWcupSvc adEvtWcupSvc() {
		AdWcupSvc adWcupSvc = new AdWcupSvc();
		adWcupSvc.setAdWcupDao(adWcupDao());
		return adWcupSvc;
	}
}
