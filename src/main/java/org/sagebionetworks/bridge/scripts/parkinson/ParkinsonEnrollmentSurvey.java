package org.sagebionetworks.bridge.scripts.onboarding;

import java.util.Arrays;
import java.util.List;

import org.sagebionetworks.bridge.sdk.models.holders.GuidCreatedOnVersionHolder;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.surveys.Constraints;
import org.sagebionetworks.bridge.sdk.models.surveys.DataType;
import org.sagebionetworks.bridge.sdk.models.surveys.DateConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.IntegerConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.MultiValueConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestion;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyRule;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyRule.Operator;
import org.sagebionetworks.bridge.sdk.models.surveys.UiHint;

import com.google.common.collect.Lists;

/**
 * There are 41 questions in this survey. Seriously.
 */
public class ParkinsonEnrollmentSurvey extends BaseSurvey {
    
    static SurveyQuestion age = new SurveyQuestion();
    {
        age.setIdentifier("age");
        age.setPrompt("How old are you?");
        age.setUiHint(UiHint.NUMBERFIELD);
        IntegerConstraints ic = new IntegerConstraints();
        ic.setMinValue(18d);
        age.setConstraints(ic);
    };
    
    static SurveyQuestion gender = new SurveyQuestion(); 
    {
        gender.setIdentifier("gender");
        gender.setPrompt("What is your sex?");
        gender.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        List<SurveyQuestionOption> list = Arrays.asList(
            new SurveyQuestionOption("Male"),
            new SurveyQuestionOption("Female"),
            new SurveyQuestionOption("Prefer not to answer")
        );
        c.setEnumeration(list);
        gender.setConstraints(c);
    };
    
    static SurveyQuestion race = new SurveyQuestion(); 
    {
        race.setIdentifier("race");
        race.setPrompt("Which race do you identify with?");
        race.setUiHint(UiHint.CHECKBOX);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setAllowMultiple(true);
        c.setAllowOther(true);
        c.setEnumeration(Lists.newArrayList(
            new SurveyQuestionOption("Black or African"),
            new SurveyQuestionOption("Latino/Hispanic"),
            new SurveyQuestionOption("Native American"),
            new SurveyQuestionOption("Pacific Islander"),
            new SurveyQuestionOption("Middle Eastern"),
            new SurveyQuestionOption("Carribean"),
            new SurveyQuestionOption("South Asian"),
            new SurveyQuestionOption("East Asian"),
            new SurveyQuestionOption("White or Caucasian"),
            new SurveyQuestionOption("Mixed")
            // and allow other. Hope they catch that...
        ));
        race.setConstraints(c);
    };
    
    static SurveyQuestion countryOfResidence = new SurveyQuestion(); 
    {
        countryOfResidence.setIdentifier("residence");
        countryOfResidence.setPrompt("What is your country of residence?");
        countryOfResidence.setUiHint(UiHint.SELECT);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setEnumeration(new CountryOfResidenceList());
        c.setRules(Lists.newArrayList(
            new SurveyRule(Operator.EQ, "USA", "state"),
            new SurveyRule(Operator.NE, "USA", "education"),
            new SurveyRule(Operator.DE, null, "education")
        ));
        countryOfResidence.setConstraints(c);
    };
    
    static SurveyQuestion stateOfResidence = new SurveyQuestion(); 
    {
        stateOfResidence.setIdentifier("state");
        stateOfResidence.setPrompt("In which state do you reside?");
        stateOfResidence.setUiHint(UiHint.SELECT);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setEnumeration(new UnitedStatesList());
        stateOfResidence.setConstraints(c);
    };
    
    static SurveyQuestion education = new SurveyQuestion(); 
    {
        education.setIdentifier("education");
        education.setPrompt("What is the highest level of education that you have completed?");
        education.setUiHint(UiHint.SELECT);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setEnumeration(Lists.newArrayList(
            new SurveyQuestionOption("Doctoral Degree"),
            new SurveyQuestionOption("Master's Degree"),
            new SurveyQuestionOption("Some graduate school"),
            new SurveyQuestionOption("4-year college degree"),
            new SurveyQuestionOption("2-year college degree"),
            new SurveyQuestionOption("Some college"),
            new SurveyQuestionOption("High School Diploma/GED"),
            new SurveyQuestionOption("Some high school")
        ));
        education.setConstraints(c);
    };
    
    static SurveyQuestion employment = new SurveyQuestion(); 
    {
        employment.setIdentifier("employment");
        employment.setPrompt("What is your current employment status?");
        employment.setUiHint(UiHint.SELECT);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setEnumeration(Lists.newArrayList(
            new SurveyQuestionOption("Employment for wages"),
            new SurveyQuestionOption("Self-employed"),
            new SurveyQuestionOption("Out of work and looking for work"),
            new SurveyQuestionOption("Out of work but not currently looking for work"),
            new SurveyQuestionOption("A homemaker"),
            new SurveyQuestionOption("A student"),
            new SurveyQuestionOption("Military"),
            new SurveyQuestionOption("Retired"),
            new SurveyQuestionOption("Unable to work")
        ));
        employment.setConstraints(c);
    };
    
    static SurveyQuestion maritalStatus = new SurveyQuestion(); 
    {
        maritalStatus.setIdentifier("maritalStatus");
        maritalStatus.setPrompt("What is your current marital status?");
        maritalStatus.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setAllowOther(true);
        c.setEnumeration(Lists.newArrayList(
            new SurveyQuestionOption("Single, never married"),
            new SurveyQuestionOption("Married or domestic partnership"),
            new SurveyQuestionOption("Widowed"),
            new SurveyQuestionOption("Divorced"),
            new SurveyQuestionOption("Separated")
            // and other
        ));
        maritalStatus.setConstraints(c);
    };
    
    static SurveyQuestion areCaretaker = new SurveyQuestion(); 
    {
       areCaretaker.setIdentifier("are-caretaker");
       areCaretaker.setPrompt("Are you a spouse, partner or carepartner of someone who has Parkinson disease?");
       areCaretaker.setUiHint(UiHint.RADIOBUTTON);
       areCaretaker.setConstraints(ScriptUtils.booleanish());
    };
    
    static SurveyQuestion pastParticipation = new SurveyQuestion(); 
    {
        pastParticipation.setIdentifier("past-participation");
        pastParticipation.setPrompt("Have you ever participated in a research study or clinical trial on Parkinson disease before?");
        pastParticipation.setUiHint(UiHint.RADIOBUTTON);
        pastParticipation.setConstraints(ScriptUtils.booleanish());
     };
    
     static SurveyQuestion smartphone = new SurveyQuestion(); 
     {
         smartphone.setIdentifier("smartphone");
         smartphone.setPrompt("How easy is it for you to use your smart phone?");
         smartphone.setUiHint(UiHint.RADIOBUTTON);
         MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
         c.setEnumeration(Lists.newArrayList(
             new SurveyQuestionOption("Very easy"),
             new SurveyQuestionOption("Easy"),
             new SurveyQuestionOption("Neither easy nor difficult"),
             new SurveyQuestionOption("Difficult"),
             new SurveyQuestionOption("Very Difficult")
         ));
         smartphone.setConstraints(c);
    };
    
    static SurveyQuestion phoneUsage = new SurveyQuestion(); 
    {
        phoneUsage.setIdentifier("phone-usage");
        phoneUsage.setPrompt("Do you ever use your cell phone to look for health or medical information online?");
        phoneUsage.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setEnumeration(Lists.newArrayList(
                new SurveyQuestionOption("Yes"),
                new SurveyQuestionOption("No"),
                new SurveyQuestionOption("Not sure")
        ));
        phoneUsage.setConstraints(c);
    };
    
    static SurveyQuestion homeUsage = new SurveyQuestion(); 
    {
        homeUsage.setIdentifier("home-usage");
        homeUsage.setPrompt("Do you use the internet or email at home?");
        homeUsage.setUiHint(UiHint.RADIOBUTTON);
        homeUsage.setConstraints(ScriptUtils.booleanish());
    };
     
    static SurveyQuestion medicalUsage  = new SurveyQuestion(); 
    {
        medicalUsage.setIdentifier("medical-usage");
        medicalUsage.setPrompt("Do you ever use the Internet to look for health or medical information online?");
        medicalUsage.setUiHint(UiHint.RADIOBUTTON);
        // Could be a boolean, but many of these have a third option, which is "don't know"
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setEnumeration(Lists.newArrayList(
            new SurveyQuestionOption("Yes, I have done this", "yes"),
            new SurveyQuestionOption("No, I have never done this", "no")
        ));
        c.getRules().add(new SurveyRule(Operator.EQ, "yes", "medical-usage-yesterday"));
        c.getRules().add(new SurveyRule(Operator.DE, null, "video-usage"));
        c.getRules().add(new SurveyRule(Operator.NE, "yes", "video-usage"));
        medicalUsage.setConstraints(c);
    };
    
    static SurveyQuestion medicalUsageYesterday = new SurveyQuestion(); 
    {
        medicalUsageYesterday.setIdentifier("medical-usage-yesterday");
        medicalUsageYesterday.setPrompt("Did you happen to do this yesterday, or not?");
        medicalUsageYesterday.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setEnumeration(Lists.newArrayList(
            new SurveyQuestionOption("Did this yesterday", "yes"),
            new SurveyQuestionOption("Did not do this yesterday", "no"),
            new SurveyQuestionOption("Don't know", "dont-know")
        ));
        medicalUsageYesterday.setConstraints(c);
    };
    
    static SurveyQuestion videoUsage = new SurveyQuestion(); 
    {
        videoUsage.setIdentifier("video-usage");
        videoUsage.setPrompt("Do you ever use your smartphone to participate in a video call or video chat?");
        videoUsage.setUiHint(UiHint.RADIOBUTTON);
        videoUsage.setConstraints(ScriptUtils.booleanish());
    };
    
    static SurveyQuestion professionalDiagnosis = new SurveyQuestion(); 
    {
        professionalDiagnosis.setIdentifier("professional-diagnosis");
        professionalDiagnosis.setPrompt("Have you been diagnosed by a medical professional with Parkinson disease?");
        professionalDiagnosis.setUiHint(UiHint.RADIOBUTTON);
        Constraints c = ScriptUtils.booleanish();
        c.getRules().add(new SurveyRule(Operator.EQ, "true", "onset-year"));
        c.getRules().add(new SurveyRule(Operator.DE, null, "deep-brain-stimulation"));
        c.getRules().add(new SurveyRule(Operator.NE, "true", "deep-brain-stimulation"));
        professionalDiagnosis.setConstraints(ScriptUtils.booleanish());
    };
    
    static SurveyQuestion onsetYear = new SurveyQuestion(); 
    {
        onsetYear.setIdentifier("onset-year");
        onsetYear.setPrompt("In what year did your movement symptoms begin?");
        onsetYear.setUiHint(UiHint.NUMBERFIELD);
        onsetYear.setConstraints(new IntegerConstraints());
    };
    
    static SurveyQuestion diagnosisYear = new SurveyQuestion(); 
    {
        diagnosisYear.setIdentifier("diagnosis-year");
        diagnosisYear.setPrompt("In what year were you diagnosed with Parkinson disease?");
        diagnosisYear.setUiHint(UiHint.NUMBERFIELD);
        diagnosisYear.setConstraints(new IntegerConstraints());
    };
      
    static SurveyQuestion medicationStartYear = new SurveyQuestion(); 
    {
        medicationStartYear.setIdentifier("medication-start-year");
        medicationStartYear.setPrompt("In what year did you begin taking Parkinson disease medication? Leave blank if you have not started taking medication.");
        medicationStartYear.setUiHint(UiHint.NUMBERFIELD);
        medicationStartYear.setConstraints(new IntegerConstraints());
    };
    
    static SurveyQuestion healthCareProvider = new SurveyQuestion(); 
    {
        healthCareProvider.setIdentifier("healthcare-provider");
        healthCareProvider.setPrompt("What kind of health care provider currently cares for your Parkinson disease?");
        healthCareProvider.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setAllowOther(true);
        c.setEnumeration(Lists.newArrayList(
           new SurveyQuestionOption("Parkinson Disease/Movement Disorder Specialist"),
           new SurveyQuestionOption("General Neurologist (non-Parkinson Disease specialist)"),
           new SurveyQuestionOption("Primary Care Doctor"),
           new SurveyQuestionOption("Nurse Practitioner or Physician's Assistant"),
           new SurveyQuestionOption("Don't know")
           // or other
        ));
        healthCareProvider.setConstraints(c);
    };
    
    static SurveyQuestion deepBrainStimulation = new SurveyQuestion(); 
    {
        deepBrainStimulation.setIdentifier("deep-brain-stimulation");
        deepBrainStimulation.setPrompt("Have you ever had Deep Brain Stimulation?");
        deepBrainStimulation.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints c = ScriptUtils.booleanish();
        c.getRules().add(new SurveyRule(Operator.EQ, "true", "when-dbs"));
        c.getRules().add(new SurveyRule(Operator.NE, "true", "surgery"));
        c.getRules().add(new SurveyRule(Operator.DE, null, "surgery"));
        deepBrainStimulation.setConstraints(c);
    };
    
    static SurveyQuestion whenDBS = new SurveyQuestion(); 
    {
        whenDBS.setIdentifier("when-dbs");
        whenDBS.setPrompt("When did you have DBS?");
        whenDBS.setUiHint(UiHint.DATEPICKER);
        DateConstraints dc = new DateConstraints();
        dc.setAllowFuture(false);
        whenDBS.setConstraints(dc);
    };
    
    static SurveyQuestion surgery = new SurveyQuestion(); 
    {
        surgery.setIdentifier("surgery");
        surgery.setPrompt("Have you ever had any surgery for Parkinson disease, other than DBS?");
        surgery.setUiHint(UiHint.RADIOBUTTON);
        surgery.setConstraints(ScriptUtils.booleanish());
    };
    
    static SurveyQuestion smoked = new SurveyQuestion(); 
    {
        smoked.setIdentifier("smoked");
        smoked.setPrompt("Have you ever smoked?");
        smoked.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints c = ScriptUtils.booleanish();
        c.getRules().add(new SurveyRule(Operator.EQ, "true", "years-smoking"));
        c.getRules().add(new SurveyRule(Operator.NE, "true", "health-history"));
        c.getRules().add(new SurveyRule(Operator.DE, null, "health-history"));
        smoked.setConstraints(c);
    };
    
    static SurveyQuestion yearsSmoking = new SurveyQuestion(); 
    {
        yearsSmoking.setIdentifier("years-smoking");
        yearsSmoking.setPrompt("How many years have you smoked?");
        yearsSmoking.setUiHint(UiHint.SELECT);
        IntegerConstraints c = new IntegerConstraints();
        c.setEnumeration(new NumberList(0,70));
        yearsSmoking.setConstraints(c);
    };
    
    static SurveyQuestion packsPerDay = new SurveyQuestion(); 
    {
        packsPerDay.setIdentifier("packs-per-day");
        packsPerDay.setPrompt("On average, how many packs did you smoke each day?");
        packsPerDay.setUiHint(UiHint.SELECT);
        IntegerConstraints c = new IntegerConstraints();
        c.setEnumeration(new NumberList(1,5));
        packsPerDay.setConstraints(c);
    };
    
    static SurveyQuestion lastSmoked = new SurveyQuestion(); 
    {
        lastSmoked.setIdentifier("last-smoked");
        lastSmoked.setPrompt("When is the last time you smoked (leave blank if you are still smoking)?");
        lastSmoked.setUiHint(UiHint.DATEPICKER);
        DateConstraints dc = new DateConstraints();
        dc.setAllowFuture(false);
        lastSmoked.setConstraints(dc);
    };
    
    static SurveyQuestion healthHistory = new SurveyQuestion(); 
    {
        healthHistory.setIdentifier("health-history");
        healthHistory.setPrompt("Has a doctor ever told you that you have, or have you ever taken medication for any of the following conditions? Please check all that apply.");
        healthHistory.setUiHint(UiHint.LIST);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setAllowMultiple(true);
        c.setEnumeration(Lists.newArrayList(
            new SurveyQuestionOption("Acute Myocardial Infarction/Heart Attack"),
            new SurveyQuestionOption("Alzheimer Disease or Alzheimer dementia"),
            new SurveyQuestionOption("Atrial Fibrillation"),
            new SurveyQuestionOption("Anxiety"),
            new SurveyQuestionOption("Cataract"),
            new SurveyQuestionOption("Kidney Disease"),
            new SurveyQuestionOption("Chronic Obstructive Pulumonary Disease (COPD) or Asthma"),
            new SurveyQuestionOption("Heart Failure/Congestive Heart Failure"),
            new SurveyQuestionOption("Diabetes or Prediabetes or High Blood Sugar"),
            new SurveyQuestionOption("Glaucoma"),
            new SurveyQuestionOption("Hip/Pelvic Fracture"),
            new SurveyQuestionOption("Ischemic Heart Disease"),
            new SurveyQuestionOption("Depression"),
            new SurveyQuestionOption("Osteoporosis"),
            new SurveyQuestionOption("Rheumatoid Arthritis"),
            new SurveyQuestionOption("Dementia"),
            new SurveyQuestionOption("Stroke/Transient Ischemic Attack (TIA)"),
            new SurveyQuestionOption("Breast Cancer"),
            new SurveyQuestionOption("Colo-rectal Cancer"),
            new SurveyQuestionOption("Prostate Cancer"),
            new SurveyQuestionOption("Lung Cancer"),
            new SurveyQuestionOption("Endometrial/Uterine Cancer"),
            new SurveyQuestionOption("Head Injury with Loss of Consciousness/Concussion"),
            new SurveyQuestionOption("Any other kind of cancer OR tumor"),
            new SurveyQuestionOption("Urinary Tract infections"),
            new SurveyQuestionOption("Obstructive Sleep Apnea"),
            new SurveyQuestionOption("Schizophrenia or Bipolar Disorder"),
            new SurveyQuestionOption("Peripheral Vascular Disease"),
            new SurveyQuestionOption("High Blood Pressure/Hypertension"),
            new SurveyQuestionOption("Fainting/Syncope"),
            new SurveyQuestionOption("Alcoholism"),
            new SurveyQuestionOption("Multiple Sclerosis"),
            new SurveyQuestionOption("Impulse control disorder"),
            new SurveyQuestionOption("AIDS or HIV"),
            new SurveyQuestionOption("Liver Disease"),
            new SurveyQuestionOption("Leukemia or Lymphoma"),
            new SurveyQuestionOption("Ulcer Disease"),
            new SurveyQuestionOption("Connective Tissue Disease"),
            new SurveyQuestionOption("Coronary Artery Disease"),
            new SurveyQuestionOption("Anemia"),
            new SurveyQuestionOption("Asthma")
        ));
        healthHistory.setConstraints(c);
    };
    
    public static Survey create() {
        Survey survey = new Survey();
        survey.setName("Enrollment Survey");
        survey.setIdentifier("parkinson-enrollment");
        survey.getElements().add(age);
        survey.getElements().add(gender);
        survey.getElements().add(race);
        survey.getElements().add(countryOfResidence);
        survey.getElements().add(stateOfResidence);
        survey.getElements().add(education);
        survey.getElements().add(employment);
        survey.getElements().add(maritalStatus);
        survey.getElements().add(areCaretaker);
        survey.getElements().add(pastParticipation);
        survey.getElements().add(smartphone);
        survey.getElements().add(phoneUsage);
        survey.getElements().add(homeUsage);
        survey.getElements().add(medicalUsage);
        survey.getElements().add(medicalUsageYesterday);
        survey.getElements().add(videoUsage);
        // getQuestions().add(hasPD);
        survey.getElements().add(professionalDiagnosis);
        survey.getElements().add(onsetYear);
        survey.getElements().add(diagnosisYear);
        survey.getElements().add(medicationStartYear);
        survey.getElements().add(healthCareProvider);
        survey.getElements().add(deepBrainStimulation);
        survey.getElements().add(whenDBS);
        survey.getElements().add(surgery);
        survey.getElements().add(smoked);
        survey.getElements().add(yearsSmoking);
        survey.getElements().add(packsPerDay);
        survey.getElements().add(lastSmoked);
        survey.getElements().add(healthHistory);
        return survey;
    }

	public static Schedule getSchedule(GuidCreatedOnVersionHolder survey) {
        Schedule schedule = new Schedule();
        schedule.setLabel("Enrollment survey");
        ScriptUtils.setSurveyActivity(schedule, survey);
        schedule.setScheduleType(ScheduleType.ONCE);
        return schedule;
	}
    
}
