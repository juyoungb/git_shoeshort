package config;

import static config.DbConfig.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class EventWcupConfig {

	@Bean
	public EventWcupDao eventWcupDao() {
		return new EventWcupDao(dataSource());
	}
	
	@Bean
	public EventWcupSvc EventWcupSvc() {
		EventWcupSvc eventWcupSvc = new EventWcupSvc();
		eventWcupSvc.setEventWcupDao(eventWcupDao());
		return eventWcupSvc;
	}
	
}
