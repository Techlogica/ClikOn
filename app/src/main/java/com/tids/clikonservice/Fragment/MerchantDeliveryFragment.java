package com.tids.clikonservice.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;
import com.tids.clikonservice.R;
import com.tids.clikonservice.Utils.Constant;
import com.tids.clikonservice.Utils.Helper.PrefManager;
import com.tids.clikonservice.adapter.driver.DriverPickupNotificationAdapter;
import com.tids.clikonservice.model.LocationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MerchantDeliveryFragment extends Fragment {

    private View view;
    SharedPreferences sp;
    PrefManager pref;
    String DRIVER_ID = "";

    private RecyclerView rv_orders;

    private DriverPickupNotificationAdapter pickupNotificationAdapter;
    private ArrayList<LocationModel> locationModelArrayList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_merchant_delivery, container, false);

        pref = new PrefManager(getActivity());
        sp = getActivity().getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        DRIVER_ID = sp.getString(Constant.USER_USERID,"");

        rv_orders = view.findViewById(R.id.recycler_view);
        pickupNotificationAdapter = new DriverPickupNotificationAdapter(getActivity(), locationModelArrayList);
        rv_orders.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,false));
        rv_orders.setAdapter(pickupNotificationAdapter);
        loadShopDeliveryProducts();
        loadHomeDeliveryProducts();

        return view;
    }

    private void loadShopDeliveryProducts() {
        try {
            SharedPreferences sp = getActivity().getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String authorization = "Bearer " + sp.getString(Constant.USER_AUTHORIZATION, "");

            String condition = "SELECT DISTINCT CUST_CODE,CUST_NAME,CUST_DEL_ADD_2,CUST_DEL_ADD_3 FROM OM_CUSTOMER " +
                    "WHERE CUST_CODE IN (SELECT CM_CUST_CODE FROM OT_COLLECTION_MODULE WHERE CM_DOC_NO " +
                    "IN (SELECT CTI_CM_DOC_NO FROM OT_CLCTN_ITEMS WHERE CTI_STS_CODE='DRDASN' AND " +
                    "CTI_SHP_CONS_UNIT = 'SHOP' AND CTI_CM_DOC_NO IN " +
                    "(SELECT DVR_CLCN_DOCNO FROM OT_DVR_CLCTN WHERE DVR_DV_SYS_ID IN " +
                    "(SELECT DV_SYS_ID FROM OT_DVR_REQ_ALLCTN WHERE DV_DVR_CODE = '"+DRIVER_ID+"'))))";

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query",condition);
            Log.e("query::",condition);

            AndroidNetworking.post(Constant.BASE_URL + "GetData")
                    .addHeaders("Authorization", authorization)
                    .addJSONObjectBody(jsonObject)
                    .setTag(this)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Response::",response.toString());

                            try {
                                if (response.getBoolean("status")) {
                                    //Get the instance of JSONArray that contains JSONObjects
                                    JSONArray jsonArray = response.getJSONArray("data");
                                    if (jsonArray.length() != 0) {
                                        for (int i = 0; i< jsonArray.length(); i++){
                                            String id = jsonArray.getJSONObject(i).getString("CUST_CODE");
                                            String shop_name = jsonArray.getJSONObject(i).getString("CUST_NAME");
                                            String address1 = jsonArray.getJSONObject(i).getString("CUST_DEL_ADD_2");
                                            String address2 = jsonArray.getJSONObject(i).getString("CUST_DEL_ADD_3");
                                            String address = address1+" "+address2;
                                            String type = "merchant_delivery";
                                            String unit = "shop";

                                            LocationModel locationModel = new LocationModel(id,shop_name,address,type,unit);
                                            locationModelArrayList.add(locationModel);
                                        }
                                        pickupNotificationAdapter.notifyDataSetChanged();
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onError(ANError anError) {
                            showError(anError);
                        }
                    });

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void loadHomeDeliveryProducts() {
        try {
            SharedPreferences sp = getActivity().getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String authorization = "Bearer " + sp.getString(Constant.USER_AUTHORIZATION, "");

            String condition = "SELECT DISTINCT CTI_CM_DOC_NO,CTI_CUSTOMER_NAME,CTI_AREA_CODE,CTI_CNSMR_ADDRSS," +
                    "CTI_CUSTOMER_MOBILE FROM OT_CLCTN_ITEMS WHERE CTI_STS_CODE='DRDASN' AND " +
                    "CTI_SHP_CONS_UNIT = 'CONSUMER' AND CTI_CM_DOC_NO IN " +
                    "(SELECT DVR_CLCN_DOCNO FROM OT_DVR_CLCTN WHERE DVR_DV_SYS_ID IN " +
                    "(SELECT DV_SYS_ID FROM OT_DVR_REQ_ALLCTN WHERE DV_DVR_CODE = '"+DRIVER_ID+"'))";

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("query",condition);
            Log.e("query::",condition);

            AndroidNetworking.post(Constant.BASE_URL + "GetData")
                    .addHeaders("Authorization", authorization)
                    .addJSONObjectBody(jsonObject)
                    .setTag(this)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("Response::",response.toString());

                            try {
                                if (response.getBoolean("status")) {
                                    //Get the instance of JSONArray that contains JSONObjects
                                    JSONArray jsonArray = response.getJSONArray("data");
                                    if (jsonArray.length() != 0) {
                                        for (int i = 0; i< jsonArray.length(); i++){
                                            String id = jsonArray.getJSONObject(i).getString("CTI_CM_DOC_NO");
                                            String shop_name = jsonArray.getJSONObject(i).getString("CTI_CUSTOMER_NAME");
                                            String address1 = jsonArray.getJSONObject(i).getString("CTI_AREA_CODE");
                                            String address2 = jsonArray.getJSONObject(i).getString("CTI_CNSMR_ADDRSS");
                                            String mobile_number = jsonArray.getJSONObject(i).getString("CTI_CUSTOMER_MOBILE");
                                            String address = address1+"\n"+address2+"\n"+mobile_number;
                                            String type = "merchant_delivery";
                                            String unit = "consumer";

                                            LocationModel locationModel = new LocationModel(id,shop_name,address,type,unit);
                                            locationModelArrayList.add(locationModel);
                                        }
                                        pickupNotificationAdapter.notifyDataSetChanged();
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onError(ANError anError) {
                            showError(anError);
                        }
                    });

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void showError(ANError anError) {
        Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
        Log.e("Error :: ", anError.getErrorBody());
    }

    private void customToast(String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setBackgroundTint(getResources().getColor(R.color.colorPrimary)).show();
    }
}