package com.github.collagedemo.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * @author Sergey Ryabov
 *         Date: 12.03.14
 */
@Data
public class IGResponse<T> {

    private Meta meta;
    // actually should be T data, but for our two requests this simplification will be correct
    private List<T> data;

    @Data
    public class Meta {
        private int code;
        @SerializedName("error_type")
        private String errorType;
        @SerializedName("error_message")
        private String errorMessage;
    }

}

