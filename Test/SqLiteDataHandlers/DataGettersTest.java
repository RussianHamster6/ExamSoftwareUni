package SqLiteDataHandlers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

class DataGettersTest {
    private IDataGetters dataGetter;
    private Connection c = null;

    @BeforeEach
    void setUp() {
        dataGetter = new DataGetters();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
            c.setAutoCommit(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        dataGetter = null;
        c = null;
    }

    @Test
    void getAllQuestion() {
        ResultSet rs = dataGetter.getAll("question",c);
        try {
            Assertions.assertTrue(rs.next());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getAllTest() {
        ResultSet rs = dataGetter.getAll("test",c);
        try {
            Assertions.assertTrue(rs.next());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getAllTestResult() {
        ResultSet rs = dataGetter.getAll("testResult",c);
        try {
            Assertions.assertTrue(rs.next());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getAllUser() {
        ResultSet rs = dataGetter.getAll("user",c);
        try {
            Assertions.assertTrue(rs.next());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getAllByCol() {
        ResultSet rs = dataGetter.getAllByCol("user","userID","1",c);
        try {
            Assertions.assertTrue(rs.next());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}