package common;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private Object sentObj; /* The object holds the kind of object that we want to send to server */
	private Object returnObj; /* this obj is the obj that we are receive after the query */
	private String ClassType; // to sort the msg in echo server
	private String queryToDo; // which action we want to do
	private String handler;

	public Message(String queryQ, String queryTodo) {
		sentObj = null;
		returnObj = null;
		queryToDo = queryTodo;
	}

	public Message() {
		sentObj = null;
		returnObj = null;
	}

	public String getqueryToDo() {
		return queryToDo;
	}

	public void setqueryToDo(String queryNeedTo) {
		this.queryToDo = queryNeedTo;
	}

	public Object getSentObj() {
		return sentObj;
	}

	public void setSentObj(Object sentObj) {
		this.sentObj = sentObj;
	}

	public Object getReturnObj() {
		return returnObj;
	}

	public void setReturnObj(Object returnObj) {
		this.returnObj = returnObj;
	}

	public String getClassType() {
		return this.ClassType;
	}

	public void setClassType(String cl) {
		this.ClassType = cl;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}
}
