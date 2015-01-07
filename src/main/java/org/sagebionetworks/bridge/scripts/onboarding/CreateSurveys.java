package org.sagebionetworks.bridge.scripts.onboarding;

import org.joda.time.LocalDate;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.UserClient;
import org.sagebionetworks.bridge.sdk.exceptions.ConsentRequiredException;
import org.sagebionetworks.bridge.sdk.models.ResourceList;
import org.sagebionetworks.bridge.sdk.models.holders.GuidCreatedOnVersionHolder;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.users.ConsentSignature;
import org.sagebionetworks.bridge.sdk.models.users.SignInCredentials;

public class CreateSurveys {
    public static void main(String[] args) throws Exception {
        Config config = ClientProvider.getConfig();
        config.set(Config.Props.HOST, "https://parkinson-develop.sagebridge.org");
        
        SignInCredentials signIn = new SignInCredentials("alxdark", "P4ssword");
        
        Session session = null;
        try {
            session = ClientProvider.signIn(signIn);
        } catch(ConsentRequiredException e) {
            session = e.getSession();
            ConsentSignature sig = new ConsentSignature("Alx Dark", LocalDate.parse("1970-12-12"), null, null);
            session.getUserClient().consentToResearch(sig);
            e.getSession().signOut();
            
            session = ClientProvider.signIn(signIn);
        }
        
        UserClient client = session.getUserClient();
        ResourceList<Schedule> schedules = client.getSchedules();
        
        for (Schedule schedule : schedules) {
            System.out.println(schedule);
        }
        
        session.signOut();
        //createSurveyAndPlan(client, new ParkinsonEnrollmentSurvey());
    }

    /*
    private static void updatePlan(ResearcherClient client, SchedulePlan plan, Survey survey) {
        SimpleScheduleStrategy strategy = (SimpleScheduleStrategy)plan.getStrategy();
        ScriptUtils.setMostRecentlyPublishedSurveyActivity(strategy.getSchedule(), survey.getGuid());

        client.updateSchedulePlan(plan);
    }*/
    
    /*
    private static void updateSurvey(ResearcherClient client, SchedulePlan plan, Survey survey, Survey update) {
        GuidCreatedOnVersionHolder keys = client.versionSurvey(survey);
        
        update.setGuidCreatedOnVersionHolder(keys);
        keys = client.updateSurvey(update);
        client.publishSurvey(keys);
        
        SimpleScheduleStrategy strategy = (SimpleScheduleStrategy)plan.getStrategy();
        ScriptUtils.setSurveyActivity(strategy.getSchedule(), keys);
        
        client.updateSchedulePlan(plan);
    }
    */

    private static void createSurveyAndPlan(ResearcherClient client, Survey survey) {
        GuidCreatedOnVersionHolder keys = client.createSurvey(survey);
        survey.setGuidCreatedOnVersionHolder(keys);
        client.publishSurvey(keys);

        ScheduleHolder holder = (ScheduleHolder) survey;
        SchedulePlan sp = new SchedulePlan();
        sp.setSchedule(holder.getSchedule(survey));
        client.createSchedulePlan(sp);
    }
}
