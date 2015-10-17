package microblog.kitsch.business.dataaccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import microblog.kitsch.business.domain.Member;
import microblog.kitsch.business.service.MemberDao;
import microblog.kitsch.helper.ConvertDateType;

public class MemberDaoImpl implements MemberDao{

	private Connection obtainConnection() throws SQLException {
		return DatabaseUtil.getConnection();
    	//return DatabaseUtil_old.getConnection();
    }
	
	private void closeResources(Connection connection, Statement stmt, ResultSet rs){
		DatabaseUtil.close(connection, stmt, rs);
		//DatabaseUtil_old.close(connection, stmt, rs);
	}
	
	private void closeResources(Connection connection, Statement stmt){
		this.closeResources(connection, stmt, null);
		//DatabaseUtil.close(connection, stmt);
	}
	
	@Override
	public boolean memberEmailExists(String email) {
		boolean result = false;
		
		String sql ="SELECT email FROM member WHERE email = ?";
		System.out.println("MemberDaoImpl memberEmailExists() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return result;
	}

	@Override
	public boolean memberNameExists(String name) {
		boolean result = false;
		
		String sql = "SELECT name FROM member WHERE name = ?";
		System.out.println("MemberDaoImpl memberNameExists() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		return result;
	}

	@Override
	public void insertMember(Member member) {
		String sql = "INSERT INTO member (email, name, password, reg_date, role) "
				+ "VALUES (?, ?, ?, ?, ?)";
		System.out.println("MemberDaoImpl insertMember() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPassword());
			pstmt.setDate(4, ConvertDateType.ConvertDateUtilToSql(new java.util.Date()));
			pstmt.setInt(5, member.getRole());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt);
		}
	}

	@Override
	public void updateMember(Member member) {
		String sql = "UPDATE member SET password=?, role=? WHERE email=?";
		System.out.println("MemberDaoImpl updateMember() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, member.getPassword());
			pstmt.setInt(2, member.getRole());
			pstmt.setString(3, member.getEmail());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt);
		}
	}

	@Override
	public void deleteMember(Member member) {
		String sql ="DELETE FROM member WHERE email=?";
		System.out.println("MemberDaoImpl deleteMember() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, member.getEmail());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt);
		}
	}

	@Override
	public Member selectMemberAsName(String name) {
		Member member = null;
		
		String sql = "SELECT * FROM member WHERE name=?";
		System.out.println("MemberDaoImpl selectMemberAsName() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				member = new Member(rs.getString("email"), rs.getString("name"), rs.getString("password"), 
						ConvertDateType.ConvertDateSqlToUtil(rs.getDate("reg_date")), rs.getInt("role"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return member;
	}

	@Override
	public Member[] selectAllMembers() {
		List<Member> mList = new ArrayList<Member>();
		Member member = null;
		
		String sql = "SELECT * FROM member";
		System.out.println("MemberDaoImpl selectAllMembers() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				member = new Member(rs.getString("email"), rs.getString("name"), rs.getString("password"), 
						ConvertDateType.ConvertDateSqlToUtil(rs.getDate("reg_date")), rs.getInt("role"));
				
				mList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return mList.toArray(new Member[0]);
	}

	@Override
	public Member checkMember(String email, String password) {
		Member member = new Member(email, password);

		String sql = "SELECT * FROM member WHERE email=?";
		System.out.println("MemberDaoImpl checkMember() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				String pw = rs.getString("password");
				if (pw.equals(password)) {
					member.setName(rs.getString("name"));
					//member.setRegDate(ConvertDateType.ConvertDateSqlToUtil(rs.getDate("reg_Date")));
					member.setRegDate(rs.getDate("reg_date"));
					member.setRole(rs.getInt("role"));
					member.setCheck(Member.VALID_MEMBER);
				} else {
                    member.setCheck(Member.INVALID_PASSWORD);
                }
            } else {
                member.setCheck(Member.INVALID_EMAIL);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return member;
	}

	@Override
	public List<Member> getMemberList(Map<String, Object> searchInfo) {
		List<Member> mList = new ArrayList<Member>();
		Member member = null;
		
		String searchType = null;
		String searchText = null;
		String searchTextKeyword = null;
		int startRow = 0;
		int endRow = 0;
		
		String whereSyntax = "";
		
		if (searchInfo != null) {
			if (!(searchInfo.isEmpty())) {
				String target = (String) searchInfo.get("target");
				if (target.equals("member") || target.equals("all")) {
					searchType = (String) searchInfo.get("searchType");
					searchText = (String) searchInfo.get("searchText");
					searchTextKeyword = null;
					startRow = (Integer) searchInfo.get("startRow");
					endRow = (Integer) searchInfo.get("endRow");
					
					if (searchType.equals("all")) {
						whereSyntax = " WHERE name LIKE ? OR email LIKE ? ESCAPE '@'";
					} else if (searchType.equals("memberName")) {
						whereSyntax = " WHERE name LIKE ? ESCAPE '@'";
					} else if (searchType.equals("memberEmail")) {
						whereSyntax = " WHERE email LIKE ? ESCAPE '@'";
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
		
		String sql = "SELECT * FROM "
				+ "(SELECT ROWNUM AS row_num, email, name, password, reg_date, role FROM "
				+ "(SELECT * FROM member " + whereSyntax + ")) WHERE row_num BETWEEN ? AND ?";
		System.out.println("MemberDaoImpl selectMemberList() query : " + sql);
		
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
			} else if (searchType.equals("memberName")) {
				pstmt.setString(1, searchTextKeyword);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
			} else if (searchType.equals("memberEmail")) {
				pstmt.setString(1, searchTextKeyword);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				member = new Member(rs.getString("email"), rs.getString("name"), rs.getString("password"), 
						ConvertDateType.ConvertDateSqlToUtil(rs.getDate("reg_date")), rs.getInt("role"));
				mList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return mList;
	}

	@Override
	public int getMemberCount(Map<String, Object> searchInfo) {
		int count = 0;
		
		String searchType = null;
		String searchText = null;
		String searchTextKeyword = null;
		
		String whereSyntax = "";
		
		if (searchInfo != null) {
			if (!(searchInfo.isEmpty())) {
				String target = (String) searchInfo.get("target");
				if (target.equals("member") || target.equals("all")) {
					searchType = (String) searchInfo.get("searchType");
					searchText = (String) searchInfo.get("searchText");
					searchTextKeyword = null;
					
					if (searchType.equals("all")) {
						whereSyntax = " WHERE name LIKE ? OR email LIKE ? ESCAPE '@'";
					} else if (searchType.equals("memberName")) {
						whereSyntax = " WHERE name LIKE ? ESCAPE '@'";
					} else if (searchType.equals("memberEmail")) {
						whereSyntax = " WHERE email LIKE ? ESCAPE '@'";
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
		
		String sql = "SELECT count(email) FROM member " + whereSyntax;
		System.out.println("MemberDaoImpl selectMemberCount() query : " + sql);
		
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
			} else if (searchType.equals("memberName")) {
				pstmt.setString(1, searchTextKeyword);
			} else if (searchType.equals("memberEmail")) {
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
	public Member selectMemberAsEmail(String email) {
		Member member = null;
		
		String sql = "SELECT * FROM member WHERE email=?";
		System.out.println("MemberDaoImpl selectMemberAsEmail() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				member = new Member(rs.getString("email"), rs.getString("name"), rs.getString("password"), 
						ConvertDateType.ConvertDateSqlToUtil(rs.getDate("reg_date")), rs.getInt("role"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return member;
	}

	@Override
	public Member[] selectMemberAsRole(int role) {
		List<Member> mList = new ArrayList<Member>();
		Member member = null;
		
		String sql = "SELECT * FROM member WHERE role=?";
		System.out.println("MemberDaoImpl selectMemberAsRole() query : " + sql);
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = this.obtainConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, role);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				member = new Member(rs.getString("email"), rs.getString("name"), rs.getString("password"), 
						ConvertDateType.ConvertDateSqlToUtil(rs.getDate("reg_date")), rs.getInt("role"));
				
				mList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeResources(connection, pstmt, rs);
		}
		
		return mList.toArray(new Member[0]);
	}

}
