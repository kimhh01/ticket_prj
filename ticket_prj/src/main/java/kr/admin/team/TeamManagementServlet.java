package kr.admin.team;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

@WebServlet("/teamManagement")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024,
	    maxFileSize = 1024 * 1024 * 2,
	    maxRequestSize = 1024 * 1024 * 10
	)
public class TeamManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private TeamManagementService service;

    @Override
    public void init() throws ServletException {
        service = new TeamManagementService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        getTeamManagementPage(request, response);
    }

    private void getTeamManagementPage(HttpServletRequest request,
                                       HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("activeMenu", "team");

        request.setAttribute("scheduleList",
                service.getGameScheduleList());
        
        request.setAttribute("scheduleSaveList",
                service.getGameScheduleEditList());

        request.setAttribute("teamList",
                service.getTeamList());

        request.setAttribute("teamOptionList",
                service.getTeamOptions());

        request.setAttribute("stadiumOptionList",
                service.getStadiumOptions());

        request.getRequestDispatcher("/manage/team/teamManagement.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        System.out.println("===== TeamManagement POST 진입 =====");
        System.out.println("action = " + action);

        if ("schedule".equals(action)) {
            saveGameSchedule(request, response);
            return;
        }
        
        if ("team".equals(action)) {
            saveTeam(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath()
                + "/teamManagement?error=unknownAction");
    }

    /**
     * 경기 일정 등록 / 수정
     */
    private void saveGameSchedule(HttpServletRequest request,
                                  HttpServletResponse response)
            throws IOException {

        String scheduleMode =
                request.getParameter("scheduleMode");

        System.out.println("===== 경기 일정 저장 =====");
        System.out.println("scheduleMode = " + scheduleMode);
        System.out.println("gameScheduleCode = " + request.getParameter("gameScheduleCode"));
        System.out.println("homeTeamCode = " + request.getParameter("homeTeamCode"));
        System.out.println("awayTeamCode = " + request.getParameter("awayTeamCode"));
        System.out.println("gameDate = " + request.getParameter("gameDate"));
        System.out.println("gameStartTime = " + request.getParameter("gameStartTime"));
        System.out.println("stadiumCode = " + request.getParameter("stadiumCode"));

        ScheduleSaveDTO schedule = new ScheduleSaveDTO();

        schedule.setGameScheduleCode(
                parseInt(request.getParameter("gameScheduleCode")));

        schedule.setHomeTeamCode(
                parseInt(request.getParameter("homeTeamCode")));

        schedule.setAwayTeamCode(
                parseInt(request.getParameter("awayTeamCode")));

        schedule.setGameDate(
                request.getParameter("gameDate"));

        schedule.setGameStartTime(
                request.getParameter("gameStartTime"));

        schedule.setStadiumCode(
                parseInt(request.getParameter("stadiumCode")));

        boolean result = false;

        if ("insert".equals(scheduleMode)) {

            result = service.registerGameSchedule(schedule);

        } else if ("edit".equals(scheduleMode)) {

            result = service.modifyGameSchedule(schedule);

        } else {

            System.out.println("알 수 없는 scheduleMode 값 : " + scheduleMode);
        }

        if (result) {
            response.sendRedirect(request.getContextPath()
                    + "/teamManagement");
        } else {
            response.sendRedirect(request.getContextPath()
                    + "/teamManagement?error=schedule");
        }
    }
    /**
     * 팀 등록
     */
    /**
     * 팀 등록 / 수정
     */
    private void saveTeam(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String teamMode = request.getParameter("teamMode");

        System.out.println("===== 팀 저장 =====");
        System.out.println("teamCode = " + request.getParameter("teamCode"));
        System.out.println("teamMode = " + teamMode);
        System.out.println("teamName = " + request.getParameter("teamName"));
        System.out.println("teamShortName = " + request.getParameter("teamShortName"));
        System.out.println("stadiumCode = " + request.getParameter("stadiumCode"));
        System.out.println("oldTeamLogoImg = " + request.getParameter("oldTeamLogoImg"));

        int teamCode =
                parseInt(request.getParameter("teamCode"));

        int stadiumCode =
                parseInt(request.getParameter("stadiumCode"));

        String teamName =
                request.getParameter("teamName");

        String teamShortName =
                request.getParameter("teamShortName");

        TeamSaveDTO team = new TeamSaveDTO();

        /*
         * 이 코드가 빠져 있어서 수정 시 teamCode가 0으로 넘어갔음
         */
        team.setTeamCode(teamCode);

        team.setTeamName(teamName);
        team.setTeamShortName(teamShortName);
        team.setStadiumCode(stadiumCode);

        /*
         * 새 로고 파일 저장 시도
         */
        String teamLogoPath = saveTeamLogo(request);

        /*
         * 수정 모드에서 새 로고를 선택하지 않았다면 기존 로고 경로 유지
         */
        if (teamLogoPath == null ||
            teamLogoPath.trim().isEmpty()) {

            teamLogoPath =
                    request.getParameter("oldTeamLogoImg");
        }

        /*
         * 등록 모드에서는 로고가 반드시 필요
         */
        if ("insert".equals(teamMode) &&
            (teamLogoPath == null ||
             teamLogoPath.trim().isEmpty())) {

            System.out.println("팀 등록 실패 : 로고 이미지 없음");

            response.sendRedirect(request.getContextPath()
                    + "/teamManagement?tab=teamlist&error=teamLogo");
            return;
        }

        /*
         * 수정 모드에서는 teamCode가 반드시 필요
         */
        if ("edit".equals(teamMode) &&
            teamCode <= 0) {

            System.out.println("팀 수정 실패 : teamCode가 없습니다.");

            response.sendRedirect(request.getContextPath()
                    + "/teamManagement?tab=teamlist&error=teamCode");
            return;
        }

        /*
         * 수정 모드에서도 기존 로고든 새 로고든 최종 경로가 있어야 함
         */
        if (teamLogoPath == null ||
            teamLogoPath.trim().isEmpty()) {

            System.out.println("팀 저장 실패 : 최종 로고 경로가 없습니다.");

            response.sendRedirect(request.getContextPath()
                    + "/teamManagement?tab=teamlist&error=teamLogo");
            return;
        }

        team.setTeamLogoImg(teamLogoPath);

        System.out.println("DTO teamCode = " + team.getTeamCode());
        System.out.println("DTO teamLogoImg = " + team.getTeamLogoImg());

        boolean result = false;

        if ("insert".equals(teamMode)) {

            result = service.registerTeam(team);

        } else if ("edit".equals(teamMode)) {

            result = service.modifyTeam(team);

        } else {

            System.out.println("알 수 없는 teamMode 값 : " + teamMode);
        }

        if (result) {
            response.sendRedirect(request.getContextPath()
                    + "/teamManagement?tab=teamlist");
        } else {
            response.sendRedirect(request.getContextPath()
                    + "/teamManagement?tab=teamlist&error=team");
        }
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
    
    private String saveTeamLogo(HttpServletRequest request)
            throws IOException, ServletException {

        Part filePart = request.getPart("teamLogo");

        if (filePart == null || filePart.getSize() <= 0) {
            System.out.println("업로드된 팀 로고 없음");
            return null;
        }

        String contentType = filePart.getContentType();

        if (!"image/png".equals(contentType) &&
            !"image/jpeg".equals(contentType) &&
            !"image/svg+xml".equals(contentType)) {

            System.out.println("허용되지 않은 팀 로고 타입 : " + contentType);
            return null;
        }

        String originalFileName =
                Paths.get(filePart.getSubmittedFileName())
                     .getFileName()
                     .toString();

        if (originalFileName == null ||
            originalFileName.trim().isEmpty()) {

            System.out.println("팀 로고 원본 파일명 없음");
            return null;
        }

        String extension = "";

        int dotIndex = originalFileName.lastIndexOf(".");

        if (dotIndex != -1) {
            extension = originalFileName.substring(dotIndex);
        }

        String saveFileName =
                "team_" +
                UUID.randomUUID().toString().replace("-", "") +
                extension;

        String uploadRealPath =
                request.getServletContext()
                       .getRealPath("/upload/team");

        System.out.println("team uploadRealPath = " + uploadRealPath);

        File uploadDir = new File(uploadRealPath);

        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            System.out.println("팀 로고 업로드 폴더 생성 여부 = " + created);
        }

        String savePath =
                uploadRealPath + File.separator + saveFileName;

        filePart.write(savePath);

        File savedFile = new File(savePath);

        System.out.println("팀 로고 저장 완료 여부 = " + savedFile.exists());
        System.out.println("팀 로고 저장 크기 = " + savedFile.length());

        return "/upload/team/" + saveFileName;
    }
}