package kr.admin.event;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import kr.admin.common.BoardRangeDTO;

@WebServlet({
    "/event",
    "/event/edit",
    "/event/save",
    "/event/delete"
})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 30
)
public class EventManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private EventManagementService service;

    @Override
    public void init() throws ServletException {
        service = new EventManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("activeMenu", "event");

        String servletPath =
                request.getServletPath();

        if ("/event/edit".equals(servletPath)) {
            getEventEditPage(request, response);
            return;
        }

        if ("/event/delete".equals(servletPath)) {
            deleteEvent(request, response);
            return;
        }

        getEventListPage(request, response);
    }

    /**
     * 이벤트 목록 페이지
     */
    private void getEventListPage(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {

        int currentPage = 1;

        String pageParam =
                request.getParameter("page");

        if (pageParam != null &&
            !pageParam.trim().isEmpty()) {

            try {
                currentPage =
                        Integer.parseInt(pageParam);
            } catch (Exception e) {
                currentPage = 1;
            }
        }

        BoardRangeDTO range =
                new BoardRangeDTO();

        int pageScale =
                service.pageScale();

        range.setPage(currentPage);
        range.setPageScale(pageScale);

        range.setStartNum(
                service.startNum(
                        currentPage,
                        pageScale));

        range.setEndNum(
                service.endNum(
                        currentPage,
                        pageScale));

        int totalCount =
                service.totalCount(range);

        range.setTotalCount(totalCount);

        range.setTotalPage(
                service.totalPage(
                        totalCount,
                        pageScale));

        List<EventListDTO> eventList =
                service.getEventDashboardList(range);

        request.setAttribute("eventList", eventList);
        request.setAttribute("range", range);

        request.getRequestDispatcher(
                "/manage/event/eventManagement.jsp")
               .forward(request, response);
    }

    /**
     * 이벤트 등록 / 수정 페이지
     *
     * /event/edit
     *   → 등록 화면
     *
     * /event/edit?eventCode=1
     *   → 수정 화면
     */
	private void getEventEditPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int eventCode = parseInt(request.getParameter("eventCode"));

		EventDetailDTO event = null;

		String mode = "insert";

		if (eventCode > 0) {

			event = service.getEventDetail(eventCode);

			if (event == null) {
				response.sendRedirect(request.getContextPath() + "/event?error=notfound");
				return;
			}

			mode = "edit";
		}

		request.setAttribute("event", event);
		request.setAttribute("mode", mode);

		request.getRequestDispatcher("/manage/event/eventDetail.jsp").forward(request, response);
	}

    /**
     * 이벤트 삭제
     */
    private void deleteEvent(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException {

        int eventCode =
                parseInt(request.getParameter("eventCode"));

        boolean result = false;

        if (eventCode > 0) {
            result =
                    service.removeEvent(eventCode);
        }

        if (result) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/event");
        } else {
            response.sendRedirect(
                    request.getContextPath()
                    + "/event?error=delete");
        }
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String servletPath = request.getServletPath();

		if ("/event/save".equals(servletPath)) {
			saveEvent(request, response);
			return;
		}

		response.sendRedirect(request.getContextPath() + "/event?error=unknownAction");
	}

	/**
	 * 이벤트 등록 / 수정 저장
	 */
	private void saveEvent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String mode = request.getParameter("mode");

		int eventCode = parseInt(request.getParameter("eventCode"));

		String eventTitle = request.getParameter("eventTitle");

		String eventSummary = request.getParameter("eventSummary");

		String startDate = request.getParameter("startDate");

		String endDate = request.getParameter("endDate");

		String eventContent = request.getParameter("eventContent");

		boolean isDiscount = request.getParameter("isDiscount") != null;

		/*
		 * TEAM_ID는 더 이상 사용하지 않음. 할인 쿠폰 번호는 EVENT_DISCOUNT.DISCOUNT_RULE_CODE가 자동 발급됨.
		 */
		int discountRate = parseInt(request.getParameter("discountRate"));

		String oldThumbnailImg = request.getParameter("oldThumbnailImg");

		String oldRepresentativeImg = request.getParameter("oldRepresentativeImg");

		String thumbnailDeleted = request.getParameter("thumbnailDeleted");

		String representativeDeleted = request.getParameter("representativeDeleted");

		System.out.println("===== 이벤트 저장 =====");
		System.out.println("mode = " + mode);
		System.out.println("eventCode = " + eventCode);
		System.out.println("eventTitle = " + eventTitle);
		System.out.println("eventSummary = " + eventSummary);
		System.out.println("startDate = " + startDate);
		System.out.println("endDate = " + endDate);
		System.out.println("isDiscount = " + isDiscount);
		System.out.println("discountRate = " + discountRate);
		System.out.println("oldThumbnailImg = " + oldThumbnailImg);
		System.out.println("oldRepresentativeImg = " + oldRepresentativeImg);
		System.out.println("thumbnailDeleted = " + thumbnailDeleted);
		System.out.println("representativeDeleted = " + representativeDeleted);

		/*
		 * 새 이미지 저장
		 *
		 * DB에는 /upload/event/ 경로를 저장하지 않고 파일명만 저장한다.
		 */
		String newThumbnailImg = saveEventImage(request, "thumbnailImgFile", "event_thumb_");

		String newRepresentativeImg = saveEventImage(request, "representativeImgFile", "event_rep_");

		String thumbnailImg = null;
		String representativeImg = null;

		/*
		 * 썸네일 최종 파일명 결정
		 */
		if (newThumbnailImg != null && !newThumbnailImg.trim().isEmpty()) {

			thumbnailImg = newThumbnailImg;

		} else if (!"Y".equalsIgnoreCase(thumbnailDeleted)) {

			thumbnailImg = oldThumbnailImg;
		}

		/*
		 * 대표 이미지 최종 파일명 결정
		 */
		if (newRepresentativeImg != null && !newRepresentativeImg.trim().isEmpty()) {

			representativeImg = newRepresentativeImg;

		} else if (!"Y".equalsIgnoreCase(representativeDeleted)) {

			representativeImg = oldRepresentativeImg;
		}

		/*
		 * 등록 / 수정 공통 필수 검증
		 */
		if (eventTitle == null || eventTitle.trim().isEmpty() || eventSummary == null || eventSummary.trim().isEmpty()
				|| startDate == null || startDate.trim().isEmpty() || endDate == null || endDate.trim().isEmpty()
				|| eventContent == null || eventContent.trim().isEmpty()) {

			redirectSaveFail(request, response, mode, eventCode, "required");
			return;
		}

		/*
		 * 이미지 2개 모두 필수
		 */
		if (thumbnailImg == null || thumbnailImg.trim().isEmpty()) {

			redirectSaveFail(request, response, mode, eventCode, "thumbnail");
			return;
		}

		if (representativeImg == null || representativeImg.trim().isEmpty()) {

			redirectSaveFail(request, response, mode, eventCode, "representative");
			return;
		}

		/*
		 * 할인 이벤트라면 할인율만 필수. 쿠폰 번호는 DAO에서 DISCOUNT_RULE_CODE로 자동 생성.
		 */
		if (isDiscount) {

			if (discountRate <= 0 || discountRate > 100) {

				redirectSaveFail(request, response, mode, eventCode, "discountRate");

				return;
			}

		} else {

			discountRate = 0;
		}

		/*
		 * 수정 모드라면 eventCode 필수
		 */
		if ("edit".equals(mode) && eventCode <= 0) {

			response.sendRedirect(request.getContextPath() + "/event?error=eventCode");
			return;
		}

		int adminId = 0;

		Object adminCodeObj = request.getSession().getAttribute("adminCode");

		if (adminCodeObj != null) {
			adminId = parseInt(String.valueOf(adminCodeObj));
		}

		if (adminId <= 0) {
			response.sendRedirect(request.getContextPath() + "/manage/adminLogin/adminLogin.jsp");
			return;
		}

		EventDetailDTO event = new EventDetailDTO();

		event.setEventCode(eventCode);
		event.setAdminId(adminId);

		event.setEventTitle(eventTitle);
		event.setEventSummary(eventSummary);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setEventContent(eventContent);

		event.setThumbnailImg(thumbnailImg);
		event.setRepresentativeImg(representativeImg);

		event.setDiscount(isDiscount);
		event.setDiscountRate(discountRate);

		System.out.println("DTO = " + event);

		boolean result = false;

		if ("insert".equals(mode)) {

			result = service.registerEvent(event);

		} else if ("edit".equals(mode)) {

			result = service.modifyEvent(event);

		} else {

			System.out.println("알 수 없는 이벤트 mode 값 : " + mode);
		}

		if (result) {

			response.sendRedirect(request.getContextPath() + "/event");

		} else {

			redirectSaveFail(request, response, mode, eventCode, "save");
		}
	}

    /**
     * 이벤트 이미지 저장
     *
     * @param request multipart request
     * @param partName input type=file name
     * @param prefix 저장 파일명 prefix
     * @return 저장된 파일명. 업로드 파일이 없으면 null.
     */
    private String saveEventImage(HttpServletRequest request,
                                  String partName,
                                  String prefix)
            throws IOException, ServletException {

        Part filePart =
                request.getPart(partName);

        if (filePart == null ||
            filePart.getSize() <= 0) {

            System.out.println(partName + " 업로드 파일 없음");
            return null;
        }

        String contentType =
                filePart.getContentType();

        if (!"image/png".equals(contentType) &&
            !"image/jpeg".equals(contentType) &&
            !"image/svg+xml".equals(contentType) &&
            !"image/webp".equals(contentType)) {

            System.out.println(
                    "허용되지 않은 이벤트 이미지 타입 : "
                    + contentType);

            return null;
        }

        String originalFileName =
                Paths.get(
                        filePart.getSubmittedFileName())
                     .getFileName()
                     .toString();

        if (originalFileName == null ||
            originalFileName.trim().isEmpty()) {

            return null;
        }

        String extension = "";

        int dotIndex =
                originalFileName.lastIndexOf(".");

        if (dotIndex != -1) {
            extension =
                    originalFileName.substring(dotIndex);
        }

        String saveFileName =
                prefix
                + UUID.randomUUID()
                      .toString()
                      .replace("-", "")
                + extension;

        String uploadRealPath =
                request.getServletContext()
                       .getRealPath("/upload/event");

        System.out.println("event uploadRealPath = " + uploadRealPath);

        File uploadDir =
                new File(uploadRealPath);

        if (!uploadDir.exists()) {
            boolean created =
                    uploadDir.mkdirs();

            System.out.println("이벤트 업로드 폴더 생성 여부 = " + created);
        }

        String savePath =
                uploadRealPath
                + File.separator
                + saveFileName;

        filePart.write(savePath);

        File savedFile =
                new File(savePath);

        System.out.println(partName + " 저장 완료 여부 = "
                + savedFile.exists());

        System.out.println(partName + " 저장 크기 = "
                + savedFile.length());

        /*
         * DB에는 파일명만 저장
         */
        return saveFileName;
    }

    /**
     * 저장 실패 시 등록/수정 페이지로 복귀
     */
    private void redirectSaveFail(HttpServletRequest request,
                                  HttpServletResponse response,
                                  String mode,
                                  int eventCode,
                                  String errorCode)
            throws IOException {

        if ("edit".equals(mode) &&
            eventCode > 0) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/event/edit?eventCode="
                    + eventCode
                    + "&error="
                    + errorCode);

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/event/edit?error="
                    + errorCode);
        }
    }

    private int parseInt(String value) {

        if (value == null ||
            value.trim().isEmpty()) {
            return 0;
        }

        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }
}