package org.sagebionetworks.bridge.scripts.breastcancer.optional;

import org.sagebionetworks.bridge.sdk.models.surveys.StringConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestion;
import org.sagebionetworks.bridge.sdk.models.surveys.UiHint;

public class MyThoughtsSurvey {
    public static Survey create() {
        Survey survey = new Survey();
        survey.setName("My Thoughts");
        survey.setIdentifier("mythoughts");
        
        SurveyQuestion q = new SurveyQuestion();
        q.setUiHint(UiHint.MULTILINETEXT);
        q.setIdentifier("feeling_better");
        q.setPrompt("What, if anything, has made you feel better in the last day?");
        q.setPromptDetail("Please provide your thoughts on what has made you feel better the last day.  Your answers will help researchers explore the connections between your activities and your symptoms.");
        q.setConstraints(new StringConstraints());
        survey.getElements().add(q);
        
        q = new SurveyQuestion();
        q.setUiHint(UiHint.MULTILINETEXT);
        q.setIdentifier("feeling_worse");
        q.setPrompt("What, if anything, has made you feel worse in the last day?");
        q.setPromptDetail("Please provide your thoughts on what has made you feel worse the last day.  Your answers will help researchers explore the connections between your activities and your symptoms.");
        q.setConstraints(new StringConstraints());
        survey.getElements().add(q);
        
        return survey;
    }
}
