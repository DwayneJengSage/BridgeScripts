package org.sagebionetworks.bridge.scripts;

import java.util.List;

import org.sagebionetworks.bridge.sdk.models.surveys.DataType;
import org.sagebionetworks.bridge.sdk.models.surveys.DateConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.DateTimeConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.IntegerConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.MultiValueConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.StringConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestion;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyRule;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyRule.Operator;
import org.sagebionetworks.bridge.sdk.models.surveys.TimeConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.UiHint;

import com.google.common.collect.Lists;

public class SurveyBuilder {

    private final Survey survey;
    
    public SurveyBuilder(Survey survey) {
        this.survey = survey;
    }
    
    public Survey build() {
        return survey;
    }
    
    private SurveyQuestion add(String identifier, String prompt, String promptDetail) {
        SurveyQuestion q = new SurveyQuestion();
        q.setIdentifier(identifier);
        q.setPrompt(prompt);
        q.setPromptDetail(promptDetail);
        survey.getElements().add(q);
        return q;
    }
    public SurveyBuilder addMultilineText(String identifier, String prompt, String promptDetail) {
        SurveyQuestion q = add(identifier, prompt, promptDetail);
        q.setUiHint(UiHint.MULTILINETEXT);
        q.setConstraints(new StringConstraints());
        return this;
    }
    public SurveyBuilder addNumber(String identifier, String prompt, String promptDetail) {
        SurveyQuestion q = add(identifier, prompt, promptDetail);
        q.setUiHint(UiHint.NUMBERFIELD);
        IntegerConstraints c = new IntegerConstraints();
        q.setConstraints(c);
        return this;
    }
    public SurveyBuilder addNumber(String identifier, String prompt, String promptDetail, double min, double max) {
        SurveyQuestion q = add(identifier, prompt, promptDetail);
        q.setUiHint(UiHint.NUMBERFIELD);
        IntegerConstraints c = new IntegerConstraints();
        c.setMinValue(min);
        c.setMaxValue(max);
        q.setConstraints(c);
        return this;
    }
    public SurveyBuilder addSlider(String identifier, String prompt, String promptDetail, double min, double max) {
        SurveyQuestion q = add(identifier, prompt, promptDetail);
        q.setUiHint(UiHint.SLIDER);
        IntegerConstraints c = new IntegerConstraints();
        c.setMinValue(min);
        c.setMaxValue(max);
        q.setConstraints(c);
        return this;
    }
    public SurveyBuilder addDate(String identifier, String prompt, String promptDetail) {
        SurveyQuestion q = add(identifier, prompt, promptDetail);
        q.setPromptDetail(promptDetail);
        q.setUiHint(UiHint.DATEPICKER);
        q.setConstraints(new DateConstraints());
        return this;
    }
    public SurveyBuilder addPastDate(String identifier, String prompt, String promptDetail) {
        SurveyQuestion q = add(identifier, prompt, promptDetail);
        q.setUiHint(UiHint.DATEPICKER);
        DateConstraints c = new DateConstraints();
        c.setAllowFuture(false);
        q.setConstraints(c);
        return this;
    }
    public SurveyBuilder addDateTime(String identifier, String prompt, String promptDetail) {
        SurveyQuestion q = add(identifier, prompt, promptDetail);
        q.setUiHint(UiHint.DATETIMEPICKER);
        q.setConstraints(new DateTimeConstraints());
        return this;
    }
    public SurveyBuilder addTime(String identifier, String prompt, String promptDetail) {
        SurveyQuestion q = add(identifier, prompt, promptDetail);
        q.setUiHint(UiHint.TIMEPICKER);
        q.setConstraints(new TimeConstraints());
        return this;
    }
    public SurveyBuilder addRadio(String identifier, String prompt, String promptDetail, boolean allowOther, List<SurveyQuestionOption> options) {
        return addRadio(identifier, prompt, promptDetail, allowOther, options, Lists.<SurveyRule>newArrayList());
    }
    public SurveyBuilder addRadio(String identifier, String prompt, String promptDetail, boolean allowOther, List<SurveyQuestionOption> options, List<SurveyRule> rules) {
        SurveyQuestion q = add(identifier, prompt, promptDetail);
        q.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setAllowOther(allowOther);
        c.setAllowMultiple(false);
        c.setEnumeration(options);
        c.setRules(rules);
        q.setConstraints(c);
        return this;
    }
    public SurveyBuilder addList(String identifier, String prompt, String promptDetail, boolean allowOther, List<SurveyQuestionOption> options) {
        SurveyQuestion q = add(identifier, prompt, promptDetail);
        q.setUiHint(UiHint.LIST);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setAllowOther(allowOther);
        c.setEnumeration(options);
        c.setAllowMultiple(true);
        q.setConstraints(c);
        return this;
    }
    public SurveyBuilder onNoSkipTo(String noIdentifier) {
        SurveyQuestion question = (SurveyQuestion)survey.getElements().get(survey.getElements().size()-1);
        List<SurveyRule> rules = question.getConstraints().getRules();
        rules.add(new SurveyRule(Operator.NE, "true", noIdentifier));
        rules.add(new SurveyRule(Operator.DE, "true", noIdentifier));
        return this;
    }
}
