package com.hisao.qdrestaurant.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vinicius on 27/03/17.
 */

public class Table implements Parcelable {

    private boolean isOcupied;
    private int id;

    public boolean isOcupied() {
        return isOcupied;
    }


    public int isOcupiedInteger() {
        if (isOcupied){
            return 1;
        }
        return 0;
    }

    public void setOcupied(boolean ocupied) {
        isOcupied = ocupied;
    }

    public void setOcupied(int ocupied) {
        if (ocupied == 0){
            isOcupied = false;
        }else{
            isOcupied = true;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Table(){

    }


    protected Table(Parcel in) {
        isOcupied = in.readByte() != 0;
        id = in.readInt();
    }

    public static final Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isOcupied ? 1 : 0));
        dest.writeInt(id);
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " OCUPIED: " + this.isOcupied();
    }
}
