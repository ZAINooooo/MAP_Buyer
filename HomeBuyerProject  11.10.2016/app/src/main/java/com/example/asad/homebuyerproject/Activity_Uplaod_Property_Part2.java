package com.example.asad.homebuyerproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity_Uplaod_Property_Part2 extends AppCompatActivity {

    private String Sell, Rent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__uplaod__property__part2);

        //getting data

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Sell = null;
                Rent = null;

            } else {
                Sell = extras.getString("Sell");
                Rent = extras.getString("Rent");

            }
        } else {
            Sell = (String) savedInstanceState.getSerializable("Sell");
            Rent = (String) savedInstanceState.getSerializable("Rent");

        }


        Typecasting();
        OpenFragments();


/*
        String[] list = getResources().getStringArray(R.array.itemsforarea);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coverdarea.setAdapter(adapter);
        */

    }

    private void OpenFragments() {

        if(Sell.equals("Sell")) {

            String SellRemoveItem="SellCommercialLand";
            String SellRemoveItem2="SellIndustrialLand";
            String SellRemoveItem3="SellPlot";

            Fragment_Sell_All_Part2 frag = new Fragment_Sell_All_Part2();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Residential Flat");

            //send data to fragment
            Bundle bundle = new Bundle();
            bundle.putString("SellData",SellRemoveItem );
            bundle.putString("SellData2",SellRemoveItem2 );
            bundle.putString("SellData3",SellRemoveItem3 );
            frag.setArguments(bundle);
            transaction.commit();

        }else if(Sell.equals("Rent")) {

            String RentRemoveItem="RentCommercialLand";
            String RentRemoveItem2="RentIndustrialLand";

            Fragment_Rent_All_Part2 frag = new Fragment_Rent_All_Part2();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.parent, frag, "Residential Flat");
            //send data to fragment
            Bundle bundle = new Bundle();
            bundle.putString("RentData",RentRemoveItem );
            bundle.putString("RentData2",RentRemoveItem2 );
            transaction.commit();
        }


    }

    private void Typecasting() {


    }
}