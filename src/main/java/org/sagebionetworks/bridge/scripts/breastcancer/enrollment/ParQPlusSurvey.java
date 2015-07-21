package org.sagebionetworks.bridge.scripts.breastcancer.enrollment;

import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.scripts.SurveyProvider;
import org.sagebionetworks.bridge.scripts.enumerations.YesNoList;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.schedules.SurveyReference;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;

public class ParQPlusSurvey implements SurveyProvider {
    
    @Override
    public Survey createSurvey() {
        Survey survey = new Survey();
        survey.setName("Physical Activity Readiness Questionnaire");
        survey.setIdentifier("PAR-Q");
        return new SurveyBuilder(survey)
            .info("info1", "Physical Activity Readiness Questionnaire: PAR-Q", "Regular physical activity is fun and healthy, and increasingly more people are starting to become more active every day. Being more active is very safe for most people. However, some people should check with their doctor before they start becoming much more physically active. Source: Canadian Society for Exercise Physiology, © 2012. Used with permission.")
            .info("info2", "Physical Activity Readiness Questionnaire: PAR-Q", "If you are planning to become much more physically active than you are now, start by answering the seven questions on the following screens. If you are between the ages of 15 and 69, the PAR-Q will tell you if you should check with your doctor before you start. If you are over 69 years of age, and you are not used to being very active, check with your doctor. Common sense is your best guide when you answer these questions. Please read the questions carefully and answer each one honestly: check YES or NO.")
            .radio("chestPain", "Do you feel pain in your chest when you do physical activity?", false, new YesNoList())
            .radio("chestPainInLastMonth", "In the past month, have you had chest pain when you were not doing physical activity?", false, new YesNoList())
            .radio("dizziness", "Do you lose your balance because of dizziness or do you ever lose consciousness?", false, new YesNoList())
            .radio("jointProblem", "Do you have a bone or joint problem (for example, back, knee or hip) that could be made worse by a change in your physical activity?", false, new YesNoList())
            .radio("prescriptionDrugs", "Is your doctor currently prescribing drugs (for example, water pills) for your blood pressure or heart condition?", false, new YesNoList())
            .radio("physicallyCapable", "Do you know of any other reason why you should not do physical activity?", false, new YesNoList())
            .info("stopHere", "If you answered YES to one or more questions", "Talk with your doctor by phone or in person BEFORE you start becoming much more physically active or BEFORE you have a fitness appraisal. Tell your doctor about the PAR-Q and which questions you answered YES. You may be able to do any activity you want — as long as you start slowly and build up gradually. Or, you may need to restrict your activities to those which are safe for you. Talk with your doctor about the kinds of activities you wish to participate in and follow his/her advice.")
            .info("goodToGo", "If you answered NO to all questions, you can reasonably be sure that you can:", "1) start becoming much more physically active – begin slowly and build up gradually. This is the safest and easiest way to go. 2) take part in a fitness appraisal – this is an excellent way to determine your basic fitness so that you can plan the best way for you to live actively. It is also highly recommended that you have your blood pressure evaluated. If your reading is over 144/94, talk with your doctor before you start becoming much more physically active. DELAY BECOMING MUCH MORE ACTIVE. 3) if you are not feeling well because of a temporary illness such as a cold or a fever – wait until you feel better; or• if you are or may be pregnant – talk to your doctor before you start becoming more active. PLEASE NOTE: If your health changes so that you then answer YES to any of the above questions, tell your fitness or health professional. Ask whether you should change your physical activity plan.")
            .build();
    }
    
    @Override
    public SchedulePlan createSchedulePlan(String surveyIdentifier, String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel("ParQ+ Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel("ParQ+ Schedule");
        schedule.setDelay("P2D");
        schedule.setScheduleType(ScheduleType.ONCE);

        Activity activity = new Activity("Exercise Readiness Survey", "10 Questions", new SurveyReference(surveyIdentifier, surveyGuid));
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }     
}
