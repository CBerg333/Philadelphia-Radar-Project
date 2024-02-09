import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.*;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Driver {

	public static void main(String[] args) throws JSONException {
		CrimeLocationData criminalStat = new CrimeLocationData("https://api.crimeometer.com/v1/incidents/raw-data", "RJbfWvNmy08CYXMpGcSXiTWcXg28v9z3ARISTbK9"
, 39.95233,  -75.16379);
		org.json.simple.JSONObject obj = criminalStat.assignJSON();
		criminalStat.grade(obj);
		String g = criminalStat.getGrade();
		System.out.println(g);
		
		YelpLocationData businessStat = new YelpLocationData("https://api.yelp.com/v3/businesses/search", "-_Rs7keJSgr9hZAVIfImQ4R-sFDvSK6QwuyUyiiRrwOOzDX2qncN8Rl5gx7n0U-vl2oI_nZa7t5-pd8wc88RfuqWhrD31yveEofG9efYI87NFzjh25RiZBHDHx2QYXYx", 39.95261295, -75.16522768);
		org.json.simple.JSONObject obj2 = businessStat.assignJSON();
		businessStat.grade(obj2);
		String g2 = businessStat.getGrade();
		System.out.println(g2);
		
	}
}
