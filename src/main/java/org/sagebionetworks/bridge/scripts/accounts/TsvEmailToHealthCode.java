package org.sagebionetworks.bridge.scripts.accounts;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.io.Files;
import com.stormpath.sdk.client.Client;

import org.sagebionetworks.bridge.crypto.AesGcmEncryptor;
import org.sagebionetworks.bridge.crypto.Encryptor;
import org.sagebionetworks.bridge.scripts.Scripts;
import org.sagebionetworks.bridge.scripts.dynamo.DynamoHelper;

/**
 * <p>
 * This script takes a TSV of data and replaces the email address with a health code. This script assumes there is only
 * one email column.
 * </p>
 * <p>
 * Usage: TsvEmailToHealthCode [studyId] [input file path] [output file path] [email column name]
 * <p>
 * <p>
 * To use this in Maven:
 * mvn exec:java -Dexec.mainClass=org.sagebionetworks.bridge.scripts.accounts.TsvEmailToHealthCode \
 * -Dexec.args="[studyId] [input file path] [output file path] [email column name]"
 */
public class TsvEmailToHealthCode {
    private static final Joiner COLUMN_JOINER = Joiner.on('\t').useForNull("");
    private static final String HEALTH_CODE_COLUMN_NAME = "healthCode";

    private DynamoHelper dynamoHelper;
    private StormpathHelper stormpathHelper;

    /** Dynamo helper, used to get the health code from the health ID. */
    public final void setDynamoHelper(DynamoHelper dynamoHelper) {
        this.dynamoHelper = dynamoHelper;
    }

    /** Stormpath helper, used to get the health ID from the email address. */
    public final void setStormpathHelper(StormpathHelper stormpathHelper) {
        this.stormpathHelper = stormpathHelper;
    }

    /** Java main method. Main entry point for the converter. */
    public static void main(String[] args) throws IOException {
        // parse args
        if (args.length != 4) {
            throw new IllegalArgumentException(
                    "Usage: TsvEmailToHealthCode [studyId] [input file path] [output file path] [email column name]");
        }
        String studyId = args[0];
        File inputFile = new File(args[1]);
        File outputFile = new File(args[2]);
        String emailColumnName = args[3];

        // init and execute
        try {
            TsvEmailToHealthCode converter = init();
            converter.execute(studyId, inputFile, outputFile, emailColumnName);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            // Maven exec:java won't shut down cleanly without this.
            System.exit(0);
        }
    }

    /** Initializes the converter. In a more mature project, this might be Spring config. */
    private static TsvEmailToHealthCode init() {
        // load config properties
        Properties props = Scripts.loadProperties();

        // set up Dynamo helper
        String ddbPrefix = props.getProperty("env") + "-" + props.getProperty("user") + "-";
        DynamoDB ddbClient = new DynamoDB(new AmazonDynamoDBClient());
        Table ddbHealthIdTable = ddbClient.getTable(ddbPrefix + "HealthId");
        DynamoHelper dynamoHelper = new DynamoHelper();
        dynamoHelper.setDdbHealthIdTable(ddbHealthIdTable);

        // set up Encryptor
        String healthCodeKey = props.getProperty("health.code.key");
        Encryptor healthCodeEncryptor = new AesGcmEncryptor(healthCodeKey);

        // set up Stormpath Helper
        Client stormpathClient = Scripts.getStormpathClient(props);
        StormpathHelper stormpathHelper = new StormpathHelper();
        stormpathHelper.setHealthCodeEncryptor(healthCodeEncryptor);
        stormpathHelper.setProperties(props);
        stormpathHelper.setStormpathClient(stormpathClient);

        // set up converter
        TsvEmailToHealthCode converter = new TsvEmailToHealthCode();
        converter.setDynamoHelper(dynamoHelper);
        converter.setStormpathHelper(stormpathHelper);
        return converter;
    }

    /** Runs the actual conversion. */
    private void execute(String studyId, File inputFile, File outputFile, String emailColumnName) throws IOException {
        Integer emailColIdx = null;
        int count = 0;
        Stopwatch stopwatch = Stopwatch.createStarted();
        try (BufferedReader inputFileReader = Files.newReader(inputFile, Charsets.UTF_8);
                PrintWriter outputFileWriter = new PrintWriter(Files.newWriter(outputFile, Charsets.UTF_8))) {
            // read headers - Replace email with health code and write to output
            String[] headerArray = inputFileReader.readLine().split("\t");
            for (int i = 0; i < headerArray.length; i++) {
                if (headerArray[i].equals(emailColumnName)) {
                    headerArray[i] = HEALTH_CODE_COLUMN_NAME;
                    emailColIdx = i;
                    break;
                }
            }

            if (emailColIdx == null) {
                throw new IllegalArgumentException("input file doesn't contain email column");
            }

            outputFileWriter.println(COLUMN_JOINER.join(headerArray));

            // loop over rows
            String inputLine;
            while ((inputLine = inputFileReader.readLine()) != null) {
                // read input
                String[] columnArray = inputLine.split("\t");

                // convert email to health code
                String email = columnArray[emailColIdx];
                try {
                    String healthId = stormpathHelper.getHealthIdForEmail(studyId, email);
                    String healthCode = dynamoHelper.getHealthCodeFromHealthId(healthId);
                    columnArray[emailColIdx] = healthCode;

                    // write output
                    outputFileWriter.println(COLUMN_JOINER.join(columnArray));
                } catch (Exception ex) {
                    // Don't want to fail the whole thing based on one row.
                    System.err.println("Couldn't convert email address " + email + ": " + ex.getMessage());
                    ex.printStackTrace();
                }

                // DDB HealthId table has a read capacity of 30. For safety, rate limit ourselves to 20/s (50ms sleep
                // time). This should get through ~40k rows in ~33 min.
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                // Log progress
                if (++count % 2000 == 0) {
                    System.out.println("Progress: " + count + " rows in " + stopwatch.elapsed(TimeUnit.SECONDS) +
                            " seconds");
                }
            }
        }
    }
}
