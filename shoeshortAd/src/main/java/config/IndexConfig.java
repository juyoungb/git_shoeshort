package config;

import static config.DbCon.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class IndexConfig {
	@Bean
	public IndexDao indexDao() {
		return new IndexDao(dataSource());
	}
	
	@Bean
	public IndexSvc indexSvc() {
		IndexSvc indexSvc = new IndexSvc();
		indexSvc.setIndexDao(indexDao());
		return indexSvc;
	}
}
