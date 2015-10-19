package microblog.kitsch.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
	
	/**
	 * 입력받은 문자열 배열의 내용을 -각 문자열 앞에 구분자를 포함 혹은 미포함하는- 하나의 문자열로 연결하여 리턴한다.
	 * @param items 하나의 문자열로 만들 문자열 배열
	 * @param delimiter 문자열을 구분할 구분자
	 * @param containDelim 구분자 포함 여부 : true - 포함, false - 미포함
	 * @return 문자열 배열의 내용을 연결한 하나의 문자열 객체
	 */
	public static String convertToString(String[] items, String delimiter, boolean containDelim) {
		StringBuilder convertedItems = new StringBuilder();
		if (containDelim) {
			for (String item : items) {
				convertedItems.append(delimiter + item);
			}
		} else {
			for (String item : items) {
				convertedItems.append(item);
			}
		}
		return convertedItems.toString();
	}
	
	/**
	 * 입력받은 하나의 문자열을 입력받은 구분자로 구분하여 -각 문자열 앞에 구분자를 포함 혹은 미포함하는- 문자열(들)을 배열로 리턴한다.
	 * @param items 배열로 구분할 문자열
	 * @param delimiter 문자열을 구분할 구분자
	 * @param containDelim 구분자 포함 여부 :true - 포함, false -미포함
	 * @return 구분된 문자열이 저장된 문자열 배열 객체
	 */
	public static String[] convertToStringArray(String items, String delimiter, boolean containDelim) {
		List<String> convertedItems = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(items, delimiter);
		if (containDelim) {
			while (tokenizer.hasMoreTokens()) {
				convertedItems.add(delimiter + tokenizer.nextToken());
			}
		} else {
			while (tokenizer.hasMoreTokens()) {
				convertedItems.add(tokenizer.nextToken());
			}
		}
		return convertedItems.toArray(new String[0]);
	}
}
