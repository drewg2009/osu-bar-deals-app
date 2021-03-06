package mobileapps.osubardeals.osubardealsapp.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drewgallagher on 3/7/18.
 */

public class JSONHelper {

    public static List<JSONObject> getJSONArray(String response){
        List<JSONObject> list = new ArrayList<>();
        try {
            JSONArray jsonArr = new JSONArray(response);
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                list.add(jsonObj);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return list;
    }

    public static JSONObject getOneJSONObject(String response){
        JSONObject obj = null;
        try {
            JSONArray jsonArr = new JSONArray(response);
            obj = jsonArr.getJSONObject(0);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return obj;
    }

    public static JSONObject getJSONObject(String response){
        JSONObject obj = null;
        try {
            obj = new JSONObject(response);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public static String getJSONField(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
