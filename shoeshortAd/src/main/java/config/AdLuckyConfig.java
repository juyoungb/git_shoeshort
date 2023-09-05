package config;

import static config.DbCon.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

public class AdLuckyConfig {
	@Bean
	public AdLuckyDao adLuckyDao() {
		return new AdLuckyDao(dataSource());
	}
	
	@Bean
	public AdLuckySvc adLuckySvc() {
		AdLuckySvc adLuckySvc = new AdLuckySvc();
		adLuckySvc.setAdLuckyDao(adLuckyDao());
		return adLuckySvc;
	}
}
