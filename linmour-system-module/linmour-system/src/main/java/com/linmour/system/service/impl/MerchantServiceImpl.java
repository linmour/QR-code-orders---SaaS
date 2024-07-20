package com.linmour.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmour.common.tess4j.Tess4jClient;
import com.linmour.security.dtos.LoginUser;
import com.linmour.security.dtos.LoginVo;
import com.linmour.security.dtos.Result;
import com.linmour.security.exception.CustomException;
import com.linmour.security.exception.enums.AppHttpCodeEnum;
import com.linmour.security.utils.JwtUtil;
import com.linmour.security.utils.RedisCache;
import com.linmour.system.convert.MerchantConvert;
import com.linmour.system.mapper.MerchantMapper;
import com.linmour.system.pojo.Do.Merchant;
import com.linmour.system.pojo.Dto.MerchantPage;
import com.linmour.system.pojo.Dto.UserInfoDto;
import com.linmour.system.service.MerchantService;
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
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.linmour.security.constants.constant.USER_LOGIN_KEY;


/**
 * @author linmour
 * @description 针对表【system_merchant】的数据库操作Service实现
 * @createDate 2023-07-17 20:05:36
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant>
        implements MerchantService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;
    @Resource
    private MerchantMapper merchantMapper;

    @Resource
    private MerchantService merchantService;
    @Resource
    private Tess4jClient tess4jClient;


    @Override
    public Result login(LoginVo loginVo) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getPhone(), loginVo.getPassword());
        //这个方法就会调用我们写的UserDetailsService类进行数据库查询，如果返回null说明用户名错误
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        if (loginUser.getLoginVo().getStatus() == 0) {
            throw new CustomException(AppHttpCodeEnum.ACCOUNT_DISABLE);
        }
        String UserId = loginUser.getLoginVo().getId().toString();
        //用id生成jwt
        String jwt = JwtUtil.createJWT(UserId);
        //获取用户信息

        //把用户信息存入redis
        redisCache.setCacheObject(USER_LOGIN_KEY + UserId, loginUser);
        HashMap<String, Object> map = new HashMap<>();
        UserInfoDto userInfo = getUserInfo(Long.valueOf(UserId));
        map.put("token", jwt);
        map.put("userInfo", userInfo);
        return Result.success(map);
    }

    @Override
    public Result logout(Long id) {
        redisCache.deleteObject(USER_LOGIN_KEY + id);
        return Result.success();
    }

    @Override
    public UserInfoDto getUserInfo(Long userId) {
        Merchant merchant = merchantMapper.selectById(userId);
        if (ObjectUtil.isNull(merchant)) {
            throw new CustomException(AppHttpCodeEnum.USERINFO_ERROR);
        }
        UserInfoDto userInfo = MerchantConvert.IN.MerchantToUserInfoDto(merchant);
        return userInfo;
    }

    @Override
    public Result uploadAvatar(MultipartFile multipartFile) {

        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = user.getLoginVo().getId();
        String fileName = id.toString();
        return Result.success();

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

        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = user.getLoginVo().getId();
        String fileNmae = id.toString();
        return Result.success();

    }

    @Override
    public Long  saveOrUpdateMerchant(Merchant merchant) {
        merchantService.saveOrUpdate(merchant);
        return merchant.getId();
    }

    @Override
    public List<Merchant> getMerchantPage(MerchantPage merchant) {
        return merchantMapper.selectList(new LambdaQueryWrapper<Merchant>().eq(Merchant::getType,2));
    }

    @Override
    public Long save0rUpdateMerchant(Merchant merchant) {
        merchantService.saveOrUpdate(merchant);
        return merchant.getId();
    }

    @Override
    public List<Merchant> getMerchantByIds(List<Long> ids) {
        return merchantMapper.selectBatchIds(ids);

    }


}




