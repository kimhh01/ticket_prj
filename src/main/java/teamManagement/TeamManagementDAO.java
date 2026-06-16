package teamManagement;

import java.util.List;

import common.StadiumOptionDTO;

public class TeamManagementDAO {
	
	public List<ScheduleListDTO> selectGameScheduleList(){
		return null;	
	}
	
	public void insertGameSchedule(ScheduleSaveDTO schedule){
		
	}
	
	public int updateGameSchedule(ScheduleSaveDTO schedule) {
		return 0;
	}
	
	public List<TeamInfoDTO> selectTeamList(){
		return null;
	}
	
	public void insertTeam(TeamSaveDTO team) {
		
	}
	
	public int updateTeam(TeamSaveDTO team) {
		return 0;
	}
	
	public int deleteTeam(int teamCode) {
		return 0;
	}
	
	public List<StadiumOptionDTO> selecteTeamOption(){
		return null;
	}
	
	public List<StadiumOptionDTO> selecteStadiumOption(){
		return null;
	}
}
