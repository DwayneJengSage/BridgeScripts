package org.sagebionetworks.bridge.scripts.molemapper;

import java.util.Collections;

import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.scripts.SurveyProvider;
import org.sagebionetworks.bridge.scripts.enumerations.YesNoList;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.schedules.SurveyReference;
import org.sagebionetworks.bridge.sdk.models.surveys.DataType;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyRule;

public class MonthlySurvey implements SurveyProvider {
    
    private static final String NAME = "Monthly Survey";
    private static final String IDENTIFIER = "MonthlySurvey";

    @Override
    public Survey createSurvey() {
        Survey survey = new Survey();
        survey.setName(NAME);
        survey.setIdentifier(IDENTIFIER);
        return new SurveyBuilder(survey)
            .radio("tan", "In the last month, have you gotten a tan?", false, new YesNoList(), Collections.<SurveyRule>emptyList(), DataType.BOOLEAN)
            .radio("burn", "In the last month, have you been sunburned?", false, new YesNoList(), Collections.<SurveyRule>emptyList(), DataType.BOOLEAN)
            .radio("sunscreen", "In the last month, have you used sunscreen?", false, new YesNoList(), Collections.<SurveyRule>emptyList(), DataType.BOOLEAN)
            .radio("sick", "In the last month, have you become sick or ill enough to miss work or school?", false, new YesNoList(), Collections.<SurveyRule>emptyList(), DataType.BOOLEAN)
            .radio("removed", "In the last month, have you had any moles removed?", false, new YesNoList(), Collections.<SurveyRule>emptyList(), DataType.BOOLEAN)
            .build();
    }

    @Override
    public SchedulePlan createSchedulePlan(String surveyIdentifier, String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel(NAME + " Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel(NAME + " Schedule");
        schedule.setScheduleType(ScheduleType.RECURRING);
        schedule.setDelay("P1M");
        schedule.setInterval("P1M");
        schedule.setExpires("P21D");
        schedule.addTimes("08:00");

        Survey survey = createSurvey();
        int numQuestions = (survey.getElements().size());
        
        Activity activity = new Activity(NAME, numQuestions+" Questions", 
            new SurveyReference(surveyIdentifier, surveyGuid));
        schedule.addActivity(activity);

        plan.setSchedule(schedule);
        return plan;
    }

}
