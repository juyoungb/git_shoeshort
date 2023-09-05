package config;

import static config.DbConfig.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class CsConfig {
	@Bean
	public CsDao csDao() {
		return new CsDao(dataSource());
	}

	@Bean
	public CsSvc csSvc() {
		CsSvc csSvc = new CsSvc();
		csSvc.setCsDao(csDao());
		return csSvc;
	}
}
