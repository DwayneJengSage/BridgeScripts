package org.sagebionetworks.bridge.scripts.parkinson;

import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;

public class FeedbackSurvey {
    public static Survey create() {
        Survey survey = new Survey();
        survey.setName("Study Feedback");
        survey.setIdentifier("study_feedback");
        return new SurveyBuilder(survey)
            .multilineText("feedback", "In what ways would you improve or change the mPower study?", "We depend on you as our partners in this research study.  Please provide us anonymous feedback on ways we can enhance the study and reflect the interests of the Parkinson Disease community in improvements to come in June, 2015.")
            .build();
    }
}
