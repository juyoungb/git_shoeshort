package config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

import api.*;
import ctrl.*;
import svc.*;

@Configuration
public class CtrlConfig {
   @Autowired
   private LoginSvc loginSvc;
   @Autowired
   private MemberSvc memberSvc;   
   @Autowired
   private EventWcupSvc eventWcupSvc;  
   @Autowired
   private OrderSvc orderSvc;
   @Autowired
   private ProductSvc productSvc;
   @Autowired
   private StyleSvc styleSvc;
   @Autowired
   private RcmdSvc rcmdSvc;
   @Autowired
   private MainSvc mainSvc;
   @Autowired
   private ApiSvc apiSvc;
   @Autowired
   private CsSvc csSvc;
   @Autowired
   private StoreSvc storeSvc;
   @Autowired
   private EvtLuckySvc evtLuckySvc;
   @Autowired
   private WalkingSvc walkingSvc;

   @Bean
   public LoginCtrl loginCtrl() {
      LoginCtrl loginCtrl = new LoginCtrl();      
      loginCtrl.setLoginSvc(loginSvc);
      return loginCtrl;
   }
   
   @Bean
   public LogoutCtrl logoutCtrl() {
      return new LogoutCtrl();
   }
   
   @Bean
   public MemberCtrl memberCtrl() {
	   MemberCtrl memberCtrl = new MemberCtrl();
	   memberCtrl.setMemberSvc(memberSvc);   
      return memberCtrl;
   }
   
   @Bean
   public OrderCtrl orderCtrl() {
	   OrderCtrl orderCtrl = new OrderCtrl();
	   orderCtrl.setMemberSvc(memberSvc); 
	   orderCtrl.setOrderSvc(orderSvc);   
      return orderCtrl;
   }
   
   @Bean 
   public ProductCtrl productCtrl() {
	   ProductCtrl productCtrl = new ProductCtrl();
	   productCtrl.setProductSvc(productSvc);   
      return productCtrl;
   }
   
   @Bean 
   public EventWcupCtrl eventWcupCtrl() {
	   EventWcupCtrl eventWcupCtrl = new EventWcupCtrl();
	   eventWcupCtrl.seteventWcupSvc(eventWcupSvc);	   
	   return eventWcupCtrl;
   }
   
   @Bean
   public StyleCtrl styleCtrl() {
	   StyleCtrl styleCtrl = new StyleCtrl();      
	   styleCtrl.setStyleSvc(styleSvc);
	   
	   return styleCtrl;
   }
   
	@Bean
	public RcmdCtrl rcmdCtrl() {
		RcmdCtrl rcmdCtrl = new RcmdCtrl();      
		rcmdCtrl.setRcmdSvc(rcmdSvc);
		
		return rcmdCtrl;
	}
	@Bean
	public MainCtrl mainCtrl() {
		MainCtrl mainCtrl = new MainCtrl();      
		mainCtrl.setProductSvc(productSvc); 
		mainCtrl.setStyleSvc(styleSvc);
		mainCtrl.setMainSvc(mainSvc);
		
		return mainCtrl;
	}
	@Bean
	public ApiCtrl apiCtrl() {
		ApiCtrl apiCtrl = new ApiCtrl();
		apiCtrl.setLoginSvc(loginSvc);
		apiCtrl.setApiSvc(apiSvc);
		
		return apiCtrl;
	}
   @Bean
	public CsCtrl csCtrl() {
		CsCtrl csCtrl = new CsCtrl();      
		csCtrl.setCsSvc(csSvc);
		
		return csCtrl;
	}
	@Bean
	public StoreCtrl storeCtrl() {
		StoreCtrl storeCtrl = new StoreCtrl();      
		storeCtrl.setStoreSvc(storeSvc);
		
		return storeCtrl;
	}
	
	@Bean
	public EvtLuckyCtrl evtLuckyCtrl() {
		EvtLuckyCtrl evtLuckyCtrl = new EvtLuckyCtrl();      
	   evtLuckyCtrl.setEvtLuckySvc(evtLuckySvc);
	   return evtLuckyCtrl;
    }

	@Bean
	public WalkingCtrl walkingCtrl() {
		WalkingCtrl walkingCtrl = new WalkingCtrl();      
		walkingCtrl.setWalkingSvc(walkingSvc);
	   return walkingCtrl;
    }
}