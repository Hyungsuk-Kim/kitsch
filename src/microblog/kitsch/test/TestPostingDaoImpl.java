package microblog.kitsch.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import microblog.kitsch.business.dataaccess.PostingDaoImpl;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.business.domain.PostingContent;
import microblog.kitsch.business.service.PostingDao;

public class TestPostingDaoImpl {
	private PostingDao postingDao = null;
	private Posting testPosting1 = null;
	private Posting testPosting2 = null;
	private PostingContent testPostingContent1 = null;
	private PostingContent testPostingContent2 = null;
	private Member testMember = null;
	
	@Before
	public void init() {
		postingDao = new PostingDaoImpl();
		testPostingContent1 = new PostingContent("This is test PostingContent written by admin.\n", "@content1.jpg", "@content2,jpg", "@content3.png");
		testPostingContent2 = new PostingContent(null, "@content1.mp4");
		testPosting1 = new Posting("Test posting. Mixed type posting. It contains multiple image files.", "admin", testPostingContent1, PostingContent.MIXED_IMAGE_FILE_CONTENT,
				Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#test#testing#admin", Posting.NORMAL_TYPE_POSTING, Posting.ON_UPDATE_AND_DELETE_CASCADE);
		testPosting2 = new Posting("Test posting. Single type posting. It contains a video file.", "admin", testPostingContent2, PostingContent.SINGLE_VIDEO_FILE_CONTENT,
				Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#test#testing#admin", Posting.NORMAL_TYPE_POSTING, Posting.ON_UPDATE_AND_DELETE_CASCADE);
		testMember = new Member("test@kitsch.com", "test", "test1234", new java.util.Date(), 1);
		//System.out.println(testPosting1.toString());
		//System.out.println(testPosting2.toString());
	}
	
	// Must be changed numeric parameters(postingNum) to valid values at 41 and 42 lines.
	/*@Test 
	public void testInsertDeleteSelect() {
		postingDao.insertPosting("first_blog", testPosting1);
		postingDao.insertPosting("first_blog", testPosting2);
		
		Posting selectedPosting1 = postingDao.selectPosting("first_blog", 5);
		Posting selectedPosting2 = postingDao.selectPosting("first_blog", 6);
		
		PostingContent pContent1 = selectedPosting1.getContents();
		PostingContent pContent2 = selectedPosting2.getContents();
		
		assertEquals(selectedPosting1.getTitle(), testPosting1.getTitle());
		assertEquals(selectedPosting1.getWriter(), testPosting1.getWriter());
		assertEquals(selectedPosting1.getContentType(), testPosting1.getContentType());
		assertEquals(selectedPosting1.getExposure(), testPosting1.getExposure());
		assertEquals(selectedPosting1.getTags(), testPosting1.getTags());
		assertEquals(selectedPosting1.getPostingType(), testPosting1.getPostingType());
		assertEquals(selectedPosting1.getReblogOption(), testPosting1.getReblogOption());
		
		String[] filePath1 = pContent1.getFilePaths();
		String[] filePath2 = pContent2.getFilePaths();
		
		for (int i = 0; i < filePath1.length; i++) {
			assertEquals(filePath1[i], testPostingContent1.getFilePaths()[i]);
		}
		for (int i = 0; i < filePath2.length; i++) {
			assertEquals(filePath2[i], testPostingContent2.getFilePaths()[i]);
		}
		assertEquals(pContent1.getBlogName(), "first_blog");
		assertEquals(pContent1.getTextContent(), testPostingContent1.getTextContent());
		
		postingDao.deletePosting(pContent1.getBlogName(), selectedPosting1);
		postingDao.deletePosting(pContent2.getBlogName(), selectedPosting2);
		
		assertFalse(postingDao.postingExists(pContent1.getBlogName(), selectedPosting1.getNum()));
		assertFalse(postingDao.postingExists(pContent2.getBlogName(), selectedPosting2.getNum()));
	}*/
	
	/*@Test
	public void testSelectAll() {
		Posting[] tempAllPostings = null;
		Posting tempPosting = null;
		
		List<Posting> pList1 = postingDao.selectAllPostings();
		tempAllPostings = pList1.toArray(new Posting[0]);
		assertFalse(pList1.isEmpty());
		for (int i = 0; i < tempAllPostings.length; i++) {
			tempPosting = postingDao.selectPosting(tempAllPostings[i].getContents().getBlogName(), tempAllPostings[i].getNum());
			assertEquals(tempAllPostings[i].getTitle(), tempPosting.getTitle());
			assertEquals(tempAllPostings[i].getWriter(), tempPosting.getWriter());
			assertEquals(tempAllPostings[i].getContentType(), tempPosting.getContentType());
			assertEquals(tempAllPostings[i].getExposure(), tempPosting.getExposure());
			assertEquals(tempAllPostings[i].getTags(), tempPosting.getTags());
			assertEquals(tempAllPostings[i].getPostingType(), tempPosting.getPostingType());
			assertEquals(tempAllPostings[i].getReblogOption(), tempPosting.getReblogOption());
			
			String[] filePath1 = tempAllPostings[i].getContents().getFilePaths();
			
			for (int j = 0; j < filePath1.length; j++) {
				assertEquals(filePath1[j], tempPosting.getContents().getFilePaths()[j]);
			}
			assertEquals(tempAllPostings[i].getContents().getBlogName(), tempPosting.getContents().getBlogName());
			assertEquals(tempAllPostings[i].getContents().getTextContent(), tempPosting.getContents().getTextContent());
		}
		
		List<Posting> pList2 = postingDao.selectAllPostings("first_blog");
		tempAllPostings = pList2.toArray(new Posting[0]);
		assertFalse(pList2.isEmpty());
		for (int i = 0; i < tempAllPostings.length; i++) {
			tempPosting = postingDao.selectPosting(tempAllPostings[i].getContents().getBlogName(), tempAllPostings[i].getNum());
			assertEquals(tempAllPostings[i].getTitle(), tempPosting.getTitle());
			assertEquals(tempAllPostings[i].getWriter(), tempPosting.getWriter());
			assertEquals(tempAllPostings[i].getContentType(), tempPosting.getContentType());
			assertEquals(tempAllPostings[i].getExposure(), tempPosting.getExposure());
			assertEquals(tempAllPostings[i].getTags(), tempPosting.getTags());
			assertEquals(tempAllPostings[i].getPostingType(), tempPosting.getPostingType());
			assertEquals(tempAllPostings[i].getReblogOption(), tempPosting.getReblogOption());
			
			String[] filePath1 = tempAllPostings[i].getContents().getFilePaths();
			
			for (int j = 0; j < filePath1.length; j++) {
				assertEquals(filePath1[j], tempPosting.getContents().getFilePaths()[j]);
			}
			assertEquals(tempAllPostings[i].getContents().getBlogName(), tempPosting.getContents().getBlogName());
			assertEquals(tempAllPostings[i].getContents().getTextContent(), tempPosting.getContents().getTextContent());
		}
		
		assertTrue(postingDao.postingExists("first_blog", 4));
		assertFalse(postingDao.postingExists("first_blog", 220));
	}*/
	
	/*@Test
	public void searching() {
		Map<String, Object> searchInfo1 = new HashMap<String, Object>();
		searchInfo1.put("target", "posting");
		searchInfo1.put("searchType", "title");
		searchInfo1.put("searchText", "mixed");
		searchInfo1.put("startRow", 1);
		searchInfo1.put("endRow", 4);
		
		Map<String, Object> searchInfo2 = new HashMap<String, Object>();
		searchInfo2.put("target", "posting");
		searchInfo2.put("contentType", PostingContent.SINGLE_VIDEO_FILE_CONTENT);
		searchInfo2.put("startRow", 1);
		searchInfo2.put("endRow", 4);
		
		Posting[] tempAllPostings = null;
		Posting tempPosting = null;
		
		List<Posting> pList1 = postingDao.selectPostingList(searchInfo1);
		tempAllPostings = pList1.toArray(new Posting[0]);
		assertFalse(pList1.isEmpty());
		for (int i = 0; i < tempAllPostings.length; i++) {
			tempPosting = postingDao.selectPosting(tempAllPostings[i].getContents().getBlogName(), tempAllPostings[i].getNum());
			assertEquals(tempAllPostings[i].getTitle(), tempPosting.getTitle());
			assertEquals(tempAllPostings[i].getWriter(), tempPosting.getWriter());
			assertEquals(tempAllPostings[i].getContentType(), tempPosting.getContentType());
			assertEquals(tempAllPostings[i].getExposure(), tempPosting.getExposure());
			assertEquals(tempAllPostings[i].getTags(), tempPosting.getTags());
			assertEquals(tempAllPostings[i].getPostingType(), tempPosting.getPostingType());
			assertEquals(tempAllPostings[i].getReblogOption(), tempPosting.getReblogOption());
			
			String[] filePath1 = tempAllPostings[i].getContents().getFilePaths();
			
			for (int j = 0; j < filePath1.length; j++) {
				assertEquals(filePath1[j], tempPosting.getContents().getFilePaths()[j]);
			}
			assertEquals(tempAllPostings[i].getContents().getBlogName(), tempPosting.getContents().getBlogName());
			assertEquals(tempAllPostings[i].getContents().getTextContent(), tempPosting.getContents().getTextContent());
		}
		
		Map<String, Object> searchInfo3 = new HashMap<String, Object>();
		searchInfo3.put("target", "posting");
		searchInfo3.put("searchType", "title");
		searchInfo3.put("searchText", "Mixed");
		
		Map<String, Object> searchInfo4 = new HashMap<String, Object>();
		searchInfo4.put("target", "posting");
		searchInfo4.put("contentType", PostingContent.SINGLE_VIDEO_FILE_CONTENT);
		
		int selectedCount1 = postingDao.selectPostingCount(searchInfo3);
		assertEquals(selectedCount1, 9);
		int selectedCount2 = postingDao.selectPostingCount(searchInfo4);
		assertEquals(selectedCount2, 9);
	}*/
	
	/*@Test
	public void testLikes() {
		Posting selectedPosting = null;
		int beforeLikeCount = 0;
		int postNum = 7;
		
		beforeLikeCount = postingDao.selectPosting("first_blog", postNum).getLikes();
		postingDao.addLikes(testMember, "first_blog", postNum);
		selectedPosting = postingDao.selectPosting("first_blog", postNum);
		assertEquals(selectedPosting.getLikes(), beforeLikeCount+1);
		
		beforeLikeCount = postingDao.selectPosting("first_blog", postNum).getLikes();
		postingDao.cancelLikes(testMember, "first_blog", postNum);
		selectedPosting = postingDao.selectPosting("first_blog", postNum);
		assertEquals(selectedPosting.getLikes(), beforeLikeCount-1);
		
	}*/
	
	/*@Test
	public void readPosting() {
		Posting selectedPosting = null;
		int beforeReadCount = 0;
		int postNum = 7;
		
		beforeReadCount = postingDao.selectPosting("first_blog", postNum).getReadCount();
		postingDao.addReadCount("first_blog", postNum);
		selectedPosting = postingDao.selectPosting("first_blog", postNum);
		assertEquals(selectedPosting.getReadCount(), beforeReadCount+1);
	}*/
	
	/*@Test
	public void testReblog() {
		postingDao.reblog(testMember, "first_blog", 7, targetBlogName);
	}*/
	
	/*@Test
	public void testGetAndSetContent() {
		int postNum = 4;
		String[] newFilePaths = {"@newVideo1.mp4", "@newVideo2.mp4"};
		PostingContent selectedContent = postingDao.getContents("first_blog", postNum);
		selectedContent.setFilePaths(newFilePaths);
		postingDao.setContents("first_blog", postNum, selectedContent);
		PostingContent changedContent = postingDao.getContents("first_blog", postNum);
		assertEquals(selectedContent.getBlogName(), changedContent.getBlogName());
		for (int i =0; i < changedContent.getFilePaths().length; i++) {
			assertEquals(newFilePaths[i], changedContent.getFilePaths()[i]);
			System.out.println("newFilePaths[" + i + "] -- " + newFilePaths[i]);
			System.out.println("changedFilePaths[" + i + "] -- " + changedContent.getFilePaths()[i]);
		}
		assertEquals(selectedContent.getPostingNum(), changedContent.getPostingNum());
		assertEquals(selectedContent.getTextContent(), changedContent.getTextContent());
	}*/
	
	/*@Test
	public void testSelectReplies() {
		PostingContent replyContent1 = new PostingContent("This is reply1. It written by aa!");
		PostingContent replyContent2 = new PostingContent("This is reply2. It written by aa!");
		PostingContent replyContent3 = new PostingContent("This is reply3. It written by aa!");
		Posting reply1 = new Posting("aa", replyContent1, PostingContent.TEXT_CONTENT, Posting.REPLY_TYPE_POSTING);
		Posting reply2 = new Posting("aa", replyContent2, PostingContent.TEXT_CONTENT, Posting.REPLY_TYPE_POSTING);
		Posting reply3 = new Posting("aa", replyContent3, PostingContent.TEXT_CONTENT, Posting.REPLY_TYPE_POSTING);
		
	}*/
	
	@Test
	public void testSelectLikes() {
		
	}
	
	@After
	public void end() {
	}
}
