package service.department.testcase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.department.api.Department;


import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class TestDepartment {
    //    static int id = 534;
    static Department department = new Department();

    @BeforeAll
    public static void beforeAll() {
        ArrayList<Integer> ids = department.list(department.parentDepartId).then()
                .extract().body().path("department.findAll {d->d.parentid==" + department.parentDepartId + " }.id");
        System.out.println(ids);
//        System.out.println();
        ids.forEach(id -> department.delete(id));
    }

    @Test
    public void create() {
        String name = "部门2";
        department.create(name).then().body("errmsg", equalTo("created"));
        department.list(department.parentDepartId)
                .then().body("department.findAll {d->d.name=='" + name + "'}.id", hasSize(1));
    }

    @Test
    public void list() {
        department.list(department.parentDepartId).then().body("errmsg", equalTo("ok"));
    }

    @Test
    public void delete() {
        int id = department.create("部门3").then().body("errmsg", equalTo("created"))
                .extract().body().path("id");
        System.out.println(id);
        department.delete(id).then().body("errmsg", equalTo("deleted"));
    }
}
