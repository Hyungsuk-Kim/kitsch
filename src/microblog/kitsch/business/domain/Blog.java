package microblog.kitsch.business.domain;

import java.io.Serializable;

import microblog.kitsch.KitschSystem;
import microblog.kitsch.helper.KitschUtil;

/**
 * 
 */
public class Blog implements Serializable {
	// Variables
	// Instance Variables
	private static final long serialVersionUID = 1900033617868843759L;
	
	private String blogId; // 실제 데이터베이스에서 생성되어질 블로그의 테이블 이름
	private String email; //블로그 소유자의 이메일
	private String blogName; // 블로그의 이름(URL로 사용됨)
	private int followCount; // 해당 블로그를 팔로잉한 사람의 수
	private int visitCount; // 누적 방문자수
	private int backgroundColor; // 블로그의 배경색
	private String headerImage; // 블로그 헤더 이미지 파일의 URL(Built-in / custom img)
	private int blogLayout; // 블로그의 레이아웃
	private java.util.Date regDate;
	
	// Class Variables
	/** 포스팅의 전체 정보를 포함한 포스팅들이 목록화 되어 보여지는 레이아웃 */
	public static final int LISTED_LAYOUT = 0;
	/** 포스팅의 요약 정보만 포함한 포스팅들이 목록화 되어 보여지는 레이아웃 */
	public static final int LISTED_SUMMARY_LAYOUT = 1;
	/** 포스팅의 전체 정보를 포함한 포스팅들이 행렬과 유사한 형식으로 보여지는 레이아웃 */
	public static final int GRID_LAYOUT = 2;
	
	public static final String DEFAULT_TABLE_NAME_PREFIX = "blog_id_";
	
	public static final int[] DEFAULT_BACKGROUND_COLORS = {0xffffff, 0x000000};
	public static final String[] DEFAULT_HEADER_IMAGES = {
			KitschSystem.BUILT_IN_HEADER_IMAGES_DIR + "header1.jpg",
			KitschSystem.BUILT_IN_HEADER_IMAGES_DIR + "header2.jpg"
			};
	
	// Constructors
	// 기본 블로그 생성
	public Blog(String email, String blogName) {
		this.email = email;
		this.blogName = blogName;
		this.blogLayout = LISTED_LAYOUT;
		this.backgroundColor = DEFAULT_BACKGROUND_COLORS[KitschUtil.makeRandomIndex(DEFAULT_BACKGROUND_COLORS.length)];
		//this.headerImage = DEFAULT_HEADER_IMAGES[KitschUtil.makeRandomIndex(DEFAULT_HEADER_IMAGES.length)];
	}
	
/*	// 블로그 생성용
	public Blog(String email, String blogName, String headerImage, int blogLayout) {
		this.email = email;
		this.blogName = blogName;
		this.headerImage = headerImage;
		this.blogLayout = blogLayout;
	}
	
	public Blog(String email, String blogName, int backgroundColor, int blogLayout) {
		this.email = email;
		this.blogName = blogName;
		this.backgroundColor = backgroundColor;
		this.blogLayout = blogLayout;
	}*/
	
	// 조회용
	public Blog(String blogId, String email, String blogName, int followCount, int visitCount, int backgroundColor, String headerImage, int blogLayout, java.util.Date regDate) {
		this.blogId = blogId;
		this.email = email;
		this.blogName = blogName;
		this.blogName = blogName;
		this.followCount = followCount;
		this.visitCount = visitCount;
		this.backgroundColor = backgroundColor;
		this.headerImage = headerImage;
		this.blogLayout = blogLayout;
		this.regDate = regDate;
	}

	// Methods
	@Override
	public String toString() {
		return "Blog [blogId=" + blogId + ", email=" + email + ", blogName=" + blogName + ", followCount=" + followCount
				+ ", visitCount=" + visitCount + ", backgroundColor=" + backgroundColor + ", headerImage=" + headerImage
				+ ", blogLayout=" + blogLayout + ", regDate=" + regDate + "]";
	}

	// Getters
	public String getBlogId() {
		return blogId;
	}

	public String getEmail() {
		return email;
	}

	public String getBlogName() {
		return blogName;
	}

	public int getFollowCount() {
		return followCount;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public String getHeaderImage() {
		return headerImage;
	}

	public int getBlogLayout() {
		return blogLayout;
	}

	public java.util.Date getRegDate() {
		return regDate;
	}

	// Setters
	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}

	public void setFollowCount(int followCount) {
		this.followCount = followCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setHeaderImage(String headerImage) {
		this.headerImage = headerImage;
	}

	public void setBlogLayout(int blogLayout) {
		this.blogLayout = blogLayout;
	}

	public void setRegDate(java.util.Date regDate) {
		this.regDate = regDate;
	}
	
}
