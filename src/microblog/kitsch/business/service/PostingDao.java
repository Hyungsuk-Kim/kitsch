package microblog.kitsch.business.service;

import java.util.List;
import java.util.Map;

import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.business.domain.PostingContent;

public interface PostingDao {
	public abstract int insertPosting(String blogId, Posting posting);
	public abstract void updatePosting(String blogId, Posting posting);
	public abstract void deletePosting(String blogId, Posting posting);
	public abstract Posting selectPosting(String blogId, int postingNum);
	public abstract List<Posting> selectAllPostings(String blogId);
	public abstract List<Posting> selectAllPostings();
	public abstract List<Posting> selectPostingList(Map<String, Object> searchInfo);
	public abstract int selectPostingCount(Map<String, Object> searchInfo);
	public abstract boolean postingExists(String blogId, int postingNum);
	public abstract int addReadCount(String blogId, int postingNum);
	public abstract void addLikes(Member member, String blogId, int postingNum);
	public abstract void cancelLikes(Member member, String blogId, int postingNum);
	public abstract PostingContent getContents(String blogId, int postingNum);
	public abstract void setContents(String blogId, int postingNum, PostingContent pContent);
	public abstract void reblog(Member member, String originBlogId, int postingNum, String targetBlogId);
	public abstract List<Posting> selectLikedPostings(Member member);
	public abstract List<Posting> selectReplyPostings(String blogId, int postingNum);
	public abstract void insertReply(String blogId, Posting posting);
}