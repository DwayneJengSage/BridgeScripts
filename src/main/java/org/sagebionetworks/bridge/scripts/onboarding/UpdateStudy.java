package org.sagebionetworks.bridge.scripts.onboarding;

import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.SimpleScheduleStrategy;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;

public class UpdateStudy extends BaseSignIn {
    public static void main(String[] args) {
        
        Session session = signIn("https://parkinson-develop.sagebridge.org");
        
        ResearcherClient client = session.getResearcherClient();
        
        for (SchedulePlan plan : client.getSchedulePlans()) {
            Activity activity = ((SimpleScheduleStrategy)plan.getStrategy()).getSchedule().getActivities().get(0);
            
            Survey survey = client.getSurveyMostRecentlyPublishedVersion(activity.getSurvey().getGuid());
            System.out.println(survey);
        }
    }
}
