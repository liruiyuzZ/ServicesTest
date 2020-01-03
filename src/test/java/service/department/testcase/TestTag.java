package service.department.testcase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.department.api.Tag;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class TestTag {
    static Tag tag = new Tag();

    @BeforeAll
    public static void beforeAll() {
        ArrayList<Integer> ids = tag.list().then().log().all()
                .extract().body().path("taglist.tagid");
        ids.forEach(id -> tag.del(id));
    }


    @Test
    @DisplayName("获取列表")
    public void list() {
        tag.list().then().body("errmsg", equalTo("ok"));
    }

    @Test
    @DisplayName("创建标签")
    public void create() {
        String name = "标签101";
        tag.create(name).then().body("errmsg", equalTo("created"));
        tag.list().then().body("taglist.findAll{t->t.tagname=='" + name + "'}.tagid", hasSize(1));
    }

    @Test
    @DisplayName("删除标签")
    public void del() {
        int id = tag.create("标签102").then().body("errmsg", equalTo("created"))
                .extract().body().path("tagid");
        tag.del(id).then().body("errmsg", equalTo("deleted"));
    }

    @Test
    @DisplayName("更新标签")
    public void update() {
        String name = "标签107";
        int id = tag.create(name).then().body("errmsg", equalTo("created"))
                .extract().body().path("tagid");
        tag.updateTag(id, "标签108").then().body("errmsg", equalTo("updated"));
        tag.del(id).then().body("errmsg", equalTo("deleted"));
    }
}
