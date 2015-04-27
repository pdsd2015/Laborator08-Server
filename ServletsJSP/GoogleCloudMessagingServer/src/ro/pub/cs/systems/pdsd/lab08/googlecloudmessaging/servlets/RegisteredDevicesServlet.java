package ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.dataaccess.DataBaseWrapper;
import ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.general.Constants;

public class RegisteredDevicesServlet extends HttpServlet {

    final public static long serialVersionUID = 33333333L;

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
        JSONArray jsonArray = new JSONArray();
        
        try {
			ArrayList<ArrayList<String>> tableContent = dataBaseWrapper.getTableContent(
					Constants.TABLE_NAME, 
					null, 
					null, 
					null, 
					null);
			
			for (int k = 0; k < tableContent.size(); k++) {
				ArrayList<String> currentObject = tableContent.get(k);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(
						Constants.ID, 
						Integer.parseInt(currentObject.get(Constants.ID_INDEX))
						);
				jsonObject.put(
						Constants.REGISTRATION_ID, 
						currentObject.get(Constants.REGISTRATION_ID_INDEX)
						);
				jsonObject.put(
						Constants.USERNAME, 
						currentObject.get(Constants.USERNAME_INDEX)
						);
				jsonObject.put(
						Constants.EMAIL, 
						currentObject.get(Constants.EMAIL_INDEX)
						);
				jsonObject.put(
						Constants.TIMESTAMP, 
						currentObject.get(Constants.TIMESTAMP_INDEX)
						);
				jsonArray.add(jsonObject);
			}
		} catch (SQLException sqlException) {
			System.out.println("An exception has occurred: "+sqlException.getMessage());			
			if (Constants.DEBUG) {
				sqlException.printStackTrace();
			}
		}
        
        response.setContentType("text/html");
        PrintWriter printWriter = new PrintWriter(response.getWriter());
        printWriter.println(jsonArray.toJSONString());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
