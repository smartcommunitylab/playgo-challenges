package it.smartcommunitylab.challenges.bean;

import java.util.Date;

public class Suspension {
    private Date from;
    private Date to;


    public Suspension(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public boolean shouldBeSuspended(Date check) {
        return (check.equals(from) || check.after(from)) && check.before(to);
    }
}
