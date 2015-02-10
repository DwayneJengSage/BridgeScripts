package org.sagebionetworks.bridge.scripts.onboarding;

import org.joda.time.Period;
import org.sagebionetworks.bridge.sdk.models.holders.GuidCreatedOnVersionHolder;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.surveys.MultiValueConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestion;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyRule;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyRule.Operator;
import org.sagebionetworks.bridge.sdk.models.surveys.UiHint;

import com.google.common.collect.Lists;

public class ParkinsonMonthlySurvey {
    
    private static void addTableQuestion(Survey survey, String identifier, String prompt) {
        SurveyQuestion q = new SurveyQuestion();
        q.setIdentifier(identifier);
        q.setPrompt(prompt);
        q.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints c = new MultiValueConstraints();
        c.setEnumeration(Lists.newArrayList(
            new SurveyQuestionOption("Never"),
            new SurveyQuestionOption("Occassionally"),
            new SurveyQuestionOption("Sometimes"),
            new SurveyQuestionOption("Often"),
            new SurveyQuestionOption("Always")
        ));
        q.setConstraints(c);
        survey.getElements().add(q);
    }
    
    public static Survey create() {
        SurveyQuestion medicationsChange = new SurveyQuestion(); 
        medicationsChange.setIdentifier("medications-change");
        medicationsChange.setPrompt("Did your medications for Parkinson disease change in the previous month?");
        medicationsChange.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints c = ScriptUtils.booleanish();
        c.getRules().add(new SurveyRule(Operator.eq, "true", "medications"));
        c.getRules().add(new SurveyRule(Operator.ne, "true", "moving"));
        c.getRules().add(new SurveyRule(Operator.de, null, "moving"));
        medicationsChange.setConstraints(c);
    
        SurveyQuestion medications = new SurveyQuestion(); 
        medications.setIdentifier("medications");
        medications.setPrompt("What medication(s) are you currently using to manage Parkinson disease?");
        medications.setUiHint(UiHint.LIST);
        MultiValueConstraints mvc = new MultiValueConstraints();
        mvc.setAllowMultiple(true);
        mvc.setAllowOther(true);
        mvc.setEnumeration(Lists.newArrayList(
            new SurveyQuestionOption("Amantadine (Symmetrel)"),
            new SurveyQuestionOption("Apomorphine (Apokyn)"),
            new SurveyQuestionOption("Benztropine (Cogentin)"),
            new SurveyQuestionOption("Bromocriptine (Parlodel)"),
            new SurveyQuestionOption("Carbidopa, levodopa, and entacapone (Stalevo)"),
            new SurveyQuestionOption("Carbidopa-levodopa (Sinemet, Sinemet CR)"),
            new SurveyQuestionOption("Entacapone (Comtan)"),
            new SurveyQuestionOption("Levodopa-benserazide (Madopar)"),
            new SurveyQuestionOption("Melevodopa (Sirio)"),
            new SurveyQuestionOption("Pramipexole (Mirapex, Mirapex ER, Mirapexin, Sifrol)"),
            new SurveyQuestionOption("Rasagiline (Azilect)"),
            new SurveyQuestionOption("Ropinirole (Adartel, Requip, Requip XL, Ropark)"),
            new SurveyQuestionOption("Rotigotine (Neupro)"),
            new SurveyQuestionOption("Selegiline (l-deprenyl, Eldepryl, Zelapar)"),
            new SurveyQuestionOption("Tolacapone (Tasmar)"),
            new SurveyQuestionOption("Trihexyphenidyl (Apo-Trihex, Artane)"),
            new SurveyQuestionOption("None"),
            new SurveyQuestionOption("N/A")
        ));
        medications.setConstraints(mvc);
        
        Survey survey = new Survey();
        survey.setName("Parkinson Monthly Survey");
        survey.setIdentifier("parkinson-monthly");
        survey.getElements().add(medicationsChange);
        survey.getElements().add(medications);
        addTableQuestion(survey, "moving","During the last month have you had difficulty getting around in public?");
        addTableQuestion(survey, "dressing","During the last month have you had difficulty dressing yourself?");
        addTableQuestion(survey, "depressed","During the last month have you felt depressed?");
        addTableQuestion(survey, "personal-relationships","During the last month have you had problems with your close personal relationships?");
        addTableQuestion(survey, "concentration","During the last month have you had problems with your concentration, e.g. when reading or watching TV?");
        addTableQuestion(survey, "communication","During the last month have you felt unable to communicate with people properly?");
        addTableQuestion(survey, "spasms","During the last month have you had painful muscle cramps or spasms?");
        addTableQuestion(survey, "public-embarrassment","During the last month have you felt embarrassed in public due to having Parkinson's disease?");
        return survey;
    }
    
	public Schedule getSchedule(GuidCreatedOnVersionHolder survey) {
        Schedule schedule = new Schedule();
        schedule.setLabel("Monthly Survey");
        schedule.setCronTrigger("0 0 6 ? 1/1 THU#1 *");
        schedule.setExpires(Period.days(7));
        ScriptUtils.setSurveyActivity(schedule, survey);
        schedule.setScheduleType(ScheduleType.recurring);
        return schedule;
	}
	
}
