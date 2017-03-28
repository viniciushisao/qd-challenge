package com.hisao.qdrestaurant.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by vinicius on 24/03/17.
 */


public class Customer implements Parcelable {

    private String customerFirstName;
    private String customerLastName;
    private Integer id;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Parcelable.Creator<Customer> CREATOR = new Creator<Customer>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Customer createFromParcel(Parcel in) {
            Customer instance = new Customer();
            instance.customerFirstName = ((String) in.readValue((String.class.getClassLoader())));
            instance.customerLastName = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.additionalProperties = ((Map<String, Object>) in.readValue((Map.class.getClassLoader())));
            return instance;
        }

        public Customer[] newArray(int size) {
            return (new Customer[size]);
        }

    };

    public String getFirstName() {
        return customerFirstName;
    }

    public void setFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getLastName() {
        return customerLastName;
    }

    public void setLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(customerFirstName);
        dest.writeValue(customerLastName);
        dest.writeValue(id);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return this.id + "ID: " + this.id + " FIRST NAME: " + this.customerFirstName + " SECOND NAME: " + this.customerLastName;
    }
}