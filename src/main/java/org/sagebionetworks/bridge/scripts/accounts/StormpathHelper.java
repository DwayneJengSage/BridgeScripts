package org.sagebionetworks.bridge.scripts.accounts;

import java.util.Properties;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.account.Accounts;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.directory.Directory;

import org.sagebionetworks.bridge.crypto.Encryptor;

/** Helper class to get account info from Stormpath. */
// TODO: Consolidate this into shared package
public class StormpathHelper {
    private Encryptor healthCodeEncryptor;
    private Properties properties;
    private Client stormpathClient;

    /** AES GCM encryptor, used to decrypt the health ID from Stormpath custom data. */
    public final void setHealthCodeEncryptor(Encryptor healthCodeEncryptor) {
        this.healthCodeEncryptor = healthCodeEncryptor;
    }

    /** Config properties, used to get the Stormpath directory. */
    public final void setProperties(Properties properties) {
        this.properties = properties;
    }

    /** Stormpath client. */
    public final void setStormpathClient(Client stormpathClient) {
        this.stormpathClient = stormpathClient;
    }

    /**
     * Calls Stormpath to get the health ID for a given email address.
     *
     * @param studyId
     *         ID of the study this account lives in
     * @param email
     *         email address to look up
     * @return corresponding health ID
     */
    public String getHealthIdForEmail(String studyId, String email) {
        // get account from Stormpath
        Directory directory = stormpathClient.getResource(properties.getProperty(studyId + ".href"), Directory.class);
        AccountList accountList = directory.getAccounts(Accounts.where(Accounts.email().eqIgnoreCase(email))
                .withCustomData());
        Account account = accountList.single();

        // Decrypt health ID. It's called "code" in Stormpath, but it's actually the Health ID.
        String encryptedHealthId = (String) account.getCustomData().get(studyId + "_code");
        String healthId = healthCodeEncryptor.decrypt(encryptedHealthId);
        return healthId;
    }
}
