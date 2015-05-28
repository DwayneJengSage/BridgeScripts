package org.sagebionetworks.bridge.scripts.parkinson;

import java.util.List;

import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.scripts.enumerations.NumberList;
import org.sagebionetworks.bridge.scripts.enumerations.UnitedStatesList;
import org.sagebionetworks.bridge.scripts.enumerations.YesNoList;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;

import com.google.common.collect.Lists;

public class EnrollmentSurvey {
    
    private static final List<SurveyQuestionOption> gender = Lists.newArrayList();
    {
        gender.add(new SurveyQuestionOption("Male"));
        gender.add(new SurveyQuestionOption("Female"));
        gender.add(new SurveyQuestionOption("Prefer not to answer"));
    }
    private static final List<SurveyQuestionOption> race = Lists.newArrayList();
    {
        race.add(new SurveyQuestionOption("Black or African"));
        race.add(new SurveyQuestionOption("Latino/Hispanic"));
        race.add(new SurveyQuestionOption("Native American"));
        race.add(new SurveyQuestionOption("Pacific Islander"));
        race.add(new SurveyQuestionOption("Middle Eastern"));
        race.add(new SurveyQuestionOption("Caribbean"));
        race.add(new SurveyQuestionOption("South Asian"));
        race.add(new SurveyQuestionOption("East Asian"));
        race.add(new SurveyQuestionOption("White or Caucasian"));
        race.add(new SurveyQuestionOption("Mixed"));
    }
    private static final List<SurveyQuestionOption> education = Lists.newArrayList();
    {
        education.add(new SurveyQuestionOption("Some high school"));
        education.add(new SurveyQuestionOption("High School Diploma/GED"));
        education.add(new SurveyQuestionOption("Some college"));
        education.add(new SurveyQuestionOption("2-year college degree"));
        education.add(new SurveyQuestionOption("4-year college degree"));
        education.add(new SurveyQuestionOption("Some graduate school"));
        education.add(new SurveyQuestionOption("Master's Degree"));
        education.add(new SurveyQuestionOption("Doctoral Degree"));
    }
    private static final List<SurveyQuestionOption> employment = Lists.newArrayList();
    {
        employment.add(new SurveyQuestionOption("Employment for wages"));
        employment.add(new SurveyQuestionOption("Self-employed"));
        employment.add(new SurveyQuestionOption("Out of work and looking for work"));
        employment.add(new SurveyQuestionOption("Out of work but not currently looking for work"));
        employment.add(new SurveyQuestionOption("A homemaker"));
        employment.add(new SurveyQuestionOption("A student"));
        employment.add(new SurveyQuestionOption("Military"));
        employment.add(new SurveyQuestionOption("Retired"));
        employment.add(new SurveyQuestionOption("Unable to work"));
    }
    private static final List<SurveyQuestionOption> maritalStatus = Lists.newArrayList();
    {
        maritalStatus.add(new SurveyQuestionOption("Single, never married"));
        maritalStatus.add(new SurveyQuestionOption("Married or domestic partnership"));
        maritalStatus.add(new SurveyQuestionOption("Widowed"));
        maritalStatus.add(new SurveyQuestionOption("Divorced"));
        maritalStatus.add(new SurveyQuestionOption("Separated"));
    }
    private static final List<SurveyQuestionOption> veryEasyToVeryDifficult = Lists.newArrayList();
    {
        veryEasyToVeryDifficult.add(new SurveyQuestionOption("Very easy"));
        veryEasyToVeryDifficult.add(new SurveyQuestionOption("Easy"));
        veryEasyToVeryDifficult.add(new SurveyQuestionOption("Neither easy nor difficult"));
        veryEasyToVeryDifficult.add(new SurveyQuestionOption("Difficult"));
        veryEasyToVeryDifficult.add(new SurveyQuestionOption("Very Difficult"));
    }
    private static final List<SurveyQuestionOption> yesNoNotSure = Lists.newArrayList();
    {
        yesNoNotSure.add(new SurveyQuestionOption("Yes"));
        yesNoNotSure.add(new SurveyQuestionOption("No"));
        yesNoNotSure.add(new SurveyQuestionOption("Not sure"));
    }
    private static final List<SurveyQuestionOption> yesNoVerbose = Lists.newArrayList();
    {
        yesNoVerbose.add(new SurveyQuestionOption("Yes, I have done this", "true"));
        yesNoVerbose.add(new SurveyQuestionOption("No, I have never done this", "false"));
    }
    private static final List<SurveyQuestionOption> didThis = Lists.newArrayList();
    {
        didThis.add(new SurveyQuestionOption("Did this yesterday", "yes"));
        didThis.add(new SurveyQuestionOption("Did not do this yesterday", "no"));
        didThis.add(new SurveyQuestionOption("Don't know", "dont-know"));
    }
    private static final List<SurveyQuestionOption> provider = Lists.newArrayList();
    {
        provider.add(new SurveyQuestionOption("Parkinson Disease/Movement Disorder Specialist"));
        provider.add(new SurveyQuestionOption("General Neurologist (non-Parkinson Disease specialist)"));
        provider.add(new SurveyQuestionOption("Primary Care Doctor"));
        provider.add(new SurveyQuestionOption("Nurse Practitioner or Physician's Assistant"));
        provider.add(new SurveyQuestionOption("Don't know"));
    }
    private static final List<SurveyQuestionOption> diseases = Lists.newArrayList();
    {
        diseases.add(new SurveyQuestionOption("AIDS or HIV"));
        diseases.add(new SurveyQuestionOption("Acute Myocardial Infarction/Heart Attack"));
        diseases.add(new SurveyQuestionOption("Alcoholism"));
        diseases.add(new SurveyQuestionOption("Alzheimer Disease or Alzheimer dementia"));
        diseases.add(new SurveyQuestionOption("Anemia"));
        diseases.add(new SurveyQuestionOption("Anxiety"));
        diseases.add(new SurveyQuestionOption("Asthma"));
        diseases.add(new SurveyQuestionOption("Atrial Fibrillation"));
        diseases.add(new SurveyQuestionOption("Breast Cancer"));
        diseases.add(new SurveyQuestionOption("Cancer OR tumor (see specific types of cancer in list)"));
        diseases.add(new SurveyQuestionOption("Cataract"));
        diseases.add(new SurveyQuestionOption("Chronic Obstructive Pulmonary Disease (COPD) or Asthma"));
        diseases.add(new SurveyQuestionOption("Colorectal Cancer"));
        diseases.add(new SurveyQuestionOption("Connective Tissue Disease"));
        diseases.add(new SurveyQuestionOption("Coronary Artery Disease"));
        diseases.add(new SurveyQuestionOption("Dementia"));
        diseases.add(new SurveyQuestionOption("Depression"));
        diseases.add(new SurveyQuestionOption("Diabetes or Pre-diabetes or High Blood Sugar"));
        diseases.add(new SurveyQuestionOption("Endometrial/Uterine Cancer"));
        diseases.add(new SurveyQuestionOption("Fainting/Syncope"));
        diseases.add(new SurveyQuestionOption("Glaucoma"));
        diseases.add(new SurveyQuestionOption("Head Injury with Loss of Consciousness/Concussion"));
        diseases.add(new SurveyQuestionOption("Heart Failure/Congestive Heart Failure"));
        diseases.add(new SurveyQuestionOption("High Blood Pressure/Hypertension"));
        diseases.add(new SurveyQuestionOption("Hip/Pelvic Fracture"));
        diseases.add(new SurveyQuestionOption("Impulse control disorder"));
        diseases.add(new SurveyQuestionOption("Ischemic Heart Disease"));
        diseases.add(new SurveyQuestionOption("Kidney Disease"));
        diseases.add(new SurveyQuestionOption("Leukemia or Lymphoma"));
        diseases.add(new SurveyQuestionOption("Liver Disease"));
        diseases.add(new SurveyQuestionOption("Lung Cancer"));
        diseases.add(new SurveyQuestionOption("Multiple Sclerosis"));
        diseases.add(new SurveyQuestionOption("Obstructive Sleep Apnea"));
        diseases.add(new SurveyQuestionOption("Osteoporosis"));
        diseases.add(new SurveyQuestionOption("Peripheral Vascular Disease"));
        diseases.add(new SurveyQuestionOption("Prostate Cancer"));
        diseases.add(new SurveyQuestionOption("Rheumatoid Arthritis"));
        diseases.add(new SurveyQuestionOption("Schizophrenia or Bipolar Disorder"));
        diseases.add(new SurveyQuestionOption("Stroke/Transient Ischemic Attack (TIA)"));
        diseases.add(new SurveyQuestionOption("Ulcer Disease"));
        diseases.add(new SurveyQuestionOption("Urinary Tract infections"));
    }
    
    public static Survey create() {
        Survey survey = new Survey();
        survey.setName("Enrollment Survey");
        survey.setIdentifier("EnrollmentSurvey");
        return new SurveyBuilder(survey)
            .addNumber("age", null, "How old are you?", 18d, 100d)
            .addRadio("gender", null, "What is your sex?", false, gender)
            .addList("race", null, "Which race do you identify with?", true, race)
            .addList("Enter_State", null, "In which state do you reside?", true, new UnitedStatesList())
            .addRadio("education", null, "What is the highest level of education that you have completed?", false, education)
            .addRadio("employment", null, "What is your current employment status?", false, employment)
            .addRadio("maritalStatus", null, "What is your current marital status?", false, maritalStatus)
            .addRadio("care-taker", null, "Are you a spouse, partner or care-partner of someone who has Parkinson disease?", false, new YesNoList())
            .addRadio("past-participation", null, "Have you ever participated in a research study or clinical trial on Parkinson disease before?", false, new YesNoList())
            .addRadio("smartphone", null, "How easy is it for you to use your smartphone?", false, veryEasyToVeryDifficult)
            .addRadio("phone-usage", null, "Do you ever use your smartphone to look for health or medical information online?", false, yesNoNotSure)
            .addRadio("home-usage", null, "Do you use the Internet or email at home?", false, new YesNoList())
            .addRadio("medical-usage", null, "Do you ever use the Internet to look for health or medical information online?", false, yesNoVerbose).onNoSkipTo("video-usage")
            .addRadio("medical-usage-yesterday", null, "Did you happen to do this yesterday, or not?", false, didThis)
            .addRadio("video-usage", null, "Do you ever use your smartphone to participate in a video call or video chat?", false, new YesNoList())
            .addRadio("professional-diagnosis", null, "Have you been diagnosed by a medical professional with Parkinson disease?", false, new YesNoList())
            .addNumber("onset-year", null, "In what year did your movement symptoms begin?", 1900d, 2015d)
            .addNumber("diagnosis-year", null, "In what year were you diagnosed with Parkinson disease?", 1900d, 2015d)
            .addNumber("medication-start-year", null, "In what year did you begin taking Parkinson disease medication? Type in 0 if you have not started to take Parkinson medication.", 0d, 2015d)
            .addRadio("healthcare-provider", null, "What kind of health care provider currently cares for your Parkinson disease?", false, provider)
            .addRadio("deep-brain-stimulation", null, "Have you ever had Deep Brain Stimulation?", false, new YesNoList()).onNoSkipTo("surgery")
            .addDate("when-dbs", null, "When did you last have DBS?")
            .addRadio("surgery", null, "Have you ever had any surgery for Parkinson disease, other than DBS?", false, new YesNoList())
            .addRadio("smoked", null, "Have you ever smoked?", false, new YesNoList()).onNoSkipTo("health-history")
            .addNumber("years-smoking", null, "How many years have you smoked?")
            .addList("packs-per-day", null, "On average, how many packs did you smoke each day?", false, new NumberList(1,5))
            .addDate("last-smoked", null, "When is the last time you smoked (put todays date if you are still smoking)?")
            .addList("health-history", null, "Has a doctor ever told you that you have, or have you ever taken medication for any of the following conditions? Please check all that apply.", true, diseases)
            .build();
    }

}
