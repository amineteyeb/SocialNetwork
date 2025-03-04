package com.esprit.socialnetwork.Utils;

import java.util.Map;

public interface IObjectToJsonConverter {

    Map<String, Object> toJson(Object from);
}
