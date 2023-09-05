package svc;

import java.util.List;

import dao.*;
import vo.*;

public class ScheduleSvc {
	private ScheduleDao scheduleDao;

	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}

	public int scheduleInsert(ScheduleInfo si) {
		int result = scheduleDao.scheduleInsert(si);
		return result;
	}

	public List<ScheduleInfo> getScheduleList(String uid, int y, int m) {
		List<ScheduleInfo> schduleList = scheduleDao.getScheduleList(uid, y, m);
		return schduleList;
	}
}
