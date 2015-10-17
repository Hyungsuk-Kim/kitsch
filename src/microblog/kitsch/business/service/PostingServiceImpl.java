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
		Blog blog = blogService.findBlog(blogName);
		String tableName = blog.getTableName();
		
		Posting posting = null;
		
		if (postingDao.addReadCount(tableName, postingNum) != 0) {
			if (postingDao.postingExists(tableName, postingNum)) {
				posting = postingDao.selectPosting(tableName, postingNum);
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
		Blog blog = blogService.findBlog(blogName);
		String tableName = blog.getTableName();
		
		Posting selectedPosting = null;
		
		if (postingDao.postingExists(tableName, postingNum)) {
			selectedPosting = postingDao.selectPosting(tableName, postingNum);
		} else {
			throw new DataNotFoundException("해당 포스팅이 존재하지 않습니다.");
		}
		
		return selectedPosting;
	}

	@Override
	public void removePosting(String blogName, int postingNum)
			throws DataNotFoundException {
		System.out.println("PostingService removePosting()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlog(blogName);
		String tableName = blog.getTableName();
		
		try {
			Posting posting = this.findPosting(blogName, postingNum);
			postingDao.deletePosting(tableName, posting);
		} catch (DataNotFoundException e) {
			throw new DataNotFoundException("해당 포스팅이 존재하지 않습니다.", e);
		}
		
	}

	@Override
	public void writePosting(String blogName, Posting posting) throws DataNotFoundException {
		System.out.println("PostingService writePosting()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlog(blogName);
		String tableName = blog.getTableName();
		
		postingDao.insertPosting(tableName, posting);

	}

	@Override
	public void writePosting(Map<String, Object> contentsInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePosting(String blogName, Posting posting)
			throws DataNotFoundException {
		System.out.println("PostingService updatePosting()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlog(blogName);
		String tableName = blog.getTableName();
		
		postingDao.updatePosting(tableName, posting);
	}

	@Override
	public void replyPosting(String blogName, Posting posting)
			throws DataNotFoundException {
		System.out.println("PostingService replyPosting()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlog(blogName);
		String tableName = blog.getTableName();
		
		postingDao.insertPosting(tableName, posting);
	}

	@Override
	public int getPostingCount(Map<String, Object> searchInfo) throws DataNotFoundException {
		System.out.println("PostingService getPostingCount()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		
		if (searchInfo.containsKey("blogName")) {
			String blogName = (String) searchInfo.get("blogName");
			Blog blog = blogService.findBlog(blogName);
			String tableName = blog.getTableName();
			searchInfo.replace("blogName", tableName);
		}
		
		return postingDao.selectPostingCount(searchInfo);
	}

	@Override
	public Posting[] getPostingList(Map<String, Object> searchInfo) throws DataNotFoundException {
		System.out.println("PostingService ");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		
		if (searchInfo.containsKey("blogName")) {
			String blogName = (String) searchInfo.get("blogName");
			Blog blog = blogService.findBlog(blogName);
			String tableName = blog.getTableName();
			searchInfo.replace("blogName", tableName);
		}
		
		return postingDao.selectPostingList(searchInfo).toArray(new Posting[0]);
	}

	@Override
	public void addLikes(Member member, String blogName, int postingNum)
			throws DataNotFoundException {
		System.out.println("PostingService addLikes()");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		BlogService blogService = this.getBlogServiceImplementaion();
		Blog blog = blogService.findBlog(blogName);
		String tableName = blog.getTableName();
		MemberService memberService = this.getMemberServiceImplementation();
		Member validMember = memberService.findMember(member.getName());
		
		if (postingDao.postingExists(tableName, postingNum)) {
			postingDao.addLikes(validMember, tableName, postingNum);
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
		Blog blog = blogService.findBlog(blogName);
		String tableName = blog.getTableName();
		MemberService memberService = this.getMemberServiceImplementation();
		Member validMember = memberService.findMember(member.getName());
		
		if (postingDao.postingExists(tableName, postingNum)) {
			postingDao.cancelLikes(validMember, tableName, postingNum);
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
		Blog originBlog = blogService.findBlog(originBlogName);
		String originTableName = originBlog.getTableName();
		Blog targetBlog = blogService.findBlog(targetBlogName);
		String targetTableName = targetBlog.getTableName();
		MemberService memberService = this.getMemberServiceImplementation();
		Member validMember = memberService.findMember(member.getName());
		
		if (postingDao.postingExists(originTableName, postingNum)) {
			postingDao.reblog(validMember, originTableName, postingNum, targetTableName);
		} else {
			throw new DataNotFoundException("해당 포스팅이 존재하지 않습니다.");
		}
	}

	@Override
	public int getContentType(String blogName, int postingNum)
			throws DataNotFoundException {
		System.out.println("PostingService getContentType()");
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public Posting[] getReblogedPostings(Member member) throws DataNotFoundException {
		System.out.println("PostingService getReblogedPostings");
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public Posting[] getLikedPostings(Member member) throws DataNotFoundException {
		System.out.println("PostingService get");
		
		PostingDao postingDao = this.getPosingDaoImplementation();
		MemberService memberService = this.getMemberServiceImplementation();
		Member validMember = memberService.findMember(member.getName());
		
		return postingDao.selectLikedPostings(validMember).toArray(new Posting[0]);
	}

}
