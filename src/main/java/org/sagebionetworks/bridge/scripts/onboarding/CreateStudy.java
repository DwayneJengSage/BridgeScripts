package org.sagebionetworks.bridge.scripts.onboarding;

import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.SimpleScheduleStrategy;

public class CreateStudy extends BaseSignIn {

    public static void main(String[] args) {
        
        Session session = signIn(Environment.LOCAL, "parkinson");
        ResearcherClient client = session.getResearcherClient();

        for (SchedulePlan plan : client.getSchedulePlans()) {
            if (plan.getStrategy() instanceof SimpleScheduleStrategy) {
                System.out.println(plan);
                /*
                SimpleScheduleStrategy strategy = (SimpleScheduleStrategy)plan.getStrategy();
                Activity activity = strategy.getSchedule().getActivities().get(0);
                if (activity.getLabel().equals("Test Survey")) {
                    client.deleteSchedulePlan(plan.getGuid());
                }*/
                
            }
        }
        /*
        for (Survey survey : client.getAllSurveysMostRecentlyPublishedVersion()) {
            if (survey.getIdentifier().equals("type-test")) {
                client.closeSurvey(survey);
                client.deleteSurvey(survey);
            }
        }*/
    }
    
}
