package com.example.digikalastore.database;

import androidx.room.TypeConverter;

import java.util.UUID;

public class Converter {

    @TypeConverter
    public static String uuidToString(UUID uuid) {
        return uuid.toString();
    }

    @TypeConverter
    public static UUID stringToUuid(String uuid) {
        return UUID.fromString(uuid);
    }
}
