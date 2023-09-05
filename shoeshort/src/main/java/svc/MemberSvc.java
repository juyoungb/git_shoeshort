package svc;

import java.util.*;
import dao.*;
import vo.*;

public class MemberSvc {
	private MemberDao memberDao;

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	public int memberInsert(MemberInfo mi) {
		int result = memberDao.memberInsert(mi);
		return result;
	}
	public int chkDupId(String uid) {
		int result = memberDao.chkDupId(uid);		
		return result;
	}

	public int memberUpdate(MemberInfo mi) {
		int result = memberDao.memberUpdate(mi);
		return result;
	}
	public List<PointDetail> getPointList(String miid) {
		List<PointDetail> pointList = memberDao.getPointList(miid);
		return pointList;
	}
	public int setMemberInfo(String uid, String kind, String data) {

		int result = memberDao.setMemberInfo(uid,kind,data);
		return result;
	}
	public MemberInfo getMemberInfo(String mi_id, String mi_pw) {
		MemberInfo mi = memberDao.getMemberInfo(mi_id,mi_pw);
		return mi;
	}
	public List<MemberAddr> getMemberAddr(String mi_id) {
		List<MemberAddr> ma = memberDao.getMemberAddr(mi_id);
		return ma;
	}
	public MemberAddr getMemberAddr(int maidx) {
		MemberAddr ma = memberDao.getMemberAddr(maidx);
		return ma;
	}
	public int AddrInsert(MemberAddr ma) {
		int result = memberDao.AddrInsert(ma);
		return result;
	}
	public int AddrUpdate(MemberAddr ma) {
		int result = memberDao.AddrUpdate(ma);
		return result;
	}
	public int AddrDelete(String maidx) {
		int result = memberDao.AddrDelete(maidx);
		return result;
	}
	public String getMemberId(MemberInfo mi) {
		String mi_id = memberDao.getMemberId(mi);
		return mi_id;
	}
	public int checkMember(MemberInfo mi) {
		int result = memberDao.checkMember(mi);
		return result;
	}
}
