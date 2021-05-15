package UserDetails;

public class Student extends User {
    public int stuNumber;

    public Student(String FIRST, String SUR, boolean ISEMPLOYEE, int STUNUM){
        firstName = FIRST;
        surname = SUR;
        isEmployee = ISEMPLOYEE;
        stuNumber = STUNUM;
    }
}

