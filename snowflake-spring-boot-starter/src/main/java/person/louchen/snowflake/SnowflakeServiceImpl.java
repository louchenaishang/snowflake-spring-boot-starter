package person.louchen.snowflake;

import org.springframework.stereotype.Service;
import person.louchen.snowflake.shadow.uid.UidGenerator;
import person.louchen.snowflake.shadow.uid.exception.UidGenerateException;

import javax.annotation.Resource;

/**
 * Created by louchen on 2017/5/31.
 */
@Service
public class SnowflakeServiceImpl implements SnowflakeService {

    @Resource(name = "defaultUidGenerator")
    private UidGenerator uidGenerator;

    @Override
    public long getUID() throws UidGenerateException {
        return uidGenerator.getUID();
    }

    @Override
    public String parseUID(long uid) {
        return uidGenerator.parseUID(uid);
    }

}
