package microblog.kitsch.test;

import org.junit.Test;

import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.business.domain.PostingContent;
import microblog.kitsch.business.service.BlogService;
import microblog.kitsch.business.service.BlogServiceImpl;
import microblog.kitsch.business.service.MemberService;
import microblog.kitsch.business.service.MemberServiceImpl;
import microblog.kitsch.business.service.PostingService;
import microblog.kitsch.business.service.PostingServiceImpl;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;

public class TestAll {
	private PostingService postingService = new PostingServiceImpl();
	private BlogService blogService = new BlogServiceImpl();
	private MemberService memberService = new MemberServiceImpl();
	
	@Test
	public void insertBlog() {
		Member admin = new Member("admin.kitsch.com", "admin", "admin", new java.util.Date(), Member.ADMINISTRATOR);
		Member aa = new Member("dd@naver.com" ,"aa", "1234", new java.util.Date(), Member.NORMAL_USER);
		Member test = new Member("test@kitsch.com", "test", "test1234", new java.util.Date(), 1);
		
		Blog testBlog1 = new Blog("test_blog1", "admin");
		Blog testBlog2 = new Blog("test_blog2", "admin");
		Blog testBlog3 = new Blog("test_blog3", "aa");

		PostingContent testPostingContent1 = new PostingContent("This is test PostingContent written by admin.\n", "@content1.jpg", "@content2,jpg", "@content3.png");
		PostingContent testPostingContent2 = new PostingContent(null, "@content1.mp4");
		Posting testPosting1 = new Posting("Test posting. Mixed type posting. It contains multiple image files.", "admin", testPostingContent1, PostingContent.MIXED_IMAGE_FILE_CONTENT,
				Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#test#testing#admin", Posting.NORMAL_TYPE_POSTING, Posting.ON_UPDATE_AND_DELETE_CASCADE);
		Posting testPosting2 = new Posting("Test posting. Single type posting. It contains a video file.", "admin", testPostingContent2, PostingContent.SINGLE_VIDEO_FILE_CONTENT,
				Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG, "#test#testing#admin", Posting.NORMAL_TYPE_POSTING, Posting.ON_UPDATE_AND_DELETE_CASCADE);
	
		try {
			blogService.createBlog(admin, "test_blog11");
			blogService.createBlog(admin, "test_blog22");
			blogService.createBlog(aa, "aa_blog");
		} catch (DataDuplicatedException e) {
			e.printStackTrace();
		} catch (DataNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			postingService.writePosting("test_blog11", testPosting1);
			postingService.writePosting("test_blog22", testPosting2);
		} catch (DataNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
