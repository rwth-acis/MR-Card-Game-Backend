package i5.las2peer.services.mrCardGameService;

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

//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;

//import i5.las2peer.api.Context;
//import i5.las2peer.api.security.UserAgent;
//import i5.las2peer.restMapper.RESTService;
//import i5.las2peer.restMapper.annotations.ServicePath;

// TODO Adjust the following configuration
@Api(value="Save Load", description = "Save Data resources")
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
@Path("/")

// The class that contain the most basic functions to save and load data
public class SaveLoadLevel
{
	
	@GET
	@Path("/{levelName}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get level files",
            notes = "Returns the list of the files that are contained in the level levelName")
    @ApiResponses(
            value = { @ApiResponse(
                    code = HttpURLConnection.HTTP_OK,
                    message = "REPLACE THIS WITH YOUR OK MESSAGE") })
    public Response getLevelFiles(@PathParam("levelName") String levelName) {
		
		    // Create a new save file
            File saveDir = new File("saveData");
            
            // Get the array of the level files that end with json
            File[] levelFiles = saveDir.listFiles((d, name) -> name.endsWith(".json"));
            
            // Create an array of the same length as the level files array
            String[] names = new String[levelFiles.length];
            
            // Go through the level files array
            for (int i=0;i<levelFiles.length;i++)
            {
            	// Initialize the variable containing the full name of the file
                String fullName = levelFiles[i].getName();
                
                // Remove the .json ending and save it in the names array
                names[i] = fullName.substring(0, fullName.length() - 5);
            }
            
            // Get an array that contains the names but is compatible with unity
            try {
                String result = UnityConverter.toUnityCompatibleArray(names);
                return Response.ok().entity(result).build();
            }
            
            // Check if an exception was thrown
            catch (IOException e)
            {
                return Response.serverError().entity(e).build();
            }
    }

	/**
	 * The load data function
	 * 
	 * @param saveName the name of the file that has to be saved
	 * @param levelName the name of the level in which the file to be loaded is located
	 * 
	 * @return Returns the content of the file with name saveName in the saves/levelName path
	 */
	@GET
	@Path("/{levelName}/{saveName}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Loads the data file with the given name and in the level with the given name",
			notes = "Returns the content of the save file")
	@ApiResponses(
			value = { @ApiResponse(
					code = HttpURLConnection.HTTP_OK,
					message = "REPLACE THIS WITH YOUR OK MESSAGE") })
	public Response loadData(@PathParam("levelName") String levelName, @PathParam("saveName") String saveName)
	{
		try
        {
			// Get the string contained in the json file at path saveData/saveName
            String content = new String(Files.readAllBytes(Paths.get("saveData/" + saveName + ".json")));
            
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

	/**
	 * The save data function
	 * 
	 * @param saveName the name of the file that has to be saved
	 * @param levelName the name of the level in which the file to be loaded is located
	 * 
	 * @return Returns an HTTP response that states if the save was successful
	 */
	@POST
	@Path("/{levelName}/{saveName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(
			value = "Saves the data",
			notes = "Returns whether the operation was successful")
	@ApiResponses(
			value = { @ApiResponse(
					code = HttpURLConnection.HTTP_OK,
					message = "The file was saved correctly.") })
	public Response saveData(@PathParam("levelName") String levelName, @PathParam("saveName") String saveName, String text)
	{		
		// Try to print out the given text in a json file with name saveName at the place saveData
		try (PrintWriter out = new PrintWriter("saveData/" + saveName + ".json"))
        {
            out.println(text);
        }
		
		// Check if an exception was thrown
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }

		// Return wether the operation was successfull
        return Response.ok().build();
    }
}
