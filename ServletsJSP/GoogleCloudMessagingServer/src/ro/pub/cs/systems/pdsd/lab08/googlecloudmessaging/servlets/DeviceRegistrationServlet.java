package ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.dataaccess.DataBaseException;
import ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.dataaccess.DataBaseWrapper;
import ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.systems.pdsd.lab08.googlecloudmessaging.general.Constants;

public class DeviceRegistrationServlet extends HttpServlet {

    final public static long serialVersionUID = 11111111L;

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

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Enumeration<String> parameters = request.getParameterNames();
        
        String registrationId = null;
        String username = null;
        String email = null;

        while (parameters.hasMoreElements()) {
            String parameter = (String)parameters.nextElement();
            if (parameter.equals(Constants.REGISTRATION_ID)) {
                registrationId = request.getParameter(parameter);
            }
            if (parameter.equals(Constants.USERNAME)) {
                username = request.getParameter(parameter);
            }
            if (parameter.equals(Constants.EMAIL)) {
                email = request.getParameter(parameter);
            }
        }
        
        if (registrationId != null && !registrationId.isEmpty() &&
        		username != null && !username.isEmpty() &&
        		email != null && !email.isEmpty()) {
        	ArrayList<String> columnNames = new ArrayList<String>();
        	columnNames.add(Constants.REGISTRATION_ID);
        	columnNames.add(Constants.USERNAME);
        	columnNames.add(Constants.EMAIL);
        	ArrayList<String> values = new ArrayList<String>();
        	values.add(registrationId);
        	values.add(username);
        	values.add(email);
        	try {
				dataBaseWrapper.insertValuesIntoTable(Constants.TABLE_NAME, columnNames, values, false);
			} catch (SQLException | DataBaseException exception) {
				System.out.println("An exception has occurred: "+exception.getMessage());
				if (Constants.DEBUG) {
					exception.printStackTrace();
				}
			}
        	response.setStatus(HttpServletResponse.SC_OK); 
        } else {
        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
