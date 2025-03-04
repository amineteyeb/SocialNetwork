package com.esprit.socialnetwork.Utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    private Integer status;

    @Contract("_, _ -> new")
    public static @NotNull ApiResponse ofError(String error, Integer code) {
        return new ApiResponse(null, error, code);
    }

    @Contract("_, _ -> new")
    public static @NotNull ApiResponse ofSuccess(String success, Integer code) {
        return new ApiResponse(success, null, code);
    }
}
