package org.sagebionetworks.bridge.scripts.parkinson.monthly;

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

public class PDQ8Survey implements SurveyProvider {

    private List<SurveyQuestionOption> neverToAlways = Scripts.options(
        "Never", "Occasionally", "Sometimes", "Often", "Always"              
    );
                    
    
    @Override
    public Survey createSurvey() {
        Survey survey = new Survey();
        survey.setName("PDQ8 Survey");
        survey.setIdentifier("PDQ8");
        return new SurveyBuilder(survey)
            .radio("PDQ8-1", "Due to Parkinson’s disease, how often during the last month have you had difficulty getting around in public?", false, neverToAlways)
            .radio("PDQ8-2", "Due to Parkinson’s disease, how often during the last month have you had difficulty dressing yourself?", false, neverToAlways)
            .radio("PDQ8-3", "Due to Parkinson’s disease, how often during the last month have you felt depressed?", false, neverToAlways)
            .radio("PQD8-4", "Due to Parkinson’s disease, how often during the last month have you had problems with your close personal relationships?", false, neverToAlways)
            .radio("PDQ8-5", "Due to Parkinson’s disease, how often during the last month have you had problems with your concentration, e.g. when reading or watching TV?", false, neverToAlways)
            .radio("PDQ8-6", "Due to Parkinson’s disease, how often during the last month have you felt unable to communicate with people properly?", false, neverToAlways)
            .radio("PDQ8-7", "Due to Parkinson’s disease, how often during the last month have you had painful muscle cramps or spasms?", false, neverToAlways)
            .radio("PDQ8-8", "Due to Parkinson’s disease, how often during the last month have you felt embarrassed in public due to having Parkinson’s disease?", false, neverToAlways)
            .build();
    }

    @Override
    public SchedulePlan createSchedulePlan(String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel("PDQ8 Survey Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel("PDQ8 Survey Schedule");
        schedule.setScheduleType(ScheduleType.RECURRING);
        schedule.setDelay("P1M");
        schedule.setInterval("P1M");
        schedule.setExpires("P21D");
        schedule.addTimes("10:00");

        SurveyReference reference = Scripts.getPublishedSurveyReference(surveyGuid);
        Activity activity = new Activity("Monthly Survey", reference);
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }

}
