package mz.co.insystems.mobicare.common;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import mz.co.insystems.mobicare.base.BaseVO;

/**
 * Created by Voloide Tamele on 2/21/2018.
 */

public interface JsonParseble<T extends BaseVO> {

    JSONObject toJsonObject() throws JsonProcessingException, JSONException;

    String toJson() throws JsonProcessingException;

    T fromJson(String jsonData) throws IOException;

    T  fromJsonObject(JSONObject response) throws IOException;
}
