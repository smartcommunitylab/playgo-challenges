package it.smartcommunitylab.challenges;

public class ValidResult implements Result {
    private boolean executionResult;

    public ValidResult(boolean result) {
        executionResult = result;
    }
    @Override
    public boolean getResult() {
        return executionResult;
    }

    @Override
    public String getError() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<?> getErrorType() {
        // TODO Auto-generated method stub
        return null;
    }

}
