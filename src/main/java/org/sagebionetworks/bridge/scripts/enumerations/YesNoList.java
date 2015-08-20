package org.sagebionetworks.bridge.scripts.enumerations;

import java.util.LinkedList;

import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;

@SuppressWarnings("serial")
public class YesNoList extends LinkedList<SurveyQuestionOption> {

    public YesNoList() {
        add(new SurveyQuestionOption("Yes", "true"));
        add(new SurveyQuestionOption("No", "false"));
    }

}
