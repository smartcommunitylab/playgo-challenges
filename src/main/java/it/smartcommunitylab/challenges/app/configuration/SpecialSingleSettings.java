package it.smartcommunitylab.challenges.app.configuration;

import java.time.Period;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.smartcommunitylab.challenges.bean.SpecialSettings;

public class SpecialSingleSettings {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",
            timezone = "Europe/Rome")
    private Date start;
    private Period duration;
    private String model;
    private boolean hide;
    private Map<String, Object> fields;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public SpecialSettings toConfig() {
        SpecialSettings settings = new SpecialSettings(start, duration, model);
        settings.setHide(hide);
        settings.setFields(fields);
        return settings;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

}
