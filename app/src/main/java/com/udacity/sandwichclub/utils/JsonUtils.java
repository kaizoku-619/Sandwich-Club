package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String DESCRIPTION = "description";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String IMAGE = "image";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject mainObject = new JSONObject(json);
        JSONObject name = mainObject.getJSONObject(NAME);

        String  mainName = name.optString(MAIN_NAME);
        String description = mainObject.optString(DESCRIPTION);
        String origin = mainObject.optString(PLACE_OF_ORIGIN);
        String image = mainObject.optString(IMAGE);

        ArrayList<String> alsoKnownAs = new ArrayList<>();
        JSONArray jAlsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS);
        if (jAlsoKnownAs != null) {
           for (int i=0;i<jAlsoKnownAs.length();i++){
               alsoKnownAs.add(jAlsoKnownAs.optString(i));
           }
        }

        ArrayList<String> ingredients = new ArrayList<>();
        JSONArray jIngredients = mainObject.getJSONArray(INGREDIENTS);
        if (jIngredients != null) {
           for (int i=0;i<jIngredients.length();i++){
            ingredients.add(jIngredients.optString(i));
           }
        }

        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, origin, description, image, ingredients);

        return sandwich;
    }
}
