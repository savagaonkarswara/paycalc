package paycalc;

import java.nio.charset.StandardCharsets;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;

public class AcuityAccessor {
	private String uid;
	private String api_key;
	
	public AcuityAccessor(String uid, String api_key) {
		this.uid = uid;
		this.api_key = api_key;
	}
	
	public String GetCalendarJsonString(String start_date, String end_date) {
		// Create default SSL context and configure it to accept all certificates.
		SSLContext sslContext = null;
		try {
			sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
		} catch (Exception e) {
			System.err.println("Error setting up SSL context for the connection");
			return "";
		}
		
		// Create HTTP Client that accepts all server certificates and processes cookies using standard spec.
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		HttpClient httpclient = HttpClients.custom().setSSLContext(sslContext)
				.setSSLHostnameVerifier(new NoopHostnameVerifier()).setDefaultRequestConfig(requestConfig).build();
		
		// Format HTTP GET request.
		HttpGet req = new HttpGet("https://acuityscheduling.com/api/v1/appointments?canceled=false&minDate="
				+ start_date + "&maxDate=" + end_date + "&excludeForms=true");
		
		// Enable basic auth, and set the auth header with username and password.
		String auth = uid + ":" + api_key;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
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
