package it.smartcommunitylab.challenges;

public interface Result {
    boolean getResult();

    String getError();

    Class<?> getErrorType();
}
