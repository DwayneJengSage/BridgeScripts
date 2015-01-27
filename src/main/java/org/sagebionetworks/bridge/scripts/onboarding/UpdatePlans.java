package org.sagebionetworks.bridge.scripts.onboarding;

import java.util.List;

import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.SimpleScheduleStrategy;

public class UpdatePlans extends BaseSignIn {

    public static void main(String[] args) {
        String host = "https://breastcancer.sagebridge.org";
        Session session = signIn(host);
        
        ResearcherClient client = session.getResearcherClient();
        
        for(SchedulePlan plan : client.getSchedulePlans()) {
            SimpleScheduleStrategy strategy = (SimpleScheduleStrategy)plan.getStrategy();

            List<Activity> activities = strategy.getSchedule().getActivities();
            Activity oldActivity = activities.get(0);
            
            Activity newActivity = new Activity(oldActivity.getLabel(), oldActivity.getRef());
            
            activities.clear();
            activities.add(newActivity);
            
            client.updateSchedulePlan(plan);
        }
    }
    
    
}
