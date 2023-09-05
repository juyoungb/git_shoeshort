package config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import ctrl.*;
import svc.*;

@Configuration
public class CtrlConfig {

	@Autowired
	private AdStyleSvc adStyleSvc;
	@Autowired
	private AdMemberSvc adMemberSvc;
	@Autowired
	private AdProductSvc adProductSvc;
	@Autowired
	private AdWcupSvc adWcupSvc;
	@Autowired
	private IndexSvc indexSvc;
	@Autowired
	private AdOrderSvc adOrderSvc;
	@Autowired
	private AdMainSvc adMainSvc;
	@Autowired
	private AdCsSvc adCsSvc; 
	@Autowired
	private AdStoreSvc adStoreSvc;
	@Autowired
	private AdLuckySvc adLuckySvc;

	@Bean
	public AdStyleCtrl adStyleCtrl() {
		AdStyleCtrl adStyleCtrl = new AdStyleCtrl();
		adStyleCtrl.setAdStyleSvc(adStyleSvc);
		
		return adStyleCtrl;
	}
	@Bean
	public AdMemberCtrl adMemberCtrl() {
		AdMemberCtrl adMemberCtrl = new AdMemberCtrl();
		adMemberCtrl.setAdMemberSvc(adMemberSvc);
		
		return adMemberCtrl;
	}
	@Bean
	public LogoutCtrl logoutCtrl() {
		return new LogoutCtrl();
	}
	@Bean
	public AdProductCtrl adProductCtrl() {
		AdProductCtrl adProductCtrl = new AdProductCtrl();
		adProductCtrl.setAdProductSvc(adProductSvc);
		
		return adProductCtrl;
	}

	@Bean
	public AdWcupCtrl adWcupCtrl() {
		AdWcupCtrl adWcupCtrl = new AdWcupCtrl();
		adWcupCtrl.setAdWcupSvc(adWcupSvc);
		
		return adWcupCtrl;
	}

	@Bean
	public IndexCtrl IndexCtrl() {
		IndexCtrl indexCtrl = new IndexCtrl();
		indexCtrl.setIndexSvc(indexSvc);
		
		return indexCtrl;
	}

	@Bean
	public AdOrderCtrl adOrderCtrl() {
		AdOrderCtrl adOrderCtrl = new AdOrderCtrl();
		adOrderCtrl.setAdOrderSvc(adOrderSvc);
		
		return adOrderCtrl;
	}

	@Bean
	public AdMainCtrl adMainCtrl() {
		AdMainCtrl adMainCtrl = new AdMainCtrl();
		adMainCtrl.setAdMainSvc(adMainSvc);
		
		return adMainCtrl;
	}
	@Bean
	public AdCsCtrl adCsCtrl() {
		AdCsCtrl adCsCtrl = new AdCsCtrl();
		adCsCtrl.setAdCsSvc(adCsSvc);
		
		return adCsCtrl;
	}
	@Bean
	public AdStoreCtrl adStoreCtrl() {
		AdStoreCtrl adStoreCtrl = new AdStoreCtrl();
		adStoreCtrl.setAdStoreSvc(adStoreSvc);
		
		return adStoreCtrl;
	}
	@Bean
	public AdLuckyCtrl adLuckyCtrl() {
		AdLuckyCtrl adLuckyCtrl = new AdLuckyCtrl();
		adLuckyCtrl.setAdLuckySvc(adLuckySvc);
		
		return adLuckyCtrl;
	}
}