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

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnownAsTextView;
    private TextView placeOfOriginTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTextView = findViewById(R.id.tv_also_known_as);
        placeOfOriginTextView = findViewById(R.id.tv_place_of_origin);
        descriptionTextView = findViewById(R.id.tv_description);
        ingredientsTextView = findViewById(R.id.tv_ingredients);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Populate the non-label text views within this activity with the values from the specified
     * sandwich model.
     *
     * @param sandwich the sandwich model used to populate all the non-label textviews within this
     *                 activity.
     */
    private void populateUI(Sandwich sandwich) {
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        DetailActivity.populateTextViewWithList(alsoKnownAsTextView, alsoKnownAsList);

        populateTextViewWithString(placeOfOriginTextView, sandwich.getPlaceOfOrigin());

        populateTextViewWithString(descriptionTextView, sandwich.getDescription());

        List<String> ingredientsList = sandwich.getIngredients();
        DetailActivity.populateTextViewWithList(ingredientsTextView, ingredientsList);
    }

    /**
     * Helper function used to populate the specified text view with the all the strings from the
     * specified list. The string shall be displayed within the specified text view, comma
     * separated. If the specified list is empty the text view is populated with the string "N/A".
     *
     * @param textView the text view that will be populated with the strings from the specified list.
     * @param list the list containing the strings that will be populated within the specified text view.
     */
    private static void populateTextViewWithList(TextView textView, List<String> list)
    {
        if(list.isEmpty())
        {
            textView.setText("N/A");
        }
        else
        {
            StringBuilder sb = new StringBuilder();

            sb.append(list.get(0));

            for(int i = 1; i < list.size(); ++i)
            {
                sb.append(", ");
                sb.append(list.get(i));
            }

            textView.setText(sb.toString());
        }
    }

    /**
     * Helper function used to populate the specified text view with the specified string. If the
     * specified string is empty, then the text view is populated with the string "N/A".
     *
     * @param textView the text view that will be populated with the specified string.
     * @param string the string that will be populated within the specified text view.
     */
    private static void populateTextViewWithString(TextView textView, String string)
    {
        if(string.length() > 0)
        {
            textView.setText(string);
        }
        else
        {
            textView.setText("N/A");
        }
    }
}
