package it.smartcommunitylab.challenges.app.configuration;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.smartcommunitylab.challenges.bean.Suspension;

public class SuspensionConfig {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",
            timezone = "Europe/Rome")
    private Date from;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",
            timezone = "Europe/Rome")
    private Date to;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Suspension toSuspension() {
        return new Suspension(from, to);
    }

}
