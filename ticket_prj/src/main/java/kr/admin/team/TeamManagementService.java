package kr.admin.team;

import java.util.List;

import common.StadiumOptionDTO;
import common.TeamOptionDTO;

public class TeamManagementService {
	
	private TeamManagementDAO dao = new TeamManagementDAO();


	public List<ScheduleListDTO> getGameScheduleList() {

	    return dao.selectGameScheduleList();
	}
	
	public boolean registerGameSchedule(ScheduleSaveDTO schedule) {
		return false;
	}
	
	public boolean modifyGameSchedule(ScheduleSaveDTO schedule) {
		return false;
	}
	
	public List<TeamInfoDTO> getTeamList() {
	    return dao.selectTeamList();
	}
	
	public boolean registerTeam(TeamSaveDTO team) {
		return false;
	}
	
	public boolean modifyTeam(TeamSaveDTO team) {
		return false;
	}
	
	public boolean deleteTeam(int teamCode) {
		return false;
	}
	
	public List<TeamOptionDTO> getTeamOptions(){
		return null;
	}
	
	public List<StadiumOptionDTO> getStadiumOptions(){
		return null;
	}
}
