package kr.user.main;

import java.util.List;

public class MainService {

	private MainDAO mainDAO;

	public MainService() {
		mainDAO = new MainDAO();
	}

	/**
	 * 메인화면에 출력할 최근 경기 목록 조회
	 * 현재 날짜 기준 가까운 경기 3개를 조회한다.
	 * 
	 * @return 최근 경기 목록
	 */
	public List<MainGameDTO> getRecentGameList() {
		return mainDAO.selectRecentGameList();
	}

	/**
	 * 메인화면에 출력할 팀 순위 목록 조회
	 * DB에 저장된 팀 순위 데이터를 순위 기준으로 조회한다.
	 * 
	 * @return 팀 순위 목록
	 */
	public List<TeamRankDTO> getTeamRankList() {
		return mainDAO.selectTeamRankList();
	}

	/**
	 * 메인화면 상단 배너 목록 조회
	 * 사용 상태인 배너만 출력 순서 기준으로 조회한다.
	 * 
	 * @return 메인 배너 목록
	 */
	public List<MainBannerDTO> getMainBannerList() {
		return mainDAO.selectMainBannerList();
	}

	/**
	 * 메인화면 하단 이벤트 배너 조회
	 * 진행 중인 이벤트 중 메인에 노출할 이벤트를 조회한다.
	 * 
	 * @return 메인 이벤트 배너 정보
	 * <생성 후 연결>
	 */
//	public EventDTO getMainEventBanner() {
//		return mainDAO.selectMainEventBanner();
//	}
}