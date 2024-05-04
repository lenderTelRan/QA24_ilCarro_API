package api;

import okhttp3.MediaType;

public interface BaseAPI {
    String BASE_URL = "https://ilcarro-backend.herokuapp.com";
    String LOGIN_URL = "/v1/user/login/usernamepassword";
    MediaType JSON = MediaType.get("application/json");
    String EMAIL = "Patrick321@gmail.com";
    String PASSWORD = "Patrick555$";
    String DELETE_SERIAL_NUMBER = "/v1/cars/";
}
