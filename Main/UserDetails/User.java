package UserDetails;

public class User {
    public int UID;
    public String firstName;
    public String surname;
    public boolean isEmployee;

    public User(int userID,String FIRST,String SUR, boolean ISEMP){
        UID = userID;
        firstName = FIRST;
        surname = SUR;
        isEmployee = ISEMP;
    }

    public int getUID() {
        return UID;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getSurname() {
        return surname;
    }
    public boolean getIsEmployee(){
        return isEmployee;
    }
    public String getName(){
        return firstName + " " + surname;
    }

    public Void deleteUser(){
        throw new UnsupportedOperationException();
    }

    public boolean isEmployeeByInt(int in){
        if (in == 0){
            return false;
        }
        else {
            return true;
        }
    }

    public int isEmployeeAsInt() {
        if (isEmployee) {
            return 1;
        } else return 0;
    }
}

