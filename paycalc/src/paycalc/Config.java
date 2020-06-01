package paycalc;

import java.util.ArrayList;
import java.util.Map;

public class Config {
	public String uid;
	public String api_key;
	ArrayList<SessionDefinition> session_defs;
	
	public void populateTutorMap(Map<String, String> tutor_map) {}
	
	public void populatePayRateMap(Map<String, Float> pay_rate_map) {}
	
}
