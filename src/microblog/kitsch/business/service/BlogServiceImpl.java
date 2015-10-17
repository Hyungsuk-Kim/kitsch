package microblog.kitsch.business.service;

import java.util.List;
import java.util.Map;

import microblog.kitsch.business.dataaccess.BlogDaoImpl;
import microblog.kitsch.business.dataaccess.MemberDaoImpl;
import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.helper.DataDuplicatedException;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.IllegalDataException;

public class BlogServiceImpl implements BlogService {

	private MemberDao getMemberDaoImplimentation() {
		return new MemberDaoImpl();
	}
	
	private BlogDao getBlogDaoImplimentation() {
		return new BlogDaoImpl();
	}

	@Override
	public void createBlog(Member member, String blogName) throws DataDuplicatedException, DataNotFoundException {
		System.out.println("BlogServiceImpl createBlog()");
		
		MemberDao memberDao = this.getMemberDaoImplimentation();
		BlogDao blogDao = null;
		Blog blog = null;
		
		if (memberDao.memberNameExists(member.getName())) {
			blogDao = this.getBlogDaoImplimentation();
			if (!blogDao.blogExists(blogName)) {
				blog = new Blog(blogName, member.getName());
				blogDao.insertBlog(blog);
			} else {
				throw new DataDuplicatedException("이미 존재하는 블로그 이름입니다. [" + blogName + "]");
			}
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getName() + "]");
		}
	}

	@Override
	public void updateBlog(Member member, Blog blog) throws DataNotFoundException {
		System.out.println("BlogServiceImpl updateBlog()");
		
		MemberDao memberDao = this.getMemberDaoImplimentation();
		BlogDao blogDao = null;
		
		if (memberDao.memberNameExists(member.getName())) {
			blogDao = this.getBlogDaoImplimentation();
			if (!blogDao.blogExists(blog.getBlogName())) {
				throw new DataNotFoundException("존재하지 않는 블로그 입니다. [" + blog.getBlogName() + "]");
			}
			blogDao.updateBlog(blog);
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getName() + "]");
		}
	}

	@Override
	public void removeBlog(Member member, Blog blog) throws DataNotFoundException {
		System.out.println("BlogServiceImpl removeBlog()");
		
		MemberDao memberDao = this.getMemberDaoImplimentation();
		BlogDao blogDao = null;
		
		if (memberDao.memberNameExists(member.getName())) {
			blogDao = this.getBlogDaoImplimentation();
			if (!blogDao.blogExists(blog.getBlogName())) {
				throw new DataNotFoundException("존재하지 않는 블로그 입니다. [" + blog.getBlogName() + "]");
			}
			blogDao.deleteBlog(blog);
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getName() + "]");
		}
	}

	@Override
	public Blog findBlog(String blogName) throws DataNotFoundException {
		System.out.println("BlogServiceImpl findBlog()");
		
		BlogDao blogDao = this.getBlogDaoImplimentation();
		if (blogDao.blogExists(blogName)) {
			return blogDao.selectBlog(blogName);
		} else {
			throw new DataNotFoundException("존재하지 않는 블로그 입니다. [" + blogName + "]");
		}
	}

	@Override
	public void following(Member member, String blogName) throws DataNotFoundException {
		System.out.println("BlogServiceImpl following()");
		
		MemberDao memberDao = this.getMemberDaoImplimentation();
		BlogDao blogDao = null;
		if (memberDao.memberNameExists(member.getName())) {
			blogDao = this.getBlogDaoImplimentation();
			if (blogDao.blogExists(blogName)) {
				blogDao.addFollowing(member, blogName);
			} else {
				throw new DataNotFoundException("존재하지 않는 블로그 입니다. [" + blogName + "]");
			}
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getName() + "]");
		}
	}

	@Override
	public void unfollow(Member member, String blogName) throws DataNotFoundException {
		System.out.println("BlogServiceImpl unfollow()");
		
		MemberDao memberDao = this.getMemberDaoImplimentation();
		BlogDao blogDao = null;
		if (memberDao.memberNameExists(member.getName())) {
			blogDao = this.getBlogDaoImplimentation();
			if (blogDao.blogExists(blogName)) {
				blogDao.cancelFollowing(member, blogName);
			} else {
				throw new DataNotFoundException("존재하지 않는 블로그 입니다. [" + blogName + "]");
			}
		} else {
			throw new DataNotFoundException("존재하지 않는 회원정보입니다. [" + member.getName() + "]");
		}
	}

	@Override
	public Blog[] getFollowingList(Member member) throws DataNotFoundException {
		System.out.println("BlogServiceImpl getFollowingList()");
		
		MemberDao memberDao = this.getMemberDaoImplimentation();
		if (memberDao.memberNameExists(member.getName())) {
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
		
		MemberDao memberDao = this.getMemberDaoImplimentation();
		BlogDao blogDao = null;
		Member tempMember = null;
		Blog tempBlog = null;
		if (memberDao.memberNameExists(member.getName())) {
			tempMember = memberDao.selectMemberAsName(member.getName());
			blogDao = this.getBlogDaoImplimentation();
			if (blogDao.blogExists(originBlogName)) {
				tempBlog = blogDao.selectBlog(originBlogName);
				if (tempBlog.getMemberName().equals(tempMember.getName())) {
					if (blogDao.blogExists(newBlogName)) {
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
	public void visitBlog(Member member, String blogName) throws DataNotFoundException {
		System.out.println("BlogServiceImpl visitBlog()");
		
		MemberDao memberDao = this.getMemberDaoImplimentation();
		BlogDao blogDao = null;
		if (memberDao.memberNameExists(member.getName())) {
			blogDao = this.getBlogDaoImplimentation();
			if (blogDao.blogExists(blogName)) {
				Blog blog = blogDao.selectBlog(blogName);
				if (!(member.getName()).equals(blog.getMemberName())) {
					blogDao.addVisitCount(blogName);
				}
			} else {
				throw new DataNotFoundException("존재하지 않는 블로그입니다. [" + blogName + "]");
			}
		} else {
			throw new DataNotFoundException("등록되지 않은 회원입니다. [" + member.getName() + "]");
		}
	}

	@Override
	public boolean checkValidBlogName(String blogName) {
		System.out.println("BlogServiceImpl checkValidBlogName()");
		BlogDao blogDao = this.getBlogDaoImplimentation();
		return blogDao.blogExists(blogName);
	}

	@Override
	public Blog[] getMyBlogs(Member member) throws DataNotFoundException {
		System.out.println("BlogServiceImpl getMyBlogs()");
		BlogDao blogDao = this.getBlogDaoImplimentation();
		MemberDao memberDao = this.getMemberDaoImplimentation();
		
		if (memberDao.memberNameExists(member.getName())) {
			return blogDao.selectMemberBlogs(member).toArray(new Blog[0]);
		} else {
			throw new DataNotFoundException("존재하지 않는 회원입니다. [" + member.getName() + "]");
		}
	}

}
