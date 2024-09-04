package Listeners;

import Utilities.LogsUtility;
import Utilities.Utility;
import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class IInvokedMethodListeners implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {


        LogsUtility.LoggerInfo("Test Case " + context.getStartDate());

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {


        File latestlogfile = Utility.GetLatestFile(LogsUtility.Logs_Path);
        try {
            assert latestlogfile != null;
            Allure.addAttachment("logs.latestlog", Files.readString(Path.of(latestlogfile.getPath())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (testResult.getStatus() == ITestResult.FAILURE) {

            //Utility.TakingFullScreenShot(GetThreadDriver(), new P02_HomePage(GetThreadDriver()).getNumberofproduct());
            //Utility.TakingScreenShot(GetThreadDriver(), testResult.getName());
            LogsUtility.LoggerInfo("Test Case " + testResult.getName() + " has Failed");
        }


    }
}
