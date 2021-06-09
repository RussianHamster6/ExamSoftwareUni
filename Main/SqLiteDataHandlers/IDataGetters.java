package SqLiteDataHandlers;

import java.sql.Connection;
import java.sql.ResultSet;

public interface IDataGetters {

    public ResultSet getAll(String tableName, Connection c);

    public ResultSet getAllByCol(String tableName, String colName, String searchParam, Connection c);
}
