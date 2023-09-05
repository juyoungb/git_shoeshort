package config;

import static config.DbCon.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class AdCsConfig {
	@Bean
	public AdCsDao adCsDao() {
		return new AdCsDao(dataSource());
	}
	@Bean
	public AdCsSvc adCsSvc() {
		AdCsSvc adCsSvc = new AdCsSvc();
		adCsSvc.setAdCsDao(adCsDao());
		return adCsSvc;
	}
}
