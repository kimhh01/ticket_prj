package kr.admin.notice;

import java.util.List;

import kr.admin.common.TeamOptionDTO;

public class NoticeManagementService {

    private NoticeManagementDAO nDAO;

    public NoticeManagementService() {
        nDAO =
                new NoticeManagementDAO();
    }

    /*
     * 공지사항 목록 조회
     */
    public List<NoticeManagementDTO> getNoticeList(int noticeTab,
                                                   int teamId,
                                                   String keyword) {

        if (keyword == null) {
            keyword = "";
        }

        return nDAO.selectNoticeList(
                noticeTab,
                teamId,
                keyword.trim());
    }

    /*
     * 공지사항 상세 조회
     */
    public NoticeManagementDTO getNoticeDetail(int noticeId) {

        if (noticeId <= 0) {
            return null;
        }

        return nDAO.selectNoticeDetail(
                noticeId);
    }

    /*
     * 공지사항 등록
     */
    public boolean registerNotice(NoticeManagementDTO notice) {

        if (!validateNotice(notice)) {
            return false;
        }

        cleanNotice(notice);

        return nDAO.insertNotice(
                notice);
    }

    /*
     * 공지사항 수정
     */
    public boolean modifyNotice(NoticeManagementDTO notice) {

        if (notice == null ||
            notice.getNoticeId() <= 0) {
            return false;
        }

        if (!validateNotice(notice)) {
            return false;
        }

        cleanNotice(notice);

        return nDAO.updateNotice(
                notice);
    }

    /*
     * 공지사항 삭제
     */
    public boolean removeNotice(int noticeId) {

        if (noticeId <= 0) {
            return false;
        }

        return nDAO.deleteNotice(
                noticeId);
    }

    /*
     * 기존 이미지명 조회
     */
    public String getNoticeImg(int noticeId) {

        if (noticeId <= 0) {
            return null;
        }

        return nDAO.selectNoticeImg(
                noticeId);
    }

    private boolean validateNotice(NoticeManagementDTO notice) {

        if (notice == null) {
            return false;
        }

        if (notice.getTeamId() <= 0) {
            return false;
        }

        if (notice.getNoticeTab() != 1 &&
            notice.getNoticeTab() != 2) {
            return false;
        }

        if (isEmpty(notice.getNoticeTitle())) {
            return false;
        }

        /*
         * 이미지 공지는 이미지가 있을 수 있고,
         * 일반 공지는 이미지 없이 내용만 있을 수 있으므로
         * noticeImg는 필수 검증 안 함.
         */
        if (isEmpty(notice.getNoticeImg())) {
            return false;
        }

        return true;
    }

    private void cleanNotice(NoticeManagementDTO notice) {

        if (notice.getNoticeTitle() != null) {
            notice.setNoticeTitle(
                    notice.getNoticeTitle().trim());
        }

        if (notice.getNoticeImg() != null) {
            notice.setNoticeImg(
                    notice.getNoticeImg().trim());
        }
    }

    private boolean isEmpty(String value) {
        return value == null ||
               value.trim().isEmpty();
    }
    
    public List<TeamOptionDTO> getTeamOptionList() {
        return nDAO.selectTeamOptionList();
    }
}