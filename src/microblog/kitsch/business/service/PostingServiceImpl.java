package microblog.kitsch.business.service;

import java.util.Map;

import microblog.kitsch.business.dataaccess.PostingDaoImpl;
import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.helper.DataNotFoundException;

public class PostingServiceImpl implements PostingService {

	private BlogService getBlogServiceImplementaion() {
		return new BlogServiceImpl();
	}
	
	private MemberService getMemberServiceImplementation() {
		return new MemberServiceImpl();
	}
	
	private PostingDao getPosingDaoImplementation() {
		return new PostingDaoImpl();
	}
	
	@Override
	public Posting readPosting(String blogName, int postingNum)
			throws DataNotFoundException {
		System.out.println("PostingService readPosting()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlogByName(blogName);
		String blogId = blog.getBlogId();
		
		Posting posting = null;
		
		if (postingDao.addReadCount(blogId, postingNum) != 0) {
			if (postingDao.postingExists(blogId, postingNum)) {
				posting = postingDao.selectPosting(blogId, postingNum);
			} else {
				throw new DataNotFoundException("해당 포스팅이 존재하지 않습니다.");
			}
		}
		
		return posting;
	}

	@Override
	public Posting findPosting(String blogName, int postingNum)
			throws DataNotFoundException {
		System.out.println("PostingService findPosting()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlogByName(blogName);
		String blogId = blog.getBlogId();
		
		Posting selectedPosting = null;
		
		if (postingDao.postingExists(blogId, postingNum)) {
			selectedPosting = postingDao.selectPosting(blogId, postingNum);
		} else {
			throw new DataNotFoundException("해당 포스팅 정보가 존재하지 않습니다. [" + blogId + ", " + postingNum + "]");
		}
		
		return selectedPosting;
	}

	@Override
	public void removePosting(String blogName, int postingNum)
			throws DataNotFoundException {
		System.out.println("PostingService removePosting()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlogByName(blogName);
		String blogId = blog.getBlogId();
		
		try {
			Posting posting = this.findPosting(blogName, postingNum);
			postingDao.deletePosting(blogId, posting);
		} catch (DataNotFoundException e) {
			e.printStackTrace();
			throw new DataNotFoundException("해당 포스팅이 존재하지 않습니다.", e);
		}
		
	}

	@Override
	public void writePosting(String blogName, Posting posting) throws DataNotFoundException {
		System.out.println("PostingService writePosting()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlogByName(blogName);
		String blogId = blog.getBlogId();
		
		postingDao.insertPosting(blogId, posting);

	}

	@Override
	public void updatePosting(String blogName, Posting posting)
			throws DataNotFoundException {
		System.out.println("PostingService updatePosting()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlogByName(blogName);
		String blogId = blog.getBlogId();
		
		postingDao.updatePosting(blogId, posting);
	}

	@Override
	public void replyPosting(String blogName, Posting posting, int postingNum)
			throws DataNotFoundException {
		System.out.println("PostingService replyPosting()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlogByName(blogName);
		String blogId = blog.getBlogId();
		
		postingDao.insertReply(blogId, posting, postingNum);;
	}

	@Override
	public int getPostingCount(Map<String, Object> searchInfo) throws DataNotFoundException {
		System.out.println("PostingService getPostingCount()");
		String blogName = (String) searchInfo.get("blogName");
		if (blogName != null) {
			Blog blog = this.getBlogServiceImplementaion().findBlogByName(blogName);
			searchInfo.replace("blogName", blog.getBlogId());
		}
		return this.getPosingDaoImplementation().selectPostingCount(searchInfo);
	}

	@Override
	public Posting[] getPostingList(Map<String, Object> searchInfo) throws DataNotFoundException {
		System.out.println("PostingService getPostingList()");
		String blogName = (String) searchInfo.get("blogName");
		if (blogName != null) {
			Blog blog = this.getBlogServiceImplementaion().findBlogByName(blogName);
			searchInfo.replace("blogName", blog.getBlogId());
		}
		return this.getPosingDaoImplementation().selectPostingList(searchInfo).toArray(new Posting[0]);
	}

	@Override
	public void addLikes(Member member, String blogName, int postingNum)
			throws DataNotFoundException {
		System.out.println("PostingService addLikes()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlogByName(blogName);
		String blogId = blog.getBlogId();
		MemberService memberService = this.getMemberServiceImplementation();
		Member validMember = memberService.findMemberByName(member.getName());
		
		if (postingDao.postingExists(blogId, postingNum)) {
			postingDao.addLikes(validMember, blogId, postingNum);
		} else {
			throw new DataNotFoundException("해당 포스팅이 존재하지 않습니다.");
		}
	}

	@Override
	public void cancelLikes(Member member, String blogName, int postingNum)
			throws DataNotFoundException {
		System.out.println("PostingService cancelLikes()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlogByName(blogName);
		String blogId = blog.getBlogId();
		MemberService memberService = this.getMemberServiceImplementation();
		Member validMember = memberService.findMemberByName(member.getName());
		
		if (postingDao.postingExists(blogId, postingNum)) {
			postingDao.cancelLikes(validMember, blogId, postingNum);
		} else {
			throw new DataNotFoundException("해당 포스팅이 존재하지 않습니다.");
		}
	}

	@Override
	public void reblog(Member member, String originBlogName, int postingNum, String targetBlogName)
			throws DataNotFoundException {
		System.out.println("PostingService ");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog originBlog = blogService.findBlogByName(originBlogName);
		String originBlogId = originBlog.getBlogId();
		Blog targetBlog = blogService.findBlogByName(targetBlogName);
		String targetBlogId = targetBlog.getBlogId();
		MemberService memberService = this.getMemberServiceImplementation();
		Member validMember = memberService.findMemberByName(member.getName());
		
		if (postingDao.postingExists(originBlogId, postingNum)) {
			postingDao.reblog(validMember, originBlogId, postingNum, targetBlogId);
		} else {
			throw new DataNotFoundException("해당 포스팅이 존재하지 않습니다.");
		}
	}

	@Override
	public Posting[] getLikedPostings(Member member) throws DataNotFoundException {
		System.out.println("PostingService getLikedPostings()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		MemberService memberService = this.getMemberServiceImplementation();
		Member validMember = memberService.findMemberByName(member.getName());
		
		return postingDao.selectLikedPostings(validMember).toArray(new Posting[0]);
	}

	@Override
	public Posting[] getReplies(String blogName, int postingNum) throws DataNotFoundException {
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlogByName(blogName);
		return this.getPosingDaoImplementation().selectReplyPostings(blog.getBlogId(), postingNum).toArray(new Posting[0]);
	}

	@Override
	public boolean isLiked(Member member, String blogName, int postingNum) throws DataNotFoundException {
		if (this.getMemberServiceImplementation().memberExistsByEmail(member.getEmail())) {
			BlogService blogService = this.getBlogServiceImplementaion();
			Blog blog = blogService.findBlogByName(blogName);
			return this.getPosingDaoImplementation().isLiked(member, blog.getBlogId(), postingNum);
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getEmail() + "]");
		}
	}

}
