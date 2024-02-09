import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.HttpURLConnection;   
import java.io.InputStream; 
import java.net.MalformedURLException;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.*;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CrimeLocationData extends LocationData {
//Field determines the start date for crime statistics collection.
	protected String startTime;
//Determines the end date for crime statistics collection.
	protected String endTime;
//Stores number of total incidents found by the API.
	protected long incidentNum;
	
	public CrimeLocationData(String api, String key, double latitude, double longitude) throws JSONException {
		super(api, key, latitude, longitude);
		this.startTime = "2020-01-01%2000:00:00";
		this.endTime = "2020-12-31%2000:00:00";
		this.incidentNum = 0;
	}

	 
	public String retrieveAPI() {
//Stringbuilder used to record API information.
		StringBuilder crime = new StringBuilder();
//In case there are URL issues:
		try {
//Creates URL. Adds latitude, longitude, start date, end date, and radius.
			URL address = new URL(api + "?lat=" + latitude + "&lon=" + longitude + "&distance=" + radius + "m" + "&datetime_ini=" + startTime + "&datetime_end=" + endTime + "&page=1");

//In case there are API issues:
			try {
//Creates API connection with the created URL.
				HttpURLConnection gap = (HttpURLConnection) address.openConnection();	
				gap.setRequestMethod("GET");
				gap.setRequestProperty("Content-Type", "application/json");
				gap.setRequestProperty("x-api-key", key);
				InputStream input = gap.getInputStream();
//Scanner object created to translate the API data.
				Scanner translator = new Scanner(input);
//Loop adds every line to the StringBuilder.
				while(translator.hasNext()) {
					crime.append(translator.nextLine());
				}
				translator.close();
			}
//Catches input/output exceptions for the API.
			catch (IOException e)
	        {
	            // Display the handled error.
	            e.printStackTrace();
	        }

			
		}
//Catches improper URL declarations.
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//Catches improper JSON syntax.
		try {
			JSONParser parse = new JSONParser();
			JSONObject tst = (JSONObject)parse.parse(String.valueOf(crime));
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return String.valueOf(crime);
	}

	@Override
/**
 * Method that grades JSONObject analysis on an A-F scale.
 * @Param analysis JSONObject that stores API data.
 */
	public void grade(JSONObject analysis) throws JSONException {
/**
 * Two-dimensional array that stores the grade metrics,
 * along with their corresponding letter grades.
 */
		Object[][]gradeStorage = { {200, 400, 550, 730}, {"A", "B", "C", "D", "F"}};
//Sets incidentNum based on the number of crimes detected in the JSON object.
		incidentNum = ((Long) analysis.get("total_incidents")).intValue();
		for(int i = gradeStorage[0].length-1; i >= 0; i--) {
/**
* Compares incidentNum to gradeStorage[1].
*/
//Sets grade with the array's values.
			if(incidentNum <= (int)(gradeStorage[0][i]))
				grade = (String) gradeStorage[1][i];
//In case incidentNum is GREATER than 730:
			else if(incidentNum > 730)
				grade = "F";
		}
	}

	@Override
	public void display() {
		
	} 

}