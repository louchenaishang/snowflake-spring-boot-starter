package person.louchen.snowflake.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by louchen on 2017/5/31.
 */
@ConfigurationProperties("snowflake")
@Getter
@Setter
public class SnowflakeProperties {

    private Boolean enabled;

    private Integer timeBits;
    private Integer workerBits;
    private Integer seqBits;
    private String epochStr;

}
