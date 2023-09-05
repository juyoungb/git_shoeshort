package ctrl;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.http.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class StoreCtrl {
	private StoreSvc storeSvc;
	public void setStoreSvc(StoreSvc storeSvc) {
		this.storeSvc = storeSvc;
	}
	@GetMapping("/storeInfo")
	public String storeInfo (HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String where = " where bsi_isview = 'y' ";
		String pbid = request.getParameter("pbid");
		if (pbid == null || pbid.equals("")) pbid = "NN";
		where += " and pb_id = '"+ pbid +"' ";
		
		List<StoreInfo> si = storeSvc.getStoreList(where);
		request.setAttribute("si", si);
		
		return "store/storeInfo";
	}
}
