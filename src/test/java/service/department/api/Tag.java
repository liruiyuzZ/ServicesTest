package service.department.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import service.Work;

import java.util.HashMap;
import java.util.StringTokenizer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Tag {
    String listUrl = "https://qyapi.weixin.qq.com/cgi-bin/tag/list";
    String delUrl = "https://qyapi.weixin.qq.com/cgi-bin/tag/delete";
    String updateUrl = "https://qyapi.weixin.qq.com/cgi-bin/tag/update";

    public Response list() {
        return given()
                .queryParam("access_token", Work.getInstance().getToken())
                .when().log().all()
                .get(listUrl)
                .then().log().all()
                .body("errcode", equalTo(0))
                .extract().response();
    }

    public Response create(String name) {
        HashMap<String, Object> data = new HashMap();
        data.put("tagname", name);

        return given()
                .queryParam("access_token", Work.getInstance().getToken())
                .contentType(ContentType.JSON)
                .body(data)
                .when().log().all()
                .post("https://qyapi.weixin.qq.com/cgi-bin/tag/create")
                .then().log().all()
                .body("errcode", equalTo(0))
                .extract().response();

    }

    public Response del(int id) {
        return given()
                .queryParam("access_token", Work.getInstance().getToken())
                .queryParam("tagid", id)
                .when().log().all()
                .get(delUrl)
                .then().log().all()
                .body("errcode", equalTo(0))
                .extract().response();
    }

    public Response updateTag(int id, String name) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("tagid", id);
        data.put("tagname", name);
        return given()
                    .queryParam("access_token", Work.getInstance().getToken())
                    .contentType(ContentType.JSON)
                    .body(data)
                .when().log().all()
                    .post(updateUrl)
                .then().log().all()
                    .body("errcode", equalTo(0))
                .extract().response();
    }
}
