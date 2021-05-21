package QuestionPack;

import java.util.ArrayList;

public class Question {
    public enum questionType{
        aritmetic,
        multiChoice
    }

    public int QID;
    public String description;
    public questionType QType;
    public String answer;
    public ArrayList<String> tagList;
    public int points;

    public Question(int QuesID, String DESC, String QTYPE, String ANS, int POINTS){
        QID = QuesID;
        description = DESC;
        QType = questionType.valueOf(QTYPE);
        answer = ANS;
        tagList = new ArrayList<String>();
        points = POINTS;
    }

    public int getQID(){
        return QID;
    }
    public String getDescription(){
        return description;
    }
    public questionType getQType(){
        return QType;
    }
    public String getAnswer(){
        return answer;
    }
    public ArrayList<String> getTagList(){
        return tagList;
    }
    public int getPoints(){
        return points;
    }
}


