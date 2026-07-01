package kr.admin.stadium;

import java.util.List;

import kr.admin.common.StadiumOptionDTO;

public class StadiumManagementService {
    
    private StadiumManagementDAO dao;
    
    public StadiumManagementService() {
        dao = new StadiumManagementDAO();
    }
    
    public List<StadiumListDTO> getStadiumList(){
        return dao.selectStadiumList();
    }
    
    public StadiumDetailDTO getStadiumDetail(int stadiumCode){
        return dao.selectStadiumDetail(stadiumCode);
    }
    
    public List<StadiumSeatDTO> getStadiumSeat(int stadiumCode){
        return dao.selectStadiumSeat(stadiumCode);
    }
    
    public boolean registerStadium(StadiumDetailDTO stadium,
                                   StadiumSeatDTO[] seat) {

        try {
            dao.insertStadium(stadium);

            if (seat != null) {

                for (StadiumSeatDTO s : seat) {

                    if (s == null) {
                        continue;
                    }

                    if (s.getSeatName() == null ||
                        s.getSeatName().trim().isEmpty()) {
                        continue;
                    }

                    s.setStadiumCode(stadium.getStadiumCode());

                    dao.insertStadiumSeat(s);
                }
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean registerStadiumSeat(int stadiumCode,
                                       StadiumSeatDTO[] seat) {

        try {
            if (seat == null) {
                return true;
            }
            
            boolean success = false;

            for (StadiumSeatDTO s : seat) {

                if (s == null) {
                    continue;
                }

                if (s.getSeatName() == null ||
                    s.getSeatName().trim().isEmpty()) {
                    continue;
                }

                s.setStadiumCode(stadiumCode);

                dao.insertStadiumSeat(s);
                
                success = true;
            }

            return success;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean modifyStadium(StadiumDetailDTO stadium,
                                 StadiumSeatDTO[] seat) {

        int result = dao.updateStadium(stadium);


        return result>0;
    }
    
    public boolean modifyStadiumSeat(int stadiumCode,
            StadiumSeatDTO[] seat) {

	try {
		if (seat == null) {
		return false;
	}
	
	boolean success = false;
	
	for (StadiumSeatDTO s : seat) {
	
		if (s == null) {
			continue;
		}
		
		if (s.getSeatName() == null ||
				s.getSeatName().trim().isEmpty()) {
			continue;
		}
		
		s.setStadiumCode(stadiumCode);
		
		if (s.getSeatCode() > 0) {
		
			int result = dao.updateStadiumSeat(s);
		
			if (result > 0) {
				success = true;
			}
		
			} else {
		
				dao.insertStadiumSeat(s);
				success = true;
			}
		}
	
		return success;
	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
    
    public boolean deleteStadium(int stadiumCode) {

        int result = dao.deleteStadium(stadiumCode);

        return result > 0;
    }
    
    public boolean deleteStadiumSeat(int stadiumSeatCode) {

        int result = dao.deleteStadiumSeat(stadiumSeatCode);

        return result > 0;
    }
    
    public List<StadiumOptionDTO> getTeamOption(){

        return dao.selectTeamOptions();
    }
}