package net.dictionary.tests;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.restassured.RestAssured;
import net.dictionary.api.client.EndpointUrl;
import org.eclipse.jetty.server.Request;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class RestAssuredTest {

    private static final String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private static final String API_KEY = "trnsl.1.1.20180711T123306Z.b012f6b5034cc719.aff4999263480fef925a97d8863cbccc7ab40f50";

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://translate.yandex.net/api/v1.5/tr.json/";
    }




    @Test public
    void TC1_dictionaryTest() {
        RestAssured.useRelaxedHTTPSValidation(); //SSL

        String additionalPath = getPathFormated(API_KEY, "Привет, мир!", "ru-en");

        given()
                .header("User-Agent", "Mozilla...")
                .header("secret_key", "super_secret")
//                .header("JWT", authorizeMe("login", "password"))

                .when()
                .get(EndpointUrl.TRANSLATE.addPath(additionalPath))
                .then()
                .statusCode(200)
                .body("text", hasItem("Hello world!"))
                .body("lang", equalTo("ru-en"))
                .body("code", equalTo(200));
    }

    protected static String getPathFormated(String key, String text, String languageFormat) {
        return String.format("?key=%s&text=%s&lang=%s", key, text, languageFormat);
    }

    private static String authorizeMe(String login, String password) {
//        return Unirest.get(API_URL + getPathFormated(API_KEY, "Привет,мир!", "ru-en")).asString().getBody();
        return null;
    }

    public static void main(String[] args) throws UnirestException {
        String response = Unirest.get(API_URL + getPathFormated(API_KEY, "Привет,мир!", "ru-en")).asString().getBody();

        System.out.println(response);
    }
}
