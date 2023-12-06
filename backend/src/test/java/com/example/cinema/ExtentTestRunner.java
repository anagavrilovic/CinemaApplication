package com.example.cinema;

import com.example.cinema.extent_config.ExtentReportListener;
import com.example.cinema.integration.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.RunListener;

public class ExtentTestRunner {

    public static void main(String[] args) {
        JUnitCore runner = new JUnitCore();
        runner.addListener(createExtentReportListener());
        runner.run(UserControllerTest.class);
    }

    private static RunListener createExtentReportListener() {
        return new ExtentReportListener();
    }
}
