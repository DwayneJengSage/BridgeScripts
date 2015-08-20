package org.sagebionetworks.bridge.scripts.parkinson.monthly;

import org.sagebionetworks.bridge.scripts.Scripts;
import org.sagebionetworks.bridge.scripts.SurveyBuilder;
import org.sagebionetworks.bridge.scripts.SurveyProvider;
import org.sagebionetworks.bridge.sdk.models.schedules.Activity;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;
import org.sagebionetworks.bridge.sdk.models.schedules.SchedulePlan;
import org.sagebionetworks.bridge.sdk.models.schedules.ScheduleType;
import org.sagebionetworks.bridge.sdk.models.schedules.SurveyReference;
import org.sagebionetworks.bridge.sdk.models.surveys.Survey;

public class MedicationsSurvey implements SurveyProvider {

    private final String NAME = "Medications Survey";

    @Override
    public Survey createSurvey() {
        Survey survey = new Survey();
        survey.setName(NAME);
        survey.setIdentifier("MedicationsSurvey");
        return new SurveyBuilder(survey)
            .medicineSet("Amantadine", "Amantadine (Symmetrel)", "Apomorphine", Scripts.options("50mg (syrup)", "100mg"))
            .medicineSet("Apomorphine", "Apomorphine (Apokyn)", "Benztropine", Scripts.options("2mg", "4mg", "6mg"))
            .medicineSet("Benztropine", "Benztropine (Cogentin)", "Bromocriptine", Scripts.options("0.5mg", "1mg", "2mg"))
            .medicineSet("Bromocriptine", "Bromocriptine (Parlodel)", "Carbidopa", Scripts.options("0.8mg", "2.5mg", "5mg"))
            .medicineSet("Carbidopa", "Carbidopa, levodopa, or entacapone (Stalevo)", "Carbidopa-levodopa", 
                            Scripts.options("Stalevo 50", "Stalevo 75", "Stalevo 100", "Stalevo 125", "Stalevo 150", "Stalevo 200"))
            .medicineSet("Carbidopa-levodopa", "Carbidopa-levodopa (Sinemet, Sinemet CR)", "Entacapone", 
                            Scripts.options("10/100 (carbidopa/levodopa)", "25/100 (carbidopa/levodopa)", "25/250 (carbidopa/levodopa)"))
            .medicineSet("Entacapone", "Entacapone (Comtan)", "Levodopa-benserazide", 
                            Scripts.options("200mg"))
            .medicineSet("Levodopa-benserazide", "Levodopa-benserazide (Madopar)", "Melevodopa", 
                            Scripts.options("50/12.5mg (levodopa/benserazide)", "100/25mg (levodopa/benserazide)", "200/50mg (levodopa/benserazide)"))
            .medicineSet("Melevodopa", "Melevodopa (Sirio)", "Pramipexole", null)
            .medicineSet("Pramipexole", "Pramipexole (Mirapex, Mirapex ER, Mirapexin, Sifrol)", "Rasagiline", 
                            Scripts.options("0.125mg", "0.25mg", "0.5mg", "0.75mg", "1.0mg", "1.5mg", "2.25mg", "3mg", "3.75mg", "4.5mg"))
            .medicineSet("Rasagiline", "Rasagiline (Azilect)", "Ropinirole", Scripts.options("0.5mg", "1.0mg"))
            .medicineSet("Ropinirole", "Ropinirole (Adartel, Requip, Requip XL, Ropark)", "Rotigotine", 
                            Scripts.options("0.25mg", "0.5mg", "1.5mg", "2mg", "3mg", "4mg", "5mg", "6mg", "8mg", "12mg"))
            .medicineSet("Rotigotine", "Rotigotine (Neupro)", "Selegiline", Scripts.options("1mg", "2mg", "3mg", "4mg", "6mg", "8mg"))
            .medicineSet("Selegiline", "Selegiline (l-deprenyl, Eldepryl, Zelapar)", "Tolacapone",
                            Scripts.options("1.25mg (Zelapar)", "5mg", "6mg", "9mg", "12mg"))
            .medicineSet("Tolacapone", "Tolacapone (Tasmar)", "Trihexyphenidyl", Scripts.options("100mg"))
            .medicineSet("Trihexyphenidyl", "Trihexyphenidyl (Apo-Trihex, Artane)", "Other",
                            Scripts.options("0.4mg (elixir)", "2mg", "5mg"))
            .text("Other1", "Please list any medications (with dosages) you take that were not included in this survey", 0, 255)
            .build();
    }

    @Override
    public SchedulePlan createSchedulePlan(String surveyIdentifier, String surveyGuid) {
        SchedulePlan plan = new SchedulePlan();
        plan.setLabel(NAME + " Schedule Plan");
        
        Schedule schedule = new Schedule();
        schedule.setLabel(NAME + " Schedule");
        schedule.setScheduleType(ScheduleType.RECURRING);
        schedule.setDelay("P1D");
        schedule.setInterval("P1M");
        schedule.setExpires("P21D");
        schedule.addTimes("10:00");

        Activity activity = new Activity(NAME, "16 Questions", new SurveyReference(surveyIdentifier, surveyGuid));
        schedule.addActivity(activity);
        
        plan.setSchedule(schedule);
        return plan;
    }
    
}
