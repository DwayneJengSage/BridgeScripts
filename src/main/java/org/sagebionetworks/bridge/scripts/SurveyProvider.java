package org.sagebionetworks.bridge.scripts;

import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;

public interface SurveyProvider {

    public Survey createSurvey();
    
    public SchedulePlan createSchedulePlan(String surveyIdentifier, String surveyGuid);
    
}
