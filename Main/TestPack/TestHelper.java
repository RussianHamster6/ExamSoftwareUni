package TestPack;

import SqLiteDataHandlers.DataGetters;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestHelper implements ITestHelper{
    private SqLiteDataHandlers.IDataGetters dataGetter;

    public String returnQListAfterAdd(String qToAdd,CheckBox isManMarkedBox, String QIDsText){
        dataGetter = new DataGetters();
        ResultSet rs = null;
        Connection c = null;

        if(dataConversions.testConversions.isInteger(qToAdd)) {
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
                c.setAutoCommit(false);
                rs = dataGetter.getAllByCol("question","questionID",qToAdd,c);
                if (rs.next() != false) {
                    var qType = rs.getString("questionType");
                    if(qType != "aritmetic" || qType != "multiChoice")
                    {
                        if(isManMarkedBox != null)
                            isManMarkedBox.setSelected(true);
                    }

                    if (QIDsText.length() == 0) {
                        c.close();
                        return qToAdd;
                    } else {
                        c.close();
                        return (QIDsText+ "," + qToAdd);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The QID you entered could not be found");
                    alert.showAndWait();
                    c.close();
                    return QIDsText;
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return QIDsText;
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The QID you entered is not an integer");
            alert.showAndWait();
            return QIDsText;
        }
    }

    public String returnStuListAfterAdd(String stuList, String stuToAdd){
        dataGetter = new DataGetters();
        ResultSet rs = null;
        Connection c = null;
        PreparedStatement statement = null;

        if(dataConversions.testConversions.isInteger(stuToAdd)) {
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite::resource:database/ExamSoftware.db");
                c.setAutoCommit(false);
                rs = dataGetter.getAllByCol("user", "userID", stuToAdd, c);

                if (rs.next() != false) {
                    if (rs.getInt("isEmployee") == 0) {
                        if (stuList.length() == 0) {
                            stuList = stuToAdd;
                        } else {
                            stuList = (stuList + "," + stuToAdd);
                        }
                        c.close();
                        return stuList;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "The user ID you entered is for a Staff member");
                        alert.showAndWait();
                        c.close();
                        return stuList;
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "The user ID you entered could not be found");
                    alert.showAndWait();
                    c.close();
                    return stuList;
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return stuList;
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The user ID you entered is not an integer");
            alert.showAndWait();
            return stuList;
        }
    }
}
