package edu.cs.birzeit.manger;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class CarManagementActivity {
    private static final String TAG = CarManagementActivity.class.getSimpleName();
    private RequestQueue requestQueue;
    private Context context;

    public CarManagementActivity(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void addCar(String make, String model, int year, double price, final VolleyCallback callback) {
        String url = "http://10.0.2.2/mysql/addCar.php"; // Correct URL to PHP server endpoint

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("make", make);
            jsonObject.put("model", model);
            jsonObject.put("year", year);
            jsonObject.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");
                            callback.onSuccess(status, message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error adding car: " + error.getMessage());
                callback.onFailure(error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    public interface VolleyCallback {
        void onSuccess(String status, String message);
        void onFailure(String errorMessage);
    }





}
