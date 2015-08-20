package org.sagebionetworks.bridge.scripts.onboarding;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import org.joda.time.Period;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.holders.GuidCreatedOnVersionHolder;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.schedules.SurveyReference;
import org.sagebionetworks.bridge.sdk.models.surveys.BooleanConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.Constraints;
import org.sagebionetworks.bridge.sdk.models.surveys.DataType;
import org.sagebionetworks.bridge.sdk.models.surveys.DateConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.DateTimeConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.DecimalConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.DurationConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.Image;
import org.sagebionetworks.bridge.sdk.models.surveys.IntegerConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.MultiValueConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.StringConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyInfoScreen;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestion;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;
import org.sagebionetworks.bridge.sdk.models.surveys.TimeConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.UiHint;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CreateSurvey extends BaseSignIn {

    private static Map<DataType,List<UiHint>> oneMap = Maps.newHashMap();
    static {
        oneMap.put(DataType.DURATION, Lists.newArrayList(UiHint.NUMBERFIELD, UiHint.SLIDER));
        oneMap.put(DataType.STRING, Lists.newArrayList(UiHint.MULTILINETEXT, UiHint.TEXTFIELD));
        oneMap.put(DataType.INTEGER, Lists.newArrayList(UiHint.NUMBERFIELD, UiHint.SLIDER));
        oneMap.put(DataType.DECIMAL, Lists.newArrayList(UiHint.NUMBERFIELD, UiHint.SLIDER));
        oneMap.put(DataType.BOOLEAN, Lists.newArrayList(UiHint.CHECKBOX, UiHint.TOGGLE));
        oneMap.put(DataType.DATE, Lists.newArrayList(UiHint.DATEPICKER));
        oneMap.put(DataType.TIME, Lists.newArrayList(UiHint.TIMEPICKER));
        oneMap.put(DataType.DATETIME, Lists.newArrayList(UiHint.DATETIMEPICKER));
    }
    
    private static Map<DataType,Constraints> constraintsMap = Maps.newHashMap();
    static {
        constraintsMap.put(DataType.DURATION, new DurationConstraints());
        constraintsMap.put(DataType.STRING, new StringConstraints());
        constraintsMap.put(DataType.INTEGER, new IntegerConstraints());
        constraintsMap.put(DataType.DECIMAL, new DecimalConstraints());
        constraintsMap.put(DataType.BOOLEAN, new BooleanConstraints());
        constraintsMap.put(DataType.DATE, new DateConstraints());
        constraintsMap.put(DataType.TIME, new TimeConstraints());
        constraintsMap.put(DataType.DATETIME, new DateTimeConstraints());
    }
    
    private static List<UiHint> multiList = Lists.newArrayList(UiHint.CHECKBOX, UiHint.COMBOBOX, UiHint.LIST, UiHint.RADIOBUTTON, UiHint.SELECT, UiHint.SLIDER);
    
    private static Map<DataType,List<String>> valuesMap = Maps.newHashMap();
    static {
        valuesMap.put(DataType.DURATION, Lists.newArrayList("1","2","3"));
        valuesMap.put(DataType.STRING, Lists.newArrayList("value1","value2","value3"));
        valuesMap.put(DataType.INTEGER, Lists.newArrayList("1","2","3"));
        valuesMap.put(DataType.DECIMAL, Lists.newArrayList("1.0","2.0","3.0"));
        valuesMap.put(DataType.BOOLEAN, Lists.newArrayList("true","false"));
        valuesMap.put(DataType.DATE, Lists.newArrayList("2015-02-10", "2015-02-09", "2015-02-08"));
        valuesMap.put(DataType.TIME, Lists.newArrayList("17:03:21.902Z", "17:03:21.902Z", "17:03:21.902Z"));
        valuesMap.put(DataType.DATETIME, Lists.newArrayList("2015-02-10T17:03:21.902Z", "2015-02-09T17:03:21.902Z", "2015-02-08T17:03:21.902Z"));
    }
    
    private static EnumSet<UiHint> dontAllowMultiples = EnumSet.of(UiHint.SLIDER, UiHint.SELECT, UiHint.RADIOBUTTON, UiHint.COMBOBOX);
    
    public static void main(String[] args) throws Exception {
        Session session = signIn(Environment.LOCAL, "parkinson");
        ResearcherClient client = session.getResearcherClient();

        Survey survey = new Survey();
        survey.setIdentifier("type-test");
        survey.setName("Survey types/UI hints test");
        
        SurveyInfoScreen screen = new SurveyInfoScreen();
        screen.setIdentifier("explanation");
        screen.setTitle("Survey types/UI hints test");
        screen.setPrompt("This survey includes every combination of data type/UI Hint supported by the Bridge server.");
        screen.setPromptDetail("(And this example of an instructional screen.)");
        screen.setImage(new Image("https://s3.amazonaws.com/org-sagebridge-ui-test/test-pattern.jpg", 583, 437));
        survey.getElements().add(screen);
        
        for (DataType type : DataType.values()) {
            List<UiHint> hints = oneMap.get(type);
            for (UiHint hint : hints) {
                SurveyQuestion question = makeQuestion(type, hint);
                survey.getElements().add(question);
            }
            for (UiHint hint : multiList) {
                SurveyQuestion question = makeMultiline(type, hint);
                survey.getElements().add(question);
            }
        }
        GuidCreatedOnVersionHolder keys = client.createSurvey(survey);
        survey.setGuidCreatedOnVersionHolder(keys);
        keys = client.publishSurvey(survey);

        //SurveyReference ref = new SurveyReference(keys.getGuid()); 
        Schedule schedule = new Schedule();
        schedule.setLabel("Schedule for Test Survey");
        schedule.setCronTrigger("0 0 7 1/1 * ? *");
        schedule.setExpires(Period.parse("PT12H"));
        schedule.setScheduleType(ScheduleType.recurring);
        // schedule.getActivities().add(new Activity("Test Survey", ref));
        
        SchedulePlan plan = new SchedulePlan();
        plan.setSchedule(schedule);
        client.createSchedulePlan(plan);
    }
    
    private static SurveyQuestion makeQuestion(DataType type, UiHint hint) {
        SurveyQuestion question = new SurveyQuestion();
        question.setIdentifier(String.format("%s-%s", type.name().toLowerCase(), hint.name().toLowerCase()));
        question.setConstraints(constraintsMap.get(type));
        question.setUiHint(hint);
        question.setPrompt(String.format("This is a %s question, allowing one answer, with the UI hint of '%s'.", type.name().toLowerCase(), hint.name().toLowerCase()));
        return question;
    }
    
    private static SurveyQuestion makeMultiline(DataType type, UiHint hint) {
        SurveyQuestion question = new SurveyQuestion();
        question.setIdentifier(String.format("multi-%s-%s", type.name().toLowerCase(), hint.name().toLowerCase()));
        MultiValueConstraints mvc = new MultiValueConstraints();
        mvc.setAllowMultiple(!dontAllowMultiples.contains(hint));
        mvc.setAllowOther(true);
        List<SurveyQuestionOption> options = Lists.newArrayList();
        for (String value : valuesMap.get(type)) {
            SurveyQuestionOption option = new SurveyQuestionOption("Label " + value, value);
            options.add(option);
        }
        mvc.setEnumeration(options);
        question.setConstraints(mvc);
        question.setUiHint(hint);
        question.setPrompt(String.format("This is a %s question allowing multiple answers (along with an 'other' response), with the UI hint of '%s'.", type.name().toLowerCase(), hint.name().toLowerCase()));
        return question;
    }
    
}
