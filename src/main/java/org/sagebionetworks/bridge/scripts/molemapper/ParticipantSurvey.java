package org.sagebionetworks.bridge.scripts.molemapper;

import java.util.Collections;
import java.util.List;

import org.sagebionetworks.bridge.scripts.Scripts;
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
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyRule;

public class ParticipantSurvey implements SurveyProvider {

    private static final String NAME = "Participant Survey";
    private static final String IDENTIFIER = "ParticipantSurvey";
    
    private static final List<SurveyQuestionOption> professions = Scripts.options(
        "Biomedical Researcher",
        "Coal/Oil/Gas Extraction",
        "Construction",
        "Dental professional",
        "Doctor/Nurse",
        "Electrician",
        "Farming",
        "Military Veteran",
        "Pilot or flight crew",
        "Radiology Technician",
        "TSA Agent",
        "Welding/Soldering",
        "None of the above choices"
    );
    
    @Override
    public Survey createSurvey() {
        Survey survey = new Survey();
        survey.setName(NAME);
        survey.setIdentifier(IDENTIFIER);
        return new SurveyBuilder(survey)
            .info("info1", "Participant Survey", "This survey will collect information and compare this to mole data to better understand melanoma risk. This survey should take about 5 minutes.")
            .number("age", "How old are you?", 18, 120)
            .radio("gender", "What is your sex?", false, Scripts.options("Male", "Female", "Other"))
            .number("zipCode", "What is the first 3 digits of your zip code?", 0, 999)
            .list("hairColor", "What is your natural hair color?", false, Scripts.options("Red","Blonde","Brown","Black"))
            .list("eyeColor", "What is your eye color?", false, Scripts.options("Blue","Green","Brown"))
            .list("profession", "Have you worked in any of the following professions?", "Check all that apply", false, true, professions)
            .radio("melanomaDiagnosis", "Have you ever been diagnosed with melanoma?", false, new YesNoList(), Collections.<SurveyRule>emptyList(), DataType.BOOLEAN)
            .radio("familyHistory", "Do you have a family history of melanoma (parents, siblings, children)?", false, new YesNoList(), Collections.<SurveyRule>emptyList(), DataType.BOOLEAN)
            .radio("moleRemoved", "Have you ever had a mole removed?", false, new YesNoList(), Collections.<SurveyRule>emptyList(), DataType.BOOLEAN)
            .radio("autoImmune", "Do you have an autoimmune condition (Psoriasis, Chron's disease, or others)?", false, new YesNoList(), Collections.<SurveyRule>emptyList(), DataType.BOOLEAN)
            .radio("immunocompromised", "Do you have a weakened immune system for any reason (e.g. transplant recipient, HIV/AIDS, lupus, drugs intended to suppress immune system)?", false, new YesNoList(), Collections.<SurveyRule>emptyList(), DataType.BOOLEAN)
            .build();
    }

    @Override
    public SchedulePlan createSchedulePlan(String surveyIdentifier, String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel(NAME + " Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel(NAME + " Schedule");
        schedule.setScheduleType(ScheduleType.ONCE);

        Survey survey = createSurvey();
        int numQuestions = (survey.getElements().size()-1);
        
        Activity activity = new Activity(NAME, "(One Time) "+numQuestions+" Questions", 
            new SurveyReference(surveyIdentifier, surveyGuid));
        schedule.addActivity(activity);

        plan.setSchedule(schedule);
        return plan;
    }

}
