package it.smartcommunitylab.challenges.app.configuration;

import java.time.Period;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.smartcommunitylab.challenges.bean.GroupSettings;

public class StandardGroupSettings {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",
            timezone = "Europe/Rome")
    private Date start;
    private Period duration;
    private List<String> modeConcepts;

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

    public List<String> getModeConcepts() {
        return modeConcepts;
    }

    public void setModeConcepts(List<String> modeConcepts) {
        this.modeConcepts = modeConcepts;
    }

    public GroupSettings toConfig() {
        GroupSettings settings = new GroupSettings(start, duration);
        settings.setModes(new HashSet<>(modeConcepts));
        return settings;
    }


}
