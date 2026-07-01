package kr.admin.stadium;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import kr.admin.common.StadiumOptionDTO;

@WebServlet({
    "/stadium",
    "/stadium/seat"
})

@MultipartConfig(
	fileSizeThreshold = 1024 * 1024,
	maxFileSize = 1024 * 1024 * 10,
	maxRequestSize = 1024 * 1024 * 30
)

public class StadiumManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private StadiumManagementService service;

    @Override
    public void init() throws ServletException {
        service = new StadiumManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if ("/stadium/seat".equals(servletPath)) {
            response.sendRedirect(request.getContextPath() + "/stadium");
            return;
        }

        getStadiumPage(request, response);
    }

    /**
     * 구장 관리 목록 화면
     */
    private void getStadiumPage(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("activeMenu", "stadium");

        List<StadiumListDTO> stadiumList =
                service.getStadiumList();

        Map<Integer, StadiumDetailDTO> stadiumDetailMap =
                new HashMap<>();

        Map<Integer, List<StadiumSeatDTO>> seatMap =
                new HashMap<>();

        for (StadiumListDTO dto : stadiumList) {

            int stadiumCode = dto.getStadiumCode();

            StadiumDetailDTO detail =
                    service.getStadiumDetail(stadiumCode);

            List<StadiumSeatDTO> seatList =
                    service.getStadiumSeat(stadiumCode);

            stadiumDetailMap.put(stadiumCode, detail);
            seatMap.put(stadiumCode, seatList);
        }

        List<StadiumOptionDTO> teamOptionList =
                service.getTeamOption();

        request.setAttribute("stadiumList", stadiumList);
        request.setAttribute("stadiumDetailMap", stadiumDetailMap);
        request.setAttribute("seatMap", seatMap);
        request.setAttribute("teamOptionList", teamOptionList);

        request.getRequestDispatcher(
                "/manage/stadium/stadiumManagement.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String servletPath = request.getServletPath();

        if ("/stadium/seat".equals(servletPath)) {
            saveStadiumSeat(request, response);
            return;
        }

        saveStadium(request, response);
    }

    /**
     * 구장 등록 / 수정
     */
    /**
     * 구장 등록 / 수정
     */
    private void saveStadium(HttpServletRequest request,
                             HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("===== Stadium POST 진입 =====");

        String mode =
                request.getParameter("mode");

        System.out.println("mode = " + mode);
        System.out.println("stadiumCode = " + request.getParameter("stadiumCode"));
        System.out.println("stadiumName = " + request.getParameter("stadiumName"));
        System.out.println("stadiumRegion = " + request.getParameter("stadiumRegion"));
        System.out.println("stadiumAddr = " + request.getParameter("stadiumAddr"));
        System.out.println("homeTeamCode = " + request.getParameter("homeTeamCode"));
        System.out.println("homeTeamCode2 = " + request.getParameter("homeTeamCode2"));
        System.out.println("oldStadiumSeatImg = " + request.getParameter("oldStadiumSeatImg"));

        int stadiumCode =
                parseInt(request.getParameter("stadiumCode"));

        int homeTeamCode =
                parseInt(request.getParameter("homeTeamCode"));

        int homeTeamCode2 =
                parseInt(request.getParameter("homeTeamCode2"));

        /*
         * 홈팀 1은 필수
         */
        if (homeTeamCode <= 0) {

            System.out.println("구장 저장 실패 : 홈팀 1이 선택되지 않았습니다.");

            response.sendRedirect(
                    request.getContextPath()
                    + "/stadium?error=team");

            return;
        }

        /*
         * 홈팀 2는 선택.
         * 단, 선택했다면 홈팀 1과 달라야 함.
         */
        if (homeTeamCode2 > 0 &&
            homeTeamCode == homeTeamCode2) {

            System.out.println("구장 저장 실패 : 홈팀 1과 홈팀 2가 같습니다.");

            response.sendRedirect(
                    request.getContextPath()
                    + "/stadium?error=sameTeam");

            return;
        }

        /*
         * 수정 모드라면 stadiumCode 필수
         */
        if ("edit".equals(mode) &&
            stadiumCode <= 0) {

            System.out.println("구장 수정 실패 : stadiumCode가 없습니다.");

            response.sendRedirect(
                    request.getContextPath()
                    + "/stadium?error=stadiumCode");

            return;
        }

        /*
         * 1. 새 이미지가 있으면 저장 후 새 경로 반환
         * 2. 새 이미지가 없으면 null 반환
         */
        String stadiumSeatImgPath =
                saveStadiumImage(request);

        /*
         * 수정 모드에서 새 이미지를 선택하지 않은 경우
         * 기존 이미지 경로를 그대로 사용
         */
        if (stadiumSeatImgPath == null ||
            stadiumSeatImgPath.trim().isEmpty()) {

            stadiumSeatImgPath =
                    request.getParameter("oldStadiumSeatImg");
        }

        /*
         * 등록 모드에서는 이미지가 반드시 있어야 함
         */
        if ("insert".equals(mode) &&
            (stadiumSeatImgPath == null ||
             stadiumSeatImgPath.trim().isEmpty())) {

            System.out.println("구장 등록 실패 : 이미지 파일이 없습니다.");

            response.sendRedirect(
                    request.getContextPath()
                    + "/stadium?error=image");

            return;
        }

        StadiumDetailDTO stadium =
                new StadiumDetailDTO();

        stadium.setStadiumCode(stadiumCode);
        stadium.setStadiumName(request.getParameter("stadiumName"));
        stadium.setStadiumLocation(request.getParameter("stadiumRegion"));
        stadium.setAddress(request.getParameter("stadiumAddr"));

        /*
         * 중요:
         * homeTeamCode2까지 반드시 DTO에 넣어야 DAO에서 저장 가능
         */
        stadium.setHomeTeamCode(homeTeamCode);
        stadium.setHomeTeamCode2(homeTeamCode2);

        /*
         * DB에는 이미지 파일이 아니라 이미지 경로가 저장됨
         */
        stadium.setStadiumSeatImg(stadiumSeatImgPath);

        System.out.println("===== 구장 DTO 확인 =====");
        System.out.println("stadiumCode = " + stadium.getStadiumCode());
        System.out.println("homeTeamCode = " + stadium.getHomeTeamCode());
        System.out.println("homeTeamCode2 = " + stadium.getHomeTeamCode2());
        System.out.println("stadiumSeatImgPath = " + stadiumSeatImgPath);

        boolean result = false;

        if ("insert".equals(mode)) {

            result =
                    service.registerStadium(stadium, null);

        } else if ("edit".equals(mode)) {

            result =
                    service.modifyStadium(stadium, null);

        } else {

            System.out.println("알 수 없는 구장 mode 값 : " + mode);
        }

        if (result) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/stadium");

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/stadium?error=save");
        }
    }

    /**
     * 좌석 등록 / 수정
     */
    private void saveStadiumSeat(HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException {

        System.out.println("===== Stadium Seat POST 진입 =====");

        String seatMode = request.getParameter("seatMode");

        System.out.println("seatMode = " + seatMode);
        System.out.println("stadiumCode = " + request.getParameter("stadiumCode"));
        System.out.println("seatCode = " + request.getParameter("seatCode"));
        System.out.println("seatName = " + request.getParameter("seatName"));
        System.out.println("adultPrice = " + request.getParameter("adultPrice"));
        System.out.println("youthPrice = " + request.getParameter("youthPrice"));
        System.out.println("childPrice = " + request.getParameter("childPrice"));
        System.out.println("seatQty = " + request.getParameter("seatQty"));

        int stadiumCode =
                parseInt(request.getParameter("stadiumCode"));

        int seatCode =
                parseInt(request.getParameter("seatCode"));

        StadiumSeatDTO seat = new StadiumSeatDTO();

        seat.setStadiumCode(stadiumCode);
        seat.setSeatCode(seatCode);
        seat.setSeatName(request.getParameter("seatName"));
        seat.setAdultPrice(parseInt(request.getParameter("adultPrice")));
        seat.setYouthPrice(parseInt(request.getParameter("youthPrice")));
        seat.setChildPrice(parseInt(request.getParameter("childPrice")));
        seat.setSeatQty(parseInt(request.getParameter("seatQty")));

        StadiumSeatDTO[] seatArray = { seat };

        boolean result = false;

        if ("insert".equals(seatMode)) {

            result = service.registerStadiumSeat(
                    stadiumCode,
                    seatArray);

        } else if ("edit".equals(seatMode)) {

            result = service.modifyStadiumSeat(
                    stadiumCode,
                    seatArray);

        } else {

            System.out.println("알 수 없는 좌석 mode 값 : " + seatMode);
        }

        if (result) {
            response.sendRedirect(request.getContextPath() + "/stadium");
        } else {
            response.sendRedirect(request.getContextPath() + "/stadium?error=seat");
        }
    }
    
    private String saveStadiumImage(HttpServletRequest request)
            throws IOException, ServletException {

        Part filePart = request.getPart("stadiumSeatImg");

        if (filePart == null || filePart.getSize() <= 0) {
            return null;
        }

        String originalFileName =
                Paths.get(filePart.getSubmittedFileName())
                     .getFileName()
                     .toString();

        if (originalFileName == null ||
            originalFileName.trim().isEmpty()) {
            return null;
        }

        String extension = "";

        int dotIndex = originalFileName.lastIndexOf(".");

        if (dotIndex != -1) {
            extension = originalFileName.substring(dotIndex);
        }

        String saveFileName =
                "stadium_" +
                UUID.randomUUID().toString().replace("-", "") +
                extension;

        String uploadRealPath =
                request.getServletContext()
                       .getRealPath("/upload/stadium");

        File uploadDir = new File(uploadRealPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String savePath =
                uploadRealPath + File.separator + saveFileName;

        filePart.write(savePath);

        /*
         * DB에 저장할 경로
         */
        return "/upload/stadium/" + saveFileName;
    }

    private int parseInt(String value) {

        if (value == null || value.trim().isEmpty()) {
            return 0;
        }

        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }
}