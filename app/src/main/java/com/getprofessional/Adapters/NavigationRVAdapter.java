package com.getprofessional.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.getprofessional.Activity.LoginActivity;
import com.getprofessional.Activity.MainActivity;
import com.getprofessional.Fragments.AboutUsFragment;
import com.getprofessional.Fragments.HelpFragment;
import com.getprofessional.Fragments.MainFragment;
import com.getprofessional.Fragments.MyOrdersFragment;
import com.getprofessional.R;

import java.util.ArrayList;

public class NavigationRVAdapter extends RecyclerView.Adapter<NavigationRVAdapter.MyViewHolder> {

    private ArrayList<String> arrayList;
    private Context mContext;
    private DrawerLayout drawerLayout;

    public NavigationRVAdapter(ArrayList<String> arrayList, Context mContext,DrawerLayout drawerLayout){
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.drawerLayout = drawerLayout;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_row_layout,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.textView.setText(arrayList.get(position));
        myViewHolder.contentHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(position == 0){
                    MainFragment mainFragment = new MainFragment();
                    ((MainActivity)mContext).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentLayout,mainFragment,"MainFragment")
                            .addToBackStack(null)
                            .commit();
                }else if( position == 1){
                    MyOrdersFragment myOrdersFragment = new MyOrdersFragment();
                    ((MainActivity)mContext).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentLayout,myOrdersFragment,"MainFragment")
                            .addToBackStack(null)
                            .commit();
                }else if( position == 2){
                    HelpFragment helpFragment = new HelpFragment();
                    ((MainActivity)mContext).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentLayout,helpFragment,"MainFragment")
                            .addToBackStack(null)
                            .commit();
                }else if ( position == 3){
                    AboutUsFragment aboutUsFragment = new AboutUsFragment();
                    ((MainActivity)mContext).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentLayout,aboutUsFragment,"MainFragment")
                            .addToBackStack(null)
                            .commit();
                }else if ( position == 4){
                    Intent i = new Intent();
                    i.setClass(mContext.getApplicationContext(), LoginActivity.class);
                    mContext.startActivity(i);
                    ((Activity)mContext).finish();
                    mContext.stopService(i);
                    SharedPreferences sharedPref = mContext.getSharedPreferences("SESSION",Context.MODE_PRIVATE);
                    sharedPref.edit().clear().apply();
                }

                 drawerLayout = drawerLayout.findViewById(R.id.drawer_layout);
                 drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull MyViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RelativeLayout contentHolder;

        public MyViewHolder(View view) {
            super(view);

            textView = (TextView)view.findViewById(R.id.navTextItems);
            contentHolder = (RelativeLayout)view.findViewById(R.id.nav_conten_holder);

        }
    }
}

