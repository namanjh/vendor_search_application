package com.surfarrayinc.surf.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.surfarrayinc.surf.R;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private TextView vname;
    private ImageView vimage;
    private TextView vcategory;
    private TextView vrating;
    private TextView vcontact;
    private TextView vlocation;
    private TextView vdistance;
    private TextView vdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        vname = findViewById(R.id.detailVendorName);
        vimage = findViewById(R.id.detailVendorImage);
        vcategory = findViewById(R.id.detailVendorCategory);
        vrating = findViewById(R.id.detailVendorRating);
        vcontact = findViewById(R.id.detailVendorContact);
        vlocation = findViewById(R.id.detailVendorLocation);
        vdistance = findViewById(R.id.detailVendorDistance);
        vdesc = findViewById(R.id.detailVendorDescription);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        String name =  String.valueOf(b.get("name"));
        String imageurl = String.valueOf(b.get("image"));
        String categoty = String.valueOf(b.get("category"));
        String rating = String.valueOf(b.get("rating"));
        String contact = String.valueOf(b.get("contact"));
        String location = String.valueOf(b.get("location"));
        String distance = String.valueOf(b.get("distance"));
        String desc = String.valueOf(b.get("description"));

        vname.setText(name);

        Picasso.get().load(imageurl).into(vimage);

        vcategory.setText("Category: " + categoty);
        vrating.setText("Rated: " + rating + "/5");
        vcontact.setText("Phone: "+ contact);
        vlocation.setText("Location" + location);
        vdistance.setText("Distance: " + distance);
        vdesc.setText(desc);

    }
}
