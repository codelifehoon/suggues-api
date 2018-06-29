package somun.api.v1.hello;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import somun.common.util.LogUtil;
import somun.service.repository.user.AddressRepository;
import somun.service.repository.user.User;
import somun.service.repository.user.UserRepository;


@Slf4j
@Controller
@RequestMapping(path="/Sample/User")
@Api(value = "/Sample/User", description = "User Service Sample", tags = {"UserSample"})
@ApiResponses(value = {
        @ApiResponse(code = 400, message = "Wrong Type Parameter"),
        @ApiResponse(code = 404, message = "Does not exists User"),
        @ApiResponse(code = 500, message = "Server Error")})
public class UserRestSampleService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    EntityManagerFactory emf;

    @GetMapping("/User/FindAll/{size}/{pageNo}")
    @ResponseBody
    @ApiOperation(value = "none",notes = "User Info select")
    public  Page<User> list(@PathVariable("size") Integer size, @PathVariable("pageNo") Integer pageNo)
    {
        PageRequest  request = new PageRequest(pageNo,size);

        Page<User> userPage = userRepository.findAll(request);


        return userPage;
    }

    @ResponseBody
    @RequestMapping(value="/User/FindAll/pageAuto" , method =  RequestMethod.GET)
    @ApiOperation(value = "none",notes = "User Info select")
//    http://localhost:8080/User/User/FindAll/pageAuto?size=5&sort=userNm,desc
//    @GetMapping("/User/FindAll/pageAuto")
    public  Page<User> listPageAuto(@Param("cnt") Integer cnt , @PageableDefault(size=10,sort="userNm",direction = Sort.Direction.ASC ) Pageable pageable) throws RuntimeException {
        Page<User> userPage = userRepository.findAll(pageable);
//        log.error("#####cnt : " + cnt);
//        if (cnt == 100) try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//
//        if (cnt == 200) throw new RuntimeException("##### RuntimeException");

        log.debug("#### hot swapping 222####");

        return userPage;
    }


    @GetMapping("/User/Get/{userNo}")
    @ResponseBody
    @ApiOperation(value = "사용자 정보 단건 조회 ",notes = "User Info select")
    public  User list(@PathVariable("userNo")User user)
    {
        new LogUtil().printObject(user);
        return user;
    }



    @PostMapping("/Add")
    @ResponseBody
    @ApiOperation(value = "사용자 추가",notes = "User Add")
    @Transactional(rollbackFor=Exception.class)
    public  User save(User user) throws Exception {

/*
        Address address = user.getAddress();
        user.setAddress(null);

        user  = userRepository.save(user);
        new LogUtil().printObject(user);


        address.setUserNo(user.getUserNo());

        if (1==1) throw new Exception("exption");
        address = addressRepository.save(address);
        new LogUtil().printObject(address);


        user.setAddress(address);
*/


        return user;
    }


    @PutMapping("/Del/{userNo}")
    @ResponseBody
    @ApiOperation(value = "사용자 삭제",notes = "User Del")
    public  String del(User user)
    {
        userRepository.delete(user);
        return "success";
    }


    @GetMapping("/User/ResponseEntityTest")
    public ResponseEntity<String> ResponseEntityTest() {

        String ret = "success";
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }




//
//    @ResponseBody
//    @RequestMapping(value="/User/FindAll/listModel" , method =  RequestMethod.GET)
//    @ApiOperation(value = "none",notes = "User Info select")
//    public String listModel(Model model )
//    {
//        Iterable<User> userPage = userRepository.findAll();
//
//        model.addAttribute("userList",userPage);
//        return "userList";
//    }


//
//    @GetMapping("/User/GetResponseEntity/{userNo}")
//    @ResponseBody
//    @ApiOperation(value = "사용자 정보 단건 조회 ",notes = "User Info select")
//    public  ResponseEntity<Page<User> >  listResponseEntity(@PathVariable("userNo")User user)
//    {
//        PageRequest  request = new PageRequest(0,5);
//        Page<User> userPage = userRepository.findAll(request);
//        return new ResponseEntity<>(userPage, HttpStatus.OK);
//    }


}
