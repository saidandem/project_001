package com.hussain.project_001.services;

import com.hussain.project_001.model.Utility;

import java.util.List;

public class UtilityService {
    public List<Utility> getUtilities() {
        return MockUtilityDB.utilities;
    }
}
