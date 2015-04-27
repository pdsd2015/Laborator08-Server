package ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.dataaccess.DataBaseWrapper;
import ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.general.Constants;

public class MessagePushServlet extends HttpServlet {

    final public static long serialVersionUID = 22222222L;

    private DataBaseWrapper dataBaseWrapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dataBaseWrapper = DataBaseWrapperImplementation.getInstance();
    }

    @Override
    public void destroy() {
        dataBaseWrapper.releaseResources();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration<String> parameters = request.getParameterNames();
		String registrationId = null;
		String message = null;
		int id = -1;
		while (parameters.hasMoreElements()) {
			String parameter = (String) parameters.nextElement();
			if (parameter.equals(Constants.REGISTRATION_ID)) {
				registrationId = request.getParameter(parameter);
			}
			if (parameter.equals(Constants.MESSAGE)) {
				message = request.getParameter(parameter);
			}
			if (parameter.startsWith(Constants.MESSAGE_PUSH)) {
				id = Integer.parseInt(parameter.substring(parameter.indexOf("#")+1));
				registrationId = request.getParameter(Constants.REGISTRATION_ID+"#"+id);
				message = request.getParameter(Constants.MESSAGE+"#"+id);
			}
        }
		
		if (registrationId != null && !registrationId.isEmpty() &&
				message != null && !message.isEmpty()) {
			
			CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();

			HttpPost httpRequest = new HttpPost(Constants.GCM_SERVER_ADDRESS);
			HttpResponse httpResponse;

			/**
			 * HTTP headers
			 * 
			 * Authorization: key=API key provided from Google Developer Console
			 * Content-Type: the format of the HTTP body (may be omitted for plain text)
			 *   - application/json for JSON
			 *   - application/x-www-form-urlencoded;charset=UTF-8 for plain text
			 *
			 */

			httpRequest.setHeader("Authorization", "key=" + Constants.API_KEY);
			httpRequest.setHeader("Content-Type", "application/json");

			/**
			 * HTTP body
			 * 
			 * -> registration_id/registration_ids indicates the recipient/recipients
			 * (for each mobile device to which the message is to be transmitted the message must specify
			 * the id provided by the GCM server during the registration process)
			 * 
			 * -> data contains the payload to be transmitted; the developer may define its own keys
			 * 
			 * -> other fields: collapse_key, delay_while_idle, time_to_live, restricted_package_name, dry_run
			 * 
			 * The complete list of the message parameters can be found out at: 
			 * http://developer.android.com/google/gcm/server.html#params
			 */

			JSONObject requestBody = new JSONObject();
			JSONArray registrationIds = new JSONArray();
			registrationIds.add(registrationId);
			requestBody.put(Constants.REGISTRATION_IDS, registrationIds);
			JSONObject payload = new JSONObject();
			payload.put(Constants.MESSAGE, message);
			requestBody.put(Constants.DATA, payload);

			try {
				httpRequest.setEntity(new StringEntity(JSONValue.toJSONString(requestBody)));

				// send the request
				httpResponse = httpClient.execute(httpRequest);

				// receive the response
				if (httpResponse != null) {

					// HTTP Response Status Code
					int responseCode = httpResponse.getStatusLine().getStatusCode();
					if (Constants.DEBUG) {
						System.out.println("HTTP Status Code: " + Integer.toString(responseCode));
					}

					// HTTP Response Body
					InputStream inputStream = httpResponse.getEntity().getContent();
					if (Constants.DEBUG) {
						System.out.println("HTTP Body: " + IOUtils.toString(inputStream));
					}

					// display the HTTP Response
					if (Constants.DEBUG) {
						System.out.println("HTTP Response: " + httpResponse.toString());
					}
				} 
			} catch (UnsupportedEncodingException unsupportedEncodingException) {
				System.out.println("An exception has occurred: "+unsupportedEncodingException.getMessage());
				if (Constants.DEBUG) {
					unsupportedEncodingException.printStackTrace();
				}
			} catch (ClientProtocolException clientProtocolException) {
				System.out.println("An exception has occurred: "+clientProtocolException.getMessage());
				if (Constants.DEBUG) {
					clientProtocolException.printStackTrace();
				}
			} catch (IOException ioException) {
				System.out.println("An exception has occurred: "+ioException.getMessage());
				if (Constants.DEBUG) {
					ioException.printStackTrace();
				}
			}
		}
		
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/message_push.jsp");
		if (requestDispatcher != null) {
			requestDispatcher.forward(request, response);
		} 

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
