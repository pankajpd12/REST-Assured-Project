package Tests;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.baseURI;

import static org.hamcrest.Matchers.equalTo;

public class SoapXMLRequest {
	
	
	@Test
	public void validateSoapXML() throws IOException {
		
		File file = new File("./SoapRequest/add.xml");
		
		if(file.exists())
			System.out.println("   >>     File Exists");
		
		FileInputStream fileInputStream = new FileInputStream(file);
		String requestBody = IOUtils.toString(fileInputStream, "UTF-8");
		
		baseURI = "http://www.dneonline.com/";
		
		given().
		  contentType("text/xml").
		  accept(ContentType.XML).
		  body(requestBody).
		when().
		  post("/calculator.asmx").
        then().
          statusCode(200).log().all().
        and().                     // To validate SOAP xml Response body
          body("//*:AddResult.text()", equalTo("30"));
		
	}

	

}
