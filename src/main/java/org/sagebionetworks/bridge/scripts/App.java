package org.sagebionetworks.bridge.scripts;

import java.util.List;
import java.util.Properties;

import org.sagebionetworks.bridge.scripts.breastcancer.enrollment.SymptomsSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.enrollment.BackgroundSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.enrollment.ExerciseReadinessSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.monthly.AssessmentOfFunctioningSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.monthly.PersonalHealthSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.monthly.SleepQualitySurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.optional.FeedbackSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.optional.MyThoughtsSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.trimonthly.GeneralHealthSurvey;
import org.sagebionetworks.bridge.scripts.breastcancer.weekly.WeeklySurvey;
import org.sagebionetworks.bridge.scripts.parkinson.monthly.PDQuestionnaire;
import org.sagebionetworks.bridge.scripts.parkinson.weekly.PDRatingScaleSurvey;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.DeveloperClient;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.models.holders.GuidCreatedOnVersionHolder;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.schedules.TaskReference;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.users.SignInCredentials;

import com.google.common.collect.Lists;

public class App {

    public static void main(String[] args) throws Exception {
        new App().createShareTheJourneySurveys(Environment.STAGING);
        new App().createMPowerSurveys(Environment.STAGING);
    }
    
    @SuppressWarnings("unchecked")
    public void createShareTheJourneySurveys(Environment env) throws Exception {
        DeveloperClient client = signIn(env, "breastcancer");
        
        List<Class<? extends SurveyProvider>> providers = Lists.newArrayList(
            SymptomsSurvey.class, 
            BackgroundSurvey.class,
            ExerciseReadinessSurvey.class,
            AssessmentOfFunctioningSurvey.class,
            PersonalHealthSurvey.class, 
            SleepQualitySurvey.class,
            GeneralHealthSurvey.class,
            WeeklySurvey.class,
            FeedbackSurvey.class, 
            MyThoughtsSurvey.class
        );

        SchedulePlan plan1 = createPlan("Weekly Exersize Check-in", "1 Minute", "a-APHWeeklyExerciseSurvey-1259AC11-A719-17A9-ZDBA-FCFCECDED1D6", "P7D", "P7D");
        SchedulePlan plan2 = createPlan("My Journal", null, "6-APHDailyJournal-80F09109-265A-49C6-9C5D-765E49AAF5D9", "0 0 5,11,17 1/1 * ? *");
        
        execute(client, providers, plan1, plan2);
    }
    @SuppressWarnings("unchecked")
    public void createMPowerSurveys(Environment env) throws Exception {
        DeveloperClient client = signIn(env, "parkinson");
        
        List<Class<? extends SurveyProvider>> providers = Lists.newArrayList(
            org.sagebionetworks.bridge.scripts.parkinson.enrollment.BackgroundSurvey.class,
            PDQuestionnaire.class,
            PDRatingScaleSurvey.class,
            org.sagebionetworks.bridge.scripts.parkinson.optional.FeedbackSurvey.class,
            org.sagebionetworks.bridge.scripts.parkinson.optional.MyThoughtsSurvey.class
        );
        
        SchedulePlan plan1 = createPlan("Walking Activity", "1.5 Minutes", "4-APHTimedWalking-80F09109-265A-49C6-9C5D-765E49AAF5D9", "0 0 5,11,17 1/1 * ? *");
        SchedulePlan plan2 = createPlan("Voice Activity", "20 Seconds", "3-APHPhonation-C614A231-A7B7-4173-BDC8-098309354292", "0 0 5,11,17 1/1 * ? *");
        SchedulePlan plan3 = createPlan("Tapping Activity", "30 Seconds", "2-APHIntervalTapping-7259AC18-D711-47A6-ADBD-6CFCECDED1DF", "0 0 5,11,17 1/1 * ? *");
        SchedulePlan plan4 = createPlan("Memory Activity", "2 Minutes", "7-APHSpatialSpanMemory-4A04F3D0-AC05-11E4-AB27-0800200C9A66", "0 0 5 1/1 * ? *");

        execute(client, providers, plan1, plan2, plan3, plan4);
    }

    private DeveloperClient signIn(Environment env, String studyId) {
        Config config = ClientProvider.getConfig();
        config.set(env);
        
        Properties props = Scripts.loadProperties();
        SignInCredentials signIn = new SignInCredentials(
            studyId, props.getProperty("signin.username"), props.getProperty("signin.password"));
        
        Session session = ClientProvider.signIn(signIn);
        
        return session.getDeveloperClient();
    }
    
    private void execute(DeveloperClient client, List<Class<? extends SurveyProvider>> providers, SchedulePlan... plans)
                    throws InstantiationException, IllegalAccessException {
        for (Class<? extends SurveyProvider> providerClass : providers) {
            SurveyProvider provider = providerClass.newInstance();
            Survey survey = provider.createSurvey();
            GuidCreatedOnVersionHolder keys = client.createSurvey(survey);
            keys = client.publishSurvey(survey);
            SchedulePlan plan = provider.createSchedulePlan(survey.getIdentifier(), keys.getGuid());
            client.createSchedulePlan(plan);
        }
        for(SchedulePlan aPlan : plans) {
            client.createSchedulePlan(aPlan);
        }
    }
    
    private SchedulePlan createPlan(String label, String labelDetail, String taskId, String cronTrigger) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel(label + " Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel(label + " Schedule");
        schedule.setScheduleType(ScheduleType.RECURRING);
        schedule.setCronTrigger(cronTrigger);

        Activity activity = new Activity(label, labelDetail, new TaskReference(taskId));
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }
    
    private SchedulePlan createPlan(String label, String labelDetail, String taskId, String delay, String interval) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel(label + " Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel(label + " Schedule");
        schedule.setScheduleType(ScheduleType.RECURRING);
        //schedule.setDelay(delay);
        schedule.setInterval(interval);
        schedule.addTimes("06:00");

        Activity activity = new Activity(label, labelDetail, new TaskReference(taskId));
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }
}
