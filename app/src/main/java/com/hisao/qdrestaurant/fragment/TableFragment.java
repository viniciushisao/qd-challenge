package com.hisao.qdrestaurant.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hisao.qdrestaurant.R;
import com.hisao.qdrestaurant.adapter.TableAdapter;
import com.hisao.qdrestaurant.model.Customer;
import com.hisao.qdrestaurant.model.Table;

import java.util.ArrayList;


public class TableFragment extends Fragment {


    private static final String ARG_TABLES_ARRAYLIST = "arg_table_arraylist";
    private static final String ARG_CUSTOMER = "arg_customer";
    private TableFragmentInteractionListener mListener;

    private ArrayList<Table> tableArrayList;
    private Customer customer;

    public TableFragment() {
    }

    public static TableFragment newInstance(ArrayList<Table> tables, Customer customer) {
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_TABLES_ARRAYLIST, tables);
        args.putParcelable(ARG_CUSTOMER, customer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.tableArrayList = getArguments().getParcelableArrayList(ARG_TABLES_ARRAYLIST);
            this.customer = getArguments().getParcelable(ARG_CUSTOMER);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            recyclerView.setAdapter(new TableAdapter(this.tableArrayList, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TableFragmentInteractionListener) {
            mListener = (TableFragmentInteractionListener) context;
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


    public interface TableFragmentInteractionListener {
        void onTableFragmentListInteraction(Table table);
    }
}
