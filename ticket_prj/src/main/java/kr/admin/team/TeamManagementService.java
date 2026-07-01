package kr.admin.team;

import java.util.List;

import kr.admin.common.StadiumOptionDTO;
import kr.admin.common.TeamOptionDTO;

public class TeamManagementService {
	
	private TeamManagementDAO dao = new TeamManagementDAO();


	public List<ScheduleListDTO> getGameScheduleList() {

	    return dao.selectGameScheduleList();
	}
	
	public List<ScheduleSaveDTO> getGameScheduleEditList() {

	    return dao.selectGameScheduleEditList();
	}
	
	public boolean registerGameSchedule(ScheduleSaveDTO schedule) {
		if (!isValidSchedule(schedule)) {
	        return false;
	    }

	    try {
	        dao.insertGameSchedule(schedule);
	        return true;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean modifyGameSchedule(ScheduleSaveDTO schedule) {
		if (!isValidSchedule(schedule)) {
	        return false;
	    }

	    if (schedule.getGameScheduleCode() <= 0) {
	        return false;
	    }

	    int result = dao.updateGameSchedule(schedule);

	    return result > 0;
	}
	
	public List<TeamInfoDTO> getTeamList() {
	    return dao.selectTeamList();
	}
	
	public boolean registerTeam(TeamSaveDTO team) {
		 try {
	            dao.insertTeam(team);
	            return true;

	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	}
	
	public boolean modifyTeam(TeamSaveDTO team) {
		 int result = dao.updateTeam(team);

	     return result > 0;
	}
	
	public boolean deleteTeam(int teamCode) {
		 int result = dao.deleteTeam(teamCode);

	     return result > 0;
	}
	
	public List<TeamOptionDTO> getTeamOptions(){
	    return dao.selecteTeamOption();
	}

	public List<StadiumOptionDTO> getStadiumOptions(){
	    return dao.selecteStadiumOption();
	}
	
	private boolean isValidSchedule(ScheduleSaveDTO schedule) {

	    if (schedule == null) {
	        return false;
	    }

	    if (schedule.getHomeTeamCode() <= 0) {
	        return false;
	    }

	    if (schedule.getAwayTeamCode() <= 0) {
	        return false;
	    }

	    if (schedule.getHomeTeamCode() == schedule.getAwayTeamCode()) {
	        return false;
	    }

	    if (schedule.getStadiumCode() <= 0) {
	        return false;
	    }

	    if (schedule.getGameDate() == null ||
	        schedule.getGameDate().trim().isEmpty()) {
	        return false;
	    }

	    if (schedule.getGameStartTime() == null ||
	        schedule.getGameStartTime().trim().isEmpty()) {
	        return false;
	    }

	    return true;
	}
}
