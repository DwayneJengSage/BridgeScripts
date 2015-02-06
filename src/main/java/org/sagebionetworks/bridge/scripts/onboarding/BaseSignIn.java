package org.sagebionetworks.bridge.scripts.onboarding;

import org.joda.time.LocalDate;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Config.Props;
import org.sagebionetworks.bridge.sdk.Session;
import org.sagebionetworks.bridge.sdk.exceptions.ConsentRequiredException;
import org.sagebionetworks.bridge.sdk.models.users.ConsentSignature;
import org.sagebionetworks.bridge.sdk.models.users.SignInCredentials;

public class BaseSignIn {

    protected static Session signIn(String url) {
        return signIn(ClientProvider.getConfig().getAdminCredentials(), url);
    }
    
    protected static Session signIn(SignInCredentials credentials, String url) {
        Config config = ClientProvider.getConfig();
        config.set(Props.HOST, url);
        
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
