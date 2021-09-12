package i5.las2peer.services.mRCardGameService;

import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

import java.nio.charset.StandardCharsets;

// TODO Describe your own service
/**
 * las2peer-Template-Service
 * 
 * This is a template for a very basic las2peer service that uses the las2peer WebConnector for RESTful access to it.
 * 
 * Note: If you plan on using Swagger you should adapt the information below in the SwaggerDefinition annotation to suit
 * your project. If you do not intend to provide a Swagger documentation of your service API, the entire Api and
 * SwaggerDefinition annotation should be removed.
 * 
 */
// TODO Adjust the following configuration
@Api
@SwaggerDefinition(
		info = @Info(
				title = "las2peer Template Service",
				version = "1.0.0",
				description = "A las2peer Template Service for demonstration purposes.",
				termsOfService = "http://your-terms-of-service-url.com",
				contact = @Contact(
						name = "John Doe",
						url = "provider.com",
						email = "john.doe@provider.com"),
				license = @License(
						name = "your software license name",
						url = "http://your-software-license-url.com")))
@ServicePath("/template")
// TODO Your own service class
public class MRCardGameService extends RESTService
{
	/**
	 * Template of a get function.
	 * 
	 * @return Returns an HTTP response with the username as string content.
	 */
	@GET
	@Path("/get")
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
	 */
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
	@GET
	@Path("/ping")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(
			value = "Check if the server is online",
			notes = "Returns a string")
	@ApiResponses(
			value = { @ApiResponse(
					code = HttpURLConnection.HTTP_OK,
					message = "REPLACE THIS WITH YOUR OK MESSAGE") })
	public Response ping() {
		System.out.println("Ping received");
		return Response.ok().entity("pong").build();
	}                                 
	
	// TODO your own service methods, e. g. for RMI	
	
	// The method used to get all the levels that were saved
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
	    value = "Get Levels",
	    notes = "Returns the list of projects which are saved on the server")
	@ApiResponses(
	    value = { @ApiResponse(
	        code = HttpURLConnection.HTTP_OK,
	        message = "REPLACE THIS WITH YOUR OK MESSAGE") })
	public Response getLevels() {
		
		// Get the save directory file
	    File saveDir = new File("saveLevel");
	    
	    // Get the array of all directories
	    File[] saveFiles = saveDir.listFiles();
	    
	    String[] levelNames = new String[saveFiles.length];
	    
	    for(int index = 0; index < saveFiles.length; index = index + 1) 
	    {
	    	levelNames[index] = saveFiles[index].getName();
	    }
	         
	    // Try to convert the array and return it
	    try 
	    {
	        String result = UnityConverter.toUnityCompatibleArray(levelNames);
	        return Response.ok().entity(result).build();
	    }
	         
	    // Catch the exception if one was thrown
	    catch (IOException e)
	    {
	        return Response.serverError().entity(e).build();
	    }
	}
	
	@GET
	@Path("/{levelName}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get the files in a level",
            notes = "Returns the list of projects which are saved on the server")
    @ApiResponses(
            value = { @ApiResponse(
                    code = HttpURLConnection.HTTP_OK,
                    message = "REPLACE THIS WITH YOUR OK MESSAGE") })
    public Response getFilesInLevel(@PathParam("levelName") String levelName)
	{
		// Get the level directory
		File levelDir = new File("saveLevel/" + levelName);
		
		System.out.println("Trying to access the files in the directory: " + levelDir);
		
		// Get the array of json files in the level directory
        File[] saveFiles = levelDir.listFiles((d, name) -> name.endsWith(".json"));
        
        // Initialize the array of names with the length of the save files array
        String[] names = new String[saveFiles.length];
        
        // Go through all the save files
        for (int i=0;i<saveFiles.length;i++)
        {
        	// Extract the name of the file
            String fullName = saveFiles[i].getName();
            
            // Remove the .json ending
            names[i] = fullName.substring(0, fullName.length() - 5);
        }
        
        // Try to convert it to a unity compatible array and return it
        try
		{
            String result = UnityConverter.toUnityCompatibleArray(names);
            return Response.ok().entity(result).build();
        }
        
        // Catch the exception if it was thrown
        catch (IOException e)
        {
            return Response.serverError().entity(e).build();
        }
    }
	
	
	 // The method used to load a file
	@GET
	@Path("/{levelName}/{fileName}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Loads the data file with the given name and in the level with the given name1",
			notes = "Returns the content of the save file1")
	@ApiResponses(
			value = { @ApiResponse(
					code = HttpURLConnection.HTTP_OK,
					message = "REPLACE THIS WITH YOUR OK MESSAGE") })
	public Response loadData(@PathParam("levelName") String levelName, @PathParam("fileName") String fileName)
	{
		// Get the path to the file to load
		String filePath = "saveLevel/" + levelName + "/" + fileName + ".json";
		
		System.out.println("Trying to access the file: " + filePath);
		
		try
        {
			// Get the string contained in the json file at path saveData/saveName
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            
            System.out.println("The text that was found in the file was: " + content);
            
            // Return the file content
            return Response.ok().entity(content).build();
        }
		
		// Check if an exception was thrown because the file could not be found
        catch (FileNotFoundException e)
        {
            return Response.ok().entity("{ }").build();
        }
		
		// Check if an exception was thrown because the server could not be reached
        catch (IOException e)
        {
            return Response.serverError().entity(e).build();
        }
	}
	
	// The method used to save a file
	@POST
	@Path("/{levelName}/{fileName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Saves the data4",
			notes = "Returns whether the operation was successful4")
	@ApiResponses(
			value = { @ApiResponse(
					code = HttpURLConnection.HTTP_OK,
					message = "The file was saved correctly.") })
	public Response saveData(@PathParam("levelName") String levelName, @PathParam("fileName") String fileName, byte[] givenArray)
	{		
		// Get the path to the file that should be created
		String directoryPath = "saveLevel/" + levelName;
		String filePath = directoryPath + "/" + fileName + ".json";
		
		// Get the directory in which the file should be saved
		File directory = new File(directoryPath);
		
		// Check if the directories leading to it and including it exist
		if (!directory.exists())
		{
			// Create it if it does not exist
			directory.mkdirs();
		}
		 
		// Convert the byte array to a string
		String content = new String(givenArray, StandardCharsets.UTF_8);
		 
		System.out.println("Trying to create a file: " + filePath);
		
		// Try to print out the given text in a json file with name saveName at the right path
		try (PrintWriter out = new PrintWriter(filePath))
        {
            out.println(content);
            System.out.println("The text that was given was: " + content);
        }
		
		// Check if an exception was thrown
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }

		// Return whether the operation was successful
        return Response.ok().build();
    }
	
	// The ping method
	@GET
	@Path("/write")
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(
			value = "Check if the server is online",
			notes = "Returns a string")
	@ApiResponses(
			value = { @ApiResponse(
					code = HttpURLConnection.HTTP_OK,
					message = "REPLACE THIS WITH YOUR OK MESSAGE") })
	public void writeLine() {
		System.out.println("Ping received");
	}
}
