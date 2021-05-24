package com.portshift.scn;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DashboardManagerTest {
        @Test public void testAppHasAGreeting() {
            DashboardManager classUnderTest = DashboardManager.getInstance();
           classUnderTest.run();
        }
    }

