package kr.admin.notice;

import java.io.File;
import java.io.IOException;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import kr.co.util.UploadPathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import kr.admin.common.TeamOptionDTO;

@WebServlet({
        "/admin/notice",
        "/admin/notice/save",
        "/admin/notice/delete"
})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class NoticeManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private NoticeManagementService noticeManagementService;

    @Override
    public void init() throws ServletException {
        noticeManagementService =
                new NoticeManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String servletPath =
                request.getServletPath();

        if ("/admin/notice/delete".equals(servletPath)) {
            deleteNotice(request, response);
            return;
        }

        getNoticeManagementPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String servletPath =
                request.getServletPath();

        if ("/admin/notice/save".equals(servletPath)) {
            saveNotice(request, response);
            return;
        }

        response.sendRedirect(
                request.getContextPath()
                + "/admin/notice?error=unknownAction");
    }

    private void getNoticeManagementPage(HttpServletRequest request,
                                         HttpServletResponse response)
            throws ServletException, IOException {

        int noticeTab =
                parseInt(request.getParameter("noticeTab"));

        /*
         * 화면에서는 팀명을 select로 보여주지만,
         * 실제 검색 조건은 teamId 값으로 처리한다.
         */
        int teamId =
                parseInt(request.getParameter("teamId"));

        String keyword =
                request.getParameter("keyword");

        if (keyword == null) {
            keyword = "";
        }

        List<NoticeManagementDTO> noticeList =
                noticeManagementService.getNoticeList(
                        noticeTab,
                        teamId,
                        keyword);

        /*
         * 팀 select 박스용 목록
         */
        List<TeamOptionDTO> teamOptionList =
                noticeManagementService.getTeamOptionList();

        request.setAttribute("activeMenu", "notice");
        request.setAttribute("noticeList", noticeList);
        request.setAttribute("teamOptionList", teamOptionList);
        request.setAttribute("noticeTab", noticeTab);
        request.setAttribute("teamId", teamId);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher(
                "/manage/notice/noticeManagement.jsp")
               .forward(request, response);
    }

    private void saveNotice(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException {

        String mode =
                request.getParameter("mode");

        int noticeId =
                parseInt(request.getParameter("noticeId"));

        /*
         * 등록/수정 화면에서는 팀명을 선택하지만,
         * 넘어오는 값은 teamId다.
         */
        int teamId =
                parseInt(request.getParameter("teamId"));

        int noticeTab =
                parseInt(request.getParameter("noticeTab"));

        String noticeTitle =
                request.getParameter("noticeTitle");

        String noticeImg =
                uploadNoticeImage(request);

        String oldImage =
                null;

        if ("edit".equals(mode)) {

            oldImage =
                    noticeManagementService.getNoticeImg(noticeId);

            if (noticeImg == null ||
                noticeImg.trim().isEmpty()) {

                noticeImg =
                        oldImage;
            }
        }

        NoticeManagementDTO notice =
                new NoticeManagementDTO();

        notice.setNoticeId(noticeId);
        notice.setTeamId(teamId);
        notice.setNoticeTab(noticeTab);
        notice.setNoticeTitle(noticeTitle);
        notice.setNoticeImg(noticeImg);

        boolean resultFlag =
                false;

        if ("insert".equals(mode)) {

            resultFlag =
                    noticeManagementService.registerNotice(notice);

        } else if ("edit".equals(mode)) {

            resultFlag =
                    noticeManagementService.modifyNotice(notice);
        }

        if (resultFlag) {

            if (oldImage != null &&
                !oldImage.trim().isEmpty() &&
                !oldImage.equals(noticeImg)) {

                deleteNoticeImageFile(request, oldImage);
            }

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/notice?success=save");

        } else {

            if (noticeImg != null &&
                !noticeImg.trim().isEmpty()) {

                deleteNoticeImageFile(request, noticeImg);
            }

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/notice?error=save");
        }
    }

    private void deleteNotice(HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException {

        int noticeId =
                parseInt(request.getParameter("noticeId"));

        String oldImage =
                noticeManagementService.getNoticeImg(noticeId);

        boolean resultFlag =
                noticeManagementService.removeNotice(noticeId);

        if (resultFlag) {

            if (oldImage != null &&
                !oldImage.trim().isEmpty()) {

                deleteNoticeImageFile(request, oldImage);
            }

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/notice?success=delete");

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/notice?error=delete");
        }
    }

    private String uploadNoticeImage(HttpServletRequest request)
            throws ServletException, IOException {

        Part filePart =
                request.getPart("noticeImgFile");

        if (filePart == null ||
            filePart.getSize() <= 0) {

            System.out.println("공지 이미지 업로드 파일 없음");
            return "";
        }

        String contentType =
                filePart.getContentType();

        if (contentType == null ||
            !contentType.startsWith("image/")) {

            System.out.println("허용되지 않은 공지 이미지 타입 = " + contentType);
            return "";
        }

        String originalFileName =
                Paths.get(filePart.getSubmittedFileName())
                     .getFileName()
                     .toString();

        if (originalFileName == null ||
            originalFileName.trim().isEmpty()) {

            System.out.println("공지 이미지 원본 파일명 없음");
            return "";
        }

        String extension = "";

        int dotIndex =
                originalFileName.lastIndexOf(".");

        if (dotIndex != -1) {
            extension =
                    originalFileName.substring(dotIndex);
        }

        String savedFileName =
                "notice_"
                + UUID.randomUUID()
                      .toString()
                      .replace("-", "")
                + extension;

        File uploadDir =
                UploadPathUtil.getImageUploadDir(
                        getServletContext(),
                        "notice");

        if (!uploadDir.exists()) {
            boolean created =
                    uploadDir.mkdirs();

            System.out.println("공지 이미지 폴더 생성 여부 = " + created);
        }

        File saveFile =
                new File(uploadDir, savedFileName);

        System.out.println("===== 공지 이미지 저장 =====");
        System.out.println("원본 파일명 = " + originalFileName);
        System.out.println("저장 파일명 = " + savedFileName);
        System.out.println("저장 폴더 = " + uploadDir.getAbsolutePath());
        System.out.println("저장 전체 경로 = " + saveFile.getAbsolutePath());

        Files.copy(
                filePart.getInputStream(),
                saveFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );

        System.out.println("공지 이미지 저장 완료 여부 = " + saveFile.exists());
        System.out.println("공지 이미지 저장 크기 = " + saveFile.length());

        /*
         * DB에는 파일명만 저장
         */
        return savedFileName;
    }

	private void deleteNoticeImageFile(HttpServletRequest request, String dbValue) {

		String fileName = extractNoticeFileName(dbValue);

		if (fileName == null || fileName.trim().isEmpty()) {

			return;
		}

		try {

			File uploadDir = UploadPathUtil.getImageUploadDir(getServletContext(), "notice");

			File file = new File(uploadDir, fileName);

			if (file.exists() && file.isFile()) {

				boolean deleted = file.delete();

				System.out.println("공지 이미지 삭제 여부 = " + deleted + ", file = " + file.getAbsolutePath());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    private String extractNoticeFileName(String dbValue) {

        if (dbValue == null ||
            dbValue.trim().isEmpty()) {

            return "";
        }

        String value =
                dbValue.trim()
                       .replace("\\", "/");

        int index =
                value.lastIndexOf("/");

        if (index != -1) {
            value =
                    value.substring(index + 1);
        }

        if (value.contains("..") ||
            value.contains("/") ||
            value.contains("\\")) {

            return "";
        }

        return value;
    }

    private int parseInt(String value) {

        if (value == null ||
            value.trim().isEmpty()) {
            return 0;
        }

        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}