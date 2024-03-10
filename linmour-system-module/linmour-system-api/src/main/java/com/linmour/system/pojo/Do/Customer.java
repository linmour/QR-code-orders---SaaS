package com.linmour.system.pojo.Do;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value ="system_customer")
@Data
public class Customer {

    private String id;
    private String nickName;
    private String avatar;
    private String openid;
}
