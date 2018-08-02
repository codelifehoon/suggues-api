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
import somun.batch.WorkflowBatch;

@Slf4j
@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="Cron/V1/")
@Api(value = "Cron/V1/", description = "Cron Service", tags = {"Cron"})
@ApiResponses(value = {
                        @ApiResponse(code = 400, message = "Wrong Type Parameter"),
                        @ApiResponse(code = 404, message = "Does not exists User"),
                        @ApiResponse(code = 500, message = "Server Error")})
public class CronController {


    @Autowired
    WorkflowBatch workflowBatch;

    @GetMapping("/updateVisitKoreaSearchDoc")
    @ResponseBody
    @ApiOperation(value="",notes = "VisitKorea 갱신 및 검색 갱신")
    public int updateVisitKoreaSearchDoc() {
        workflowBatch.updateVisitKoreaSearchDoc();
        return 1;
    }

}
