package com.example.pranaykumar.bakingcakes;

import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PRANAYKUMAR on 24-06-2017.
 */

public class QueryUtils {
  private QueryUtils(){
  }
  private static final String TAG="O_MY";

  public static ArrayList<Recipe> fetchRecipes(String requestUrl){
    URL url=createUrl(requestUrl);

    String jsonResponse=null;
    jsonResponse=makeHttpRequest(url);

    ArrayList<Recipe> recipes=extractRecipeFromJson(jsonResponse);
    return recipes;
  }

  private static ArrayList<Recipe> extractRecipeFromJson(String jsonResponse) {
    if(TextUtils.isEmpty(jsonResponse)){
      return null;
    }
    ArrayList<Recipe> recipes=new ArrayList<>();
    try{
      JSONArray baseJson=new JSONArray(jsonResponse);
      for(int i=0;i<baseJson.length();i++){
        JSONObject currentRecipe=baseJson.getJSONObject(i);
        String recipe_name=currentRecipe.getString("name");
        int id=currentRecipe.getInt("id");
        int servings=currentRecipe.getInt("servings");
        ArrayList<ArrayList<String>> ingredients = new ArrayList<>();

        JSONArray ing=currentRecipe.getJSONArray("ingredients");

        for(int j=0;j<ing.length();j++){
          ArrayList<String> item=new ArrayList<>();
          JSONObject currentIngredient=ing.getJSONObject(j);
          String quantity=currentIngredient.getString("quantity");
          String measure=currentIngredient.getString("measure");
          String ingredient=currentIngredient.getString("ingredient");

          item.add(0,quantity);
          item.add(1,measure);
          item.add(2,ingredient);
          ingredients.add(item);

        }

        ArrayList<ArrayList<String>> Steps = new ArrayList<>();

        JSONArray steps=currentRecipe.getJSONArray("steps");

        for(int j=0;j<steps.length();j++){
          ArrayList<String> step=new ArrayList<>();
          JSONObject currentStep=steps.getJSONObject(j);
          String shortDescription=currentStep.getString("shortDescription");
          String description=currentStep.getString("description");
          String videoUrl=currentStep.getString("videoURL");
          String thumbnailUrl=currentStep.getString("thumbnailURL");

          step.add(0,shortDescription);

          step.add(1,description);
          step.add(2,videoUrl);
          step.add(3,thumbnailUrl);
          Steps.add(step);
        }
        Recipe recipe=new Recipe(id,recipe_name,ingredients,Steps,servings);
        recipes.add(recipe);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return recipes;
  }
  private static String makeHttpRequest(URL url) {
    String jsonResponse = "";

    //If the URL is null,then return early.
    if (url == null) {
      return jsonResponse;
    }
    HttpURLConnection urlConnection = null;
    InputStream inputStream = null;
    try {
      urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setReadTimeout(10000/*milliseconds*/);
      urlConnection.setConnectTimeout(15000);
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();

      //If the request was succesful (response code 200),
      //then read the input stream and parse the response.
      if (urlConnection.getResponseCode() == 200) {
        inputStream = urlConnection.getInputStream();
        jsonResponse = readFromStream(inputStream);
      }
    } catch (IOException e) {
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return jsonResponse;
  }

  private static String readFromStream(InputStream inputStream) {
    StringBuilder output = new StringBuilder();
    if (inputStream != null) {
      InputStreamReader inputStreamReader =
          new InputStreamReader(inputStream, Charset.forName("UTF-8"));
      BufferedReader reader = new BufferedReader(inputStreamReader);
      String line = null;
      try {
        line = reader.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      while (line != null) {
        output.append(line);
        try {
          line = reader.readLine();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return output.toString();
  }

  private static URL createUrl(String requestUrl) {
    URL url = null;
    try {
      url = new URL(requestUrl);
    } catch (MalformedURLException e) {
    }
    return url;
  }
}
