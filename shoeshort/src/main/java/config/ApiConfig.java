package config;


import static config.DbConfig.*;
import org.springframework.context.annotation.*;
import api.*;

@Configuration
public class ApiConfig {
	@Bean
	public ApiDao apiDao() {
		return new ApiDao(dataSource());
	}
	@Bean
	public ApiSvc apiSvc() {
		ApiSvc apiSvc = new ApiSvc();
		apiSvc.setApiDao(apiDao());
		return apiSvc;
	}
	
}
