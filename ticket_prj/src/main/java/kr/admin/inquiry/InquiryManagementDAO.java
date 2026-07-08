package kr.admin.inquiry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.admin.common.AdminDBConnection;
import kr.admin.common.BoardRangeDTO;

public class InquiryManagementDAO {

    public int selectTotalCount(String status, String keyword, String searchDate) {

        int totalCount = 0;

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT COUNT(*) total_count ");
        sql.append("   FROM INQUIRY i ");
        sql.append("   LEFT JOIN INQUIRY_CATEGORY c ");
        sql.append("     ON i.INQUIRY_CATEGORY_ID = c.INQUIRY_CATEGORY_ID ");
        sql.append("  WHERE 1 = 1 ");

        if ("waiting".equals(status)) {
            sql.append("    AND i.REPLY_CONTENT IS NULL ");
        } else if ("complete".equals(status)) {
            sql.append("    AND i.REPLY_CONTENT IS NOT NULL ");
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("    AND ( ");
            sql.append("          LOWER(i.INQUIRY_TITLE) LIKE ? ");
            sql.append("       OR LOWER(i.MEMBER_ID) LIKE ? ");
            sql.append("       OR LOWER(c.INQUIRY_TYPE) LIKE ? ");
            sql.append("        ) ");
        }

        if (searchDate != null && !searchDate.trim().isEmpty()) {
            sql.append("    AND TRUNC(i.INQUIRY_DATE) = TO_DATE(?, 'YYYY-MM-DD') ");
        }

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            int index = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchKeyword = "%" + keyword.trim().toLowerCase() + "%";

                pstmt.setString(index++, searchKeyword);
                pstmt.setString(index++, searchKeyword);
                pstmt.setString(index++, searchKeyword);
            }

            if (searchDate != null && !searchDate.trim().isEmpty()) {
                pstmt.setString(index++, searchDate);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalCount = rs.getInt("total_count");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalCount;
    }

    public List<InquiryListDTO> selectMatchDashboard(
            BoardRangeDTO range,
            String status,
            String keyword,
            String searchDate
    ) {

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
        sql.append("                        TO_CHAR(i.INQUIRY_DATE, 'YYYY-MM-DD') INQUIRY_DATE, ");
        sql.append("                        CASE ");
        sql.append("                            WHEN i.REPLY_CONTENT IS NULL THEN '답변대기' ");
        sql.append("                            ELSE '답변완료' ");
        sql.append("                        END STATE ");
        sql.append("                   FROM INQUIRY i ");
        sql.append("                   LEFT JOIN INQUIRY_CATEGORY c ");
        sql.append("                     ON i.INQUIRY_CATEGORY_ID = c.INQUIRY_CATEGORY_ID ");
        sql.append("                  WHERE 1 = 1 ");

        if ("waiting".equals(status)) {
            sql.append("                    AND i.REPLY_CONTENT IS NULL ");
        } else if ("complete".equals(status)) {
            sql.append("                    AND i.REPLY_CONTENT IS NOT NULL ");
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("                    AND ( ");
            sql.append("                          LOWER(i.INQUIRY_TITLE) LIKE ? ");
            sql.append("                       OR LOWER(i.MEMBER_ID) LIKE ? ");
            sql.append("                       OR LOWER(c.INQUIRY_TYPE) LIKE ? ");
            sql.append("                        ) ");
        }

        if (searchDate != null && !searchDate.trim().isEmpty()) {
            sql.append("                    AND TRUNC(i.INQUIRY_DATE) = TO_DATE(?, 'YYYY-MM-DD') ");
        }

        sql.append("                  ORDER BY i.INQUIRY_ID DESC ");
        sql.append("                ) a ");
        sql.append("          WHERE ROWNUM <= ? ");
        sql.append("       ) ");
        sql.append("  WHERE rnum >= ? ");

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            int index = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchKeyword = "%" + keyword.trim().toLowerCase() + "%";

                pstmt.setString(index++, searchKeyword);
                pstmt.setString(index++, searchKeyword);
                pstmt.setString(index++, searchKeyword);
            }

            if (searchDate != null && !searchDate.trim().isEmpty()) {
                pstmt.setString(index++, searchDate);
            }

            pstmt.setInt(index++, range.getEndNum());
            pstmt.setInt(index++, range.getStartNum());

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

        InquiryDetailDTO dto = null;

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT i.INQUIRY_ID, ");
        sql.append("        i.INQUIRY_CONTENT, ");
        sql.append("        i.REPLY_CONTENT, ");
        sql.append("        TO_CHAR(i.REPLY_DATE, 'YYYY-MM-DD HH24:MI:SS') REPLY_DATE, ");
        sql.append("        i.ADMIN_ID ");
        sql.append("   FROM INQUIRY i ");
        sql.append("  WHERE i.INQUIRY_ID = ? ");

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, inquiryCode);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    dto = new InquiryDetailDTO();

                    dto.setInquiryCode(rs.getInt("INQUIRY_ID"));
                    dto.setInquiryContent(rs.getString("INQUIRY_CONTENT"));
                    dto.setReplyContent(rs.getString("REPLY_CONTENT"));
                    dto.setReplyDate(rs.getString("REPLY_DATE"));
                    dto.setAdminId(rs.getString("ADMIN_ID"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    public int updateInquiryAnswer(InquiryDetailDTO inquiry) {

        int result = 0;

        StringBuilder sql = new StringBuilder();

        sql.append(" UPDATE INQUIRY ");
        sql.append("    SET REPLY_CONTENT = ?, ");
        sql.append("        REPLY_DATE = SYSDATE, ");
        sql.append("        ADMIN_ID = ? ");
        sql.append("  WHERE INQUIRY_ID = ? ");

        try (
            Connection con = AdminDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql.toString())
        ) {

            pstmt.setString(1, inquiry.getReplyContent());
            pstmt.setString(2, inquiry.getAdminId());
            pstmt.setInt(3, inquiry.getInquiryCode());

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}