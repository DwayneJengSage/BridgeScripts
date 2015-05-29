package org.sagebionetworks.bridge.scripts.breastcancer.enrollment;

import java.util.List;

import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.schedules.SurveyReference;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;

import com.google.common.collect.Lists;

public class BCSBackgroundSurvey {
    
    private static List<SurveyQuestionOption> races = Lists.newArrayList();
    {
        races.add(new SurveyQuestionOption("White"));
        races.add(new SurveyQuestionOption("African American/Black"));
        races.add(new SurveyQuestionOption("Asian (Chinese, Japanese, Korean, South East Asian, Filipino)"));
        races.add(new SurveyQuestionOption("Native Hawaiian or Other Pacific Islander"));
        races.add(new SurveyQuestionOption("Native American/Alaska Native (American Indian, Aleutian, Eskimo)"));
        races.add(new SurveyQuestionOption("Indian Subcontinent"));
        races.add(new SurveyQuestionOption("Other"));
        races.add(new SurveyQuestionOption("Don't know"));
    }
    private static List<SurveyQuestionOption> yesNo = Lists.newArrayList();
    {
        yesNo.add(new SurveyQuestionOption("Yes", "true"));
        yesNo.add(new SurveyQuestionOption("No", "false"));
    }
    private static List<SurveyQuestionOption> marriedStatus = Lists.newArrayList();
    {
        marriedStatus.add(new SurveyQuestionOption("Married"));
        marriedStatus.add(new SurveyQuestionOption("Divorced"));
        marriedStatus.add(new SurveyQuestionOption("Divorced"));
        marriedStatus.add(new SurveyQuestionOption("Widowed"));
        marriedStatus.add(new SurveyQuestionOption("Legally separated"));
        marriedStatus.add(new SurveyQuestionOption("Married but not living with spouse"));
        marriedStatus.add(new SurveyQuestionOption("Committed relationship (partner opposite sex)"));
        marriedStatus.add(new SurveyQuestionOption("Committed relationship (partner same sex)"));
        marriedStatus.add(new SurveyQuestionOption("Single (never married)"));
    }
    private static List<SurveyQuestionOption> education = Lists.newArrayList();
    {
        education.add(new SurveyQuestionOption("Grade school (1-8 years)"));
        education.add(new SurveyQuestionOption("Some high school (9-11 years)"));
        education.add(new SurveyQuestionOption("High school graduate or GED (12 years)"));
        education.add(new SurveyQuestionOption("Vocational or training school after high school graduation"));
        education.add(new SurveyQuestionOption("Some college"));
        education.add(new SurveyQuestionOption("Associate Degree (A.D. or A.A.)"));
        education.add(new SurveyQuestionOption("College graduate (B.A. or B.S.)"));
        education.add(new SurveyQuestionOption("Some college or professional school after college graduation"));
        education.add(new SurveyQuestionOption("Completed Master#s Degree (M.A., M.S., M.P.H., M.S.W., M.Div., etc.)"));
        education.add(new SurveyQuestionOption("Completed Doctoral Degree (Ph.D., M.D., D.D.S., J.D., etc.)"));
    }
    private static List<SurveyQuestionOption> employment = Lists.newArrayList();
    {
        employment.add(new SurveyQuestionOption("Employed full-time (including self-employed)"));
        employment.add(new SurveyQuestionOption("Employed part-time (including self-employed)"));
        employment.add(new SurveyQuestionOption("Full-time homemaker"));
        employment.add(new SurveyQuestionOption("Full-time or part-time volunteer"));
        employment.add(new SurveyQuestionOption("Full-time or part-time student"));
        employment.add(new SurveyQuestionOption("On temporary medical leave"));
        employment.add(new SurveyQuestionOption("Retired"));
        employment.add(new SurveyQuestionOption("Unemployed"));
        employment.add(new SurveyQuestionOption("Permanently disabled"));
    }
    private static List<SurveyQuestionOption> income = Lists.newArrayList();
    {
        income.add(new SurveyQuestionOption("Under $15,000"));
        income.add(new SurveyQuestionOption("$15,001 - $30,000"));
        income.add(new SurveyQuestionOption("$30,001 - $60,000"));
        income.add(new SurveyQuestionOption("$60,001 - $100,000"));
        income.add(new SurveyQuestionOption("Over $100,000"));
    }
    private static List<SurveyQuestionOption> leftToRight = Lists.newArrayList();
    {
        leftToRight.add(new SurveyQuestionOption("Right"));
        leftToRight.add(new SurveyQuestionOption("Left"));
        leftToRight.add(new SurveyQuestionOption("Both"));
    }
    private static List<SurveyQuestionOption> surgeries = Lists.newArrayList();
    {
        surgeries.add(new SurveyQuestionOption("Lumpectomy (left)"));
        surgeries.add(new SurveyQuestionOption("Lumpectomy (right)"));
        surgeries.add(new SurveyQuestionOption("Sentinel node biopsy (left)"));
        surgeries.add(new SurveyQuestionOption("Sentinel node biopsy (right)"));
        surgeries.add(new SurveyQuestionOption("Axillary node dissection (left)"));
        surgeries.add(new SurveyQuestionOption("Axillary node dissection (right)"));
        surgeries.add(new SurveyQuestionOption("Mastectomy (left)"));
        surgeries.add(new SurveyQuestionOption("Mastectomy (right)"));
        surgeries.add(new SurveyQuestionOption("Mastectomy with immediate reconstruction (left)"));
        surgeries.add(new SurveyQuestionOption("Mastectomy with immediate reconstruction (right)"));
        surgeries.add(new SurveyQuestionOption("Expander with later breast reconstruction planned (left)"));
        surgeries.add(new SurveyQuestionOption("Expander with later breast reconstruction planned (right)"));
    }
    private static List<SurveyQuestionOption> chemos = Lists.newArrayList();
    {
        chemos.add(new SurveyQuestionOption("TC (Taxotere and Cytoxan)"));
        chemos.add(new SurveyQuestionOption("TAC (Taxotere, Adriamycin, and Cytoxan)"));
        chemos.add(new SurveyQuestionOption("CMF (Cytoxan, Methotrexate and 5FU)"));
        chemos.add(new SurveyQuestionOption("CMF+A (Cytoxan, Methotrexate, 5FU and Adriamycin)"));
        chemos.add(new SurveyQuestionOption("AC (Adriamycin and Cytoxan)"));
        chemos.add(new SurveyQuestionOption("FAC or CAF (Cytoxan, Adriamycin and 5FU)"));
        chemos.add(new SurveyQuestionOption("AC+Taxol (Adriamycin, Cytoxan and Taxol)"));
        chemos.add(new SurveyQuestionOption("CEF or FEC (5FU, Epirubicin and Cytoxan)"));
        chemos.add(new SurveyQuestionOption("TC (Taxotere and Carboplatin)"));
        chemos.add(new SurveyQuestionOption("Some other chemotherapy not listed above"));
    }
    private static List<SurveyQuestionOption> herceptin = Lists.newArrayList();
    {
        herceptin.add(new SurveyQuestionOption("Yes, Herceptin"));
        herceptin.add(new SurveyQuestionOption("Yes, some other therapy"));
        herceptin.add(new SurveyQuestionOption("No"));
    }
    private static List<SurveyQuestionOption> chemoprevention = Lists.newArrayList();
    {
        chemoprevention.add(new SurveyQuestionOption("Tamoxifen"));
        chemoprevention.add(new SurveyQuestionOption("Raloxifene (Evista)"));
        chemoprevention.add(new SurveyQuestionOption("Aromatase inhibitor"));
    }
    private static List<SurveyQuestionOption> yesNoDontKnow = Lists.newArrayList();
    {
        yesNoDontKnow.add(new SurveyQuestionOption("Yes"));
        yesNoDontKnow.add(new SurveyQuestionOption("No"));
        yesNoDontKnow.add(new SurveyQuestionOption("Don't Know"));
    }
    private static List<SurveyQuestionOption> conditions = Lists.newArrayList();
    {
        conditions.add(new SurveyQuestionOption("Allergies"));
        conditions.add(new SurveyQuestionOption("Arthritis"));
        conditions.add(new SurveyQuestionOption("Asthma"));
        conditions.add(new SurveyQuestionOption("Diabetes"));
        conditions.add(new SurveyQuestionOption("Glaucoma"));
        conditions.add(new SurveyQuestionOption("Frequent headaches/migraines"));
        conditions.add(new SurveyQuestionOption("Heart disease (heart attack, angina, heart failure)"));
        conditions.add(new SurveyQuestionOption("High blood pressure"));
        conditions.add(new SurveyQuestionOption("Osteopenia or osteoporosis requiring medication"));
        conditions.add(new SurveyQuestionOption("Thyroid problems"));
        conditions.add(new SurveyQuestionOption("Moderate to severe psychological difficulties (such as depression, anxiety, recent suicide attempts, or recent mental health hospitalization)"));
        conditions.add(new SurveyQuestionOption("Problems with alcohol"));
        conditions.add(new SurveyQuestionOption("Problems with drug use or dependence (either prescription or street drugs)"));
        conditions.add(new SurveyQuestionOption("Other medical conditions"));
        conditions.add(new SurveyQuestionOption("I do not have any of these conditions"));
    }

    public static Survey create() {
        Survey survey = new Survey();
        survey.setName("BCS Background Survey");
        survey.setIdentifier("BCSbackgroundSurvey");
        return new SurveyBuilder(survey)
            .slider("BCPT1", "What is your age?", 18, 80)
            .radio("BCPT2", "What category below best describes your racial/ethnic background? If you are of mixed racial/ethnic background, choose the category with which you most closely identify", false, races)
            .radio("BCPT3", "Are you Hispanic or Latina?", false, yesNo)
            .radio("BCPT4", "What is your current marital/relationship status?", false, marriedStatus)
            .radio("BCPT5", "What category below best describes the highest level of formal education you have completed?", false, education)
            .radio("BCPT6", "What is your current employment status?", false, education)
            .radio("BCPT7", "Check one income range that best describes your family's total income before taxes for the previous year, including salaries, wages, tips, social security, welfare and any other income.", false, income)
            .radio("BCPTHealth0", "Have you been treated for breast cancer?", false, yesNo).onNoSkipTo("BCPTHealth12")
            .date("BCPTHealth1", "What was the month and year of your initial breast cancer diagnosis (date of 1st cancer biopsy)?")
            .radio("BCPTHealth2", "In which breast(s) was your cancer diagnosed?", false, leftToRight)
            .list("BCPTHealth3", "What type(s) of surgery have you had for your breast cancer?", false, surgeries)
            .radio("BCPTHealth4", "Did you receive chemotherapy after your breast cancer diagnosis or surgery?", false, yesNo).onNoSkipTo("BCPTHealth5")
            .date("BCPTHealth4a", "When did you last have chemotherapy?")
            .radio("BCPTHealth4b", "What kind of chemotherapy treatment did you have?", false, chemos)
            .radio("BCPTHealth5", "Did you receive or are you currently receiving Herceptin (trastuzumab) or some other form of therapy that targets HER2 overexpression in your tumor?", false, herceptin)
            .radio("BCPTHealth6", "Did you receive radiation as part of your breast cancer treatment?", false, yesNo).onNoSkipTo("BCPTHealth7")
            .date("BCPTHealth6a", "When did you last have radiation?")
            .radio("BCPTHealth7", "Did you receive an endocrine therapy in the past for chemoprevention?", false, yesNo).onNoSkipTo("BCPTHealth8")
            .radio("BCPTHealth7a", "What drug did you use for chemoprevention?", false, chemoprevention)
            .date("BCPTHealth7b", "When did you last take a drug for chemoprevention?")
            .radio("BCPTHealth8", "Are you receiving endocrine therapy for breast cancer now (tamoxifen, ovarian suppression, Femara (letrozole), Anastrozole (arimidex), Aromasin (exemestane)?", false, yesNoDontKnow)
            .radio("BCPTHealth9", "Have you ever taken hormone replacement therapy (estrogen in any form, other than birth control pills)?", false, yesNo).onNoSkipTo("BCPTHealth12")
            .date("BCPTHealth9a", "When did you last have hormone replacement therapy?")
            .list("BCPTHealth12", "Do you currently have any of the following conditions?", true, conditions)
            .build();
    }
    
    public static SchedulePlan createSchedulePlan(String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel("Background Survey Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel("Background Survey Schedule");
        schedule.setScheduleType(ScheduleType.ONCE);

        Config config = ClientProvider.getConfig();
        String url = config.getRecentlyPublishedSurveyUserApi(surveyGuid);
        SurveyReference reference = new SurveyReference(url);
        Activity activity = new Activity("Background Survey", reference);
        schedule.addActivity(activity);

        plan.setSchedule(schedule);
        return plan;
    }     
}
