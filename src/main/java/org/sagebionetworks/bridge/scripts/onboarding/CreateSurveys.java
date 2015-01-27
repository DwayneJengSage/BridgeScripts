package org.sagebionetworks.bridge.scripts.onboarding;

import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.ResearcherClient;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.ResourceList;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;

public class CreateSurveys {
    
    public static void main(String[] args) throws Exception {
        /*
        Config config = ClientProvider.getConfig();
        config.set(Config.Props.HOST, "https://parkinson-develop.sagebridge.org");
        Session session = null;
        try {
            session = ClientProvider.signIn(config.getAccountCredentials());    
        } catch(ConsentRequiredException e) {
            session = e.getSession();
            session.getUserClient().consentToResearch(new ConsentSignature("Alx Dark", LocalDate.parse("1968-12-05"), null, null));
        }
        ResourceList<Schedule> schedules = session.getUserClient().getSchedules();
        for (Schedule schedule : schedules) {
            System.out.println(schedule);
        }
        */
        
        Config config = ClientProvider.getConfig();
        config.set(Config.Props.HOST, "https://parkinson-develop.sagebridge.org");
        Session session = ClientProvider.signIn(config.getAdminCredentials());
        ResearcherClient client = session.getResearcherClient();
        
        ResourceList<SchedulePlan> plans = client.getSchedulePlans();
        System.out.println(plans);
        /*
        Survey survey = client.getSurveyMostRecentlyPublishedVersionByIdentifier("parkinson-enrollment");
        SchedulePlan plan = getRelatedPlan(client, survey, "Enrollment Survey");
        updateSurvey(client, plan, survey, new ParkinsonEnrollmentSurvey(), "Enrollment Survey");
        
        survey = client.getSurveyMostRecentlyPublishedVersionByIdentifier("parkinson-weekly");
        plan = getRelatedPlan(client, survey, "Weekly Survey");
        updateSurvey(client, plan, survey, new ParkinsonWeeklySurvey(), "Weekly Survey");
        */
    }
    
    /*
    private static SchedulePlan getRelatedPlan(ResearcherClient client, Survey survey, String label) {
        ResourceList<SchedulePlan> plans = client.getSchedulePlans();
        for (SchedulePlan plan : plans) {
            SimpleScheduleStrategy strategy = (SimpleScheduleStrategy)plan.getStrategy();
            
            if(label.equals(strategy.getSchedule().getLabel())) {
                return plan;
            }
        }
        return null;
    }

    private static void updatePlan(ResearcherClient client, SchedulePlan plan, Survey survey) {
        SimpleScheduleStrategy strategy = (SimpleScheduleStrategy)plan.getStrategy();
        ScriptUtils.setMostRecentlyPublishedSurveyActivity(strategy.getSchedule(), survey.getGuid());

        client.updateSchedulePlan(plan);
    }
    
    private static void updateSurvey(ResearcherClient client, SchedulePlan plan, Survey survey, Survey update, String scheduleLabel) {
        Preconditions.checkNotNull(client);
        Preconditions.checkNotNull(plan);
        Preconditions.checkNotNull(survey);
        Preconditions.checkNotNull(update);
        Preconditions.checkNotNull(scheduleLabel);
        
        GuidCreatedOnVersionHolder keys = client.versionSurvey(survey);
        
        update.setGuidCreatedOnVersionHolder(keys);
        keys = client.updateSurvey(update);
        client.publishSurvey(keys);
        
        SimpleScheduleStrategy strategy = (SimpleScheduleStrategy)plan.getStrategy();
        
        strategy.getSchedule().getActivities().clear();
        strategy.getSchedule().addActivity(new Activity(scheduleLabel, keys));
        
        client.updateSchedulePlan(plan);
    }

    private static void createSurveyAndPlan(ResearcherClient client, Survey survey) {
        GuidCreatedOnVersionHolder keys = client.createSurvey(survey);
        survey.setGuidCreatedOnVersionHolder(keys);
        client.publishSurvey(keys);

        ScheduleHolder holder = (ScheduleHolder) survey;
        SchedulePlan sp = new SchedulePlan();
        sp.setSchedule(holder.getSchedule(survey));
        client.createSchedulePlan(sp);
    }*/
}
