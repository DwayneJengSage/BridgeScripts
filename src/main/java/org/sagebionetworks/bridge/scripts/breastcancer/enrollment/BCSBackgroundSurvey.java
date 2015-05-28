package org.sagebionetworks.bridge.scripts.breastcancer.enrollment;

import java.util.List;

import org.sagebionetworks.bridge.scripts.SurveyBuilder;
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
    private static List<SurveyQuestionOption> meds = Lists.newArrayList();
    {
        meds.add(new SurveyQuestionOption("Allergies"));
        meds.add(new SurveyQuestionOption("Arthritis"));
        meds.add(new SurveyQuestionOption("Asthma"));
        meds.add(new SurveyQuestionOption("Diabetes"));
        meds.add(new SurveyQuestionOption("Glaucoma"));
        meds.add(new SurveyQuestionOption("Frequent headaches/migraines"));
        meds.add(new SurveyQuestionOption("Heart disease (heart attack, angina, heart failure)"));
        meds.add(new SurveyQuestionOption("High blood pressure"));
        meds.add(new SurveyQuestionOption("Osteopenia or osteoporosis requiring medication"));
        meds.add(new SurveyQuestionOption("Thyroid problems"));
        meds.add(new SurveyQuestionOption("Moderate to severe psychological difficulties (such as depression, anxiety, recent suicide attempts, or recent mental health hospitalization)"));
        meds.add(new SurveyQuestionOption("Problems with alcohol"));
        meds.add(new SurveyQuestionOption("Problems with drug use or dependence (either prescription or street drugs)"));
        meds.add(new SurveyQuestionOption("Other medical conditions"));
        meds.add(new SurveyQuestionOption("I do not have any of these conditions"));
    }

    public static Survey create() {
        Survey survey = new Survey();
        survey.setName("BCS Background Survey");
        survey.setIdentifier("BCSbackgroundSurvey");
        return new SurveyBuilder(survey)
            .addRadio("BCPT2", null, "What category below best describes your racial/ethnic background? If you are of mixed racial/ethnic background, choose the category with which you most closely identify", false, races)
            .addRadio("BCPT3", null, "Are you Hispanic or Latina?", false, yesNo)
            .addRadio("BCPT4", null, "What is your current marital/relationship status?", false, marriedStatus)
            .addRadio("BCPT5", null, "What category below best describes the highest level of formal education you have completed?", false, education)
            .addRadio("BCPT6", null, "What is your current employment status?", false, education)
            .addRadio("BCPT7", null, "Check one income range that best describes your family's total income before taxes for the previous year, including salaries, wages, tips, social security, welfare and any other income.", false, income)
            .addRadio("BCPTHealth0", null, "Have you been treated for breast cancer?", false, yesNo).onNoSkipTo("BCPTHealth12")
            .addDate("BCPTHealth1", null, "What was the month and year of your initial breast cancer diagnosis (date of 1st cancer biopsy)?")
            .addRadio("BCPTHealth2", null, "In which breast(s) was your cancer diagnosed?", false, leftToRight)
            .addList("BCPTHealth3", null, "What type(s) of surgery have you had for your breast cancer?", false, surgeries)
            .addRadio("BCPTHealth4", null, "Did you receive chemotherapy after your breast cancer diagnosis or surgery?", false, yesNo).onNoSkipTo("BCPTHealth5")
            .addDate("BCPTHealth4a", null, "When did you last have chemotherapy?")
            .addRadio("BCPTHealth4b", null, "What kind of chemotherapy treatment did you have?", false, chemos)
            .addRadio("BCPTHealth5", null, "Did you receive or are you currently receiving Herceptin (trastuzumab) or some other form of therapy that targets HER2 overexpression in your tumor?", false, herceptin)
            .build();
    }

}