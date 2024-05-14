package api.test;

import static org.testng.Assert.assertEquals;

import org.apache.http.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoint2;
import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	
	Faker faker;
	User userpayload;
	public Logger logger;  // for logs
	
	@BeforeClass
	public void setup() {
		
		faker=new Faker();
		userpayload =new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5,10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger=LogManager.getLogger(this.getClass());
		
	}
	
	@Test(priority = 1)
	public void testPostUser() {
		
		logger.info("********Creating an user********");
		
	Response response=UserEndPoint2.createUser(userpayload);
		response.then().log().all();
		
		System.out.println(response.asPrettyString());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("********Created an user********");
	}
	
	
	@Test(priority = 2)
	public void testGetUserByName() {
		
		logger.info("********Getting an user********");
		
		
		try{
			Response responseFromReadUser =	UserEndPoint2.readUser(this.userpayload.getUsername());
			responseFromReadUser.then().log().all();
			Assert.assertEquals(responseFromReadUser.getStatusCode(), 200);
			} catch(Exception e) {
			     //process exception
//			     if(e instanceof HttpStatusCodeException){
//			        String responseText=((HttpStatusCodeException)e).getResponseBodyAsString();
//			         //now you have the response, construct json from it, and extract the errors
			         System.out.println("Exception :" +e);
			     }
		logger.info("********User retrieved********");
		
	}
	
	@Test(priority = 3)
	public void testUpdateUserByName() {
		
		//update data using payload
		
		logger.info("********updating an user********");
		
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
		Response responseBeforeUpdate=UserEndPoint2.updateUser(this.userpayload.getUsername(),userpayload);
		responseBeforeUpdate.prettyPrint();
		
		Assert.assertEquals(responseBeforeUpdate.getStatusCode(), 200);
		
		logger.info("********user is updated********");
		//checking data after update
		
		Response responseAfterUpdate =	UserEndPoint2.readUser(this.userpayload.getUsername());
		responseAfterUpdate.then().log().all();
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
		logger.info("********Verifying updated  user********");
		
	}
	
	@Test(priority = 4)
	public void testDeleteUserByName() {
		
		logger.info("********Before delete an user********");
		
		Response responseAfterDelete =	UserEndPoint2.deleteUser(this.userpayload.getUsername());
		responseAfterDelete.then().log().all();
		Assert.assertEquals(responseAfterDelete.getStatusCode(), 200);
		 
		logger.info("********After delete the user********");
		
	}

}
