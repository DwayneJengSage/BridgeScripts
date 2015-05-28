package org.sagebionetworks.bridge.scripts.breastcancer.enrollment;

import java.util.List;

import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;

import com.google.common.collect.Lists;

public class BCPTSymptomsSurvey {
    
    private static final List<SurveyQuestionOption> notAtAllToExtremely = Lists.newArrayList();
    {
        notAtAllToExtremely.add(new SurveyQuestionOption("Not at all"));
        notAtAllToExtremely.add(new SurveyQuestionOption("Slightly"));
        notAtAllToExtremely.add(new SurveyQuestionOption("Moderately"));
        notAtAllToExtremely.add(new SurveyQuestionOption("Quite a bit"));
        notAtAllToExtremely.add(new SurveyQuestionOption("Extremely"));
    }
    
    public static Survey create() {
        Survey survey = new Survey();
        survey.setName("BCS Symptoms Survey");
        survey.setIdentifier("BCSSymptomsSurvey");
        return new SurveyBuilder(survey)
            .addRadio("q58a", null, "Have hot flashes bothered you in the last week?", false, notAtAllToExtremely)
            .addRadio("q58b", null, "Has nausea bothered you in the last week?", false, notAtAllToExtremely)
            .addRadio("q58c", null, "Has vomiting bothered you in the last week?", false, notAtAllToExtremely)
            .addRadio("q58d", null, "Have you had difficulty with bladder control when laughing or crying in the last week?", false, notAtAllToExtremely)
            .addRadio("q58e", null, "Have you had difficulty with bladder control at other times in the last week?", false, notAtAllToExtremely)
            .addRadio("q58f", null, "Have you had vaginal dryness in the last week?", false, notAtAllToExtremely)
            .addRadio("q58g", null, "Have you had pain with intercourse in the last week?", false, notAtAllToExtremely)
            .addRadio("q58h", null, "Have you had general aches and pains in the last week?", false, notAtAllToExtremely)
            .addRadio("q58i", null, "Have you had joint pains in the last week?", false, notAtAllToExtremely)
            .addRadio("q58j", null, "Have you had muscle stiffness in the last week?", false, notAtAllToExtremely)
            .addRadio("q58k", null, "Have you had weight gain in the last week?", false, notAtAllToExtremely)
            .addRadio("q58l", null, "Have you been unhappy with the appearance of your body in the last week?", false, notAtAllToExtremely)
            .addRadio("q58m", null, "Have you experienced forgetfulness in the last week?", false, notAtAllToExtremely)
            .addRadio("q58n", null, "Have you had night sweats in the last week?", false, notAtAllToExtremely)
            .addRadio("q58o", null, "Have you had difficulty concentrating in the last week?", false, notAtAllToExtremely)
            .addRadio("q58p", null, "Have you been easily distracted in the last week?", false, notAtAllToExtremely)
            .addRadio("q58q", null, "Have you had a lack of interest in sex in the last week?", false, notAtAllToExtremely)
            .addRadio("q58r", null, "Have you experienced low sexual enjoyment in the last week?", false, notAtAllToExtremely)
            .addRadio("q58s", null, "Have you had arm swelling (lymphedema) in the last week?", false, notAtAllToExtremely)
            .addRadio("q58t", null, "Have you had decreased range of motion in the arm on surgery side in the last week?", false, notAtAllToExtremely)
            .build();
    }
}
