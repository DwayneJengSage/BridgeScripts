package org.sagebionetworks.bridge.scripts.onboarding;

import org.joda.time.Period;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.schedules.SimpleScheduleStrategy;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;

public class BreastcancerBackgroundSurvey {
    
    public static Survey create() {
        Survey survey = new Survey();
        survey.setIdentifier("bcs-background");
        survey.setName("Background Survey");

        SurveyBuilder builder = new SurveyBuilder(survey)
            .addSlider("BCPT1", "What is your age in years?", 18.0, 80.0)
            .addOneFromList("BCPT2", 
                "What category below best describes your racial/ethnic background? If you are of mixed racial/ethnic background, choose the category with which you most closely identify", 
                "White", "African American/Black", "Asian (Chinese, Japanese, Korean, South East Asian, Filipino)", "Native Hawaiian or Other Pacific Islander", "Native American/Alaska Native (American Indian, Aleutian, Eskimo)", "Indian Subcontinent", "Other", "Don't know"
            ).addYesNo("BCPT3", "Are you Hispanic or Latina?")
            .addOneFromList("BCPT4", 
                "What is your current marital/relationship status?",
                "Married", "Divorced", "Widowed",  "Legally separated", "Married but not living with spouse", "Committed relationship (partner opposite sex)", "Committed relationship (partner same sex)", "Single (never married)"
            ).addOneFromList("BCPT5", 
                "What category below best describes the highest level of formal education you have completed?",
                "Grade school (1-8 years)", "Some high school (9-11 years)", "High school graduate or GED (12 years)", "Vocational or training school after high school graduation", "Some college", "Associate Degree (A.D. or A.A.)", "College graduate (B.A. or B.S.)", "Some college or professional school after college graduation", "Completed Master’s Degree (M.A., M.S., M.P.H., M.S.W., M.Div., etc.)", "Completed Doctoral Degree (Ph.D., M.D., D.D.S., J.D., etc.)"
            ).addOneFromList("BCPT6",
                "What is your current employment status?",
                "Employed full-time (including self-employed)", "Employed part-time (including self-employed)", "Full-time homemaker", "Full-time or part-time volunteer", "Full-time or part-time student", "On temporary medical leave", "Retired", "Unemployed", "Permanently disabled"
            ).addOneFromList("BCPT7",
                "Check one income range that best describes your family's total income before taxes for the previous year, including salaries, wages, tips, social security, welfare and any other income.",
                "Under $15,000", "$15,001 - $30,000", "$30,001 - $60,000", "$60,001 - $100,000", "Over $100,000"
            ).addYesNo("BCPTHealth0", "Have you been treated for breast cancer?").onNoSkipTo("BCPTHealth12")
            .addPastDate("BCPTHealth1", "What was the month and year of your initial breast cancer diagnosis (date of 1st cancer biopsy)?")
            .addOneFromList("CPTHealth2", "In which breast(s) was your cancer diagnosed?", "Right", "Left", "Both")
            .addList("BCPTHealth3", 
                "What type(s) of surgery have you had for your breast cancer?",
                "Lumpectomy (left)", "Lumpectomy (right)", "Sentinel node biopsy (left)", "Sentinel node biopsy (right)", "Axillary node dissection (left)", "Axillary node dissection (right)", "Mastectomy (left)", "Mastectomy (right)", "Mastectomy with immediate reconstruction (left)", "Mastectomy with immediate reconstruction (right)", "Expander with later breast reconstruction planned (left)", "Expander with later breast reconstruction planned (right)"
            ).addYesNo("BCPTHealth4", "Did you receive chemotherapy after your breast cancer diagnosis or surgery?").onNoSkipTo("BCPTHealth5")
            .addPastDate("BCPTHealth4a", "When did you last have chemotherapy?")
            .addOneFromList("BCPTHealth4b", 
                "What kind of chemotherapy treatment did you have?",
                "TC (Taxotere and Cytoxan)", "TAC (Taxotere, Adriamycin, and Cytoxan)", "CMF (Cytoxan, Methotrexate and 5FU)", "CMF+A (Cytoxan, Methotrexate, 5FU and Adriamycin)", "AC (Adriamycin and Cytoxan)", "FAC or CAF (Cytoxan, Adriamycin and 5FU)", "AC+Taxol (Adriamycin, Cytoxan and Taxol)", "CEF or FEC (5FU, Epirubicin and Cytoxan)", "TC (Taxotere and Carboplatin)", "Some other chemotherapy not listed above"    
            )
            .addOneFromList("BCPTHealth5", 
                "Did you receive or are you currently receiving Herceptin (trastuzumab) or some other form of therapy that targets HER2 overexpression in your tumor?",
                "Yes, Herceptin", "Yes, some other therapy", "No"
            )
            .addYesNo("BCPTHealth6", "Did you receive radiation as part of your breast cancer treatment?").onNoSkipTo("BCPTHealth7")
            .addPastDate("BCPTHealth6a", "When did you last have radiation?")
            .addYesNo("BCPTHealth7", "Did you receive an endocrine therapy in the past for chemoprevention?").onNoSkipTo("BCPTHealth8")
            .addOneFromList("BCPTHealth7a", "What drug did you use for chemoprevention?",
                "Tamoxifen", "Raloxifene (Evista)", "Aromatase inhibitor"
            ).addPastDate("BCPTHealth7b", "When did you last take a drug for chemoprevention?")
            .addOneFromList("BCPTHealth8", 
                "Are you receiving endocrine therapy for breast cancer now (tamoxifen, ovarian suppression, Femara (letrozole), Anastrozole (arimidex), Aromasin (exemestane)?",
                "Yes", "No", "Don't Know"
            ).addYesNo("BCPTHealth9", "Have you ever taken hormone replacement therapy (estrogen in any form, other than birth control pills)?").onNoSkipTo("BCPTHealth12")
            .addPastDate("BCPTHealth9a", "When did you last have hormone replacement therapy?")
            .addList("BCPTHealth12", "Do you currently have any of the following conditions?",
                "Allergies", "Arthritis", "Asthma", "Diabetes", "Glaucoma", "Frequent headaches/migraines", "Heart disease (heart attack, angina, heart failure)", "High blood pressure", "Osteopenia or osteoporosis requiring medication", "Thyroid problems", "Moderate to severe psychological difficulties (such as depression, anxiety, recent suicide attempts, or recent mental health hospitalization)", "Problems with alcohol", "Problems with drug use or dependence (either prescription or street drugs)", "Other medical conditions", "I do not have any of these conditions"        
            );
        return builder.build();
    }
    
    public static SchedulePlan createPlan(String ref) {
        Schedule schedule = new Schedule();
        schedule.setLabel("Background Survey");
        schedule.setScheduleType(ScheduleType.once);
        schedule.setExpires(Period.parse("P14D"));
        schedule.getActivities().add(new Activity("Background Survey", ref));
        
        SchedulePlan plan = new SchedulePlan();
        plan.setSchedule(schedule);
        plan.setStrategy(new SimpleScheduleStrategy(schedule));
        return plan;
    }
    
}
