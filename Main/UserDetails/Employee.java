package Classes;

public class Employee extends User{
    public int empNumber;

    public Employee(String FIRST, String SUR, String USERNAME, boolean ISEMPLOYEE, int EMPNUM){
        firstName = FIRST;
        surname = SUR;
        userName = USERNAME;
        isEmployee = ISEMPLOYEE;
        empNumber = EMPNUM;
    }
}
