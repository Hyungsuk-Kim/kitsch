package microblog.kitsch.test;

import org.junit.Before;
import org.junit.Test;

import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.service.BlogDao;
import microblog.kitsch.business.service.BlogService;
import microblog.kitsch.business.service.BlogServiceImpl;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.IllegalDataException;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;

public class TestBlogServiceImpl {
	private BlogService blogService;
	private Member testMember1 = new Member("admin@kitsch.com", "administrator", "admin1234");
	private Member testMember2 = new Member("kitsch@kitsch.com" ,"kitsch", "kitsch1234");
	private Member testMember3 = new Member("test_member1@kitsch.com", "test_member1", "test1234");
	private Member testMember4 = new Member("test_member2@kitsch.com", "test_member2", "test1234");
	private Member testMember5 = new Member("test_member3@kitsch.com", "test_member3", "test1234");
	private Member InvalidMember = new Member("Not Exists", "Not Exists", "1234");
	
	
	@Before
	public void init() throws DataDuplicatedException, DataNotFoundException {
		blogService = new BlogServiceImpl();
	}
	
	@Test
	public void testCreateBlog() throws DataDuplicatedException, DataNotFoundException, IllegalDataException {
		System.out.println("********** testCreateBlog() **********");
		blogService.createBlog(testMember2, "test_blog1");
		Blog temp =  blogService.findBlogByName("test_blog1");
		blogService.removeBlog(testMember2, temp);
	}
	@Test(expected=DataNotFoundException.class)
	public void createBlogInvalidMember() throws DataDuplicatedException, DataNotFoundException {
		System.out.println("********** createBlogInvalidMember() **********");
		blogService.createBlog(InvalidMember, "test_blog5");
	}
	
	@Test(expected=DataDuplicatedException.class)
	public void testDuplicatedBlog() throws DataDuplicatedException, DataNotFoundException {
		System.out.println("********** testDuplicatedBlog() **********");
		blogService.createBlog(testMember5, "QnA");
	}
	
	@Test(expected=DataNotFoundException.class)
	public void testFind() throws DataNotFoundException, DataDuplicatedException, IllegalDataException {
		System.out.println("********** testFind() **********");
		Blog selectedBlog = null;
		
		blogService.createBlog(testMember2, "test_blog1");
		selectedBlog = blogService.findBlogByName("test_blog1");
		blogService.removeBlog(testMember2, selectedBlog);
		selectedBlog = blogService.findBlogByName("test_blog4");
	}
	
	@Test
	public void testUpdateBlog() throws DataDuplicatedException, DataNotFoundException, IllegalDataException {
		System.out.println("********** testUpdateBlog() **********");
		blogService.createBlog(testMember1, "test_blog");
		
		Blog selectedBlog = blogService.findBlogByName("test_blog");
		selectedBlog.setBackgroundColor(0x888888);
		selectedBlog.setBlogLayout(Blog.LISTED_SUMMARY_LAYOUT);
		
		blogService.updateBlog(testMember1, selectedBlog);
		Blog changedBlog = blogService.findBlogByName("test_blog");
		assertEquals(selectedBlog.getBlogName(), changedBlog.getBlogName());
		assertEquals(selectedBlog.getEmail(), changedBlog.getEmail());
		assertEquals(selectedBlog.getFollowCount(), changedBlog.getFollowCount());
		assertEquals(selectedBlog.getVisitCount(), changedBlog.getVisitCount());
		assertEquals(selectedBlog.getBackgroundColor(), changedBlog.getBackgroundColor());
		assertEquals(selectedBlog.getHeaderImage(), changedBlog.getHeaderImage());
		assertEquals(selectedBlog.getBlogLayout(), changedBlog.getBlogLayout());
		assertEquals(selectedBlog.getBlogId(), changedBlog.getBlogId());
		
		blogService.removeBlog(testMember1, changedBlog);
	}
	
	@Test
	public void testFollowing() throws DataDuplicatedException, DataNotFoundException, IllegalDataException {
		System.out.println("********** testCreateBlog() **********");
		blogService.createBlog(testMember1, "test_blog1");
		blogService.createBlog(testMember1, "test_blog2");
		
		Blog tempBlog1 = blogService.findBlogByName("test_blog1");
		Blog tempBlog2 = blogService.findBlogByName("test_blog2");
		
		blogService.following(testMember2, tempBlog1.getBlogId());
		blogService.following(testMember2, tempBlog2.getBlogId());
		
		Blog selectedBlog = blogService.findBlogByName("test_blog1");
		assertEquals(1, selectedBlog.getFollowCount());
		
		Blog[] followingList = blogService.getFollowingList(testMember2);
		assertEquals(2, followingList.length);
		Blog compare = null;
		for (Blog temp : followingList) {
			compare = blogService.findBlogByName(temp.getBlogName());
			assertEquals(temp.getBlogName(), compare.getBlogName());
			assertEquals(temp.getEmail(), compare.getEmail());
			assertEquals(temp.getFollowCount(), compare.getFollowCount());
			assertEquals(temp.getVisitCount(), compare.getVisitCount());
			assertEquals(temp.getBackgroundColor(), compare.getBackgroundColor());
			assertEquals(temp.getHeaderImage(), compare.getHeaderImage());
			assertEquals(temp.getBlogLayout(), compare.getBlogLayout());
			assertEquals(temp.getBlogId(), compare.getBlogId());
			
			blogService.unfollow(testMember2, temp.getBlogId());
		}
		
		followingList = blogService.getFollowingList(testMember2);
		assertEquals(0, followingList.length);
		
		blogService.removeBlog(testMember1, blogService.findBlogByName("test_blog1"));
		blogService.removeBlog(testMember1, blogService.findBlogByName("test_blog2"));
	}
	
	@Test
	public void testSearch() throws DataDuplicatedException, DataNotFoundException, IllegalDataException {
		System.out.println("********** testCreateBlog() **********");
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("target", "blog");
		searchInfo.put("searchType", "email");
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
			compare = blogService.findBlogByName(temp.getBlogName());
			assertEquals(temp.getBlogName(), compare.getBlogName());
			assertEquals(temp.getEmail(), compare.getEmail());
			assertEquals(temp.getFollowCount(), compare.getFollowCount());
			assertEquals(temp.getVisitCount(), compare.getVisitCount());
			assertEquals(temp.getBackgroundColor(), compare.getBackgroundColor());
			assertEquals(temp.getHeaderImage(), compare.getHeaderImage());
			assertEquals(temp.getBlogLayout(), compare.getBlogLayout());
			assertEquals(temp.getBlogId(), compare.getBlogId());
		}
		
		blogService.removeBlog(testMember1, blogService.findBlogByName("test_blog1"));
		blogService.removeBlog(testMember2, blogService.findBlogByName("test_blog2"));
		blogService.removeBlog(testMember1, blogService.findBlogByName("test_blog3"));
		blogService.removeBlog(testMember1, blogService.findBlogByName("test_blog4"));
		blogService.removeBlog(testMember2, blogService.findBlogByName("test_blog5"));
		blogService.removeBlog(testMember1, blogService.findBlogByName("test_blog6"));
	}
	@Test
	public void testVisitBlog() throws DataDuplicatedException, DataNotFoundException, IllegalDataException {
		System.out.println("********** testCreateBlog() **********");
		Blog blog = null;
		blogService.createBlog(testMember1, "test_blog");
		blog = blogService.findBlogByName("test_blog");
		assertEquals(0, blog.getVisitCount());
		
		blogService.visitBlog(testMember2, blog.getBlogId());
		blog = blogService.findBlogByName("test_blog");
		assertEquals(1, blog.getVisitCount());
		blogService.visitBlog(testMember3, blog.getBlogId());
		blog = blogService.findBlogByName("test_blog");
		assertEquals(2, blog.getVisitCount());
		blogService.visitBlog(testMember4, blog.getBlogId());
		blog = blogService.findBlogByName("test_blog");
		assertEquals(3, blog.getVisitCount());
		
		blogService.removeBlog(testMember1, blogService.findBlogByName("test_blog"));
	}
	
	@Test(expected=DataDuplicatedException.class)
	public void testRenameBlogDuplicateBlogName() throws DataNotFoundException, DataDuplicatedException, IllegalDataException {
		System.out.println("********** testCreateBlog() **********");
		blogService.createBlog(testMember1, "test_blog");
		blogService.createBlog(testMember2, "test_blog1");
		try {
			blogService.changeBlogName(testMember1, "test_blog", "test_blog1");
		} finally {
			blogService.removeBlog(testMember1, blogService.findBlogByName("test_blog"));
			blogService.removeBlog(testMember2, blogService.findBlogByName("test_blog1"));
		}
	}
	
	@Test(expected=IllegalDataException.class)
	public void testRenameBlogInvalidRole() throws DataNotFoundException, DataDuplicatedException, IllegalDataException {
		System.out.println("********** testCreateBlog() **********");
		blogService.createBlog(testMember1, "test_blog");
		try {
			blogService.changeBlogName(testMember2, "test_blog", "test_blog1");
		} finally {
			blogService.removeBlog(testMember1, blogService.findBlogByName("test_blog"));
		}
	}

	@Test
	public void testRenameBlog() throws DataNotFoundException, DataDuplicatedException, IllegalDataException {
		System.out.println("********** testCreateBlog() **********");
		blogService.createBlog(testMember1, "test_blog");
		blogService.changeBlogName(testMember1, "test_blog", "test_blog_admin");
		blogService.removeBlog(testMember1, blogService.findBlogByName("test_blog_admin"));
	}
	
	@After
	public void end() throws DataNotFoundException {
	
	}
}
