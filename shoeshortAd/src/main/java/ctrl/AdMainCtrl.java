package ctrl;

import java.io.*;
import java.net.URLEncoder;
import java.time.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class AdMainCtrl {
	private AdMainSvc adMainSvc;

	public void setAdMainSvc(AdMainSvc adMainSvc) {
		this.adMainSvc = adMainSvc;
	}
	@GetMapping("/adMainSet")
	public String adMainSetting(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception  {
		return "mainAdmin/adMainSetting";
	}
	@GetMapping("/adMainBox")
	public String adMainSelect(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception  {
		return "mainAdmin/adMainBox";
	}
	
}
