package somun.api.v1.content;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import somun.common.util.CommonUtil;
import somun.service.auth.WebCertService;
import somun.service.repository.PushSubscription;
import somun.service.repository.PushSubscriptionModifyRepository;
import somun.service.repository.PushSubscriptionRepository;
import somun.service.repositoryComb.WebCertInfo;

@Slf4j
@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="Subscription/V1/")
@Api(value = "Subscription/V1/", description = "Push Subscription Service", tags = {"Subscription"})
@ApiResponses(value = {
    @ApiResponse(code = 400, message = "Wrong Type Parameter"),
    @ApiResponse(code = 404, message = "Does not exists User"),
    @ApiResponse(code = 500, message = "Server Error")})
public class PushSubscriptionService {


    private PushSubscriptionRepository pushSubscriptionRepository;

    private WebCertService  webCertService;

    private PushSubscriptionModifyRepository pushSubscriptionModifyRepository;

    public PushSubscriptionService(PushSubscriptionRepository pushSubscriptionRepository,
                                   WebCertService webCertService,
                                   PushSubscriptionModifyRepository pushSubscriptionModifyRepository) {
        this.pushSubscriptionRepository = pushSubscriptionRepository;
        this.webCertService = webCertService;
        this.pushSubscriptionModifyRepository = pushSubscriptionModifyRepository;
    }

    @Transactional
    @PostMapping("modifyPushSubscription")
    @ResponseBody
    @ApiOperation(value="",notes = "push를 받기위한 정보 전달")
    public Integer modifyPushSubscription(@CookieValue("webCertInfo") String webCertInfoStr
                                              ,@RequestBody PushSubscription pushSubscription) throws NoSuchAlgorithmException {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);

        pushSubscription.setUserNo(webCertInfo.getUser().getUserNo());
        pushSubscription.setCreateNo(webCertInfo.getUser().getUserNo());
        pushSubscription.setCreateDt(toDay);
        pushSubscription.setUpdateNo(webCertInfo.getUser().getUserNo());
        pushSubscription.setUpdateDt(toDay);
        pushSubscription.setUseYn("Y");

        pushSubscription.setEndPointHash(CommonUtil.bytesToHex(MessageDigest
                                                                   .getInstance("SHA-256")
                                                                   .digest(pushSubscription.getEndPoint().getBytes(StandardCharsets.UTF_8))));


        PushSubscription orgSubscription = Optional.ofNullable(pushSubscriptionRepository.findByUserNoAndUseYn(webCertInfo.getUser().getUserNo(), "Y"))
                                     .orElse(PushSubscription.builder().userNo(0).build());

        if (orgSubscription.getUserNo() > 0) {

            // 동일한 EndPoint 값이 라면 업데이트 없이 리턴
            if (orgSubscription.getEndPointHash().equals(pushSubscription.getEndPointHash())) return 0;

            orgSubscription.setEndPoint(pushSubscription.getEndPoint());
            orgSubscription.setEndPointHash(pushSubscription.getEndPointHash());

            pushSubscriptionModifyRepository.updateSubscription(orgSubscription);
            return orgSubscription.getPushSubscriptionNo();

        }

        return pushSubscriptionModifyRepository.save(pushSubscription)
                                         .getPushSubscriptionNo();
    }





}
