package microblog.kitsch.business.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.business.domain.PostingContent;
import microblog.kitsch.business.service.PostingDao;
import microblog.kitsch.helper.DataNotFoundException;
import microblog.kitsch.helper.KitschUtil;

public class PostingDaoImpl implements PostingDao {
	private static String ALL_COLUMNS = " num, title, writer, content_type, read_count, reg_date, "
										+ "likes, exposure, tags, ref, reply_step, reply_depth, "
										+ "reply_count, posting_type, reblog_count, reblog_option ";
	
	private Connection obtainConnection() throws SQLException {
    	//return DatabaseUtil_old.getConnection();
    	return DatabaseUtil.getConnection();
    }
	
	private void closeResources(Connection connection, Statement stmt, ResultSet rs){
		//DatabaseUtil_old.close(connection, stmt, rs);
		DatabaseUtil.close(connection, stmt, rs);
	}
	
	private void closeResources(Connection connection, Statement stmt){
		this.closeResources(connection, stmt, null);
	}
	
	private String getContentTable(Posting posting, String blogId){
		String contentTable = null;
		
		int contentType = posting.getContentType();
		if (contentType / 100 == PostingContent.MIXED_TYPE_CONTENT) {
			contentTable = "_mixed";
		} else if (contentType / 100 == PostingContent.SINGLE_TYPE_CONTENT) {
			contentTable = "_single";
		} else if (contentType == PostingContent.TEXT_CONTENT) {
			contentTable = "_text";
		}
		return blogId + contentTable;
	}
	
	private String getContentTable(int contentType, String blogId){
		String contentTable = null;
		if (contentType / 100 == PostingContent.MIXED_TYPE_CONTENT) {
			contentTable = "_mixed";
		} else if (contentType / 100 == PostingContent.SINGLE_TYPE_CONTENT) {
			contentTable = "_single";
		} else if (contentType == PostingContent.TEXT_CONTENT) {
			contentTable = "_text";
		}
		return blogId + contentTable;
	}
	
	private boolean checkValidContentType(int typeOfContent) {
		boolean result = false;
		
		switch (typeOfContent) {
		case PostingContent.MIXED_AUDIO_FILE_CONTENT:
		case PostingContent.MIXED_AUDIO_LINK_CONTENT:
		case PostingContent.MIXED_IMAGE_FILE_CONTENT:
		case PostingContent.MIXED_IMAGE_LINK_CONTENT:
		case PostingContent.MIXED_VIDEO_FILE_CONTENT:
		case PostingContent.MIXED_VIDEO_LINK_CONTENT:
		case PostingContent.SINGLE_AUDIO_FILE_CONTENT:
		case PostingContent.SINGLE_AUDIO_LINK_CONTENT:
		case PostingContent.SINGLE_IMAGE_FILE_CONTENT:
		case PostingContent.SINGLE_IMAGE_LINK_CONTENT:
		case PostingContent.SINGLE_VIDEO_FILE_CONTENT:
		case PostingContent.SINGLE_VIDEO_LINK_CONTENT:
		case PostingContent.TEXT_CONTENT:
			result = true;
			break;

		default:
			break;
		}
		
		return result;
	}

	@Override
	public int insertPosting(String blogId, Posting posting) {
		int affectedRow = 0;
		String contentTable = this.getContentTable(posting, blogId);
		String columns = "";
		PostingContent pContent = posting.getContents();
		
		if (contentTable.equals(blogId + "_mixed")) {
			columns = "?, ?";
		} else if (contentTable.equals(blogId + "_single")) {
			columns = "?";
		} else if (contentTable.equals(blogId + "_text")) {
			columns = "?";
		}
		
		int seqNum = 0;
		
		String query = "SELECT " + blogId + "_num_seq.NEXTVAL FROM dual";
		String sql = "INSERT INTO " + blogId + " (num, title, writer, content_type, reg_date, exposure, tags, ref, posting_type, reblog_option) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String sql2= "INSERT INTO " + contentTable + " VALUES (?, " + columns + ")";
		System.out.println("PostingDaoImpl insertPosting() first query : " + query);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				seqNum = rs.getInt(1);
			} else {
				rs.close();
				return affectedRow;
			}
			rs.close();
			pstmt.close();
			
			System.out.println("PostingDaoImpl insertPosting() second query : " + sql);
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, seqNum);
			pstmt.setString(2, posting.getTitle());
			pstmt.setString(3, posting.getWriter());
			pstmt.setInt(4, posting.getContentType());
			pstmt.setDate(5, KitschUtil.convertDateUtilToSql(new java.util.Date()));
			pstmt.setInt(6, posting.getExposure());
			pstmt.setString(7, posting.getTags());
			pstmt.setInt(8, seqNum);
			pstmt.setInt(9, posting.getPostingType());
			pstmt.setInt(10, posting.getReblogOption());
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("PostingDaoImpl insertPosting() third query : " + sql2);
			pstmt = connection.prepareStatement(sql2);
			pstmt.setInt(1, seqNum);
			if (contentTable.equals(blogId + "_mixed")) {
				pstmt.setString(2, pContent.getTextContent());
				pstmt.setString(3, KitschUtil.convertToString(pContent.getFilePaths(), Posting.PATH_DELIMITER, true));
			} else if (contentTable.equals(blogId + "_single")) {
				pstmt.setString(2, KitschUtil.convertToString(pContent.getFilePaths(), Posting.PATH_DELIMITER, true));
			} else if (contentTable.equals(blogId + "_text")) {
				pstmt.setString(2, pContent.getTextContent());
			}
			affectedRow = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.closeResources(connection, pstmt);
		}
		
		return affectedRow;
	}

	@Override
	public void updatePosting(String blogId, Posting posting) {
		PostingContent pContent = posting.getContents();
		
		String contentTable = this.getContentTable(posting, blogId);
		String updateComponents = "";
		
		if (contentTable.equals(blogId + "_mixed")) {
			updateComponents = "text_content=?, file_paths=?";
		} else if (contentTable.equals(blogId + "_single")) {
			updateComponents = "file_paths=?";
		} else if (contentTable.equals(blogId + "_text")) {
			updateComponents = "text_content=?";
		}
		
		String sql = "UPDATE " + blogId + " SET title=?, content_type=?, exposure=?, tags=?, reblog_option=? WHERE num=?";
		String sql2 = "UPDATE " + contentTable + " SET " + updateComponents + " WHERE num=?";
		System.out.println("PostingDaoImpl updatePosting() first query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.setString(1, posting.getTitle());
			pstmt.setInt(2, posting.getContentType());
			pstmt.setInt(3, posting.getExposure());
			pstmt.setString(4, posting.getTags());
			pstmt.setInt(5, posting.getReblogOption());
			pstmt.setInt(6, posting.getNum());
			pstmt.close();
			
			System.out.println("PostingDaoImpl updatePosting() second query : " + sql2);
			pstmt = connection.prepareStatement(sql2);
		
			if (contentTable.equals(blogId + "_mixed")) {
				pstmt.setString(1, pContent.getTextContent());
				pstmt.setString(2, KitschUtil.convertToString(pContent.getFilePaths(), Posting.PATH_DELIMITER, true));
				pstmt.setInt(3, posting.getNum());
			} else if (contentTable.equals(blogId + "_single")) {
				pstmt.setString(1, KitschUtil.convertToString(pContent.getFilePaths(), Posting.PATH_DELIMITER, true));
				pstmt.setInt(2, posting.getNum());
			} else if (contentTable.equals(blogId + "_text")) {
				pstmt.setString(1, pContent.getTextContent());
				pstmt.setInt(2, posting.getNum());
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.closeResources(connection, pstmt);
		}
	}

	@Override
	public void deletePosting(String blogId, Posting posting) {
		String contentTable = this.getContentTable(posting, blogId);
		
		String sql = "DELETE FROM " + blogId + " WHERE num=?";
		String sql2 = "DELETE FROM " + contentTable + " WHERE num=?";
		System.out.println("PostingDaoImpl deletePosting() first query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, posting.getNum());
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("PostingDaoImpl deletePosting() second query : " + sql2);
			pstmt = connection.prepareStatement(sql2);
			pstmt.setInt(1, posting.getContents().getNum());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.closeResources(connection, pstmt);
		}
	}

	@Override
	public Posting selectPosting(String blogId, int postingNum) {
		Posting selectedPosting = null;
		PostingContent pContent = null;
		String contentTable = null;
		
		String sql = "SELECT * FROM " + blogId + " WHERE num=?";
		System.out.println("PostingDaoImpl selectPosting() first query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, postingNum);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String writer = rs.getString("writer");
				int contentType = rs.getInt("content_type");
				int readCount = rs.getInt("read_count");
				java.util.Date regDate = KitschUtil.convertDateSqlToUtil(rs.getDate("reg_date"));
				int likes = rs.getInt("likes");
				int exposure = rs.getInt("exposure");
				String tags = rs.getString("tags");
				int ref = rs.getInt("ref");
				int replyStep = rs.getInt("reply_step");
				int replyDepth = rs.getInt("reply_depth");
				int replyCount = rs.getInt("reply_count");
				int postingType = rs.getInt("posting_type");
				int reblogCount = rs.getInt("reblog_count");
				int reblogOption = rs.getInt("reblog_option");
				
				contentTable = this.getContentTable(contentType, blogId);
				String sql2 = "SELECT * FROM " + contentTable + " WHERE num=?";
				System.out.println("PostingDaoImpl selectPosting() second query : " + sql2);
				pstmt2 = connection.prepareStatement(sql2);
				pstmt2.setInt(1, num);
				rs2 = pstmt2.executeQuery();
				
				if (rs2.next()) {
					int pNum = rs2.getInt("num");
					if (contentTable.equals(blogId + "_mixed")) {
						String textContents = rs2.getString("text_content");
						String filePaths = rs2.getString("file_paths");
						pContent = new PostingContent(pNum, textContents, KitschUtil.convertToStringArray(filePaths, Posting.PATH_DELIMITER, false));
						
					} else if (contentTable.equals(blogId + "_single")) {
						String filePaths = rs2.getString("file_path");
						pContent = new PostingContent(pNum, KitschUtil.convertToStringArray(filePaths, Posting.PATH_DELIMITER, false));
						
					} else if (contentTable.equals(blogId + "_text")) {
						String textContent = rs2.getString("text_contents");
						pContent = new PostingContent(pNum, textContent);
					}
					selectedPosting = new Posting(num, title, writer, pContent, contentType,
							readCount, regDate, likes, exposure, tags, ref, replyStep, 
							replyDepth, replyCount, postingType, reblogCount, reblogOption);
				}
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			this.closeResources(null, pstmt2, rs2);
			this.closeResources(connection, pstmt, rs);
		}
		
		return selectedPosting;
	}

	@Override
	public List<Posting> selectAllPostings(String blogId) {
		List<Posting> pList = new ArrayList<Posting>();
		Posting selectedPosting = null;
		PostingContent pContent = null;
		String contentTable = null;
		
		String sql = "SELECT * FROM " + blogId;
		System.out.println("PostingDaoImpl selectAllPostings() first query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String writer = rs.getString("writer");
				int contentType = rs.getInt("content_type");
				int readCount = rs.getInt("read_count");
				java.util.Date regDate = KitschUtil.convertDateSqlToUtil(rs.getDate("reg_date"));
				int likes = rs.getInt("likes");
				int exposure = rs.getInt("exposure");
				String tags = rs.getString("tags");
				int ref = rs.getInt("ref");
				int replyStep = rs.getInt("reply_step");
				int replyDepth = rs.getInt("reply_depth");
				int replyCount = rs.getInt("reply_count");
				int postingType = rs.getInt("posting_type");
				int reblogCount = rs.getInt("reblog_count");
				int reblogOption = rs.getInt("reblog_option");
				
				contentTable = this.getContentTable(contentType, blogId);
				String sql2 = "SELECT * FROM " + contentTable + " WHERE num=?";
				System.out.println("PostingDaoImpl selectAllPostings() second query : " + sql2);
				pstmt2 = connection.prepareStatement(sql2);
				pstmt2.setInt(1, num);
				rs2 = pstmt2.executeQuery();
				
				while (rs2.next()) {
					int pNum = rs2.getInt("num");
					if (contentTable.equals(blogId + "_mixed")) {
						String textContents = rs2.getString("text_content");
						String filePaths = rs2.getString("file_paths");
						pContent = new PostingContent(pNum, textContents, KitschUtil.convertToStringArray(filePaths, Posting.PATH_DELIMITER, false));
						
					} else if (contentTable.equals(blogId + "_single")) {
						String filePaths = rs2.getString("file_paths");
						pContent = new PostingContent(pNum, KitschUtil.convertToStringArray(filePaths, Posting.PATH_DELIMITER, false));
						
					} else if (contentTable.equals(blogId + "_text")) {
						String textContent = rs2.getString("text_content");
						pContent = new PostingContent(pNum, textContent);
					}
					selectedPosting = new Posting(num, title, writer, pContent, contentType,
							readCount, regDate, likes, exposure, tags, ref, replyStep, 
							replyDepth, replyCount, postingType, reblogCount, reblogOption);
					
					pList.add(selectedPosting);
				}
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.closeResources(null, pstmt2, rs2);
			this.closeResources(connection, pstmt, rs);
		}
		return pList;
	}

	@Override
	public List<Posting> selectAllPostings() {
		List<Posting> pList = new ArrayList<Posting>();
		String blogId = null;
		Posting selectedPosting = null;
		PostingContent pContent = null;
		String contentTable = null;
		
		String sql = "SELECT table_name FROM tabs "
				+ " WHERE table_name NOT IN ('BLOG', 'MEMBER', 'QNA', 'KITSCH', 'LIKES', 'REBLOG', 'FOLLOW', 'MESSAGE_BOX') "
				+ " AND NOT LIKE '%_MIXED' AND NOT LIKE '%_SINGLE' AND NOT LIKE '%_TEXT'";
		System.out.println("PostingDaoImpl selectAllPostings() : first query :" + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				blogId = rs.getString(1);
				
				String sql2 = "SELECT * FROM " + blogId;
				System.out.println("PostingDaoImpl selectAllPostings() second query : " + sql2);
				pstmt2 = connection.prepareStatement(sql2);
				rs2 = pstmt2.executeQuery();
				
				while (rs2.next()) {
					int num = rs2.getInt("num");
					String title = rs2.getString("title");
					String writer = rs2.getString("writer");
					int contentType = rs2.getInt("content_type");
					int readCount = rs2.getInt("read_count");
					java.util.Date regDate = KitschUtil.convertDateSqlToUtil(rs2.getDate("reg_date"));
					int likes = rs2.getInt("likes");
					int exposure = rs2.getInt("exposure");
					String tags = rs2.getString("tags");
					int ref = rs2.getInt("ref");
					int replyStep = rs2.getInt("reply_step");
					int replyDepth = rs2.getInt("reply_depth");
					int replyCount = rs2.getInt("reply_count");
					int postingType = rs2.getInt("posting_type");
					int reblogCount = rs2.getInt("reblog_count");
					int reblogOption = rs2.getInt("reblog_option");
					
					contentTable = this.getContentTable(contentType, blogId);
					String sql3 = "SELECT * FROM " + contentTable + " WHERE num=?";
					System.out.println("PostingDaoImpl selectAllPostings() third query : " + sql3);
					pstmt3 = connection.prepareStatement(sql3);
					pstmt3.setInt(1, num);
					rs3 = pstmt3.executeQuery();
					
					while (rs3.next()) {
						int pNum = rs3.getInt("num");
						if (contentTable.equals(blogId + "_mixed")) {
							String textContents = rs3.getString("text_content");
							String filePaths = rs3.getString("file_paths");
							pContent = new PostingContent(pNum, textContents, KitschUtil.convertToStringArray(filePaths, Posting.PATH_DELIMITER, false));
						} else if (contentTable.equals(blogId + "_single")) {
							String filePaths = rs3.getString("file_paths");
							pContent = new PostingContent(pNum, KitschUtil.convertToStringArray(filePaths, Posting.PATH_DELIMITER, false));
						} else if (contentTable.equals(blogId + "_text")) {
							String textContent = rs3.getString("text_content");
							pContent = new PostingContent(pNum, textContent);
						}
						selectedPosting = new Posting(num, title, writer, pContent, contentType,
								readCount, regDate, likes, exposure, tags, ref, replyStep, 
								replyDepth, replyCount, postingType, reblogCount, reblogOption);
						
						pList.add(selectedPosting);
					}
				}
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.closeResources(null, pstmt3, rs3);
			this.closeResources(null, pstmt2, rs2);
			this.closeResources(connection, pstmt, rs);
		}
		return pList;
	}

	@Override
	public List<Posting> selectPostingList(Map<String, Object> searchInfo) {
		List<Posting> pList = new ArrayList<Posting>();
		Posting selectedPosting = null;
		PostingContent pContent = null;
		String blogId = null;
		
		String orderBySyntax = "";
		String whereSyntax = "";
		String specifyBlog = "";
		
		if(searchInfo == null) {
			return pList;
		}
		
		for (String temp : searchInfo.keySet()){
			System.out.println(searchInfo.get(temp));
		}
		
		
		String target = (String) searchInfo.get("target");
		String searchType = (String) searchInfo.get("searchType");
		String searchText = (String) searchInfo.get("searchText");
		String blogName = (String) searchInfo.get("blogName");
		String sortingOption = null;
		int startRow = (Integer) searchInfo.get("startRow");
		int endRow = (Integer) searchInfo.get("endRow");
		int typeOfContent = 0;
		String searchTextKeyword = "";
		boolean selectAsContentType = false;
		
		if (target != null && target.trim().length() != 0) {
			if (target.equals("all") || target.equals("posting")) {
			}
		}
		
		if (searchInfo.containsKey("sortingOption")) {                                             
			sortingOption = (String) searchInfo.get("sortingOption");
			if (sortingOption != null && sortingOption.trim().length() != 0) {
				if (sortingOption.equals("recently")) {
					orderBySyntax = " ORDER BY reg_date DESC, reblog DESC, likes DESC, read_count DESC";
				} else if (sortingOption.equals("correctly")) {
					System.out.println("#################################################################");
					System.out.println("This function is not implimented yet. PostingDaoImpl at 600 line.");
					System.out.println("#################################################################");
					orderBySyntax = " ORDER BY reblog DESC, likes DESC, read_count DESC, reg_date DESC";
				} else if (sortingOption.equals("popularity")) {
					orderBySyntax = " ORDER BY reblog DESC, likes DESC, read_count DESC, reg_date DESC";
				} else if (sortingOption.equals("inSpecifyBlog")) {
					orderBySyntax = " ORDER BY reg_date DESC, num DESC";
				}
			}
		}
		
		if (searchInfo.containsKey("contentType")) {
			typeOfContent = (Integer) searchInfo.get("contentType");
			selectAsContentType = this.checkValidContentType(typeOfContent);
		}
		
		if (searchText != null) {
			searchText.trim();
			String searchTextTemp1 = searchText.replace("@", "@@");
			String searchTextTemp2 = searchTextTemp1.replace("_", "@_");
			String searchTextTemp3 = searchTextTemp2.replace("%", "@%");
			searchTextKeyword = "%" + searchTextTemp3.replace(' ', '%') + "%";
			searchTextKeyword = searchTextKeyword.toUpperCase();
		}
		
		if (searchInfo.containsKey("blogName") && blogName != null && blogName.trim().length() != 0) {
			specifyBlog = " AND table_name=?";
		}
		
		String sql = "SELECT table_name FROM tabs WHERE table_name NOT IN ('BLOG', 'MEMBER', 'QNA', 'KITSCH', 'LIKES', 'LIKE', 'REBLOG', 'MESSAGE_BOX', 'FOLLOW') "
				+ " AND NOT LIKE '%_MIXED' AND NOT LIKE '%_SINGLE' AND NOT LIKE '%_TEXT'" + specifyBlog;
		System.out.println("PostingDaoImpl getPostingList() first query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			
			if (searchInfo.containsKey("blogName") && blogName != null && blogName.trim().length() != 0) {
				pstmt.setString(1, blogName);
			}
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				blogId = rs.getString(1);
				
				if (selectAsContentType) {
					whereSyntax = " WHERE content_type=?";
				} else if (searchType != null && searchType.trim().length() != 0) {
					if (searchType.equals("all")) {
						whereSyntax = " WHERE num IN (SELECT num FROM " + blogId + "_text WHERE UPPER(text_content) LIKE ? ESCAPE '@') OR "
											+ " num IN (SELECT num FROM " + blogId + "_mixed WHERE UPPER(text_content) LIKE ? ESCAPE '@') OR "
											+ " UPPER(title) LIKE ? OR UPPER(writer) LIKE ? OR UPPER(tags) LIKE ? ESCAPE '@'";
					} else if (searchType.equals("title")) {
						whereSyntax = " WHERE UPPER(title) LIKE ? ESCAPE '@'";
					} else if (searchType.equals("writer")) {
						whereSyntax = " WHERE UPPER(writer) LIKE ? ESCAPE '@'";
					} else if (searchType.equals("contents")) {
						whereSyntax = " WHERE num IN (SELECT num FROM " + blogId + "_text WHERE UPPER(text_content) LIKE ? ESCAPE '@')";
					} else if (searchType.equals("tags")) {
						whereSyntax = " WHERE UPPER(tags) LIKE ? ESCAPE '@'";
					} else { /*return pList;*/ }
				} else { /*return pList;*/ }
			
			
				String sql2 = "SELECT * FROM ("
								+ "SELECT ROWNUM AS row_num, " + ALL_COLUMNS + " FROM ("
									+ "SELECT * FROM " + blogId + whereSyntax + orderBySyntax
								+ ")"
							+ ") WHERE row_num BETWEEN ? AND ?";
				System.out.println("PostingDaoImpl getPostingList() second query : " + sql2);
				pstmt2 = connection.prepareStatement(sql2);
				
				if (selectAsContentType){
					pstmt2.setInt(1, typeOfContent);
					pstmt2.setInt(2, startRow);
					pstmt2.setInt(3, endRow);
				} else if (searchType.equals("all")) {
						pstmt2.setString(1, searchTextKeyword);
						pstmt2.setString(2, searchTextKeyword);
						pstmt2.setString(3, searchTextKeyword);
						pstmt2.setString(4, searchTextKeyword);
						pstmt2.setString(5, searchTextKeyword);
						pstmt2.setInt(6, startRow);
						pstmt2.setInt(7, endRow);
				} else if (searchType.equals("title")) {
					pstmt2.setString(1, searchTextKeyword);
					pstmt2.setInt(2, startRow);
					pstmt2.setInt(3, endRow);
				} else if (searchType.equals("writer")) {
					pstmt2.setString(1, searchTextKeyword);
					pstmt2.setInt(2, startRow);
					pstmt2.setInt(3, endRow);
				} else if (searchType.equals("contents")) {
					pstmt2.setString(1, searchTextKeyword);
					pstmt2.setInt(2, startRow);
					pstmt2.setInt(3, endRow);
				} else if (searchType.equals("tags")) {
					pstmt2.setString(1, searchTextKeyword);
					pstmt2.setInt(2, startRow);
					pstmt2.setInt(3, endRow);
				}
				
				rs2 = pstmt2.executeQuery();
				
				while (rs2.next()) {
					int num = rs2.getInt("num");
					String title = rs2.getString("title");
					String writer = rs2.getString("writer");
					int contentType = rs2.getInt("content_type");
					int readCount = rs2.getInt("read_count");
					java.util.Date regDate = KitschUtil.convertDateSqlToUtil(rs2.getDate("reg_date"));
					int likes = rs2.getInt("likes");
					int exposure = rs2.getInt("exposure");
					String tags = rs2.getString("tags");
					int ref = rs2.getInt("ref");
					int replyStep = rs2.getInt("reply_step");
					int replyDepth = rs2.getInt("reply_depth");
					int replyCount = rs2.getInt("reply_count");
					int postingType = rs2.getInt("posting_type");
					int reblogCount = rs2.getInt("reblog_count");
					int reblogOption = rs2.getInt("reblog_option");
					
					String contentTable = this.getContentTable(contentType, blogId);
					String sql3 = "SELECT * FROM " + contentTable + " WHERE num=?";
					System.out.println("PostingDaoImpl getPostingList() third query : " + sql3);
					pstmt3 = connection.prepareStatement(sql3);
					pstmt3.setInt(1, num);
					rs3 = pstmt3.executeQuery();
					
					while (rs3.next()) {
						int pNum = rs3.getInt("num");
						if (contentTable.equals(blogId + "_mixed")) {
							String textContents = rs3.getString("text_content");
							String filePaths = rs3.getString("file_paths");
							pContent = new PostingContent(pNum, textContents, KitschUtil.convertToStringArray(filePaths, Posting.PATH_DELIMITER, false));
						} else if (contentTable.equals(blogId + "_single")) {
							String filePaths = rs3.getString("file_paths");
							pContent = new PostingContent(pNum, KitschUtil.convertToStringArray(filePaths, Posting.PATH_DELIMITER, false));
						} else if (contentTable.equals(blogId + "_text")) {
							String textContent = rs3.getString("text_content");
							pContent = new PostingContent(pNum, textContent);
						}
						selectedPosting = new Posting(num, title, writer, pContent, contentType,
								readCount, regDate, likes, exposure, tags, ref, replyStep, 
								replyDepth, replyCount, postingType, reblogCount, reblogOption);
						
						pList.add(selectedPosting);
					}
				}
			}
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.closeResources(null, pstmt3, rs3);
			this.closeResources(null, pstmt2, rs2);
			this.closeResources(connection, pstmt, rs);
		}
		
		return pList;
	}

	@Override
	public int selectPostingCount(Map<String, Object> searchInfo) {
		int selectedCount = 0;
		
		String whereSyntax = "";
		String specifyBlog = "";
		String blogId = null;
		String contentTable = null;
		
		if(searchInfo == null) {
			return selectedCount;
		}
		
		String target = (String) searchInfo.get("target");
		String searchType = (String) searchInfo.get("searchType");
		String searchText = (String) searchInfo.get("searchText");
		String blogName = (String) searchInfo.get("blogName");
		int typeOfContent = 0;
		String searchTextKeyword = "";
		
		boolean selectAsContentType = false;
		
		if (searchInfo.containsKey("contentType")) {
			typeOfContent = (Integer) searchInfo.get("contentType");
			selectAsContentType = this.checkValidContentType(typeOfContent);
		}
		
		if (searchText != null) {
			searchText.trim();
			String searchTextTemp1 = searchText.replace("@", "@@");
			String searchTextTemp2 = searchTextTemp1.replace("_", "@_");
			String searchTextTemp3 = searchTextTemp2.replace("%", "@%");
			searchTextKeyword = "%" + searchTextTemp3.replace(' ', '%') + "%";
			searchTextKeyword = searchTextKeyword.toUpperCase();
		}
		
		if (target != null && target.trim().length() != 0) {
			if (target.equals("all") || target.equals("posting")) {
				
			} else { /*return selectedCount;*/ }
		} else { /*return selectedCount;*/ }
		
		
		String sql = "SELECT table_name FROM tabs WHERE table_name NOT IN ('BLOG', 'MEMBER', 'QNA', 'KITSCH', 'LIKES', 'LIKE', 'REBLOG', 'MESSAGE_BOX', 'FOLLOW') "
				+ " AND NOT LIKE '%_MIXED' AND NOT LIKE '%_SINGLE' AND NOT LIKE '%_TEXT'" + specifyBlog;
		System.out.println("PostingDaoImpl getPostingCount() first query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			
			if (searchInfo.containsKey("blogName") && blogName != null && blogName.trim().length() != 0) {
				pstmt.setString(1, blogName);
			}
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				blogId = rs.getString(1);
				
				if (selectAsContentType) {
					whereSyntax = " WHERE content_type=?";
					if (searchInfo.containsKey("blogName") && blogName != null && blogName.trim().length() != 0) {
						specifyBlog = " AND table_name=?";
					}
					if (searchType.equals("all")) {
						whereSyntax = " WHERE num IN (SELECT num FROM " + blogId + "_text WHERE UPPER(text_content) LIKE ? ESCAPE '@') OR "
								+ "num IN (SELECT num FROM " + blogId + "_mixed WHERE UPPER(text_content) LIKE ? ESCAPE '@') OR"
								+ " UPPER(title) LIKE ? OR UPPER(writer) LIKE ? OR UPPER(tags) LIKE ? ESCAPE '@'";
					} else if (searchType.equals("title")) {
						whereSyntax = " WHERE UPPER(title) LIKE ? ESCAPE '@'";
					} else if (searchType.equals("writer")) {
						whereSyntax = " WHERE UPPER(writer) LIKE ? ESCAPE '@'";
					} else if (searchType.equals("contents")) {
						whereSyntax = " WHERE num IN (SELECT num FROM " + contentTable + " WHERE UPPER(text_content) LIKE ? ESCAPE '@')";
					} else if (searchType.equals("tags")) {
						whereSyntax = " WHERE UPPER(tags) LIKE ? ESCAPE '@'";
					} else { /*return selectedCount;*/ }
				} else { /*return selectedCount;*/ }
				
				String sql2 = "SELECT COUNT(*) FROM " + blogId + whereSyntax;
				System.out.println("PostingDaoImpl getPostingCount() second query : " + sql2);
				
				pstmt2 = connection.prepareStatement(sql2);
				
				if (selectAsContentType) {
						pstmt2.setInt(1, typeOfContent);
				} else if (searchType.equals("all")) {
						pstmt2.setString(1, searchTextKeyword);
						pstmt2.setString(2, searchTextKeyword);
						pstmt2.setString(3, searchTextKeyword);
						pstmt2.setString(4, searchTextKeyword);
						pstmt2.setString(5, searchTextKeyword);
				} else if (searchType.equals("title")) {
					pstmt2.setString(1, searchTextKeyword);
				} else if (searchType.equals("writer")) {
					pstmt2.setString(1, searchTextKeyword);
				} else if (searchType.equals("contents")) {
					pstmt2.setString(1, searchTextKeyword);
				} else if (searchType.equals("tags")) {
					pstmt2.setString(1, searchTextKeyword);
				}
				
				rs2 = pstmt2.executeQuery();
				
				if (rs2.next()) {
					selectedCount += rs2.getInt(1);
				}
			}
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.closeResources(null, pstmt2, rs2);
			this.closeResources(connection, pstmt, rs);
		}
		
		return selectedCount;
	}

	@Override
	public boolean postingExists(String blogId, int postingNum) {
		boolean result = false;
		
		String sql = "SELECT num FROM " + blogId + " WHERE num=?";
		System.out.println("PostingDaoImpl PostingExists() : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, postingNum);
			rs = pstmt.executeQuery();
			result = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return result;
	}

	@Override
	public int addReadCount(String blogId, int postingNum) {
		int result = 0;
		String sql ="UPDATE " + blogId + " SET read_count=read_count+1 WHERE num=?";
		System.out.println("PostingDaoImpl addReadCount() : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, postingNum);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt);
		}
		return result;
	}

	@Override
	public void addLikes(Member member, String blogId, int postingNum) {
		String sql = "UPDATE " + blogId + " SET likes=likes+1 WHERE num=?";
		String sql2 = "INSERT INTO likes (email, blog_id, posting_num) VALUES (?, ?, ?)";
		System.out.println("PostingDaoImpl addLikes() : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, postingNum);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = connection.prepareStatement(sql2);
			System.out.println("PostingDaoImpl addLikes() : " + sql2);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, blogId);
			pstmt.setInt(3, postingNum);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.closeResources(connection, pstmt);
		}
	}

	@Override
	public void cancelLikes(Member member, String blogId, int postingNum) {
		String sql = "UPDATE " + blogId + " SET likes=likes-1 WHERE num=?";
		String sql2 = "DELETE FROM likes WHERE email=? AND blog_id=? AND posting_num=?";
		System.out.println("PostingDaoImpl cancelLikes() first query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, postingNum);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = connection.prepareStatement(sql2);
			System.out.println("PostingDaoImpl cancelLikes() second query : " + sql2);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, blogId);
			pstmt.setInt(3, postingNum);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.closeResources(connection, pstmt);
		}
	}

	@Override
	public PostingContent getContents(String blogId, int postingNum) {
		PostingContent pContent = null;
		String contentTable = this.getContentTable(this.selectPosting(blogId, postingNum), blogId);
		
		String sql = "SELECT * FROM " + contentTable + " WHERE num=?";
		System.out.println("PostingDaoImpl getContents() : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, postingNum);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if (contentTable.equals(blogId + "_mixed")) {
					String filePaths = rs.getString("file_paths");
					pContent = new PostingContent(rs.getInt("num"), rs.getString("text_content"), KitschUtil.convertToStringArray(filePaths, Posting.PATH_DELIMITER, false));
				} else if (contentTable.equals(blogId + "_single")) {
					String filePaths = rs.getString("file_path");
					pContent = new PostingContent(rs.getInt("num"), KitschUtil.convertToStringArray(filePaths, Posting.PATH_DELIMITER, false));
				} else if (contentTable.equals(blogId + "_text")) {
					pContent = new PostingContent(rs.getInt("posting_num"), rs.getString("text_content"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return pContent;
	}

	@Override
	public void setContents(String blogId, int postingNum, PostingContent pContent) {
		String contentTable = this.getContentTable(this.selectPosting(blogId, postingNum), blogId);
		String contents = null;
		
		if (contentTable.equals(blogId + "_mixed")) {
			contents = "text_content=?, file_paths=?";
		} else if (contentTable.equals(blogId + "_single")) {
			contents = "file_paths=?";
		} else if (contentTable.equals(blogId + "_text")) {
			contents = "text_content=?";
		}
		
		String sql = "UPDATE " + contentTable + " SET " + contents + " WHERE num=?";
		System.out.println("PostingDaoImpl setContents() : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			
			if (contentTable.equals(blogId + "_mixed")) {
				pstmt.setString(1, pContent.getTextContent());
				pstmt.setString(2, KitschUtil.convertToString(pContent.getFilePaths(), Posting.PATH_DELIMITER, true));
				pstmt.setInt(3, postingNum);
			} else if (contentTable.equals(blogId + "_single")) {
				pstmt.setString(1, KitschUtil.convertToString(pContent.getFilePaths(), Posting.PATH_DELIMITER, true));
				pstmt.setInt(2, postingNum);
			} else if (contentTable.equals(blogId + "_text")) {
				pstmt.setString(1, pContent.getTextContent());
				pstmt.setInt(2, postingNum);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt);
		}
	}

	@Override
	public void reblog(Member member, String originBlogId, int postingNum, String targetBlogId) {
		
		String sql = "INSERT INTO reblog (email, origin_blog_id, target_blog_id, origin_posting_num) VALUES (?, ?, ?, ?)";
		System.out.println("PostingDaoImpl reblog() : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			
			Posting originPosting = this.selectPosting(originBlogId, postingNum);
			if (originPosting == null) {
				throw new SQLException("원본 포스팅이 존재하지 않습니다.");
			}
			originPosting.setWriter(originPosting.getWriter() + " >> " + member.getName());
			this.insertPosting(targetBlogId, originPosting);
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, originBlogId);
			pstmt.setString(3, targetBlogId);
			pstmt.setInt(4, postingNum);
			pstmt.executeUpdate();
			if (this.insertPosting(targetBlogId, originPosting) == 0) {
				throw new SQLException();
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.closeResources(connection, pstmt);
		}
	}

	@Override
	public List<Posting> selectLikedPostings(Member member) {
		String sql = "SELECT * FROM likes WHERE email=?";
		System.out.println("PostingDaoImpl selectLikedPosting() first query : " + sql);

		List<Posting> pList = new ArrayList<Posting>();
		Posting selectedPosting = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			Savepoint sp = connection.setSavepoint();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String blogId = rs.getString("blog_id");
				int postingNum = rs.getInt("posting_num");
				try {
					selectedPosting = this.selectPosting(blogId, postingNum);
					if (selectedPosting == null) {
						throw new DataNotFoundException("해당 포스팅을 찾을 수 없습니다.");
					}
				} catch (DataNotFoundException dne) {
					connection.rollback(sp);
					throw new SQLException(dne);
				} finally {
					connection.setAutoCommit(true);
					this.closeResources(connection, pstmt, rs);
				}
				pList.add(selectedPosting);
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.closeResources(connection, pstmt, rs);
		}
		
		return pList;
	}

	@Override
	public List<Posting> selectReplyPostings(String blogId, int postingNum) {
		String sql = "SELECT * FROM " + blogId + " WHERE posting_type=?";
		System.out.println("PostingDaoImpl selectReplyPostings() query : " + sql);
		
		List<Posting> pList = new ArrayList<Posting>();
		Posting selectedPosting = null;
		PostingContent pContent = null;
		String contentTable = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, Posting.REPLY_TYPE_POSTING);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String writer = rs.getString("writer");
				int contentType = rs.getInt("content_type");
				int readCount = rs.getInt("read_count");
				java.util.Date regDate = KitschUtil.convertDateSqlToUtil(rs.getDate("reg_date"));
				int likes = rs.getInt("likes");
				int exposure = rs.getInt("exposure");
				String tags = rs.getString("tags");
				int ref = rs.getInt("ref");
				int replyStep = rs.getInt("reply_step");
				int replyDepth = rs.getInt("reply_depth");
				int replyCount = rs.getInt("reply_count");
				int postingType = rs.getInt("posting_type");
				int reblogCount = rs.getInt("reblog_count");
				int reblogOption = rs.getInt("reblog_option");
				
				contentTable = this.getContentTable(contentType, blogId);
				String sql2 = "SELECT * FROM " + contentTable + " WHERE num=?";
				System.out.println("PostingDaoImpl selectReplyPostings() : " + sql2);
				
				pstmt2 = connection.prepareStatement(sql2);
				pstmt2.setString(1, blogId);
				pstmt2.setInt(2, num);
				rs2 = pstmt2.executeQuery();
				
				if (rs2.next()) {
					int pNum = rs2.getInt("num");
					if (contentTable.equals(blogId + "_mixed")) {
						String textContents = rs2.getString("text_content");
						String filePaths = rs2.getString("file_paths");
						pContent = new PostingContent(pNum, textContents, KitschUtil.convertToStringArray(filePaths, Posting.PATH_DELIMITER, false));
						
					} else if (contentTable.equals(blogId + "_single")) {
						String filePaths = rs2.getString("file_path");
						pContent = new PostingContent(pNum, KitschUtil.convertToStringArray(filePaths, Posting.PATH_DELIMITER, false));
						
					} else if (contentTable.equals(blogId + "_text")) {
						String textContent = rs2.getString("text_contents");
						pContent = new PostingContent(pNum, textContent);
					}
					selectedPosting = new Posting(num, title, writer, pContent, contentType,
							readCount, regDate, likes, exposure, tags, ref, replyStep, 
							replyDepth, replyCount, postingType, reblogCount, reblogOption);
					
					pList.add(selectedPosting);
				}
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.closeResources(null, pstmt2, rs2);
			this.closeResources(connection, pstmt, rs);
		}
		
		return pList;
	}

	@Override
	public void insertReply(String blogId, Posting posting) {
		String contentTable = this.getContentTable(posting, blogId);
		String contents = "";
		PostingContent pContent = posting.getContents();
		
		if (contentTable.equals(blogId + "_mixed")) {
			contents = "?, ?";
		} else if (contentTable.equals(blogId + "_single")) {
			contents = "?";
		} else if (contentTable.equals(blogId + "_text")) {
			contents = "?";
		}
		int seqNum = 0;
		String query = "SELECT " + blogId + "_num_seq.NEXTVAL FROM dual";
		String sql = "INSERT INTO " + blogId + " (num, writer, content_type, reg_date, exposure, ref, reply_step, reply_depth, posting_type, reblog_option) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String sql2= "INSERT INTO " + contentTable + " VALUES (?, " + contents + ")";
		
		String sql3 = "UPDATE " + blogId + " SET reply_step=reply_step+1 WHERE ref=? AND reply_step > ?";
		String sql4 = "UPDATE " + blogId + " SET reply_count=reply_count+1 WHERE num=?";
		System.out.println("PostingDaoImpl insertReply() first query : " + query);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				seqNum = rs.getInt(1);
			}
			
			rs.close();
			pstmt.close();
			
			System.out.println("PostingDaoImpl insertReply() second query : " + sql);
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, seqNum);
			pstmt.setString(2, posting.getWriter());
			pstmt.setInt(3, posting.getContentType());
			pstmt.setDate(4, KitschUtil.convertDateUtilToSql(new java.util.Date()));
			pstmt.setInt(5, posting.getExposure());
			pstmt.setInt(6, posting.getRef());
			pstmt.setInt(7, posting.getReplyStep() + 1);
			pstmt.setInt(7, posting.getReplyDepth() + 1);
			pstmt.setInt(9, posting.getPostingType());
			pstmt.setInt(10, posting.getReblogOption());
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("PostingDaoImpl insertPosting() third query : " + sql2);
			pstmt = connection.prepareStatement(sql2);
			pstmt.setInt(1, seqNum);
			if (contentTable.equals(blogId + "_mixed")) {
				pstmt.setString(2, pContent.getTextContent());
				pstmt.setString(3, KitschUtil.convertToString(pContent.getFilePaths(), Posting.PATH_DELIMITER, true));
			} else if (contentTable.equals(blogId + "_single")) {
				pstmt.setString(2, KitschUtil.convertToString(pContent.getFilePaths(), Posting.PATH_DELIMITER, true));
			} else if (contentTable.equals(blogId + "_text")) {
				pstmt.setString(2, pContent.getTextContent());
			}
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("PostingDaoImpl insertPosting() fourth query : " + sql3);
			pstmt = connection.prepareStatement(sql3);
			pstmt.setInt(1, posting.getRef());
			pstmt.setInt(2, posting.getReplyStep());
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("PostingDaoImpl insertPosting() fifth query : " + sql4);
			pstmt = connection.prepareStatement(sql4);
			pstmt.setInt(1, posting.getRef());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.closeResources(connection, pstmt);
		}
	}

}
