package microblog.kitsch.test;

import org.junit.Before;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Test;

import microblog.kitsch.business.dataaccess.BlogDaoImpl;
import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.service.BlogDao;

public class TestBlogDaoImpl {
	private BlogDao blogDao;
	private Member testMember1 = new Member("admin.kitsch.com", "admin", "admin", new java.util.Date(), Member.ADMINISTRATOR);
	private Member testMember2 = new Member("dd@naver.com" ,"aa", "1234", new java.util.Date(), Member.NORMAL_USER);
	private Blog testBlog1 = new Blog("test_blog1", "admin");
	private Blog testBlog2 = new Blog("test_blog2", "admin");
	private Blog testBlog3 = new Blog("test_blog3", "aa");
	
	@Before
	public void init() {
		blogDao = new BlogDaoImpl();
		
	}
	
	@Test
	public void testInsertAndSelectAndDeleteBlog() {
		blogDao.insertBlog(testBlog1);
		blogDao.insertBlog(testBlog2);
		
		Blog selectedBlog1 = blogDao.selectBlog("test_blog1");
		Blog selectedBlog2 = blogDao.selectBlog("test_blog2");
		
		assertEquals(selectedBlog1.getBlogName(), testBlog1.getBlogName());
		assertEquals(selectedBlog1.getMemberName(), testBlog1.getMemberName());
		assertEquals(selectedBlog1.getFollowerCount(), 0);
		assertEquals(selectedBlog1.getVisitCount(), 0);
		assertEquals(selectedBlog1.getBackgroundColor(), testBlog1.getBackgroundColor());
		assertEquals(selectedBlog1.getHeaderImage(), testBlog1.getHeaderImage());
		assertEquals(selectedBlog1.getProfileImage(), testBlog1.getProfileImage());
		assertEquals(selectedBlog1.getBlogLayout(), testBlog1.getBlogLayout());
		//assertEquals(selectedBlog1.getTableName(), "blog_table_12");

		assertEquals(selectedBlog2.getBlogName(), testBlog2.getBlogName());
		assertEquals(selectedBlog2.getMemberName(), testBlog2.getMemberName());
		assertEquals(selectedBlog2.getFollowerCount(), 0);
		assertEquals(selectedBlog2.getVisitCount(), 0);
		assertEquals(selectedBlog2.getBackgroundColor(), testBlog2.getBackgroundColor());
		assertEquals(selectedBlog2.getHeaderImage(), testBlog2.getHeaderImage());
		assertEquals(selectedBlog2.getProfileImage(), testBlog2.getProfileImage());
		assertEquals(selectedBlog2.getBlogLayout(), testBlog2.getBlogLayout());
		//assertEquals(selectedBlog2.getTableName(), "blog_table_13");
		
		blogDao.deleteBlog(selectedBlog1);
		blogDao.deleteBlog(selectedBlog2);
		selectedBlog1 = blogDao.selectBlog("test_blog1");
		selectedBlog2 = blogDao.selectBlog("test_blog2");
		assertNull(selectedBlog1);
		assertNull(selectedBlog2);
	}
	
	@Test
	public void testUpdateBlog() {
		blogDao.insertBlog(testBlog1);
		Blog selectedBlog = blogDao.selectBlog("test_blog1");
		selectedBlog.setBackgroundColor(0x888888);
		selectedBlog.setBlogLayout(Blog.GRID_LAYOUT);
		selectedBlog.setHeaderImage("custom_header.jpg");
		selectedBlog.setProfileImage("custom_profile.jpg");
		
		blogDao.updateBlog(selectedBlog);
		Blog changedBlog = blogDao.selectBlog(selectedBlog.getBlogName());
		
		assertEquals(changedBlog.getBlogName(), selectedBlog.getBlogName());
		assertEquals(changedBlog.getMemberName(), selectedBlog.getMemberName());
		assertEquals(changedBlog.getFollowerCount(), selectedBlog.getFollowerCount());
		assertEquals(changedBlog.getVisitCount(), selectedBlog.getVisitCount());
		assertEquals(changedBlog.getBackgroundColor(), selectedBlog.getBackgroundColor());
		assertEquals(changedBlog.getHeaderImage(), selectedBlog.getHeaderImage());
		assertEquals(changedBlog.getProfileImage(), selectedBlog.getProfileImage());
		assertEquals(changedBlog.getBlogLayout(), selectedBlog.getBlogLayout());
		
		blogDao.deleteBlog(changedBlog);
	}
	
	@Test
	public void selectBlogs() {
		blogDao.insertBlog(testBlog1);
		blogDao.insertBlog(testBlog2);
		blogDao.insertBlog(testBlog3);
		
		blogDao.addFollowing(testMember2, "test_blog1");
		blogDao.addFollowing(testMember2, "test_blog2");
		
		Blog selectedBlog = null;
		Blog[] followed = null;
		followed = blogDao.selectFollowedBlogs(testMember2);
		assertEquals(followed.length, 2);
		
		for (Blog temp : followed) {
			selectedBlog = blogDao.selectBlog(temp.getBlogName());
			assertEquals(temp.getBlogName(), selectedBlog.getBlogName());
			assertEquals(temp.getMemberName(), selectedBlog.getMemberName());
			assertEquals(temp.getFollowerCount(), selectedBlog.getFollowerCount());
			assertEquals(temp.getVisitCount(), selectedBlog.getVisitCount());
			assertEquals(temp.getBackgroundColor(), selectedBlog.getBackgroundColor());
			assertEquals(temp.getHeaderImage(), selectedBlog.getHeaderImage());
			assertEquals(temp.getProfileImage(), selectedBlog.getProfileImage());
		}
		blogDao.cancelFollowing(testMember2, "test_blog1");
		blogDao.cancelFollowing(testMember2, "test_blog2");
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "blog");
		searchInfo.put("searchType", "blogName");
		searchInfo.put("searchText", "test");
		searchInfo.put("startRow", 1);
		searchInfo.put("endRow", 10);
		
		int count = blogDao.selectBlogCount(searchInfo);
		assertEquals(3, count);
		
		List<Blog> bList = blogDao.selectBlogList(searchInfo);
		for (Blog temp : bList) {
			selectedBlog = blogDao.selectBlog(temp.getBlogName());
			assertEquals(temp.getBlogName(), selectedBlog.getBlogName());
			assertEquals(temp.getMemberName(), selectedBlog.getMemberName());
			assertEquals(temp.getFollowerCount(), selectedBlog.getFollowerCount());
			assertEquals(temp.getVisitCount(), selectedBlog.getVisitCount());
			assertEquals(temp.getBackgroundColor(), selectedBlog.getBackgroundColor());
			assertEquals(temp.getHeaderImage(), selectedBlog.getHeaderImage());
			assertEquals(temp.getProfileImage(), selectedBlog.getProfileImage());
			blogDao.deleteBlog(temp);
		}
		
	}
	
	@Test
	public void testFollowing() {
		Blog[] tempBlogs = null;
		ArrayList<Blog> garbageBlog = new ArrayList<Blog>();
		blogDao.insertBlog(testBlog1);
		blogDao.insertBlog(testBlog2);
		blogDao.insertBlog(testBlog3);
		
		blogDao.addFollowing(testMember2, "test_blog1");
		blogDao.addFollowing(testMember2, "test_blog2");
		tempBlogs = blogDao.selectFollowedBlogs(testMember2);
		for (Blog tempBlog : tempBlogs) {
			assertEquals(tempBlog.getFollowerCount(), 1);
			garbageBlog.add(tempBlog);
		}
		
		blogDao.addFollowing(testMember1, "test_blog3");
		tempBlogs = blogDao.selectFollowedBlogs(testMember1);
		for (Blog tempBlog : tempBlogs) {
			assertEquals(tempBlog.getFollowerCount(), 1);
			garbageBlog.add(tempBlog);
		}
		
		blogDao.cancelFollowing(testMember1, "test_blog3");
		tempBlogs = blogDao.selectFollowedBlogs(testMember1);
		assertEquals(0, tempBlogs.length);
		
		blogDao.cancelFollowing(testMember2, "test_blog1");
		tempBlogs = blogDao.selectFollowedBlogs(testMember2);
		assertEquals(1, tempBlogs.length);
		blogDao.cancelFollowing(testMember2, "test_blog2");
		tempBlogs = blogDao.selectFollowedBlogs(testMember2);
		assertEquals(0, tempBlogs.length);
		for (Blog garbage : garbageBlog) {
			blogDao.deleteBlog(garbage);
		}
	}
	
	@Test
	public void testRenameBlog() {
		blogDao.insertBlog(testBlog1);
		
		String newBlogName = "test_admin`s_blog";
		
		if (!blogDao.blogExists(newBlogName)) {
			blogDao.updateBlogName(testBlog1.getBlogName(), newBlogName);
			assertTrue(blogDao.blogExists(newBlogName));
		}
		
		Blog selectedBlog = blogDao.selectBlog(newBlogName);
		assertEquals(selectedBlog.getBlogName(), newBlogName);
		assertEquals(selectedBlog.getMemberName(), testBlog1.getMemberName());
		assertEquals(selectedBlog.getFollowerCount(), 0);
		assertEquals(selectedBlog.getVisitCount(), 0);
		assertEquals(selectedBlog.getBackgroundColor(), testBlog1.getBackgroundColor());
		assertEquals(selectedBlog.getHeaderImage(), testBlog1.getHeaderImage());
		assertEquals(selectedBlog.getProfileImage(), testBlog1.getProfileImage());
		assertEquals(selectedBlog.getBlogLayout(), testBlog1.getBlogLayout());
		
		blogDao.deleteBlog(selectedBlog);
	}
	
	@Test
	public void TestVisit() {
		blogDao.insertBlog(testBlog1);
		
		Blog selectedBlog = null;
		for (int i = 0; i < 5; i++) {
			blogDao.addVisitCount(testBlog1.getBlogName());
			selectedBlog = blogDao.selectBlog(testBlog1.getBlogName());
			assertEquals(selectedBlog.getVisitCount(), i+1);
		}
		
		blogDao.deleteBlog(selectedBlog);
	}
	
	@After
	public void end() {
		
	}
}
