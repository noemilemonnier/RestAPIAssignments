package clientSide;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.AnimalShelter.Models.Dog;
import com.AnimalShelter.Models.User;

import io.jsonwebtoken.Header;

public class ClientSide {

    private  Client client = ClientBuilder.newClient();


    /**This logins us in the server
     * @return
     */
    public String Basicauth(String username, String password) {

        WebTarget webTarget = client.target("http://localhost/SimpleMavenProject/shelter_api/users/login");
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", username);
        formData.add("password", password);
        Response response = webTarget.request().post(Entity.form(formData));
        System.out.println(response);
        //MultivaluedMap<String, Object> res = response.getHeaders();
        String token = response.getHeaderString("Authorization");
        System.out.println(token);
        return token;

    }



    /** We get all the dogs from server
     * @param token
     */
    public void getDogs(String token) {

        WebTarget webTarget = client.target("http://localhost/SimpleMavenProject/shelter_api").path("dogs");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Authorization", token);
        Response response = invocationBuilder.get();
        System.out.println("getting all the dogs");
        //Dog dog = response.readEntity(Dog.class);
        System.out.println(response.getStatus());
        //System.out.println(dog);
        System.out.println(response.readEntity(String.class));

    }


    /**Creating new user to service
     * @param id 
     * @param username
     * @param email
     * @param password
     */
    public void createUser(String id, String username, String email, String password) {
        WebTarget webTarget = client.target("http://localhost/SimpleMavenProject/shelter_api").path("users");
       // User usr = new User(id, username, email, password);
        //usr.getRole().add("user");
        
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", username);
        formData.add("password", password);
        formData.add("email", email);
        formData.add("ID", id);
        formData.add("roles", "admin");
        Response response = webTarget.request().post(Entity.form(formData));
        System.out.println(response);
        
        //Invocation.Builder invocationBuilder =  webTarget.request(MediaType.TEXT_PLAIN);
        //Response response = invocationBuilder.post(Entity.entity(usr, MediaType.APPLICATION_JSON));
        //System.out.println(response.getStatus());
        //System.out.println(response.readEntity(String.class));

    }




    public static void main(String [ ] args)
    {

        
        ClientSide basic = new ClientSide();
        ///Login with admin
        String token = basic.Basicauth("admin","admin");
        // get all the dogs
        basic.getDogs(token);
        
        /**This works also, but if you are using two clients the dogs dont print on console.
         * 
        //Create new user for the service
        ClientSide newuser = new ClientSide();
        newuser.createUser("10", "Dogman","dogman@dog","Dogman");
        
        String token1 = newuser.Basicauth("Dogman", "Dogman");
        newuser.getDogs(token1);
        */
        








    }

}
