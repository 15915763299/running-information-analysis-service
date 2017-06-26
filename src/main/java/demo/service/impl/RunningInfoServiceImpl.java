package demo.service.impl;

import demo.domain.*;
import demo.service.RunningInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RunningInfoServiceImpl implements RunningInfoService{

    private RunningInfoRepository runningInfoRepository;

    @Autowired
    public RunningInfoServiceImpl(RunningInfoRepository runningInfoRepository){
        this.runningInfoRepository = runningInfoRepository;
    }

    @Override
    public void saveRunningInfoList(List<RunningInfo> runningInfoList) {
        this.runningInfoRepository.save(runningInfoList);
    }

    @Override
    @Transactional
    public void deleteRunningInfoByRunningId(String runningId) {
        this.runningInfoRepository.deleteRunningInfoByRunningId(runningId);
    }

    @Override
    public void deleteAll() {
        this.runningInfoRepository.deleteAll();
    }

    @Override
    public RunningInfoResponseFormat findRunningInfoByRunningId(String runningId) {
        RunningInfo runningInfo = this.runningInfoRepository.findRunningInfoByRunningId(runningId);
        if(runningInfo == null) return null;
        return new RunningInfoResponseFormat(runningInfo);
    }

    @Override
    public List<RunningInfoResponseFormat> findAllByUsernameOrderByHeartRateDesc(int page, int size, String username) {
        Pageable pageable = new PageRequest(page, size);
        return formatResult(this.runningInfoRepository.findByUserInfoUsernameOrderByHeartRateDesc(username, pageable));
    }

    @Override
    public List<RunningInfoResponseFormat> findAllOrderBySingleProperty(int page, int size, String sortDir, String sortBy) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.fromString(sortDir.toLowerCase()), sortBy);
        return formatResult(this.runningInfoRepository.findAll(pageable));
    }

    private List<RunningInfoResponseFormat> formatResult(Page<RunningInfo> runningInfo){
        List<RunningInfo> runningInfoContents = runningInfo.getContent();
        List<RunningInfoResponseFormat> runningInfoResponseFormats = new ArrayList<>();
        for(RunningInfo item : runningInfoContents){
            runningInfoResponseFormats.add(new RunningInfoResponseFormat(item));
        }
        return runningInfoResponseFormats;
    }

}
