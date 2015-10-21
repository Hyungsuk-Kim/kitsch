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
	private Member testMember1 = new Member("test_member1@kitsch.com", "test_member1", "test1234");
	private Member testMember2 = new Member("test_member2@kitsch.com", "test_member2", "test1234");
	private Blog testBlog1 = new Blog(testMember1.getEmail(), "test_blog1");
	private Blog testBlog2 = new Blog(testMember1.getEmail(), "test_blog2");
	private Blog testBlog3 = new Blog(testMember2.getEmail(), "test_blog3");
	
	@Before
	public void init() {
		blogDao = new BlogDaoImpl();
	}
	
	@Test
	public void testInsertAndSelectAndDeleteBlog() {
		System.out.println("********** testInsertAndSelectAndDeleteBlog() **********");
		blogDao.insertBlog(testBlog1);
		blogDao.insertBlog(testBlog2);
		
		Blog selectedBlog1 = blogDao.selectBlogByName("test_blog1");
		Blog selectedBlog2 = blogDao.selectBlogByName("test_blog2");
		
		assertEquals(selectedBlog1.getBlogName(), testBlog1.getBlogName());
		assertEquals(selectedBlog1.getEmail(), testBlog1.getEmail());
		assertEquals(selectedBlog1.getFollowCount(), 0);
		assertEquals(selectedBlog1.getVisitCount(), 0);
		assertEquals(selectedBlog1.getBackgroundColor(), testBlog1.getBackgroundColor());
		assertEquals(selectedBlog1.getHeaderImage(), testBlog1.getHeaderImage());
		assertEquals(selectedBlog1.getBlogLayout(), testBlog1.getBlogLayout());
		//assertEquals(selectedBlog1.getTableName(), "blog_table_12");

		assertEquals(selectedBlog2.getBlogName(), testBlog2.getBlogName());
		assertEquals(selectedBlog2.getEmail(), testBlog2.getEmail());
		assertEquals(selectedBlog2.getFollowCount(), 0);
		assertEquals(selectedBlog2.getVisitCount(), 0);
		assertEquals(selectedBlog2.getBackgroundColor(), testBlog2.getBackgroundColor());
		assertEquals(selectedBlog2.getHeaderImage(), testBlog2.getHeaderImage());
		assertEquals(selectedBlog2.getBlogLayout(), testBlog2.getBlogLayout());
		//assertEquals(selectedBlog2.getTableName(), "blog_table_13");
		
		blogDao.deleteBlog(selectedBlog1);
		blogDao.deleteBlog(selectedBlog2);
		selectedBlog1 = blogDao.selectBlogByName("test_blog1");
		selectedBlog2 = blogDao.selectBlogByName("test_blog2");
		assertNull(selectedBlog1);
		assertNull(selectedBlog2);
	}
	
	@Test
	public void testUpdateBlog() {
		System.out.println("********** testUpdateBlog() **********");
		blogDao.insertBlog(testBlog1);
		Blog selectedBlog = blogDao.selectBlogByName("test_blog1");
		selectedBlog.setBackgroundColor(0x888888);
		selectedBlog.setBlogLayout(Blog.GRID_LAYOUT);
		selectedBlog.setHeaderImage("custom_header.jpg");
		
		blogDao.updateBlog(selectedBlog);
		Blog changedBlog = blogDao.selectBlogByName(selectedBlog.getBlogName());
		
		assertEquals(changedBlog.getBlogName(), selectedBlog.getBlogName());
		assertEquals(changedBlog.getEmail(), selectedBlog.getEmail());
		assertEquals(changedBlog.getFollowCount(), selectedBlog.getFollowCount());
		assertEquals(changedBlog.getVisitCount(), selectedBlog.getVisitCount());
		assertEquals(changedBlog.getBackgroundColor(), selectedBlog.getBackgroundColor());
		assertEquals(changedBlog.getHeaderImage(), selectedBlog.getHeaderImage());
		assertEquals(changedBlog.getBlogLayout(), selectedBlog.getBlogLayout());
		
		blogDao.deleteBlog(changedBlog);
	}
	@Test
	public void selectBlogs() {
		System.out.println("********** selectBlogs() **********");
		blogDao.insertBlog(testBlog1);
		blogDao.insertBlog(testBlog2);
		blogDao.insertBlog(testBlog3);
		
		Blog tempBlog1 = blogDao.selectBlogByName(testBlog1.getBlogName());
		Blog tempBlog2 = blogDao.selectBlogByName(testBlog2.getBlogName());
		
		blogDao.addFollowing(testMember2, tempBlog1.getBlogId());
		blogDao.addFollowing(testMember2, tempBlog2.getBlogId());
		
		Blog selectedBlog = null;
		Blog[] followed = null;
		followed = blogDao.selectFollowedBlogs(testMember2);
		assertEquals(followed.length, 2);
		
		for (Blog temp : followed) {
			selectedBlog = blogDao.selectBlogByName(temp.getBlogName());
			assertEquals(temp.getBlogName(), selectedBlog.getBlogName());
			assertEquals(temp.getEmail(), selectedBlog.getEmail());
			assertEquals(temp.getFollowCount(), selectedBlog.getFollowCount());
			assertEquals(temp.getVisitCount(), selectedBlog.getVisitCount());
			assertEquals(temp.getBackgroundColor(), selectedBlog.getBackgroundColor());
			assertEquals(temp.getHeaderImage(), selectedBlog.getHeaderImage());
		}
		blogDao.cancelFollowing(testMember2, tempBlog1.getBlogId());
		blogDao.cancelFollowing(testMember2, tempBlog2.getBlogId());
		
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "blog");
		searchInfo.put("searchType", "blogName");
		searchInfo.put("searchText", "test b");
		searchInfo.put("startRow", 1);
		searchInfo.put("endRow", 10);
		
		int count = blogDao.selectBlogCount(searchInfo);
		assertEquals(3, count);
		
		List<Blog> bList = blogDao.selectBlogList(searchInfo);
		for (Blog temp : bList) {
			selectedBlog = blogDao.selectBlogByName(temp.getBlogName());
			assertEquals(temp.getBlogName(), selectedBlog.getBlogName());
			assertEquals(temp.getEmail(), selectedBlog.getEmail());
			assertEquals(temp.getFollowCount(), selectedBlog.getFollowCount());
			assertEquals(temp.getVisitCount(), selectedBlog.getVisitCount());
			assertEquals(temp.getBackgroundColor(), selectedBlog.getBackgroundColor());
			assertEquals(temp.getHeaderImage(), selectedBlog.getHeaderImage());
			blogDao.deleteBlog(temp);
		}
		
	}
	
	@Test
	public void testFollowing() {
		System.out.println("********** testFollowing() **********");
		Blog[] tempBlogs = null;
		ArrayList<Blog> garbageBlog = new ArrayList<Blog>();
		blogDao.insertBlog(testBlog1);
		blogDao.insertBlog(testBlog2);
		blogDao.insertBlog(testBlog3);
		
		Blog tempBlog1 = blogDao.selectBlogByName(testBlog1.getBlogName());
		Blog tempBlog2 = blogDao.selectBlogByName(testBlog2.getBlogName());
		Blog tempBlog3 = blogDao.selectBlogByName(testBlog3.getBlogName());
		
		blogDao.addFollowing(testMember2, tempBlog1.getBlogId());
		blogDao.addFollowing(testMember2, tempBlog2.getBlogId());
		tempBlogs = blogDao.selectFollowedBlogs(testMember2);
		assertEquals(tempBlogs.length, 2);
		for (Blog tempBlog : tempBlogs) {
			assertEquals(tempBlog.getFollowCount(), 1);
			garbageBlog.add(tempBlog);
		}
		
		blogDao.addFollowing(testMember1, tempBlog3.getBlogId());
		tempBlogs = blogDao.selectFollowedBlogs(testMember1);
		for (Blog tempBlog : tempBlogs) {
			assertEquals(tempBlog.getFollowCount(), 1);
			garbageBlog.add(tempBlog);
		}
		
		blogDao.cancelFollowing(testMember1, tempBlog3.getBlogId());
		tempBlogs = blogDao.selectFollowedBlogs(testMember1);
		assertEquals(0, tempBlogs.length);
		
		blogDao.cancelFollowing(testMember2, tempBlog1.getBlogId());
		tempBlogs = blogDao.selectFollowedBlogs(testMember2);
		assertEquals(1, tempBlogs.length);
		blogDao.cancelFollowing(testMember2, tempBlog2.getBlogId());
		tempBlogs = blogDao.selectFollowedBlogs(testMember2);
		assertEquals(0, tempBlogs.length);
		for (Blog garbage : garbageBlog) {
			blogDao.deleteBlog(garbage);
		}
	}
	
	@Test
	public void testRenameBlog() {
		System.out.println("********** testRenameBlog() **********");
		blogDao.insertBlog(testBlog1);
		
		String newBlogName = "test_admin`s_blog";
		
		if (!blogDao.blogExistsByName(newBlogName)) {
			blogDao.updateBlogName(testBlog1.getBlogName(), newBlogName);
			assertTrue(blogDao.blogExistsByName(newBlogName));
		}
		
		Blog selectedBlog = blogDao.selectBlogByName(newBlogName);
		assertEquals(selectedBlog.getBlogName(), newBlogName);
		assertEquals(selectedBlog.getEmail(), testBlog1.getEmail());
		assertEquals(selectedBlog.getFollowCount(), 0);
		assertEquals(selectedBlog.getVisitCount(), 0);
		assertEquals(selectedBlog.getBackgroundColor(), testBlog1.getBackgroundColor());
		assertEquals(selectedBlog.getHeaderImage(), testBlog1.getHeaderImage());
		assertEquals(selectedBlog.getBlogLayout(), testBlog1.getBlogLayout());
		
		blogDao.deleteBlog(selectedBlog);
	}
	
	@Test
	public void TestVisit() {
		System.out.println("********** TestVisit() **********");
		blogDao.insertBlog(testBlog1);
		
		Blog tempBlog = blogDao.selectBlogByName(testBlog1.getBlogName());
		
		for (int i = 0; i < 5; i++) {
			blogDao.addVisitCount(tempBlog.getBlogId());
			tempBlog = blogDao.selectBlogByName(testBlog1.getBlogName());
			assertEquals(tempBlog.getVisitCount(), i+1);
		}
		
		blogDao.deleteBlog(tempBlog);
	}
	
	@After
	public void end() {
		
	}
}
