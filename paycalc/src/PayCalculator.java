import com.google.gson.*;
import paycalc.Config;
import paycalc.AcuityAccessor;

import java.util.HashMap;

public class PayCalculator {
	private HashMap<String,String> tutor_map;
	private HashMap<String,Float> pay_rate_map;
	private String uid;
	private String api_key;

	PayCalculator(Config config) {
		// Set up this instance based on config
	}
	
	public void ProcessPayments(String start_date, String end_date) {
		// Make REST API request
		// Get the result
		// Process the result
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Paycheck Calculator!");
		String cfg_str = "{}";
		Gson gson = new Gson();
		PayCalculator calc = new PayCalculator(gson.fromJson(cfg_str, Config.class));
		// calc.ProcessPayments(args[1], args[2]);
		AcuityAccessor accessor = new AcuityAccessor("19735213", "0a6ec9b8f43535be9065c08fd619a278");
		System.out.println(accessor.GetCalendarJsonString("2020-05-25", "2020-05-28"));
	}
}
