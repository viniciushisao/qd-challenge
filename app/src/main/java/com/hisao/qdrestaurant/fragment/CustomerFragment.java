package com.hisao.qdrestaurant.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hisao.qdrestaurant.R;
import com.hisao.qdrestaurant.adapter.CustomerAdapter;
import com.hisao.qdrestaurant.model.Customer;

import java.util.ArrayList;

public class CustomerFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_COLUMN_CUSTOMERS = "column-customers";

    private CustomerFragmentInteractionListener mListener;
    private ArrayList<Customer> customers;

    public CustomerFragment() {
    }

    public static CustomerFragment newInstance(int columnCount, ArrayList<Customer> customers) {
        CustomerFragment fragment = new CustomerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putParcelableArrayList(ARG_COLUMN_CUSTOMERS, customers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.customers = getArguments().getParcelableArrayList(ARG_COLUMN_CUSTOMERS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new CustomerAdapter(this.customers, mListener));
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CustomerFragmentInteractionListener) {
            mListener = (CustomerFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface CustomerFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCustomerFragmentInteraction(Customer customer);
    }
}
