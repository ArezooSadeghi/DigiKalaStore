package com.example.digikalastore.remote.retrofit;

import com.example.digikalastore.model.customer.Billing;
import com.example.digikalastore.model.customer.Customer;
import com.example.digikalastore.model.customer.Shipping;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class CustomerDeserializer implements JsonDeserializer<Customer> {
    @Override
    public Customer deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        JsonObject customerObject = json.getAsJsonObject();
        String firstName = customerObject.get("first_name").getAsString();
        String lastName = customerObject.get("last_name").getAsString();
        String userName = customerObject.get("username").getAsString();
        String email = customerObject.get("email").getAsString();

        JsonObject billingObject = customerObject.get("billing").getAsJsonObject();
        String company = billingObject.get("company").getAsString();
        String addressOne = billingObject.get("address_1").getAsString();
        String addressTwo = billingObject.get("address_2").getAsString();
        String city = billingObject.get("city").getAsString();
        String state = billingObject.get("state").getAsString();
        String postcode = billingObject.get("postcode").getAsString();
        String country = billingObject.get("country").getAsString();
        String phone = billingObject.get("phone").getAsString();

        Billing billing = new Billing(
                firstName,
                lastName,
                company,
                addressOne,
                addressTwo,
                city,
                state,
                postcode,
                country,
                email,
                phone);
        Shipping shipping = new Shipping(
                firstName,
                lastName,
                company,
                addressOne,
                addressTwo,
                city,
                state,
                postcode,
                country);

        return new Customer(firstName, lastName, userName, email, billing, shipping);
    }
}
