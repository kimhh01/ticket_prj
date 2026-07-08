package kr.admin.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import kr.admin.common.AdminDBConnection;
import kr.admin.common.TeamOptionDTO;

public class NoticeManagementDAO {

    /*
     * 공지사항 목록 조회
     *
     * noticeTab
     * 0 : 전체
     * 1 : 팀별 공지사항
     * 2 : 정규리그 안내
     *
     * teamId
     * 0 : 전체
     */
    public List<NoticeManagementDTO> selectNoticeList(int noticeTab,
                                                      int teamId,
                                                      String keyword) {

        List<NoticeManagementDTO> list =
                new ArrayList<>();

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT n.notice_id, ");
        sql.append("        n.team_id, ");
        sql.append("        NVL(t.team_name, '-') AS team_name, ");
        sql.append("        n.notice_tab, ");
        sql.append("        n.notice_title, ");
        sql.append("        n.notice_img, ");
        sql.append("        TO_CHAR(n.notice_write_date, 'YYYY-MM-DD HH24:MI:SS') AS notice_write_date ");
        sql.append("   FROM notice n ");
        sql.append("   LEFT JOIN team t ");
        sql.append("     ON n.team_id = t.team_id ");
        sql.append("  WHERE 1 = 1 ");

        if (noticeTab > 0) {
            sql.append(" AND n.notice_tab = ? ");
        }

        if (teamId > 0) {
            sql.append(" AND n.team_id = ? ");
        }

        if (hasText(keyword)) {
            sql.append(" AND n.notice_title LIKE '%' || ? || '%' ");
        }

        sql.append("  ORDER BY n.notice_write_date DESC, n.notice_id DESC ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            int index =
                    1;

            if (noticeTab > 0) {
                pstmt.setInt(index++, noticeTab);
            }

            if (teamId > 0) {
                pstmt.setInt(index++, teamId);
            }

            if (hasText(keyword)) {
                pstmt.setString(index++, keyword.trim());
            }

            try (
                ResultSet rs =
                        pstmt.executeQuery()
            ) {

                while (rs.next()) {

                    NoticeManagementDTO dto =
                            new NoticeManagementDTO();

                    dto.setNoticeId(
                            rs.getInt("notice_id"));

                    dto.setTeamId(
                            rs.getInt("team_id"));

                    dto.setTeamName(
                            rs.getString("team_name"));

                    dto.setNoticeTab(
                            rs.getInt("notice_tab"));

                    dto.setNoticeTitle(
                            rs.getString("notice_title"));

                    dto.setNoticeImg(
                            rs.getString("notice_img"));

                    dto.setNoticeWriteDate(
                            rs.getString("notice_write_date"));

                    list.add(dto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /*
     * 공지사항 상세 조회
     */
    public NoticeManagementDTO selectNoticeDetail(int noticeId) {

        NoticeManagementDTO dto =
                null;

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT n.notice_id, ");
        sql.append("        n.team_id, ");
        sql.append("        NVL(t.team_name, '전체') AS team_name, ");
        sql.append("        n.notice_tab, ");
        sql.append("        n.notice_title, ");
        sql.append("        n.notice_img, ");
        sql.append("        TO_CHAR(n.notice_write_date, 'YYYY-MM-DD HH24:MI:SS') AS notice_write_date ");
        sql.append("   FROM notice n ");
        sql.append("   LEFT JOIN team t ");
        sql.append("     ON n.team_id = t.team_id ");
        sql.append("  WHERE n.notice_id = ? ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            pstmt.setInt(1, noticeId);

            try (
                ResultSet rs =
                        pstmt.executeQuery()
            ) {

                if (rs.next()) {

                    dto =
                            new NoticeManagementDTO();

                    dto.setNoticeId(
                            rs.getInt("notice_id"));

                    dto.setTeamId(
                            rs.getInt("team_id"));

                    dto.setTeamName(
                            rs.getString("team_name"));

                    dto.setNoticeTab(
                            rs.getInt("notice_tab"));

                    dto.setNoticeTitle(
                            rs.getString("notice_title"));

                    dto.setNoticeImg(
                            rs.getString("notice_img"));

                    dto.setNoticeWriteDate(
                            rs.getString("notice_write_date"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    /*
     * 공지사항 등록
     */
    public boolean insertNotice(NoticeManagementDTO notice) {

        boolean resultFlag =
                false;

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection()
        ) {

            int noticeId =
                    generateNextNoticeId(con);

            StringBuilder sql =
                    new StringBuilder();

            sql.append(" INSERT INTO notice ( ");
            sql.append("        notice_id, ");
            sql.append("        team_id, ");
            sql.append("        notice_tab, ");
            sql.append("        notice_title, ");
            sql.append("        notice_img, ");
            sql.append("        notice_write_date ");
            sql.append(" ) VALUES ( ");
            sql.append("        ?, ");
            sql.append("        ?, ");
            sql.append("        ?, ");
            sql.append("        ?, ");
            sql.append("        ?, ");
            sql.append("        SYSDATE ");
            sql.append(" ) ");

            try (
                PreparedStatement pstmt =
                        con.prepareStatement(sql.toString())
            ) {

                pstmt.setInt(1, noticeId);
                pstmt.setInt(2, notice.getTeamId());
                pstmt.setInt(3, notice.getNoticeTab());
                pstmt.setString(4, notice.getNoticeTitle());
                pstmt.setString(5, notice.getNoticeImg());

                int result =
                        pstmt.executeUpdate();

                resultFlag =
                        result > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultFlag;
    }

    /*
     * 공지사항 수정
     *
     * noticeImg가 비어 있으면 기존 이미지 유지
     */
    public boolean updateNotice(NoticeManagementDTO notice) {

        boolean resultFlag =
                false;

        StringBuilder sql =
                new StringBuilder();

        sql.append(" UPDATE notice ");
        sql.append("    SET team_id = ?, ");
        sql.append("        notice_tab = ?, ");
        sql.append("        notice_title = ? ");

        if (hasText(notice.getNoticeImg())) {
            sql.append("      , notice_img = ? ");
        }

        sql.append("  WHERE notice_id = ? ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString())
        ) {

            int index =
                    1;

            pstmt.setInt(index++, notice.getTeamId());
            pstmt.setInt(index++, notice.getNoticeTab());
            pstmt.setString(index++, notice.getNoticeTitle());

            if (hasText(notice.getNoticeImg())) {
                pstmt.setString(index++, notice.getNoticeImg());
            }

            pstmt.setInt(index++, notice.getNoticeId());

            int result =
                    pstmt.executeUpdate();

            resultFlag =
                    result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultFlag;
    }

    /*
     * 공지사항 삭제
     */
    public boolean deleteNotice(int noticeId) {

        boolean resultFlag =
                false;

        String sql =
                " DELETE FROM notice WHERE notice_id = ? ";

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql)
        ) {

            pstmt.setInt(1, noticeId);

            int result =
                    pstmt.executeUpdate();

            resultFlag =
                    result > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultFlag;
    }

    /*
     * 기존 이미지 파일명 조회
     * 수정/삭제 시 실제 파일 삭제할 때 사용 가능
     */
    public String selectNoticeImg(int noticeId) {

        String noticeImg =
                null;

        String sql =
                " SELECT notice_img FROM notice WHERE notice_id = ? ";

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql)
        ) {

            pstmt.setInt(1, noticeId);

            try (
                ResultSet rs =
                        pstmt.executeQuery()
            ) {

                if (rs.next()) {
                    noticeImg =
                            rs.getString("notice_img");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return noticeImg;
    }

    /*
     * 공지사항 번호 생성
     *
     * 현재는 MAX + 1 방식.
     * 나중에 NOTICE_SEQ를 만들면 이 메서드만 시퀀스 방식으로 교체하면 됨.
     */
    private int generateNextNoticeId(Connection con)
            throws Exception {

        int nextId =
                1;

        String sql =
                " SELECT NVL(MAX(notice_id), 0) + 1 AS next_id FROM notice ";

        try (
            PreparedStatement pstmt =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pstmt.executeQuery()
        ) {

            if (rs.next()) {
                nextId =
                        rs.getInt("next_id");
            }
        }

        return nextId;
    }

    private boolean hasText(String value) {
        return value != null &&
               !value.trim().isEmpty();
    }
    
    /*
     * 팀 select 박스용 목록 조회
     */
    public List<TeamOptionDTO> selectTeamOptionList() {

        List<TeamOptionDTO> list =
                new ArrayList<>();

        StringBuilder sql =
                new StringBuilder();

        sql.append(" SELECT team_id, ");
        sql.append("        team_name ");
        sql.append("   FROM team ");
        sql.append("  ORDER BY team_name ASC ");

        try (
            Connection con =
                    AdminDBConnection.getInstance().getConnection();

            PreparedStatement pstmt =
                    con.prepareStatement(sql.toString());

            ResultSet rs =
                    pstmt.executeQuery()
        ) {

            while (rs.next()) {

                TeamOptionDTO dto =
                        new TeamOptionDTO();

                dto.setTeamCode(
                        rs.getInt("team_id"));

                dto.setTeamName(
                        rs.getString("team_name"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}