package QuestionPack;

import java.util.ArrayList;
import java.util.Locale;

public class Question {
    public int QID;
    public String description;
    //QuestionTypeEnum
    public String answer;
    public ArrayList<String> tagList;
    public int points;

    public Question(int QuesID, String DESC, /*QTYPEENUM*/ String ANS, int POINTS){
        QID = QuesID;
        description = DESC;
        //QuestionTypeEnum
        answer = ANS;
        tagList = new ArrayList<String>();
        points = POINTS;
    }

    public Boolean Checkanswer(String provAns){
        if(provAns.toLowerCase(Locale.ROOT) == this.answer.toLowerCase(Locale.ROOT)) return true;
        else return false;
    }
}


