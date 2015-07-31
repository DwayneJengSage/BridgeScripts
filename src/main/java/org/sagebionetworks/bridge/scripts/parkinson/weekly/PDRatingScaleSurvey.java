package org.sagebionetworks.bridge.scripts.parkinson.weekly;

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

public class PDRatingScaleSurvey implements SurveyProvider {
    
    private static final String NAME = "PD Rating Scale";

    List<SurveyQuestionOption> oftenToRarely = Scripts.options("Often", "Sometimes", "Never/Rarely");
    
    List<SurveyQuestionOption> cognitiveNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "No cognitive impairment.", "0"),
        new SurveyQuestionOption("Slight", "Impairment appreciated by yourself or your caregiver with no concrete interference with your ability to carry out normal activities and social interactions.", "1"),
        new SurveyQuestionOption("Mild", "Clinically evident cognitive dysfunction, but only minimal interference with your ability to carry out normal activities and social interactions.", "2"),
        new SurveyQuestionOption("Moderate", "Cognitive deficits interfere with but do not preclude your ability to carry out normal activities and social interactions.", "3"),
        new SurveyQuestionOption("Severe", "Cognitive dysfunction precludes your ability to carry out normal activities and social interactions.", "4")
    );
    List<SurveyQuestionOption> moodNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "No depressed mood.", "0"),
        new SurveyQuestionOption("Slight", "Episodes of depressed mood that are not sustained for more than one day at a time. No interference with your ability to carry out normal activities and social interactions.", "1"),
        new SurveyQuestionOption("Mild", "Depressed mood that is sustained over days, but without interference with normal activities and social interactions.", "2"),
        new SurveyQuestionOption("Moderate", "Depressed mood that interferes with, but does not preclude, your ability to carry out normal activities and social interactions.", "3"),
        new SurveyQuestionOption("Severe", "Depressed mood precludes your ability to carry out normal activities and social interactions.", "4")
    );
    List<SurveyQuestionOption> anxietyNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "No anxious feelings.", "0"),
        new SurveyQuestionOption("Slight", "Anxious feelings present but not sustained for more than one day at a time. No interference with your ability to carry out normal activities and social interactions.", "1"),
        new SurveyQuestionOption("Mild", "Anxious feelings are sustained over more than one day at a time, but without interference with your ability to carry out normal activities and social interactions.", "2"),
        new SurveyQuestionOption("Moderate", "Anxious feelings interfere with, but do not preclude, the your ability to carry out normal activities and social interactions.", "3"),
        new SurveyQuestionOption("Severe", "Anxious feelings preclude your ability to carry out normal activities and social interactions.", "4")
    );
    List<SurveyQuestionOption> apathyNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "No apathy.", "0"),
        new SurveyQuestionOption("Slight", "Apathy appreciated by yourself and/or your caregiver, but no interference with daily activities and social interactions.", "1"),
        new SurveyQuestionOption("Mild", "Apathy interferes with isolated activities and social interactions.", "2"),
        new SurveyQuestionOption("Moderate", "Apathy interferes with most activities and social interactions.", "3"),
        new SurveyQuestionOption("Severe", "Passive and withdrawn, complete loss of initiative.", "4")
    );
    List<SurveyQuestionOption> sleepNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "No problems.", "0"),
        new SurveyQuestionOption("Slight", "Sleep problems are present but usually do not cause trouble getting a full night of sleep.", "1"),
        new SurveyQuestionOption("Mild", "Sleep problems usually cause some difficulties getting a full night of sleep.", "2"),
        new SurveyQuestionOption("Moderate", "Sleep problems cause a lot of difficulties getting a full night of sleep, but I still usually sleep for more than half the night.", "3"),
        new SurveyQuestionOption("Severe", "I usually do not sleep for most of the night.", "4")
    );
    List<SurveyQuestionOption> wakingNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "No daytime sleepiness.", "0"),
        new SurveyQuestionOption("Slight", "Daytime sleepiness occurs but I can resist and I stay awake.", "1"),
        new SurveyQuestionOption("Mild", "Sometimes I fall asleep when alone and relaxing. For example, while reading or watching TV.", "2"),
        new SurveyQuestionOption("Moderate", "I sometimes fall asleep when I should not. For example, while eating or talking with other people.", "3"),
        new SurveyQuestionOption("Severe", "I often fall asleep when I should not. For example, while eating or talking with other people.", "4")
    );
    List<SurveyQuestionOption> speechNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "Not at all (no problems).", "0"),
        new SurveyQuestionOption("Slight", "My speech is soft, slurred or uneven, but it does not cause others to ask me to repeat myself.", "1"),
        new SurveyQuestionOption("Mild", "My speech causes people to ask me to occasionally repeat myself, but not everyday.", "2"),
        new SurveyQuestionOption("Moderate", "My speech is unclear enough that others ask me to repeat myself every day even though most of my speech is understood.", "3"),
        new SurveyQuestionOption("Severe", "Most or all of my speech cannot be understood.", "4")
    );
    List<SurveyQuestionOption> handlingNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "Not at all (No problems).", "0"),
        new SurveyQuestionOption("Slight", "I am slow, but I do not need any help handling my food and have not had food spills while eating.", "1"),
        new SurveyQuestionOption("Mild", "I am slow with my eating and have occasional food spills. I may need help with a few tasks such as cutting meat.", "2"),
        new SurveyQuestionOption("Moderate", "I need help with many eating tasks but can manage some alone.", "3"),
        new SurveyQuestionOption("Severe", "I need help for most or all eating tasks.", "4")
    );
    List<SurveyQuestionOption> dressingNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "Not at all (no problems).", "0"),
        new SurveyQuestionOption("Slight", "I am slow but I do not need help.", "1"),
        new SurveyQuestionOption("Mild", "I am slow and need help for a few dressing tasks (buttons, bracelets).", "2"),
        new SurveyQuestionOption("Moderate", "I need help for many dressing tasks.", "3"),
        new SurveyQuestionOption("Severe", "I need help for most or all dressing tasks.", "4")
    );
    List<SurveyQuestionOption> washingNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "Not at all (no problems).", "0"),
        new SurveyQuestionOption("Slight", "I am slow but I do not need any help.", "1"),
        new SurveyQuestionOption("Mild", "I need someone else to help me with some hygiene tasks.", "2"),
        new SurveyQuestionOption("Moderate", "I need help for many hygiene tasks.", "3"),
        new SurveyQuestionOption("Severe", "I need help for most or all of my hygiene tasks.", "4")
    );
    List<SurveyQuestionOption> readingNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "Not at all (no problems).", "0"),
        new SurveyQuestionOption("Slight", "My writing is slow, clumsy or uneven, but all words are clear.", "1"),
        new SurveyQuestionOption("Mild", "Some words are unclear and difficult to read.", "2"),
        new SurveyQuestionOption("Moderate", "Many words are unclear and difficult to read.", "3"),
        new SurveyQuestionOption("Severe", "Most or all words cannot be read.", "4")
    );
    List<SurveyQuestionOption> hobbiesNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "Not at all (no problems).", "0"),
        new SurveyQuestionOption("Slight", "I am a bit slow but do these activities easily.", "1"),
        new SurveyQuestionOption("Mild", "I have some difficulty doing these activities.", "2"),
        new SurveyQuestionOption("Moderate", "I have major problems doing these activities, but still do most.", "3"),
        new SurveyQuestionOption("Severe", "I am unable to do most or all of these activities.", "4")
    );
    List<SurveyQuestionOption> turningNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "Not at all (no problems).", "0"),
        new SurveyQuestionOption("Slight", "I have a bit of trouble turning, but I do not need any help.", "1"),
        new SurveyQuestionOption("Mild", "I have a lot of trouble turning and need occasional help from someone else.", "2"),
        new SurveyQuestionOption("Moderate", "To turn over I often need help from someone else.", "3"),
        new SurveyQuestionOption("Severe", "I am unable to turn over without help from someone else.", "4")
    );
    List<SurveyQuestionOption> tremorsNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "Not at all. I have no shaking or tremor.", "0"),
        new SurveyQuestionOption("Slight", "Shaking or tremor occurs but does not cause problems with any activities.", "1"),
        new SurveyQuestionOption("Mild", "Shaking or tremor causes problems with only a few activities.", "2"),
        new SurveyQuestionOption("Moderate", "Shaking or tremor causes problems with many of my daily activities.", "3"),
        new SurveyQuestionOption("Severe", "Shaking or tremor causes problems with most or all activities.", "4")
    );
    List<SurveyQuestionOption> walkingNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "Not at all (no problems).", "0"),
        new SurveyQuestionOption("Slight", "I am slightly slow or may drag a leg. I never use a walking aid.", "1"),
        new SurveyQuestionOption("Mild", "I occasionally use a walking aid, but I do not need any help from another person.", "2"),
        new SurveyQuestionOption("Moderate", "I usually use a walking aid (cane, walker) to walk safely without falling. However, I do not usually need the support of another person.", "3"),
        new SurveyQuestionOption("Severe", "I usually use the support of another persons to walk safely without falling.", "4")
    );
    List<SurveyQuestionOption> freezingNormalToSevere = Lists.newArrayList(
        new SurveyQuestionOption("Normal", "Not at all (no problems).", "0"),
        new SurveyQuestionOption("Slight", "I briefly freeze but I can easily start walking again. I do not need help from someone else or a walking aid (cane or walker) because of freezing.", "1"),
        new SurveyQuestionOption("Mild", "I freeze and have trouble starting to walk again, but I do not need someone’s help or a walking aid (cane or walker) because of freezing.", "2"),
        new SurveyQuestionOption("Moderate", "When I freeze I have a lot of trouble starting to walk again and, because of freezing, I sometimes need to use a walking aid or need someone else’s help.", "3"),
        new SurveyQuestionOption("Severe", "Because of freezing, most or all of the time, I need to use a walking aid or someone’s help.", "4")
    );
        
    @Override
    public Survey createSurvey() {
        Survey survey = new Survey();
        survey.setName(NAME);
        survey.setIdentifier("MDSUPDRS");
        return new SurveyBuilder(survey)
            .slider("EQ-5D1", "How good or bad is your health TODAY (0 means the worst health you can imagine, 100 means the best health you can imagine)?", 0, 100)
            .number("GELTQ-1a", "Over the past week, how many times did you do the following kinds of exercise for more than 15 minutes? Strenuous exercise (heart beats rapidly):")
            .number("GELTQ-1b", "Over the past week, how many times did you do the following kinds of exercise for more than 15 minutes? Moderate exercise (not exhausting):")
            .number("GELTQ-1c", "Over the past week, how many times did you do the following kinds of exercise for more than 15 minutes? Minimal effort:")
            .radio("GELTQ-2", "During your leisure time in the past week, how often do you engage in any regular activity long enough to work up a sweat (heart beats rapidly)?", false, oftenToRarely)
            .radio("MDS-UPRDRS1.1", "Over the past week have you had problems remembering things, following conversations, paying attention, thinking clearly, or finding your way around the house or in town?", false, cognitiveNormalToSevere)
            .radio("MDS-UPDRS1.3", "Over the past week have you felt low, sad, hopeless or unable to enjoy things?", false, moodNormalToSevere)
            .radio("MDS-UPDRS1.4", "Over the past week have you felt nervous, worried or tense?", false, anxietyNormalToSevere)
            .radio("MDS-UPDRS1.5", "Over the past week, have you felt indifferent to doing activities or being with people?", false, apathyNormalToSevere)
            .radio("MDS-UPDRS1.7", "Over the past week, have you had trouble going to sleep at night or staying asleep through the night? Consider how rested you felt after waking up in the morning?", false, sleepNormalToSevere)
            .radio("MDS-UPDRS1.8", "Over the past week, have you had trouble staying awake during the daytime?", false, wakingNormalToSevere)
            .radio("MDS-UPDRS2.1", "Over the past week, have you had problems with your speech?", false, speechNormalToSevere)
            .radio("MDS-UPDRS2.4", "Over the past week, have you usually had troubles handling your food and using eating utensils? For example, do you have trouble handling finger foods or using forks, knifes, spoons, chopsticks?", false, handlingNormalToSevere)
            .radio("MDS-UPDRS2.5", "Over the past week, have you usually had problems dressing? For example, are you slow or do you need help with buttoning, using zippers, putting on or taking off your clothes or jewelry?", false, dressingNormalToSevere)
            .radio("MDS-UPDRS2.6", "Over the past week, have you usually been slow or do you need help with washing, bathing, shaving, brushing teeth, combing your hair or with other personal hygiene?", false, washingNormalToSevere)
            .radio("MDS-UPDRS2.7", "Over the past week, have people usually had trouble reading your handwriting?", false, readingNormalToSevere)
            .radio("MDS-UPDRS2.8", "Over the past week, have you usually had trouble doing your hobbies or other things that you like to do?", false, hobbiesNormalToSevere)
            .radio("MDS-UPDRS2.9", "Over the past week, do you usually have trouble turning over in bed?", false, turningNormalToSevere)
            .radio("MDS-UPDRS2.10", "Over the past week, have you usually had shaking or tremor?", false, tremorsNormalToSevere)
            .radio("MDS-UPDRS2.12", "Over the past week, have you usually had problems with balance and walking?", false, walkingNormalToSevere)
            .radio("MDS-UPDRS2.13", "Over the past week, on your usual day when walking, do you suddenly stop or freeze as if your feet are stuck to the floor.", false, freezingNormalToSevere)
            .build();
    }

    @Override
    public SchedulePlan createSchedulePlan(String surveyIdentifier, String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel(NAME + " Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel(NAME + " Schedule");
        schedule.setScheduleType(ScheduleType.RECURRING);
        //schedule.setDelay("P2D");
        schedule.setInterval("P1M");
        schedule.setExpires("P6D");
        schedule.addTimes("10:00");
        
        Activity activity = new Activity(NAME, "21 Questions", new SurveyReference(surveyIdentifier, surveyGuid));
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }

}
