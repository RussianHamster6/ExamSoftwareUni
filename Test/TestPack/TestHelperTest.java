package TestPack;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestHelperTest {

    public ITestHelper testHelper;

    @BeforeEach
    void setUp() {
        testHelper = new TestHelper();
    }
    @AfterEach
    void tearDown() {
        testHelper = null;
    }

    @Test
    void returnQListAfterAddEmptyQList() {
        String QList = "";
        String QToAdd = "1";
        Assertions.assertEquals(QToAdd,testHelper.returnQListAfterAdd(QToAdd,null,QList));
    }
    @Test
    void returnQListAfterAddPopulatedQList() {
        String QList = "1";
        String QToAdd = "2";
        Assertions.assertEquals("1,2",testHelper.returnQListAfterAdd(QToAdd,null,QList));
    }

    @Test
    void returnStuListAfterAddEmptyStuList() {
        String StuList = "";
        String StuToAdd = "2";
        Assertions.assertEquals(StuToAdd,testHelper.returnStuListAfterAdd(StuList,StuToAdd));
    }

    @Test
    void returnStuListAfterAddPopulatedStuList() {
        String StuList = "2";
        String StuToAdd = "3";
        Assertions.assertEquals("2,3",testHelper.returnStuListAfterAdd(StuList,StuToAdd));
    }
}