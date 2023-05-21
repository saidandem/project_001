package com.hussain.project_001.services;

import com.hussain.project_001.model.Utility;

import java.util.ArrayList;
import java.util.List;

public class MockUtilityDB {
    public static List<Utility> utilities = new ArrayList<>();

    static {
        String[][] arr = {{"iPhone App", "Mobile App", "Schedule better meetings on the go.", "iphone"},
                {"Android App", "Mobile App", "Schedule better meetings on the go.", "android"},
                {"Chrome", "Browser Extension", "Quickly access and share your availability right from your browser.", "chrome"},
                {"Outlook", "Email Add-in", "Quickly access and share your availability right from  your Outlook inbox.", "outlook"},
                {"Firefox", "Browser Extension", "Quickly access and share your availability right from your browser.", "firefox"},
                {"Click", "Desktop App", "Conveniently copy your scheduling links right from your desktop.", "desktop"},
                {"Microsoft", "Browser Extension", "Quickly access and share your availability right from your browser.", "microsoft"},
                {"Opera", "Browser Extension", "Quickly access and share your availability right from your browser.", "opera"},
                {"Safari", "Browser Extension", "Quickly access and share your availability right from your browser.", "safari"}
        };

        for (String[] a : arr) {
            Utility utility = new Utility();
            utility.setTitle(a[0]);
            utility.setType(a[1]);
            utility.setDescription(a[2]);
            utility.setStyle(a[3]);
            utilities.add(utility);
        }
    }
}
