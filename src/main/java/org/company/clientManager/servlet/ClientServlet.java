package org.company.clientManager.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import org.company.clientManager.model.ThinClient;
import org.company.clientManager.dao.ThinClientDao;

@WebServlet(urlPatterns = {"/listCl"})
public class ClientServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	    resp.setContentType("application/json; charset=utf-8");
        ThinClientDao thinClientDao = new ThinClientDao();
            List<ThinClient> clients = thinClientDao.getClients();
	    JSONArray jsonObject = JSONArray.fromObject(clients);
	    resp.getWriter().print( jsonObject );

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
		doPost(req, resp);
	}

}
