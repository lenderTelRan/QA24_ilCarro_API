package okhttp;

import api.BaseAPI;
import com.google.gson.Gson;
import dto.ErrorMessageDTO;
import dto.RegistrationBodyDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class RegistrationTestsOkhttp implements BaseAPI {
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void registrationSuccess() throws IOException {
        int i = new Random().nextInt(100) + 100;

        RegistrationBodyDTO user = RegistrationBodyDTO.builder()
                .username("Patrick" + i + "@gmail.com")
                .password("Patrick555$")
                .firstName("Bibi")
                .lastName("Bobo")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(user), JSON);

        Request request = new Request.Builder()
                .url(BASE_URL + "/v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);
        System.out.println(response.toString());
    }

    @Test
    public void registrationWrongEmail() throws IOException {
        int i = new Random().nextInt(100) + 100;

        RegistrationBodyDTO user = RegistrationBodyDTO.builder()
                .username("Patrick" + i + "gmail.com")
                .password("Patrick555$")
                .firstName("Bibi")
                .lastName("Bobo")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(user), JSON);

        Request request = new Request.Builder()
                .url(BASE_URL + "/v1/user/registration/usernamepassword")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        String responseJson = response.body().string();
        System.out.println(responseJson);
//        ErrorMessageDTO errorMessageDTO = gson.fromJson(responseJson, ErrorMessageDTO.class);

        Assert.assertEquals(response.code(), 400);
        Assert.assertTrue(responseJson.contains("Bad Request"));

//        Assert.assertEquals(errorMessageDTO.getStatus(), 400);
//        Assert.assertEquals(errorMessageDTO.getError(), "Bad Request");
    }

}
