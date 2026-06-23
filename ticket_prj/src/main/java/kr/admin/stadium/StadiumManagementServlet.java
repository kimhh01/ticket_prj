package kr.admin.stadium;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/stadium")
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

        request.setAttribute("stadiumList", stadiumList);
        request.setAttribute("stadiumDetailMap", stadiumDetailMap);
        request.setAttribute("seatMap", seatMap);

        request.getRequestDispatcher(
                "/manage/stadium/stadiumManagement.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}