package config;

import static config.DbConfig.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class StoreConfig {
	@Bean
	public StoreDao storeDao() {
		return new StoreDao(dataSource());
	}
	@Bean
	public StoreSvc storeSvc() {
		StoreSvc storeSvc = new StoreSvc();
		storeSvc.setStoreDao(storeDao());
		return storeSvc;
	}
}
