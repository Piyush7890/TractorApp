package com.wce.tractorapp.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wce.tractorapp.R;
import com.wce.tractorapp.model.Sort;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FilterDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    TextView priceHighToLow, priceLowToHigh, newestFirst,oldestFirst, selectedOption;
    OnOptionSelectedListener listener;
    Button apply;
    Button cancel;
    int sortType;


    public void setListener(OnOptionSelectedListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(selectedOption.getId()==v.getId())
            return;
        resetDrawable(selectedOption);
        switch (v.getId())


        {
            case R.id.cancel_btn:{
                dismiss();
                break;
            }
            case R.id.apply_btn:
            {
                Sort sort=new Sort(sortType,locationSpinner.getSelectedItem().toString(),typeSpinner.getSelectedItem().toString());
                listener.onSortOptionSelected(sort);
            }
            case R.id.price_h_l:
            {
                setDrawable(priceHighToLow,check);
                selectedOption = priceHighToLow;
                sortType=0;
                break;
            }
            case R.id.price_l_h:
            {
                setDrawable(priceLowToHigh,check);
                selectedOption = priceLowToHigh;
                sortType=1;

                break;
            }
            case R.id.oldest_first:
            {
                setDrawable(oldestFirst,check);
                selectedOption = oldestFirst;
                sortType=2;

                break;
            }

            case R.id.newest_first:
            {
                setDrawable(newestFirst,check);
                selectedOption = newestFirst;
                sortType=3;

                break;
            }
        }


    }

    private void setDrawable(TextView view ,Drawable d)
    {
        view.setTypeface(view.getTypeface(), Typeface.BOLD);
        view.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_black_24dp,0);

    }
    private void resetDrawable(TextView view)
    {
        view.setTypeface(null, Typeface.NORMAL);

        view.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);

    }
    public enum FilterOption
    {
        PRICE_HIGH_TO_LOW(0),
        PRICE_LOW_TO_HIGH(1),
        NEWEST_FIRST(2),
        OLDEST_FIRST(3);
        private final int value;
        private FilterOption(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    public interface OnOptionSelectedListener
    {
        void onSortOptionSelected(Sort sort);
        void onLocationSelected(String location);
        void onTypeSelected(String type);
    }
    Spinner locationSpinner, typeSpinner;

    FilterOption option;
    Drawable check;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    public static FilterDialogFragment newInstance() {
        return new FilterDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_dialog,container,false);
        oldestFirst=view.findViewById(R.id.oldest_first);

        typeSpinner = view.findViewById(R.id.type_spinner);
        oldestFirst.setOnClickListener(this);
        newestFirst=view.findViewById(R.id.newest_first);
        newestFirst.setOnClickListener(this);
        apply=view.findViewById(R.id.apply_btn);
        apply.setOnClickListener(this);

        cancel=view.findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(this);
        priceHighToLow=view.findViewById(R.id.price_h_l);
        priceHighToLow.setOnClickListener(this);

        priceLowToHigh=view.findViewById(R.id.price_l_h);
        priceLowToHigh.setOnClickListener(this);
        int value = getArguments().getInt("filterOption");
        sortType=value;
        switch (value)
        {
            case 0:
            {
                selectedOption = priceHighToLow;
                setDrawable(priceHighToLow, check);
                break;
            }
            case 1:
            {                selectedOption = priceLowToHigh;

                setDrawable(priceLowToHigh, check);
                break;
            }
            case 2:
            {
                             selectedOption = newestFirst;

                    setDrawable(newestFirst, check);

                break;
            }
            case 3:
            {
                selectedOption = oldestFirst;
                setDrawable(oldestFirst, check);

                break;
            }

        }
        locationSpinner = view.findViewById(R.id.location_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.Type, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter2);
        locationSpinner.setAdapter(adapter);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog =  super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final BottomSheetDialog d = (BottomSheetDialog)dialog;

                FrameLayout bottomSheet = d.findViewById(R.id.design_bottom_sheet) ;
                bottomSheet.setBackground(bottomSheet.getContext().getResources().getDrawable(R.drawable.bottom_sheet_bg));
                       BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View view, int i) {
                        if (i == 5) {
                            d.cancel();
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View view, float v) {
                        Log.d("ONLSIDE", "onSlide: "+v);
                        Log.d("HEIGHT", "onHEIGHT"+ view.getTranslationY());
                    }
                });
            }
        });
        return dialog;
    }
}
