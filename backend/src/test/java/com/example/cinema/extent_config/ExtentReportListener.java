package com.example.cinema.extent_config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class ExtentReportListener extends RunListener {

    private ExtentReports extentReports;

    @Override
    public void testRunStarted(Description description) throws Exception {
        extentReports = new ExtentReports();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent-reports/extent-report.html");
        extentReports.attachReporter(htmlReporter);
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        extentReports.flush();
    }

    @Override
    public void testStarted(Description description) throws Exception {
        extentReports.createTest(description.getMethodName());
    }

    @Override
    public void testFinished(Description description) throws Exception {
        // Additional cleanup or logging if needed
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        // Log failure details in the Extent Report
        extentReports.createTest(failure.getDescription().getMethodName()).fail(failure.getException());
    }
}