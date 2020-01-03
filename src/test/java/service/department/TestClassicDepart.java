package service.department;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestClassicDepart {
    static String token;
    static int parentDepartId = 532;

    @BeforeAll
    public static void getToken() {

        token = given()
                .param("corpid", "wwd6da61649bd66fea")
                .param("corpsecret", "C7uGOrNyxWWzwBsUyWEbLdbZBDrc71PNOhyQ_YYPhts")
                .when()
                .log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .log().all()
                .body("errcode", equalTo(0))
                .extract()
                .body().path("access_token");

        System.out.println(token);
    }

    @Test
    public void departCreate() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", "eben_xiaofeixiaNo1");
        data.put("parentid", parentDepartId);

        given()
                .queryParam("access_token", token)
                .contentType(ContentType.JSON)
                .body(data)
                .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all()
                .body("errcode", equalTo(0));

    }

    @Test
    public void departList() {
        given()
                .queryParam("access_token", token)
                .queryParam("id", parentDepartId)
                .when().log().all()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().log().all()
                .body("errcode", equalTo(0));
    }

    public void departDel() {

    }

}
