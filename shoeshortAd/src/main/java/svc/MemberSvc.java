package svc;

import java.util.List;

import dao.*;
import vo.*;

public class MemberSvc {
	private MemberDao memberDao;

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public int getMemberCount(String where) {
		int rcnt = memberDao.getMemberCount(where);
		
		return rcnt;
	}

	public List<MemberInfo> getMemberList(int cpage, int psize, String where) {
		List<MemberInfo> memberList = memberDao.getMemberList(cpage, psize, where);
		
		return memberList;
	}

	public MemberAddr getMemberAddr(String miid) {
		MemberAddr memberAddr = memberDao.getMemberAddr(miid);
		
		return memberAddr;
	}
}
