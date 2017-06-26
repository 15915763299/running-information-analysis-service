package demo.controller;

import demo.domain.RunningInfo;
import demo.domain.RunningInfoResponseFormat;
import demo.service.RunningInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RunningInfoController {

    private static final String DEFAULT_PAGE_NUMBER = "0";
    private static final String DEFAULT_PAGE_SIZE = "2";
    private static final String DEFAULT_SORT_DIR = "desc";
    private static final String DEFAULT_SORT_BY = "heartRate";

    private RunningInfoService runningInfoService;

    @Autowired
    public RunningInfoController(RunningInfoService runningInfoService) {
        this.runningInfoService = runningInfoService;
    }

    //Add
    @RequestMapping(value = "/runningInfo/uploadInfoList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadInfoList(@RequestBody List<RunningInfo> runningInfoList){
        this.runningInfoService.saveRunningInfoList(runningInfoList);
    }

    //Delete
    @RequestMapping(value = "/runningInfo/purge", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void purge(){
        this.runningInfoService.deleteAll();
    }

    @RequestMapping(value = "/runningInfo/{runningId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteRunningInfoByRunningId(@PathVariable String runningId){
        this.runningInfoService.deleteRunningInfoByRunningId(runningId);
    }

    //Find
    @RequestMapping(value = "/runningInfo/findByRunningId/{runningId}", method = RequestMethod.GET)
    public ResponseEntity<?> findRunningInfoByRunningId(@PathVariable String runningId){
        RunningInfoResponseFormat runningInfo = this.runningInfoService.findRunningInfoByRunningId(runningId);
        if(runningInfo == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(runningInfo);
    }

    @RequestMapping(value = "/runningInfo/findByUsername/{username}", method = RequestMethod.GET)
    public List<RunningInfoResponseFormat> findAllByUserName(
            @PathVariable String username,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size
    ){
        return this.runningInfoService.findAllByUsernameOrderByHeartRateDesc(page, size, username);
    }

    @RequestMapping(value = "/runningInfo", method = RequestMethod.GET)
    public List<RunningInfoResponseFormat> findAllOrderBySingleProperty(
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "sortDir", required = false, defaultValue = DEFAULT_SORT_DIR) String sortDir,
            @RequestParam(value = "sortBy", required = false, defaultValue = DEFAULT_SORT_BY) String sortBy
    ){
        return this.runningInfoService.findAllOrderBySingleProperty(page, size, sortDir, sortBy);
    }
}
