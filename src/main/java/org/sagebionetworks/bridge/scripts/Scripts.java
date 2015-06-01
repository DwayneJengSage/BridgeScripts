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
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.Config.Props;
import org.sagebionetworks.bridge.sdk.exceptions.ConsentRequiredException;
import org.sagebionetworks.bridge.sdk.models.holders.GuidCreatedOnVersionHolder;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SurveyReference;
import org.sagebionetworks.bridge.sdk.models.surveys.DataType;
import org.sagebionetworks.bridge.sdk.models.surveys.MultiValueConstraints;
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

    /**
     * If you want to have a question that displays the choices "Yes" and "No", 
     * then it's not a boolean constraint because you have to specify the labels.
     * The return type can be the string "true"/"false" and therefore, a boolean.
     * @return
     */
    public static MultiValueConstraints booleanish() {
        MultiValueConstraints mvc = new MultiValueConstraints(DataType.BOOLEAN);
        mvc.setEnumeration(Lists.newArrayList(
            new SurveyQuestionOption("Yes", "true"),
            new SurveyQuestionOption("No", "false")
        ));
        return mvc;
    }
    public static void setMostRecentlyPublishedSurveyActivity(Schedule schedule, String guid) {
        checkNotNull(schedule);
        checkNotNull(guid);
        
        Config config = ClientProvider.getConfig();
        String url = config.getEnvironment().getUrl() + config.getRecentlyPublishedSurveyUserApi(guid);
        schedule.addActivity(new Activity("Take survey", url));
    }
    
    public static void setSurveyActivity(Schedule schedule, GuidCreatedOnVersionHolder keys) {
        checkNotNull(schedule);
        checkNotNull(keys);        
        
        schedule.addActivity(new Activity("Take survey", keys));
    }
    public static void setTaskActivity(Schedule schedule, String taskIdentifier) {
        checkNotNull(taskIdentifier);
        schedule.addActivity(new Activity("Do task", taskIdentifier));
    }
    public static SurveyReference getSurveyActivityKeys(Schedule schedule) {
        return schedule.getActivities().get(0).getSurvey();
    }
    public static List<SurveyQuestionOption> options(String... values) {
        List<SurveyQuestionOption> list = Lists.newArrayList();
        for (String value : values) {
            list.add(new SurveyQuestionOption(value));
        }
        return list;
    }
}
