package it.smartcommunitylab.challenges.app;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class Tasker {

    public static final String STANDARD_SINGLE_OPTION = "standardSingle";
    public static final String STANDARD_GROUP_OPTION = "standardGroup";
    public static final String SPECIAL_SINGLE_OPTION = "specialSingle";
    public static final String HSC_CHALLENGE_OPTION = "hsc";
    private boolean assignStandardSingle;
    private boolean assignStandardGroup;
    private boolean assignSpecialSingle;

    public Tasker(String[] assignments) {
        if (assignments == null || assignments.length == 0) {
            assignStandardSingle = assignStandardGroup = assignSpecialSingle = true;
        } else {
            Predicate<String> standardSinglePresent =
                    assignment -> STANDARD_SINGLE_OPTION.equals(assignment);
            Predicate<String> standardGroupPresent =
                    assignment -> STANDARD_GROUP_OPTION.equals(assignment);
            Predicate<String> specialSinglePresent =
                    assignment -> SPECIAL_SINGLE_OPTION.equals(assignment);
           assignStandardSingle =Stream.of(assignments).anyMatch(standardSinglePresent);
           assignStandardGroup = Stream.of(assignments).anyMatch(standardGroupPresent);
           assignSpecialSingle =Stream.of(assignments).anyMatch(specialSinglePresent); 
        }
    }

    public boolean isAssignStandardSingle() {
        return assignStandardSingle;
    }

    public boolean isAssignStandardGroup() {
        return assignStandardGroup;
    }

    public boolean isAssignSpecialSingle() {
        return assignSpecialSingle;
    }


}
