package demo.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
@Table(name = "RUNNING_ANALYSIS")
public class RunningInfo {

    public enum HealthWarningLevel{
        LOW, NORMAL, HIGH, ABNORMAL;

        public static HealthWarningLevel getHealthWarningLevel(int heartRate){
            if(heartRate >= 60 && heartRate <= 75){
                return HealthWarningLevel.LOW;
            }else if(heartRate > 75 && heartRate <= 120){
                return HealthWarningLevel.NORMAL;
            }else if(heartRate > 120){
                return HealthWarningLevel.HIGH;
            }else{
                return HealthWarningLevel.ABNORMAL;
            }
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//auto increase primary key
    private Long id;

    private String runningId;
    private double latitude;
    private double longitude;
    private double runningDistance;//meter
    private double totalRunningTime;//second
    private int heartRate = 0;
    private Date timestamp;
    private HealthWarningLevel healthWarningLevel;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="username", column = @Column(name="userName")),
            @AttributeOverride(name="address", column = @Column(name="userAddress"))
    })
    private UserInfo userInfo;

    public RunningInfo() {
        this.userInfo = null;
    }

    public RunningInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @JsonCreator
    public RunningInfo(@JsonProperty("runningId") String runningId,
                       @JsonProperty("latitude") String latitude,
                       @JsonProperty("longitude") String longitude,
                       @JsonProperty("runningDistance") String runningDistance,
                       @JsonProperty("totalRunningTime") String totalRunningTime,
                       @JsonProperty("heartRate") String heartRate,
                       @JsonProperty("timestamp") String timestamp,
                       @JsonProperty("userInfo") UserInfo userInfo) {
        this.runningId = runningId;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.runningDistance = Double.parseDouble(runningDistance);
        this.totalRunningTime = Double.parseDouble(totalRunningTime);
        this.heartRate = getRandomHeartRate(60, 200);
        this.healthWarningLevel = HealthWarningLevel.getHealthWarningLevel(this.heartRate);
        this.userInfo = userInfo;
    }

    public String getUserName(){
        return this.userInfo == null ? null : this.userInfo.getUsername();
    }

    public String getUserAddress(){
        return this.userInfo == null ? null : this.userInfo.getAddress();
    }

    private int getRandomHeartRate(int min, int max){
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
