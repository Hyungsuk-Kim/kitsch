package microblog.kitsch.helper;

public class ConvertDateType {
	
	// java.util.Date 객체를 java.sql.Date 객체로 변환하여 리턴
	public static java.sql.Date ConvertDateUtilToSql (java.util.Date uDate) {
		java.sql.Date sDate = null;
		if (uDate != null) {
			sDate = new java.sql.Date(uDate.getTime());
		}
		return sDate;
	}
	
	// java.sql.Date 객체를 java.util.Date 객체로 변환하여 리턴
	public static java.util.Date ConvertDateSqlToUtil (java.sql.Date sDate) {
		java.sql.Date uDate = null;
		if (sDate != null) {
			uDate = new java.sql.Date(sDate.getTime());
		}
		return uDate;
	}
}
