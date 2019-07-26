package daointer;

import java.util.List;

import beans.Request;

public interface ReqHandlerInter 
{
	public void updateStatus(Request req);
	public void updateRequest(Request req);
	public void createRequest(Request req);
	public List<Request> getAllRequests();
	public List<Request> getUserRequest(int userID);
	public List<Request> getAllResolved();
	public List<Request> getAllSubmitted();
	public Request getRequest(int ticketNumb);
}
