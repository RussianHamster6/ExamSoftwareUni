package TestPack;

import QuestionPack.Question;

import java.util.ArrayList;

public class Test {
    public int testID;
    public ArrayList<Integer> questionList;
    public boolean isManuallyMarked;
    public String testName;
    public ArrayList<Integer> stuList;

    public Test(int TID, boolean MANMARK, String TNAME){
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
}
