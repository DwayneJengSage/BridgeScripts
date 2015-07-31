package org.sagebionetworks.bridge.scripts.breastcancer.monthly;

import java.util.List;

import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.scripts.SurveyProvider;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.schedules.SurveyReference;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;

import com.google.common.collect.Lists;

public class SleepQualitySurvey implements SurveyProvider {
    
    private static final String NAME = "Sleep Quality Survey";

    private List<SurveyQuestionOption> timesPerMonth = Lists.newArrayList(
        new SurveyQuestionOption("Not during the past month", "0"),
        new SurveyQuestionOption("Less than once a week", "1"),
        new SurveyQuestionOption("Once or twice a week", "2"),
        new SurveyQuestionOption("Three or more times a week", "3")
    );
    
    @Override
    public Survey createSurvey() {
        Survey survey = new Survey();
        survey.setName(NAME);
        survey.setIdentifier("PSQI");
        
        return new SurveyBuilder(survey)
            .time("PSQI-1", "What time have you usually gone to bed?")
            .slider("PSQI-2", "How long (in minutes) has it taken you to fall asleep each night?", 0, 180, 10)
            .time("PSQI-3", "When have you usually gotten up in the morning?")
            .slider("PSQI-4", "How many hours of actual sleep did you get at night? (This may be different than the number of hours you spend in bed)?", 0, 12)
            .radio("PSQI-5a", "During the PAST MONTH, how often could you not get to sleep within 30 minutes?", false, timesPerMonth)
            .radio("PSQI-5b", "During the PAST MONTH, how often did you wake up in the middle of the night or early morning?", false, timesPerMonth)
            .radio("PSQI-5c", "During the PAST MONTH, how often could you not get to sleep because you had to get up to use the bathroom?", false, timesPerMonth)
            .radio("PSQI-5d", "During the PAST MONTH, how often could you not get to sleep because you could not breathe comfortably?", false, timesPerMonth)
            .radio("PSQI-5e", "During the PAST MONTH, how often could you not get to sleep because you coughed or snored loudly?", false, timesPerMonth)
            .radio("PSQI-5f", "During the PAST MONTH, how often could you not get to sleep because you felt too cold?", false, timesPerMonth)
            .radio("PSQI-5g", "During the PAST MONTH, how often could you not get to sleep because you felt too hot?", false, timesPerMonth)
            .radio("PSQI-5h", "During the PAST MONTH, how often could you not get to sleep because you had bad dreams?", false, timesPerMonth)
            .radio("PSQI-5i", "During the PAST MONTH, how often could you not get to sleep because you had pain?", false, timesPerMonth)
            .radio("PSQI-6", "During the past month, how often have you taken medicine (prescribed or over the counter) to help you sleep?", false, timesPerMonth)
            .radio("PSQI-7", "During the past month, how often have you had trouble staying awake while driving, eating meals, or engaging in social activity?", false, timesPerMonth)
            .radio("PSQI-8", "During the past month, how much of a problem has it been for you to keep up enthusiasm to get things done?", false, timesPerMonth)
            .radio("PSQI-9", "During the past month, how would you rate your sleep quality overall?", false, timesPerMonth)
            .build();
    }
    
    @Override
    public SchedulePlan createSchedulePlan(String surveyIdentifier, String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel(NAME + " Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel(NAME + " Schedule");
        schedule.setScheduleType(ScheduleType.RECURRING);
        //schedule.setDelay("P4D");
        schedule.setInterval("P1M");
        schedule.setExpires("P21D");
        schedule.addTimes("10:00");

        Activity activity = new Activity(NAME, "18 Questions", new SurveyReference(surveyIdentifier, surveyGuid));
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }    

}
