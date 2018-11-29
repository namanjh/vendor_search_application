package com.surfarrayinc.surf.data;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.surfarrayinc.surf.Activities.DetailActivity;
import com.surfarrayinc.surf.Activities.MainActivity;
import com.surfarrayinc.surf.Activities.ShowListActivity;
import com.surfarrayinc.surf.R;
import com.surfarrayinc.surf.model.vendor;

import java.util.List;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<vendor> vendorList;

    public ListRecyclerAdapter(Context context, List<vendor> vendorList) {
        this.context = context;
        this.vendorList = vendorList;
    }

    @NonNull
    @Override
    public ListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate the structure of the view in this section when the
        //view is created
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.post_row, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRecyclerAdapter.ViewHolder viewHolder, int i) {
        //bind the data into the view
        final vendor vend = vendorList.get(i);
        String imageurl = null;

        viewHolder.vname.setText(vend.getVname());
        viewHolder.vcategory.setText("Category: " + vend.getVcategory());
        viewHolder.vlocation.setText(vend.getVlocation());
        viewHolder.vcontact.setText("Contact: " + vend.getvContact());
        viewHolder.vrating.setText(vend.getRating() + "/5");

        imageurl = vend.getVimage();
        Picasso.get().load(imageurl).into(viewHolder.vimage);

        //added later
        final String vdist = String.valueOf(vend.getVdist());
        final String vdesc = String.valueOf(vend.getVdesc());

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("name",vend.getVname());
                intent.putExtra("image",vend.getVimage());
                intent.putExtra("category",vend.getVcategory());
                intent.putExtra("rating",vend.getRating());
                intent.putExtra("contact",vend.getvContact());
                intent.putExtra("location",vend.getVlocation());
                intent.putExtra("distance",vdist);
                intent.putExtra("description",vdesc);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vendorList.size();
    }


    //class to hold the view
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView vimage;
        private TextView vname;
        private TextView vcategory;
        private TextView vlocation;
        private TextView vcontact;
        private TextView vrating;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView, final Context ctx) {
            super(itemView);
            context = ctx;
            vimage = itemView.findViewById(R.id.imagePostID);
            vname = itemView.findViewById(R.id.namePostID);
            vcategory = itemView.findViewById(R.id.categoryPostID);
            vlocation = itemView.findViewById(R.id.locationPostID);
            vcontact = itemView.findViewById(R.id.contactPostID);
            vrating = itemView.findViewById(R.id.ratingPostID);
            linearLayout = itemView.findViewById(R.id.linearLayoutPostID);

        }
    }
}
