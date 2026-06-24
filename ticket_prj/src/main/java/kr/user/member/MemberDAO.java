package kr.user.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.user.common.UserDBConnection;

public class MemberDAO {

	private Connection getConnection() throws SQLException {
		return UserDBConnection.getInstance().getConnection();
	}

	/**
	 * 로그인 회원 조회
	 */
	public MemberDTO selectLogin(MemberDTO memberDTO) {
		MemberDTO resultDTO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql =
			" SELECT MEMBER_ID, NAME, GRADE_NAME, EMAIL, PASS, PHONE, ZIPCODE, " +
			"        ADDRESS, ADDRESS2, STATE, JOIN_DATE, SNS_RECEIVE_YN, EMAIL_RECEIVE_YN " +
			" FROM MEMBER " +
			" WHERE MEMBER_ID = ? " +
			" AND PASS = ? " +
			" AND STATE = '활성' ";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, memberDTO.getMemberCode());
			pstmt.setString(2, memberDTO.getPassword());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				resultDTO = new MemberDTO();

				resultDTO.setMemberCode(rs.getString("MEMBER_ID"));
				resultDTO.setName(rs.getString("NAME"));
				resultDTO.setEmail(rs.getString("EMAIL"));
				resultDTO.setPassword(rs.getString("PASS"));
				resultDTO.setPhone(rs.getString("PHONE"));
				resultDTO.setZipcode(rs.getInt("ZIPCODE"));
				resultDTO.setAddress(rs.getString("ADDRESS"));
				resultDTO.setAddress2(rs.getString("ADDRESS2"));
				resultDTO.setState(rs.getString("STATE"));
				resultDTO.setJoinDate(rs.getDate("JOIN_DATE"));
				resultDTO.setSmsReceiveYN(rs.getString("SNS_RECEIVE_YN").charAt(0));
				resultDTO.setEmailReceiveYN(rs.getString("EMAIL_RECEIVE_YN").charAt(0));
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				UserDBConnection.getInstance().close(rs, pstmt, con);
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return resultDTO;
	}

	/**
	 * 회원가입
	 */
	public int insertMember(MemberDTO memberDTO) {
		int cnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		String sql =
			" INSERT INTO MEMBER ( " +
			"     MEMBER_ID, NAME, GRADE_NAME, EMAIL, PASS, PHONE, ZIPCODE, " +
			"     ADDRESS, ADDRESS2, STATE, JOIN_DATE, SNS_RECEIVE_YN, EMAIL_RECEIVE_YN " +
			" ) VALUES ( " +
			"     ?, ?, '일반회원', ?, ?, ?, ?, ?, ?, '활성', SYSDATE, ?, ? " +
			" ) ";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, memberDTO.getMemberCode());
			pstmt.setString(2, memberDTO.getName());
			pstmt.setString(3, memberDTO.getEmail());
			pstmt.setString(4, memberDTO.getPassword());
			pstmt.setString(5, memberDTO.getPhone());
			pstmt.setInt(6, memberDTO.getZipcode());
			pstmt.setString(7, memberDTO.getAddress());
			pstmt.setString(8, memberDTO.getAddress2());
			pstmt.setString(9, String.valueOf(memberDTO.getSmsReceiveYN()));
			pstmt.setString(10, String.valueOf(memberDTO.getEmailReceiveYN()));

			cnt = pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				UserDBConnection.getInstance().close(null, pstmt, con);
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return cnt;
	}

	/**
	 * 아이디 중복 확인
	 */
	public int selectDuplicateId(String memberId) {
		int cnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = " SELECT COUNT(*) CNT FROM MEMBER WHERE MEMBER_ID = ? ";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, memberId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt("CNT");
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				UserDBConnection.getInstance().close(rs, pstmt, con);
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return cnt;
	}

	/**
	 * 아이디 찾기에 필요한 회원 정보를 조회한다.
	 */
	public MemberDTO selectId(MemberDTO memberDTO) {
		MemberDTO resultDTO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql =
			" SELECT MEMBER_ID, NAME, EMAIL " +
			" FROM MEMBER " +
			" WHERE NAME = ? " +
			" AND EMAIL = ? " +
			" AND STATE = '활성' ";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, memberDTO.getName());
			pstmt.setString(2, memberDTO.getEmail());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				resultDTO = new MemberDTO();
				resultDTO.setMemberCode(rs.getString("MEMBER_ID"));
				resultDTO.setName(rs.getString("NAME"));
				resultDTO.setEmail(rs.getString("EMAIL"));
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				UserDBConnection.getInstance().close(rs, pstmt, con);
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return resultDTO;
	}

	/**
	 * 비밀번호 찾기에서 입력한 아이디, 이름, 이메일이 일치하는지 조회한다.
	 */
	public MemberDTO selectMemberForPW(MemberDTO memberDTO) {
		MemberDTO resultDTO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql =
			" SELECT MEMBER_ID, NAME, EMAIL " +
			" FROM MEMBER " +
			" WHERE MEMBER_ID = ? " +
			" AND NAME = ? " +
			" AND EMAIL = ? " +
			" AND STATE = '활성' ";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, memberDTO.getMemberCode());
			pstmt.setString(2, memberDTO.getName());
			pstmt.setString(3, memberDTO.getEmail());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				resultDTO = new MemberDTO();
				resultDTO.setMemberCode(rs.getString("MEMBER_ID"));
				resultDTO.setName(rs.getString("NAME"));
				resultDTO.setEmail(rs.getString("EMAIL"));
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				UserDBConnection.getInstance().close(rs, pstmt, con);
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return resultDTO;
	}

	/**
	 * 비밀번호 찾기에서 발급한 임시 비밀번호를 DB에 저장한다.
	 */
	public int updatePassword(MemberDTO memberDTO) {
		int cnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		String sql =
			" UPDATE MEMBER " +
			" SET PASS = ? " +
			" WHERE MEMBER_ID = ? " +
			" AND STATE = '활성' ";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, memberDTO.getPassword());
			pstmt.setString(2, memberDTO.getMemberCode());

			cnt = pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				UserDBConnection.getInstance().close(null, pstmt, con);
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return cnt;
	}

	/**
	 * 이메일 중복 여부를 확인한다.
	 */
	public int selectDuplicateEmail(String email) {
		int cnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = " SELECT COUNT(*) CNT FROM MEMBER WHERE EMAIL = ? ";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, email);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt("CNT");
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				UserDBConnection.getInstance().close(rs, pstmt, con);
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return cnt;
	}

	/**
	 * 휴대폰 번호가 이미 가입된 회원 정보에 사용 중인지 확인한다.
	 */
	public int selectDuplicatePhone(String phone) {
		int cnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = " SELECT COUNT(*) CNT FROM MEMBER WHERE PHONE = ? ";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, phone);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt("CNT");
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				UserDBConnection.getInstance().close(rs, pstmt, con);
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return cnt;
	}

	public void updateMember(MemberDTO memberDTO) {
		
	}

}
