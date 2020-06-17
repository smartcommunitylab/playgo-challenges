package it.smartcommunitylab.challenges;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public static Date dateFromIso(String isoString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(isoString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
