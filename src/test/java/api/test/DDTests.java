package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {
	
	//if data  provider present in same class no need to provide in test annotation
	
	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class )
	public void testPostUserUsingExcel(String userID,String userName,String fname,String lname,String useremail,String pwd,String ph) {
		
		
			
		
	 User userPayload=new User();
	 
	 userPayload.setId(Integer.parseInt(userID));
	 userPayload.setUsername(userName);
	 userPayload.setFirstName(fname);
	 userPayload.setLastName(lname);
	 userPayload.setEmail(useremail);
	 userPayload.setPassword(pwd);
	 userPayload.setPhone(ph);
	 
	 Response response=UserEndPoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	

}
	@Test( priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class )
	public void testDeleteUserByName(String userName ) {
		
		Response responseAfterDelete =	UserEndPoints.deleteUser(userName);
		responseAfterDelete.then().log().all();
		Assert.assertEquals(responseAfterDelete.getStatusCode(), 200);
		
	}

@Test(enabled = false,priority = 2)
public void testGetUserByName(String userName) {
	
		try{
			Response responseFromReadUser =	UserEndPoints.readUser(userName);
			responseFromReadUser.then().log().all();
			Assert.assertEquals(responseFromReadUser.getStatusCode(), 200);
			} 
		catch(Exception e)
		{
		         System.out.println("Exception :" +e);
		         
				     }
			
		}
			
}
