package microblog.kitsch.business.domain;

import java.io.Serializable;

/**
 * 
 */
public class Posting implements Serializable {
	// Variables
	// Instance Variables
	private static final long serialVersionUID = -7379667744373720254L;
	
	private int num; // 블로그 내에서 해당 포스팅의 번호
	private String title; // 포스팅 제목
	private String writer; // 작성자의 name
	private PostingContent contents; // 포스팅의 내용을 담은 PostingContent 객체
	private int contentType; // 포스팅의 내용의 타입
	//private String ip; // 포스팅한 client의 ip 주소
	private int readCount; // 조회수
	private java.util.Date regDate; // 등록 날짜
	private int likes; // 좋아요 수
	private int exposure; // 공개여부 (PUBLIC or PRIVATE)
	private String tags; // 태그
	private int ref; // 원본 글의 번호(num)
	private int replyStep; // 원본 글에 대한 총 댓글의 수 혹은 순서
	private int replyDepth; // 원본 글(0)을 기준으로 한 댓글의 깊이(레벨)
	private int replyCount; // 현재 (댓)글에 달린 댓글의 수
	private int postingType; // 현재 글이 일반 글일 경우 0, 댓글인 경우 1, Q&A인 경우 2
	private int reblogCount; // 리블로그한 횟수
	private int reblogOption;
	
	// Class Variables
	public static final String PATH_DELIMITER = "^";
	public static final String TAG_DELIMITER = "#";
	
	public static final int PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG = 0; // 댓글과 리블로그를 허용한 공개 포스팅
	public static final int PUBLIC_NO_REPLY_AND_ALLOW_REBLOG = 1; // 댓글은 허용하지 않고 리블로그는 허용한 공개 포스팅
	public static final int PUBLIC_ALLOW_REPLY_AND_NO_REBLOG = 2; // 댓글은 허용하고 리블로그는 허용하지 않는 공개 포스팅
	public static final int PUBLIC_NO_REPLY_NO_REBLOG = 3; // 댓글과 리블로그를 허용하지 않는 공개 포스팅
	public static final int PRIVATE = 4; // 비공개 포스팅
	
	public static final int NORMAL_TYPE_POSTING = 0; // 일반 포스팅
	public static final int REPLY_TYPE_POSTING = 1; // 댓글
	public static final int QNA_TYPE_POSTING = 2; // QnA
	
	public static final int NOTHING = 0;
	public static final int ON_UPDATE_AND_DELETE_CASCADE = 1;
	public static final int ON_UPDATE_CASCADE = 2;
	public static final int ON_DELETE_CASCADE = 3;
	public static final int SET_NULL = 4;
	
	// Constructors
	// 조회용 - all instance variables
	public Posting(int num, String title, String writer, PostingContent contents, int contentType,
			int readCount, java.util.Date regDate, int likes, int exposure, String tags, int ref, int replyStep, 
			int replyDepth, int replyCount, int postingType, int reblogCount, int reblogOption) {
		this.num = num;
		this.title = title;
		this.writer = writer;
		this.contents = contents;
		this.contentType = contentType;
		this.readCount = readCount;
		this.regDate = regDate;
		this.likes = likes;
		this.exposure = exposure;
		this.tags = tags;
		this.ref = ref;
		this.replyStep = replyStep;
		this.replyDepth = replyDepth;
		this.replyCount = replyCount;
		this.postingType = postingType;
		this.reblogCount = reblogCount;
		this.reblogOption = reblogOption;
	}
	
	// 포스팅용 (글 작성)
	public Posting(String title, String writer, PostingContent contents, int contentType, /*java.util.Date regDate,*/
			int exposure, String tags, int postingType, int reblogOption) {
		this.title = title;
		this.writer = writer;
		this.contents = contents;
		//this.ip = ip;
		//this.regDate = regDate;
		this.contentType = contentType;
		this.exposure = exposure;
		this.tags = tags;
		this.postingType = postingType;
		this.reblogOption = reblogOption;
	}
	
	// 포스팅용 (글 수정)
	public Posting(String title, String writer, PostingContent contents, int contentType, int exposure, String tags, int reblogOption) {
		this.title = title;
		this.writer = writer;
		this.contents = contents;
		this.contentType = contentType;
		this.exposure = exposure;
		this.tags = tags;
		this.reblogOption = reblogOption;
		}
	
	// 댓글용 (댓글 작성)
	public Posting(String writer, PostingContent contents, int contentType, int postingType) {
		this.writer = writer;
		this.contents = contents;
		this.contentType = contentType;
		this.regDate = new java.util.Date();
		this.postingType = postingType;
		this.exposure = PUBLIC_ALLOW_REPLY_AND_NO_REBLOG;
	}

	// Methods
	@Override
	public String toString() {
		return "Posting [num=" + num + ", title=" + title + ", writer=" + writer + ", contents=" + contents
				+ ", contentType=" + contentType + ", readCount=" + readCount + ", regDate=" + regDate + ", likes="
				+ likes + ", exposure=" + exposure + ", tags=" + tags + ", ref=" + ref + ", replyStep=" + replyStep
				+ ", replyDepth=" + replyDepth + ", replyCount=" + replyCount + ", postingType=" + postingType
				+ ", reblogCount=" + reblogCount + ", reblogOption=" + reblogOption + "]";
	}

	// Getters
	public int getNum() {
		return num;
	}

	public String getTitle() {
		return title;
	}

	public String getWriter() {
		return writer;
	}

	public PostingContent getContents() {
		return contents;
	}

	public int getContentType() {
		return contentType;
	}

	public int getReadCount() {
		return readCount;
	}

	public java.util.Date getRegDate() {
		return regDate;
	}

	public int getLikes() {
		return likes;
	}

	public int getExposure() {
		return exposure;
	}

	public String getTags() {
		return tags;
	}

	public int getRef() {
		return ref;
	}

	public int getReplyStep() {
		return replyStep;
	}

	public int getReplyDepth() {
		return replyDepth;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public int getPostingType() {
		return postingType;
	}

	public int getReblogCount() {
		return reblogCount;
	}

	public int getReblogOption() {
		return reblogOption;
	}

	// Setters
	public void setNum(int num) {
		this.num = num;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public void setContents(PostingContent contents) {
		this.contents = contents;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public void setRegDate(java.util.Date regDate) {
		this.regDate = regDate;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public void setExposure(int exposure) {
		this.exposure = exposure;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public void setReplyStep(int replyStep) {
		this.replyStep = replyStep;
	}

	public void setReplyDepth(int replyDepth) {
		this.replyDepth = replyDepth;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public void setPostingType(int postingType) {
		this.postingType = postingType;
	}

	public void setReblogCount(int reblogCount) {
		this.reblogCount = reblogCount;
	}

	public void setReblogOption(int reblogOption) {
		this.reblogOption = reblogOption;
	}
	
	
}