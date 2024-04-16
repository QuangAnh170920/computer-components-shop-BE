package com.computercomponent.dto.decodeToken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Account {
    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
}
