package org.sagebionetworks.bridge.scripts.breastcancer.weekly;

import java.util.List;

import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;

import com.google.common.collect.Lists;

public class WeeklySurvey {
    
    private static List<SurveyQuestionOption> slightToExtremely = Lists.newArrayList();
    static {
        slightToExtremely.add(new SurveyQuestionOption("Very slightly or not at all", "1"));
        slightToExtremely.add(new SurveyQuestionOption("A little", "2"));
        slightToExtremely.add(new SurveyQuestionOption("Moderately", "3"));
        slightToExtremely.add(new SurveyQuestionOption("Quite a bit", "4"));
        slightToExtremely.add(new SurveyQuestionOption("Extremely", "5"));
    }

    private static List<SurveyQuestionOption> oftenToRarely = Lists.newArrayList();
    static {
        oftenToRarely.add(new SurveyQuestionOption("Often", "Often"));
        oftenToRarely.add(new SurveyQuestionOption("Sometimes", "Sometimes"));
        oftenToRarely.add(new SurveyQuestionOption("Never/Rarely", "Never/Rarely"));
    }
    
    public static Survey create() {
        Survey survey = new Survey();
        survey.setIdentifier("BCSweeklySurvey");
        survey.setName("Weekly Survey");
        
        return new SurveyBuilder(survey)
            .addSlider("FSI-1", null, "Rate your level of fatigue on the day you felt MOST fatigued during the past week (10 is the most fatigued you can be):", 0, 10)
            .addSlider("FSI-2", null, "Rate your level of fatigue on the day you felt LEAST fatigued during the past week (10 is the most fatigued you can be):", 0, 10)
            .addSlider("FSI-3", null, "Rate your level of fatigue on the AVERAGE in the past week (10 is the most fatigued you can be):", 0, 10)
            .addSlider("FSI-4", null, "Indicate how many days, in the past week, you felt fatigued for any part of the day:", 0, 7)
            .addRadio("q5a", null, "During the past week, how much have you felt interested:", false, slightToExtremely)
            .addRadio("q5b", null, "During the past week, how much have you felt distressed:", false, slightToExtremely)
            .addRadio("q5c", null, "During the past week, how much have you felt excited:", false, slightToExtremely)
            .addRadio("q5d", null, "During the past week, how much have you felt upset:", false, slightToExtremely)
            .addRadio("q5e", null, "During the past week, how much have you felt strong:", false, slightToExtremely)
            .addRadio("q5f", null, "During the past week, how much have you felt guilty:", false, slightToExtremely)
            .addRadio("q5g", null, "During the past week, how much have you felt scared:", false, slightToExtremely)
            .addRadio("q5h", null, "During the past week, how much have you felt hostile:", false, slightToExtremely)
            .addRadio("q5i", null, "During the past week, how much have you felt enthusiastic:", false, slightToExtremely)
            .addRadio("q5j", null, "During the past week, how much have you felt proud:", false, slightToExtremely)
            .addRadio("q5k", null, "During the past week, how much have you felt irritable:", false, slightToExtremely)
            .addRadio("q5l", null, "During the past week, how much have you felt alert:", false, slightToExtremely)
            .addRadio("q5m", null, "During the past week, how much have you felt ashamed:", false, slightToExtremely)
            .addRadio("q5n", null, "During the past week, how much have you felt inspired:", false, slightToExtremely)
            .addRadio("q5o", null, "During the past week, how much have you felt nervous:", false, slightToExtremely)
            .addRadio("q5p", null, "During the past week, how much have you felt determined:", false, slightToExtremely)
            .addRadio("q5q", null, "During the past week, how much have you felt attentive:", false, slightToExtremely)
            .addRadio("q5r", null, "During the past week, how much have you felt jittery:", false, slightToExtremely)
            .addRadio("q5s", null, "During the past week, how much have you felt active:", false, slightToExtremely)
            .addRadio("q5t", null, "During the past week, how much have you felt afraid:", false, slightToExtremely)
            .addNumber("GELTQ-1a", null, "Over the past week, how many times did you do the following kinds of exercise for more than 15 minutes? Strenuous exercise (heart beats rapidly):")
            .addNumber("GELTQ-1b", null, "Over the past week, how many times did you do the following kinds of exercise for more than 15 minutes? Moderate exercise (not exhausting):")
            .addNumber("GELTQ-1c", null, "Over the past week, how many times did you do the following kinds of exercise for more than 15 minutes? Minimal effort:")
            .addRadio("GELTQ-2", null, "During your leisure time in the past week, how often do you engage in any regular activity long enough to work up a sweat (heart beats rapidly)?", false, oftenToRarely)
            .build();
    }
    
}
