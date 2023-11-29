package com.getprofessional.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getprofessional.Activity.MainActivity;
import com.getprofessional.Fragments.ComputerBasicRepairingFragment;
import com.getprofessional.Fragments.ComputerMajorRepairingFragment;
import com.getprofessional.Fragments.ComputerServicingFragment;
import com.getprofessional.Fragments.LaptopBasicRepairingFragment;
import com.getprofessional.Fragments.LaptopMajorRepairingFragment;
import com.getprofessional.Fragments.LaptopServicingFragment;
import com.getprofessional.R;
import com.getprofessional.models.GPServices;

public class MainFragmentRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;

    public MainFragmentRVAdapter(Context _mContext) {
        this.mContext = _mContext;
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        if (position == 1 || position == 3 || position == 5) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return GPServices._contentText.length;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1 || viewType == 3 || viewType == 5) {
            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fragment_row_layout, parent, false);
            ViewHolder0 viewHolder0 = new ViewHolder0(view1);
            return viewHolder0;
        } else {
            View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fragment_row_layout_2, parent, false);
            ViewHolder2 viewHolder2 = new ViewHolder2(view2);
            return viewHolder2;
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 1:
                ViewHolder0.bindView(position);
                break;

            case 2:
                ViewHolder2.bindView(position);
                break;
        }

        if (holder.getItemViewType() == 1) {

            ViewHolder0.contentHodler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (position == 1) {
                        LaptopBasicRepairingFragment lbrFrag = new LaptopBasicRepairingFragment();
                        ((MainActivity) mContext).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentLayout, lbrFrag, "MainFragment")
                                .addToBackStack(null)
                                .commit();
                    } else if (position == 3) {
                        ComputerServicingFragment csFrag = new ComputerServicingFragment();
                        ((MainActivity) mContext).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentLayout, csFrag, "MainFragment")
                                .addToBackStack(null)
                                .commit();
                    } else if (position == 5) {
                        ComputerMajorRepairingFragment cmrFrag = new ComputerMajorRepairingFragment();
                        ((MainActivity) mContext).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentLayout, cmrFrag, "MainFragment")
                                .addToBackStack(null)
                                .commit();
                    } else {

                    }
                }
            });

        } else {

            ViewHolder2.contentHodler2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (position == 0) {
                        LaptopServicingFragment lsFrag = new LaptopServicingFragment();
                        ((MainActivity) mContext).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentLayout, lsFrag, "MainFragment")
                                .addToBackStack(null)
                                .commit();
                    } else if (position == 2) {
                        LaptopMajorRepairingFragment lmsFrag = new LaptopMajorRepairingFragment();
                        ((MainActivity) mContext).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentLayout, lmsFrag, "MainFragment")
                                .addToBackStack(null)
                                .commit();
                    } else if (position == 4) {
                        ComputerBasicRepairingFragment cbrFrag = new ComputerBasicRepairingFragment();
                        ((MainActivity) mContext).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentLayout, cbrFrag, "MainFragment")
                                .addToBackStack(null)
                                .commit();
                    } else {

                    }
                }
            });

        }

    }

    static class ViewHolder0 extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static TextView contentText;
        private static ImageView contentImage;
        private static RelativeLayout contentHodler;

        public ViewHolder0(View itemView) {
            super(itemView);
            contentText = itemView.findViewById(R.id.rv_text_main_fragment);
            contentImage = itemView.findViewById(R.id.rv_image_main_fragment);
            contentHodler = itemView.findViewById(R.id.main_fragment_content_holder);
            itemView.setOnClickListener(this);
        }

        public static void bindView(int position) {
            contentText.setText(GPServices._contentText[position]);
            contentImage.setImageResource(GPServices._contentImage[position]);
        }

        @Override
        public void onClick(View view) {


        }
    }

    static class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static TextView contentText2;
        private static ImageView contentImage2;
        private static RelativeLayout contentHodler2;

        public ViewHolder2(View itemView) {
            super(itemView);
            contentText2 = itemView.findViewById(R.id.rv_text_main_fragment_2);
            contentImage2 = itemView.findViewById(R.id.rv_image_main_fragment_2);
            contentHodler2 = itemView.findViewById(R.id.main_fragment_content_holder_2);
            itemView.setOnClickListener(this);
        }

        public static void bindView(int position) {
            contentText2.setText(GPServices._contentText[position]);
            contentImage2.setImageResource(GPServices._contentImage[position]);
        }

        @Override
        public void onClick(View view) {

        }
    }
}


//Single Layout Code->
//public class MainFragmentRVAdapter extends RecyclerView.Adapter<MainFragmentRVAdapter.MyViewHolder> {
//
//    @NonNull
//    @Override
//    public MainFragmentRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fragment_row_layout,parent,false);
//        MyViewHolder viewHolder = new MyViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
//                //myViewHolder.bindView(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return GPServices._contentText.length;
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        private TextView contentText;
//        private ImageView contentImage;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            contentText = (TextView)itemView.findViewById(R.id.rv_text_main_fragment);
//            contentImage = (ImageView)itemView.findViewById(R.id.rv_image_main_fragment);
//            itemView.setOnClickListener(this);
//        }
//
//        public void bindView(int position){
//          contentText.setText(GPServices._contentText[position]);
//          contentImage.setImageResource(GPServices._contentImage[position]);
//        }
//
//        @Override
//        public void onClick(View view) {
//
//        }
//    }
//}
