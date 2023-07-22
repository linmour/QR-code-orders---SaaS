package com.linmour.account.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.linmour.account.convert.UserInfoDtoConvert;
import com.linmour.account.pojo.Dto.UserInfoDto;
import com.linmour.account.utils.RedisCache;
import com.linmour.account.mapper.MerchantMapper;
import com.linmour.account.pojo.Do.Merchant;
import com.linmour.account.pojo.Dto.LoginDto;
import com.linmour.account.security.LoginUser;
import com.linmour.account.service.MerchantService;
import com.linmour.common.dtos.Result;
import com.linmour.common.exception.CustomException;
import com.linmour.common.exception.enums.AppHttpCodeEnum;
import com.linmour.common.service.FileStorageService;
import com.linmour.common.utils.JwtUtil;

import com.lowagie.text.pdf.PRIndirectReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static com.linmour.account.constants.constants.USER_LOGIN_KEY;


/**
* @author linmour
* @description 针对表【system_merchant】的数据库操作Service实现
* @createDate 2023-07-17 20:05:36
*/
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant>
    implements MerchantService{

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;
    @Resource
    private MerchantMapper merchantMapper;
    @Resource
    private FileStorageService fileStorageService;


    @Override
    public Result login(LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getPhone(), loginDto.getPassword());
        //这个方法就会调用我们写的UserDetailsService类进行数据库查询，如果返回null说明用户名错误
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        if (loginUser.getLoginDto().getStatus() == 0){
            throw new CustomException(AppHttpCodeEnum.ACCOUNT_DISABLE);
        }
        String UserId = loginUser.getLoginDto().getId().toString();
        //用id生成jwt
        String jwt = JwtUtil.createJWT(UserId);
        //获取用户信息

        //把用户信息存入redis
        redisCache.setCacheObject(USER_LOGIN_KEY +UserId,loginUser);
        HashMap<String, Object> map = new HashMap<>();
        UserInfoDto userInfo = userInfo(Long.valueOf(UserId));
        map.put("token",jwt);
        map.put("userInfo",userInfo);


        return Result.success(map);
    }

    @Override
    public Result logout(Long id) {
        redisCache.deleteObject(USER_LOGIN_KEY+id);
        return Result.success();

    }

    @Override
    public UserInfoDto userInfo(Long userId) {
        Merchant merchant = merchantMapper.selectById(userId);
        if (ObjectUtil.isNull(merchant)){
            throw new CustomException(AppHttpCodeEnum.USERINFO_ERROR);
        }
        UserInfoDto userInfo = UserInfoDtoConvert.INSTANCE.Merchant(merchant);
        return userInfo;
    }

    @Override
    public Result uploadPicture(MultipartFile multipartFile, String prefix) {
        if (multipartFile == null || multipartFile.getSize() == 0){
            return Result.error(AppHttpCodeEnum.ARAUMENT_ERROR);
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileId = null;
        String fileName =null;
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = user.getLoginDto().getId();
        if (StringUtils.isNotBlank(prefix) && prefix.equals("avatar")){
            //从SecurityContextHolder中拿到用户信息

            fileName = id.toString();
        }else {
            fileName = UUID.randomUUID().toString().replace("-", "");
        }

        try {
            fileId = fileStorageService.uploadImgFile(prefix, fileName + postfix, multipartFile.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        merchantMapper.update(null,new LambdaUpdateWrapper<Merchant>().eq(Merchant::getId,id)
                .set(StringUtils.isNotBlank(fileId),Merchant::getAvatar,fileId));


        return Result.success(fileId);

    }
}




