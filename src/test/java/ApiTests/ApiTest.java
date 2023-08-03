package ApiTests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.form;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ApiTest {
    static String token;
    static String urlAuth = "https://test-stand.gb.ru/gateway/login";
    static String urlPosts = "https://test-stand.gb.ru/api/posts";
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    @BeforeEach
    void beforeTestRequestSpecification() {
        requestSpecification = new RequestSpecBuilder()
                .addHeader("X-Auth-Token", token)
                .log(LogDetail.PARAMS)
                .build();
    }

    @BeforeEach
    void beforeTestResponseSpecification() {
        responseSpecification = new ResponseSpecBuilder()
                .expectResponseTime(Matchers.lessThan(10000L))
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();
    }

    @BeforeAll
    static void getToken() {
        Response response = given()
                //.body(form("ivanov_ivan", "cb0bc330f5"))
                .formParam("username", "ivanov_ivan")
                .formParam("password", "cb0bc330f5")
                .when()
                .post(urlAuth);

        JsonPath jsonPath = response.jsonPath();
        token = jsonPath.getString("token");
        //System.out.println("This is token: " + token);
    }

    @Test
    void checkAuth() {
        Response response = given()
                .formParam("username", "ivanov_ivan")
                .formParam("password", "cb0bc330f5")
                .when()
                .post(urlAuth);


        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("username"), equalTo("ivanov_ivan"));
        assertEquals(response.getStatusCode(), 200, "Expected status code: 200");
    }

    @Test
    void invalidLoginTest() {
        Response response = given()
                .formParam("username", "@#$%^&*")
                .formParam("password", "cb0bc330f5")
                .when()
                .post(urlAuth);

        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("error"), equalTo("Invalid credentials."));
        assertEquals(response.getStatusCode(), 401, "Expected status code: 401");
    }


    @Test
    void invalidPasswordTest() {
        Response response = given()
                .formParam("username", "ivanov_ivan")
                .formParam("password", "")
                .when()
                .post(urlAuth);

        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("error"), equalTo("Invalid credentials."));
        assertEquals(response.getStatusCode(), 401, "Expected status code: 401");
    }


    @Test
    void emptyFormTest() {
        Response response = given()
                .formParam("username", "")
                .formParam("password", "")
                .when()
                .post(urlAuth);


        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("error"), equalTo("Invalid credentials."));
        assertEquals(response.getStatusCode(), 401, "Expected status code: 401");
    }


    @Test
    void responseDataEmptyTest() {
        Response response = given()
                .spec(requestSpecification)
                .queryParam("page", "5")
                .when()
                .get(urlPosts);

        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getList("data"), is(empty()));
    }


    @Test
    void dataCurrentContainsTest() {
        given()
                .spec(requestSpecification)
                .queryParam("page", "1")
                .when()
                .get(urlPosts)
                .then()
                .body("data", everyItem(hasKey("id")))
                .body("data", everyItem(hasKey("title")))
                .body("data", everyItem(hasKey("description")))
                .body("data", everyItem(hasKey("content")))
                .body("data", everyItem(hasKey("authorId")))
                .body("data", everyItem(hasKey("mainImage")))
                .body("data", everyItem(hasKey("updatedAt")))
                .body("data", everyItem(hasKey("createdAt")))
                .body("data", everyItem(hasKey("labels")))
                .body("data", everyItem(hasKey("delayPublishTo")))
                .body("data", everyItem(hasKey("draft")))
                .body("data", not(empty()))
                .spec(responseSpecification);
    }


    @Test
    void metaCurrentContainsTest() {
        given()
                .spec(requestSpecification)
                .queryParam("page", "5")
                .when()
                .get(urlPosts)
                .then()
                .body("meta", hasKey("prevPage"))
                .body("meta", hasKey("nextPage"))
                .body("meta", hasKey("count"))
                .spec(responseSpecification);
    }


    @Test
    void checkTypePrevPageTest() {
        given()
                .spec(requestSpecification)
                .queryParam("page", "5")
                .when()
                .get(urlPosts)
                .then()
                .body("meta.prevPage", instanceOf(String.class))
                .spec(responseSpecification);
    }


    @Test
    void checkTypeNextPageTest() {
        given()
                .spec(requestSpecification)
                .queryParam("page", "1")
                .when()
                .get(urlPosts)
                .then()
                .body("meta.nextPage", anyOf(instanceOf(String.class), nullValue()))
                .spec(responseSpecification);
    }

    @Test
    void checkTypeCountTest() {
        given()
                .spec(requestSpecification)
                .queryParam("page", "1")
                .when()
                .get(urlPosts)
                .then()
                .body("meta.count", anyOf(instanceOf(Integer.class)))
                .spec(responseSpecification);
    }


    @Test
    void checkAscOrderDefaultTest() {
        Response response = given()
                .spec(requestSpecification)
                .queryParam("page", "1")
                .when()
                .get(urlPosts);

        JsonPath jsonPath = response.jsonPath();
        List<HashMap<String, Object>> data = jsonPath.getList("data");
        for (int i = 1; i < data.size(); i++) {
            HashMap <String, Object> dataArray = data.get(i);
            HashMap <String, Object> prevDataArray = data.get(i-1);
            int dataArrayId = (int) dataArray.get("id");
            int prevDataArrayId = (int) prevDataArray.get("id");
            assertThat(dataArrayId, greaterThan(prevDataArrayId));
        }
    }


    @Test
    void checkAscOrderTest() {
        Response response = given()
                .spec(requestSpecification)
                .queryParam("owner", "notMe")
                .queryParam("page", "1")
                .queryParam("order", "ASC")
                .when()
                .get(urlPosts);

        JsonPath jsonPath = response.jsonPath();
        List<HashMap<String, Object>> data = jsonPath.getList("data");
        for (int i = 1; i < data.size(); i++) {
            HashMap<String, Object> dataArray = data.get(i);
            HashMap<String, Object> prevDataArray = data.get(i - 1);
            int dataArrayId = (int) dataArray.get("id");
            int prevDataArrayId = (int) prevDataArray.get("id");
            assertThat(dataArrayId, greaterThan(prevDataArrayId));
        }
    }


    @Test
    void checkDescOrderTest() {
        Response response = given()
                .spec(requestSpecification)
                .queryParam("owner", "notMe")
                .queryParam("page", "1")
                .queryParam("order", "DESC")
                .when()
                .get(urlPosts);

        JsonPath jsonPath = response.jsonPath();
        List<HashMap<String, Object>> data = jsonPath.getList("data");
        for (int i = 1; i < data.size(); i++) {
            HashMap<String, Object> dataArray = data.get(i);
            HashMap<String, Object> prevDataArray = data.get(i - 1);
            int dataArrayId = (int) dataArray.get("id");
            int prevDataArrayId = (int) prevDataArray.get("id");
            assertThat(dataArrayId, lessThan(prevDataArrayId));
        }
    }


    @Test
    void correctPageNumberTest() {
        Response response = given()
                .spec(requestSpecification)
                .queryParam("owner", "notMe")
                .queryParam("page", "-2")
                .when()
                .get(urlPosts);

        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getList("data"), is(empty()));
    }



    @Test
    void notAuthorizationTest() {
        given()
                .header("X-Auth-Token", "")
                .queryParam("page", "1")
                .when()
                .get(urlPosts)
                .then()
                .statusCode(401);
    }


    @Test
    void postsOtherUsersTest() {
        Response response = given()
                .spec(requestSpecification)
                .queryParam("owner", "notMe")
                .queryParam("page", "1")
                .when()
                .get(urlPosts);

        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("meta.count"), greaterThan("100"));
    }


    @Test
    void checkOrderAllTest() {
        given()
                .spec(requestSpecification)
                .queryParam("owner", "notMe")
                .queryParam("page", "1")
                .queryParam("order", "ALL")
                .when()
                .get(urlPosts)
                .then()
                .spec(responseSpecification);
    }
}
