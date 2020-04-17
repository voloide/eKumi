package mz.co.insystems.mobicare.base;

import androidx.databinding.BaseObservable;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import mz.co.insystems.mobicare.util.Utilities;

public abstract class BaseVO extends BaseObservable implements Serializable {

    protected Utilities utilities = Utilities.getInstance();

    public JSONObject parseToJsonObject() throws JsonProcessingException, JSONException {
        return utilities.toJsonObject(this);
    }
}
