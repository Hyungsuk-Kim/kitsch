package microblog.kitsch.business.service;

import java.util.List;
import java.util.Map;

import microblog.kitsch.business.dataaccess.BlogDaoImpl;
import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.IllegalDataException;

public class BlogServiceImpl implements BlogService {

	private MemberService getMemberServiceImplimentation() {
		return new MemberServiceImpl();
	}
	
	private BlogDao getBlogDaoImplimentation() {
		return new BlogDaoImpl();
	}

	@Override
	public void createBlog(Member member, String blogName) throws DataDuplicatedException, DataNotFoundException {
		System.out.println("BlogServiceImpl createBlog()");
		
		MemberService memberService = this.getMemberServiceImplimentation();
		BlogDao blogDao = null;
		Blog blog = null;
		
		if (memberService.memberExistsByEmail(member.getEmail())) {
			blogDao = this.getBlogDaoImplimentation();
			if (!blogDao.blogExistsByName(blogName)) {
				blog = new Blog(member.getEmail(), blogName);
				blogDao.insertBlog(blog);
			} else {
				throw new DataDuplicatedException("이미 존재하는 블로그 이름입니다. [" + blogName + "]");
			}
		}
	}

	@Override
	public void updateBlog(Member member, Blog blog) throws DataNotFoundException, IllegalDataException {
		System.out.println("BlogServiceImpl updateBlog()");
		
		MemberService memberService = this.getMemberServiceImplimentation();
		BlogDao blogDao = null;
		
		if (memberService.memberExistsByEmail(member.getEmail())) {
			blogDao = this.getBlogDaoImplimentation();
			if (blogDao.blogExistsById(blog.getBlogId())) {
				if (member.getEmail().equals(blog.getEmail()) || member.getRole() == Member.ADMINISTRATOR || member.getRole() == Member.SUPER_USER) {
					blogDao.updateBlog(blog);
				} else {
					throw new IllegalDataException("해당 블로그에 대한 권한이 없습니다.");
				}
			} else {
				throw new DataNotFoundException("존재하지 않는 블로그 입니다. [" + blog.getBlogName() + "]");
			}
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getName() + "]");
		}
	}

	@Override
	public void removeBlog(Member member, Blog blog) throws DataNotFoundException, IllegalDataException {
		System.out.println("BlogServiceImpl removeBlog()");
		
		MemberService memberService = this.getMemberServiceImplimentation();
		BlogDao blogDao = null;
		
		if (memberService.memberExistsByEmail(member.getEmail())) {
			blogDao = this.getBlogDaoImplimentation();
			if (blogDao.blogExistsById(blog.getBlogId())) {
				if (member.getEmail().equals(blog.getEmail()) || member.getRole() == Member.ADMINISTRATOR || member.getRole() == Member.SUPER_USER) {
					blogDao.deleteBlog(blog);
				} else {
					throw new IllegalDataException("해당 블로그에 대한 권한이 없습니다.");
				}
			} else {
				throw new DataNotFoundException("존재하지 않는 블로그 입니다. [" + blog.getBlogName() + "]");
			}
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getName() + "]");
		}
	}

	@Override
	public Blog findBlogByName(String blogName) throws DataNotFoundException {
		System.out.println("BlogServiceImpl findBlog()");
		
		BlogDao blogDao = this.getBlogDaoImplimentation();
		if (blogDao.blogExistsByName(blogName)) {
			return blogDao.selectBlogByName(blogName);
		} else {
			throw new DataNotFoundException("존재하지 않는 블로그 입니다. [" + blogName + "]");
		}
	}
	
	@Override
	public Blog findBlogById(String blogId) throws DataNotFoundException {
		System.out.println("BlogServiceImpl findBlog()");
		
		BlogDao blogDao = this.getBlogDaoImplimentation();
		if (blogDao.blogExistsById(blogId)) {
			return blogDao.selectBlogById(blogId);
		} else {
			throw new DataNotFoundException("존재하지 않는 블로그 정보입니다. [" + blogId + "]");
		}
	}

	@Override
	public void following(Member member, String blogName) throws DataNotFoundException {
		System.out.println("BlogServiceImpl following()");
		
		MemberService memberService = this.getMemberServiceImplimentation();
		BlogDao blogDao = null;
		if (memberService.memberExistsByEmail(member.getEmail())) {
			blogDao = this.getBlogDaoImplimentation();
			if (blogDao.blogExistsByName(blogName)) {
				Blog blog = blogDao.selectBlogByName(blogName);
				blogDao.addFollowing(member, blog.getBlogId());
			} else {
				throw new DataNotFoundException("존재하지 않는 블로그 정보입니다. [" + blogName + "]");
			}
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getName() + "]");
		}
	}

	@Override
	public void unfollow(Member member, String blogName) throws DataNotFoundException {
		System.out.println("BlogServiceImpl unfollow()");
		
		MemberService memberService = this.getMemberServiceImplimentation();
		BlogDao blogDao = null;
		if (memberService.memberExistsByEmail(member.getEmail())) {
			blogDao = this.getBlogDaoImplimentation();
			if (blogDao.blogExistsByName(blogName)) {
				Blog blog = blogDao.selectBlogByName(blogName);
				blogDao.cancelFollowing(member, blog.getBlogId());
			} else {
				throw new DataNotFoundException("존재하지 않는 블로그 정보입니다. [" + blogName + "]");
			}
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getName() + "]");
		}
	}

	@Override
	public Blog[] getFollowingList(Member member) throws DataNotFoundException {
		System.out.println("BlogServiceImpl getFollowingList()");
		
		MemberService memberService = this.getMemberServiceImplimentation();
		if (memberService.memberExistsByEmail(member.getEmail())) {
			return this.getBlogDaoImplimentation().selectFollowedBlogs(member);
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getName() + "]");
		}
	}

	@Override
	public Blog[] getBlogList(Map<String, Object> searchInfo) {
		System.out.println("BlogServiceImpl getBlogList()");
		
		List<Blog> bList = this.getBlogDaoImplimentation().selectBlogList(searchInfo);
		return bList.toArray(new Blog[0]);
	}

	@Override
	public int getBlogCount(Map<String, Object> searchInfo) {
		System.out.println("BlogServiceImpl getBlogCount()");
		
		return this.getBlogDaoImplimentation().selectBlogCount(searchInfo);
	}

	@Override
	public void changeBlogName(Member member, String originBlogName, String newBlogName)
			throws DataNotFoundException, DataDuplicatedException, IllegalDataException {
		System.out.println("BlogServiceImpl changeBlogName()");
		
		MemberService memberService = this.getMemberServiceImplimentation();
		BlogDao blogDao = null;
		Member tempMember = null;
		Blog tempBlog = null;
		if (memberService.memberExistsByEmail(member.getEmail())) {
			tempMember = memberService.findMemberByEmail(member.getEmail());
			blogDao = this.getBlogDaoImplimentation();
			if (blogDao.blogExistsByName(originBlogName)) {
				tempBlog = blogDao.selectBlogByName(originBlogName);
				if (tempBlog.getEmail().equals(tempMember.getEmail()) || member.getRole() == Member.ADMINISTRATOR || member.getRole() == Member.SUPER_USER) {
					if (blogDao.blogExistsByName(newBlogName)) {
						throw new DataDuplicatedException("이미 등록된 블로그명 입니다. [" + newBlogName + "]");
					} else {
						blogDao.updateBlogName(originBlogName, newBlogName);
					}
				} else {
					throw new IllegalDataException("해당 블로그에 대한 권한이 없습니다.");
				}
			} else {
				throw new DataNotFoundException("존재하지 않는 블로그입니다. [" + originBlogName + "]");
			}
		} else {
			throw new DataNotFoundException("등록되지 않은 회원입니다. [" + member.getName() + "]");
		}
	}

	@Override
	public Blog visitBlog(Member member, String blogName) throws DataNotFoundException {
		System.out.println("BlogServiceImpl visitBlog()");
		
		Blog blog = null;
		
		MemberService memberService = this.getMemberServiceImplimentation();
		BlogDao blogDao = null;
		if (memberService.memberExistsByEmail(member.getEmail())) {
			blogDao = this.getBlogDaoImplimentation();
			if (blogDao.blogExistsByName(blogName)) {
				blog = blogDao.selectBlogByName(blogName);
				if (!(member.getEmail()).equals(blog.getEmail())) {
					blogDao.addVisitCount(blog.getBlogId());
				}
			} else {
				throw new DataNotFoundException("존재하지 않는 블로그 정보입니다. [" + blogName + "]");
			}
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getEmail() + "]");
		}
		
		return blog;
	}
	
	@Override
	public Blog visitBlog(String blogName) throws DataNotFoundException {
		System.out.println("BlogServiceImpl visitBlog()");
		
		Blog blog = null;
		
		BlogDao blogDao = this.getBlogDaoImplimentation();
		if (blogDao.blogExistsByName(blogName)) {
			blog = blogDao.selectBlogByName(blogName);
			blogDao.addVisitCount(blog.getBlogId());
		} else {
			throw new DataNotFoundException("존재하지 않는 블로그 정보입니다. [" + blogName + "]");
		}
		
		return blog;
	}

	@Override
	public boolean checkValidBlogName(String blogName) {
		System.out.println("BlogServiceImpl checkValidBlogName()");
		return this.getBlogDaoImplimentation().blogExistsByName(blogName);
	}

	@Override
	public Blog[] getMemberBlogs(Member member) throws DataNotFoundException {
		System.out.println("BlogServiceImpl getMyBlogs()");
		BlogDao blogDao = this.getBlogDaoImplimentation();
		MemberService memberService = this.getMemberServiceImplimentation();
		
		if (memberService.memberExistsByEmail(member.getEmail())) {
			return blogDao.selectMemberBlogs(member).toArray(new Blog[0]);
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getEmail() + "]");
		}
	}

	@Override
	public void removeBlog(Member member) throws DataNotFoundException {
		System.out.println("BlogServiceImpl removeBlog()");
		MemberService memberService = this.getMemberServiceImplimentation();
		if (memberService.memberExistsByEmail(member.getEmail())) {
			BlogDao blogDao = this.getBlogDaoImplimentation();
			Blog[] blogs = this.getMemberBlogs(member);
			for (Blog blog : blogs) {
				blogDao.deleteBlog(blog);
			}
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getEmail() + "]");
		}
	}

	@Override
	public boolean isFollowed(Member member, String blogName) throws DataNotFoundException {
		if(this.getMemberServiceImplimentation().memberExistsByEmail(member.getEmail())) {
			BlogDao blogDao = this.getBlogDaoImplimentation();
			if (blogDao.blogExistsByName(blogName)) {
				return blogDao.isFollowed(member, blogName);
			} else {
				throw new DataNotFoundException("존재하지 않는 블로그입니다. [" + blogName + "]");
			}
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getEmail() + "]");
		}
	}

}
