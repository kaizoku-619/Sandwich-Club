package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject mainObject = new JSONObject(json);
        JSONObject name = mainObject.getJSONObject("name");

        String  mainName = name.getString("mainName");
        String description = mainObject.getString("description");
        String origin = mainObject.getString("placeOfOrigin");
        String image = mainObject.getString("image");

        ArrayList<String> alsoKnownAs = new ArrayList<>();
        JSONArray jAlsoKnownAs = name.getJSONArray("alsoKnownAs");
        if (jAlsoKnownAs != null) {
           for (int i=0;i<jAlsoKnownAs.length();i++){
               alsoKnownAs.add(jAlsoKnownAs.getString(i));
           }
        }

        ArrayList<String> ingredients = new ArrayList<>();
        JSONArray jIngredients = mainObject.getJSONArray("ingredients");
        if (jIngredients != null) {
           for (int i=0;i<jIngredients.length();i++){
            ingredients.add(jIngredients.getString(i));
           }
        }

        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, origin, description, image, ingredients);

        return sandwich;
    }
}
