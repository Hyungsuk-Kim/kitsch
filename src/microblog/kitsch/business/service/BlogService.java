package microblog.kitsch.business.service;

import java.util.Map;

import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.IllegalDataException;

public interface BlogService {
	
	/**
	 * 인자로 받은 blogName으로 member 정보에 해당하는 회원의 블로그를 생성한다.
	 * @param member 블로그를 생성하고자하는 회원의 정보를 담은 Member 객체
	 * @param blogName 생성하고자하는 블로그의 이름
	 * @throws DataDuplicatedException blogName과 일치하는 블로그가 이미 존재할 경우 발생하는 Exception
	 * @throws DataNotFoundException 인자로 받은 member와 일치하는 회원 정보가 없을 경우 발생하는 Exception
	 */
	public abstract void createBlog(Member member, String blogName) throws DataDuplicatedException, DataNotFoundException;
	
	/**
	 * 인자로 받은 member 정보에 해당하는 회원의 Blog 정보에 해당하는 블로그를 갱신한다.
	 * @param member 갱신할 블로그의 소유 회원 정보를 담은 Member 객체
	 * @param blog 갱신할 블로그의 정보를 담은 Blog 객체
	 * @throws DataNotFoundException member 혹은 blog 정보에 해당하는 객체가 존재하지 않을 경우 발생하는 Exception
	 * @throws IllegalDataException 
	 */
	public abstract void updateBlog(Member member, Blog blog) throws DataNotFoundException, IllegalDataException;
	
	/**
	 * 인자로 받은 member 정보에 해당하는 회원의 Blog 정보에 해당하는 블로그를 삭제한다.
	 * @param member 삭제할 블로그의 소유 회원 정보를 담은 Member 객체
	 * @param blog 갱신할 블로그의 정보를 담은 Blog 객체
	 * @throws DataNotFoundException member 혹은 blog 정보에 해당하는 객체가 존재하지 않을 경우 발생하는 Exception
	 * @throws IllegalDataException 
	 */
	public abstract void removeBlog(Member member, Blog blog) throws DataNotFoundException, IllegalDataException;
	
	/**
	 * 인자로 받은 문자열과 일치하는 블로그 객체를 리턴한다.
	 * @param blogName 조회하고자하는 블로그 명
	 * @return 인자로 받은 문자열과 일치하는 블로그 객체
	 * @throws DataNotFoundException blogName 정보에 해당하는 Blog 객체가 존재하지 않을 경우 발생하는 Exception
	 */
	public abstract Blog findBlogByName(String blogName) throws DataNotFoundException;
	
	/**
	 * 인자로 받은 회원이 해당 블로그를 팔로우한다.
	 * @param member 해당 블로그를 팔로우할 회원 정보를 담은 Member 객체
	 * @param blogName 팔로잉할 블로그 명
	 * @throws DataNotFoundException member 혹은 blogName 정보에 해당하는 객체가 존재하지 않을 경우 발생하는 Exception
	 */
	public abstract void following(Member member, String blogId) throws DataNotFoundException;
	
	/**
	 * 인자로 받은 회원이 해당 블로그를 언팔로우 한다.
	 * @param member 해당 블로그를 언팔로우할 회원 정보를 담은 Member 객체
	 * @param blogName 언팔로우할 블로그 명
	 * @throws DataNotFoundException member 혹은 blogName 정보에 해당하는 객체가 존재하지 않을 경우 발생하는 Exception
	 */
	public abstract void unfollow(Member member, String blogId) throws DataNotFoundException;
	
	/**
	 * 회원이 팔로우한 블로그의 목록을 조회한다.
	 * @param memberName 팔로우 목록을 조회하고자 하는 회원 객체
	 * @return 해당 회원이 팔로우한 블로그 배열 객체를 리턴
	 * @exception DataNotFoundException 인자로 받은 회원 객체에 대한 정보가 존재하지 않을 경우 발생하는 exception
	 */
	public abstract Blog[] getFollowingList(Member member) throws DataNotFoundException;
	
	/**
	 * 조건에 해당하는 블로그 배열 객체를 조회한다.
	 * @param searchInfo 조회하고자하는 조건을 담은 Map 객체
	 * @return 조건에 해당하는 블로그 배열 객체
	 */
	public abstract Blog[] getBlogList(Map<String, Object> searchInfo);
	
	/**
	 * 조건에 해당하는 블로그의 수를 조회한다.
	 * @param searchInfo 조회하고자하는 조건을 담은 Map 객체
	 * @return 조건에 해당하는 블로그의 수
	 */
	public abstract int getBlogCount(Map<String, Object> searchInfo);
	
	public abstract void changeBlogName(Member member, String originBlogName, String newBlogName) throws DataNotFoundException, DataDuplicatedException, IllegalDataException;
	
	public abstract void visitBlog(Member member, String blogName) throws DataNotFoundException;
	
	public abstract boolean checkValidBlogName(String blogName);
	
	public abstract Blog[] getMemberBlogs(Member member) throws DataNotFoundException;

	public abstract void removeBlog(Member member) throws DataNotFoundException;

	public abstract Blog findBlogById(String blogId) throws DataNotFoundException;
}
