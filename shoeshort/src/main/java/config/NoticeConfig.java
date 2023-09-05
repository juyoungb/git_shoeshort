package config;

import static config.DbConfig.*;
import org.apache.tomcat.jdbc.pool.*;
import org.springframework.context.annotation.*;
import dao.*;
import svc.*;

public class NoticeConfig {
	@Bean
	public NoticeDao noticeDao() {
		return new NoticeDao(dataSource());
	}

	@Bean
	public NoticeSvc noticeSvc() {
		NoticeSvc noticeSvc = new NoticeSvc();
		noticeSvc.setNoticeDao(noticeDao());
		return noticeSvc;
	}
}
