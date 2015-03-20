package com.tse.ihm.jaifaim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.controller.GistController;
import com.tse.ihm.jaifaim.helper.UserHelper;
import com.tse.ihm.jaifaim.model.Difficulty;
import com.tse.ihm.jaifaim.model.Ingredient;
import com.tse.ihm.jaifaim.model.Recipe;
import com.tse.ihm.jaifaim.model.Step;
import com.tse.ihm.jaifaim.model.Type;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

public class NewRecipeActivity extends RoboActionBarActivity
{
    private static final String TAG = NewRecipeActivity.class.getName();

    @InjectView(R.id.new_recipe_image_container)      private LinearLayout m_ImageContainer;
    @InjectView(R.id.new_recipe_container_ingredients)      private LinearLayout m_IngredientsContainer;
    @InjectView(R.id.new_recipe_container_steps)      private LinearLayout m_StepsContainer;
    @InjectView(R.id.new_recipe_image)          private ImageView m_RecipeImage;
    @InjectView(R.id.new_recipe_title)          private EditText m_RecipeTitle;
    @InjectView(R.id.new_recipe_prep_time)      private EditText m_RecipePrepTime;
    @InjectView(R.id.new_recipe_cooking_time)   private EditText m_RecipeCookingTime;
    @InjectView(R.id.new_recipe_radio_type)   private RadioGroup m_RecipeTypeGroup;
    @InjectView(R.id.new_recipe_radio_difficulty)   private RadioGroup m_RecipeDifficultyGroup;
    @InjectView(R.id.new_recipe_ingredient)     private EditText m_RecipeIngredient;
    @InjectView(R.id.new_recipe_step)           private EditText m_RecipeStep;

    @Inject
    UserHelper m_UserHelper;

    private Difficulty m_Difficulty;
    private Type m_Type;

    private ArrayList<EditText> m_RecipeIngredients;
    private ArrayList<EditText> m_RecipeSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        m_RecipeIngredients = new ArrayList<>();
        m_RecipeSteps = new ArrayList<>();

        getDifficultyFromSelection();
        getTypeFromSelection();

        addTextWatcher(m_IngredientsContainer, m_RecipeIngredient);
        addTextWatcher(m_StepsContainer, m_RecipeStep);

        Log.d(TAG, "[onCreate] user : " + m_UserHelper.getUser().getUsername());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Class c = com.tse.ihm.jaifaim.Menu.itemSelected(item);
        if (c != null) {
            Intent i = new Intent(this, c);
            startActivity(i);
        }
        return true;
    }




    /**
     * Créer un gist contenant la recette
     * @param v
     */
    public void createRecipe(View v)
    {
        GistController gistController = new GistController();

        // TODO: finir de remplir recette
        Recipe recipe = new Recipe();

        // TODO :: changer
        recipe.setImageUrl("http://www.ot-cayeuxsurmer.fr/sites/otcsm/files/news/2014/11/08/1807-826.jpg");

        recipe.setTitle(m_RecipeTitle.getText().toString());
        recipe.setAuthor(m_UserHelper.getUser().getUsername());
        recipe.setCreationDate(String.valueOf(new Date().getTime()));
        recipe.setPrepTime(m_RecipePrepTime.getText().toString());
        recipe.setCookingTime(m_RecipeCookingTime.getText().toString());
        recipe.setDifficulty(m_Difficulty);
        recipe.setType(m_Type);

        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        for (EditText editText : getAllEditText(m_IngredientsContainer))
        {
            Ingredient ing = new Ingredient();
            ing.setName(editText.getText().toString());
            ingredientList.add(ing);
        }
        recipe.setIngredientList(ingredientList);

        ArrayList<Step> stepList = new ArrayList<>();
        for (EditText editText : getAllEditText(m_StepsContainer))
        {
            Step step = new Step();
            step.setDescription(editText.getText().toString());
            stepList.add(step);
        }
        recipe.setStepList(stepList);

        gistController.createNewRecipeInBackground(m_UserHelper.getUser(), recipe);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    /**
     * Gets the selected difficulty from the radiogroup
     * @return the difficulty
     */
    public void getDifficultyFromSelection()
    {
        m_RecipeDifficultyGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected
                if(checkedId == R.id.new_recipe_difficulty_easy) {
                    m_Difficulty = Difficulty.EASY;
                } else if(checkedId == R.id.new_recipe_difficulty_medium) {
                    m_Difficulty = Difficulty.MEDIUM;
                } else {
                    m_Difficulty = Difficulty.HARD;
                }
            }

        });




    }

    /**
     * Gets the selected type from the radiogroup
     * @return the type
     */
    public void getTypeFromSelection() {
        m_RecipeTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // find which radio button is selected
                if (checkedId == R.id.new_recipe_radio_type_starter) {
                    m_Type = Type.STARTER;
                } else if (checkedId == R.id.new_recipe_radio_type_dish) {
                    m_Type = Type.DISH;
                } else {
                    m_Type = Type.DESSERT;
                }
            }

        });

    }


    public void addTextWatcher(final LinearLayout _layout, EditText _textview)
    {
        _textview.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String nextIngredient = getLastEditText(_layout).getText().toString();

                if (s.length() > 0 && !nextIngredient.equals("")) {
                    EditText et = new EditText(NewRecipeActivity.this);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    et.setLayoutParams(p);
                    et.setHint(String.valueOf(_layout.getChildCount() + 1) + ":");
                    addEditText(_layout, et);
                } else {
                    removeLastEditText(_layout);
                }
            }
        });
    }

    public EditText getLastEditText(LinearLayout _layout)
    {
        return (EditText)_layout.getChildAt(_layout.getChildCount() - 1);
    }

    public ArrayList<EditText> getAllEditText(LinearLayout _layout)
    {
        ArrayList<EditText> list = new ArrayList<>();
        for (int i=0 ; i< _layout.getChildCount() ; i++)
        {
            list.add((EditText)_layout.getChildAt(i));
        }

        return list;
    }

    public void addEditText(LinearLayout _layout, EditText _et)
    {
        Log.d(TAG, "[addEditText]");
        _layout.addView(_et);
        addTextWatcher(_layout, _et);
    }

    public void removeLastEditText(LinearLayout _layout)
    {
        if (_layout.getChildCount() > 1)
        {
            _layout.removeView(_layout.getChildAt(_layout.getChildCount()-1));
        }

    }
}
