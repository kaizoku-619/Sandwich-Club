package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView ingredientsIv;
    private TextView alsoKnownTv, description, ingredientsTv, originTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        List<String> alsoKnowns = sandwich.getAlsoKnownAs();
        for (String alsoKnown: alsoKnowns) {
            alsoKnownTv.append("- " + alsoKnown + "\n");
        }

        description.setText(sandwich.getDescription() + "\n");

        List<String> ingredients = sandwich.getIngredients();
        for (String ingredient: ingredients) {
            ingredientsTv.append("- " + ingredient + "\n");
        }

        originTv.setText(sandwich.getPlaceOfOrigin() + "\n");

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void initView() {
        ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        description = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        originTv = findViewById(R.id.origin_tv);
    }
}
