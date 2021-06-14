package TestPack;

import javafx.scene.control.CheckBox;

public interface ITestHelper {
    public String returnQListAfterAdd(String qToAdd, CheckBox isManMarkedBox, String QIDsText);
    public String returnStuListAfterAdd(String stuList, String stuToAdd);
}
