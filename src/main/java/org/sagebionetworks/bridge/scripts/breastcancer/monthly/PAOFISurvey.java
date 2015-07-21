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

public class PAOFISurvey implements SurveyProvider {
    
    private List<SurveyQuestionOption> almostAlwaysToAlmostNever = Lists.newArrayList(
        new SurveyQuestionOption("Almost always", "1"),
        new SurveyQuestionOption("Very often", "2"),
        new SurveyQuestionOption("Fairly often", "3"),
        new SurveyQuestionOption("Once in a while", "4"),
        new SurveyQuestionOption("Very infrequently", "5"),
        new SurveyQuestionOption("Almost never", "6")
    );
    
    @Override
    public Survey createSurvey() {
        Survey survey = new Survey();
        survey.setName("Assessment of Functioning");
        survey.setIdentifier("PAOFI");
        
        return new SurveyBuilder(survey)
            .radio("PAOFI-1", "How often do you forget something that has been told to you within the last day or two?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-2", "How often do you forget events which have occurred in the last day or two?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-3", "How often do you forget people whom you met in the last day or two?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-4", "How often do you forget things that you knew a year or more ago?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-5", "How often do you forget people whom you knew or met a year or more ago?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-6", "How often do you lose track of time, or do things either earlier or later than they are usually done or are supposed to be done?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-7", "How often do you fail to finish something you start because you forgot that you were doing it? (Including such things as forgetting to put out cigarettes, turning off the stove, etc.)", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-8", "How often do you fail to complete a task that you start because you have forgotten how to do one or more aspects of it?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-9", "How often do you lose things or have trouble remembering where they are?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-10", "How often do you forget things that you are supposed to do or have agreed to do (such as putting gas in the car, paying bills, taking care of errands, etc.)?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-11", "How often do you have difficulties understanding what is said to you?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-12", "How often do you have difficulties recognizing or identifying printed words?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-13", "How often do you have difficulty understanding reading material which at one time you could have understood?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-14", "Is it easier to have people show you things than it is to have them tell you about things?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-15", "When you speak, are your words indistinct or improperly pronounced?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-15a", "If this happens, how often do people have difficulty understanding what words you are trying to say?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-16", "How often do you have difficulty thinking of names of things?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-17", "How often do you have difficulty thinking of the words (other than names) for what you want to say?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-18", "When you write things, how often do you have difficulty forming the letters correctly?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-19", "Do you have more difficulty spelling, or make more errors in spelling, than you used to?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-20", "How often do your thoughts seem confused or illogical?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-21", "How often do you become distracted from what you are doing or saying by insignificant things which at one time you would have been able to ignore?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-22", "How often do you become confused about (or make a mistake about) where you are?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-23", "How often do you have difficulty finding  your way about?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-24", "Do you have more difficulty now than you used to in calculating or working with numbers (including managing finances, paying bills, etc.)?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-25", "Do you have more difficulty now than you used to in planning or organizing activities (e.g., deciding what to do and how it should be done)?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-26", "Do you have more difficulty now than you used to in solving problems that come up around the house, at your job, etc.? (In other words, when something new has to be accomplished, or some new difficulty comes up, do you have more trouble figuring out what should be done and how to do it)?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-27", "Do you have more difficulty now than you used to in following directions to get somewhere?", false, almostAlwaysToAlmostNever)
            .radio("PAOFI-28", "Do you have more difficulty now than you used to in following instructions  concerning how to do things?", false, almostAlwaysToAlmostNever)
            .build();
    }
    
    @Override
    public SchedulePlan createSchedulePlan(String surveyIdentifier, String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel("Assessment of Functioning Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel("Assessment of Functioning Schedule");
        schedule.setScheduleType(ScheduleType.RECURRING);
        schedule.setDelay("P3D");
        schedule.setInterval("P1M");
        schedule.setExpires("P21D");
        schedule.addTimes("10:00");

        Activity activity = new Activity("Assessment of Functioning", "29 Questions", new SurveyReference(surveyIdentifier, surveyGuid));
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }     
}
