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

public class PersonalHealthSurvey implements SurveyProvider {
    
    private static final String NAME = "Personal Health Survey";
    
    private List<SurveyQuestionOption> notAtAllToEveryDay = Lists.newArrayList(
        new SurveyQuestionOption("Not at all", "0"),
        new SurveyQuestionOption("Several days", "1"),
        new SurveyQuestionOption("More than half the days", "2"),
        new SurveyQuestionOption("Nearly every day", "3")
    );
    
    @Override
    public Survey createSurvey() {
        Survey survey = new Survey();
        survey.setName(NAME);
        survey.setIdentifier("BCSPHQ8survey");
        return new SurveyBuilder(survey)
            .radio("PHQ8-1", "Over the last TWO WEEKS how often have you experienced little interest or pleasure in doing things?", false, notAtAllToEveryDay)
            .radio("PHQ8-2", "Over the last TWO WEEKS how often have you been feeling down, depressed, or hopeless?", false, notAtAllToEveryDay)
            .radio("PHQ8-3", "Over the last TWO WEEKS how often have you had trouble falling or staying asleep, or sleeping too much?", false, notAtAllToEveryDay)
            .radio("PHQ8-4", "Over the last TWO WEEKS how often have felt tired or had little energy?", false, notAtAllToEveryDay)
            .radio("PHQ8-5", "Over the last TWO WEEKS how often have you had poor appetite or overeating?", false, notAtAllToEveryDay)
            .radio("PHQ8-6", "Over the last TWO WEEKS how often have felt bad about yourself or felt that you are a failure or have let yourself or your family down?", false, notAtAllToEveryDay)
            .radio("PHQ8-7", "Over the last TWO WEEKS how often have you had trouble concentrating on things, such as reading the newspaper or watching television?", false, notAtAllToEveryDay)
            .radio("PHQ8-8", "Over the last TWO WEEKS how often have you moved or spoke so slowly that other people could have noticed? Or the opposite: been so fidgety or restless that you have been moving around a lot more than usual?", false, notAtAllToEveryDay)
            .radio("GAD7-1", "Over the last TWO WEEKS how often have you felt nervous, anxious, or on edge?", false, notAtAllToEveryDay)
            .radio("GAD7-2", "Over the last TWO WEEKS how often have you not been able to stop or control worrying?", false, notAtAllToEveryDay)
            .radio("GAD7-3", "Over the last TWO WEEKS how often have you worried too much about different things?", false, notAtAllToEveryDay)
            .radio("GAD7-4", "Over the last TWO WEEKS how often have you had trouble relaxing?", false, notAtAllToEveryDay)
            .radio("GAD7-5", "Over the last TWO WEEKS how often have you been so restless that it's hard to sit still?", false, notAtAllToEveryDay)
            .radio("GAD7-6", "Over the last TWO WEEKS how often have you become easily annoyed or irritable?", false, notAtAllToEveryDay)
            .radio("GAD7-7", "Over the last TWO WEEKS how often have you felt afraid as if something awful might happen?", false, notAtAllToEveryDay)
            .build();
    }
    
    @Override
    public SchedulePlan createSchedulePlan(String surveyIdentifier, String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel(NAME + " Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel(NAME + " Schedule");
        schedule.setScheduleType(ScheduleType.RECURRING);
        //schedule.setDelay("P1M");
        schedule.setInterval("P1M");
        schedule.setExpires("P21D");
        schedule.addTimes("10:00");

        Activity activity = new Activity(NAME, "15 Questions", new SurveyReference(surveyIdentifier, surveyGuid));
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }     

}
