package Listeners;

import Utilities.LogsUtility;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class IInvokedMethodListeners implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {


        LogsUtility.LoggerInfo("Test Case " + context.getStartDate());

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {

        if (testResult.getStatus() == ITestResult.FAILURE) {

            //Utility.TakingFullScreenShot(GetThreadDriver(), new P02_HomePage(GetThreadDriver()).getNumberofproduct());
            //Utility.TakingScreenShot(GetThreadDriver(), testResult.getName());
            LogsUtility.LoggerInfo("Test Case " + testResult.getName() + " has Failed");
        }


    }
}
