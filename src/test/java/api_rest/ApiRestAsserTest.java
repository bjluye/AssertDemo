package api_rest;

import com.sun.org.apache.xpath.internal.operations.String;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;
//import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.filters;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.hamcrest.Matchers.*;
//import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class ApiRestAsserTest {


    @Test
    public void test_1(){
        given()
                .queryParam("limit","20")
        .when()
                .get("https://testerhome.com/api/v3/topics.json")
        .then()
                .statusCode(200)
                .log().all()
                .body("topics.findAll{ it -> it.title == 'Testerhome 深圳第四期管理沙龙' }.user.name",hasItem("卡斯"))
                ;
    }

    @Test
    public void test_2(){
        useRelaxedHTTPSValidation();
        Map cookieMap = new HashMap<String,String>();
        cookieMap.put("xq_a_token","9c75d6bfbd0112c72b385fd95305e36563da53fb");
        cookieMap.put("xq_r_token","9ad364aac7522791166c59720025e1f8f11bf9eb");
        given().
                queryParam("symbol","SH000001,SZ399001,SZ399006,HKHSI,HKHSCEI,HKHSCCI,.DJI,.IXIC,.INX")
        .when()
                .cookies(cookieMap).get("https://stock.xueqiu.com/v5/stock/batch/quote.json")
        .then()
                .log().all()
                .statusCode(200)
                .body("data.items.findAll{it->it.quote.symbol=='SH000001'}.size()",equalTo(1))
                .body("data.items[0].market.status",equalTo("已收盘"))
                .body("data.items.quote.find {it.symbol == \"SH000001\"}.current.toDouble()",closeTo(2828.28,100))
                ;
    }


    @Test
    public void test_base64() {
        useRelaxedHTTPSValidation();


        filters(new Filter() {

            public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
                Response response = filterContext.next(filterableRequestSpecification,filterableResponseSpecification);
                System.out.println("origin response");
                System.out.println(response.getBody().asString());
                System.out.println("decode response");
                //String contentDescode = new String(Base64.decodeBase64());
                //String contentDecode=new String(Base64.decodeBase64(response.getBody().asString()));
                //System.out.println(contentDecode);


                return response;

            }
        });



        given()
                .auth().basic("hogwarts","123456")
        .when()
                .get("http://jenkins.testing-studio.com:9001/base64.json")
        .then()
                .log().all();
    }

    @Test
    public void test_jekins(){
        useRelaxedHTTPSValidation();

    }
//    @Test
//    public void json() {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("name", "luye");
//        map.put("age", "20");
//        useRelaxedHTTPSValidation();
//    }
//
//    @Test
//    public void extract(){
//        useRelaxedHTTPSValidation();
//        RequestSpecification response = given().proxy("127.0.0.1",8080)
//
//
//
//
//    }
//    @Test
//    public void githubOauth(){
//        given().auth().oauth2("")
//                .when().get().then().log().all();all

    //}
}
