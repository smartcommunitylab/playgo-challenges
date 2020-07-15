package it.smartcommunitylab.challenges.bean;

import java.time.Period;
import java.util.Date;
import java.util.Map;

public class SpecialSettings {
    private Date start;
    private Period duration;
    private String model;
    private boolean hide;
    private Map<String, Object> fields;

    public SpecialSettings(Date start, Period duration, String model) {
        if (start == null) {
            throw new IllegalArgumentException("settings date start is required");
        }

        if (duration == null) {
            throw new IllegalArgumentException("settings duration is required");
        }

        if (model == null) {
            throw new IllegalArgumentException("settings model is required");
        }
        this.start = start;
        this.duration = duration;
        this.model = model;
    }

    public Date getStart() {
        return start;
    }

    public Period getDuration() {
        return duration;
    }

    public String getModel() {
        return model;
    }

    public boolean isHide() {
        return hide;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }


}
