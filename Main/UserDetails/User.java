package UserDetails;

public abstract class User {
    public String firstName;
    public String surname;
    public boolean isEmployee;

    public String getName(){
        return firstName + " " + surname;
    }

    public Void deleteUser(){
        throw new UnsupportedOperationException();
    }

    public boolean isEmployee(int in){
        if (in == 0){
            return false;
        }
        else {
            return true;
        }
    }
}

