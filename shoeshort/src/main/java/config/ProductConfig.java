package config;

import static config.DbConfig.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

@Configuration
public class ProductConfig {

	@Bean
	public ProductDao productDao() {
		return new ProductDao(dataSource());
	}

	@Bean
	public ProductSvc ProductSvc() {
		ProductSvc productSvc = new ProductSvc();
		productSvc.setProductDao(productDao());
		return productSvc;
	}
}