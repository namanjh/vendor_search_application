package com.surfarrayinc.surf.data;

import android.content.Context;

import com.surfarrayinc.surf.model.vendor;

import java.util.List;

public class DetailRecyclerAdapater {

    private Context context;
    private List<vendor> vendorList;

    public DetailRecyclerAdapater(Context context, List<vendor> vendorList) {
        this.context = context;
        this.vendorList = vendorList;
    }
}
