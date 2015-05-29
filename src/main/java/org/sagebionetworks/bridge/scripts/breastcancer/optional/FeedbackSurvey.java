package org.sagebionetworks.bridge.scripts.breastcancer.optional;

import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.schedules.SurveyReference;
import org.sagebionetworks.bridge.sdk.models.surveys.StringConstraints;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestion;
import org.sagebionetworks.bridge.sdk.models.surveys.UiHint;

public class FeedbackSurvey {
    public static Survey create() {
        Survey survey = new Survey();
        survey.setName("Study Feedback");
        survey.setIdentifier("study_feedback");
        
        SurveyQuestion q = new SurveyQuestion();
        q.setUiHint(UiHint.MULTILINETEXT);
        q.setIdentifier("feedback");
        q.setPrompt("In what ways would you improve or change the Share the Journey study?");
        q.setPromptDetail("We depend on you as our partners in this research study.  Please provide us anonymous feedback on ways we can enhance the study and reflect the interests of the breast cancer survivor community in improvements to come in June, 2015.");
        q.setConstraints(new StringConstraints());
        survey.getElements().add(q);
        
        return survey;
    }
    
    public static SchedulePlan createSchedulePlan(String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel("Study Feedback Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel("Study Feedback Schedule");
        schedule.setScheduleType(ScheduleType.ONCE);
        schedule.setEventId("survey:"+surveyGuid+":finished,enrollment");

        Config config = ClientProvider.getConfig();
        String url = config.getRecentlyPublishedSurveyUserApi(surveyGuid);
        SurveyReference reference = new SurveyReference(url);
        Activity activity = new Activity("Study Feedback: (Anytime) 1 Question", reference);
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }
}
