package somun.api.v1.user;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.common.util.LogUtil;
import somun.common.util.RandomUti;
import somun.repository.User;
import somun.repository.UserRepository;


@Slf4j
@Controller
@RequestMapping(path="User/V1/")
@Api(value = "User/V1/", description = "User Service", tags = {"User"})
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Wrong Type Parameter"),
        @ApiResponse(code = 404, message = "Does not exists User"),
        @ApiResponse(code = 500, message = "Server Error")})
public class UserRestService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    @PatchMapping("UpdateUser/{userNo}")
    @ResponseBody
    @ApiOperation(value="",notes = "oauth 사용자 상태를 변경한다.")
    public Integer updateUser(@PathVariable("userNo") Integer userNo , @RequestBody User user){


        user.setUserNo(userNo);
        new LogUtil().printObject(user);
//        User save = userRepository.save(user);

        Integer cnt = userRepository.updateUserStat(user.getUserNo(), user.getUserStat());

        return cnt;
    }


    @Transactional
    @PostMapping("AddUser")
    @ResponseBody
    @ApiOperation(value="",notes = "oauth 등록을 위한 사용자 기본정보")
    public User addUser(@RequestBody User user){

        new LogUtil().printObject(user);

        User findOne = userRepository.findByUserIdAndUserProviderAndUserStat(user.getUserId(),user.getUserProvider(), Codes.USER_STAT_NOMAL);
        if (findOne != null) {
            User copyUser = User.builder().build();

            BeanUtils.copyProperties(findOne ,copyUser);
            copyUser.setUserStat(Codes.USER_STAT_SINGUP);
            return copyUser;
        }

        user.setUserHash(String.valueOf(RandomUti.randomNumber(99999999)));
        return userRepository.save(user);
    }


    @GetMapping("/findUserOne/{userId}/{userProvider}")
    @ResponseBody
    @ApiOperation(value="",notes = "oauth 등록을 위한 사용자 기본정보")
    public User findUserOne(@PathVariable("userId") String userId ,@PathVariable("userProvider")  String userProvider) {

        User user = Optional.ofNullable(userRepository.findByUserIdAndUserProviderAndUserStat(userId, userProvider, Codes.USER_STAT_NOMAL))
                .orElse(User.builder().build());


        return user;
    }



}
