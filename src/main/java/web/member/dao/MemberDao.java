package web.member.dao;

import web.member.pojo.Member;

public interface MemberDao {
	
	Member selectByUsernameAndPassword(Member member);
}
