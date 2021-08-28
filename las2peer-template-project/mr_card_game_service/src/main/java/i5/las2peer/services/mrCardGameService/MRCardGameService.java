package i5.las2peer.services.mrCardGameService;

import java.net.HttpURLConnection;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import i5.las2peer.api.Context;
import i5.las2peer.api.security.UserAgent;
import i5.las2peer.restMapper.RESTService;
import i5.las2peer.restMapper.annotations.ServicePath;
import io.swagger.annotations.*;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import io.swagger.annotations.Contact;
//import io.swagger.annotations.Info;
//import io.swagger.annotations.License;
//import io.swagger.annotations.SwaggerDefinition;

/**
 * MRCardGameService
 * 
 * This is the backend service for the mixed reality card game and creation tool.
 * 
 * 
 */

@Api
@SwaggerDefinition(
		info = @Info(
				title = "MR card game service",
				version = "1.0.0",
				description = "Backend service of the mixed reality card game and creation tool.",
				termsOfService = "",
				contact = @Contact(
						name = "Anna Perret",
						url = "",
						email = ""),
				license = @License(
						name = "MIT",
						url = "")))
@ServicePath("/saves")
public class MRCardGameService extends RESTService {
	
	@Override
	protected void initResources() {
		getResourceConfig().register(SaveLoadLevel.class);
	}

	/**
	 * Template of a get function.
	 * 
	 * @return Returns an HTTP response with the username as string content.
	 */
	/**@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(
			value = "REPLACE THIS WITH AN APPROPRIATE FUNCTION NAME",
			notes = "REPLACE THIS WITH YOUR NOTES TO THE FUNCTION")
	@ApiResponses(
			value = { @ApiResponse(
					code = HttpURLConnection.HTTP_OK,
					message = "REPLACE THIS WITH YOUR OK MESSAGE") })
	public Response getTemplate() {
		UserAgent userAgent = (UserAgent) Context.getCurrent().getMainAgent();
		String name = userAgent.getLoginName();
		return Response.ok().entity(name).build();
	}

	/**
	 * Template of a post function.
	 * 
	 * @param myInput The post input the user will provide.
	 * @return Returns an HTTP response with plain text string content derived from the path input param.
	 
	@POST
	@Path("/post/{input}")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiResponses(
			value = { @ApiResponse(
					code = HttpURLConnection.HTTP_OK,
					message = "REPLACE THIS WITH YOUR OK MESSAGE") })
	@ApiOperation(
			value = "REPLACE THIS WITH AN APPROPRIATE FUNCTION NAME",
			notes = "Example method that returns a phrase containing the received input.")
	public Response postTemplate(@PathParam("input") String myInput) {
		String returnString = "";
		returnString += "Input " + myInput;
		return Response.ok().entity(returnString).build();
	}

	// TODO your own service methods, e. g. for RMI
	*/

}
