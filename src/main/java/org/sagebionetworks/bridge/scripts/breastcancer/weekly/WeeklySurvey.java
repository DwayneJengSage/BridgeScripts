package org.sagebionetworks.bridge.scripts.breastcancer.weekly;

import java.util.List;

import org.sagebionetworks.bridge.scripts.Scripts;
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

public class WeeklySurvey implements SurveyProvider {
    
    private List<SurveyQuestionOption> slightToExtremely = Lists.newArrayList(
        new SurveyQuestionOption("Very slightly or not at all", "1"),
        new SurveyQuestionOption("A little", "2"),
        new SurveyQuestionOption("Moderately", "3"),
        new SurveyQuestionOption("Quite a bit", "4"),
        new SurveyQuestionOption("Extremely", "5")
    );

    private List<SurveyQuestionOption> oftenToRarely = Scripts.options(
        "Often", "Sometimes", "Never/Rarely"
    );
    
    @Override
    public Survey createSurvey() {
        Survey survey = new Survey();
        survey.setIdentifier("BCSweeklySurvey");
        survey.setName("Weekly Survey");
        
        return new SurveyBuilder(survey)
            .slider("FSI-1", "Rate your level of fatigue on the day you felt MOST fatigued during the past week (10 is the most fatigued you can be):", 0, 10)
            .slider("FSI-2", "Rate your level of fatigue on the day you felt LEAST fatigued during the past week (10 is the most fatigued you can be):", 0, 10)
            .slider("FSI-3", "Rate your level of fatigue on the AVERAGE in the past week (10 is the most fatigued you can be):", 0, 10)
            .slider("FSI-4", "Indicate how many days, in the past week, you felt fatigued for any part of the day:", 0, 7)
            .radio("q5a", "During the past week, how much have you felt interested:", false, slightToExtremely)
            .radio("q5b", "During the past week, how much have you felt distressed:", false, slightToExtremely)
            .radio("q5c", "During the past week, how much have you felt excited:", false, slightToExtremely)
            .radio("q5d", "During the past week, how much have you felt upset:", false, slightToExtremely)
            .radio("q5e", "During the past week, how much have you felt strong:", false, slightToExtremely)
            .radio("q5f", "During the past week, how much have you felt guilty:", false, slightToExtremely)
            .radio("q5g", "During the past week, how much have you felt scared:", false, slightToExtremely)
            .radio("q5h", "During the past week, how much have you felt hostile:", false, slightToExtremely)
            .radio("q5i", "During the past week, how much have you felt enthusiastic:", false, slightToExtremely)
            .radio("q5j", "During the past week, how much have you felt proud:", false, slightToExtremely)
            .radio("q5k", "During the past week, how much have you felt irritable:", false, slightToExtremely)
            .radio("q5l", "During the past week, how much have you felt alert:", false, slightToExtremely)
            .radio("q5m", "During the past week, how much have you felt ashamed:", false, slightToExtremely)
            .radio("q5n", "During the past week, how much have you felt inspired:", false, slightToExtremely)
            .radio("q5o", "During the past week, how much have you felt nervous:", false, slightToExtremely)
            .radio("q5p", "During the past week, how much have you felt determined:", false, slightToExtremely)
            .radio("q5q", "During the past week, how much have you felt attentive:", false, slightToExtremely)
            .radio("q5r", "During the past week, how much have you felt jittery:", false, slightToExtremely)
            .radio("q5s", "During the past week, how much have you felt active:", false, slightToExtremely)
            .radio("q5t", "During the past week, how much have you felt afraid:", false, slightToExtremely)
            .number("GELTQ-1a", "Over the past week, how many times did you do the following kinds of exercise for more than 15 minutes? Strenuous exercise (heart beats rapidly):")
            .number("GELTQ-1b", "Over the past week, how many times did you do the following kinds of exercise for more than 15 minutes? Moderate exercise (not exhausting):")
            .number("GELTQ-1c", "Over the past week, how many times did you do the following kinds of exercise for more than 15 minutes? Minimal effort:")
            .radio("GELTQ-2", "During your leisure time in the past week, how often do you engage in any regular activity long enough to work up a sweat (heart beats rapidly)?", false, oftenToRarely)
            .build();
    }
    
    @Override
    public SchedulePlan createSchedulePlan(String surveyIdentifier, String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel("Weekly Survey Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel("Weekly Survey Schedule");
        schedule.setScheduleType(ScheduleType.RECURRING);
        schedule.setDelay("P6D");
        schedule.setInterval("P7D");
        schedule.setExpires("P6D");
        schedule.addTimes("10:00");
        
        Activity activity = new Activity("Weekly Survey", "28 Questions", new SurveyReference(surveyIdentifier, surveyGuid));
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }
    
}
