package svc;

import java.util.List;

import dao.*;
import vo.MainMedia;

public class MainSvc {
	private MainDao mainDao;
	
	public void setMainDao(MainDao mainDao) {
		this.mainDao = mainDao;
	}
//	public EventWcupInfo getEventList(String ewstatus){
//		EventWcupInfo eventList = eventWcupDao.getEventWcupList(ewstatus);
//		return eventList;		
//	}

	public MainMedia getVideoInfo() {
		MainMedia videoInfo = mainDao.getVideoInfo();
		return videoInfo;
	}

	public List<MainMedia> getImgList() {
		
		List<MainMedia> imgList = mainDao.getImgList();
		return imgList;
	}

}
