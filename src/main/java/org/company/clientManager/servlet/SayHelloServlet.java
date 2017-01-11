package org.company.clientManager.servlet;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import net.sf.json.JSONObject;
import org.company.clientManager.Client;

@WebServlet(urlPatterns = {"/say"})
public class SayHelloServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//resp.setContentType("application/json; charset=utf-8");
		resp.setContentType("text/plain; charset=utf-8");
        String result = "";
        BufferedReader buff = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp = "";
        while((temp = buff.readLine()) != null){
            sb.append(temp);
        }
        buff.close();
        String acceptjson = sb.toString();
        String mac = "";
        if (acceptjson != "") {  
            JSONObject jo = JSONObject.fromObject(acceptjson);
            mac = (String)jo.get("mac");
            System.out.println("SayHelloServlet mac:" + mac);
        }

        //String mac = req.getAttribute("mac").toString();
        //System.out.println("SayHelloServlet mac:" + mac);

        try{
            Client client = new Client();
            result = client.sayHelloToServer(mac, "sayHello");
        }catch(Exception e){
            System.out.println("ERR: ERR msg!");
            e.printStackTrace();
        }

		resp.getWriter().print( result );
        //resp.setStatus(200, "Success");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
		doPost(req, resp);
	}

}
