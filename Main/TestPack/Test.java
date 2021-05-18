package TestPack;

import QuestionPack.Question;

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

    public void AddQuestion(Integer questionID){
        questionList.add(questionID);
    }

    public void removeQuestion(Integer questionID){
        questionList.remove(questionID);
    }

    public ArrayList<Question> searchQuestionTags(String searchString){
        throw new UnsupportedOperationException();
    }

    public void AddStudent(Integer studentID){
        stuList.add(studentID);
    }

    public void removeStudent(Integer studentID){
        questionList.remove(studentID);
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
