package config;

import static config.DbCon.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class AdStoreConfig {
	@Bean
	public AdStoreDao adStoreDao() {
		return new AdStoreDao(dataSource());
	}
	@Bean
	public AdStoreSvc adStoreSvc() {
		AdStoreSvc adStoreSvc = new AdStoreSvc();
		adStoreSvc.setAdStoreDao(adStoreDao());
		return adStoreSvc;
	}
}
