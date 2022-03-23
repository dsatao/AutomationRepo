package utils;

import io.restassured.response.Response;
import io.restassured.http.Method;

/*
 * @author : Dipak.Satao
 */

public class RestApiServices extends BasePage {
	static Response response;

	public Response getRequest(String endpoint) {
		response = request.request(Method.GET, endpoint);
		System.out.println("URL" + baseUrl + endpoint);

		System.out.println(request.toString());
		return response;
	}

	public Response postRequest(String endpoint) {
		response = request.request(Method.POST, endpoint);
		System.out.println(response.body());
		return response;
	}

	public Response patch(String endpoint) {
		response = request.request(Method.PATCH, endpoint);
		System.out.println(response.body());
		return response;
	}

	public Response putRequest(String endpoint) {
		response = request.request(Method.PUT, endpoint);
		System.out.println(response.body());
		return response;
	}

	public Response deleteRequest(String endpoint) {
		response = request.request(Method.DELETE, endpoint);
		System.out.println(response.body());
		return response;
	}
}
