package config;

import static config.DbConfig.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class WalkingConfig {
	@Bean
	public WalkingDao walkingDao() {
		return new WalkingDao(dataSource());
	}
	@Bean
	public WalkingSvc walkingSvc() {
		WalkingSvc walkingSvc = new WalkingSvc();
		walkingSvc.setWalkingDao(walkingDao());
		return walkingSvc;
	}
}
