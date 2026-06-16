package kr.co.baseball.main;

public class MainBannerDTO {

	private int bannerCode;
	private String bannerImg;
	private int bannerOrder;
	private String bannerStatus;
	private int teamCode;



	public MainBannerDTO() {
	}

	public MainBannerDTO(int bannerCode, int teamCode, String bannerImg, int bannerOrder, String bannerStatus) {
		this.bannerCode = bannerCode;
		this.teamCode = teamCode;
		this.bannerImg = bannerImg;
		this.bannerOrder = bannerOrder;
		this.bannerStatus = bannerStatus;
	}

	public int getBannerCode() {
		return bannerCode;
	}

	public void setBannerCode(int bannerCode) {
		this.bannerCode = bannerCode;
	}

	public int getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(int teamCode) {
		this.teamCode = teamCode;
	}

	public String getBannerImg() {
		return bannerImg;
	}

	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}

	public int getBannerOrder() {
		return bannerOrder;
	}

	public void setBannerOrder(int bannerOrder) {
		this.bannerOrder = bannerOrder;
	}

	public String getBannerStatus() {
		return bannerStatus;
	}

	public void setBannerStatus(String bannerStatus) {
		this.bannerStatus = bannerStatus;
	}

	@Override
	public String toString() {
		return "MainBannerDTO [bannerCode=" + bannerCode + ", teamCode=" + teamCode + ", bannerImg=" + bannerImg
				+ ", bannerOrder=" + bannerOrder + ", bannerStatus=" + bannerStatus + "]";
	}
}