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
import org.sagebionetworks.bridge.sdk.models.surveys.TimeConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.UiHint;

import com.google.common.collect.Lists;

public class BaseSurvey {
    private static SurveyQuestion add(Survey survey, String identifier, String prompt) {
        SurveyQuestion q = new SurveyQuestion();
        q.setIdentifier(identifier);
        q.setPrompt(prompt);
        survey.getElements().add(q);
        return q;
    }
    protected static SurveyQuestion addSlider(Survey survey, String identifier, String prompt) {
        return addSlider(survey, identifier, prompt, 10L);
    }
    protected static SurveyQuestion addSlider(Survey survey, String identifier, String prompt, double max) {
        SurveyQuestion q = add(survey, identifier, prompt);
        q.setUiHint(UiHint.SLIDER);
        IntegerConstraints c = new IntegerConstraints();
        c.setMinValue(0d);
        c.setMaxValue(max);
        q.setConstraints(c);
        return q;
    }
    protected static SurveyQuestion addDate(Survey survey, String identifier, String prompt) {
        SurveyQuestion q = add(survey, identifier, prompt);
        q.setUiHint(UiHint.DATEPICKER);
        q.setConstraints(new DateConstraints());
        return q;
    }
    protected static SurveyQuestion addPastDate(Survey survey, String identifier, String prompt) {
        SurveyQuestion q = add(survey, identifier, prompt);
        q.setUiHint(UiHint.DATEPICKER);
        DateConstraints c = new DateConstraints();
        c.setAllowFuture(false);
        q.setConstraints(c);
        return q;
    }
    protected static SurveyQuestion addDateTime(Survey survey, String identifier, String prompt) {
        SurveyQuestion q = add(survey, identifier, prompt);
        q.setUiHint(UiHint.DATETIMEPICKER);
        q.setConstraints(new DateTimeConstraints());
        return q;
    }
    protected static SurveyQuestion addTime(Survey survey, String identifier, String prompt) {
        SurveyQuestion q = add(survey, identifier, prompt);
        q.setUiHint(UiHint.TIMEPICKER);
        q.setConstraints(new TimeConstraints());
        return q;
    }
    protected static SurveyQuestion addMulti(Survey survey, String identifier, String prompt, boolean allowOther, SurveyQuestionOption... options) {
        SurveyQuestion q = add(survey, identifier, prompt);
        q.setUiHint(UiHint.RADIOBUTTON);
        MultiValueConstraints c = new MultiValueConstraints(DataType.STRING);
        c.setAllowOther(allowOther);
        List<SurveyQuestionOption> list = Arrays.asList(options);
        c.setEnumeration(list);
        q.setConstraints(c);
        return q;
    }
    protected static SurveyQuestion addMulti(Survey survey, String identifier, String prompt, boolean allowOther, String... options) {
        SurveyQuestionOption[] opts = new SurveyQuestionOption[options.length];
        for (int i=0; i < options.length; i++) {
            opts[i] = new SurveyQuestionOption(options[i]);
        }
        return addMulti(survey, identifier, prompt, allowOther, opts);
    }
    protected static SurveyQuestion addList(Survey survey, String identifier, String prompt, String... options) {
        SurveyQuestion q = add(survey, identifier, prompt);
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
        return q;
    }
    protected static SurveyQuestion addYesNo(Survey survey, String identifier, String prompt) {
        SurveyQuestion q = add(survey, identifier, prompt);
        q.setUiHint(UiHint.RADIOBUTTON);
        q.setConstraints(ScriptUtils.booleanish());
        return q;
    }
}
