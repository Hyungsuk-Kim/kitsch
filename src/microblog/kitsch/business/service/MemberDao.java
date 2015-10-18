package microblog.kitsch.business.service;

import java.util.List;
import java.util.Map;

import microblog.kitsch.business.domain.Member;

public interface MemberDao {
	public abstract boolean memberEmailExists(String email);
	public abstract boolean memberNameExists(String name);
	public abstract void insertMember(Member member);
	public abstract void updateMember(Member member);
	public abstract void deleteMember(Member member);
	public abstract Member selectMemberAsName(String name);
	public abstract Member selectMemberAsEmail(String email);
	public abstract Member[] selectMemberAsRole(int role);
	public abstract Member[] selectAllMembers();
	public abstract Member checkMember(String email, String password);
	public abstract List<Member> getMemberList(Map<String, Object> searchInfo);
	public abstract int getMemberCount(Map<String, Object> searchInfo);
	public abstract void updateRole(String email, int role);
}
