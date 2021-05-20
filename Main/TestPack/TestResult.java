package TestPack;

public class TestResult {
    public int resultId;
    public int testId;
    public int stuNum;
    public int finalScore;
    public String StuName;
    public String TestName;

    public TestResult(int RESID, int TID, int STUNUM, int FINSCORE){
        resultId = RESID;
        testId = TID;
        stuNum = STUNUM;
        finalScore = FINSCORE;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getStuNum() {
        return stuNum;
    }

    public java.lang.String getStuName() {
        return StuName;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public void setStuName(String stuName) {
        StuName = stuName;
    }

    public void setStuNum(int stuNum) {
        this.stuNum = stuNum;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }
}
