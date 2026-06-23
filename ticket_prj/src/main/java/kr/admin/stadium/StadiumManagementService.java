package kr.admin.stadium;

import java.util.List;


import common.StadiumOptionDTO;

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
	
	public boolean registerStadium (StadiumDetailDTO stadium, StadiumSeatDTO[] seat) {
		return false;
		
	}
	
	public boolean registerStadiumSeat (int stadiumCode, StadiumSeatDTO[] seat) {
		return false;
	}
	
	public boolean modifyStadium (StadiumDetailDTO stadium, StadiumSeatDTO[] seat) {
		return false;
	}
	
	public boolean modifyStadiumSeat (int stadiumCode, StadiumSeatDTO[] seat) {
		return false;
	}
	
	public boolean deleteStadium (int stadiumCode) {
		return false;
	}
	
	public boolean deleteStadiumSeat (int stadiumSeatCode) {
		return false;
	}
	
	public List<StadiumOptionDTO> getTeamOption(){
		return null;
	}
}
