import com.google.gson.*;
import paycalc.Config;

public class PayCalculator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Pay Calculator!");
		String cfg_str = "{}";
		Gson gson = new Gson();
		Config config = gson.fromJson(cfg_str, Config.class);
	}

}
