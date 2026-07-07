package kr.user.main;

import java.util.ArrayList;
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
	 * 메인화면 상단 배너 목록 구성
	 *
	 * 
	 * @return 출력 순서대로 구성한 메인 배너 목록
	 */
	public List<MainBannerDTO> getMainBannerList() {
		List<MainBannerDTO> bannerList = new ArrayList<MainBannerDTO>();

		// 배너 전용 DB 테이블이 생기기 전까지 이미지와 연결 팀을 서비스에서 관리한다.
		bannerList.add(new MainBannerDTO(1, 1, "lg_banner.png", 1, "Y"));
		bannerList.add(new MainBannerDTO(2, 3, "hanhwa_banner.png", 2, "Y"));
		bannerList.add(new MainBannerDTO(3, 8, "samsung_banner.png", 3, "Y"));
		bannerList.add(new MainBannerDTO(4, 10, "kt_banner.png", 4, "Y"));
		bannerList.add(new MainBannerDTO(5, 5, "kia_banner.png", 5, "Y"));
		bannerList.add(new MainBannerDTO(6, 6, "nc_banner.png", 6, "Y"));
		bannerList.add(new MainBannerDTO(7, 7, "ssg_banner.png", 7, "Y"));
		bannerList.add(new MainBannerDTO(8, 2, "doosan_banner.png", 8, "Y"));
		bannerList.add(new MainBannerDTO(9, 9, "kiwoom_banner.png", 9, "Y"));
		bannerList.add(new MainBannerDTO(10, 4, "lotte_banner.png", 10, "Y"));

		return bannerList;
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
