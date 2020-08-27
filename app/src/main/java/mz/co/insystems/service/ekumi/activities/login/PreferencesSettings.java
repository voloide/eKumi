package mz.co.insystems.service.ekumi.activities.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;

import java.io.IOException;

import mz.co.insystems.service.ekumi.base.AbstractBaseVO;
import mz.co.insystems.service.ekumi.util.Utilities;

public class PreferencesSettings {

    private static final String PREF_FILE = "object_pref";

    public void saveToPref(Context context, AbstractBaseVO object) throws JsonProcessingException, JSONException {
        final SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("object", object.parseToJsonObject().toString());
        editor.apply();
    }

    public <T extends AbstractBaseVO> T getObject(Class clazz, Context context) throws IOException {
        final SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        final String defaultValue = "";
        String content = sharedPref.getString("object", defaultValue);

        if (Utilities.stringHasValue(content)){
            ObjectMapper objectMapper = new ObjectMapper();
            return  (T) objectMapper.readValue(content, clazz);
        }
        return null;
    }
}
