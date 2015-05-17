package com.wapmadrid.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ismael on 17/05/2015.
 */
public class Walker implements Parcelable {

    private String profileImage;
    private String firstName;
    private String lastName;
    private String displayName;
    private boolean sex;
    private String city;
    private String about;
    private String email;
    private String telephone;
    private String birthDate;
    private String height;
    private String smoker;
    private String alcohol;
    private ArrayList<String> weight_value;
    private ArrayList<String> weight_date;
    private ArrayList<String> weight_imc;
    private ArrayList<String> diet_value;
    private ArrayList<String> diet_date;
    private ArrayList<String> exercise_value;
    private ArrayList<String> exercise_date;
    private ArrayList<String> stats_distance;
    private ArrayList<String> stats_date;
    private ArrayList<String> stats_kcal;

    public Walker(JSONObject walker) {
        weight_value = new ArrayList<>();
        weight_date = new ArrayList<>();
        weight_imc = new ArrayList<>();
        diet_value = new ArrayList<>();
        diet_date = new ArrayList<>();
        exercise_value = new ArrayList<>();
        exercise_date = new ArrayList<>();
        stats_distance = new ArrayList<>();
        stats_date = new ArrayList<>();
        stats_kcal = new ArrayList<>();
        try {
            profileImage = walker.getString("profileImage");
            firstName = walker.getString("firstName");
            lastName = walker.getString("lastName");
            displayName = walker.getString("displayName");
            sex = walker.getBoolean("sex");
            city = walker.getString("city");
            about = walker.getString("about");
            email = walker.getString("email");
            telephone = walker.getString("telephone");

            birthDate = setDate(walker.getString("birthDate"));
            height = walker.getString("height");
            smoker = walker.getString("alcohol");
            alcohol = walker.getString("alcohol");
            JSONArray weight_array = new JSONArray(walker.getString("weight"));
            JSONArray diet_array = new JSONArray(walker.getString("diet"));
            JSONArray exercise_array = new JSONArray(walker.getString("exercise"));
            JSONArray stats_array = new JSONArray(walker.getString("stats"));
            for (int i = 0; i < weight_array.length(); i++) {
                JSONObject weight = weight_array.getJSONObject(i);
                weight_value.add(i, weight.getString("value"));
                weight_date.add(i, setDate(weight.getString("date")));
                weight_imc.add(i, weight.getString("imc"));
            }
            for (int i = 0; i < diet_array.length(); i++) {
                JSONObject diet = diet_array.getJSONObject(i);
                diet_value.add(i, diet.getString("value"));
                diet_date.add(i, setDate(diet.getString("date")));
            }
            for (int i = 0; i < exercise_array.length(); i++) {
                JSONObject exercise = exercise_array.getJSONObject(i);
                exercise_value.add(i, exercise.getString("value"));
                exercise_date.add(i, setDate(exercise.getString("date")));
            }
            for (int i = 0; i < stats_array.length(); i++) {
                JSONObject stats = stats_array.getJSONObject(i);
                stats_distance.add(i, stats.getString("distance"));
                stats_date.add(i, setDate(stats.getString("date")));
                stats_kcal.add(i,stats.getString("kcal"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String setDate(String date) {
        String aux = date.substring(0, 10);
        String auxDate[] = aux.split("-");
        return auxDate[2] + "/" + auxDate[1] + "/" + auxDate[0];
    }

    public Walker(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profileImage);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(displayName);
        dest.writeString(Boolean.toString(sex));
        dest.writeString(city);
        dest.writeString(about);
        dest.writeString(email);
        dest.writeString(telephone);
        dest.writeString(birthDate);
        dest.writeString(height);
        dest.writeString(smoker);
        dest.writeString(alcohol);
        dest.writeList(weight_date);
        dest.writeList(weight_value);
        dest.writeList(weight_imc);
        dest.writeList(diet_date);
        dest.writeList(diet_value);
        dest.writeList(exercise_date);
        dest.writeList(exercise_value);
        dest.writeList(stats_date);
        dest.writeList(stats_distance);
        dest.writeList(stats_kcal);
    }

    public void readFromParcel(Parcel in) {
        profileImage = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        displayName = in.readString();
        sex = Boolean.getBoolean(in.readString());
        city = in.readString();
        about = in.readString();
        email = in.readString();
        telephone = in.readString();
        birthDate = in.readString();;
        height = in.readString();
        smoker = in.readString();
        alcohol = in.readString();
        in.readList(weight_date, null);
        in.readList(weight_value, null);
        in.readList(weight_imc, null);
        in.readList(diet_date, null);
        in.readList(diet_value, null);
        in.readList(exercise_date, null);
        in.readList(exercise_value, null);
        in.readList(stats_date, null);
        in.readList(stats_distance, null);
        in.readList(stats_kcal, null);
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSmoker() {
        return smoker;
    }

    public void setSmoker(String smoker) {
        this.smoker = smoker;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public ArrayList<String> getWeight_value() {
        return weight_value;
    }

    public void setWeight_value(ArrayList<String> weight_value) {
        this.weight_value = weight_value;
    }

    public ArrayList<String> getWeight_date() {
        return weight_date;
    }

    public void setWeight_date(ArrayList<String> weight_date) {
        this.weight_date = weight_date;
    }

    public ArrayList<String> getWeight_imc() {
        return weight_imc;
    }

    public void setWeight_imc(ArrayList<String> weight_imc) {
        this.weight_imc = weight_imc;
    }

    public ArrayList<String> getDiet_value() {
        return diet_value;
    }

    public void setDiet_value(ArrayList<String> diet_value) {
        this.diet_value = diet_value;
    }

    public ArrayList<String> getDiet_date() {
        return diet_date;
    }

    public void setDiet_date(ArrayList<String> diet_date) {
        this.diet_date = diet_date;
    }

    public ArrayList<String> getExercise_value() {
        return exercise_value;
    }

    public void setExercise_value(ArrayList<String> exercise_value) {
        this.exercise_value = exercise_value;
    }

    public ArrayList<String> getExercise_date() {
        return exercise_date;
    }

    public void setExercise_date(ArrayList<String> exercise_date) {
        this.exercise_date = exercise_date;
    }

    public ArrayList<String> getStats_distance() {
        return stats_distance;
    }

    public void setStats_distance(ArrayList<String> stats_distance) {
        this.stats_distance = stats_distance;
    }

    public ArrayList<String> getStats_date() {
        return stats_date;
    }

    public void setStats_date(ArrayList<String> stats_date) {
        this.stats_date = stats_date;
    }

    public ArrayList<String> getStats_kcal() {
        return stats_kcal;
    }

    public void setStats_kcal(ArrayList<String> stats_kcal) {
        this.stats_kcal = stats_kcal;
    }


    @Override
    public int describeContents() {
        return 0;
    }



}
