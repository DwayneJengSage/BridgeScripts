package org.sagebionetworks.bridge.scripts;

import java.util.List;
import java.util.Properties;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.ConsistentReads;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.google.common.collect.Lists;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountCriteria;
import com.stormpath.sdk.account.AccountStatus;
import com.stormpath.sdk.account.Accounts;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.directory.Directory;

public class OutputSharingStatuses {

    private Properties properties;
    
    public static void main(String[] args) throws Exception {
        new OutputSharingStatuses().run();
    }
    
    private void run() {
        properties = Scripts.loadProperties();
        
        List<String> sharing = Lists.newArrayList();
        List<String> accounts = Lists.newArrayList();
        List<String> studies = Lists.newArrayList("asthma", "breastcancer", "cardiovascular", "diabetes", "parkinson");
        for (String study : studies) {
            sharing.add(findSharingScopeForStudy(study));    
            accounts.add(findAccountsForStudy(study));
        }
        for (String out : accounts) {
            System.out.println(out);
        }
        for (String out : sharing) {
            System.out.println(out);
        }
    }

    private String findAccountsForStudy(String study) {
        Client client = Scripts.getStormpathClient(properties);

        Directory directory = client.getResource(properties.getProperty(study+".href"), Directory.class);
        
        AccountCriteria criteria = Accounts.criteria().limitTo(100);
        
        int unverified = 0;
        int enabled = 0;
        int disabled = 0;
                
        for (Account account : directory.getAccounts(criteria)) {
            if (account.getStatus() == AccountStatus.UNVERIFIED){
                unverified++;
            } else if (account.getStatus() == AccountStatus.ENABLED) {
                enabled++;
            } else if (account.getStatus() == AccountStatus.DISABLED) {
                disabled++;
            }
        }
        return String.format("%s: %s verified, %s unverified, %s total", study, enabled, unverified, (enabled+unverified+disabled));
    }
    
    private String findSharingScopeForStudy(String study) {
        AmazonDynamoDB client = Scripts.getDynamoDbClient(properties);
        
        DynamoDBMapperConfig mapperConfig = new DynamoDBMapperConfig.Builder().withSaveBehavior(SaveBehavior.UPDATE)
                .withConsistentReads(ConsistentReads.CONSISTENT)
                .withTableNameOverride(new TableNameOverride(properties.getProperty("aws.table.name"))).build();
        DynamoDBMapper mapper = new DynamoDBMapper(client, mapperConfig);
        
        // The only place we need the study, and that's to find all the options for all the 
        // participants in a given study.
        DynamoDBScanExpression scan = new DynamoDBScanExpression();

        Condition condition = new Condition();
        condition.withComparisonOperator(ComparisonOperator.EQ);
        condition.withAttributeValueList(new AttributeValue().withS(study));
        scan.addFilterCondition("studyKey", condition);
        
        List<DynamoParticipantOptions> mappings = mapper.scan(DynamoParticipantOptions.class, scan);
        
        int noSharing = 0;
        int someSharing = 0;
        int allSharing = 0;
        
        for (DynamoParticipantOptions opt : mappings) {
            String value = opt.getOptions().get("SHARING_SCOPE");
            if ("ALL_QUALIFIED_RESEARCHERS".equals(value)) {
                allSharing++;
            } else if ("SPONSORS_AND_PARTNERS".equals(value)) {
                someSharing++;
            } else {
                noSharing++;
            }
        }
        return String.format("%s: %s noSharing, %s someSharing, %s allSharing", study, noSharing, someSharing, allSharing);
    }
    
}
