package cn.bptop.metering.until;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 转json
 */
public class Json
{
    public static String getJson(Object object)
    {
        try
        {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
