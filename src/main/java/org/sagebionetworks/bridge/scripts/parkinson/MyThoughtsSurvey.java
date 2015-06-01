package org.sagebionetworks.bridge.scripts.parkinson;

import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.schedules.SurveyReference;
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
    public static SchedulePlan createSchedulePlan(String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel("My Thoughts Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel("My Thoughts Schedule");
        schedule.setScheduleType(ScheduleType.ONCE);
        schedule.setEventId("survey:"+surveyGuid+":finished,enrollment");

        Config config = ClientProvider.getConfig();
        String url = config.getRecentlyPublishedSurveyUserApi(surveyGuid);
        SurveyReference reference = new SurveyReference(url);
        Activity activity = new Activity("My Thoughts", reference);
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }    
}
