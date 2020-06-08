package paycalc;

public class CalendarEntry {
	public String firstName;
	public String lastName;
	public String date;
	public String time;
	public String duration;
	public String type;
	
	public String toString() {
		return "First Name: " + firstName + ", Last Name: " + lastName + 
				", Date: " + date + ", Time: " + time + ", Duration: " + duration + ", Type: " + type;
	}
}
