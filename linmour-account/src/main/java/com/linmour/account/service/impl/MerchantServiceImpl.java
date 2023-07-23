package com.linmour.account.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.linmour.account.convert.UserInfoDtoConvert;
import com.linmour.account.pojo.Dto.UserInfoDto;
import com.linmour.account.utils.RedisCache;
import com.linmour.account.mapper.MerchantMapper;
import com.linmour.account.pojo.Do.Merchant;
import com.linmour.account.pojo.Vo.LoginVo;
import com.linmour.account.security.LoginUser;
import com.linmour.account.service.MerchantService;
import com.linmour.common.dtos.Result;
import com.linmour.common.exception.CustomException;
import com.linmour.common.exception.enums.AppHttpCodeEnum;
import com.linmour.common.service.FileStorageService;
import com.linmour.common.tess4j.Tess4jClient;
import com.linmour.common.utils.JwtUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Resource
    private Tess4jClient tess4jClient;



    @Override
    public Result login(LoginVo loginVo) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getPhone(), loginVo.getPassword());
        //这个方法就会调用我们写的UserDetailsService类进行数据库查询，如果返回null说明用户名错误
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        if (loginUser.getLoginVo().getStatus() == 0){
            throw new CustomException(AppHttpCodeEnum.ACCOUNT_DISABLE);
        }
        String UserId = loginUser.getLoginVo().getId().toString();
        //用id生成jwt
        String jwt = JwtUtil.createJWT(UserId);
        //获取用户信息

        //把用户信息存入redis
        redisCache.setCacheObject(USER_LOGIN_KEY +UserId,loginUser);
        HashMap<String, Object> map = new HashMap<>();
        UserInfoDto userInfo = getUserInfo(Long.valueOf(UserId));
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
    public UserInfoDto getUserInfo(Long userId) {
        Merchant merchant = merchantMapper.selectById(userId);
        if (ObjectUtil.isNull(merchant)){
            throw new CustomException(AppHttpCodeEnum.USERINFO_ERROR);
        }
        UserInfoDto userInfo = UserInfoDtoConvert.INSTANCE.MerchantToUserInfoDto(merchant);
        return userInfo;
    }

    @Override
    public Result uploadPicture(MultipartFile multipartFile,String prefix) {
        if (multipartFile == null || multipartFile.getSize() == 0){
            return Result.error(AppHttpCodeEnum.ARAUMENT_ERROR);
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileId = null;
        String fileName = null;
        //从SecurityContextHolder中拿到用户信息
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = user.getLoginVo().getId();
        fileName = id.toString();

//            fileName = UUID.randomUUID().toString().replace("-", "");


        try {
            fileId = fileStorageService.uploadImgFile(prefix, fileName + postfix, multipartFile.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        merchantMapper.update(null,new LambdaUpdateWrapper<Merchant>().eq(Merchant::getId,id)
                .set(StringUtils.isNotBlank(fileId),Merchant::getAvatar,fileId));
        return Result.success(fileId);

    }

    @Override
    public Result ocr(MultipartFile file) {

        String result = null;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(file.getBytes());
            BufferedImage bufferedImage = ImageIO.read(in);
            result = tess4jClient.doOCR(bufferedImage);
            Pattern pattern = Pattern.compile("\\b\\d{17}[\\dXx]\\b");
            Matcher matcher = pattern.matcher(result);
            if (matcher.find()) {
                result = matcher.group(0);
            }
        } catch (Exception e) {
            throw new CustomException(AppHttpCodeEnum.OCR_ERRER);
        }
        String url = uploadPicture(file, "idCard").getData().toString();
        HashMap<String, String> map = new HashMap<>();
        map.put("idCardUrl",url);
        map.put("idCard",result);

        return Result.success(map);

    }
}




