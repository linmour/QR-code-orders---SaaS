package com.linmour.restaurant.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.common.dtos.Result;
import com.linmour.common.service.FileStorageService;
import com.linmour.restaurant.covert.TableConvert;
import com.linmour.restaurant.mapper.RestaurantTableMapper;
import com.linmour.restaurant.pojo.Do.RestaurantTable;
import com.linmour.restaurant.pojo.Dto.RestaurantTableDto;
import com.linmour.restaurant.service.RestaurantTableService;
import com.linmour.restaurant.utils.MyMultipartFile;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.linmour.common.utils.SecurityUtils.getShopId;
import static com.linmour.common.utils.SecurityUtils.getUserId;

/**
 * @author linmour
 * @description 针对表【restaurant_table】的数据库操作Service实现
 * @createDate 2023-08-05 20:01:23
 */
@Service
public class RestaurantTableServiceImpl extends ServiceImpl<RestaurantTableMapper, RestaurantTable>
        implements RestaurantTableService {

    @Resource
    private RestaurantTableMapper restaurantTableMapper;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private FileStorageService fileStorageService;

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;

    @Override
    public Result getTable() {
        List<RestaurantTable> restaurantTables = restaurantTableMapper.selectList(null);
        return Result.success(restaurantTables);

    }

    @Override
    public Result createTable(RestaurantTableDto dto ) {
        JsonNode json = Unirest.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx2728606f5f2b4621&secret=9ecbe72e173907443e9742803a980533").asJson().getBody();

        if(json.toString().contains("errcode")){
            return Result.error("生成失败");
        }

        RestaurantTable restaurantTable = TableConvert.IN.TableDtoToTable(dto);
        restaurantTableMapper.insert(restaurantTable);
        Long tabldId  = restaurantTable.getId();
        //获取凭证
        //TODO redis缓存
        String token = json.getObject().getString("access_token");
        //你的json数据 ,格式不要错
        JSONObject body = new JSONObject();
        String scene =  ":"+getShopId() + ":" + tabldId ;
        body.put("scene", scene);
        body.put("width", dto.getSize());
        body.put("check_path", false);
        body.put("env_version", "trial");
        body.put("page", "pages/order/order");
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+ token;
        //获取二维码
        byte[] data = Unirest.post(url).body(body.toJSONString(1)).asBytes().getBody();
        //构建上传MyMultipartFile所需的参数
        MyMultipartFile myMultipartFile = new MyMultipartFile("", "temp.png", "image/png", data);
            //哪个人/哪家店/哪个座位
//        String qrUrl = fileStorageService.uploadPicture(myMultipartFile, "QR/" + getUserId() + "/" + getShopId() + "/" + tabldId, tabldId.toString());
        String path = "QR/user_" + getUserId() + "/shop_" + getShopId() +"/" + tabldId + ".png";
        File directory = new File("D:/b/" + path).getParentFile();
        if (!directory.exists()){
            directory.mkdirs();
        }
        try {
            myMultipartFile.transferTo(new File("D:/b/" + path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        restaurantTable.setQrCodeUrl("http://127.0.0.1:12800/file/"+path);
        restaurantTableMapper.updateById(restaurantTable);
        return Result.success();
    }




}




