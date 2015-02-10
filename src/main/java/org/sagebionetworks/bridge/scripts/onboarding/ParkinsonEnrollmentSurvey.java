package org.sagebionetworks.bridge.scripts.onboarding;

import java.util.Arrays;
import java.util.List;

import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.surveys.Constraints;
import org.sagebionetworks.bridge.sdk.models.surveys.DataType;
import org.sagebionetworks.bridge.sdk.models.surveys.DateConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.IntegerConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.MultiValueConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyElement;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestion;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyRule;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyRule.Operator;
import org.sagebionetworks.bridge.sdk.models.surveys.UiHint;

import com.google.common.collect.Lists;

/**
 * There are 41 questions in this survey. Seriously.
 */
public class ParkinsonEnrollmentSurvey {

    public static Survey create() {
        Survey survey = new Survey();

        SurveyQuestion age = new SurveyQuestion();
        age.setIdentifier("age");
        age.setPrompt("How old are you?");
        age.setUiHint(UiHint.NUMBERFIELD);
        IntegerConstraints ic = new IntegerConstraints();
        ic.setMinValue(18d);
        age.setConstraints(ic);

        SurveyQuestion gender = new SurveyQuestion();
        gender.setIdentifier("gender");
        gender.setPrompt("What is your sex?");
        gender.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints mvc = new MultiValueConstraints(DataType.STRING);
        List<SurveyQuestionOption> list = Arrays.asList(new SurveyQuestionOption("Male"), new SurveyQuestionOption(
                "Female"), new SurveyQuestionOption("Prefer not to answer"));
        mvc.setEnumeration(list);
        gender.setConstraints(mvc);

        SurveyQuestion race = new SurveyQuestion();
        race.setIdentifier("race");
        race.setPrompt("Which race do you identify with?");
        race.setUiHint(UiHint.CHECKBOX);
        mvc = new MultiValueConstraints(DataType.STRING);
        mvc.setAllowMultiple(true);
        mvc.setAllowOther(true);
        mvc.setEnumeration(Lists.newArrayList(new SurveyQuestionOption("Black or African"), new SurveyQuestionOption(
                "Latino/Hispanic"), new SurveyQuestionOption("Native American"), new SurveyQuestionOption(
                "Pacific Islander"), new SurveyQuestionOption("Middle Eastern"), new SurveyQuestionOption("Carribean"),
                new SurveyQuestionOption("South Asian"), new SurveyQuestionOption("East Asian"),
                new SurveyQuestionOption("White or Caucasian"), new SurveyQuestionOption("Mixed")
        // and allow other. Hope they catch that...
                ));
        race.setConstraints(mvc);

        SurveyQuestion countryOfResidence = new SurveyQuestion();
        countryOfResidence.setIdentifier("residence");
        countryOfResidence.setPrompt("What is your country of residence?");
        countryOfResidence.setUiHint(UiHint.SELECT);
        mvc = new MultiValueConstraints(DataType.STRING);
        mvc.setEnumeration(new CountryOfResidenceList());
        mvc.setRules(Lists.newArrayList(new SurveyRule(Operator.eq, "USA", "state"), new SurveyRule(Operator.ne, "USA",
                "education"), new SurveyRule(Operator.de, null, "education")));
        countryOfResidence.setConstraints(mvc);

        SurveyQuestion stateOfResidence = new SurveyQuestion();
        stateOfResidence.setIdentifier("state");
        stateOfResidence.setPrompt("In which state do you reside?");
        stateOfResidence.setUiHint(UiHint.SELECT);
        mvc = new MultiValueConstraints(DataType.STRING);
        mvc.setEnumeration(new UnitedStatesList());
        stateOfResidence.setConstraints(mvc);

        SurveyQuestion education = new SurveyQuestion();
        education.setIdentifier("education");
        education.setPrompt("What is the highest level of education that you have completed?");
        education.setUiHint(UiHint.SELECT);
        mvc = new MultiValueConstraints(DataType.STRING);
        mvc.setEnumeration(Lists.newArrayList(new SurveyQuestionOption("Doctoral Degree"), new SurveyQuestionOption(
                "Master's Degree"), new SurveyQuestionOption("Some graduate school"), new SurveyQuestionOption(
                "4-year college degree"), new SurveyQuestionOption("2-year college degree"), new SurveyQuestionOption(
                "Some college"), new SurveyQuestionOption("High School Diploma/GED"), new SurveyQuestionOption(
                "Some high school")));
        education.setConstraints(mvc);

        SurveyQuestion employment = new SurveyQuestion();
        employment.setIdentifier("employment");
        employment.setPrompt("What is your current employment status?");
        employment.setUiHint(UiHint.SELECT);
        mvc = new MultiValueConstraints(DataType.STRING);
        mvc.setEnumeration(Lists.newArrayList(new SurveyQuestionOption("Employment for wages"),
                new SurveyQuestionOption("Self-employed"),
                new SurveyQuestionOption("Out of work and looking for work"), new SurveyQuestionOption(
                        "Out of work but not currently looking for work"), new SurveyQuestionOption("A homemaker"),
                new SurveyQuestionOption("A student"), new SurveyQuestionOption("Military"), new SurveyQuestionOption(
                        "Retired"), new SurveyQuestionOption("Unable to work")));
        employment.setConstraints(mvc);

        SurveyQuestion maritalStatus = new SurveyQuestion();
        maritalStatus.setIdentifier("maritalStatus");
        maritalStatus.setPrompt("What is your current marital status?");
        maritalStatus.setUiHint(UiHint.RADIOBUTTON);
        mvc = new MultiValueConstraints(DataType.STRING);
        mvc.setAllowOther(true);
        mvc.setEnumeration(Lists.newArrayList(new SurveyQuestionOption("Single, never married"),
                new SurveyQuestionOption("Married or domestic partnership"), new SurveyQuestionOption("Widowed"),
                new SurveyQuestionOption("Divorced"), new SurveyQuestionOption("Separated")
        // and other
                ));
        maritalStatus.setConstraints(mvc);

        SurveyQuestion areCaretaker = new SurveyQuestion();
        areCaretaker.setIdentifier("are-caretaker");
        areCaretaker.setPrompt("Are you a spouse, partner or carepartner of someone who has Parkinson disease?");
        areCaretaker.setUiHint(UiHint.RADIOBUTTON);
        areCaretaker.setConstraints(ScriptUtils.booleanish());

        SurveyQuestion pastParticipation = new SurveyQuestion();
        pastParticipation.setIdentifier("past-participation");
        pastParticipation
                .setPrompt("Have you ever participated in a research study or clinical trial on Parkinson disease before?");
        pastParticipation.setUiHint(UiHint.RADIOBUTTON);
        pastParticipation.setConstraints(ScriptUtils.booleanish());

        SurveyQuestion smartphone = new SurveyQuestion();
        smartphone.setIdentifier("smartphone");
        smartphone.setPrompt("How easy is it for you to use your smart phone?");
        smartphone.setUiHint(UiHint.RADIOBUTTON);
        mvc = new MultiValueConstraints(DataType.STRING);
        mvc.setEnumeration(Lists.newArrayList(new SurveyQuestionOption("Very easy"), new SurveyQuestionOption("Easy"),
                new SurveyQuestionOption("Neither easy nor difficult"), new SurveyQuestionOption("Difficult"),
                new SurveyQuestionOption("Very Difficult")));
        smartphone.setConstraints(mvc);

        SurveyQuestion phoneUsage = new SurveyQuestion();
        phoneUsage.setIdentifier("phone-usage");
        phoneUsage.setPrompt("Do you ever use your cell phone to look for health or medical information online?");
        phoneUsage.setUiHint(UiHint.RADIOBUTTON);
        mvc = new MultiValueConstraints(DataType.STRING);
        mvc.setEnumeration(Lists.newArrayList(new SurveyQuestionOption("Yes"), new SurveyQuestionOption("No"),
                new SurveyQuestionOption("Not sure")));
        phoneUsage.setConstraints(mvc);

        SurveyQuestion homeUsage = new SurveyQuestion();
        homeUsage.setIdentifier("home-usage");
        homeUsage.setPrompt("Do you use the internet or email at home?");
        homeUsage.setUiHint(UiHint.RADIOBUTTON);
        homeUsage.setConstraints(ScriptUtils.booleanish());

        SurveyQuestion medicalUsage = new SurveyQuestion();
        medicalUsage.setIdentifier("medical-usage");
        medicalUsage.setPrompt("Do you ever use the Internet to look for health or medical information online?");
        medicalUsage.setUiHint(UiHint.RADIOBUTTON);
        // Could be a boolean, but many of these have a third option, which is "don't know"
        mvc = new MultiValueConstraints(DataType.STRING);
        mvc.setEnumeration(Lists.newArrayList(new SurveyQuestionOption("Yes, I have done this", "yes"),
                new SurveyQuestionOption("No, I have never done this", "no")));
        mvc.getRules().add(new SurveyRule(Operator.eq, "yes", "medical-usage-yesterday"));
        mvc.getRules().add(new SurveyRule(Operator.de, null, "video-usage"));
        mvc.getRules().add(new SurveyRule(Operator.ne, "yes", "video-usage"));
        medicalUsage.setConstraints(mvc);

        SurveyQuestion medicalUsageYesterday = new SurveyQuestion();
        medicalUsageYesterday.setIdentifier("medical-usage-yesterday");
        medicalUsageYesterday.setPrompt("Did you happen to do this yesterday, or not?");
        medicalUsageYesterday.setUiHint(UiHint.RADIOBUTTON);
        mvc = new MultiValueConstraints(DataType.STRING);
        mvc.setEnumeration(Lists.newArrayList(new SurveyQuestionOption("Did this yesterday", "yes"),
                new SurveyQuestionOption("Did not do this yesterday", "no"), new SurveyQuestionOption("Don't know",
                        "dont-know")));
        medicalUsageYesterday.setConstraints(mvc);

        SurveyQuestion videoUsage = new SurveyQuestion();
        videoUsage.setIdentifier("video-usage");
        videoUsage.setPrompt("Do you ever use your smartphone to participate in a video call or video chat?");
        videoUsage.setUiHint(UiHint.RADIOBUTTON);
        videoUsage.setConstraints(ScriptUtils.booleanish());

        SurveyQuestion professionalDiagnosis = new SurveyQuestion();
        professionalDiagnosis.setIdentifier("professional-diagnosis");
        professionalDiagnosis.setPrompt("Have you been diagnosed by a medical professional with Parkinson disease?");
        professionalDiagnosis.setUiHint(UiHint.RADIOBUTTON);
        Constraints c = ScriptUtils.booleanish();
        c.getRules().add(new SurveyRule(Operator.eq, "true", "onset-year"));
        c.getRules().add(new SurveyRule(Operator.de, null, "deep-brain-stimulation"));
        c.getRules().add(new SurveyRule(Operator.ne, "true", "deep-brain-stimulation"));
        professionalDiagnosis.setConstraints(ScriptUtils.booleanish());

        SurveyQuestion onsetYear = new SurveyQuestion();
        onsetYear.setIdentifier("onset-year");
        onsetYear.setPrompt("In what year did your movement symptoms begin?");
        onsetYear.setUiHint(UiHint.NUMBERFIELD);
        onsetYear.setConstraints(new IntegerConstraints());

        SurveyQuestion diagnosisYear = new SurveyQuestion();
        diagnosisYear.setIdentifier("diagnosis-year");
        diagnosisYear.setPrompt("In what year were you diagnosed with Parkinson disease?");
        diagnosisYear.setUiHint(UiHint.NUMBERFIELD);
        diagnosisYear.setConstraints(new IntegerConstraints());

        SurveyQuestion medicationStartYear = new SurveyQuestion();
        medicationStartYear.setIdentifier("medication-start-year");
        medicationStartYear
                .setPrompt("In what year did you begin taking Parkinson disease medication? Leave blank if you have not started taking medication.");
        medicationStartYear.setUiHint(UiHint.NUMBERFIELD);
        medicationStartYear.setConstraints(new IntegerConstraints());

        SurveyQuestion healthCareProvider = new SurveyQuestion();
        healthCareProvider.setIdentifier("healthcare-provider");
        healthCareProvider.setPrompt("What kind of health care provider currently cares for your Parkinson disease?");
        healthCareProvider.setUiHint(UiHint.RADIOBUTTON);
        mvc = new MultiValueConstraints(DataType.STRING);
        mvc.setAllowOther(true);
        mvc.setEnumeration(Lists.newArrayList(
                new SurveyQuestionOption("Parkinson Disease/Movement Disorder Specialist"), new SurveyQuestionOption(
                        "General Neurologist (non-Parkinson Disease specialist)"), new SurveyQuestionOption(
                        "Primary Care Doctor"),
                new SurveyQuestionOption("Nurse Practitioner or Physician's Assistant"), new SurveyQuestionOption(
                        "Don't know")
        // or other
                ));
        healthCareProvider.setConstraints(mvc);

        SurveyQuestion deepBrainStimulation = new SurveyQuestion();
        deepBrainStimulation.setIdentifier("deep-brain-stimulation");
        deepBrainStimulation.setPrompt("Have you ever had Deep Brain Stimulation?");
        deepBrainStimulation.setUiHint(UiHint.RADIOBUTTON);
        mvc = ScriptUtils.booleanish();
        mvc.getRules().add(new SurveyRule(Operator.eq, "true", "when-dbs"));
        mvc.getRules().add(new SurveyRule(Operator.ne, "true", "surgery"));
        mvc.getRules().add(new SurveyRule(Operator.de, null, "surgery"));
        deepBrainStimulation.setConstraints(mvc);

        SurveyQuestion whenDBS = new SurveyQuestion();
        whenDBS.setIdentifier("when-dbs");
        whenDBS.setPrompt("When did you have DBS?");
        whenDBS.setUiHint(UiHint.DATEPICKER);
        DateConstraints dc = new DateConstraints();
        dc.setAllowFuture(false);
        whenDBS.setConstraints(dc);

        SurveyQuestion surgery = new SurveyQuestion();
        surgery.setIdentifier("surgery");
        surgery.setPrompt("Have you ever had any surgery for Parkinson disease, other than DBS?");
        surgery.setUiHint(UiHint.RADIOBUTTON);
        surgery.setConstraints(ScriptUtils.booleanish());

        SurveyQuestion smoked = new SurveyQuestion();
        smoked.setIdentifier("smoked");
        smoked.setPrompt("Have you ever smoked?");
        smoked.setUiHint(UiHint.RADIOBUTTON);
        mvc = ScriptUtils.booleanish();
        mvc.getRules().add(new SurveyRule(Operator.eq, "true", "years-smoking"));
        mvc.getRules().add(new SurveyRule(Operator.ne, "true", "health-history"));
        mvc.getRules().add(new SurveyRule(Operator.de, null, "health-history"));
        smoked.setConstraints(mvc);

        SurveyQuestion yearsSmoking = new SurveyQuestion();
        yearsSmoking.setIdentifier("years-smoking");
        yearsSmoking.setPrompt("How many years have you smoked?");
        yearsSmoking.setUiHint(UiHint.SELECT);
        ic = new IntegerConstraints();
        ic.setEnumeration(new NumberList(0, 70));
        yearsSmoking.setConstraints(ic);

        SurveyQuestion packsPerDay = new SurveyQuestion();
        packsPerDay.setIdentifier("packs-per-day");
        packsPerDay.setPrompt("On average, how many packs did you smoke each day?");
        packsPerDay.setUiHint(UiHint.SELECT);
        ic = new IntegerConstraints();
        ic.setEnumeration(new NumberList(1, 5));
        packsPerDay.setConstraints(ic);

        SurveyQuestion lastSmoked = new SurveyQuestion();
        lastSmoked.setIdentifier("last-smoked");
        lastSmoked.setPrompt("When is the last time you smoked (leave blank if you are still smoking)?");
        lastSmoked.setUiHint(UiHint.DATEPICKER);
        dc = new DateConstraints();
        dc.setAllowFuture(false);
        lastSmoked.setConstraints(dc);

        SurveyQuestion healthHistory = new SurveyQuestion();
        healthHistory.setIdentifier("health-history");
        healthHistory
                .setPrompt("Has a doctor ever told you that you have, or have you ever taken medication for any of the following conditions? Please check all that apply.");
        healthHistory.setUiHint(UiHint.LIST);
        mvc = new MultiValueConstraints(DataType.STRING);
        mvc.setAllowMultiple(true);
        mvc.setEnumeration(Lists.newArrayList(new SurveyQuestionOption("Acute Myocardial Infarction/Heart Attack"),
                new SurveyQuestionOption("Alzheimer Disease or Alzheimer dementia"), new SurveyQuestionOption(
                        "Atrial Fibrillation"), new SurveyQuestionOption("Anxiety"), new SurveyQuestionOption(
                        "Cataract"), new SurveyQuestionOption("Kidney Disease"), new SurveyQuestionOption(
                        "Chronic Obstructive Pulumonary Disease (COPD) or Asthma"), new SurveyQuestionOption(
                        "Heart Failure/Congestive Heart Failure"), new SurveyQuestionOption(
                        "Diabetes or Prediabetes or High Blood Sugar"), new SurveyQuestionOption("Glaucoma"),
                new SurveyQuestionOption("Hip/Pelvic Fracture"), new SurveyQuestionOption("Ischemic Heart Disease"),
                new SurveyQuestionOption("Depression"), new SurveyQuestionOption("Osteoporosis"),
                new SurveyQuestionOption("Rheumatoid Arthritis"), new SurveyQuestionOption("Dementia"),
                new SurveyQuestionOption("Stroke/Transient Ischemic Attack (TIA)"), new SurveyQuestionOption(
                        "Breast Cancer"), new SurveyQuestionOption("Colo-rectal Cancer"), new SurveyQuestionOption(
                        "Prostate Cancer"), new SurveyQuestionOption("Lung Cancer"), new SurveyQuestionOption(
                        "Endometrial/Uterine Cancer"), new SurveyQuestionOption(
                        "Head Injury with Loss of Consciousness/Concussion"), new SurveyQuestionOption(
                        "Any other kind of cancer OR tumor"), new SurveyQuestionOption("Urinary Tract infections"),
                new SurveyQuestionOption("Obstructive Sleep Apnea"), new SurveyQuestionOption(
                        "Schizophrenia or Bipolar Disorder"), new SurveyQuestionOption("Peripheral Vascular Disease"),
                new SurveyQuestionOption("High Blood Pressure/Hypertension"), new SurveyQuestionOption(
                        "Fainting/Syncope"), new SurveyQuestionOption("Alcoholism"), new SurveyQuestionOption(
                        "Multiple Sclerosis"), new SurveyQuestionOption("Impulse control disorder"),
                new SurveyQuestionOption("AIDS or HIV"), new SurveyQuestionOption("Liver Disease"),
                new SurveyQuestionOption("Leukemia or Lymphoma"), new SurveyQuestionOption("Ulcer Disease"),
                new SurveyQuestionOption("Connective Tissue Disease"), new SurveyQuestionOption(
                        "Coronary Artery Disease"), new SurveyQuestionOption("Anemia"), new SurveyQuestionOption(
                        "Asthma")));
        healthHistory.setConstraints(mvc);

        survey.setName("Enrollment Survey");
        survey.setIdentifier("parkinson-enrollment");
        List<SurveyElement> elements = survey.getElements();
        elements.add(age);
        elements.add(gender);
        elements.add(race);
        elements.add(countryOfResidence);
        elements.add(stateOfResidence);
        elements.add(education);
        elements.add(employment);
        elements.add(maritalStatus);
        elements.add(areCaretaker);
        elements.add(pastParticipation);
        elements.add(smartphone);
        elements.add(phoneUsage);
        elements.add(homeUsage);
        elements.add(medicalUsage);
        elements.add(medicalUsageYesterday);
        elements.add(videoUsage);
        elements.add(professionalDiagnosis);
        elements.add(onsetYear);
        elements.add(diagnosisYear);
        elements.add(medicationStartYear);
        elements.add(healthCareProvider);
        elements.add(deepBrainStimulation);
        elements.add(whenDBS);
        elements.add(surgery);
        elements.add(smoked);
        elements.add(yearsSmoking);
        elements.add(packsPerDay);
        elements.add(lastSmoked);
        elements.add(healthHistory);
        return survey;
    }

    public static Schedule getSchedule(Survey survey) {
        Schedule schedule = new Schedule();
        schedule.setLabel("Enrollment survey");
        ScriptUtils.setSurveyActivity(schedule, survey);
        schedule.setScheduleType(ScheduleType.once);
        return schedule;
    }

}
