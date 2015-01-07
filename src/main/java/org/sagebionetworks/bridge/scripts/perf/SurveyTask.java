package org.sagebionetworks.bridge.scripts.perf;

import java.util.List;

import org.joda.time.DateTime;
import org.sagebionetworks.bridge.scripts.onboarding.ParkinsonEnrollmentSurvey;
import org.sagebionetworks.bridge.scripts.onboarding.ScheduleHolder;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.ResourceList;
import org.sagebionetworks.bridge.sdk.models.holders.GuidCreatedOnVersionHolder;
import org.sagebionetworks.bridge.sdk.models.holders.IdentifierHolder;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyAnswer;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestion;

import com.google.common.collect.Lists;

public class SurveyTask extends PerfTask {

    public SurveyTask(Session session) {
        super(session);
    }

    @Override
    public void command() {
        ResourceList<Schedule> schedules = client.getSchedules();
        if (schedules.getTotal() == 0) {
            createASurveySchedulePlan();
        }
        schedules = client.getSchedules();
        if (schedules.getTotal() == 0) {
            throw new RuntimeException("Um no, should have a schedule at this point");
        }
        GuidCreatedOnVersionHolder keys = schedules.get(0).getActivities().get(0).getSurvey();
        Survey survey = client.getSurvey(keys);
        
        // Create some answers to submit
        List<SurveyAnswer> list = Lists.newArrayList();
        list.add(createAnswer(survey.getQuestions().get(0)));
        list.add(createAnswer(survey.getQuestions().get(1)));
        list.add(createAnswer(survey.getQuestions().get(2)));
        list.add(createAnswer(survey.getQuestions().get(3)));
        list.add(createAnswer(survey.getQuestions().get(4)));
        list.add(createAnswer(survey.getQuestions().get(5)));
        
        IdentifierHolder holder = client.submitAnswersToSurvey(keys, list);
        System.out.println("The identifier: " + holder.getIdentifier());
    }
    
    private void createASurveySchedulePlan() {
        Survey survey = new ParkinsonEnrollmentSurvey();
        GuidCreatedOnVersionHolder keys = researcherClient.createSurvey(survey);
        survey.setGuidCreatedOnVersionHolder(keys);
        researcherClient.publishSurvey(keys);

        ScheduleHolder holder = (ScheduleHolder) survey;
        SchedulePlan sp = new SchedulePlan();
        sp.setSchedule(holder.getSchedule(survey));
        researcherClient.createSchedulePlan(sp);
    }

    private SurveyAnswer createAnswer(SurveyQuestion question) {
        SurveyAnswer answer = new SurveyAnswer();
        answer.setAnsweredOn(DateTime.now());
        
        String answerString = "";
        switch(question.getConstraints().getDataType()) {
        case DURATION:
            answerString = ""; break;
        case STRING:
            if (question.getConstraints().getEnumeration() != null && !question.getConstraints().getEnumeration().isEmpty()) {
                answerString = question.getConstraints().getEnumeration().get(0).getValue();
            } else {
                answerString = "This is a string";  
            }
            break;
        case INTEGER:
            answerString = "10"; break;
        case DECIMAL:
            answerString = ""; break;
        case BOOLEAN:
            answerString = "true"; break;
        case DATE:
            answerString = DateTime.now().toString().split("T")[0]; break;
        case TIME:
            answerString = DateTime.now().toString().split("T")[1]; break;
        case DATETIME:
            answerString = DateTime.now().toString(); break;
        }
        if (question.getConstraints().getAllowMultiple()) {
            answer.setAnswers(Lists.newArrayList(answerString));  
        } else {
            answer.setAnswer(answerString);    
        }
        answer.setClient("mobile");
        answer.setQuestionGuid(question.getGuid());
        return answer;
    }
    
}
