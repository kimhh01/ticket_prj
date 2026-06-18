package ticket_site.dto;

public class ReservationDetailDTO {

	    private int reservationDetailCode;  // 예매상세코드
	    private int reservationCode;        // 예매코드
	    private int ticketPrice;            // 예매가격
	    private int stadiumSeatCode;        // 구장좌석코드
	    private String reservationType;     // 예매종류
	    private int reservationQuantity;    // 예매수량
	    private String reservationStatus;   // 예매상태

		public ReservationDetailDTO(int reservationDetailCode, int reservationCode, int ticketPrice,
				int stadiumSeatCode, String reservationType, int reservationQuantity, String reservationStatus) {
			super();
			this.reservationDetailCode = reservationDetailCode;
			this.reservationCode = reservationCode;
			this.ticketPrice = ticketPrice;
			this.stadiumSeatCode = stadiumSeatCode;
			this.reservationType = reservationType;
			this.reservationQuantity = reservationQuantity;
			this.reservationStatus = reservationStatus;
		}
	    
	    public int getReservationDetailCode() {
			return reservationDetailCode;
		}

		public void setReservationDetailCode(int reservationDetailCode) {
			this.reservationDetailCode = reservationDetailCode;
		}

		public int getReservationCode() {
			return reservationCode;
		}

		public void setReservationCode(int reservationCode) {
			this.reservationCode = reservationCode;
		}

		public int getTicketPrice() {
			return ticketPrice;
		}

		public void setTicketPrice(int ticketPrice) {
			this.ticketPrice = ticketPrice;
		}

		public int getStadiumSeatCode() {
			return stadiumSeatCode;
		}

		public void setStadiumSeatCode(int stadiumSeatCode) {
			this.stadiumSeatCode = stadiumSeatCode;
		}

		public String getReservationType() {
			return reservationType;
		}

		public void setReservationType(String reservationType) {
			this.reservationType = reservationType;
		}

		public int getReservationQuantity() {
			return reservationQuantity;
		}

		public void setReservationQuantity(int reservationQuantity) {
			this.reservationQuantity = reservationQuantity;
		}

		public String getReservationStatus() {
			return reservationStatus;
		}

		public void setReservationStatus(String reservationStatus) {
			this.reservationStatus = reservationStatus;
		}

		@Override
		public String toString() {
			return "ReservationDetailDTO [reservationDetailCode=" + reservationDetailCode + ", reservationCode="
					+ reservationCode + ", ticketPrice=" + ticketPrice + ", stadiumSeatCode=" + stadiumSeatCode
					+ ", reservationType=" + reservationType + ", reservationQuantity=" + reservationQuantity
					+ ", reservationStatus=" + reservationStatus + "]";
		}

		public ReservationDetailDTO() {
	    }

	}
