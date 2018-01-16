package org.springframework.github;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ComponentTestBase {

    @Autowired
    private ObjectMapper objectMapper;

    public String serializeJSON(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

}
