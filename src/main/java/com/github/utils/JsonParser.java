package com.github.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by saikrisv on 23/05/17.
 */
public class JsonParser {

    private String filePath;

    public JsonParser(String filePath) {
        this.filePath = filePath;
    }

    public JSONArray getJsonParsedObjectAsJsonArray() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
            String jsonContent = IOUtils.toString(bufferedReader);
            return new JSONArray(jsonContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getObjectFromJSON() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
            String jsonContent = IOUtils.toString(bufferedReader);
            return new JSONObject(jsonContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
