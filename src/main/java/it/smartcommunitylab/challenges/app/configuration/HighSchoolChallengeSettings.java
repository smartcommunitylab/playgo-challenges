package it.smartcommunitylab.challenges.app.configuration;

import java.time.Period;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.smartcommunitylab.challenges.bean.Settings;

public class HighSchoolChallengeSettings {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Rome")
	private Date start;
	private Period duration;

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Period getDuration() {
		return duration;
	}

	public void setDuration(Period duration) {
		this.duration = duration;
	}

	public Settings toConfig() {
		return new Settings(start, duration);
	}

}
