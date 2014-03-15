package com.github.collagedemo.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sergey Ryabov
 *         Date: 12.03.14
 */
@Data
public class Media implements Serializable {

    private String id;
    private String type;
    private Caption caption;
    private Images images;

    public String getCaption() {
        return caption == null ? null : caption.text;
    }

    public String getImageUrl() {
        return images.standard.url;
    }

    public boolean isImage() {
        return "image".equalsIgnoreCase(type);
    }

    @Data
    public class Caption implements Serializable {
        private String text;
    }

    @Data
    public class Images implements Serializable {
        @SerializedName("low_resolution")
        private Image standard;
        @SerializedName("standard_resolution")
        private Image lowRes;
    }

    @Data
    public class Image implements Serializable {
        private String url;
        private int width;
        private int height;
    }
}
