package kr.admin.inquiry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import common.BoardRangeDTO;

public class InquiryManagementDAO {
	
    private DataSource ds;

    public InquiryManagementDAO() {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/dbcp");
            System.out.println("DataSource = " + ds);
        } catch (Exception e) {
            throw new RuntimeException("JNDI 조회 실패", e);
        }
    }
	
    public int selectTotalCount(String status) {

        int totalCount = 0;

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(*) total_count ");
        sql.append("   FROM INQUIRY i ");

        if ("waiting".equals(status)) {
            sql.append("  WHERE i.REPLY_CONTENT IS NULL ");
        } else if ("complete".equals(status)) {
            sql.append("  WHERE i.REPLY_CONTENT IS NOT NULL ");
        }

        try (
            Connection con = ds.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery()
        ) {
            if (rs.next()) {
                totalCount = rs.getInt("total_count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalCount;
    }
	
    public List<InquiryListDTO> selectMatchDashboard(BoardRangeDTO range, String status) {

        List<InquiryListDTO> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT * ");
        sql.append("   FROM ( ");
        sql.append("         SELECT ROWNUM rnum, a.* ");
        sql.append("           FROM ( ");
        sql.append("                 SELECT i.INQUIRY_ID, ");
        sql.append("                        c.INQUIRY_TYPE, ");
        sql.append("                        i.INQUIRY_TITLE, ");
        sql.append("                        i.MEMBER_ID, ");
        sql.append("                        TO_CHAR(i.INQUIRY_DATE,'YYYY-MM-DD') INQUIRY_DATE, ");
        sql.append("                        CASE ");
        sql.append("                            WHEN i.REPLY_CONTENT IS NULL THEN '답변대기' ");
        sql.append("                            ELSE '답변완료' ");
        sql.append("                        END STATE ");
        sql.append("                   FROM INQUIRY i ");
        sql.append("                   LEFT JOIN INQUIRY_CATEGORY c ");
        sql.append("                     ON i.INQUIRY_ID = c.INQUIRY_ID ");

        if ("waiting".equals(status)) {
            sql.append("                  WHERE i.REPLY_CONTENT IS NULL ");
        } else if ("complete".equals(status)) {
            sql.append("                  WHERE i.REPLY_CONTENT IS NOT NULL ");
        }

        sql.append("                  ORDER BY i.INQUIRY_ID DESC ");
        sql.append("                ) a ");
        sql.append("          WHERE ROWNUM <= ? ");
        sql.append("       ) ");
        sql.append("  WHERE rnum >= ? ");

        try (
            Connection con = ds.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {
            pstmt.setInt(1, range.getEndNum());
            pstmt.setInt(2, range.getStartNum());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    InquiryListDTO dto = new InquiryListDTO();
                    dto.setInquiryCode(rs.getInt("INQUIRY_ID"));
                    dto.setInquiryType(rs.getString("INQUIRY_TYPE"));
                    dto.setInquiryTitle(rs.getString("INQUIRY_TITLE"));
                    dto.setMemberId(rs.getString("MEMBER_ID"));
                    dto.setInquiryDate(rs.getString("INQUIRY_DATE"));
                    dto.setState(rs.getString("STATE"));

                    list.add(dto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
	
	public InquiryDetailDTO selectInquiryDetail(int inquiryCode) {
		return null;
	}
	
	public int updateInquiryAnswer(InquiryDetailDTO inquiry) {
		return 0;
	}
	
	public int deleteInquiry(int inquiryCode) {
		return inquiryCode;
	}
}
