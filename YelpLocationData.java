import java.io.IOException;             // Needed for the IOException class.
import java.io.InputStream;             // Needed for the InputStream class.
import java.net.HttpURLConnection;      // Needed for the HttpURLConnection class.
import java.net.MalformedURLException;  // Needed for the MalformedURLException class.
import java.net.URL;                    // Needed for the URL class.
import java.util.Scanner;               // Needed for the Scanner class.
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.JSONException;
import org.json.simple.JSONObject;

/**---------------------------------------------------------------
 * Class YelpLocationData.java
 *
 * @author Christopher McGarrity
 * @author Jonathan Gehret
 * @author Joseph Piazza
 *
 * This class retrieves business data from the Yelp Fusion
 * /business/search endpoint (Incomplete).
-----------------------------------------------------------------*/

public final class YelpLocationData extends LocationData
{
    private double avgRatings; //field representing the average of business ratings.
    private double highestRated;// - field representing the highest rated business.
    private String name;


    /**
     * This constructor initializes a <i>YelpLocationData</i> object using a verified API key.
     * @param anAPI_KEY | A verified API key.
     */

    public YelpLocationData(String api, String key, double latitude, double longitude)
    {
        super(api, key, latitude, longitude);
        this.avgRatings = 0.0;
        this.highestRated = 0.0;
        this.name = "";
    }
    
    public String retrieveAPI()
    {
        // Declare a new StringBuilder object.
        StringBuilder data;
        data = new StringBuilder();

        try
        {
            /*
             * Declare a new URL object.
             *
             * Pass the base URL for Yelp Fusion: https://api.yelp.com/v3/businesses/search
             *
             * Use the user's latitude and longitude as search parameters.
             *
             * A predefined radius will allow all business data within a distance of the user
             * to be retrieved.
             */

            URL url;
            url = new URL(api + "?latitude=" + latitude + "&longitude=" + longitude + "&radius=" + radius);

            try
            {
                // Open a connection to Yelp Fusion.
                HttpURLConnection urlConnection;
                urlConnection = (HttpURLConnection) url.openConnection();

                // Set the request method.
                urlConnection.setRequestMethod("GET");

                if (this.key == null)
                {
                    // If no API key was provided, disconnect from the service.
                    urlConnection.disconnect();
                    System.exit(0);
                }
                else
                {
                    // Set the authorization header using a valid API key.
                    urlConnection.setRequestProperty("Authorization", "Bearer " + this.key);
                }

                // Retrieve the byte stream from the Yelp Fusion endpoint.
                InputStream is;
                is = urlConnection.getInputStream();

                // Create a Scanner object to parse the byte stream.
                Scanner stdin;
                stdin = new Scanner(is);

                // While the Scanner has lines to read, append them to the StringBuilder object.
                while (stdin.hasNext())
                {
                    data.append(stdin.nextLine());
                }
                stdin.close();
            }
            catch (IOException e)
            {
                // Display the handled error.
                e.printStackTrace();
            }
        }
        catch (MalformedURLException e)
        {
            // Display the handled error.
            e.printStackTrace();
        }

        // Return the data.
        return String.valueOf(data);
    }
 /**
  * Returns the average of all business ratings
  * @return Double avgRatings.
  */
    public double getAvg() {
    	return avgRatings;
    }
 /**
  * Returns the name of the highest rated business
  * @return String name.
  */
    public String getName() {
    	return name;
    }
 /**
  * Returns the highest rating found in the businesses discovered
  * @return String highestRated.
  */
    public double getHighest() {
    	return highestRated;
    }

	@Override
	public void grade(JSONObject analysis) throws JSONException {
		 	Object[][]gradeStorage = {{4.75, 4.25, 3.5, 3.0, 2.5}, {"A", "B", "C", "D", "F"}};
			 double counter = 0;
		     double total = 0;
		     double rating = 0;
			JSONArray jsonArray = (JSONArray) analysis.get("businesses");
	        for (int i = 0; i < jsonArray.size(); i++)
	        {
	            JSONObject business = (JSONObject) jsonArray.get(i);
	            counter++;
	            rating = (double) business.get("rating");
	            total += rating;

	            if (rating > highestRated)
	            {
	                highestRated = rating;
	                name = (String) business.get("name");
	            }
	        }
	        
	        avgRatings = total / counter;
	        for(int i = 0; i < gradeStorage[0].length; i++) {
	        	if(avgRatings <= (double) gradeStorage[0][i])
	        		grade = (String) gradeStorage[1][i];	        		
	        }
	        
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

}