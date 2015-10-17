package microblog.kitsch.business.service;

import java.util.List;
import java.util.Map;

import microblog.kitsch.business.dataaccess.MemberDaoImpl;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.IllegalDataException;

public class MemberServiceImpl implements MemberService {
	
	private MemberDao getMemberDaoImplimentation() {
		return new MemberDaoImpl();
	}

	@Override
	public Member loginCheck(String email, String password) {
		return this.getMemberDaoImplimentation().checkMember(email, password);
	}

	@Override
	public void registerMember(Member member) throws DataDuplicatedException {
		MemberDao memberDao = this.getMemberDaoImplimentation();
		if (memberDao.memberEmailExists(member.getEmail())) {
			throw new DataDuplicatedException("이미 등록된 E-Mail 입니다. [" + member.getEmail() + "]");
		} else {
			if (memberDao.memberNameExists(member.getName())) {
				throw new DataDuplicatedException("이미 등록된 회원이름 입니다. [" + member.getName() + "]");
			} else {
				memberDao.insertMember(member);
			}
		}
	}

	@Override
	public void updateMember(Member member) throws DataNotFoundException {
		MemberDao memberDao = this.getMemberDaoImplimentation();
		if (memberDao.memberEmailExists(member.getEmail())) {
			memberDao.updateMember(member);
		} else {
			throw new DataNotFoundException("등록되지 않은 회원입니다. [" + member.getEmail() + "]");
		}
	}

	@Override
	public void removeMember(Member member) throws DataNotFoundException {
		MemberDao memberDao = this.getMemberDaoImplimentation();
		if (memberDao.memberEmailExists(member.getEmail())) {
			memberDao.deleteMember(member);
		} else {
			throw new DataNotFoundException("등록되지 않은 회원입니다. [" + member.getEmail() + "]");
		}
	}

	@Override
	public Member findMember(String name) throws DataNotFoundException {
		MemberDao memberDao = this.getMemberDaoImplimentation();
		if (memberDao.memberNameExists(name)) {
			return memberDao.selectMemberAsName(name);
		} else {
			throw new DataNotFoundException("등록되지 않은 회원입니다. [" + name + "]");
		}
	}

	@Override
	public Member[] getAllUsers() {
		return this.getMemberDaoImplimentation().selectAllMembers();
	}

	@Override
	public Member[] getMemberList(Map<String, Object> searchInfo) {
		List<Member> mList = this.getMemberDaoImplimentation().getMemberList(searchInfo);
		return mList.toArray(new Member[0]);
	}

	@Override
	public int getMemberCount(Map<String, Object> searchInfo) {
		return this.getMemberDaoImplimentation().getMemberCount(searchInfo);
	}

	@Override
	public boolean availableName(String name) {
		return (!(this.getMemberDaoImplimentation().memberNameExists(name)));
	}

	@Override
	public void giveRole(Member administrator, String targetMemberName, int role) throws DataNotFoundException, IllegalDataException {
		MemberDao memberDao = this.getMemberDaoImplimentation();
		Member admin = null;
		if (memberDao.memberEmailExists(administrator.getEmail())) {
			admin = memberDao.selectMemberAsEmail(administrator.getEmail());
			if (admin.getRole() == Member.ADMINISTRATOR) {
				if (memberDao.memberNameExists(targetMemberName)) {
					Member targetMember = memberDao.selectMemberAsName(targetMemberName);
					if (role == Member.SUPER_USER || role == Member.NORMAL_USER) {
						targetMember.setRole(role);
					}
				} else {
					throw new DataNotFoundException("등록되지 않은 회원입니다. [" + targetMemberName + "]");
				}
			} else {
				throw new IllegalDataException("관리자 계정이 아닙니다. [" + administrator.getEmail() + "]");
			}
		} else {
			throw new DataNotFoundException("등록되지 않은 회원입니다. [" + administrator.getEmail() + "]");
		}
	}
	
}
