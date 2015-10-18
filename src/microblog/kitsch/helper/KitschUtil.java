package microblog.kitsch.helper;

public class KitschUtil {
	
	/** java.util.Date 객체를 java.sql.Date 객체로 변환하여 리턴하는 메서드 */
	public static java.sql.Date convertDateUtilToSql (java.util.Date uDate) {
		java.sql.Date sDate = null;
		if (uDate != null) {
			sDate = new java.sql.Date(uDate.getTime());
		}
		return sDate;
	}
	
	/** java.sql.Date 객체를 java.util.Date 객체로 변환하여 리턴하는 메서드 */
	public static java.util.Date convertDateSqlToUtil (java.sql.Date sDate) {
		java.sql.Date uDate = null;
		if (sDate != null) {
			uDate = new java.sql.Date(sDate.getTime());
		}
		return uDate;
	}
	
	/** 0 ~ length 사이의 난수를 발생하여 리턴하는 메서드(length 미포함) */
	public static int makeRandomIndex(int length) {
		return (int) (Math.random() * (length));
	}
}
