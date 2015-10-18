package microblog.kitsch.business.service;

import java.util.List;
import java.util.Map;

import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;

public interface BlogDao {
	public abstract void insertBlog(Blog blog);
	public abstract void updateBlog(Blog blog);
	public abstract void deleteBlog(Blog blog);
	public abstract Blog selectBlogByName(String blogName);
	public abstract Blog selectBlogById(String blogId);
	public abstract Blog selectBlogByEmail(String email);
	public abstract Blog[] selectFollowedBlogs(Member member);
	public abstract void addFollowing(Member member, String blogName);
	public abstract void cancelFollowing(Member member, String blogName);
	public abstract List<Blog> selectBlogList(Map<String, Object> searchInfo);
	public abstract int selectBlogCount(Map<String, Object> searchInfo);
	public abstract boolean blogExists(String blogName);
	public abstract void addVisitCount(String blogName);
	public abstract void updateBlogName(String originBlogName, String newBlogName);
	public abstract List<Blog> selectMemberBlogs(Member member);
}
