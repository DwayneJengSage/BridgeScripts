package org.sagebionetworks.bridge.scripts.breastcancer.trimonthly;

import java.util.List;

import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.scripts.enumerations.YesNoList;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;

import com.google.common.collect.Lists;

public class SF36Survey {
    
    private static List<SurveyQuestionOption> yesNo = new YesNoList();
 
    private static List<SurveyQuestionOption> excellentToPoor = Lists.newArrayList();
    static {
        excellentToPoor.add(new SurveyQuestionOption("Excellent"));
        excellentToPoor.add(new SurveyQuestionOption("Very Good"));
        excellentToPoor.add(new SurveyQuestionOption("Good"));
        excellentToPoor.add(new SurveyQuestionOption("Fair"));
        excellentToPoor.add(new SurveyQuestionOption("Poor"));
    }
    private static List<SurveyQuestionOption> noneToSevere = Lists.newArrayList();
    static {
        noneToSevere.add(new SurveyQuestionOption("None"));
        noneToSevere.add(new SurveyQuestionOption("Very mild"));
        noneToSevere.add(new SurveyQuestionOption("Mild"));
        noneToSevere.add(new SurveyQuestionOption("Moderate"));
        noneToSevere.add(new SurveyQuestionOption("Severe"));
    }
    private static List<SurveyQuestionOption> muchBetterToMuchWorse = Lists.newArrayList();
    static {
        muchBetterToMuchWorse.add(new SurveyQuestionOption("Much better now than one year ago"));
        muchBetterToMuchWorse.add(new SurveyQuestionOption("Somewhat better now than one year ago"));
        muchBetterToMuchWorse.add(new SurveyQuestionOption("About the same"));
        muchBetterToMuchWorse.add(new SurveyQuestionOption("Somewhat worse than one year ago"));
        muchBetterToMuchWorse.add(new SurveyQuestionOption("Much worse than one year ago"));
    }
    private static List<SurveyQuestionOption> limitedToNotLimited = Lists.newArrayList();
    static {
        limitedToNotLimited.add(new SurveyQuestionOption("Yes, limited a lot"));
        limitedToNotLimited.add(new SurveyQuestionOption("Yes, limited a little"));
        limitedToNotLimited.add(new SurveyQuestionOption("No, not limited at all"));
    }
    private static List<SurveyQuestionOption> notAtAllToExtremely = Lists.newArrayList();
    static {
        notAtAllToExtremely.add(new SurveyQuestionOption("Not at all"));
        notAtAllToExtremely.add(new SurveyQuestionOption("Slightly"));
        notAtAllToExtremely.add(new SurveyQuestionOption("Moderately"));
        notAtAllToExtremely.add(new SurveyQuestionOption("Quite a bit"));
        notAtAllToExtremely.add(new SurveyQuestionOption("Extremely"));
    }
    private static List<SurveyQuestionOption> allToNoneOfTheTime = Lists.newArrayList();
    static {
        allToNoneOfTheTime.add(new SurveyQuestionOption("All of the time"));
        allToNoneOfTheTime.add(new SurveyQuestionOption("Most of the time"));
        allToNoneOfTheTime.add(new SurveyQuestionOption("A good bit of the time"));
        allToNoneOfTheTime.add(new SurveyQuestionOption("Some of the time"));
        allToNoneOfTheTime.add(new SurveyQuestionOption("A little of the time"));
        allToNoneOfTheTime.add(new SurveyQuestionOption("None of the time"));
    }
    private static List<SurveyQuestionOption> trueToFalse = Lists.newArrayList();
    static {
        trueToFalse.add(new SurveyQuestionOption("Definitely True"));
        trueToFalse.add(new SurveyQuestionOption("Mostly True"));
        trueToFalse.add(new SurveyQuestionOption("Mostly False"));
        trueToFalse.add(new SurveyQuestionOption("Definitely False"));
    }
    
    public static Survey create() {
        Survey survey = new Survey();
        survey.setName("SF36 Survey");
        survey.setIdentifier("SF36");
        
        return new SurveyBuilder(survey)
            .radio("SF36-1", "In general, would you say your health is: ", false, excellentToPoor)
            .radio("SF36-2", "Compared to one year ago, how would you rate your health in general now?", false, muchBetterToMuchWorse)
            .radio("SF36-3", "In a typical day, does your health now limit vigorous activities, such as running, lifting heavy objects, participating in strenuous sports?", false, limitedToNotLimited)
            .radio("SF36-4", "In a typical day, does your health now limit moderate activities, such as moving a table, pushing a vacuum cleaner, bowling, or playing golf?", false, limitedToNotLimited)
            .radio("SF36-5", "In a typical day, does your health now limit lifting or carrying groceries?", false, limitedToNotLimited)
            .radio("SF36-6", "In a typical day, does your health now limit climbing several flights of stairs?", false, limitedToNotLimited)
            .radio("SF36-7", "In a typical day, does your health now limit climbing one flight of stairs?", false, limitedToNotLimited)
            .radio("SF36-8", "In a typical day, does your health now limit bending, kneeling, or stooping?", false, limitedToNotLimited)
            .radio("SF36-9", "In a typical day, does your health now limit walking more than a mile?", false, limitedToNotLimited)
            .radio("SF36-10", "In a typical day, does your health now limit walking several blocks?", false, limitedToNotLimited)
            .radio("SF36-11", "In a typical day, does your health now limit walking one block?", false, limitedToNotLimited)
            .radio("SF36-12", "In a typical day, does your health now limit bathing or dressing yourself?", false, limitedToNotLimited)
            .radio("SF36-13", "During the past 4 weeks, has your PHYSICAL health meant that you cut down the amount of time you spent on work or other activities?", false, yesNo)
            .radio("SF36-14", "During the past 4 weeks, has your PHYSICAL health meant that you accomplished less than you would like?", false, yesNo)
            .radio("SF36-15", "During the past 4 weeks, has your PHYSICAL health meant that you were limited in the kind of work or other activities?", false, yesNo)
            .radio("SF36-16", "During the past 4 weeks, has your PHYSICAL health meant that you had difficulty performing the work or other activities (for example, it took extra effort)?", false, yesNo)
            .radio("SF36-17", "During the past 4 weeks, have EMOTIONAL problems meant that you cut down the amount of time you spent on work or other activities?", false, yesNo)
            .radio("SF36-18", "During the past 4 weeks, have EMOTIONAL problems meant that you accomplished less than you would like?", false, yesNo)
            .radio("SF36-19", "During the past 4 weeks, have EMOTIONAL problems meant that you didn't do work or other activities as carefully as usual?", false, yesNo)
            .radio("SF36-20", "During the past 4 weeks, to what extent has your physical health or emotional problems interfered with your normal social activities with family, friends, neighbors, or groups?", false, notAtAllToExtremely)
            .radio("SF36-21", "How much bodily pain have you had during the past 4 weeks?", false, noneToSevere)
            .radio("SF36-22", "During the past 4 weeks, how much did pain interfere with your normal work (including both work outside the home and housework)?", false, notAtAllToExtremely)
            .radio("SF36-23", "In the past four weeks, how much of the time did you feel full of pep?", false, allToNoneOfTheTime)
            .radio("SF36-24", "In the past four weeks, how much of the time have you been a very nervous person?", false, allToNoneOfTheTime)
            .radio("SF36-25", "In the past four weeks, how much of the time have you felt so down in the dumps that nothing could cheer you up?", false, allToNoneOfTheTime)
            .radio("SF36-26", "In the past four weeks, how much of the time have you felt calm and peaceful?", false, allToNoneOfTheTime)
            .radio("SF36-27", "In the past four weeks, how much of the time did you have a lot of energy?", false, allToNoneOfTheTime)
            .radio("SF36-28", "In the past four weeks, how much of the time have you felt downhearted and blue?", false, allToNoneOfTheTime)
            .radio("SF36-29", "In the past four weeks, how much of the time did you feel worn out?", false, allToNoneOfTheTime)
            .radio("SF36-30", "In the past four weeks, how much of the time have you been a happy person?", false, allToNoneOfTheTime)
            .radio("SF36-31", "In the past four weeks, how much of the time did you feel tired?", false, allToNoneOfTheTime)
            .radio("SF36-32", "During the past 4 weeks, how much of the time have your physical health or emotional problems interfered with your social activities (like visiting with your friends, relatives, etc?)", false, allToNoneOfTheTime)
            .radio("SF36-33", "I seem to get sick a little easier than other people.", false, trueToFalse)
            .radio("SF36-34", "I am as healthy as anybody I know.", false, trueToFalse)
            .radio("SF36-35", "I expect my health to get worse.", false, trueToFalse)
            .radio("SF36-36", "My health is excellent.", false, trueToFalse)
            .build();
    }

}
