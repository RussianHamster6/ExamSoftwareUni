package TestPack;

import java.util.ArrayList;

public class Test {
    public int testID;
    public ArrayList<Integer> questionList;
    public Boolean isManuallyMarked;
    public String testName;
    public ArrayList<Integer> stuList;

    public Test(int TID, Boolean MANMARK, String TNAME){
        testID = TID;
        questionList = new ArrayList<Integer>();
        isManuallyMarked = MANMARK;
        testName = TNAME;
        stuList = new ArrayList<Integer>();
    }

    public int getTestID(){return testID;}
    public ArrayList<Integer> getQuestionList(){return questionList;}
    public Boolean getIsManuallyMarked(){return isManuallyMarked;}
    public String getTestName(){return testName;}
    public ArrayList<Integer> getStuList(){return stuList;}


    public String getIsManuallyMarkedAsString(){
        if (isManuallyMarked) return "1";
        else return "0";
    }
}
