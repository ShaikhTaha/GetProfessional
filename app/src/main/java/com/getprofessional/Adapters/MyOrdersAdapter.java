package com.getprofessional.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getprofessional.Fragments.MyOrdersFragment;
import com.getprofessional.R;
import com.getprofessional.models.OrderHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */

public class MyOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static Context mCtx;
    private static List<OrderHistory> orderHistoryList ;

    public MyOrdersAdapter(Context mCtx, List<OrderHistory> orderHistoryList) {
        this.mCtx = mCtx;
        this.orderHistoryList = orderHistoryList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_row_layout, parent, false);
        ProductViewHolder viewHolder0 = new ProductViewHolder(view1);
        return viewHolder0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ProductViewHolder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        static TextView emp_name;
        static TextView service_name;
        static TextView order_date;
        static TextView order_id;
        static TextView cust_id;
        static TextView order_amt;
        static ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            order_id = itemView.findViewById(R.id.order_id_number);
            cust_id =itemView.findViewById(R.id.cust_id_number);
            imageView = itemView.findViewById(R.id.employee_image);
            emp_name = itemView.findViewById(R.id.employee_name_text);
            service_name = itemView.findViewById(R.id.service_name);
            order_date = itemView.findViewById(R.id.date_and_time_value);
            order_amt = itemView.findViewById(R.id.amount_value);
        }

        public static void bindView(int position){
            OrderHistory orders = orderHistoryList.get(position);

            order_id.setText(Integer.toString(orders.getOrder_id()));
            cust_id.setText(Integer.toString(orders.getCust_id()));
            Glide.with(mCtx)
                    .load(orders.getEmp_image())
                    .into(imageView);
            emp_name.setText(orders.getEmp_name());
            service_name.setText(orders.getService_name());
            order_date.setText(orders.getOrder_date());
            order_amt.setText(Double.toString(orders.getOrder_amt()));
        }
    }
}
