package i5.las2peer.services.mrCardGameService;

import io.swagger.annotations.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

// The class used to convert arrays to be readable in unity
public class UnityConverter
{
	/**
     * Creates a JSON array which is compatible to Unity's JSON parser
     * @param obj The object to convert to a JSON string
     * @return Returns the JSON string which represents the given array
     * @throws JsonProcessingException Exception if the conversion to JSON failed
     */
    public static String toUnityCompatibleArray(Object obj) throws JsonProcessingException
    {
    	// Initialize an object mapper
    	ObjectMapper mapper = new ObjectMapper();
    	
    	// Initialize a writer with the name array
    	ObjectWriter writer = mapper.writer().withRootName("array");
    	
    	// Return the written values as string of the given object
        return writer.writeValueAsString(obj);
    }
}
