package com.esprit.socialNetwork.Utils;

import java.util.Map;

public interface IObjectToJsonConverter {

    Map<String, Object> toJson(Object from);
}
