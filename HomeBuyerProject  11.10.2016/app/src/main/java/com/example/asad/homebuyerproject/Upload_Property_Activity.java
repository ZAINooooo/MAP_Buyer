package com.example.asad.homebuyerproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import static android.R.attr.onClick;
import static com.example.asad.homebuyerproject.R.id.asad;
import static com.example.asad.homebuyerproject.R.id.spinner;

public class Upload_Property_Activity extends AppCompatActivity {

    private ImageView mUploadPhotos;
    private Spinner coverdarea;
    private EditText mTestingValue;
    private NumberFormat numberformat;
    private String SellRent, ResidentialCommercial, PropertyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__property_);

        //getting data

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                SellRent = null;
                ResidentialCommercial = null;
                PropertyType = null;
            } else {
                SellRent = extras.getString("SellRentData");
                ResidentialCommercial = extras.getString("ResidentialCommercialData");
                PropertyType = extras.getString("Type");
            }
        } else {
            SellRent = (String) savedInstanceState.getSerializable("SellRentData");
            ResidentialCommercial = (String) savedInstanceState.getSerializable("ResidentialCommercialData");
            PropertyType = (String) savedInstanceState.getSerializable("Type");
        }


        Toast.makeText(getApplication(),"sellrent:"+SellRent,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplication(),"residential commercial:"+ResidentialCommercial,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplication(),"propertytype:"+PropertyType,Toast.LENGTH_SHORT).show();

        Typecasting();
        OpenFragments();
/*
        String[] list = getResources().getStringArray(R.array.itemsforarea);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coverdarea.setAdapter(adapter);
        */




/*
mTestingValue.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        double d;
        String Valuees=mTestingValue.getText().toString();
        d = Double.parseDouble(Valuees);
        NumberFormat defaultFormat = NumberFormat.getNumberInstance(Locale.US);
        Toast.makeText(Upload_Property_Activity.this,defaultFormat.format(d),Toast.LENGTH_SHORT).show();

    }
});
*/
      /*  mUploadPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gallery_Activity_Fragment frag = new Gallery_Activity_Fragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.Replace_Gallery_Image, frag, "Upload Photos");
                transaction.addToBackStack(null);
                transaction.commit();
                Toast.makeText(getApplication(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });
*/
    }

    private void OpenFragments() {
        //  Toast.makeText(getApplication(),"before",Toast.LENGTH_SHORT).show();

        if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Flat")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Sell_Residential_Flat frag = new Fragment_Sell_Residential_Flat();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Residential Flat");
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Plot")) {
            Toast.makeText(getApplication(), "From Sell", Toast.LENGTH_SHORT).show();
            Fragment_Sell_Residential_Plot frag = new Fragment_Sell_Residential_Plot();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Plot");
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Builder Floor")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Sell_Residential_BuilderFloor frag = new Fragment_Sell_Residential_BuilderFloor();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Builder Floor");
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("House")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Sell_Residential_House frag = new Fragment_Sell_Residential_House();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "House");
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Pent House")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Sell_Residential_Penthouse frag = new Fragment_Sell_Residential_Penthouse();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Pent House");
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Studio Apartment")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Sell_Residential_StudioApartment frag = new Fragment_Sell_Residential_StudioApartment();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Studio Apartment");
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Villa")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Sell_Residential_Villa frag = new Fragment_Sell_Residential_Villa();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Villa");
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Office Space")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Sell_Commercial_Officespace frag = new Fragment_Sell_Commercial_Officespace();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Office Space");
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Showroom")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Sell_Commercial_Showroom frag = new Fragment_Sell_Commercial_Showroom();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Showroom");
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Shop")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Sell_Commercial_Shop frag = new Fragment_Sell_Commercial_Shop();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Shop");
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Commercial Land")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Sell_Commercial_CommercialLand frag = new Fragment_Sell_Commercial_CommercialLand();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Commercial Land");
            transaction.commit();

        } else if (SellRent.equals("Sell") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Industrial Land")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Sell_Commercial_IndustrialLand frag = new Fragment_Sell_Commercial_IndustrialLand();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Shop");
            transaction.commit();

        }


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Flat")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Rent_Residential_Flat frag = new Fragment_Rent_Residential_Flat();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Residential Flat");
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("PG Hostel")) {

            Toast.makeText(getApplication(), "From Rent", Toast.LENGTH_SHORT).show();
            Fragment_Rent_Residential_Pghostel frag = new Fragment_Rent_Residential_Pghostel();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Pg Hostel");
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Builder Floor")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Rent_Residential_BuilderFloor frag = new Fragment_Rent_Residential_BuilderFloor();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Builder Floor");
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("House")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Rent_Residential_House frag = new Fragment_Rent_Residential_House();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "House");
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Pent House")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Rent_Residential_Penthouse frag = new Fragment_Rent_Residential_Penthouse();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Pent House");
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Studio Apartment")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Rent_Residential_StudioApartment frag = new Fragment_Rent_Residential_StudioApartment();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Studio Apartment");
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Residential") && PropertyType.equals("Villa")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Rent_Residential_Villa frag = new Fragment_Rent_Residential_Villa();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Villa");
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Office Space")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Rent_Commercial_Officespace frag = new Fragment_Rent_Commercial_Officespace();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Office Space");
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Showroom")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Rent_Commercial_Showroom frag = new Fragment_Rent_Commercial_Showroom();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Showroom");
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Shop")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Rent_Commercial_Shop frag = new Fragment_Rent_Commercial_Shop();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Shop");
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Commercial Land")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
            Fragment_Rent_Commercial_CommercialLand frag = new Fragment_Rent_Commercial_CommercialLand();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Commercial Land");
            transaction.commit();

        } else if (SellRent.equals("Rent") && ResidentialCommercial.equals("Commercial") && PropertyType.equals("Industrial Land")) {
            Toast.makeText(getApplication(), "After", Toast.LENGTH_SHORT).show();
           Fragment_Rent_Commercial_IndustrialLand frag = new Fragment_Rent_Commercial_IndustrialLand();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Shop");
            transaction.commit();

        }


    }


    public void onStart() {
        super.onStart();
           /* mTestingValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
               public void onFocusChange(View v,boolean hasFocus) {
                    if(hasFocus){
                        DateDialog dialog= new DateDialog(v);
                        FragmentTransaction ft=getFragmentManager().beginTransaction();
                        dialog.show(ft,"DatePicker");
                    }
                }
            });
            */

    }


    private void Typecasting() {

        //  mUploadPhotos = (ImageView) findViewById(R.id.Upload_Photos);
        //  coverdarea = (Spinner) findViewById(R.id.spinnerArea);
        mTestingValue = (EditText) findViewById(R.id.TestingValue);
    }
   /* @Override
    public void onBackPressed() {

        //  startActivity(new Intent(this,Residential_Commercial_Activity.class));
    }
    */


}
