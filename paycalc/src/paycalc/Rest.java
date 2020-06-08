package paycalc;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;

public class Rest {
	// Makes REST requests using the HTTP GET method.
	public static String Get(String basic_auth, String url, String[] params) {		
		// Create HTTP Client that processes cookies using standard specification.
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		HttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		
		// Format HTTP GET request.
		String request_string = url;
		String delimeter = "?";
		for (String param : params) {
			request_string += delimeter + param;
			delimeter = "&";
		}
		HttpGet req = new HttpGet(request_string);
		
		// Enable basic authentication, and set the authentication header.
		byte[] encodedAuth = Base64.encodeBase64(basic_auth.getBytes(StandardCharsets.ISO_8859_1));
		String authHeader = "Basic " + new String(encodedAuth);
		req.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		String response_body = "";
		
		// Execute the HTTP request and extract the response body.
		try {
			HttpResponse response = httpclient.execute(req);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				response_body = EntityUtils.toString(response.getEntity());

			} else {
				System.err.println("API server returned non-ok response");
			}
		} catch (Exception e) {
			System.err.println("Error encountered in HttpGet");
		}
		req.releaseConnection();
		return response_body;
	}
}
