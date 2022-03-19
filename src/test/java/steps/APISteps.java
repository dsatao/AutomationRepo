package steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.node.ArrayNode;
import io.cucumber.java.en.Given;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import utils.BasePage;
import utils.FileUtility;
import utils.RestApiServices;

/*
 * @author : Jayesh.Hinge
 */

public class APISteps extends BasePage {

	Map<String, String> headers = new HashMap<String, String>();
	Map<String, String> queryParam = new HashMap<String, String>();
	Map<String, String> pathParam = new HashMap<String, String>();

	Object resolveProperty(String var) {
		String resolvedValue = "";
		if (var.startsWith("#$")) {
			if (prop1.containsKey(var.substring(2))) {
				resolvedValue = prop1.getProperty(var);
				log.info("Property value found for [" + var.substring(2) + "] and resolved value is [" + resolvedValue
						+ "]");
			} else {
				resolvedValue = var.substring(2);
				log.info("Resolved value is [" + resolvedValue + "]");
			}
		} else if (StringUtils.isBlank(var)) {
			log.info("Resolved value is null please pass some input in the step.");
		} else {
			resolvedValue = var;
			log.info("Resolved value is [" + resolvedValue + "]");
		}
		return resolvedValue;
	}

	@Given("^Set API testing environment \"([^\"]*)\"$")
	public void Set_API_testing_environment(String url) {
		String baseUrl = (String) resolveProperty(url);
		// APIVars.put(APIVariables.URL, baseUrl);
		APIVars.put("URL", baseUrl);
	}

	@Given("^Set Path \"([^\"]*)\"$")
	public void Set_Path(String path) {
		String finalPath = (String) resolveProperty(path);
		// APIVars.put(APIVariables.PATH, finalPath);
		APIVars.put("PATH", finalPath);
	}

	@Given("^Set Method type \"([^\"]*)\"$")
	public void Set_Method_type(String method) {
		APIVars.put("METHOD", method.toUpperCase());
	}

	@Given("^Set header \"([^\"]*)\" as \"([^\"]*)\"$")
	public void Set_header_as(String key, String value) {
		headers.put(key, value);
		log.info("Header contains [" + headers + "]");
		APIVars.put("HEADERS", headers);
	}

	@Given("^Set url path paramter \"([^\"]*)\" as value \"([^\"]*)\"$")
	public void Set_url_path_paramter_as_value(String key, String value) {
		pathParam.put(key, value);
		log.info("Path  parameter contains [" + pathParam + "]");
		APIVars.put("PATH_PARAM", pathParam);
	}

	@Given("^Set query parameter \"([^\"]*)\" as \"([^\"]*)\"$")
	public void Set_query_parameter_as(String key, String value) {
		queryParam.put(key, value);
		log.info("Query parameter contains [" + queryParam + "]");
		APIVars.put("QUERY_PARAM", queryParam);
	}

	@Given("^Set header with oauth bearer token \"([^\"]*)\"$")
	public void Set_header_with_oauth_bearer_token(String token) {
		headers.put("Authorization", "Bearer " + token);
		APIVars.put("HEADERS", headers);
	}

	@Given("^Set request body from variable \"([^\"]*)\"$")
	public void Set_request_body_from_variable(String body) {
		APIVars.put("REQUEST_BODY", body);
	}

	@Given("^Set request body parameter \"([^\"]*)\" as \\\"([^\\\"]*)\\\"$")
	public void Set_request_body_parameter_as(String key, String value) {
		requestBody.put(key, value);
	}

	@Given("^Set request body parameter \"([^\"]*)\"$")
	public void Set_request_body_parameter_as(DataTable table) {
		FileUtility.readDataTableAndReturnMap(table, log, requestBody);
		APIVars.put("REQUEST_BODY", requestBody);
	}

	@Given("^Load header from file \"([^\"]*)\"$")
	public void Load_header_from_file(String filepath) {
		APIVars.put("HEADERS", FileUtility.loadFileIntoString(filepath, log));
	}

	@Given("^Load query paramters from file \"([^\"]*)\"$")
	public void Load_query_paramters_from_file(String filepath) {
		APIVars.put("QUERY_PARAM", FileUtility.loadFileIntoString(filepath, log));
	}

	@Given("^Load and set payload from file \"([^\"]*)\" using path$")
	public void Load_and_set_payload_from_file_using_path(String filepath) {
		APIVars.put("REQUEST_BODY", FileUtility.updateValuesInFile(filepath, requestBody, log));
	}

	@Given("^Load and set payload from file \"([^\"]*)\" using absolute path$")
	public void Load_and_set_payload_from_file_using_absolute_path(String filepath) {
		APIVars.put("REQUEST_BODY", FileUtility.replaceValuesInFile(filepath, requestBody, log));
	}

	@Given("^Make \"([^\"]*)\" call and save response body text as variable \"([^\"]*)\"$")
	public void Make_call_and_save_response_body_text_as_variable(String method, String var) {
		RestApiServices ra = new RestApiServices();
		Response response = null;
		switch (method) {
		case "POST":
			response = ra.postRequest((String) APIVars.get("PATH"));
			break;
		case "GET":
			response = ra.getRequest((String) APIVars.get("PATH"));
			break;
		case "PUT":
			response = ra.putRequest((String) APIVars.get("PATH"));
			break;
		case "DELETE":
			response = ra.deleteRequest((String) APIVars.get("PATH"));
			break;
		default:
			log.info("Please specify the correct method name");
		}
		APIVars.put("RESPONSE", response);
		APIVars.put("RESPONSE_STATUS", Integer.toString(response.getStatusCode()));
		APIVars.put("RESPONSE_CONTENT_TYPE", response.getContentType());
		APIVars.put("RESPONSE_HEADERS", response.getHeaders());
		APIVars.put("RESPONSE_TIME", response.getTime());
		APIVars.put(var, response.getBody().prettyPrint());
	}

	@Given("^Make \"([^\"]*)\" call and save response as variable \"([^\"]*)\"$")
	public void Make_call_and_save_response_as_variable(String method, String var) {
		RestApiServices ra = new RestApiServices();
		Response response = null;
		switch (method) {
		case "POST":
			response = ra.postRequest((String) APIVars.get(""));
			break;
		case "GET":
			response = ra.getRequest((String) APIVars.get(""));
			break;
		case "PUT":
			response = ra.putRequest((String) APIVars.get(""));
			break;
		case "DELETE":
			response = ra.deleteRequest((String) APIVars.get(""));
			break;
		default:
			log.info("Please specify the correct method name");
		}
		APIVars.put("RESPONSE", response);
		APIVars.put("RESPONSE_STATUS", Integer.toString(response.getStatusCode()));
		APIVars.put("RESPONSE_CONTENT_TYPE", response.getContentType());
		APIVars.put("RESPONSE_HEADERS", response.getHeaders());
		APIVars.put("RESPONSE_TIME", response.getTime());
		APIVars.put(var, response.getBody().prettyPrint());
	}

	@Given("^Assert status is \"([^\"]*)\"$")
	public void Assert_status_is(String expectedStatusCode) {
		log.info("Actual Status Code [" + APIVars.get("STATUS") + "], expected Status Code [" + expectedStatusCode
				+ "]");
		Assert.assertEquals(APIVars.get("STATUS"), expectedStatusCode, "Status code match failed. Actual Status Code ["
				+ APIVars.get("STATUS") + "] Expected Status Code [" + expectedStatusCode + "]");
	}

	@Given("^Assert response header contains key \"([^\"]*)\" with value as \"([^\"]*)\"$")
	public void Assert_response_header_contains_key_with_value_as(String headerName, String value) {
		Headers header = (Headers) APIVars.get("RESPONSE_HEADERS");
		String header_key = header.get(headerName).getName();
		String header_value = header.get(headerName).getValue();
		if (header_key == null) {
			Assert.fail("Response header doesn't present.");
		}
		Assert.assertEquals(header_value, value, "Response header doesn't match the header value");
	}

	@Given("^Assert \"([^\"]*)\" with path and expected values$")
	public void Assert_with_path_and_expected_values(String responseVariable, DataTable table) {
		List<Map<String, String>> list = table.asMaps(String.class, String.class);
		String responseJson = (String) APIVars.get(resolveProperty(responseVariable));
		if (list.size() < 1) {
			throw new RuntimeException("Expected Data Tables Values, please provide | path | expected value |");
		}

		SoftAssert softAssert = new SoftAssert();
		Configuration conf = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
				.options(new Option[] { Option.ALWAYS_RETURN_LIST, Option.SUPPRESS_EXCEPTIONS }).build();
		for (int i = 0; i < list.size(); i++) {
			String expected = (String) ((Map) list.get(i)).get("path");
			String path = (String) ((Map) list.get(i)).get("expected value");
			ArrayNode value = (ArrayNode) com.jayway.jsonpath.JsonPath.using(conf).parse(responseJson).read(expected,
					new Predicate[0]);
			log.info("Json path: [" + expected + "], Actual: [" + value.toString() + "], Expected: [" + path + "]");
			softAssert.assertEquals(value.toString(), path,
					"Json path: [" + expected + "], Actual: [" + value.toString() + "], Expected: [" + path + "]");
		}
		softAssert.assertAll();

	}

}
