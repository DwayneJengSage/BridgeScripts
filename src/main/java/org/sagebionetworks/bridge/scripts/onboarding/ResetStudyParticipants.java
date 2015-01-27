package org.sagebionetworks.bridge.scripts.onboarding;

import org.sagebionetworks.bridge.sdk.AdminClient;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Session;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.account.AccountStatus;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeys;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.directory.Directory;

/**
 * The origin of this class was a desire to reset the 100 users allowed in one of the studies. 
 *
 */
public class ResetStudyParticipants {

    public static void main(String[] args) {
        Config config = ClientProvider.getConfig();
        
        Session session = ClientProvider.signIn(config.getAdminCredentials());
        AdminClient adminClient = session.getAdminClient();
        
        // Get all accounts in a specific stormpath directory
        ApiKey apiKey = ApiKeys.builder()
                .setId("")
                .setSecret("").build();
        Client client = Clients.builder().setApiKey(apiKey).build();
        
        // Iterate and when you find a YMedia account, delete it.
        Directory asthmaStaging = client.getResource("", Directory.class);
        AccountList accounts = asthmaStaging.getAccounts();
        for (Account account : accounts) {
            if (account.getStatus() == AccountStatus.UNVERIFIED) {
                System.out.println("Deleting: " + account.getEmail());
                adminClient.deleteUser(account.getEmail());
            } else {
                System.out.println("NOT deleting: " + account.getEmail());
            }
        }
    }
    
}
