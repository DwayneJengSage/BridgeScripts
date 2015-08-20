package org.sagebionetworks.bridge.scripts.onboarding;

import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.ResourceList;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.SimpleScheduleStrategy;
import org.sagebionetworks.bridge.sdk.models.schedules.SurveyReference;

public class UpdateStudy extends BaseSignIn {
    public static void main(String[] args) throws InterruptedException {
        
        Session session = signIn(Environment.STAGING, "parkinson");
        
        ResearcherClient client = session.getResearcherClient();
        
        ResourceList<SchedulePlan> plans = client.getSchedulePlans();
        for (SchedulePlan plan : plans) {
            Schedule schedule = ((SimpleScheduleStrategy)plan.getStrategy()).getSchedule();
            SurveyReference ref = schedule.getActivities().get(0).getSurvey();
            client.getSurveyMostRecentlyPublishedVersion(ref.getGuid());
        }
    }
}
