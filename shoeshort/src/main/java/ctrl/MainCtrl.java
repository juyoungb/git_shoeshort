package ctrl;

import java.io.*;
import java.time.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class MainCtrl {
	private ProductSvc productSvc;
	private StyleSvc styleSvc;
	private MainSvc mainSvc;
	
	public void setProductSvc(ProductSvc productSvc) {
		this.productSvc = productSvc;
	}		
	public void setStyleSvc(StyleSvc styleSvc) {
		this.styleSvc = styleSvc;
	}
	public void setMainSvc(MainSvc mainSvc) {
		this.mainSvc = mainSvc;
	}
	
	@GetMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		List<ProductInfo> newList = productSvc.getMainList("pi_date");
		List<ProductInfo> saleList = productSvc.getMainList("pi_sale");
		List<StyleInfo> styleList = styleSvc.getTopStyle();

		MainMedia videoInfo = mainSvc.getVideoInfo();
		List<MainMedia> imgList = mainSvc.getImgList();

		model.addAttribute("videoInfo", videoInfo);
		model.addAttribute("imgList", imgList);
		model.addAttribute("saleList", saleList);
		model.addAttribute("newList", newList);
		model.addAttribute("styleList", styleList);
		return "index";
	}

}
