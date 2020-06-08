import com.google.gson.*;
import paycalc.Config;
import paycalc.Rest;
import paycalc.CalendarEntry;

import java.util.HashMap;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import java.nio.charset.StandardCharsets;

public class PayCalculator {
	private HashMap<String,String> tutor_map;
	private HashMap<String,Float> pay_rate_map;
	private HashMap<String, Float> tutor_final_rate;
	private CalendarEntry[] calendarEntries;
	private String uid;
	private String password;

	PayCalculator(Config config) {
		// Set up this instance based on config
		uid = config.uid;
		password = config.password;
		tutor_map = new HashMap<String,String>();
		pay_rate_map = new HashMap<String,Float>();
		tutor_final_rate = new HashMap<String, Float>();
	
		config.populateTutorMap(tutor_map);
		config.populatePayRateMap(pay_rate_map);
		
	/*	for (String key: tutor_map.keySet()){
            System.out.println(key + " " + tutor_map.get(key));
		}

		for (String key: pay_rate_map.keySet()){
            System.out.println(key + " " + pay_rate_map.get(key) + "");
		}*/
	}
	
	public void ProcessPayments(String start_date, String end_date) {
		// Make REST API request
		// Get the result
		// Process the result
		String[] params = { "canceled=true", "minDate=" + start_date, "maxDate=" + end_date };
		String resp = Rest.Get(uid + ":" + password,
				"https://acuityscheduling.com/api/v1/appointments", params);
		//System.out.println(resp);
		Gson gson = new Gson();
		calendarEntries = gson.fromJson(resp, CalendarEntry[].class);
		/*for (CalendarEntry c : calendarEntries) {
			System.out.println(c);
		}*/
		
		for(CalendarEntry c : calendarEntries) {
			String tutorName = tutor_map.get(c.type);
			if(tutor_final_rate.containsKey(tutorName)){
				tutor_final_rate.put(tutorName, tutor_final_rate.get(tutorName) + pay_rate_map.get(c.type));
			} else {
				tutor_final_rate.put(tutorName, pay_rate_map.get(c.type));
			}
		}
		
		for (String key: tutor_final_rate.keySet()){
            System.out.println(key + " " + tutor_final_rate.get(key) + "");
		}
	} 
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Paycheck Calculator!");
		String cfg_str = FileUtils.readFileToString(new File(args[0]), StandardCharsets.UTF_8);
		Gson gson = new Gson();
		Config config = gson.fromJson(cfg_str, Config.class);
		PayCalculator calc = new PayCalculator(config);
		System.out.println(gson.toJson(config));
		calc.ProcessPayments(args[1], args[2]);
		//System.out.println(resp);
	}
}
