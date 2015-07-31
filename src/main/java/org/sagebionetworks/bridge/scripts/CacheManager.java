package org.sagebionetworks.bridge.scripts;

import org.sagebionetworks.bridge.sdk.AdminClient;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Environment;
import org.sagebionetworks.bridge.sdk.Session;

public class CacheManager {

    public static void main(String[] args) {
        Config config = ClientProvider.getConfig();
        config.set(Environment.STAGING);
        
        Session session = ClientProvider.signIn(config.getAdminCredentials());
        
        AdminClient client = session.getAdminClient();
        
        for (String key : client.getCacheItemKeys()) {
            if (key.endsWith(".Survey:view")) {
                client.deleteCacheKey(key);      
            }
        }
    }
    
}
