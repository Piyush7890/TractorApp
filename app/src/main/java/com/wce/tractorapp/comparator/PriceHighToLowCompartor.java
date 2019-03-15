package com.wce.tractorapp.comparator;

import com.wce.tractorapp.model.RenterData;

import java.util.Comparator;

public class PriceHighToLowCompartor implements Comparator<RenterData> {
    @Override
    public int compare(RenterData o1, RenterData o2) {
        return o1.getRent()>o2.getRent()?1:0;
    }
}
