package org.sagebionetworks.bridge.scripts.perf;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.joda.time.LocalDate;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.exceptions.ConsentRequiredException;
import org.sagebionetworks.bridge.sdk.models.users.ConsentSignature;
import org.sagebionetworks.bridge.sdk.models.users.SignInCredentials;

import com.google.common.collect.Lists;

public class PerfRunner {

    public static void main(String[] args) throws Exception {
        int TASK_COUNT = 10;
        int NUM_PROCESSORS = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(NUM_PROCESSORS);
        
        System.out.println("------ RECORDING ------");
        Session session = signIn();
        List<PerfTask> tasks = Lists.newArrayListWithCapacity(TASK_COUNT*2);
        //for (int i=0; i < TASK_COUNT; i++) {
            tasks.add(new SurveyTask(session));
            tasks.add(new HealthDataTask(session));
            tasks.add(new UploadTask(session));
        //}
        executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
        executor.invokeAll(tasks);
        System.out.println("------ STOP RECORDING ------");
        executor.shutdown();
    }

    public static Session signIn() {
        Session session = null;

        Config config = ClientProvider.getConfig();
        config.set(Config.Props.HOST, "http://localhost:9000");

        SignInCredentials credentials = config.getAdminCredentials();
        try {
            return ClientProvider.signIn(credentials);
        } catch(ConsentRequiredException e) {
            session = e.getSession();
            LocalDate birthdate = LocalDate.parse("1968-05-12");
            session.getUserClient().consentToResearch(new ConsentSignature("Alx Dark", birthdate, null, null));
        }
        return session;
    }
    
}
