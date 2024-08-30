package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

// this class concerns with the Data like handling data from external files
public class DataUtility {

    public static final String TestData_Path = "src/test/resources/TestData/";


    /* Reading From Json File */
    public static String GetJsonDataFromFile(String jsonfilename, String field) {

        try {
            FileReader reader = new FileReader(TestData_Path + jsonfilename + ".json");

            JsonElement jsonElement = JsonParser.parseReader(reader);

            return jsonElement.getAsJsonObject().get(field).getAsString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    /* Reading From Properties File */
    public static String GetPropertiesDataFromFile(String propertiesfilename, String key) {

        try {

            Properties properties = new Properties();
            properties.load(new FileInputStream(TestData_Path + propertiesfilename + ".properties"));
            return properties.getProperty(key);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
