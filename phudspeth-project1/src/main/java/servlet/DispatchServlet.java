package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Employee;
import beans.Request;
import daoimpl.RegImpl;
import daoimpl.ReqHandlerImpl;
import daointer.RegistrarInterface;
import daointer.ReqHandlerInter;
import services.LoginService;

/**
 * Servlet implementation class DispatchServlet
 */

public class DispatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	RegistrarInterface dao;
	ReqHandlerInter req;
    
    @Override
    public void init() throws ServletException 
    {
    	this.dao = new RegImpl();
    	this.req = new ReqHandlerImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(false) == null || request.getSession(false).getAttribute("accessLevel") == null) 
		{
			System.out.println("URI = " + request.getRequestURI() + ": doGet: session access level null");
			request.getRequestDispatcher("/login.html").forward(request, response);
		}
		String doing = request.getRequestURI();
		System.out.println(doing);
		switch(doing) 
		{
		case"/jcindustries/app/emppage":
			request.getRequestDispatcher("/emppage.html").forward(request, response);
			break;
		case"/jcindustries/app/manpage":
			System.out.println("Routing to manpage");
			request.getRequestDispatcher("/manpage.html").forward(request, response);
			break;
		case"/jcindustries/app/login":
			System.out.println("Routing to login");
			request.getRequestDispatcher("/login.html").forward(request, response);
			break;
		default:
			System.out.println("doGet Default block");
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String doing= request.getRequestURI();
		//System.out.println(doing);
		//response.setStatus(201);
		ObjectMapper mapper = new ObjectMapper();
		switch(doing)
		{
		case"/jcindustries/app/login":
			
			Employee emp = (Employee) mapper.readValue(request.getInputStream(), Employee.class);
			String username = emp.getUsername();
			String password = emp.getPassword();
			emp = dao.getEmp(emp.getUsername());
			if(LoginService.userAuthenticate(emp, username, password))
			{
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				session.setAttribute("password", emp.getPassword());
				session.setAttribute("email", emp.getEmail());
				session.setAttribute("name", emp.getName());
				session.setAttribute("id", emp.getId());
				session.setAttribute("accessLevel", emp.getAccessLevel());
				if(session.getAttribute("accessLevel").equals(1)) 
				{
					//System.out.println("URI = /jcindustries/app/login: doPost: forwarding to /jcindustries/app/emppage");
					//response.sendRedirect("/jcindustries/emppage.html");	
					response.getWriter().println("emppage");
					
				}
				else if(session.getAttribute("accessLevel").equals(2)) 
				{
					response.getWriter().println("manpage");
					System.out.println(response.toString());
				}
			}
			else 
			{
				//request.getRequestDispatcher("login.html").forward(request, response);
				response.getWriter().print("false");
				response.sendRedirect("/jcindustries");
			}
			break;
		case"/jcindustries/app/getcuruser":
			HttpSession session = request.getSession(false);
			Employee curEmp = new Employee();
			curEmp.setUsername((String) session.getAttribute("username"));
			curEmp.setPassword((String) session.getAttribute("password"));
			curEmp.setEmail((String) session.getAttribute("email"));
			curEmp.setName((String) session.getAttribute("name"));
			curEmp.setId((int) session.getAttribute("id"));
			curEmp.setAccessLevel((int) session.getAttribute("accessLevel"));
			response.getWriter().print(mapper.writeValueAsString(curEmp));
			break;
//		case"/jcindustries/app/manpage":
//			
//			break;
		case"/jcindustries/app/addemp":
			Employee newemp = (Employee) mapper.readValue(request.getInputStream(), Employee.class);
			dao.addUser(newemp.getUsername(), newemp.getPassword(), "default", newemp.getName());
			System.out.println("Username: " + newemp.getUsername() + " Password: " + newemp.getPassword() + " Name: " + newemp.getName());
			break;
		case"/jcindustries/app/submitrequest":
			Request userReq = (Request) mapper.readValue(request.getInputStream(), Request.class);
			req.createRequest(userReq);
			break;
		case"/jcindustries/app/getemprequest":
			List<Request> requests = req.getUserRequest((int)request.getSession(false).getAttribute("id"));
			response.getWriter().print(mapper.writeValueAsString(requests));
			break;
		case"/jcindustries/app/getemprequests":
			Employee er = (Employee) mapper.readValue(request.getInputStream(), Employee.class);
			List<Request> emprequests = req.getUserRequest(er.getId());
			response.getWriter().print(mapper.writeValueAsString(emprequests));
			break;
		case"/jcindustries/app/updateuser":
			Employee upEmp = (Employee) mapper.readValue(request.getInputStream(), Employee.class);
			dao.updateUser(upEmp);
			break;
		case"/jcindustries/app/getapprovedrequests":
			List<Request> appRequests = req.getAllResolved();
			response.getWriter().print(mapper.writeValueAsString(appRequests));
			break;
		case"/jcindustries/app/getpendingrequests":
			List<Request> penRequests = req.getAllSubmitted();
			response.getWriter().print(mapper.writeValueAsString(penRequests));
			break;
		default: 
			System.out.println("URI = " + doing + ": doPost: default block");
			break;
		}
		//response.sendRedirect("jcindustries/");
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		RegImpl des = (RegImpl) dao;
		ReqHandlerImpl rdes = (ReqHandlerImpl) req;
		try {
			des.getDBConnection().close();
			rdes.getDBConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
