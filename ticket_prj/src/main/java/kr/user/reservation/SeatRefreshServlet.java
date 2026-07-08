package kr.user.reservation;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class SeatRefreshServlet
 */
@WebServlet("/seatRefresh")
public class SeatRefreshServlet extends HttpServlet {

    private ReservationService rpService = new ReservationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        try {
            int gameScheduleCode = Integer.parseInt(request.getParameter("gameScheduleCode"));

            ReservationDTO gameInfo = rpService.searchGame(gameScheduleCode);
            List<ReservationDTO> seatList = rpService.searchRemainingSeat(gameInfo.getStadiumCode());

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(seatList));

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
