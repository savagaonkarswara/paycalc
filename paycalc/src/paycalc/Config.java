package paycalc;

import java.util.ArrayList;
import java.util.Map;

public class Config {
	public String uid;
	public String password;
	public ArrayList<SessionDefinition> session_defs;
	
	public void populateTutorMap(Map<String, String> tutor_map) {
		for(SessionDefinition session : session_defs) {
			if (!tutor_map.containsKey(session.type)) {
				tutor_map.put(session.type, session.tutor);
			} else if (tutor_map.get(session.type) != session.tutor) {
				System.err.println("Error: the key " + session.type + " is multiply defined in the config");
			}
		}
	}
	
	public void populatePayRateMap(Map<String, Float> pay_rate_map) {
		for(SessionDefinition session : session_defs) {
			if (!pay_rate_map.containsKey(session.type)) {
				pay_rate_map.put(session.type, session.pay_rate);
			} else if (pay_rate_map.get(session.type) != session.pay_rate) {
				System.err.println("Error: the key " + session.type + " is multiply defined in the config");
			} 	
		}
	}
	
}
