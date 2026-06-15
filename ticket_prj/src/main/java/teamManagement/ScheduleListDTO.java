package teamManagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString

public class ScheduleListDTO {
	private int gameScheduleCode;
	private String gameDate;
	private String homeTeam;
	private String awayTeam;
	private String stadiumName;
	private String salesState;
}
