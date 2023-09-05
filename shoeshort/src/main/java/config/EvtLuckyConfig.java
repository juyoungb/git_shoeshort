package config;

import static config.DbConfig.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class EvtLuckyConfig {
	@Bean
	public EvtLuckyDao evtLuckyDao() {
		return new EvtLuckyDao(dataSource());
	}
	
	@Bean
	public EvtLuckySvc evtLuckySvc() {
		EvtLuckySvc evtLuckySvc = new EvtLuckySvc();
		evtLuckySvc.setEvtLuckyDao(evtLuckyDao());
		return evtLuckySvc;
	}
	
}
