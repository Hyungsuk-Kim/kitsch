package microblog.kitsch;

public class Sql {
	private String sql = 
			"SELECT * FROM ("
					+ "SELECT ROWNUM AS row_num, num, title, writer, content_type, read_count, reg_date, likes, exposure, tags, ref, reply_step, reply_depth, reply_count, posting_type, reblog_count, reblog_option FROM ("
			+ "SELECT * FROM ("
			+ "SELECT table_name FROM tabs WHERE table_name NOT IN ('BLOG', 'MEMBER', 'QNA', 'KITSCH', 'LIKES', 'LIKE', 'REBLOG', 'MESSAGE_BOX', 'FOLLOW') AND NOT LIKE '%_MIXED' AND NOT LIKE '%_SINGLE' AND NOT LIKE '%_TEXT'" + specifyBlog + ")" + whereSyntax + orderBySyntax + "))"
			+ " WHERE row_num BETWEEN ? AND ?";
	
}
