package ctrl;

import java.util.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class NoticeCtrl {
	private NoticeSvc noticeSvc;
	
	public void setNoticeSvc(NoticeSvc noticeSvc) {
		this.noticeSvc = noticeSvc;
	}
	@GetMapping("noticeList")
	public String noticeList () {
		
		return "notice/notice_list";
	}
}
