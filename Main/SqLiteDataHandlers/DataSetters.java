package SqLiteDataHandlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataSetters implements IDataSetters{

    public Boolean createNewEntry(String tableName, String params, String param1, String param2, String param3) {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("INSERT INTO "+ tableName +" (" + params +") " + "VALUES(?, ?, ?);");
            statement.setString(1,param1);
            statement.setString(2,param2);
            statement.setString(3,param3);
            statement.execute();
            c.commit();
            c.close();
            return true;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public Boolean createNewEntry(String tableName, String params, String param1, String param2, String param3, String param4) {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("INSERT INTO "+ tableName +" (" + params +") " + "VALUES(?, ?, ?, ?);");
            statement.setString(1,param1);
            statement.setString(2,param2);
            statement.setString(3,param3);
            statement.setString(4,param4);
            statement.execute();
            c.commit();
            c.close();
            return true;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public Boolean createNewEntry(String tableName, String params, String param1, String param2, String param3, String param4, String param5) {
        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("DBConnected");
            PreparedStatement statement = c.prepareStatement("INSERT INTO "+ tableName+" (" + params +") " + "VALUES(?, ?, ?, ?, ?);");
            statement.setString(1,param1);
            statement.setString(2,param2);
            statement.setString(3,param3);
            statement.setString(4,param4);
            statement.setString(5,param5);
            statement.execute();
            c.commit();
            c.close();
            return true;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }
}
