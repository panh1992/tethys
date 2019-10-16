package org.athena.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.athena.storage.resp.FileResp;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class CommonUtilTest {

    @Test
    public void objectMapper() throws IOException {
        ObjectMapper mapper = CommonUtil.getObjectMapper();

        Map<String, Object> map = Maps.newHashMap();
        map.put("test", 9999999999999D);
        System.out.println(map);
        String mapJson = mapper.writeValueAsString(map);
        System.out.println(mapJson);
        System.out.println(mapper.readValue("{\"test\":9999999999999}", Map.class));

        FileResp fileResp = new FileResp();
        fileResp.setFileId(99999999922222L);
        String respJson = mapper.writeValueAsString(fileResp);
        System.out.println(respJson);
        System.out.println(mapper.readValue("{\"fileId\":99999999922222}", FileResp.class));

        Assert.assertNotNull(mapJson);

    }

}