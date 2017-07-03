package me.chenyongrui.movism.mvp.model.omdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {

    private final static long serialVersionUID = 1623576714510265893L;
    @SerializedName("Source")
    @Expose
    private String source;
    @SerializedName("Value")
    @Expose
    private String value;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}