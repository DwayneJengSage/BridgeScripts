package org.sagebionetworks.bridge.scripts.parkinson;

import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;

public class MyThoughtsSurvey {
    public static Survey create() {
        Survey survey = new Survey();
        survey.setName("My Thoughts");
        survey.setIdentifier("mythoughts");
        return new SurveyBuilder(survey)
            .multilineText("feeling_better", "What, if anything, has made you feel better in the last day?", "Please provide your thoughts on what has made you feel better the last day.  Your answers will help researchers explore the connections between your activities and your symptoms.")
            .multilineText("feeling_worse", "What, if anything, has made you feel worse in the last day?", "Please provide your thoughts on what has made you feel worse the last day.  Your answers will help researchers explore the connections between your activities and your symptoms.")
            .build();
    }
}
