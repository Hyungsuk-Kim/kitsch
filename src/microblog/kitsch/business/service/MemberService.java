package microblog.kitsch.business.service;

import java.util.Map;

import microblog.kitsch.business.domain.Member;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.IllegalDataException;

public interface MemberService {
	
	/**
	 * 매개변수로 들어온 Member 객체 정보에 해당하는 회원을 등록(회원 가입)한다.
	 * @param member 등록할 회원의 정보가 담긴 Member 객체
	 */
	public abstract void registerMember(Member member) throws DataDuplicatedException;
	
	/**
	 * 매개변수로 들어온 Member 객체 정보에 해당하는 회원 정보를 갱신(회원정보 변경)한다.
	 * @param member 수정할 사용자의 정보가 담긴 Member 객체
	 */
	public abstract void updateMember(Member member) throws DataNotFoundException;
	
	/**
	 * 매개변수(argument)로 들어온 Member 객체 
	 * @param email 확인하고자하는 회원의 E-Mail(ID)
	 * @param password 확인하고자하는 회원의 password
	 * @return 유효성 검증 정보가 포함된 Member 객체
	 */
	public abstract Member loginCheck(String email, String password);

	/**
	 * 매개변수로 들어온 Member 객체 정보에 해당하는 회원을 제거(회원 탈퇴)한다.
	 * @param member 삭제할 회원의 정보가 담긴 Member 객체
	 */
	public abstract void removeMember(Member member) throws DataNotFoundException;
	
	/**
	 * 매개변수로 들어온 이름과 같은 회원을 검색한다.
	 * @param name 검색하고자하는 회원의 name
	 * @return 매개변수로 들어온 문자열과 일치하는 이름을 가진 회원 정보가 포함된 Member 객체
	 */
	public abstract Member findMember(String name) throws DataNotFoundException;
	/** 
	 * 모든 User를 검색한다.
	 * @return 저장소에 저장된 모든 Member 객체 배열
	 */
	public abstract Member[] getAllUsers();
	
	/**
	 * 조건에 해당하는 Member 배열 객체를 조회한다.
	 * @param searchInfo 조회하고자하는 조건을 담은 Map 객체
	 * @return 조건에 해당하는 Member 배열 객체
	 */
	public abstract Member[] getMemberList(Map<String, Object> searchInfo);
	
	/**
	 * 조건에 해당하는 회원의 수를 조회한다.
	 * @param searchInfo 조회하고자하는 조건을 담은 Map 객체
	 * @return 조건에 해당하는 회원의 수
	 */
	public abstract int getMemberCount(Map<String, Object> searchInfo);
	
	/**
	 * 인자로 받은 회원 이름의 사용 가능 여부를 리턴한다.
	 * @param 사용하고자하는 회원의 이름의 문자열 값
	 * @return 사용가능한 이름일 경우 true, 이미 존재하는 이름일 경우 false를 리턴
	 */
	public abstract boolean availableName(String name);
	
	public abstract void giveRole(Member administrator, String targetMemberName, int role) throws DataNotFoundException, IllegalDataException;
}
