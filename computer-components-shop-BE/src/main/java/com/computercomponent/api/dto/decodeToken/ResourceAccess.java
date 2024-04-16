package com.computercomponent.dto.decodeToken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ResourceAccess {
    @SerializedName("account")
    @Expose
    private Account account;
}
