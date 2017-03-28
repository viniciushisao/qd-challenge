package com.hisao.qdrestaurant;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hisao.qdrestaurant.database.DbHelper;
import com.hisao.qdrestaurant.database.QdWebApi;
import com.hisao.qdrestaurant.fragment.CustomerFragment;
import com.hisao.qdrestaurant.model.Customer;
import com.hisao.qdrestaurant.model.Table;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CustomerFragment.OnListFragmentInteractionListener{

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(getApplicationContext());

        this.retrieveCustomers();
//        this.retrieveTables();

    }



    private void retrieveCustomers(){
        Call<List<Customer>> listCustomers = QdWebApi.getQdApiInterface().getCustomers();
        listCustomers.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                List<Customer> customers = response.body();

                for (Customer customer : customers) {
                    dbHelper.addCustomer(customer);
                }

                customers = dbHelper.getAllCustomers();

                for (Customer customer : customers){
                    Log.d("Vai ", customer.toString());
                }

                showCustomers(customers);

            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }

    private void showCustomers(List<Customer> customers){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, CustomerFragment.newInstance(customers.size(), new ArrayList<Customer>(customers)));
        fragmentTransaction.commit();
    }

    private void retrieveTables(){
        Call<boolean[]> listTables = QdWebApi.getQdApiInterface().getTables();
        listTables.enqueue(new Callback<boolean[]>() {
            @Override
            public void onResponse(Call<boolean[]> call, Response<boolean[]> response) {
                boolean[] tablesBoolean = response.body();

                int counter = 1;
                Table t;
                for (boolean b : tablesBoolean){
                    t = new Table();
                    t.setOcupied(b);
                    t.setId(counter++);
                    dbHelper.addTable(t);
                }

                List<Table> tables = dbHelper.getAllTables();

                for (Table table : tables ){
                    Log.d("Vai", table.toString());
                }

            }

            @Override
            public void onFailure(Call<boolean[]> call, Throwable t) {

            }
        });

    }

    @Override
    public void onListFragmentInteraction(Customer customer) {

    }
}