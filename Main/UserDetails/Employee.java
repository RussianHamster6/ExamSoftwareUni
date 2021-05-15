package UserDetails;

public class Employee extends User{
    public int empNumber;

    public Employee(String FIRST, String SUR, boolean ISEMPLOYEE, int EMPNUM){
        firstName = FIRST;
        surname = SUR;
        isEmployee = ISEMPLOYEE;
        empNumber = EMPNUM;
    }

    public Integer isEmployeeAsInt(){
        return 1;
    }
}


