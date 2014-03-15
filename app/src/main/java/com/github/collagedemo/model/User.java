package com.github.collagedemo.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Sergey Ryabov
 *         Date: 12.03.14
 */
@Data
public class User {

    private String id;
    private String username;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("profile_picture")
    private String profilePicUrl;
}
