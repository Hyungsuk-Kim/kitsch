package microblog.kitsch.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.business.domain.PostingContent;
import microblog.kitsch.business.service.*;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;

public class TestPostingServiceImpl {
	private PostingService postingService = new PostingServiceImpl();
	private BlogService blogService = new BlogServiceImpl();
	private MemberService memberService = new MemberServiceImpl();
	
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
	
	private Posting testSinglePosting3 = new Posting("First Single Content`s Title written by test2.", testMember2.getName(), singleContent3, PostingContent.SINGLE_AUDIO_FILE_CONTENT, 
			Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#TEST#testing#single_audio", Posting.NORMAL_TYPE_POSTING, Posting.NOTHING);
	
	private Posting testMixedPosting3 = new Posting("First Mixed Content`s Title written by test2.", testMember2.getName(), mixedContent3, PostingContent.MIXED_VIDEO_FILE_CONTENT, 
			Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#TEST#testing#mixed_video", Posting.NORMAL_TYPE_POSTING, Posting.NOTHING);
	private Posting testTextPosting3 = new Posting("First Single Content`s Title written by test2.", testMember2.getName(), textContent3, PostingContent.TEXT_CONTENT, 
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
	}
	
	@Test(expected=DataNotFoundException.class)
	public void testPostingWriteFindDelete() throws DataNotFoundException {
		postingService.writePosting("first_blog", testMixedPosting1);
		postingService.writePosting("first_blog", testTextPosting1);
		postingService.writePosting("first_blog", testSinglePosting1);
		postingService.writePosting("second_blog", testMixedPosting3);
		postingService.writePosting("second_blog", testTextPosting3);
		postingService.writePosting("second_blog", testSinglePosting3);
		
		Posting tempPosting = postingService.findPosting("first_blog", 1);
		
		assertEquals(testMixedPosting1.getWriter(), tempPosting.getWriter());
		assertEquals(testMixedPosting1.getTitle(), tempPosting.getTitle());
		assertEquals(testMixedPosting1.getContentType(), tempPosting.getContentType());
		assertEquals(testMixedPosting1.getExposure(), tempPosting.getExposure());
		assertEquals(testMixedPosting1.getTags(), tempPosting.getTags());
		assertEquals(testMixedPosting1.getPostingType(), tempPosting.getPostingType());
		assertEquals(testMixedPosting1.getReblogOption(), tempPosting.getReblogOption());
		
		PostingContent originContent = testMixedPosting1.getContents();
		PostingContent compareContent = tempPosting.getContents();
		assertEquals(originContent.getTextContent(), compareContent.getTextContent());
		
		String[] originFiles = originContent.getFilePaths();
		String[] compareFiles = compareContent.getFilePaths();
		for (int i = 0; i < originFiles.length; i++) {
			assertEquals(originFiles[i], compareFiles[i]);
		}
		
		
		postingService.removePosting("first_blog", 1);
		postingService.removePosting("first_blog", 2);
		postingService.removePosting("first_blog", 3);
		postingService.removePosting("second_blog", 1);
		postingService.removePosting("second_blog", 2);
		postingService.removePosting("second_blog", 3);
		
		tempPosting = postingService.findPosting("second_blog", 2);
	}
	
	@Test
	public void testReadPosting() throws DataNotFoundException {
		postingService.writePosting("first_blog", testMixedPosting2);
		postingService.writePosting("first_blog", testSinglePosting2);
		postingService.writePosting("first_blog", testTextPosting2);
		
		int readCount = 0;
		for (int i = 0; i < 4; i++) {
			postingService.readPosting("first_blog", 1);
			readCount++;
		}
		Posting tempPosting = postingService.findPosting("first_blog", 1);
		assertEquals(tempPosting.getReadCount(), readCount);
	}
	
	@Test
	public void testLikes() throws DataNotFoundException {
		postingService.writePosting("first_blog", testMixedPosting1);
		postingService.writePosting("first_blog", testTextPosting1);
		postingService.writePosting("first_blog", testSinglePosting1);
		postingService.writePosting("first_blog", testMixedPosting2);
		
		postingService.addLikes(testMember2, "first_blog", 1);
		postingService.addLikes(testMember2, "first_blog", 2);
		postingService.addLikes(testMember2, "first_blog", 3);
		postingService.addLikes(testMember2, "first_blog", 4);
		Posting posting = postingService.findPosting("first_blog", 1);
		assertEquals(posting.getLikes(), 1);
		
		Posting[] likePostings = postingService.getLikedPostings(testMember2);
		assertEquals(4, likePostings.length);
		
		postingService.cancelLikes(testMember2, "first_blog", 1);
		postingService.cancelLikes(testMember2, "first_blog", 2);
		postingService.cancelLikes(testMember2, "first_blog", 3);
		postingService.cancelLikes(testMember2, "first_blog", 4);
		posting = postingService.findPosting("first_blog", 1);
		assertEquals(posting.getLikes(), 0);
	}

	@Test
	public void testUpdatePosting() throws DataNotFoundException {
		String newTitle = "The new title.";
		String newTags = "#update";
		
		postingService.writePosting("first_blog", testMixedPosting1);
		
		Posting originPosting = postingService.findPosting("first_blog", 1);
		originPosting.setContents(mixedContent2);
		originPosting.setTitle(newTitle);
		originPosting.setTags(newTags);
		
		postingService.updatePosting("first_blog", originPosting);
		
		Posting changedPosting = postingService.findPosting("first_blog", 1);
		assertEquals(newTitle, changedPosting.getTitle());
		assertEquals(newTags, changedPosting.getTags());
		PostingContent changedContents = changedPosting.getContents();
		assertEquals(mixedContent2.getTextContent(), changedContents.getTextContent());
		String[] newFiles = mixedContent2.getFilePaths();
		String[] changedFiles = changedContents.getFilePaths(); 
		for (int i = 0; i < newFiles.length; i++) {
			assertEquals(newFiles[i], changedFiles[i]);
		}
		
		postingService.removePosting("first_blog", 1);
	}
	
	@Test
	public void testReply() throws DataNotFoundException {
		postingService.writePosting("first_blog", testMixedPosting1);
		
		postingService.replyPosting("first_blog", reply1, 1);
		postingService.replyPosting("first_blog", reply2, 1);
		postingService.replyPosting("first_blog", reply3, 1);
		
		Posting[] replies = postingService.getReplies("first_blog", 1);
		assertEquals(3, replies.length);
		assertEquals(reply1.getWriter(), replies[0].getWriter());
		assertEquals(reply1.getContentType(), replies[0].getContentType());
		assertEquals(reply1.getExposure(), replies[0].getExposure());
		assertEquals(reply1.getTags(), replies[0].getTags());
		assertEquals(reply1.getPostingType(), replies[0].getPostingType());
		assertEquals(reply1.getReblogOption(), replies[0].getReblogOption());
		
		postingService.removePosting("first_blog", 1);
	}
	
	@Test
	public void testReblog() throws DataNotFoundException {
		postingService.writePosting("first_blog", testMixedPosting1);
		postingService.reblog(testMember2, "first_blog", 1, "third_blog");
		
		Posting reblogedPosting = postingService.findPosting("third_blog", 1);
		assertEquals(reblogedPosting.getTitle(), testMixedPosting1.getTitle());
		assertEquals(reblogedPosting.getWriter(), testMixedPosting1.getWriter() + " >> " + testMember2.getName());
		assertEquals(reblogedPosting.getContentType(), testMixedPosting1.getContentType());
		assertEquals(reblogedPosting.getExposure(), testMixedPosting1.getExposure());
		assertEquals(reblogedPosting.getTags(), testMixedPosting1.getTags());
		assertEquals(reblogedPosting.getPostingType(), testMixedPosting1.getPostingType());
		assertEquals(reblogedPosting.getReblogOption(), testMixedPosting1.getReblogOption());
		
		postingService.removePosting("first_blog", 1);
		postingService.removePosting("third_blog", 1);
	}
	
	@Test
	public void testSearchPosting() throws DataNotFoundException {
		postingService.writePosting("first_blog", testMixedPosting1);
		postingService.writePosting("first_blog", testTextPosting1);
		postingService.writePosting("first_blog", testSinglePosting1);
		postingService.writePosting("second_blog", testMixedPosting2);
		postingService.writePosting("second_blog", testTextPosting2);
		postingService.writePosting("second_blog", testSinglePosting2);
		postingService.writePosting("third_blog", testMixedPosting3);
		postingService.writePosting("third_blog", testTextPosting3);
		postingService.writePosting("third_blog", testSinglePosting3);
		
		Map<String, Object> searchInfo1 =  new HashMap<String, Object>(); // expected - 6
		searchInfo1.put("target", "all");
		searchInfo1.put("searchType", "writer");
		searchInfo1.put("searchText", "1");
		searchInfo1.put("startRow", 1);
		searchInfo1.put("endRow", 10);
		
		Map<String, Object> searchInfo2 =  new HashMap<String, Object>(); // expected - 3
		searchInfo2.put("target", "posting");
		searchInfo2.put("searchType", "title");
		searchInfo2.put("searchText", "secon");
		searchInfo2.put("startRow", 1);
		searchInfo2.put("endRow", 10);
		
		// problem1 : 모든 테이블마다 startRow & endRow num이 적용됨. 통합 적용 필요!!!
		Map<String, Object> searchInfo3 =  new HashMap<String, Object>(); // expected - count = 9, list = 4
		searchInfo3.put("target", "all");
		searchInfo3.put("searchType", "tags");
		searchInfo3.put("searchText", "tes");
		searchInfo3.put("startRow", 1);
		searchInfo3.put("endRow", 4);
		
		Map<String, Object> searchInfo4 =  new HashMap<String, Object>(); // expected - count = 4, list = 3
		searchInfo4.put("target", "all");
		searchInfo4.put("searchType", "contents");
		searchInfo4.put("searchText", "first");
		searchInfo4.put("startRow", 2);
		searchInfo4.put("endRow", 4);
		
		Map<String, Object> searchInfo5 =  new HashMap<String, Object>(); // expected - count = 1, list = 1
		searchInfo5.put("target", "all");
		searchInfo5.put("contentType", PostingContent.MIXED_IMAGE_FILE_CONTENT);
		searchInfo5.put("startRow", 1);
		searchInfo5.put("endRow", 10);
		
		Map<String, Object> searchInfo6 =  new HashMap<String, Object>(); // expected - count = 3, list = 2
		searchInfo6.put("target", "posting");
		searchInfo6.put("blogName", "first_blog");
		searchInfo6.put("searchType", "title");
		searchInfo6.put("searchText", "first");
		searchInfo6.put("startRow", 2);
		searchInfo6.put("endRow", 3);
		
		System.out.println("1------------------------------------------------------------------");
		int count = postingService.getPostingCount(searchInfo1);
		assertEquals(6, count);
		Posting[] postings = postingService.getPostingList(searchInfo1);
		assertEquals(count, postings.length);
		System.out.println("------------------------------------------------------------------1");
		
		System.out.println("2------------------------------------------------------------------");
		count = postingService.getPostingCount(searchInfo2);
		assertEquals(3, count);
		postings = postingService.getPostingList(searchInfo2);
		assertEquals(count, postings.length);
		System.out.println("------------------------------------------------------------------2");
		
		System.out.println("3------------------------------------------------------------------");
		count = postingService.getPostingCount(searchInfo3);
		assertEquals(9, count);
		postings = postingService.getPostingList(searchInfo3);
		assertEquals(4, postings.length);
		System.out.println("------------------------------------------------------------------3");
		
		System.out.println("4------------------------------------------------------------------");
		count = postingService.getPostingCount(searchInfo4);
		assertEquals(4, count);
		postings = postingService.getPostingList(searchInfo4);
		assertEquals(3, postings.length);
		System.out.println("------------------------------------------------------------------4");
		
		System.out.println("5------------------------------------------------------------------");
		count = postingService.getPostingCount(searchInfo5);
		assertEquals(1, count);
		postings = postingService.getPostingList(searchInfo5);
		assertEquals(count, postings.length);
		System.out.println("------------------------------------------------------------------5");
		
		System.out.println("6------------------------------------------------------------------");
		count = postingService.getPostingCount(searchInfo6);
		assertEquals(3, count);
		searchInfo6.replace("blogName", "first_blog"); // getPostingCount()에서 바뀌었기 때문에 원래 값으로 초기화.
		postings = postingService.getPostingList(searchInfo6);
		assertEquals(2, postings.length);
		System.out.println("------------------------------------------------------------------6");
		
	}
	
	@After
	public void end() throws DataNotFoundException {
		blogService.removeBlog(testMember1);
		blogService.removeBlog(testMember2);
		memberService.removeMember(testMember1);
		memberService.removeMember(testMember2);
	}
}
