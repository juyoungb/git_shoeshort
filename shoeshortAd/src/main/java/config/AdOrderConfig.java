package config;

import static config.DbCon.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class AdOrderConfig {
	@Bean
	public AdOrderDao adOrderDao() {
		return new AdOrderDao(dataSource());
	}
	
	@Bean
	public AdOrderSvc adOrderSvc() {
		AdOrderSvc adOrderSvc = new AdOrderSvc();
		adOrderSvc.setAdOrderDao(adOrderDao());
		return adOrderSvc;
	}
}
