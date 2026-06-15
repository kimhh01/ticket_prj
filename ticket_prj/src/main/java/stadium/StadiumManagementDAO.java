package stadium;

import java.util.List;

import common.TeamOptionDTO;

public class StadiumManagementDAO {
	
	public List<StadiumListDTO> selectStadiumList(){
		return null;
	}
	
	public StadiumDetailDTO selectStadiumDetail(int stadiumCode){
		return null;	
	}
	
	public List<StadiumSeatDTO> selectStadiumSeat(int stadiumCode){
		return null;
	}
	
	public void insertStadium(StadiumDetailDTO stadium) {
		
	}
	
	public int updateStadium(StadiumDetailDTO stadium) {
		return 0;
	}
	
	public void insertStadiumSeat(StadiumSeatDTO seat) {
		
	}
	
	public int deleteStadium(int stadiumCode) {
		return stadiumCode;
	}
	
	public int deleteStadiumSeat(int stadiumSeatCode) {
		return stadiumSeatCode;
	}
	
	public List<TeamOptionDTO> selectTeamOptions() {
		return null;
	}
}
