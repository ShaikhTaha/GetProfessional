package com.getprofessional.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.getprofessional.Adapters.MyOrdersAdapter;
import com.getprofessional.BackGrounds.UserDetails;
import com.getprofessional.R;

/**
 * A simple {@link Fragment} subclass.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.getprofessional.models.GP_BG_Data;
import com.getprofessional.models.OrderHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyOrdersFragment extends Fragment {

    //this is the JSON Data URL
    private static final String URL_ORDERS = "http://getprofessional.in/odrw3s.php";

    //a list to store all the products
    List<OrderHistory> orderHistoryList;

    RecyclerView mRecyclerView;
    MyOrdersAdapter mAdapter;

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        orderHistoryList = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_orders_recycler_view);
        setAdapter(orderHistoryList);

        UserDetails insertOrders = new UserDetails(getActivity());
        GP_BG_Data DB = new GP_BG_Data(getActivity());
        String cust_id = DB.getCustID();
        insertOrders.execute("orders",cust_id);
        Cursor cursor = DB.getOrders();

        if(cursor.getCount()==0){
            /* NO ORDERS FOUND HERE */
        }else {
            while (cursor.moveToNext()) {
                OrderHistory orders = new OrderHistory(cursor.getInt(1), cursor.getInt(2)
                        , cursor.getString(3), cursor.getString(4), cursor.getString(5)
                        , cursor.getString(6), cursor.getDouble(7));
                orderHistoryList.add(orders);
            }
            cursor.close();
        }
        setAdapter(orderHistoryList);

        RecyclerView.LayoutManager myOrdersLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(myOrdersLayoutManager);

        return view;
    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ORDERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray order = new JSONArray(response);

                            //traversing through all the object
                            for (int i=0; i<order.length() ;i++) {

                                //getting product object from json array
                                JSONObject orderObject = order.getJSONObject(i);

                                int order_id = orderObject.getInt("order_id");
                                int cust_id = orderObject.getInt("cust_id");
                                String emp_image = orderObject.getString("emp_image");
                                String emp_name = orderObject.getString("emp_name");
                                String service_name = orderObject.getString("service_name");
                                String order_date = orderObject.getString("order_date");
                                double order_amt = orderObject.getDouble("order_amt");



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setAdapter(orderHistoryList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    public void setAdapter(List<OrderHistory> results){
        mAdapter = new MyOrdersAdapter(this.getActivity(),results);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
