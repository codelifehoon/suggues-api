package somun.api.v1.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import somun.common.service.JwtService;
import somun.common.util.LogUtil;
import somun.common.util.RandomUti;
import somun.service.repository.user.UserModifyRepository;
import somun.service.repository.user.UserRepository;
import somun.service.repository.vo.user.User;
import somun.service.repository.vo.user.UserForLogin;

@Slf4j
@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="User/V1/")
@Api(value = "User/V1/", description = "User Service", tags = {"User"})
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Wrong Type Parameter"),
        @ApiResponse(code = 404, message = "Does not exists User"),
        @ApiResponse(code = 500, message = "Server Error")})
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserModifyRepository userModifyRepository;

    @Autowired
    JwtService jwtService;



    // TODO User에 updateDt 추가 및 변경 해야 한다.
//    @Transactional
    @PatchMapping("UpdateUser/{userNo}")
    @ResponseBody
    @ApiOperation(value="",notes = "oauth 사용자 상태를 변경한다.")
    public Integer updateUser(@PathVariable("userNo") Integer userNo , @RequestBody User user){

        user.setUserNo(userNo);
        new LogUtil().printObject(user);
//        User save = userRepository.save(user);

        Integer cnt = userModifyRepository.updateUserStat(user.getUserNo(), user.getUserStat());

        return cnt;
    }


    // TODO 로그인시 AddUser 사용하는데 기 등록된 사용자에 대해서 로그인 history 추가하는 해야함 , Hash값 구하는부분 수정 해야함.
//    @Transactional
    @PostMapping("AddUser")
    @ResponseBody
    @ApiOperation(value="",notes = "oauth 등록을 위한 사용자 기본정보")
    public UserForLogin addUser(@RequestBody User user){

//        new LogUtil().printObject(user);

        User findOne = userRepository.findByUserIdAndUserProviderAndUserStat(user.getUserId(),user.getUserProvider(), Codes.USER_STAT.S1);
        if (findOne != null) {


            return UserForLogin.builder()
                               .userStat(Codes.USER_STAT.S9)
                               .userHash(jwtService.createJwt("data",findOne.getUserHash()))
                               .build();
        } else {
            user.setUserHash(new String(user.getUserId() + user.getUserProvider()).hashCode() + String.valueOf(RandomUti.randomString(9)));
            User save = userModifyRepository.save(user);

            return UserForLogin.builder()
                               .userStat(save.getUserStat())
                               .userHash(jwtService.createJwt("data",save.getUserHash()))
                               .build();
        }
    }


    @GetMapping("/findUserOne/{userId}/{userProvider}")
    @ResponseBody
    @ApiOperation(value="",notes = "oauth 등록을 위한 사용자 기본정보")
    public User findUserOne(@PathVariable("userId") String userId ,@PathVariable("userProvider")  String userProvider) {

        User user = Optional.ofNullable(userRepository.findByUserIdAndUserProviderAndUserStat(userId, userProvider, Codes.USER_STAT.S1))
                .orElse(User.builder().build());


        return user;
    }



}
