package QuestionPack;

import java.util.ArrayList;

public class QuestionBank {

    public int QBankID;
    public String QBankTitle;
    public ArrayList<Integer> QList;

    public QuestionBank(int QBANKID, String TITLE){
        QBankID = QBANKID;
        QBankTitle = TITLE;
        QList = new ArrayList<Integer>();
    }

    public void addQuestion(Integer questionID){
        QList.add(questionID);
    }

    public void removeQuestion(Integer questionID){
        QList.remove(QList.indexOf(questionID));
    }

    public ArrayList<Question> searchQuestionTags(String searchString){
        throw new UnsupportedOperationException();
    }
}

