package com.osmos.qa.utils;

import com.github.javafaker.Faker;
import com.osmos.qa.models.Lead;

public class TestDataProvider {

    private static final Faker faker = new Faker();

    public static Lead newLead() {
        return Lead.builder()
                .setName(faker.name().fullName())
                .setEmail(faker.internet().emailAddress())
                .setPriority("High")
                .setStatus("Contacted")
                .setNotes(faker.lorem().sentence())
                .build();
    }

    public static Lead newHighPriorityLead() {
        return Lead.builder()
                .setName(faker.name().fullName())
                .setEmail(faker.internet().emailAddress())
                .setPriority("High")
                .setStatus("New")
                .setQualified(true)
                .setEmailOptIn(true)
                .setNotes(faker.lorem().sentence())
                .build();
    }
}
