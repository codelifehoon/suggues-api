package somun.api.v1.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import somun.batch.VisitKoreaBatch;

@Slf4j
@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="Provider/V1/")
@Api(value = "Provider/V1/", description = "Provider Service", tags = {"Provider"})
@ApiResponses(value = {
                        @ApiResponse(code = 400, message = "Wrong Type Parameter"),
                        @ApiResponse(code = 404, message = "Does not exists User"),
                        @ApiResponse(code = 500, message = "Server Error")})
public class VisitKoreaService {


    @Autowired
    VisitKoreaBatch visitKoreaBatch;

    @GetMapping("/getVisitKoreaContent")
    @ResponseBody
    @ApiOperation(value="",notes = "VisitKorea 자료 추출")
    public int getVisitKoreaContent() {
        return visitKoreaBatch.getVisitKoreaContent();
    }

    @GetMapping("/regVisitKoreaContentToEventContent")
    @ResponseBody
    @ApiOperation(value="", notes = "VisitKorea 자료 반영")
    public long regVisitKoreaContentToEventContent() {
        return visitKoreaBatch.regVisitKoreaContentToEventContent();
    }

}
