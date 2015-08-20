package org.sagebionetworks.bridge.scripts.onboarding;

import org.joda.time.LocalDate;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Config.Props;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.exceptions.ConsentRequiredException;
import org.sagebionetworks.bridge.sdk.models.users.ConsentSignature;
import org.sagebionetworks.bridge.sdk.models.users.SignInCredentials;

public class BaseSignIn {

    protected static Session signIn(Environment env, String studyIdentifier) {
        return signIn(ClientProvider.getConfig().getAdminCredentials(), env, studyIdentifier);
    }
    
    protected static Session signIn(SignInCredentials credentials, Environment env, String studyIdentifier) {
        Config config = ClientProvider.getConfig();
        config.set(env);
        config.set(Props.STUDY_IDENTIFIER, studyIdentifier);
        Session session = null;
        try {
            session = ClientProvider.signIn(credentials);
        } catch(ConsentRequiredException e) {
            session = e.getSession();
            session.getUserClient().consentToResearch(new ConsentSignature("[Test User]", LocalDate.parse("1970-12-30"), null, null));
        }
        return session;
    }
    
}
