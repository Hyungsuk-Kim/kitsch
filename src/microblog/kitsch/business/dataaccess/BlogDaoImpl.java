package microblog.kitsch.business.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import microblog.kitsch.business.domain.Blog;
import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.domain.Posting;
import microblog.kitsch.business.domain.PostingContent;
import microblog.kitsch.business.service.BlogDao;
import microblog.kitsch.helper.KitschUtil;

public class BlogDaoImpl implements BlogDao{

	private Connection obtainConnection() throws SQLException{
		return DatabaseUtil_old.getConnection();
		//return DatabaseUtil.getConnection();
	}
	
	private void closeResources(Connection connection, Statement stmt, ResultSet rs){
		DatabaseUtil_old.close(connection, stmt, rs);
		//DatabaseUtil.close(connection, stmt, rs);
	}
	
	private void closeResources(Connection connection, Statement stmt){
		this.closeResources(connection, stmt, null);
	}
	
	@Override
	public void insertBlog(Blog blog) {
		String blogId = Blog.DEFAULT_TABLE_NAME_PREFIX;
		
		String query = "SELECT blog_id_seq.NEXTVAL FROM dual";
		System.out.println("BlogDaoImpl insertBlog() first query : " + query);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				blogId += rs.getString(1);
			}
			rs.close();
			pstmt.close();
			
			String sql;
			if (blog.getHeaderImage() == null) {
				sql = "INSERT INTO blog (blog_id, email, blog_name, background_color, blog_layout, reg_date) "
						+ " VALUES (?, ?, ?, ?, ?, ?)";
			} else {
				sql = "INSERT INTO blog (blog_id, email, blog_name, header_image, blog_layout, reg_date) "
						+ " VALUES (?, ?, ?, ?, ?, ?)";
			}
			System.out.println("BlogDaoImpl insertBlog() second query : " + sql);
			
			String sql2 = "CREATE TABLE " + blogId + "("
							+ "num INTEGER,"
							+ "title VARCHAR2(420),"
							+ "writer VARCHAR2(60) NOT NULL,"
							+ "content_type NUMBER(3) NOT NULL,"
							+ "read_count NUMBER(10) DEFAULT 0,"
							+ "reg_date DATE,"
							+ "likes INTEGER DEFAULT 0,"
							+ "exposure NUMBER(1),"
							+ "tags VARCHAR2(4000),"
							+ "ref INTEGER NOT NULL,"
							+ "reply_step INTEGER DEFAULT 0,"
							+ "reply_depth INTEGER DEFAULT 0,"
							+ "reply_count INTEGER DEFAULT 0,"
							+ "posting_type NUMBER(1),"
							+ "reblog_count INTEGER DEFAULT 0,"
							+ "reblog_option NUMBER(1),"
							+ "PRIMARY KEY(num),"
							+ "CHECK(content_type IN("
							+ PostingContent.TEXT_CONTENT + ", "
							+ PostingContent.MIXED_AUDIO_FILE_CONTENT + ", "
							+ PostingContent.MIXED_AUDIO_LINK_CONTENT + ", "
							+ PostingContent.MIXED_IMAGE_FILE_CONTENT + ", "
							+ PostingContent.MIXED_IMAGE_LINK_CONTENT + ", "
							+ PostingContent.MIXED_VIDEO_FILE_CONTENT + ", "
							+ PostingContent.MIXED_VIDEO_LINK_CONTENT + ", "
							+ PostingContent.SINGLE_AUDIO_FILE_CONTENT +", "
							+ PostingContent.SINGLE_AUDIO_LINK_CONTENT +", "
							+ PostingContent.SINGLE_IMAGE_FILE_CONTENT +", "
							+ PostingContent.SINGLE_IMAGE_LINK_CONTENT +", "
							+ PostingContent.SINGLE_VIDEO_FILE_CONTENT +", "
							+ PostingContent.SINGLE_VIDEO_LINK_CONTENT + ")),"
							+ "CHECK(exposure IN(" 
							+ Posting.PUBLIC_ALLOW_BOTH_REPLY_AND_REBLOG + ", "
							+ Posting.PUBLIC_ALLOW_REPLY_AND_NO_REBLOG + ", "
							+ Posting.PUBLIC_NO_REPLY_AND_ALLOW_REBLOG + ", "
							+ Posting.PUBLIC_NO_REPLY_NO_REBLOG + ", "
							+ Posting.PRIVATE + ")),"
							+ "CHECK(posting_type IN("
							+ Posting.NORMAL_TYPE_POSTING + ", "
							+ Posting.REPLY_TYPE_POSTING + ", "
							+ Posting.QNA_TYPE_POSTING + ")),"
							+ "CHECK(reblog_option IN("
							+ Posting.NOTHING + ", "
							+ Posting.ON_DELETE_CASCADE + ", "
							+ Posting.ON_UPDATE_CASCADE + ", "
							+ Posting.SET_NULL + ", "
							+ Posting.ON_UPDATE_AND_DELETE_CASCADE + "))"
						+")";
			
			String sql3 = "CREATE SEQUENCE " + blogId + "_num_seq START with 1 INCREMENT BY 1";
			
			String sql4 = "CREATE TABLE " + blogId + "_mixed ("
					+ "num INTEGER,"
					+ "text_content VARCHAR2(4000),"
					+ "file_paths VARCHAR2(4000),"
					+ "PRIMARY KEY(num))";
			
			String sql5 = "CREATE TABLE " + blogId + "_single ("
					+ "num INTEGER,"
					+ "file_paths VARCHAR2(4000),"
					+ "PRIMARY KEY(num))";
			
			String sql6 = "CREATE TABLE " + blogId + "_text ("
					+ "num INTEGER,"
					+ "text_content VARCHAR2(4000),"
					+ "PRIMARY KEY(num))";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, blogId);
			pstmt.setString(2, blog.getEmail());
			pstmt.setString(3, blog.getBlogName());
			if (blog.getHeaderImage() == null) {
				pstmt.setInt(4, blog.getBackgroundColor());
			} else {
				pstmt.setString(4, blog.getHeaderImage());
			}
			pstmt.setInt(5, blog.getBlogLayout());
			pstmt.setDate(6, KitschUtil.convertDateUtilToSql(new java.util.Date()));
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("BlogDaoImpl insertBlog() third query : " + sql2);
			pstmt = connection.prepareStatement(sql2);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("BlogDaoImpl insertBlog() fourth query : " + sql3);
			pstmt = connection.prepareStatement(sql3);
			pstmt.executeUpdate();
			
			System.out.println("BlogDaoImpl insertBlog() fifth query : " + sql4);
			pstmt = connection.prepareStatement(sql4);
			pstmt.executeUpdate();
			
			System.out.println("BlogDaoImpl insertBlog() sixth query : " + sql5);
			pstmt = connection.prepareStatement(sql5);
			pstmt.executeUpdate();
			
			System.out.println("BlogDaoImpl insertBlog() seventh query : " + sql6);
			pstmt = connection.prepareStatement(sql6);
			
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
	public void updateBlog(Blog blog) {
		//String sql = "UPDATE blog SET blog_name=?, follower_count=?, visit_count=?, background_color=?, header_image, profile_image=?, blog_layout=? WHERE member_name=?";
		String sql = "UPDATE blog SET blog_name=?, background_color=?, header_image=?, blog_layout=? WHERE blog_id=?";
		System.out.println("BlogDaoImpl updateBlog() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, blog.getBlogName());
			pstmt.setInt(2, blog.getBackgroundColor());
			pstmt.setString(3, blog.getHeaderImage());
			pstmt.setInt(4, blog.getBlogLayout());
			pstmt.setString(5, blog.getBlogId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt);
		}
	}

	@Override
	public void deleteBlog(Blog blog) {
		String sql = "DELETE FROM blog WHERE blog_name=?";
		String sql2 ="DROP TABLE " + blog.getBlogId();
		String sql3 = "DROP SEQUENCE " + blog.getBlogId() + "_num_seq";
		String sql4 = "DROP TABLE " + blog.getBlogId() + "_mixed";
		String sql5 = "DROP TABLE " + blog.getBlogId() + "_single";
		String sql6 = "DROP TABLE " + blog.getBlogId() + "_text";
		System.out.println("BlogDaoImpl deleteBlog() first query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, blog.getBlogName());
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("BlogDaoImpl deleteBlog() second query : " + sql2);
			pstmt = connection.prepareStatement(sql2);
			pstmt.executeUpdate();
			
			System.out.println("BlogDaoImpl deleteBlog() third query : " + sql3);
			pstmt = connection.prepareStatement(sql3);
			pstmt.executeUpdate();
			
			System.out.println("BlogDaoImpl deleteBlog() fourth query : " + sql4);
			pstmt = connection.prepareStatement(sql4);
			pstmt.executeUpdate();
			
			System.out.println("BlogDaoImpl deleteBlog() fifth query : " + sql5);
			pstmt = connection.prepareStatement(sql5);
			pstmt.executeUpdate();
			
			System.out.println("BlogDaoImpl deleteBlog() sixth query : " + sql6);
			pstmt = connection.prepareStatement(sql6);
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
	public Blog selectBlogByName(String blogName) {
		Blog blog = null;
		
		String sql = "SELECT * FROM blog WHERE blog_name=?";
		System.out.println("BlogDaoImpl selectBlog() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, blogName);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				blog = new Blog(rs.getString("blog_id"), rs.getString("email"), rs.getString("blog_name"), 
						rs.getInt("follow_count"), rs.getInt("visit_count"), rs.getInt("background_color"), 
						rs.getString("header_image"), rs.getInt("blog_layout"), KitschUtil.convertDateSqlToUtil(rs.getDate("reg_date")));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return blog;
	}

	@Override
	public Blog[] selectFollowedBlogs(Member member) {
		List<Blog> bList = new ArrayList<Blog>();
		Blog blog = null;
		
		String sql ="SELECT * FROM follow WHERE email=?";
		String sql2 = "SELECT * FROM blog WHERE blog_id=?";
		System.out.println("BlogDaoImpl selectFollowedBlogs() first query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try{
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String blogId = rs.getString("origin_blog_id");
				
				System.out.println("BlogDaoImpl selectFollowedBlogs() second query : " + sql2);
				pstmt2 = connection.prepareStatement(sql2);
				pstmt2.setString(1, blogId);
				rs2 = pstmt2.executeQuery();
				
				while (rs2.next()) {
					blog = new Blog(rs2.getString("blog_id"), rs2.getString("email"), rs2.getString("blog_name"), 
							rs2.getInt("follow_count"), rs2.getInt("visit_count"), rs2.getInt("background_color"), 
							rs2.getString("header_image"), rs2.getInt("blog_layout"), KitschUtil.convertDateSqlToUtil(rs2.getDate("reg_date")));
					
					bList.add(blog);
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
		
		return bList.toArray(new Blog[0]);
	}

	@Override
	public void addFollowing(Member member, String blogId) {
		String sql = "INSERT INTO follow (email, origin_blog_id) VALUES (?, ?)";
		String sql2 = "UPDATE blog SET follow_count = follow_count + 1 WHERE blog_id=?";
		String sql3 = "SELECT email FROM blog WHERE blog_id=?";
		String sql4 = "UPDATE member SET total_follower_count=total_follower_count+1 WHERE email=?";
		System.out.println("BlogDaoImpl addFollowing() first query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, blogId);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("BlogDaoImpl addFollowing() second query : " + sql2);
			pstmt = connection.prepareStatement(sql2);
			pstmt.setString(1, blogId);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("BlogDaoImpl addFollowing() third query : " + sql3);
			pstmt = connection.prepareStatement(sql3);
			pstmt.setString(1, blogId);
			rs = pstmt.executeQuery();
			
			String email;
			while (rs.next()) {
				email = rs.getString("email");
				System.out.println("BlogDaoImpl addFollowing() fourth query : " + sql4);
				pstmt2 = connection.prepareStatement(sql4);
				pstmt2.setString(1, email);
				pstmt2.executeUpdate();
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
			this.closeResources(null, pstmt2);
			this.closeResources(connection, pstmt, rs);
		}
	}

	@Override
	public void cancelFollowing(Member member, String blogId) {
		String sql = "DELETE FROM follow WHERE email=? AND origin_blog_id=?";
		String sql2 = "UPDATE blog SET follow_count = follow_count-1 WHERE email=?";
		String sql3 = "SELECT email FROM blog WHERE blog_id=?";
		String sql4 = "UPDATE member SET total_follower_count=total_follower_count-1 WHERE email=?";
		System.out.println("BlogDaoImpl cancelFollowing() first query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, blogId);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("BlogDaoImpl cancelFollowing() second query : " + sql2);
			pstmt = connection.prepareStatement(sql2);
			pstmt.setString(1, blogId);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("BlogDaoImpl cancelFollowing() third query : " + sql3);
			pstmt = connection.prepareStatement(sql3);
			pstmt.setString(1, blogId);
			rs = pstmt.executeQuery();
			
			String email;
			while (rs.next()) {
				email = rs.getString("email");
				System.out.println("BlogDaoImpl cancelFollowing() fourth query : " + sql4);
				pstmt2 = connection.prepareStatement(sql4);
				pstmt2.setString(1, email);
				pstmt2.executeUpdate();
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
			this.closeResources(null, pstmt2);
			this.closeResources(connection, pstmt, rs);
		}
	}

	@Override
	public List<Blog> selectBlogList(Map<String, Object> searchInfo) {
		List<Blog> bList = new ArrayList<Blog>();
		Blog blog = null;
		
		String searchType = null;
		String searchText = null;
		String searchTextKeyword = null;
		int startRow = 0;
		int endRow = 0;
		
		String whereSyntax ="";
		
		if (!(searchInfo.isEmpty())) {
			String target = (String) searchInfo.get("target");
			if (target.equals("blog") || target.equals("all")) {
				searchType = (String) searchInfo.get("searchType");
				searchText = (String) searchInfo.get("searchText");
				searchTextKeyword = null;
				startRow = (Integer) searchInfo.get("startRow");
				endRow = (Integer) searchInfo.get("endRow");
				
				if (searchType.equals("all")) {
					whereSyntax = " WHERE email LIKE ? OR blog_name LIKE ? ESCAPE '@'";
				} else if (searchType.equals("email")) {
					whereSyntax = " WHERE email LIKE ? ESCAPE '@'";
				} else if (searchType.equals("blogName")) {
					whereSyntax = " WHERE blog_name LIKE ? ESCAPE '@'";
				}
				
				if (searchText != null) {
					searchText.trim();
					String searchTextTemp1 = searchText.replace("@", "@@");
					String searchTextTemp2 = searchTextTemp1.replace("_", "@_");
					String searchTextTemp3 = searchTextTemp2.replace("%", "@%");
					searchTextKeyword = "%" + searchTextTemp3.replace(' ', '%') + "%";
				}
			}
		}
		
		String sql = "SELECT * FROM "
				+ "(SELECT ROWNUM AS row_num, blog_id, email, blog_name, follow_count, visit_count, background_color, header_image, blog_layout, reg_date "
				+ "FROM (SELECT * FROM blog " + whereSyntax + ")) WHERE row_num BETWEEN ? AND ?";
		System.out.println("BlogDaoImpl selectBlogList() query: " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			
			if (searchType == null || searchType.trim().length() == 0) {
            	pstmt.setInt(1, startRow);
            	pstmt.setInt(2, endRow);
            } else if (searchType.equals("all")) {
				pstmt.setString(1, searchTextKeyword);
				pstmt.setString(2, searchTextKeyword);
				pstmt.setInt(3, startRow);
				pstmt.setInt(4, endRow);
			} else if (searchType.equals("email")) {
				pstmt.setString(1, searchTextKeyword);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
			} else if (searchType.equals("blogName")) {
				pstmt.setString(1, searchTextKeyword);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				blog = new Blog(rs.getString("blog_id"), rs.getString("email"), rs.getString("blog_name"), 
						rs.getInt("follow_count"), rs.getInt("visit_count"), rs.getInt("background_color"), 
						rs.getString("header_image"), rs.getInt("blog_layout"), KitschUtil.convertDateSqlToUtil(rs.getDate("reg_date")));
				bList.add(blog);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return bList;
	}

	@Override
	public int selectBlogCount(Map<String, Object> searchInfo) {
		int count = 0;
		
		String searchType = null;
		String searchText = null;
		String searchTextKeyword = null;
		
		String whereSyntax ="";
		
		if (searchInfo != null) {
			if (!(searchInfo.isEmpty())) {
				String target = (String) searchInfo.get("target");
				if (target.equals("blog") || target.equals("all")) {
					searchType = (String) searchInfo.get("searchType");
					searchText = (String) searchInfo.get("searchText");
					searchTextKeyword = null;
					
					if (searchType.equals("all")) {
						whereSyntax = " WHERE email LIKE ? OR blog_name LIKE ? ESCAPE '@'";
					} else if (searchType.equals("email")) {
						whereSyntax = " WHERE email LIKE ? ESCAPE '@'";
					} else if (searchType.equals("blogName")) {
						whereSyntax = " WHERE blog_name LIKE ? ESCAPE '@'";
					}
					
					if (searchText != null) {
						searchText.trim();
						String searchTextTemp1 = searchText.replace("@", "@@");
						String searchTextTemp2 = searchTextTemp1.replace("_", "@_");
						String searchTextTemp3 = searchTextTemp2.replace("%", "@%");
						searchTextKeyword = "%" + searchTextTemp3.replace(' ', '%') + "%";
					}
				}
			}
		}
		
		
		String sql = "SELECT COUNT(blog_name) FROM blog " + whereSyntax;
		System.out.println("MemberDaoImpl selectBlogCount() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			
			if (searchType == null || searchType.trim().length() == 0) {
            } else if (searchType.equals("all")) {
				pstmt.setString(1, searchTextKeyword);
				pstmt.setString(2, searchTextKeyword);
			} else if (searchType.equals("email")) {
				pstmt.setString(1, searchTextKeyword);
			} else if (searchType.equals("blogName")) {
				pstmt.setString(1, searchTextKeyword);
			}
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return count;
	}

	@Override
	public boolean blogExistsByName(String blogName) {
		boolean result = false;
		
		String sql = "SELECT * FROM blog WHERE blog_name=?";
		System.out.println("BlogDaoImpl blogExists() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, blogName);
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
	public void addVisitCount(String blogId) {
		String sql = "UPDATE blog SET visit_count = visit_count + 1 WHERE blog_id=?";
		System.out.println("BlogDaoImpl addVisitCount() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, blogId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt);
		}
	}

	@Override
	public void updateBlogName(String originBlogName, String newBlogName) {
		String sql = "UPDATE blog SET blog_name=? WHERE blog_name=?";
		System.out.println("BlogDaoImpl updateBlogName() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, newBlogName);
			pstmt.setString(2, originBlogName);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt);
		}
	}

	@Override
	public List<Blog> selectMemberBlogs(Member member) {
		List<Blog> bList = new ArrayList<Blog>();
		Blog memberBlog = null;
		
		String sql = "SELECT * FROM blog WHERE email=?";
		System.out.println("BlogDaoImpl selectMemberBlogs() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				memberBlog = new Blog(rs.getString("blog_id"), rs.getString("email"), rs.getString("blog_name"), 
						rs.getInt("follow_count"), rs.getInt("visit_count"), rs.getInt("background_color"), 
						rs.getString("header_image"), rs.getInt("blog_layout"), KitschUtil.convertDateSqlToUtil(rs.getDate("reg_date")));
				bList.add(memberBlog);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return bList;
	}

	@Override
	public Blog selectBlogById(String blogId) {
		String sql = "SELECT * FROM blog WHERE blog_id=?";
		System.out.println("BlogDaoImpl selectBlogById() : query : " + sql);
		
		Blog blog = null;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, blogId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				blog = new Blog(rs.getString("blog_id"), rs.getString("email"), rs.getString("blog_name"), 
						rs.getInt("follow_count"), rs.getInt("visit_count"), rs.getInt("background_color"), 
						rs.getString("header_image"), rs.getInt("blog_layout"), KitschUtil.convertDateSqlToUtil(rs.getDate("reg_date")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		return blog;
	}

	@Override
	public boolean blogExistsById(String blogId) {
		String sql = "SELECT blog_id FROM blog WHERE blog_id = ?";
		System.out.println("BlogDaoImpl blogExistsById() : query : " + sql);
		
		boolean result = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, blogId);
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
	public boolean isFollowed(Member member, String blogId) {
		String sql = "SELECT * FROM follow WHERE email=? AND origin_blog_id=?";
		System.out.println("BlogDaoImpl isFollowed() query : " + sql);
		
		boolean result = false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, blogId);
			rs = pstmt.executeQuery();
			result = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return result;
	}
	
}
