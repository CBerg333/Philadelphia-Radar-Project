import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.JSONException;


public abstract class LocationData {
	//API URL template
		protected String api;
	//Field used for the API key.
		protected String key;
	//Stores the grade for the data.
		protected String grade; 
	//Stores latitude.
		protected double latitude;
	//Stores longitude.
		protected double longitude;
	//Stores radius in meters. Only 2 acceptable values.
		protected int radius;
/**
 * For this constructor, the string is set to "Ungraded" until the grade() method is called. Radius
 * is set to a default 125 meters.
 */
		public LocationData(String api, String key, double latitude, double longitude) {
			this.api = api;
			this.key = key;
			this.radius = 125;
			this.latitude = latitude;
			this.longitude = longitude;
			this.grade = "Ungraded";
		}	

/**
 * Getter for the data's final grade.
 * @return Letter grade from A-F. 
 */
		public String getGrade() {
			return grade;
		}
		
		@SuppressWarnings("unused")
	    public void setAPI_KEY(String key)
	    {
	        this.key = key;
	    }

	    /**
	     * The <i>getAPI_KEY</i> method returns a valid API key.
	     * @return | A verified API key.
	     */

	    @SuppressWarnings("unused")
	    public String getAPI_KEY()
	    {
	        return this.key;
	    }
	    

	    public String toString()
	    {
	        return "Endpoint: " + api;
	    }
	    

	    /**
	     * This method assigns and returns the JSON object needed to interpret and grade the crime data.
	     * @throws JSONException Handles any JSON exceptions.
	     * @return JSONObject analysis.
	     */
	    public JSONObject assignJSON() throws JSONException {
	    	//Creating the JSON parser.
	    			JSONParser parse = new JSONParser();
	    	//Calls the retrieveAPI to store the API statistics into a string.
	    			String info = retrieveAPI();
	    	//Creates the JSONObject that we'll create with crimeInfo.
	    			JSONObject analysis;
	    	//In case there's any exceptions:
	    			try {
	    				//Creates JSONObject used to grade the data.
	    				analysis = (JSONObject)parse.parse(info);
	    			} catch (ParseException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    				return null;
	    			}
	    	//Returns API JSON object.
	    			return analysis;
	    		}
/**
 * 		
 * @param obj JSON object created from the data sourced from the API.
 * @throws JSONException Catches any potential errors in the JSON object.
 */
		public abstract void grade(org.json.simple.JSONObject obj) throws JSONException;

/**
 * The <i>retrieveAPI()</i> method returns a <i>String</i> containing all relevant location data in a radius
 * <i>radius</i> around the user's location.
 */
		public abstract String retrieveAPI();
		
		public abstract void display();	
		
}

