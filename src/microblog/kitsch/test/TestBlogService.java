package microblog.kitsch.test;

import org.junit.Before;
import org.junit.Test;

import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.service.BlogService;
import microblog.kitsch.business.service.BlogServiceImpl;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.IllegalDataException;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;

public class TestBlogService {
	private BlogService blogService;
	private Member testMember1 = new Member("admin.kitsch.com", "admin", "admin", new java.util.Date(), Member.ADMINISTRATOR);
	private Member testMember2 = new Member("dd@naver.com" ,"aa", "1234", new java.util.Date(), Member.NORMAL_USER);
	private Member testMember3 = new Member("dh@naver.com" ,"dhdh", "12345", new java.util.Date(), Member.NORMAL_USER);
	private Member testMember4 = new Member("gg@naver.com" ,"가애", "123456", new java.util.Date(), Member.NORMAL_USER);
	private Member testMember5 = new Member("test@naver.com" ,"invalid_member", "1234", new java.util.Date(), Member.NORMAL_USER);
	
	
	@Before
	public void init() throws DataDuplicatedException, DataNotFoundException {
		blogService = new BlogServiceImpl();
	}
	
	@Test
	public void testCreateBlog() throws DataDuplicatedException, DataNotFoundException {
		blogService.createBlog(testMember2, "test_blog1");
		Blog temp =  blogService.findBlog("test_blog1");
		blogService.removeBlog(testMember2, temp);
	}

	@Test(expected=DataNotFoundException.class)
	public void createBlogInvalidMember() throws DataDuplicatedException, DataNotFoundException {
		blogService.createBlog(testMember5, "test_blog5");
	}
	
	@Test(expected=DataNotFoundException.class)
	public void testFind() throws DataNotFoundException, DataDuplicatedException {
		Blog selectedBlog = null;
		
		blogService.createBlog(testMember2, "test_blog1");
		selectedBlog = blogService.findBlog("test_blog1");
		blogService.removeBlog(testMember1, selectedBlog);
		selectedBlog = blogService.findBlog("test_blog4");
	}
	
	@Test
	public void testUpdateBlog() throws DataDuplicatedException, DataNotFoundException {
		blogService.createBlog(testMember1, "test_blog");
		
		Blog selectedBlog = blogService.findBlog("test_blog");
		selectedBlog.setBackgroundColor(0x888888);
		selectedBlog.setBlogLayout(Blog.LISTED_SUMMARY_LAYOUT);
		
		blogService.updateBlog(testMember1, selectedBlog);
		Blog changedBlog = blogService.findBlog("test_blog");
		assertEquals(selectedBlog.getBlogName(), changedBlog.getBlogName());
		assertEquals(selectedBlog.getMemberName(), changedBlog.getMemberName());
		assertEquals(selectedBlog.getFollowerCount(), changedBlog.getFollowerCount());
		assertEquals(selectedBlog.getVisitCount(), changedBlog.getVisitCount());
		assertEquals(selectedBlog.getBackgroundColor(), changedBlog.getBackgroundColor());
		assertEquals(selectedBlog.getHeaderImage(), changedBlog.getHeaderImage());
		assertEquals(selectedBlog.getProfileImage(), changedBlog.getProfileImage());
		assertEquals(selectedBlog.getBlogLayout(), changedBlog.getBlogLayout());
		assertEquals(selectedBlog.getTableName(), changedBlog.getTableName());
		
		blogService.removeBlog(testMember1, changedBlog);
	}
	
	@Test
	public void testFollowing() throws DataDuplicatedException, DataNotFoundException {
		blogService.createBlog(testMember1, "test_blog1");
		blogService.createBlog(testMember1, "test_blog2");
		
		blogService.following(testMember2, "test_blog1");
		blogService.following(testMember2, "test_blog2");
		
		Blog selectedBlog = blogService.findBlog("test_blog1");
		assertEquals(1, selectedBlog.getFollowerCount());
		
		Blog[] followingList = blogService.getFollowingList(testMember2);
		assertEquals(2, followingList.length);
		Blog compare = null;
		for (Blog temp : followingList) {
			compare = blogService.findBlog(temp.getBlogName());
			assertEquals(temp.getBlogName(), compare.getBlogName());
			assertEquals(temp.getMemberName(), compare.getMemberName());
			assertEquals(temp.getFollowerCount(), compare.getFollowerCount());
			assertEquals(temp.getVisitCount(), compare.getVisitCount());
			assertEquals(temp.getBackgroundColor(), compare.getBackgroundColor());
			assertEquals(temp.getHeaderImage(), compare.getHeaderImage());
			assertEquals(temp.getProfileImage(), compare.getProfileImage());
			assertEquals(temp.getBlogLayout(), compare.getBlogLayout());
			assertEquals(temp.getTableName(), compare.getTableName());
			
			blogService.unfollow(testMember2, temp.getBlogName());
		}
		
		followingList = blogService.getFollowingList(testMember2);
		assertEquals(0, followingList.length);
		
		blogService.removeBlog(testMember1, blogService.findBlog("test_blog1"));
		blogService.removeBlog(testMember1, blogService.findBlog("test_blog2"));
	}
	
	@Test
	public void testSearch() throws DataDuplicatedException, DataNotFoundException {
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "blog");
		searchInfo.put("searchType", "memberName");
		searchInfo.put("searchText", "adm");
		searchInfo.put("startRow", 1);
		searchInfo.put("endRow", 10);
		
		blogService.createBlog(testMember1, "test_blog1");
		blogService.createBlog(testMember2, "test_blog2");
		blogService.createBlog(testMember1, "test_blog3");
		blogService.createBlog(testMember1, "test_blog4");
		blogService.createBlog(testMember2, "test_blog5");
		blogService.createBlog(testMember1, "test_blog6");
		
		int count = blogService.getBlogCount(searchInfo);
		assertEquals(4, count);
		
		Blog[] searchedBlogs = blogService.getBlogList(searchInfo);
		Blog compare = null;
		for (Blog temp : searchedBlogs) {
			compare = blogService.findBlog(temp.getBlogName());
			assertEquals(temp.getBlogName(), compare.getBlogName());
			assertEquals(temp.getMemberName(), compare.getMemberName());
			assertEquals(temp.getFollowerCount(), compare.getFollowerCount());
			assertEquals(temp.getVisitCount(), compare.getVisitCount());
			assertEquals(temp.getBackgroundColor(), compare.getBackgroundColor());
			assertEquals(temp.getHeaderImage(), compare.getHeaderImage());
			assertEquals(temp.getProfileImage(), compare.getProfileImage());
			assertEquals(temp.getBlogLayout(), compare.getBlogLayout());
			assertEquals(temp.getTableName(), compare.getTableName());
		}
		
		blogService.removeBlog(testMember1, blogService.findBlog("test_blog1"));
		blogService.removeBlog(testMember2, blogService.findBlog("test_blog2"));
		blogService.removeBlog(testMember1, blogService.findBlog("test_blog3"));
		blogService.removeBlog(testMember1, blogService.findBlog("test_blog4"));
		blogService.removeBlog(testMember2, blogService.findBlog("test_blog5"));
		blogService.removeBlog(testMember1, blogService.findBlog("test_blog6"));
	}
	
	@Test
	public void testVisitBlog() throws DataDuplicatedException, DataNotFoundException {
		Blog blog = null;
		blogService.createBlog(testMember1, "test_blog");
		blog = blogService.findBlog("test_blog");
		assertEquals(0, blog.getVisitCount());
		
		blogService.visitBlog(testMember2, "test_blog");
		blog = blogService.findBlog("test_blog");
		assertEquals(1, blog.getVisitCount());
		blogService.visitBlog(testMember3, "test_blog");
		blog = blogService.findBlog("test_blog");
		assertEquals(2, blog.getVisitCount());
		blogService.visitBlog(testMember4, "test_blog");
		blog = blogService.findBlog("test_blog");
		assertEquals(3, blog.getVisitCount());
		
		blogService.removeBlog(testMember1, blogService.findBlog("test_blog"));
	}
	
	@Test(expected=DataDuplicatedException.class)
	public void testRenameBlogDuplicateBlogName() throws DataNotFoundException, DataDuplicatedException, IllegalDataException {
		blogService.createBlog(testMember1, "test_blog");
		blogService.createBlog(testMember2, "test_blog1");
		try {
			blogService.changeBlogName(testMember1, "test_blog", "test_blog1");
		} finally {
			blogService.removeBlog(testMember1, blogService.findBlog("test_blog"));
			blogService.removeBlog(testMember2, blogService.findBlog("test_blog1"));
		}
	}
	
	@Test(expected=IllegalDataException.class)
	public void testRenameBlogInvalidRole() throws DataNotFoundException, DataDuplicatedException, IllegalDataException {
		blogService.createBlog(testMember1, "test_blog");
		try {
			blogService.changeBlogName(testMember2, "test_blog", "test_blog1");
		} finally {
			blogService.removeBlog(testMember1, blogService.findBlog("test_blog"));
		}
	}

	@Test
	public void testRenameBlog() throws DataNotFoundException, DataDuplicatedException, IllegalDataException {
		blogService.createBlog(testMember1, "test_blog");
		blogService.changeBlogName(testMember1, "test_blog", "test_blog_admin");
		blogService.removeBlog(testMember1, blogService.findBlog("test_blog_admin"));
	}
	
	@After
	public void end() throws DataNotFoundException {
	
	}
}
