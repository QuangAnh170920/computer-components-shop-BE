package com.computercomponent.dto.decodeToken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Example {
    @SerializedName("exp")
    @Expose
    private Integer exp;
    @SerializedName("iat")
    @Expose
    private Integer iat;
    @SerializedName("jti")
    @Expose
    private String jti;
    @SerializedName("iss")
    @Expose
    private String iss;
    @SerializedName("aud")
    @Expose
    private String aud;
    @SerializedName("sub")
    @Expose
    private String sub;
    @SerializedName("typ")
    @Expose
    private String typ;
    @SerializedName("azp")
    @Expose
    private String azp;
    @SerializedName("session_state")
    @Expose
    private String sessionState;
    @SerializedName("acr")
    @Expose
    private String acr;
    @SerializedName("allowed-origins")
    @Expose
    private List<String> allowedOrigins = null;
    @SerializedName("realm_access")
    @Expose
    private RealmAccess realmAccess;
    @SerializedName("resource_access")
    @Expose
    private ResourceAccess resourceAccess;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("email_verified")
    @Expose
    private Boolean emailVerified;
    @SerializedName("preferred_username")
    @Expose
    private String preferredUsername;
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("idAdmin")
    @Expose
    private String idAdmin;
}
