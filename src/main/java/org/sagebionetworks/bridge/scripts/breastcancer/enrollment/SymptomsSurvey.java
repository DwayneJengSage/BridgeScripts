package org.sagebionetworks.bridge.scripts.breastcancer.enrollment;

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

public class SymptomsSurvey implements SurveyProvider {
    
    private static final String NAME = "Symptoms Survey";
    
    private final List<SurveyQuestionOption> notAtAllToExtremely = Scripts.options(
        "Not at all",
        "Slightly",
        "Moderately",
        "Quite a bit",
        "Extremely"
    );
    
    @Override
    public Survey createSurvey() {
        Survey survey = new Survey();
        survey.setName(NAME);
        survey.setIdentifier("BCSSymptomsSurvey");
        return new SurveyBuilder(survey)
            .radio("q58a", "Have hot flashes bothered you in the last week?", false, notAtAllToExtremely)
            .radio("q58b", "Has nausea bothered you in the last week?", false, notAtAllToExtremely)
            .radio("q58c", "Has vomiting bothered you in the last week?", false, notAtAllToExtremely)
            .radio("q58d", "Have you had difficulty with bladder control when laughing or crying in the last week?", false, notAtAllToExtremely)
            .radio("q58e", "Have you had difficulty with bladder control at other times in the last week?", false, notAtAllToExtremely)
            .radio("q58f", "Have you had vaginal dryness in the last week?", false, notAtAllToExtremely)
            .radio("q58g", "Have you had pain with intercourse in the last week?", false, notAtAllToExtremely)
            .radio("q58h", "Have you had general aches and pains in the last week?", false, notAtAllToExtremely)
            .radio("q58i", "Have you had joint pains in the last week?", false, notAtAllToExtremely)
            .radio("q58j", "Have you had muscle stiffness in the last week?", false, notAtAllToExtremely)
            .radio("q58k", "Have you had weight gain in the last week?", false, notAtAllToExtremely)
            .radio("q58l", "Have you been unhappy with the appearance of your body in the last week?", false, notAtAllToExtremely)
            .radio("q58m", "Have you experienced forgetfulness in the last week?", false, notAtAllToExtremely)
            .radio("q58n", "Have you had night sweats in the last week?", false, notAtAllToExtremely)
            .radio("q58o", "Have you had difficulty concentrating in the last week?", false, notAtAllToExtremely)
            .radio("q58p", "Have you been easily distracted in the last week?", false, notAtAllToExtremely)
            .radio("q58q", "Have you had a lack of interest in sex in the last week?", false, notAtAllToExtremely)
            .radio("q58r", "Have you experienced low sexual enjoyment in the last week?", false, notAtAllToExtremely)
            .radio("q58s", "Have you had arm swelling (lymphedema) in the last week?", false, notAtAllToExtremely)
            .radio("q58t", "Have you had decreased range of motion in the arm on surgery side in the last week?", false, notAtAllToExtremely)
            .build();
    }
    
    @Override
    public SchedulePlan createSchedulePlan(String surveyIdentifier, String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel(NAME + " Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel(NAME + " Schedule");
        //schedule.setDelay("P4D");
        schedule.setScheduleType(ScheduleType.ONCE);
        
        Activity activity = new Activity(NAME, "20 Questions", new SurveyReference(surveyIdentifier, surveyGuid));
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }     
}
