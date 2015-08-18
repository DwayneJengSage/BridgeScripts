package org.sagebionetworks.bridge.scripts;

import static com.google.common.base.Preconditions.checkNotNull;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.joda.time.LocalDate;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Config.Props;
import org.sagebionetworks.bridge.sdk.DeveloperClient;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.exceptions.ConsentRequiredException;
import org.sagebionetworks.bridge.sdk.models.ResourceList;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.SimpleScheduleStrategy;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;
import org.sagebionetworks.bridge.sdk.models.surveys.SurveyQuestionOption;
import org.sagebionetworks.bridge.sdk.models.users.ConsentSignature;
import org.sagebionetworks.bridge.sdk.models.users.SharingScope;
import org.sagebionetworks.bridge.sdk.models.users.SignInCredentials;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.common.collect.Lists;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeys;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.impl.client.DefaultClientBuilder;

public class Scripts {

    public static Properties loadProperties() {
        final Properties properties = new Properties();
        try (InputStream ins = new FileInputStream(System.getProperty("user.dir") + "/keys.properties")) {
            properties.load(ins);    
        } catch(IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
    
    public static Client getStormpathClient(Properties properties) {
        ApiKey apiKey = ApiKeys.builder()
            .setId(properties.getProperty("stormpath.key"))
            .setSecret(properties.getProperty("stormpath.secret.key")).build();
        ClientBuilder clientBuilder = Clients.builder().setApiKey(apiKey);
        ((DefaultClientBuilder)clientBuilder).setBaseUrl("https://enterprise.stormpath.io/v1");
        return clientBuilder.build();
    }
    
    public static AmazonDynamoDBClient getDynamoDbClient(Properties properties) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
                properties.getProperty("aws.key"), 
                properties.getProperty("aws.secret.key"));
        return new AmazonDynamoDBClient(awsCredentials);
    }

    public static Session signIn(String url, String studyIdentifier) {
        return signIn(ClientProvider.getConfig().getAdminCredentials(), Environment.DEV, studyIdentifier);
    }
    
    public static Session signIn(SignInCredentials credentials, Environment env, String studyIdentifier) {
        Config config = ClientProvider.getConfig();
        config.set(env);
        config.set(Props.STUDY_IDENTIFIER, studyIdentifier);
        Session session = null;
        try {
            session = ClientProvider.signIn(credentials);
        } catch(ConsentRequiredException e) {
            session = e.getSession();
            session.getUserClient().consentToResearch(
                new ConsentSignature("[Test User]", LocalDate.parse("1970-12-30"), null, null),
                SharingScope.NO_SHARING);
        }
        return session;
    }

    public static List<SurveyQuestionOption> options(String... values) {
        List<SurveyQuestionOption> list = Lists.newArrayList();
        for (String value : values) {
            list.add(new SurveyQuestionOption(value));
        }
        return list;
    }
    
    public static List<Activity> getActivities(ResourceList<SchedulePlan> plans) {
        List<Activity> activities = Lists.newArrayList();
        for (SchedulePlan plan : plans) {
            Activity activity = ((SimpleScheduleStrategy)plan.getStrategy()).getSchedule().getActivities().get(0);
            activities.add(activity);
        }
        return activities;
    }
    
    public static Survey getMostRecentSurveyByIdentifier(DeveloperClient client, String identifier) {
        checkNotNull(client);
        checkNotNull(identifier);
        ResourceList<Survey> surveys = client.getAllSurveysMostRecent();
        for (Survey survey : surveys) {
            if (identifier.equals(survey.getIdentifier())) {
                // But return it with the elements
                return client.getSurvey(survey);
            }
        }
        return null;
    }
    
}
