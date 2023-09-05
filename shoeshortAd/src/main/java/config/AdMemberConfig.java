package config;

import static config.DbCon.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class AdMemberConfig {
	@Bean
	public AdMemberDao adMemberDao() {
		return new AdMemberDao(dataSource());
	}
	@Bean
	public AdMemberSvc adMemberSvc() {
		AdMemberSvc adMemberSvc = new AdMemberSvc();
		adMemberSvc.setAdMemberDao(adMemberDao());
		return adMemberSvc;
	}
}
