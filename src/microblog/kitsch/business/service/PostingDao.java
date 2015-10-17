package microblog.kitsch.business.service;

import java.util.List;
import java.util.Map;

import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.business.domain.PostingContent;

public interface PostingDao {
	public abstract int insertPosting(String tableName, Posting posting);
	public abstract void updatePosting(String tableName, Posting posting);
	public abstract void deletePosting(String tableName, Posting posting);
	public abstract Posting selectPosting(String tableName, int postingNum);
	public abstract List<Posting> selectAllPostings(String tableName);
	public abstract List<Posting> selectAllPostings();
	public abstract List<Posting> selectPostingList(Map<String, Object> searchInfo);
	public abstract int selectPostingCount(Map<String, Object> searchInfo);
	public abstract boolean postingExists(String tableName, int postingNum);
	public abstract int addReadCount(String tableName, int postingNum);
	public abstract void addLikes(Member member, String tableName, int postingNum);
	public abstract void cancelLikes(Member member, String tableName, int postingNum);
	public abstract PostingContent getContents(String tableName, int postingNum);
	public abstract void setContents(String tableName, int postingNum, PostingContent pContent);
	public abstract void reblog(Member member, String originTableName, int postingNum, String targetTableName);
	public abstract List<Posting> selectLikedPostings(Member member);
	public abstract List<Posting> selectReplyPostings(String tableName, int postingNum);
	public abstract void insertReply(String tableName, Posting posting);
}