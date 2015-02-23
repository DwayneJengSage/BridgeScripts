package org.sagebionetworks.bridge.scripts.onboarding;

import org.sagebionetworks.bridge.sdk.AdminClient;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.Config.Props;
import org.sagebionetworks.bridge.sdk.Session;

/**
 * The origin of this class was a desire to reset the 100 users allowed in one of the studies. 
 *
 */
public class ResetStudyParticipants {
    public static void main(String[] args) {
        Config config = ClientProvider.getConfig();
        config.set(Environment.STAGING);
        config.set(Props.STUDY_IDENTIFIER, "asthma");
        
        /*
        Session session = ClientProvider.signIn(config.getAdminCredentials());
        AdminClient adminClient = session.getAdminClient();
        */
    }
    
}
