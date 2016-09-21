package com.fusemobiletest.classes;

/**
 * Created by Chris on 2016/09/20.
 */

public class CompanyResponse {
    String name;
    String logo;
    String custom_colour;
    PasswordObject password_changing;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCustom_colour() {
        return custom_colour;
    }

    public void setCustom_colour(String custom_colour) {
        this.custom_colour = custom_colour;
    }

    public PasswordObject getPassword_changing() {
        return password_changing;
    }

    public void setPassword_changing(PasswordObject password_changing) {
        this.password_changing = password_changing;
    }

    public class PasswordObject {
        String enabled;
        String secure_field;

        public String getEnabled() {
            return enabled;
        }

        public void setEnabled(String enabled) {
            this.enabled = enabled;
        }

        public String getSecure_field() {
            return secure_field;
        }

        public void setSecure_field(String secure_field) {
            this.secure_field = secure_field;
        }
    }
}
