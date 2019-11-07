package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * Parse the specified JSON string into a Sandwich object.
     *
     * The specified JSON string is expected to have the following format:
     *
     * <pre>
     * name: Object
     *   mainName: String
     *   alsoKnownAs: String Array
     * placeOfOrigin: String
     * description: String
     * image: String (Path to an image external image resource)
     * ingredients: String Array
     * </pre>
     *
     * @param json the JSON string to be parsed.
     * @return the JSON string in a Sandwich object or null if parsing the specified JSON string fails.
     */
    public static Sandwich parseSandwichJson(String json) {
        try
        {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject nameObject = jsonObject.getJSONObject("name");

            String mainName = nameObject.getString("mainName");

            List<String> alsoKnownAs = JsonUtils.fromJsonArrayToList(nameObject, "alsoKnownAs");

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");

            String description = jsonObject.getString("description");

            String image = jsonObject.getString("image");

            List<String> ingredients = JsonUtils.fromJsonArrayToList(jsonObject, "ingredients");

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        }
        catch (JSONException e)
        {
            // Failed to parse the JSON string
            return null;
        }
    }

    /**
     * A Helper function that converts the JSON array specified by the JSON array into a List.
     *
     * @param jsonObject the JSON object containing the JSON array to be converted.
     * @param jsonArrayName the name of the JSON array in the specified JSONObject.
     * @return the JSON array specified by the JSON array name as a List.
     * @throws JSONException if there is a parse error.
     */
    private static List<String> fromJsonArrayToList(JSONObject jsonObject,
    		                                        String jsonArrayName) throws JSONException
    {
        List<String> list = new ArrayList<String>();
        JSONArray jsonArray = jsonObject.getJSONArray(jsonArrayName);
        for(int i = 0; i < jsonArray.length(); ++i)
        {
            list.add(jsonArray.getString(i));
        }

        return list;
    }
}
