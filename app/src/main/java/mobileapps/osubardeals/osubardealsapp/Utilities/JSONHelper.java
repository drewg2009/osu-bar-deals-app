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
}
