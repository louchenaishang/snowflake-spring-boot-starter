package person.louchen.snowflake;

import person.louchen.snowflake.shadow.uid.exception.UidGenerateException;

/**
 * Created by louchen on 2017/5/31.
 */
public interface SnowflakeService {

    /**
     * Get a unique ID
     *
     * @return UID
     * @throws UidGenerateException
     */
    long getUID() throws UidGenerateException;

    /**
     * Parse the UID into elements which are used to generate the UID. <br>
     * Such as timestamp & workerId & sequence...
     *
     * @param uid
     * @return Parsed info
     */
    String parseUID(long uid);
}
