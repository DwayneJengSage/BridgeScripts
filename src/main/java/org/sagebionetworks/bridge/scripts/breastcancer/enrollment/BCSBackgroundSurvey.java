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

import com.google.common.collect.Lists;

public class BCSBackgroundSurvey implements SurveyProvider {
    
    private List<SurveyQuestionOption> races = Scripts.options(
        "White",
        "African American/Black",
        "Asian (Chinese, Japanese, Korean, South East Asian, Filipino)",
        "Native Hawaiian or Other Pacific Islander",
        "Native American/Alaska Native (American Indian, Aleutian, Eskimo)",
        "Indian Subcontinent",
        "Other",
        "Don't know"
    );
    private List<SurveyQuestionOption> yesNo = Lists.newArrayList(
        new SurveyQuestionOption("Yes", "true"),
        new SurveyQuestionOption("No", "false")
    );
    private List<SurveyQuestionOption> marriedStatus = Scripts.options(
        "Married",
        "Divorced",
        "Divorced",
        "Widowed",
        "Legally separated",
        "Married but not living with spouse",
        "Committed relationship (partner opposite sex)",
        "Committed relationship (partner same sex)",
        "Single (never married)"
    );
    private List<SurveyQuestionOption> education = Scripts.options(
        "Grade school (1-8 years)",
        "Some high school (9-11 years)",
        "High school graduate or GED (12 years)",
        "Vocational or training school after high school graduation",
        "Some college",
        "Associate Degree (A.D. or A.A.)",
        "College graduate (B.A. or B.S.)",
        "Some college or professional school after college graduation",
        "Completed Master#s Degree (M.A., M.S., M.P.H., M.S.W., M.Div., etc.)",
        "Completed Doctoral Degree (Ph.D., M.D., D.D.S., J.D., etc.)"
    );
    private List<SurveyQuestionOption> employment = Scripts.options(
        "Employed full-time (including self-employed)",
        "Employed part-time (including self-employed)",
        "Full-time homemaker",
        "Full-time or part-time volunteer",
        "Full-time or part-time student",
        "On temporary medical leave",
        "Retired",
        "Unemployed",
        "Permanently disabled"
    );
    private List<SurveyQuestionOption> income = Scripts.options(
        "Under $15,000", "$15,001 - $30,000", "$30,001 - $60,000",
        "$60,001 - $100,000", "Over $100,000"
    );
    private List<SurveyQuestionOption> leftToRight = Scripts.options(
        "Right", "Left", "Both"
    );
    private List<SurveyQuestionOption> surgeries = Scripts.options(
        "Lumpectomy (left)",
        "Lumpectomy (right)",
        "Sentinel node biopsy (left)",
        "Sentinel node biopsy (right)",
        "Axillary node dissection (left)",
        "Axillary node dissection (right)",
        "Mastectomy (left)",
        "Mastectomy (right)",
        "Mastectomy with immediate reconstruction (left)",
        "Mastectomy with immediate reconstruction (right)",
        "Expander with later breast reconstruction planned (left)",
        "Expander with later breast reconstruction planned (right)"
    );
    private List<SurveyQuestionOption> chemos = Scripts.options(
        "TC (Taxotere and Cytoxan)",
        "TAC (Taxotere, Adriamycin, and Cytoxan)",
        "CMF (Cytoxan, Methotrexate and 5FU)",
        "CMF+A (Cytoxan, Methotrexate, 5FU and Adriamycin)",
        "AC (Adriamycin and Cytoxan)",
        "FAC or CAF (Cytoxan, Adriamycin and 5FU)",
        "AC+Taxol (Adriamycin, Cytoxan and Taxol)",
        "CEF or FEC (5FU, Epirubicin and Cytoxan)",
        "TC (Taxotere and Carboplatin)",
        "Some other chemotherapy not listed above"
    );
    private List<SurveyQuestionOption> herceptin = Scripts.options(
        "Yes, Herceptin",
        "Yes, some other therapy",
        "No"
    );
    private List<SurveyQuestionOption> chemoprevention = Scripts.options(
        "Tamoxifen",
        "Raloxifene (Evista)",
        "Aromatase inhibitor"
    );
    private List<SurveyQuestionOption> yesNoDontKnow = Scripts.options(
        "Yes",
        "No",
        "Don't Know"
    );
    private List<SurveyQuestionOption> conditions = Scripts.options(
        "Allergies",
        "Arthritis",
        "Asthma",
        "Diabetes",
        "Glaucoma",
        "Frequent headaches/migraines",
        "Heart disease (heart attack, angina, heart failure)",
        "High blood pressure",
        "Osteopenia or osteoporosis requiring medication",
        "Thyroid problems",
        "Moderate to severe psychological difficulties (such as depression, anxiety, recent suicide attempts, or recent mental health hospitalization)",
        "Problems with alcohol",
        "Problems with drug use or dependence (either prescription or street drugs)",
        "Other medical conditions",
        "I do not have any of these conditions"
    );

    @Override
    public Survey createSurvey() {
        Survey survey = new Survey();
        survey.setName("BCS Background Survey");
        survey.setIdentifier("BCSbackgroundSurvey");
        return new SurveyBuilder(survey)
            .slider("BCPT1", "What is your age?", 18, 80)
            .radio("BCPT2", "What category below best describes your racial/ethnic background? If you are of mixed racial/ethnic background, choose the category with which you most closely identify", false, races)
            .radio("BCPT3", "Are you Hispanic or Latina?", false, yesNo)
            .radio("BCPT4", "What is your current marital/relationship status?", false, marriedStatus)
            .radio("BCPT5", "What category below best describes the highest level of formal education you have completed?", false, education)
            .radio("BCPT6", "What is your current employment status?", false, employment)
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
    
    @Override
    public SchedulePlan createSchedulePlan(String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel("Background Survey Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel("Background Survey Schedule");
        schedule.setScheduleType(ScheduleType.ONCE);

        SurveyReference reference = Scripts.getPublishedSurveyReference(surveyGuid);
        Activity activity = new Activity("Background Survey", reference);
        schedule.addActivity(activity);

        plan.setSchedule(schedule);
        return plan;
    }     
}
