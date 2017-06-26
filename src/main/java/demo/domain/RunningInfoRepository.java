package demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RunningInfoRepository extends JpaRepository<RunningInfo, String>{

    void deleteRunningInfoByRunningId(@Param("runningId") String runningId);

    RunningInfo findRunningInfoByRunningId(@Param("runningId") String runningId);

    Page<RunningInfo> findByUserInfoUsernameOrderByHeartRateDesc(@Param("username") String username, Pageable pageable);

    Page<RunningInfo> findAll(Pageable pageable);
}
