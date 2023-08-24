import cn.hutool.json.JSONObject;
import com.linmour.restaurant.RestaurantApplication;
import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest(classes = RestaurantApplication.class)
public class a {




    @Test
    public void a() {


    }

    public static void main(String[] args) {
        JsonNode body = Unirest.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx2728606f5f2b4621&secret=9ecbe72e173907443e9742803a980533").asJson().getBody();
//        String accessToken = body.getObject().getString("errcode");
        System.out.println(body.toString().contains("errcode"));
    }


}



