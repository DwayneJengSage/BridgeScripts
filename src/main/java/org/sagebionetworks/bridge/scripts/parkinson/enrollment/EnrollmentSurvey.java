package org.sagebionetworks.bridge.scripts.parkinson.enrollment;

import java.util.List;

import org.sagebionetworks.bridge.scripts.Scripts;
import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.scripts.enumerations.NumberList;
import org.sagebionetworks.bridge.scripts.enumerations.UnitedStatesList;
import org.sagebionetworks.bridge.scripts.enumerations.YesNoList;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.schedules.SurveyReference;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;

import com.google.common.collect.Lists;

public class EnrollmentSurvey {
    
    private static final List<SurveyQuestionOption> gender = Scripts.options(
        "Male", "Female", "Prefer not to answer"
    );
    private static final List<SurveyQuestionOption> race = Scripts.options(
        "Black or African",
        "Latino/Hispanic",
        "Native American",
        "Pacific Islander",
        "Middle Eastern",
        "Caribbean",
        "South Asian",
        "East Asian",
        "White or Caucasian",
        "Mixed"
    );
    private static final List<SurveyQuestionOption> education = Scripts.options(
        "Some high school",
        "High School Diploma/GED",
        "Some college",
        "2-year college degree",
        "4-year college degree",
        "Some graduate school",
        "Master's Degree",
        "Doctoral Degree"
    );
    private static final List<SurveyQuestionOption> employment = Scripts.options(
        "Employment for wages",
        "Self-employed",
        "Out of work and looking for work",
        "Out of work but not currently looking for work",
        "A homemaker",
        "A student",
        "Military",
        "Retired",
        "Unable to work"
    );
    private static final List<SurveyQuestionOption> maritalStatus = Scripts.options(
        "Single, never married",
        "Married or domestic partnership",
        "Widowed",
        "Divorced",
        "Separated"
    );
    private static final List<SurveyQuestionOption> veryEasyToVeryDifficult = Scripts.options(
        "Very easy",
        "Easy",
        "Neither easy nor difficult",
        "Difficult",
        "Very Difficult"
    );
    private static final List<SurveyQuestionOption> yesNoNotSure = Scripts.options(
        "Yes", "No", "Not sure"
    );
    private static final List<SurveyQuestionOption> yesNoVerbose = Lists.newArrayList(
        new SurveyQuestionOption("Yes, I have done this", "true"),
        new SurveyQuestionOption("No, I have never done this", "false")
    );
    private static final List<SurveyQuestionOption> didThis = Lists.newArrayList(
        new SurveyQuestionOption("Did this yesterday", "yes"),
        new SurveyQuestionOption("Did not do this yesterday", "no"),
        new SurveyQuestionOption("Don't know", "dont-know")
    );
    private static final List<SurveyQuestionOption> provider = Scripts.options(
        "Parkinson Disease/Movement Disorder Specialist",
        "General Neurologist (non-Parkinson Disease specialist)",
        "Primary Care Doctor",
        "Nurse Practitioner or Physician's Assistant",
        "Don't know"
    );
    private static final List<SurveyQuestionOption> diseases = Scripts.options(
        "AIDS or HIV",
        "Acute Myocardial Infarction/Heart Attack",
        "Alcoholism",
        "Alzheimer Disease or Alzheimer dementia",
        "Anemia",
        "Anxiety",
        "Asthma",
        "Atrial Fibrillation",
        "Breast Cancer",
        "Cancer OR tumor (see specific types of cancer in list)",
        "Cataract",
        "Chronic Obstructive Pulmonary Disease (COPD) or Asthma",
        "Colorectal Cancer",
        "Connective Tissue Disease",
        "Coronary Artery Disease",
        "Dementia",
        "Depression",
        "Diabetes or Pre-diabetes or High Blood Sugar",
        "Endometrial/Uterine Cancer",
        "Fainting/Syncope",
        "Glaucoma",
        "Head Injury with Loss of Consciousness/Concussion",
        "Heart Failure/Congestive Heart Failure",
        "High Blood Pressure/Hypertension",
        "Hip/Pelvic Fracture",
        "Impulse control disorder",
        "Ischemic Heart Disease",
        "Kidney Disease",
        "Leukemia or Lymphoma",
        "Liver Disease",
        "Lung Cancer",
        "Multiple Sclerosis",
        "Obstructive Sleep Apnea",
        "Osteoporosis",
        "Peripheral Vascular Disease",
        "Prostate Cancer",
        "Rheumatoid Arthritis",
        "Schizophrenia or Bipolar Disorder",
        "Stroke/Transient Ischemic Attack (TIA)",
        "Ulcer Disease",
        "Urinary Tract infections"
    );
    
    public static Survey create() {
        Survey survey = new Survey();
        survey.setName("Enrollment Survey");
        survey.setIdentifier("EnrollmentSurvey");
        return new SurveyBuilder(survey)
            .number("age", "How old are you?", 18d, 100d)
            .radio("gender", "What is your sex?", false, gender)
            .list("race", "Which race do you identify with?", true, race)
            .list("Enter_State", "In which state do you reside?", true, new UnitedStatesList())
            .radio("education", "What is the highest level of education that you have completed?", false, education)
            .radio("employment", "What is your current employment status?", false, employment)
            .radio("maritalStatus", "What is your current marital status?", false, maritalStatus)
            .radio("care-taker", "Are you a spouse, partner or care-partner of someone who has Parkinson disease?", false, new YesNoList())
            .radio("past-participation", "Have you ever participated in a research study or clinical trial on Parkinson disease before?", false, new YesNoList())
            .radio("smartphone", "How easy is it for you to use your smartphone?", false, veryEasyToVeryDifficult)
            .radio("phone-usage", "Do you ever use your smartphone to look for health or medical information online?", false, yesNoNotSure)
            .radio("home-usage", "Do you use the Internet or email at home?", false, new YesNoList())
            .radio("medical-usage", "Do you ever use the Internet to look for health or medical information online?", false, yesNoVerbose).onNoSkipTo("video-usage")
            .radio("medical-usage-yesterday", "Did you happen to do this yesterday, or not?", false, didThis)
            .radio("video-usage", "Do you ever use your smartphone to participate in a video call or video chat?", false, new YesNoList())
            .radio("professional-diagnosis", "Have you been diagnosed by a medical professional with Parkinson disease?", false, new YesNoList())
            .number("onset-year", "In what year did your movement symptoms begin?", 1900d, 2015d)
            .number("diagnosis-year", "In what year were you diagnosed with Parkinson disease?", 1900d, 2015d)
            .number("medication-start-year", "In what year did you begin taking Parkinson disease medication? Type in 0 if you have not started to take Parkinson medication.", 0d, 2015d)
            .radio("healthcare-provider", "What kind of health care provider currently cares for your Parkinson disease?", false, provider)
            .radio("deep-brain-stimulation", "Have you ever had Deep Brain Stimulation?", false, new YesNoList()).onNoSkipTo("surgery")
            .date("when-dbs", "When did you last have DBS?")
            .radio("surgery", "Have you ever had any surgery for Parkinson disease, other than DBS?", false, new YesNoList())
            .radio("smoked", "Have you ever smoked?", false, new YesNoList()).onNoSkipTo("health-history")
            .number("years-smoking", "How many years have you smoked?")
            .list("packs-per-day", "On average, how many packs did you smoke each day?", false, new NumberList(1,5))
            .date("last-smoked", "When is the last time you smoked (put todays date if you are still smoking)?")
            .list("health-history", "Has a doctor ever told you that you have, or have you ever taken medication for any of the following conditions? Please check all that apply.", true, diseases)
            .build();
    }
    
    public static SchedulePlan createSchedulePlan(String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel("Enrollment Survey Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel("Enrollment Survey Schedule");
        schedule.setScheduleType(ScheduleType.ONCE);

        SurveyReference reference = Scripts.getPublishedSurveyReference(surveyGuid);
        Activity activity = new Activity("Enrollment Survey", reference);
        schedule.addActivity(activity);

        plan.setSchedule(schedule);
        return plan;
    }    
}
