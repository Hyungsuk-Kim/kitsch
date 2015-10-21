package microblog.kitsch.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import microblog.kitsch.business.dataaccess.PostingDaoImpl;
import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.business.domain.PostingContent;
import microblog.kitsch.business.service.BlogService;
import microblog.kitsch.business.service.BlogServiceImpl;
import microblog.kitsch.business.service.MemberService;
import microblog.kitsch.business.service.MemberServiceImpl;
import microblog.kitsch.business.service.PostingDao;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;

public class TestPostingDaoImpl {
	private PostingDao postingDao = new PostingDaoImpl();
	private BlogService blogService;
	private MemberService memberService;
	
	private Member testMember1 = new Member("test1@kitsch.com", "test1", "test1234");
	private Member testMember2 = new Member("test2@kitsch.com", "test2", "test1234");
	
	private String[] files1 = {"file1", "file2", "file3"};
	private String[] files2 = {"file1"};
	
	private PostingContent textContent1 = new PostingContent("This is first Text Content.");
	private PostingContent mixedContent1 = new PostingContent("This is first Mixed Content.", files2);
	private PostingContent singleContent1 = new PostingContent(files1);
	private Posting testSinglePosting1 = new Posting("First Single Content`s Title.", testMember1.getName(), singleContent1, PostingContent.SINGLE_AUDIO_FILE_CONTENT, 
			Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#TEST#testing#single_audio", Posting.NORMAL_TYPE_POSTING, Posting.NOTHING);
	private Posting testMixedPosting1 = new Posting("First Mixed Content`s Title.", testMember1.getName(), mixedContent1, PostingContent.MIXED_VIDEO_FILE_CONTENT, 
			Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#TEST#testing#mixed_video", Posting.NORMAL_TYPE_POSTING, Posting.NOTHING);
	private Posting testTextPosting1 = new Posting("First Single Content`s Title.", testMember1.getName(), textContent1, PostingContent.TEXT_CONTENT, 
			Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#TEST#testing#text", Posting.NORMAL_TYPE_POSTING, Posting.NOTHING);

	private PostingContent textContent2 = new PostingContent("This is second Text Content.");
	private PostingContent mixedContent2 = new PostingContent("This is second Mixed Content.", files1);
	private PostingContent singleContent2 = new PostingContent(files1);
	private Posting testSinglePosting2 = new Posting("Second Single Content`s Title.", testMember1.getName(), singleContent2, PostingContent.SINGLE_VIDEO_FILE_CONTENT, 
			Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#TEST#testing#single_video", Posting.NORMAL_TYPE_POSTING, Posting.NOTHING);
	private Posting testMixedPosting2 = new Posting("Second Mixed Content`s Title.", testMember1.getName(), mixedContent2, PostingContent.MIXED_IMAGE_FILE_CONTENT, 
			Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#TEST#testing#mixed_image", Posting.NORMAL_TYPE_POSTING, Posting.NOTHING);
	private Posting testTextPosting2 = new Posting("Second Single Content`s Title.", testMember1.getName(), textContent2, PostingContent.TEXT_CONTENT, 
			Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#TEST#testing#text", Posting.NORMAL_TYPE_POSTING, Posting.NOTHING);
	
	private PostingContent replyContent1 = new PostingContent("This is first reply.");
	private PostingContent replyContent2 = new PostingContent("This is second reply.");
	private PostingContent replyContent3 = new PostingContent("This is first reply`s reply.");
	private Posting reply1 = new Posting(testMember2.getName(), replyContent1, PostingContent.TEXT_CONTENT, Posting.REPLY_TYPE_POSTING);
	private Posting reply2 = new Posting(testMember2.getName(), replyContent2, PostingContent.TEXT_CONTENT, Posting.REPLY_TYPE_POSTING);
	private Posting reply3 = new Posting(testMember1.getName(), replyContent3, PostingContent.TEXT_CONTENT, Posting.REPLY_TYPE_POSTING);
	
	private PostingContent textContent3 = new PostingContent("This is first Text Content written by test2.");
	private PostingContent mixedContent3 = new PostingContent("This is first Mixed Content written by test2.", files2);
	private PostingContent singleContent3 = new PostingContent(files1);
	private Posting testSinglePosting3 = new Posting("First Single Content`s Title written by test2.", testMember2.getName(), singleContent1, PostingContent.SINGLE_AUDIO_FILE_CONTENT, 
			Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#TEST#testing#single_audio", Posting.NORMAL_TYPE_POSTING, Posting.NOTHING);
	private Posting testMixedPosting3 = new Posting("First Mixed Content`s Title written by test2.", testMember2.getName(), mixedContent1, PostingContent.MIXED_VIDEO_FILE_CONTENT, 
			Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#TEST#testing#mixed_video", Posting.NORMAL_TYPE_POSTING, Posting.NOTHING);
	private Posting testTextPosting3 = new Posting("First Single Content`s Title written by test2.", testMember2.getName(), textContent1, PostingContent.TEXT_CONTENT, 
			Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#TEST#testing#text", Posting.NORMAL_TYPE_POSTING, Posting.NOTHING);
	
	@Before
	public void init() throws DataDuplicatedException, DataNotFoundException {
		this.memberService = new MemberServiceImpl();
		memberService.registerMember(testMember1);
		memberService.registerMember(testMember2);
		
		this.blogService = new BlogServiceImpl();
		blogService.createBlog(testMember1, "first_blog");
		blogService.createBlog(testMember2, "second_blog");
		blogService.createBlog(testMember1, "third_blog");
		//System.out.println(testPosting1.toString());
		//System.out.println(testPosting2.toString());
	}
	/*
	// Must be changed numeric parameters(postingNum) to valid values at 41 and 42 lines.
	@Test 
	public void testInsertDeleteSelect() throws DataNotFoundException {
		Blog blog = blogService.findBlogByName("first_blog");
		
		postingDao.insertPosting(blog.getBlogId(), testTextPosting1);
		postingDao.insertPosting(blog.getBlogId(), testSinglePosting1);
		postingDao.insertPosting(blog.getBlogId(), testMixedPosting1);
		
		Posting selectedPosting1 = postingDao.selectPosting(blog.getBlogId(), 1);
		Posting selectedPosting2 = postingDao.selectPosting(blog.getBlogId(), 2);
		Posting selectedPosting3 = postingDao.selectPosting(blog.getBlogId(), 3);
		
		PostingContent pContent1 = selectedPosting1.getContents();
		PostingContent pContent2 = selectedPosting2.getContents();
		PostingContent pContent3 = selectedPosting3.getContents();
		
		assertEquals(selectedPosting1.getTitle(), testTextPosting1.getTitle());
		assertEquals(selectedPosting1.getWriter(), testTextPosting1.getWriter());
		assertEquals(selectedPosting1.getContentType(), testTextPosting1.getContentType());
		assertEquals(selectedPosting1.getExposure(), testTextPosting1.getExposure());
		assertEquals(selectedPosting1.getTags(), testTextPosting1.getTags());
		assertEquals(selectedPosting1.getPostingType(), testTextPosting1.getPostingType());
		assertEquals(selectedPosting1.getReblogOption(), testTextPosting1.getReblogOption());
		
		String[] filePath2 = pContent2.getFilePaths();
		String[] filePath3 = pContent3.getFilePaths();
		
		for (int i = 0; i < filePath2.length; i++) {
			assertEquals(filePath2[i], testSinglePosting1.getContents().getFilePaths()[i]);
		}
		
		for (int i = 0; i < filePath3.length; i++) {
			assertEquals(filePath3[i], testMixedPosting1.getContents().getFilePaths()[i]);
		}
		assertEquals(pContent1.getTextContent(), textContent1.getTextContent());
		assertEquals(pContent3.getTextContent(), mixedContent1.getTextContent());
		
		postingDao.deletePosting(blog.getBlogId(), selectedPosting1);
		postingDao.deletePosting(blog.getBlogId(), selectedPosting2);
		postingDao.deletePosting(blog.getBlogId(), selectedPosting3);
		
		assertFalse(postingDao.postingExists(blog.getBlogId(), selectedPosting1.getNum()));
		assertFalse(postingDao.postingExists(blog.getBlogId(), selectedPosting2.getNum()));
		assertFalse(postingDao.postingExists(blog.getBlogId(), selectedPosting3.getNum()));
	}
	@Test
	public void testSelectAll() throws DataNotFoundException {
		Blog blog1 = blogService.findBlogByName("first_blog");
		
		postingDao.insertPosting(blog1.getBlogId(), testMixedPosting1);
		postingDao.insertPosting(blog1.getBlogId(), testTextPosting1);
		postingDao.insertPosting(blog1.getBlogId(), testSinglePosting1);
		
		postingDao.insertPosting(blog1.getBlogId(), testMixedPosting2);
		postingDao.insertPosting(blog1.getBlogId(), testTextPosting2);
		postingDao.insertPosting(blog1.getBlogId(), testSinglePosting2);
		
		Posting[] tempAllPostings = null;
		Posting tempPosting = null;
		
		List<Posting> pList1 = postingDao.selectAllPostings();
		tempAllPostings = pList1.toArray(new Posting[0]);
		assertFalse(pList1.isEmpty());
		for (int i = 0; i < tempAllPostings.length; i++) {
			tempPosting = postingDao.selectPosting(blog1.getBlogId(), tempAllPostings[i].getNum());
			assertEquals(tempAllPostings[i].getTitle(), tempPosting.getTitle());
			assertEquals(tempAllPostings[i].getWriter(), tempPosting.getWriter());
			assertEquals(tempAllPostings[i].getContentType(), tempPosting.getContentType());
			assertEquals(tempAllPostings[i].getExposure(), tempPosting.getExposure());
			assertEquals(tempAllPostings[i].getTags(), tempPosting.getTags());
			assertEquals(tempAllPostings[i].getPostingType(), tempPosting.getPostingType());
			assertEquals(tempAllPostings[i].getReblogOption(), tempPosting.getReblogOption());
			
			if (tempAllPostings[i].getContentType() / 100 == PostingContent.SINGLE_TYPE_CONTENT || tempAllPostings[i].getContentType() / 100 == PostingContent.MIXED_TYPE_CONTENT) {
				String[] filePath1 = tempAllPostings[i].getContents().getFilePaths();
				for (int j = 0; j < filePath1.length; j++) {
					assertEquals(filePath1[j], tempPosting.getContents().getFilePaths()[j]);
				}
			}
			if (tempAllPostings[i].getContentType() == PostingContent.TEXT_CONTENT || tempAllPostings[i].getContentType() / 100 == PostingContent.MIXED_TYPE_CONTENT) {
				assertEquals(tempAllPostings[i].getContents().getTextContent(), tempPosting.getContents().getTextContent());
			}
		}
		
		List<Posting> pList2 = postingDao.selectAllPostings(blog1.getBlogId());
		tempAllPostings = pList2.toArray(new Posting[0]);
		assertFalse(pList2.isEmpty());
		for (int i = 0; i < tempAllPostings.length; i++) {
			tempPosting = postingDao.selectPosting(blog1.getBlogId(), tempAllPostings[i].getNum());
			assertEquals(tempAllPostings[i].getTitle(), tempPosting.getTitle());
			assertEquals(tempAllPostings[i].getWriter(), tempPosting.getWriter());
			assertEquals(tempAllPostings[i].getContentType(), tempPosting.getContentType());
			assertEquals(tempAllPostings[i].getExposure(), tempPosting.getExposure());
			assertEquals(tempAllPostings[i].getTags(), tempPosting.getTags());
			assertEquals(tempAllPostings[i].getPostingType(), tempPosting.getPostingType());
			assertEquals(tempAllPostings[i].getReblogOption(), tempPosting.getReblogOption());
			
			if (tempAllPostings[i].getContentType() / 100 == PostingContent.SINGLE_TYPE_CONTENT || tempAllPostings[i].getContentType() / 100 == PostingContent.MIXED_TYPE_CONTENT) {
				String[] filePath1 = tempAllPostings[i].getContents().getFilePaths();
				for (int j = 0; j < filePath1.length; j++) {
					assertEquals(filePath1[j], tempPosting.getContents().getFilePaths()[j]);
				}
			}
			if (tempAllPostings[i].getContentType() == PostingContent.TEXT_CONTENT || tempAllPostings[i].getContentType() / 100 == PostingContent.MIXED_TYPE_CONTENT) {
				assertEquals(tempAllPostings[i].getContents().getTextContent(), tempPosting.getContents().getTextContent());
			}
		}
		
		assertTrue(postingDao.postingExists(blog1.getBlogId(), 2));
		assertFalse(postingDao.postingExists(blog1.getBlogId(), 4220));
	}
	
	@Test
	public void searching() throws DataNotFoundException {
		Blog blog1 = blogService.findBlogByName("first_blog");
		
		postingDao.insertPosting(blog1.getBlogId(), testMixedPosting1);
		postingDao.insertPosting(blog1.getBlogId(), testTextPosting1);
		postingDao.insertPosting(blog1.getBlogId(), testSinglePosting1);
		
		postingDao.insertPosting(blog1.getBlogId(), testMixedPosting2);
		postingDao.insertPosting(blog1.getBlogId(), testTextPosting2);
		postingDao.insertPosting(blog1.getBlogId(), testSinglePosting2);
		
		Map<String, Object> searchInfo1 = new HashMap<String, Object>();
		searchInfo1.put("target", "posting");
		searchInfo1.put("searchType", "title");
		searchInfo1.put("searchText", "mixed");
		searchInfo1.put("startRow", 1);
		searchInfo1.put("endRow", 4);
		
		Map<String, Object> searchInfo2 = new HashMap<String, Object>();
		searchInfo2.put("target", "posting");
		searchInfo2.put("contentType", PostingContent.SINGLE_AUDIO_FILE_CONTENT);
		searchInfo2.put("startRow", 1);
		searchInfo2.put("endRow", 4);
		
		Posting[] tempAllPostings = null;
		Posting tempPosting = null;
		
		List<Posting> pList1 = postingDao.selectPostingList(searchInfo1);
		tempAllPostings = pList1.toArray(new Posting[0]);
		assertFalse(pList1.isEmpty());
		for (int i = 0; i < tempAllPostings.length; i++) {
			tempPosting = postingDao.selectPosting(blog1.getBlogId(), tempAllPostings[i].getNum());
			assertEquals(tempAllPostings[i].getTitle(), tempPosting.getTitle());
			assertEquals(tempAllPostings[i].getWriter(), tempPosting.getWriter());
			assertEquals(tempAllPostings[i].getContentType(), tempPosting.getContentType());
			assertEquals(tempAllPostings[i].getExposure(), tempPosting.getExposure());
			assertEquals(tempAllPostings[i].getTags(), tempPosting.getTags());
			assertEquals(tempAllPostings[i].getPostingType(), tempPosting.getPostingType());
			assertEquals(tempAllPostings[i].getReblogOption(), tempPosting.getReblogOption());
			
			if (tempAllPostings[i].getContentType() / 100 == PostingContent.SINGLE_TYPE_CONTENT || tempAllPostings[i].getContentType() / 100 == PostingContent.MIXED_TYPE_CONTENT) {
				String[] filePath1 = tempAllPostings[i].getContents().getFilePaths();
				for (int j = 0; j < filePath1.length; j++) {
					assertEquals(filePath1[j], tempPosting.getContents().getFilePaths()[j]);
				}
			}
			if (tempAllPostings[i].getContentType() == PostingContent.TEXT_CONTENT || tempAllPostings[i].getContentType() / 100 == PostingContent.MIXED_TYPE_CONTENT) {
				assertEquals(tempAllPostings[i].getContents().getTextContent(), tempPosting.getContents().getTextContent());
			}
		}
		
		Map<String, Object> searchInfo3 = new HashMap<String, Object>();
		searchInfo3.put("target", "posting");
		searchInfo3.put("searchType", "title");
		searchInfo3.put("searchText", "Mixed");
		
		Map<String, Object> searchInfo4 = new HashMap<String, Object>();
		searchInfo4.put("target", "posting");
		searchInfo4.put("contentType", PostingContent.SINGLE_AUDIO_FILE_CONTENT);
		
		int selectedCount1 = postingDao.selectPostingCount(searchInfo3);
		assertEquals(selectedCount1, 2);
		int selectedCount2 = postingDao.selectPostingCount(searchInfo4);
		assertEquals(selectedCount2, 1);
	}
	
	@Test
	public void testLikes() throws DataNotFoundException {
		Blog blog = blogService.findBlogByName("first_blog");
		
		postingDao.insertPosting(blog.getBlogId(), testMixedPosting1);
		postingDao.insertPosting(blog.getBlogId(), testSinglePosting1);
		postingDao.insertPosting(blog.getBlogId(), testMixedPosting3);
		postingDao.insertPosting(blog.getBlogId(), testTextPosting3);
		Posting selectedPosting = null;
		int beforeLikeCount = 0;
		int postNum = 3;
		
		beforeLikeCount = postingDao.selectPosting(blog.getBlogId(), postNum).getLikes();
		postingDao.addLikes(testMember1, blog.getBlogId(), postNum);
		selectedPosting = postingDao.selectPosting(blog.getBlogId(), postNum);
		assertEquals(selectedPosting.getLikes(), beforeLikeCount+1);
		
		beforeLikeCount = postingDao.selectPosting(blog.getBlogId(), postNum).getLikes();
		postingDao.cancelLikes(testMember1, blog.getBlogId(), postNum);
		selectedPosting = postingDao.selectPosting(blog.getBlogId(), postNum);
		assertEquals(selectedPosting.getLikes(), beforeLikeCount-1);
		
	}
	
	@Test
	public void readPosting() throws DataNotFoundException {
		Blog blog = blogService.findBlogByName("first_blog");
		
		postingDao.insertPosting(blog.getBlogId(), testMixedPosting1);
		postingDao.insertPosting(blog.getBlogId(), testSinglePosting1);
		postingDao.insertPosting(blog.getBlogId(), testMixedPosting3);
		postingDao.insertPosting(blog.getBlogId(), testTextPosting3);
		
		Posting selectedPosting = null;
		int beforeReadCount = 0;
		int postNum = 2;
		
		beforeReadCount = postingDao.selectPosting(blog.getBlogId(), postNum).getReadCount();
		postingDao.addReadCount(blog.getBlogId(), postNum);
		selectedPosting = postingDao.selectPosting(blog.getBlogId(), postNum);
		assertEquals(selectedPosting.getReadCount(), beforeReadCount+1);
	}
	
	@Test
	public void testReblog() throws DataNotFoundException {
		Blog origin = blogService.findBlogByName("first_blog");
		Blog target = blogService.findBlogByName("second_blog");
		
		postingDao.insertPosting(origin.getBlogId(), testMixedPosting1);
		postingDao.reblog(testMember2, origin.getBlogId(), 1, target.getBlogId());
		
		Posting originPosting = postingDao.selectPosting(origin.getBlogId(), 1);
		Posting reblogedPosting = postingDao.selectPosting(target.getBlogId(), 1);
		
		assertEquals(originPosting.getTitle(), reblogedPosting.getTitle());
		assertEquals(originPosting.getWriter() + " >> " + testMember2.getName(), reblogedPosting.getWriter());
		assertEquals(originPosting.getContentType(), reblogedPosting.getContentType());
		assertEquals(originPosting.getExposure(), reblogedPosting.getExposure());
		assertEquals(originPosting.getPostingType(), reblogedPosting.getPostingType());
		assertEquals(originPosting.getReblogOption(), reblogedPosting.getReblogOption());
		assertEquals(originPosting.getTags(), reblogedPosting.getTags());
		assertEquals(originPosting.getReblogCount(), 1);
		
		if (originPosting.getContentType() / 100 == PostingContent.SINGLE_TYPE_CONTENT || originPosting.getContentType() / 100 == PostingContent.MIXED_TYPE_CONTENT) {
			String[] filePath1 = originPosting.getContents().getFilePaths();
			String[] filePath2 = reblogedPosting.getContents().getFilePaths();
			for (int j = 0; j < filePath1.length; j++) {
				assertEquals(filePath1[j], filePath2[j]);
			}
		}
		if (originPosting.getContentType() == PostingContent.TEXT_CONTENT || originPosting.getContentType() / 100 == PostingContent.MIXED_TYPE_CONTENT) {
			assertEquals(originPosting.getContents().getTextContent(), reblogedPosting.getContents().getTextContent());
		}
	}
	 */
	
	@Test
	public void testGetAndSetContent() {
		int postNum = 1;
		String[] newFilePaths = {"@newVideo1.mp4", "@newVideo2.mp4"};
		
		Blog blog = blogService.findBlogByName("first_blog");
		
		postingDao.insertPosting(blog.getBlogId(), testMixedPosting1);
		
		PostingContent selectedContent = postingDao.getContents(blog.getBlogId(), postNum);
		selectedContent.setFilePaths(newFilePaths);
		postingDao.setContents(blog.getBlogId(), postNum, selectedContent);
		PostingContent changedContent = postingDao.getContents("first_blog", postNum);
		assertEquals(selectedContent.getBlogName(), changedContent.getBlogName());
		for (int i =0; i < changedContent.getFilePaths().length; i++) {
			assertEquals(newFilePaths[i], changedContent.getFilePaths()[i]);
			System.out.println("newFilePaths[" + i + "] -- " + newFilePaths[i]);
			System.out.println("changedFilePaths[" + i + "] -- " + changedContent.getFilePaths()[i]);
		}
		assertEquals(selectedContent.getPostingNum(), changedContent.getPostingNum());
		assertEquals(selectedContent.getTextContent(), changedContent.getTextContent());
	}
	
	/*@Test
	public void testSelectReplies() {
		PostingContent replyContent1 = new PostingContent("This is reply1. It written by aa!");
		PostingContent replyContent2 = new PostingContent("This is reply2. It written by aa!");
		PostingContent replyContent3 = new PostingContent("This is reply3. It written by aa!");
		Posting reply1 = new Posting("aa", replyContent1, PostingContent.TEXT_CONTENT, Posting.REPLY_TYPE_POSTING);
		Posting reply2 = new Posting("aa", replyContent2, PostingContent.TEXT_CONTENT, Posting.REPLY_TYPE_POSTING);
		Posting reply3 = new Posting("aa", replyContent3, PostingContent.TEXT_CONTENT, Posting.REPLY_TYPE_POSTING);
		
	}*/
	
	@After
	public void end() throws DataNotFoundException {
		blogService.removeBlog(testMember1);
		blogService.removeBlog(testMember2);
		memberService.removeMember(testMember1);
		memberService.removeMember(testMember2);
	}
}
