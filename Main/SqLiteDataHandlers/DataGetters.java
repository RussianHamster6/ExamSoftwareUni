package SqLiteDataHandlers;

import java.sql.*;

public class DataGetters implements IDataGetters {

    //gets all data as a ResultSet from a table by the table name
    public ResultSet getAll(String tableName) {
        Connection c = null;

        if (tableName.isEmpty()){
            try {
                throw new Exception("tablename cannot be null or empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            PreparedStatement statement = c.prepareStatement("Select * from ?;");
            statement.setString(1, tableName);
            ResultSet rs = statement.executeQuery();
            c.close();
            return rs;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
