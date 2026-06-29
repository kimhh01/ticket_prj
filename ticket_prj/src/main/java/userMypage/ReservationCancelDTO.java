package userMypage;

import java.sql.Date;

public class ReservationCancelDTO {

    private int cancelCode;          // 취소코드
    private int reservationCode;     // 예매코드
    private String cancelType;       // 취소구분
    private Date cancelDate;         // 취소일자


	public ReservationCancelDTO(int cancelCode, int reservationCode, String cancelType, Date cancelDate) {
		super();
		this.cancelCode = cancelCode;
		this.reservationCode = reservationCode;
		this.cancelType = cancelType;
		this.cancelDate = cancelDate;
	}

	public int getCancelCode() {
		return cancelCode;
	}

	public void setCancelCode(int cancelCode) {
		this.cancelCode = cancelCode;
	}

	public int getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(int reservationCode) {
		this.reservationCode = reservationCode;
	}

	public String getCancelType() {
		return cancelType;
	}

	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public ReservationCancelDTO() {
    }

	@Override
	public String toString() {
		return "ReservationCancelDTO [cancelCode=" + cancelCode + ", reservationCode=" + reservationCode
				+ ", cancelType=" + cancelType + ", cancelDate=" + cancelDate + "]";
	}
}
