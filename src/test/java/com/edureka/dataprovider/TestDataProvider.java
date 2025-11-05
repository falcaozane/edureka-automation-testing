package com.edureka.dataprovider;

import org.testng.annotations.DataProvider;
import com.edureka.parameter.ExcelReader;

public class TestDataProvider {

    @DataProvider(name = "webinarSearchTerms")
    public static Object[][] getWebinarSearchTerms() {
        return ExcelReader.getTestData("SearchTestData");
    }
    
    @DataProvider(name="ApplyForJob")
    public static Object[][] getApplicantDetails(){
    	return ExcelReader.getTestData("JobApplicationData");
    }
}
