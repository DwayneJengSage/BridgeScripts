package org.sagebionetworks.bridge.scripts.enumerations;

import java.util.LinkedList;

import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;

@SuppressWarnings("serial")
public class NumberList extends LinkedList<SurveyQuestionOption> {
    
    private void addNumber(String name, String num) {
        add(new SurveyQuestionOption(name, num));
    }
    
    public NumberList(int start, int end) {
        for (int i = start; i <= end; i++) {
            String year = Integer.toString(i); 
            addNumber(year, year);
        }
    }
}
