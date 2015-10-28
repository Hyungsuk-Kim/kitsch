package microblog.kitsch.business.service;

import java.util.Map;

import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.helper.DataNotFoundException;

public interface PostingService {
	
	/**
	 * 저장소에서 blogName에 해당하는 블로그의 postingNum와 일치하는 포스팅을 읽어온 뒤 조회수를 증가시킨다.
	 * @param blogName 읽어올 포스팅이 존재하는 blogName
	 * @param postingNum 읽어올 포스팅의 번호
	 * @return blogName의 블로그에 postingNum와 일치하는 Posting 객체
	 * @throws DataNotFoundException blogName 혹은 postingNum과 일치하는 Posting이 없을 경우 발생하는 Exception
	 */
	public abstract Posting readPosting(String blogName, int postingNum) throws DataNotFoundException;
	
	/**
	 * 저장소에서 blogName에 해당하는 블로그의 postingNum와 일치하는 포스팅을 찾는다.
	 * @param blogName 찾을 포스팅이 존재하는 blogName
	 * @param postingNum 찾는 포스팅의 번호
	 * @return blogName의 블로그에 postingNum와 일치하는 Posting 객체
	 * @throws DataNotFoundException blogName 혹은 postingNum과 일치하는 Posting이 없을 경우 발생하는 Exception
	 */
	public abstract Posting findPosting(String blogName, int postingNum) throws DataNotFoundException;
	
	/**
	 * 저장소에서 blogName에 해당하는 블로그의 postingNum와 일치하는 포스팅을 삭제한다.
	 * @param blogName 삭제할 포스팅이 존재하는 blogName
	 * @param postingNum 삭제할 포스팅의 번호
	 * @throws DataNotFoundException blogName 혹은 postingNum과 일치하는 Posting이 없을 경우 발생하는 Exception
	 */
	public abstract void removePosting(String blogName, int postingNum) throws DataNotFoundException;
	
	/**
	 * 인자로 받은 blogName에 해당하는 블로그에 포스팅을 등록한다.
	 * @param blogName 포스팅이 등록될 블로그 명
	 * @param posting 등록할 포스팅 객체
	 * @throws DataNotFoundException 
	 */
	public abstract void writePosting(String blogName, Posting posting) throws DataNotFoundException;
	
	/**
	 * 저장소에서 blogName에 해당하는 블로그의 postingNum와 일치하는 포스팅을 갱신한다.
	 * @param blogName 갱신할 포스팅이 등록된 블로그 명
	 * @param posting 갱신할 포스팅 객체
	 * @throws DataNotFoundException blogName 혹은 postingNum과 일치하는 Posting이 없을 경우 발생하는 Exception
	 */
	public abstract void updatePosting(String blogName, Posting posting) throws DataNotFoundException;
	
	/**
	 * blogName에 해당하는 블로그의 postingNum와 일치하는 포스팅에 댓글을 작성한다.
	 * @param blogName 댓글을 달 포스팅이 존재하는 블로그 명
	 * @param posting 등록할 댓글 객체
	 * @param postingNum 댓글을 등록할 포스팅 객체의 번호
	 * @throws DataNotFoundException blogName 혹은 postingNum과 일치하는 Posting이 없을 경우 발생하는 Exception
	 */
	public abstract void replyPosting(String blogName, Posting posting, int postingNum) throws DataNotFoundException;
	
	/**
	 * 인자로 받은 Map 객체에 저장된 properties와 일치하는 포스팅의 갯수를 저장소에서 얻어온다.
	 * @param searchInfo 검색어 키워드, 검색 대상(블로그, 내용, 제목, 작성자, 태그, 전체), 검색할 블로그(optional)
	 * @return 얻어올 포스팅의 갯수
	 * @throws DataNotFoundException 
	 */
	public abstract int getPostingCount(Map<String, Object> searchInfo) throws DataNotFoundException;
	
	/**
	 * 인자로 받은 Map 객체에 저장된 properties와 일치하는 포스팅들을 저장소에서 얻어온다.
	 * @param searchInfo 검색어 키워드, 검색 대상(블로그, 내용, 제목, 작성자, 태그, 전체), 검색할 블로그(optional)
	 * @return 얻어올 포스팅들이 담긴 배열 객체
	 * @throws DataNotFoundException 
	 */
	public abstract Posting[] getPostingList(Map<String, Object> searchInfo) throws DataNotFoundException;
	
	/**
	 * 해당 포스팅에 좋아요를 추가한다.
	 * @param member 좋아요를 추가할 Member 객체
	 * @param blogName 해당 포스팅이 존재하는 블로그 명
	 * @param postingNum 해당 포스팅의 번호
	 * @throws DataNotFoundException member, blogName 혹은 postingNum과 일치하는 Posting이 없을 경우 발생하는 Exception
	 */
	public abstract void addLikes(Member member, String blogName, int postingNum) throws DataNotFoundException;
	
	/**
	 * 해당 포스팅에 추가했던 좋아요를 취소한다.
	 * @param member 좋아요를 취소할 Member 객체
	 * @param blogName 해당 포스팅이 존재하는 블로그 명
	 * @param postingNum 해당 포스팅의 번호
	 * @throws DataNotFoundException member, blogName 혹은 postingNum과 일치하는 Posting이 없을 경우 발생하는 Exception
	 */
	public abstract void cancelLikes(Member member, String blogName, int postingNum) throws DataNotFoundException;
	
	/**
	 * 인자로 받은 fromBlogName에 해당하는 블로그의 포스팅을 toBlogName에 해당하는 블로그에 다시 포스팅한다.
	 * @param fromBlogName 원본 포스팅이 존재하는 블로그
	 * @param postingNum 원본 포스팅의 번호
	 * @param toBlogName 다시 포스팅할 (목적지) 블로그
	 * @throws DataNotFoundException fromBlogName, toBlogName 혹은 postingNum과 일치하는 Posting이 없을 경우 발생하는 Exception
	 */
	public abstract void reblog(Member member, String originBlogName, int postingNum, String targetBlogName) throws DataNotFoundException;
	
//	/**
//	 * 해당 포스팅 내용의 타입을 반환한다.
//	 * @param blogName 해당 포스팅이 존재하는 블로그 명
//	 * @param postingNum 해당 포스팅의 번호
//	 * @return 해당 포스팅 내용의 타입 (PostingContent 클래스에 상수로 선언된 정수 값을 리턴)
//	 * @throws DataNotFoundException blogName 혹은 postingNum과 일치하는 Posting이 없을 경우 발생하는 Exception
//	 */
//	public abstract int getContentType(String blogName, int postingNum) throws DataNotFoundException;
	
	public abstract Posting[] getLikedPostings(Member member) throws DataNotFoundException;
	public abstract Posting[] getReplies(String blogName, int postingNum) throws DataNotFoundException;
	public abstract boolean isLiked(Member member, String blogName, int postingNum) throws DataNotFoundException;

	public abstract Posting[] getRelativePostings(Member member, int startRow, int endRow);
	
}
