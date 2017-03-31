package com.hisao.qdrestaurant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hisao.qdrestaurant.R;
import com.hisao.qdrestaurant.fragment.TableFragment.TableFragmentInteractionListener;
import com.hisao.qdrestaurant.model.Table;

import java.util.List;


public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private final List<Table> tables;
    private final TableFragmentInteractionListener mListener;

    public TableAdapter(List<Table> items, TableFragmentInteractionListener listener) {
        tables = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_table, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTable = tables.get(position);
        holder.mIdView.setText(String.valueOf(holder.mTable.getId()) );
        if (!holder.mTable.isOcupied()){
            holder.mContentView.setVisibility(View.VISIBLE);
            holder.mContentView.setText("FREE");
        }else{
            holder.mContentView.setVisibility(View.INVISIBLE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onTableFragmentListInteraction(holder.mTable);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Table mTable;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
