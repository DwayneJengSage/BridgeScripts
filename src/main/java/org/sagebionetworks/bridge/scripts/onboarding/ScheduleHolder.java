package org.sagebionetworks.bridge.scripts.onboarding;

import org.sagebionetworks.bridge.sdk.models.holders.GuidCreatedOnVersionHolder;
import org.sagebionetworks.bridge.sdk.models.schedules.Schedule;

public interface ScheduleHolder {
	public Schedule getSchedule(GuidCreatedOnVersionHolder keys);
}
