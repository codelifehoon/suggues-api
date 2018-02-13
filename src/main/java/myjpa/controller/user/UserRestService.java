package myjpa.controller.user;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import myjpa.core.util.LogUtil;
import myjpa.repository.User;
import myjpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@Controller
@RequestMapping(path="/User")
@Api(value = "/User", description = "User Service", tags = {"User"})
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Wrong Type Parameter"),
        @ApiResponse(code = 404, message = "Does not exists User"),
        @ApiResponse(code = 500, message = "Server Error")})
public class UserRestService {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("AddUser")
    @ResponseBody
    @ApiOperation(value="",notes = "oauth 등록을 위한 사용자 기본정보")
    public User addUser(@RequestBody User user){

        new LogUtil().printObject(user);

        User save = userRepository.save(user);

        return save;
    }


    @GetMapping("/findUserOne/{userId}/{userProvider}")
    @ResponseBody
    @ApiOperation(value="",notes = "oauth 등록을 위한 사용자 기본정보")
    public User findUserOne(@PathVariable("userId") String userId ,@PathVariable("userProvider")  String userProvider) {

        User user = Optional.ofNullable(userRepository.findByUserIdAndUserProvider(userId, userProvider))
                .orElse(User.builder().build());


        return user;
    }



}
