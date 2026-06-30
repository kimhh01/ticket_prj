package kr.user.inquiry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.user.common.UserDBConnection;

public class InquiryDAO {

	/**
	 * DB 연결 객체를 얻는 메서드
	 * 사용자 전용 JNDI DataSource에서 DB 연결을 가져온다.
	 */
	private Connection getConnection() throws SQLException {
		return UserDBConnection.getInstance().getConnection();
	}// getConnection

	/**
	 * 다음 문의 번호 조회
	 * 
	 * 현재 INQUIRY 테이블에 시퀀스가 없으므로
	 * MAX(inquiry_id) + 1 방식으로 다음 번호를 구한다.
	 */
	private int selectNextInquiryCode(Connection con) throws SQLException {
		int nextCode = 1;

		String sql = "SELECT NVL(MAX(inquiry_id), 0) + 1 AS next_code FROM inquiry";

		try (PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				nextCode = rs.getInt("next_code");
			}
		}

		return nextCode;
	}// selectNextInquiryCode

	/**
	 * 다음 문의 카테고리 번호 조회
	 * 
	 * 현재 INQUIRY_CATEGORY 테이블에 시퀀스가 없으므로
	 * MAX(inquiry_category_id) + 1 방식으로 다음 번호를 구한다.
	 */
	private int selectNextInquiryCategoryCode(Connection con) throws SQLException {
		int nextCode = 1;

		String sql = "SELECT NVL(MAX(inquiry_category_id), 0) + 1 AS next_code FROM inquiry_category";

		try (PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				nextCode = rs.getInt("next_code");
			}
		}

		return nextCode;
	}// selectNextInquiryCategoryCode

	/**
	 * 1:1 문의 등록
	 * 
	 * 사용자 문의 등록 시 INQUIRY와 INQUIRY_CATEGORY 두 테이블에 INSERT한다.
	 * 둘 중 하나라도 실패하면 rollback 처리한다.
	 * 
	 * @param inquiryDTO 등록할 문의 정보
	 * @return INSERT 성공 건수. 정상 등록이면 2가 반환된다.
	 */
	public int insertInquiry(InquiryDTO inquiryDTO) {
		int result = 0;
		InquiryType inquiryType = InquiryType.fromCode(inquiryDTO.getInquiryCategoryCode());
		if (inquiryType == null) {
			return 0;
		}

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();

			// INQUIRY와 INQUIRY_CATEGORY 둘 다 성공해야 하므로 자동 커밋을 끈다.
			con.setAutoCommit(false);

			int inquiryCode = selectNextInquiryCode(con);
			int inquiryCategoryCode = selectNextInquiryCategoryCode(con);

			// 1. 문의글 본문 저장
			String insertInquirySql =
					"INSERT INTO inquiry ( "
					+ " inquiry_id, member_id, inquiry_title, inquiry_content, "
					+ " reply_content, inquiry_date, reply_date "
					+ ") VALUES ( "
					+ " ?, ?, ?, ?, NULL, SYSDATE, NULL "
					+ ")";

			pstmt = con.prepareStatement(insertInquirySql);
			pstmt.setInt(1, inquiryCode);
			pstmt.setString(2, inquiryDTO.getMemberCode());
			pstmt.setString(3, inquiryDTO.getInquiryTitle());
			pstmt.setString(4, inquiryDTO.getInquiryContent());

			result += pstmt.executeUpdate();

			pstmt.close();

			// 2. 문의글의 문의유형 저장
			String insertCategorySql =
					"INSERT INTO inquiry_category ( "
					+ " inquiry_category_id, admin_id, inquiry_id, inquiry_type "
					+ ") VALUES ( "
					+ " ?, NULL, ?, ? "
					+ ")";

			pstmt = con.prepareStatement(insertCategorySql);
			pstmt.setInt(1, inquiryCategoryCode);
			pstmt.setInt(2, inquiryCode);
			pstmt.setString(3, inquiryType.getDbValue());

			result += pstmt.executeUpdate();

			// 두 INSERT가 모두 성공하면 commit
			con.commit();

		} catch (SQLException se) {
			se.printStackTrace();

			// 중간에 오류가 나면 두 테이블 모두 저장되지 않도록 rollback
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			result = 0;

		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (con != null) {
					con.setAutoCommit(true);
					con.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;
	}// insertInquiry

	/**
	 * 회원별 1:1 문의 목록 조회
	 * 
	 * 로그인한 회원의 문의만 조회한다.
	 * 답변 상태는 DB 컬럼이 없으므로 reply_content가 NULL인지 아닌지로 판단한다.
	 */
	public List<InquiryDTO> selectInquiryList(String memberCode) {
		List<InquiryDTO> inquiryList = new ArrayList<InquiryDTO>();

		String sql =
				"SELECT i.inquiry_id, "
				+ "      i.member_id, "
				+ "      c.inquiry_type, "
				+ "      i.inquiry_title, "
				+ "      i.inquiry_content, "
				+ "      i.inquiry_date, "
				+ "      i.reply_content, "
				+ "      i.reply_date, "
				+ "      CASE "
				+ "          WHEN i.reply_content IS NULL THEN '답변대기' "
				+ "          ELSE '답변완료' "
				+ "      END AS inquiry_status "
				+ "FROM inquiry i "
				+ "JOIN inquiry_category c "
				+ "ON i.inquiry_id = c.inquiry_id "
				+ "WHERE i.member_id = ? "
				+ "ORDER BY i.inquiry_id ASC";;

		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, memberCode);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					InquiryDTO dto = new InquiryDTO();

					dto.setInquiryCode(rs.getInt("inquiry_id"));
					dto.setMemberCode(rs.getString("member_id"));
					dto.setInquiryCategoryCode(InquiryType.fromDbValue(rs.getString("inquiry_type")).getCode());
					dto.setInquiryTitle(rs.getString("inquiry_title"));
					dto.setInquiryContent(rs.getString("inquiry_content"));
					dto.setInquiryDate(rs.getDate("inquiry_date"));
					dto.setReplyContent(rs.getString("reply_content"));
					dto.setReplyDate(rs.getDate("reply_date"));
					dto.setInquiryStatus(rs.getString("inquiry_status"));

					inquiryList.add(dto);
				}
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}

		return inquiryList;
	}// selectInquiryList

	/**
	 * 1:1 문의 상세 조회
	 * 
	 * 문의 번호와 회원 코드를 같이 조건으로 걸어서
	 * 다른 회원의 문의를 볼 수 없게 한다.
	 */
	public InquiryDTO selectInquiryDetail(int inquiryCode, String memberCode) {
		InquiryDTO inquiryDTO = null;

		String sql =
				"SELECT i.inquiry_id, "
				+ "      i.member_id, "
				+ "      c.inquiry_type, "
				+ "      i.inquiry_title, "
				+ "      i.inquiry_content, "
				+ "      i.inquiry_date, "
				+ "      i.reply_content, "
				+ "      i.reply_date, "
				+ "      CASE "
				+ "          WHEN i.reply_content IS NULL THEN '답변대기' "
				+ "          ELSE '답변완료' "
				+ "      END AS inquiry_status "
				+ "FROM inquiry i "
				+ "JOIN inquiry_category c "
				+ "ON i.inquiry_id = c.inquiry_id "
				+ "WHERE i.inquiry_id = ? "
				+ "AND i.member_id = ?";

		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, inquiryCode);
			pstmt.setString(2, memberCode);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					inquiryDTO = new InquiryDTO();

					inquiryDTO.setInquiryCode(rs.getInt("inquiry_id"));
					inquiryDTO.setMemberCode(rs.getString("member_id"));
					inquiryDTO.setInquiryCategoryCode(InquiryType.fromDbValue(rs.getString("inquiry_type")).getCode());
					inquiryDTO.setInquiryTitle(rs.getString("inquiry_title"));
					inquiryDTO.setInquiryContent(rs.getString("inquiry_content"));
					inquiryDTO.setInquiryDate(rs.getDate("inquiry_date"));
					inquiryDTO.setReplyContent(rs.getString("reply_content"));
					inquiryDTO.setReplyDate(rs.getDate("reply_date"));
					inquiryDTO.setInquiryStatus(rs.getString("inquiry_status"));
				}
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}

		return inquiryDTO;
	}// selectInquiryDetail

	/**
	 * 1:1 문의 수정
	 * 
	 * 답변이 없는 문의만 수정해야 한다.
	 * 실제 답변 여부 확인은 Service의 updateInquiry()에서 먼저 처리한다.
	 */
	public int updateInquiry(InquiryDTO inquiryDTO) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			con.setAutoCommit(false);

			String updateInquirySql =
					"UPDATE inquiry "
					+ "SET inquiry_title = ?, "
					+ "    inquiry_content = ? "
					+ "WHERE inquiry_id = ? "
					+ "AND member_id = ? "
					+ "AND reply_content IS NULL";

			pstmt = con.prepareStatement(updateInquirySql);
			pstmt.setString(1, inquiryDTO.getInquiryTitle());
			pstmt.setString(2, inquiryDTO.getInquiryContent());
			pstmt.setInt(3, inquiryDTO.getInquiryCode());
			pstmt.setString(4, inquiryDTO.getMemberCode());

			int inquiryResult = pstmt.executeUpdate();

			pstmt.close();

			// 소유자와 답변 상태 조건을 만족한 문의만 카테고리까지 수정한다.
			if (inquiryResult != 1) {
				con.rollback();
				return 0;
			}

			String updateCategorySql =
					"UPDATE inquiry_category "
					+ "SET inquiry_type = ? "
					+ "WHERE inquiry_id = ?";

			pstmt = con.prepareStatement(updateCategorySql);
			InquiryType inquiryType = InquiryType.fromCode(inquiryDTO.getInquiryCategoryCode());
			if (inquiryType == null) {
				con.rollback();
				return 0;
			}
			pstmt.setString(1, inquiryType.getDbValue());
			pstmt.setInt(2, inquiryDTO.getInquiryCode());

			int categoryResult = pstmt.executeUpdate();

			if (categoryResult != 1) {
				con.rollback();
				return 0;
			}

			con.commit();
			result = inquiryResult + categoryResult;

		} catch (SQLException se) {
			se.printStackTrace();

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (con != null) {
					con.setAutoCommit(true);
					con.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return result;
	}// updateInquiry

	/**
	 * 답변 여부 확인
	 * 
	 * reply_content가 존재하면 답변이 등록된 문의로 판단한다.
	 * 반환값이 0이면 답변 없음, 1이면 답변 있음.
	 */
	public int selectReplyStatus(int inquiryCode, String memberCode) {
		int replyCount = 0;

		String sql =
				"SELECT COUNT(*) AS reply_count "
				+ "FROM inquiry "
				+ "WHERE inquiry_id = ? "
				+ "AND member_id = ? "
				+ "AND reply_content IS NOT NULL";

		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, inquiryCode);
			pstmt.setString(2, memberCode);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					replyCount = rs.getInt("reply_count");
				}
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}

		return replyCount;
	}// selectReplyStatus

}// class
