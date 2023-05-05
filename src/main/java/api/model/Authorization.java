package api.model;

import org.apache.commons.lang3.RandomStringUtils;

public class Authorization {
    private String login;
    private String password;

    public Authorization(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static Authorization getRandomLoginData() {
        return new Authorization(
                RandomStringUtils.randomAlphabetic(10) + "",
                RandomStringUtils.randomAlphanumeric(10) + ""
        );
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}