package kr.user.inquiry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.common.InquiryType;
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
	 * 1:1 문의 등록
	 * 
	 * 문의번호는 INQUIRY_SEQ에서 발급하고,
	 * 선택한 고정 카테고리 번호는 INQUIRY의 외래키로 저장한다.
	 * 
	 * @param inquiryDTO 등록할 문의 정보
	 * @return INSERT 성공 건수. 정상 등록이면 1이 반환된다.
	 */
	public int insertInquiry(InquiryDTO inquiryDTO) {
		InquiryType inquiryType = InquiryType.fromCode(inquiryDTO.getInquiryCategoryCode());
		if (inquiryType == null) {
			return 0;
		}

		String sql =
				"INSERT INTO inquiry ( "
				+ " inquiry_id, member_id, inquiry_title, inquiry_content, "
				+ " reply_content, inquiry_date, reply_date, inquiry_category_id "
				+ ") VALUES ( "
				+ " INQUIRY_SEQ.NEXTVAL, ?, ?, ?, NULL, SYSDATE, NULL, ? "
				+ ")";

		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, inquiryDTO.getMemberCode());
			pstmt.setString(2, inquiryDTO.getInquiryTitle());
			pstmt.setString(3, inquiryDTO.getInquiryContent());
			pstmt.setInt(4, inquiryType.getCode());

			return pstmt.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
			return 0;
		}
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
				+ "      i.inquiry_category_id, "
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
				+ "ON i.inquiry_category_id = c.inquiry_category_id "
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
					dto.setInquiryCategoryCode(rs.getInt("inquiry_category_id"));
					dto.setInquiryTitle(rs.getString("inquiry_title"));
					dto.setInquiryContent(rs.getString("inquiry_content"));
					dto.setInquiryDate(rs.getTimestamp("inquiry_date"));
					dto.setReplyContent(rs.getString("reply_content"));
					dto.setReplyDate(rs.getTimestamp("reply_date"));
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
				+ "      i.inquiry_category_id, "
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
				+ "ON i.inquiry_category_id = c.inquiry_category_id "
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
					inquiryDTO.setInquiryCategoryCode(rs.getInt("inquiry_category_id"));
					inquiryDTO.setInquiryTitle(rs.getString("inquiry_title"));
					inquiryDTO.setInquiryContent(rs.getString("inquiry_content"));
					inquiryDTO.setInquiryDate(rs.getTimestamp("inquiry_date"));
					inquiryDTO.setReplyContent(rs.getString("reply_content"));
					inquiryDTO.setReplyDate(rs.getTimestamp("reply_date"));
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
		InquiryType inquiryType = InquiryType.fromCode(inquiryDTO.getInquiryCategoryCode());
		if (inquiryType == null) {
			return 0;
		}

		String sql =
				"UPDATE inquiry "
				+ "SET inquiry_title = ?, "
				+ "    inquiry_content = ?, "
				+ "    inquiry_category_id = ? "
				+ "WHERE inquiry_id = ? "
				+ "AND member_id = ? "
				+ "AND reply_content IS NULL";

		try (Connection con = getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, inquiryDTO.getInquiryTitle());
			pstmt.setString(2, inquiryDTO.getInquiryContent());
			pstmt.setInt(3, inquiryType.getCode());
			pstmt.setInt(4, inquiryDTO.getInquiryCode());
			pstmt.setString(5, inquiryDTO.getMemberCode());

			return pstmt.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
			return 0;
		}
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
