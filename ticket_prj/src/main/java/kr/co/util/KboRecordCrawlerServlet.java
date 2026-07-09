package kr.co.util;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin/dashboard/kboRecord/run")
public class KboRecordCrawlerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /*
     * 본인이 만든 bat 파일 경로로 수정
     */
    private static final String BAT_PATH =
            "C:/ticket_prj/crawler/run_kbo_record.bat";

    /*
     * 버튼을 여러 번 눌러도 동시에 실행되지 않게 막기
     */
    private static final AtomicBoolean running =
            new AtomicBoolean(false);

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        Integer adminCode =
                (Integer) session.getAttribute("adminCode");

        if (adminCode == null) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/manage/adminLogin/adminLogin.jsp");
            return;
        }

        if (!running.compareAndSet(false, true)) {
            session.setAttribute(
                    "crawlerMsg",
                    "이미 KBO 팀 기록 갱신 작업이 실행 중입니다.");
            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/dashboard");
            return;
        }

        try {
            File batFile =
                    new File(BAT_PATH);

            if (!batFile.exists()) {
                session.setAttribute(
                        "crawlerMsg",
                        "bat 파일을 찾을 수 없습니다. 경로를 확인해 주세요.");
                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/dashboard");
                return;
            }

            ProcessBuilder processBuilder =
                    new ProcessBuilder(
                            "cmd.exe",
                            "/c",
                            "\"" + batFile.getAbsolutePath() + "\"");

            processBuilder.directory(
                    batFile.getParentFile());

            processBuilder.redirectErrorStream(true);

            Process process =
                    processBuilder.start();

            boolean finished =
                    process.waitFor(180, TimeUnit.SECONDS);

            if (!finished) {
                process.destroyForcibly();

                session.setAttribute(
                        "crawlerMsg",
                        "KBO 팀 기록 갱신 시간이 초과되었습니다.");
            } else {
                int exitCode =
                        process.exitValue();

                if (exitCode == 0) {
                    session.setAttribute(
                            "crawlerMsg",
                            "KBO 팀 기록 갱신이 완료되었습니다.");
                } else {
                    session.setAttribute(
                            "crawlerMsg",
                            "KBO 팀 기록 갱신 중 오류가 발생했습니다. exitCode="
                            + exitCode);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

            session.setAttribute(
                    "crawlerMsg",
                    "KBO 팀 기록 갱신 중 오류가 발생했습니다. "
                    + e.getMessage());

        } finally {
            running.set(false);
        }

        response.sendRedirect(
                request.getContextPath()
                + "/admin/dashboard");
    }
}