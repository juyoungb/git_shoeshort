package ctrl;

import java.util.*;
import javax.servlet.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import svc.*;
import vo.*;

@Controller
public class ProductCtrl {
	private ProductSvc productSvc;
	
	public void setProductSvc(ProductSvc productSvc) {
		this.productSvc = productSvc;
	}
	
	// New/�Ż�ǰ List�����ִ� @GetMapping
	@GetMapping("/newProduct")
	public String newProduct(HttpServletRequest request) throws Exception {
		
		List<ProductInfo> productList = productSvc.getNewList(); 
		
		request.setAttribute("productList", productList);
		return "product/newProduct";
	}	
	
	// ��ǰ Ŭ���� view�� �̵��ϴ� @GetMapping
	@GetMapping("/productView")
	public String productView(Model model, HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String piid = request.getParameter("piid");
		
		List<ProductInfo> productList = productSvc.productView(piid); // ��ǰ�� ���� ������ �������°�
		
		List<ProductStock> stockList = productSvc.productStockView(piid); // ��ǰ�� ���� �Ź� ������ �������°�
	
		
		request.setAttribute("productList", productList); 
		model.addAttribute("stockList", stockList);	
		
		return "product/productView";
	}
	
	// ��з�(����,����,Ű��) Ŭ���� List�� �̵��ϴ� @GetMapping
	@GetMapping("/productList")
	public String productList(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		request.setCharacterEncoding("utf-8");

		int cpage = 1, spage = 0, psize = 12, bsize = 10, rcnt = 0, pcnt = 0;
		String gender ="", s = "", where = "", schargs ="";
		
		if(request.getParameter("cpage") != null) 
		cpage = Integer.parseInt(request.getParameter("cpage"));
		
		
		gender = request.getParameter("g"); 
		if(gender != null ) where += " and a.pi_gubun= '"+ gender +"' ";
		
		String pcb = request.getParameter("pc	b"); // ��з� ����		
		String sch = request.getParameter("sch"); // �˻� ����(���ݴ� : p, ��ǰ�� : n, �귣�� : b, ������ :s, ����:m)
		
		
		if (sch != null && !sch.equals("")) {// �˻����� : &sch=ntest,bB1:B2:B3,p100000~20000	
			schargs +="&sch=" + sch;
			String[] arrSch = sch.split(",");
			for (int i = 0 ; i < arrSch.length ; i++) {
				char c = arrSch[i].charAt(0);
				if(c == 'n') {// ��ǰ�� �˻��� ���(n �˻���)
					where += " and a.pi_name like '%" + arrSch[i].substring(1) + "%' ";
					
				} else if (c =='b') {// �귣�� �˻��� ���(b �귣�� 1:�귣��2)
				// where +=" and (a.pb_id = '��1' or a.pb_id = '��2')";
					String[] arr = arrSch[i].substring(1).split(":"); // :�� ��� �������
					where += " and (";
					for(int j = 0 ; j < arr.length; j++) {
						where += (j == 0 ? "" : " or ") + "a.pb_id ='" + arr[j] + "' "; // +�� �켱������ �����Ƿ� ���׿����ڿ� ()�� ����
					} 
					where += ") ";
					
				} else if (c =='s') {// �귣�� �˻��� ���(s ������ 1:������2)
						s = "k";
						String[] arr = arrSch[i].substring(1).split(":"); // :�� ��� �������
						where += " and (";
						for(int j = 0 ; j < arr.length; j++) {
							where += (j == 0 ? "" : " or ") + "b.ps_size = " + arr[j]; // +�� �켱������ �����Ƿ� ���׿����ڿ� ()�� ����
							
						} 
						where += ") ";
						
						
				}else if (c =='g') {// �귣�� �˻��� ���(s �귣�� 1:�귣��2)
						// where +=" and (a.pb_id = '��1' or a.pb_id = '��2')";
							String[] arr = arrSch[i].substring(1).split(":"); // :�� ��� �������
							where += " and (";
							for(int j = 0 ; j < arr.length; j++) {
								where += (j == 0 ? "" : " or ") + "a.pi_gubun ='" + arr[j] + "' "; // +�� �켱������ �����Ƿ� ���׿����ڿ� ()�� ����
							} 
							where += ") ";
													
				} else if (c =='p') { // ���ݴ� �˻��� ���(p���۰�~���ᰡ (�̻�~����))
					String sp = arrSch[i].substring(1, arrSch[i].indexOf('~'));
					if (sp !=null && !sp.equals(""))
					where += " and a.pi_price >= " + sp;
					
					String ep = arrSch[i].substring(arrSch[i].indexOf('~') + 1) ;
					if (ep !=null && !ep.equals(""))
					where += " and a.pi_price <= " + ep;
					
				}
			}
		}	
		
		String orderBy = " order by "; // ��� ���� ���� 
		String  ob = request.getParameter("ob"); // ���� ����
		//System.out.println(ob);
		if(ob == null || ob.equals(""))  ob = "a";
		String obargs = "&ob=" + ob; // ���� ������ ���� ������Ʈ��
		switch (ob) {	
		case "a" : //�ֽż�(�⺻��)
			orderBy += " a.pi_date desc ";  break;
		case "b" : // �Ǹŷ�(�α��)
			orderBy += " a.pi_sale desc ";  break;
		case "c" : // ���� ���ݼ�
			orderBy += " a.pi_price asc ";  break;
		case "d" : // ���� ���ݼ�
			orderBy += " a.pi_price desc ";  break;	
		}
		
		List<ProductInfo> productList = productSvc.getProductList(cpage, psize, where, orderBy,s);//�˻��� ��ǰ�� �� ���� ���������� ������ ��ǰ ����� �޾ƿ�

		rcnt = productSvc.getProductCount(where); // �˻��� ��ǰ�� �� ������ ��ü ���������� ���� �� ����
		
	
		
		List<ProductBrand> brandList = productSvc.getBrandList(); // �˻� �������� ������ �귣����� ����� �޾ƿ�
		List<ProductCtgrBig> ctgrList = productSvc.getCtgrList(); // �˻� �������� ������ ī�װ����� ����� �޾ƿ�
		List<ProductStock> stockList = productSvc.getSizeList(); // �˻� �������� ������ ��������� ����� �޾ƿ�

		pcnt = rcnt /psize;
		if(rcnt % psize > 0) 	pcnt++;
		spage = (cpage -1) / bsize * bsize + 1;
		
		// ����¡�� ��ũ�� ���õ� �������� pageInfo�� �ν��Ͻ��� ����
		
		
			
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCpage(cpage);		pageInfo.setSpage(spage);
		pageInfo.setPsize(psize); 		pageInfo.setBsize(bsize);				
		pageInfo.setPcnt(pcnt);		    pageInfo.setRcnt(rcnt);
		pageInfo.setSch(sch);			pageInfo.setOb(ob);
	   	pageInfo.setSchargs(schargs);	pageInfo.setObargs(obargs);   
	   	pageInfo.setGender(gender);   

	   	

		request.setAttribute("pageInfo", pageInfo);	  	  // ������ ����
		request.setAttribute("ctgrList", ctgrList);   	  // ī�װ� 
		request.setAttribute("brandList", brandList); 	  // �귣��
		request.setAttribute("stockList", stockList);     // ������ ����Ʈ
		request.setAttribute("productList", productList); // ��ǰ ����Ʈ
		model.addAttribute("gender", gender);
		return "product/productList";
	}
	
}
