package org.sagebionetworks.bridge.scripts.parkinson.optional;

import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.scripts.SurveyProvider;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.schedules.SurveyReference;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;

public class MyThoughtsSurvey implements SurveyProvider {
    
    private static final String NAME = "My Thoughts";
    
    @Override
    public Survey createSurvey() {
        Survey survey = new Survey();
        survey.setName(NAME);
        survey.setIdentifier("mythoughts");
        return new SurveyBuilder(survey)
            .multilineText("feeling_better", "What, if anything, has made you feel better in the last day?", "Please provide your thoughts on what has made you feel better the last day.  Your answers will help researchers explore the connections between your activities and your symptoms.")
            .multilineText("feeling_worse", "What, if anything, has made you feel worse in the last day?", "Please provide your thoughts on what has made you feel worse the last day.  Your answers will help researchers explore the connections between your activities and your symptoms.")
            .build();
    }
    
    @Override
    public SchedulePlan createSchedulePlan(String surveyIdentifier, String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel(NAME + " Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel(NAME + " Schedule");
        schedule.setScheduleType(ScheduleType.ONCE);
        schedule.setEventId("survey:"+surveyGuid+":finished,enrollment");

        Activity activity = new Activity(NAME, "2 Questions", new SurveyReference(surveyIdentifier, surveyGuid));
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }    
}
