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

        if (!isValidTableName(tableName)){
            try {
                throw new Exception("tablename is invalid");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("Select * from "+ tableName + ";");

            return rs;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getAllByCol(String tableName, String colName, String searchParam) {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            String statementStr = "select * from " + tableName + " Where " + colName + " = ?";

            PreparedStatement preparedStatement = c.prepareStatement(statementStr);
            preparedStatement.setString(1, searchParam);
            preparedStatement.execute();
            return preparedStatement.getResultSet();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private boolean isValidTableName(String inputString){
        if (inputString.toLowerCase() == "question"
                || inputString.toLowerCase() == "user"
                || inputString.toLowerCase() == "test"
                || inputString.toLowerCase() == "qbank"
                || inputString.toLowerCase() == "testresult"
                ){
            return true;
        }
        else {
            return false;
        }
    }
}
