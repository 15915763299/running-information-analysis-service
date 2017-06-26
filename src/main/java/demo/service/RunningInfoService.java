package demo.service;

import demo.domain.RunningInfo;
import demo.domain.RunningInfoResponseFormat;

import java.util.List;

public interface RunningInfoService {

    void saveRunningInfoList(List<RunningInfo> runningInfoList);

    void deleteRunningInfoByRunningId(String runningId);

    void deleteAll();

    RunningInfoResponseFormat findRunningInfoByRunningId(String runningId);

    List<RunningInfoResponseFormat> findAllByUsernameOrderByHeartRateDesc(int page, int size, String username);

    List<RunningInfoResponseFormat> findAllOrderBySingleProperty(int page, int size, String sortDir, String sortBy);

}
