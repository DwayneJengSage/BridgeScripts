package org.sagebionetworks.bridge.scripts.onboarding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.sagebionetworks.bridge.sdk.AdminClient;
import org.sagebionetworks.bridge.sdk.ClientProvider;
import org.sagebionetworks.bridge.sdk.Config;
import org.sagebionetworks.bridge.sdk.Config.Props;
import org.sagebionetworks.bridge.sdk.Session;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountCriteria;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.account.AccountStatus;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeys;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.directory.Directory;
import com.stormpath.sdk.impl.account.DefaultAccountCriteria;

/**
 * The origin of this class was a desire to reset the 100 users allowed in one of the studies. 
 *
 */
public class ResetStudyParticipants {
    public static class StormpathAccountIterator implements Iterator<List<Account>>, Iterable<List<Account>> {
        private static final int DEFAULT_PAGE_SIZE = 100;
        private final Directory dir;
        private int pageStart = 0;
        private boolean hasNext = true;
        @Override
        public final boolean hasNext() {
            return hasNext;
        }
        @Override
        public final List<Account> next() {
            if (!hasNext) {
                return Collections.emptyList();
            }
            final int pageSize = pageSize();
            Iterator<Account> iterator = nextPage();
            List<Account> page = new ArrayList<>(pageSize);
            while (iterator.hasNext()) {
                page.add(iterator.next());
            }
            hasNext = page.size() == pageSize;
            pageStart += pageSize;
            return Collections.unmodifiableList(page);
        }
        @Override public final void remove() {
            throw new UnsupportedOperationException();
        }
        @Override public final Iterator<List<Account>> iterator() {
            return this;
        }
        public final int pageStart() {
            return pageStart;
        }
        public StormpathAccountIterator(Directory dir) {
            this.dir = dir;
        }
        public int pageSize() {
            return DEFAULT_PAGE_SIZE;
        }
        public Iterator<Account> nextPage() {
            AccountCriteria criteria = new DefaultAccountCriteria();
            criteria.offsetBy(pageStart());
            criteria.limitTo(pageSize());
            criteria.withCustomData();
            AccountList list = dir.getAccounts(criteria);
            return list.iterator();
        }
    }

    public static void main(String[] args) {
        Config config = ClientProvider.getConfig();
        config.set(Props.HOST, "https://asthma-staging.sagebridge.org");
        
        Session session = ClientProvider.signIn(config.getAdminCredentials());
        AdminClient adminClient = session.getAdminClient();
        
        // Get all accounts in a specific stormpath directory
        ApiKey apiKey = ApiKeys.builder()
                .setId("<FILL>")
                .setSecret("<FILL>").build();
        Client client = Clients.builder().setApiKey(apiKey).build();

        // Iterate and when you find a YMedia account, delete it.
        Directory asthmaStaging = client.getResource("<FILL>", Directory.class);
        
        StormpathAccountIterator pages = new StormpathAccountIterator(asthmaStaging);
        for (List<Account> page : pages) {
            for (Account account : page) {
                System.out.println("What the heck: " + account.getEmail());
                //if (account.getEmail().endsWith("@ymedialabs.com")) {
                if (account.getStatus() == AccountStatus.UNVERIFIED) {
                    System.out.println("Deleting: " + account.getEmail());
                    adminClient.deleteUser(account.getEmail());
                }
            }
        }
        /*
        AccountList accounts = asthmaStaging.getAccounts();
        for (Account account : accounts) {
            System.out.println("What the heck: " + account.getEmail());
            if (account.getEmail().endsWith("@ymedialabs.com")) {
            // if (account.getStatus() == AccountStatus.UNVERIFIED) {
                System.out.println("Deleting: " + account.getEmail());
                // adminClient.deleteUser(account.getEmail());
            } else {
                System.out.println("NOT deleting: " + account.getEmail());
            }
        }*/
    }
    
}
