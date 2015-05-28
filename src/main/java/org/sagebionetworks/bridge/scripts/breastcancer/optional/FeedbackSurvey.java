package org.sagebionetworks.bridge.scripts.breastcancer.optional;

import org.sagebionetworks.bridge.sdk.models.surveys.StringConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestion;
import org.sagebionetworks.bridge.sdk.models.surveys.UiHint;

public class FeedbackSurvey {
    public static Survey create() {
        Survey survey = new Survey();
        survey.setName("Study Feedback");
        survey.setIdentifier("study_feedback");
        
        SurveyQuestion q = new SurveyQuestion();
        q.setUiHint(UiHint.MULTILINETEXT);
        q.setIdentifier("feedback");
        q.setPrompt("In what ways would you improve or change the Share the Journey study?");
        q.setPromptDetail("We depend on you as our partners in this research study.  Please provide us anonymous feedback on ways we can enhance the study and reflect the interests of the breast cancer survivor community in improvements to come in June, 2015.");
        q.setConstraints(new StringConstraints());
        survey.getElements().add(q);
        
        return survey;
    }

}
