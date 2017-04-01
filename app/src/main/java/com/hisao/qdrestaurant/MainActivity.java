package com.hisao.qdrestaurant;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.hisao.qdrestaurant.database.DbHelper;
import com.hisao.qdrestaurant.database.QdWebApi;
import com.hisao.qdrestaurant.fragment.CustomerFragment;
import com.hisao.qdrestaurant.fragment.TableFragment;
import com.hisao.qdrestaurant.model.Customer;
import com.hisao.qdrestaurant.model.Table;
import com.hisao.qdrestaurant.service.QdService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CustomerFragment.CustomerFragmentInteractionListener, TableFragment.TableFragmentInteractionListener {

    private DbHelper dbHelper;

    private ArrayList<Customer> customerArrayList;
    private ArrayList<Table> tableArrayList;
    private RelativeLayout rlMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(getApplicationContext());
        rlMain = (RelativeLayout) findViewById(R.id.rl_main);

        Intent intentService = new Intent(this, QdService.class);
        startService(intentService);

        customerArrayList = new ArrayList<>();
        tableArrayList = new ArrayList<>();

        retrieveData();
    }

    private void onDoneRetrieve() {
        if (customerArrayList.size() > 0 && tableArrayList.size() > 0) {
            rlMain.setVisibility(View.GONE);
            showCustomers(customerArrayList);
        }
    }

    private void retrieveData() {
        this.retrieveCustomers();
        this.retrieveTables();
    }

    private void failedRetrieveFromInternet() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Failed")
                .setCancelable(false)
                .setMessage("Failed to load data from web. Check your internet connection and try again")
                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        retrieveData();
                    }
                }).show();
    }

    private void retrieveCustomers() {

        //prior local data
        customerArrayList = dbHelper.getAllCustomers();

        if (customerArrayList.size() > 0) {
            onDoneRetrieve();
            return;
        }

        Call<List<Customer>> listCustomers = QdWebApi.getQdApiInterface().getCustomers();
        listCustomers.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                List<Customer> customers = response.body();

                //save on local data
                for (Customer customer : customers) {
                    dbHelper.addCustomer(customer);
                    customerArrayList.add(customer);
                }
                onDoneRetrieve();
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                failedRetrieveFromInternet();
            }
        });
    }

    private void showCustomers(List<Customer> customers) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, CustomerFragment.newInstance(customers.size(), new ArrayList<>(customers)));
        fragmentTransaction.commit();
    }

    private void retrieveTables() {

        tableArrayList = dbHelper.getAllTables();
        if (tableArrayList.size() > 0) {
            onDoneRetrieve();
            return;
        }


        Call<boolean[]> listTables = QdWebApi.getQdApiInterface().getTables();
        listTables.enqueue(new Callback<boolean[]>() {
            @Override
            public void onResponse(Call<boolean[]> call, Response<boolean[]> response) {
                boolean[] tablesBoolean = response.body();

                int counter = 1;
                Table t;
                for (boolean b : tablesBoolean) {
                    t = new Table();
                    t.setOcupied(b);
                    t.setId(counter++);
                    dbHelper.addTable(t);
                    tableArrayList.add(t);
                }
                onDoneRetrieve();
            }

            @Override
            public void onFailure(Call<boolean[]> call, Throwable t) {
                failedRetrieveFromInternet();
            }
        });
    }

    @Override
    public void onCustomerFragmentInteraction(Customer customer) {

        tableArrayList = dbHelper.getAllTables();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, TableFragment.newInstance(tableArrayList, customer));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onTableFragmentListInteraction(final Table table) {

        if (table.isOcupied()) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Set table")
                    .setCancelable(false)
                    .setMessage("Table already in use")
                    .setPositiveButton("Yes", null).show();
        } else {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Set table")
                    .setCancelable(false)
                    .setMessage("Are you sure you want to set table?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbHelper.setOcupancyTable(true, table.getId());
                            tableArrayList = dbHelper.getAllTables();
                            onBackPressed();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }

    }
}