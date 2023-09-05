package svc;

import java.util.List;

import dao.*;
import vo.*;

public class AdMemberSvc {
	private AdMemberDao adMemberDao;

	public void setAdMemberDao(AdMemberDao adMemberDao) {
		this.adMemberDao = adMemberDao;
	}

	public int getMemberCount(String where) {
		int rcnt = adMemberDao.getMemberCount(where);
		
		return rcnt;
	}

	public List<MemberInfo> getMemberList(int cpage, int psize, String where) {
		List<MemberInfo> memberList = adMemberDao.getMemberList(cpage, psize, where);
		
		return memberList;
	}

	public MemberDetails getMemberDetails(String miid) {
		MemberDetails memberDetails = adMemberDao.getMemberDetails(miid);
		
		return memberDetails;
	}
	public int getMemberPointCount(String where) {
		int rcnt = adMemberDao.getMemberPointCount(where);

		return rcnt;
	}
	
	public List<MemberPoint> getPointList(String where, int cpage, int psize) {
		List<MemberPoint> pointList = adMemberDao.getPointList(where, cpage, psize);
		
		return pointList;
	}
	public MemberInfo getPointForm(String miid) {
		MemberInfo memberPoint = adMemberDao.getPointForm(miid);
		
		return memberPoint;
	}
	
	public int getGivePoint(String miid, int point, String mp_desc, String mp_detail) {
		int result = adMemberDao.getGivePoint(miid, point, mp_desc, mp_detail);
		
		return result;
	}
}
