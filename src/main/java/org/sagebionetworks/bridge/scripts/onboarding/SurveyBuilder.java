package org.sagebionetworks.bridge.scripts.onboarding;

import java.util.Arrays;
import java.util.List;

import org.sagebionetworks.bridge.sdk.models.surveys.DataType;
import org.sagebionetworks.bridge.sdk.models.surveys.DateConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.DateTimeConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.IntegerConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.MultiValueConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestion;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyRule;
import org.sagebionetworks.bridge.sdk.models.surveys.TimeConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.UiHint;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyRule.Operator;

import com.google.common.collect.Lists;

public class SurveyBuilder {

    private final Survey survey;
    
    public SurveyBuilder(Survey survey) {
        this.survey = survey;
    }
    
    public Survey build() {
        return survey;
    }
    
    private SurveyQuestion add(String identifier, String prompt) {
        SurveyQuestion q = new SurveyQuestion();
        q.setIdentifier(identifier);
        q.setPrompt(prompt);
        survey.getElements().add(q);
        return q;
    }
    protected SurveyBuilder addSlider(String identifier, String prompt) {
        addSlider(identifier, prompt, 10L);
        return this;
    }
    protected SurveyBuilder addSlider(String identifier, String prompt, double min, double max) {
        SurveyQuestion q = add(identifier, prompt);
        q.setUiHint(UiHint.SLIDER);
        IntegerConstraints c = new IntegerConstraints();
        c.setMinValue(min);
        c.setMaxValue(max);
        q.setConstraints(c);
        return this;
    }
    protected SurveyBuilder addSlider(String identifier, String prompt, double max) {
        SurveyQuestion q = add(identifier, prompt);
        q.setUiHint(UiHint.SLIDER);
        IntegerConstraints c = new IntegerConstraints();
        c.setMinValue(0d);
        c.setMaxValue(max);
        q.setConstraints(c);
        return this;
    }
    protected SurveyBuilder addDate(String identifier, String prompt) {
        SurveyQuestion q = add(identifier, prompt);
        q.setUiHint(UiHint.DATEPICKER);
        q.setConstraints(new DateConstraints());
        return this;
    }
    protected SurveyBuilder addPastDate(String identifier, String prompt) {
        SurveyQuestion q = add(identifier, prompt);
        q.setUiHint(UiHint.DATEPICKER);
        DateConstraints c = new DateConstraints();
        c.setAllowFuture(false);
        q.setConstraints(c);
        return this;
    }
    protected SurveyBuilder addDateTime(String identifier, String prompt) {
        SurveyQuestion q = add(identifier, prompt);
        q.setUiHint(UiHint.DATETIMEPICKER);
        q.setConstraints(new DateTimeConstraints());
        return this;
    }
    protected SurveyBuilder addTime(String identifier, String prompt) {
        SurveyQuestion q = add(identifier, prompt);
        q.setUiHint(UiHint.TIMEPICKER);
        q.setConstraints(new TimeConstraints());
        return this;
    }
    protected SurveyBuilder addMulti(String identifier, String prompt, boolean allowOther, SurveyQuestionOption... options) {
        SurveyQuestion q = add(identifier, prompt);
        q.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setAllowOther(allowOther);
        List<SurveyQuestionOption> list = Arrays.asList(options);
        c.setEnumeration(list);
        q.setConstraints(c);
        return this;
    }
    protected SurveyBuilder addMulti(String identifier, String prompt, boolean allowOther, String... options) {
        SurveyQuestionOption[] opts = new SurveyQuestionOption[options.length];
        for (int i=0; i < options.length; i++) {
            opts[i] = new SurveyQuestionOption(options[i]);
        }
        return addMulti(identifier, prompt, allowOther, opts);
    }
    protected SurveyBuilder addList(String identifier, String prompt, String... options) {
        SurveyQuestion q = add(identifier, prompt);
        q.setUiHint(UiHint.LIST);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        List<SurveyQuestionOption> list = Lists.newArrayList();
        for (int i=0; i < options.length; i++) {
            SurveyQuestionOption option = new SurveyQuestionOption(options[i]);
            list.add(option);
        }
        c.setAllowOther(false);
        c.setEnumeration(list);
        c.setAllowMultiple(true);
        q.setConstraints(c);
        return this;
    }
    protected SurveyBuilder addOneFromList(String identifier, String prompt, String... options) {
        SurveyQuestion q = add(identifier, prompt);
        q.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        List<SurveyQuestionOption> list = Lists.newArrayList();
        for (int i=0; i < options.length; i++) {
            SurveyQuestionOption option = new SurveyQuestionOption(options[i]);
            list.add(option);
        }
        c.setAllowOther(false);
        c.setEnumeration(list);
        c.setAllowMultiple(false);
        q.setConstraints(c);
        return this;
    }
    
    protected SurveyBuilder addYesNo(String identifier, String prompt) {
        SurveyQuestion q = add(identifier, prompt);
        q.setUiHint(UiHint.RADIOBUTTON);
        q.setConstraints(ScriptUtils.booleanish());
        return this;
    }
    
    protected SurveyBuilder onNoSkipTo(String noIdentifier) {
        SurveyQuestion question = (SurveyQuestion)survey.getElements().get(survey.getElements().size()-1);
        List<SurveyRule> rules = question.getConstraints().getRules();
        rules.add(new SurveyRule(Operator.ne, "true", noIdentifier));
        rules.add(new SurveyRule(Operator.de, "true", noIdentifier));
        return this;
    }
    
}
