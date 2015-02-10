package org.sagebionetworks.bridge.scripts.onboarding;

import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;

public class UpdatePlans extends BaseSignIn {

    public static void main(String[] args) {
        Session session = signIn(Environment.DEV, "parkinson");
        
        ResearcherClient client = session.getResearcherClient();
        
        for(SchedulePlan plan : client.getSchedulePlans()) {
            
            System.out.println(plan);
            /*
            SimpleScheduleStrategy strategy = (SimpleScheduleStrategy)plan.getStrategy();

            List<Activity> activities = strategy.getSchedule().getActivities();
            Activity oldActivity = activities.get(0);
            
            SurveyReference ref = oldActivity.getSurvey();
            String href = host + ClientProvider.getConfig().getRecentlyPublishedSurveyUserApi(ref.getGuid());
            
            Activity newActivity = new Activity(oldActivity.getLabel(), href);
            
            activities.clear();
            activities.add(newActivity);
            client.updateSchedulePlan(plan);
            */
        }
    }
    
    
}
