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
}

