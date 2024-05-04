package okhttp;

import api.BaseAPI;
import com.google.gson.Gson;
import dto.CarDTO;
import dto.RegistrationBodyDTO;
import dto.ResponseMessageDTO;
import dto.TokenDTO;
import enumclasses.Fuel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.Random;

public class AddNewCarTestsOkhttp implements BaseAPI {
    OkHttpClient okHttpClient = new OkHttpClient();
    Gson gson = new Gson();
    SoftAssert softAssert = new SoftAssert();
    String token;

    @BeforeClass
    public void loginPositiveTest() throws IOException {
        RegistrationBodyDTO user = RegistrationBodyDTO.builder()
                .username(EMAIL)
                .password(PASSWORD)
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(user), JSON);

        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TokenDTO tokenDTO = gson.fromJson(response.body().string(), TokenDTO.class);
        token = tokenDTO.getAccessToken();

        System.out.println(token);
    }

    @Test
    public void addNewCarPositiveTest() {
        int i = new Random().nextInt(1000) + 1000;
        CarDTO car = CarDTO.builder()
                .serialNumber("333-" + i)
                .manufacture("Opel")
                .model("Astra")
                .year("2020")
                .fuel(Fuel.DIESEL)
                .seats(4)
                .carClass("A")
                .pricePerDay(100.23)
                .city("Haifa")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(car), JSON);

        Request request = new Request.Builder()
                .url(BASE_URL + "/v1/cars")
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();

        Response response;
        String responseJson;

        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (response == null)
            Assert.fail("response is null");
        else if (response.code() == 200) {
            try {
                responseJson = response.body().string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ResponseMessageDTO responseMessage = gson.fromJson(responseJson, ResponseMessageDTO.class);
            Assert.assertEquals(responseMessage.getMessage(), "Car added successfully");
        } else
            Assert.fail("Error, response code --> " + response.code());
    }
}
