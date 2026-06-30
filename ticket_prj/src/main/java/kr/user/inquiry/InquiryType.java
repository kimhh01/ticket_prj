package kr.user.inquiry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 문의 유형의 코드, DB 저장값, 화면 표시명을 한곳에서 관리한다.
 */
public enum InquiryType {
	RESERVATION(1, "예매문의", "예매문의"),
	PAYMENT(2, "결제문의", "결제문의"),
	MEMBER(3, "회원문의", "회원문의"),
	ETC(4, "기타문의", "기타문의");

	private static final Map<Integer, String> DISPLAY_NAMES;

	static {
		Map<Integer, String> displayNames = new LinkedHashMap<Integer, String>();
		for (InquiryType type : values()) {
			displayNames.put(type.code, type.displayName);
		}
		DISPLAY_NAMES = Collections.unmodifiableMap(displayNames);
	}

	private final int code;
	private final String dbValue;
	private final String displayName;

	InquiryType(int code, String dbValue, String displayName) {
		this.code = code;
		this.dbValue = dbValue;
		this.displayName = displayName;
	}

	public int getCode() {
		return code;
	}

	public String getDbValue() {
		return dbValue;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static InquiryType fromCode(int code) {
		for (InquiryType type : values()) {
			if (type.code == code) {
				return type;
			}
		}
		return null;
	}

	public static InquiryType fromDbValue(String dbValue) {
		for (InquiryType type : values()) {
			if (type.dbValue.equals(dbValue)) {
				return type;
			}
		}
		return ETC;
	}

	public static Map<Integer, String> toDisplayMap() {
		return DISPLAY_NAMES;
	}
}
