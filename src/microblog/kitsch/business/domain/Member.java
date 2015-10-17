package microblog.kitsch.business.domain;

import java.io.Serializable;

/**
 * 회원과 관련한 정보를 저장하고 있는 객체를 정의한 도메인 클래스(Value Object, Data Transfer Object)
 */
public class Member implements Serializable {
	// Variables
	// Instance Variables
	private static final long serialVersionUID = -6332193929269000121L;

	private String email; // 로그인할 때, ID로 사용될 E-mail
	private String name; // Member name 다른 회원들과 구분될 수 있는 이름(닉네임)
	private String password; // 해당 회원 계정의 password
	private java.util.Date regDate; // 계정 생성한 날짜
	private int role; // 회원의 타입 (NORMAL_USER or SUPER_USER)
	private int check; // 로그인 검증의 결과가 담길 변수

	// Class Variables
	/** 해당 사용자가 관리자임을 나타내는 상수 */
	public static final int ADMINISTRATOR = -1;
	/** 해당 사용자가 일반 사용자임을 나타내는 상수 */
	public static final int NORMAL_USER = 0;
	/** 해당 사용자가 부관리자임을 나타내는 상수 */
	public static final int SUPER_USER = 1; // 수퍼유저(admin이 지정한 관리자, 관리자 임명 권한은 없음.)
	
	/** 유효한 회원임을 나타내는 상수 */
	public static final int VALID_MEMBER = 10;
	/** E-Mail(ID)이 존재하지 않는 회원임을 나타내는 상수 */
	public static final int INVALID_EMAIL = 0;
	/** Password가 일치하지 않는 회원임을 나타내는 상수 */
	public static final int INVALID_PASSWORD = -10;
	

	// Constructors
	public Member() {
	}

	// 계정 생성용
	public Member(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.role = Member.NORMAL_USER;
	}

	// 조회용
	public Member(String email, String name, String password, java.util.Date regDate, int role) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.regDate = regDate;
		this.role = role;
	}

	// 로그인용
	public Member(String email, String password) {
		this.email = email;
		this.password = password;
	}

	// Methods
	@Override
	public String toString() {
		return "Member [email=" + email + ", name=" + name + ", password=" + password + ", regDate=" + regDate
				+ ", role=" + role + ", check=" + check + "]";
	}

	// Getters
	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public java.util.Date getRegDate() {
		return regDate;
	}

	public int getRole() {
		return role;
	}

	public int getCheck() {
		return check;
	}

	// Setters
	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRegDate(java.util.Date regDate) {
		this.regDate = regDate;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public void setCheck(int check) {
		this.check = check;
	}
}