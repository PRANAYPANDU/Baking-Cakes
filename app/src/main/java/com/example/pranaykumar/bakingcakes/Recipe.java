package com.example.pranaykumar.bakingcakes;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PRANAYKUMAR on 24-06-2017.
 */

public class Recipe implements Parcelable{

  private String mRecipeName;
  private ArrayList<ArrayList<String>> mIngredients;
  private ArrayList<ArrayList<String>> mSteps;
  private int mId;
  private int mServings;
  public Recipe(int id,String vRecipeName,ArrayList<ArrayList<String>> vIngredients,ArrayList<ArrayList<String>> vSteps,int servings){
      mRecipeName=vRecipeName;
      mIngredients=vIngredients;
      mSteps=vSteps;
      mServings=servings;
  }

  protected Recipe(Parcel in) {
    mId = in.readInt();
    mRecipeName = in.readString();
    Bundle IngredientsBundle = in.readBundle();
    Bundle StepsBundle = in.readBundle();
    mServings = in.readInt();
    ArrayList<ArrayList<String>>ing=new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>>stp=new ArrayList<ArrayList<String>>();
    for (int i = 0; i < IngredientsBundle.size(); i++) {
      ing.add(i, IngredientsBundle.getStringArrayList(String.valueOf(i)));
    }
    for (int i = 0; i < StepsBundle.size(); i++) {
      stp.add(i, StepsBundle.getStringArrayList(String.valueOf(i)));
    }
    mIngredients=ing;
    mSteps=stp;
  }

  public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
    @Override
    public Recipe createFromParcel(Parcel in) {
      return new Recipe(in);
    }

    @Override
    public Recipe[] newArray(int size) {
      return new Recipe[size];
    }
  };

  public String getmRecipeName() {
    return mRecipeName;
  }
  public List<ArrayList<String>> getmIngredients(){
    return mIngredients;
  }
  public ArrayList<ArrayList<String>> getmSteps(){
    return mSteps;
  }

  public int getmId() {
    return mId;
  }

  public int getmServings() {
    return mServings;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(mId);
      dest.writeString(mRecipeName);
    Bundle ingredients=new Bundle();
    for(int i=0;i<mIngredients.size();i++){
        ingredients.putStringArrayList(String.valueOf(i),mIngredients.get(i));
    }
    dest.writeBundle(ingredients);
    Bundle steps=new Bundle();
    for(int i=0;i<mSteps.size();i++){
      steps.putStringArrayList(String.valueOf(i),mSteps.get(i));
    }
    dest.writeBundle(steps);
    dest.writeInt(mServings);
  }
}
