package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javabean.db_conn;
import javabean.get_md5;

public class check extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println("??????????????????????????????");
		}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		String id = null;
		String password = null;
		HttpSession session = req.getSession();
		id = req.getParameter("id");
		password = req.getParameter("password");
		
		get_md5 MD5 = new get_md5();//??????????MD5??????password??????
		password = MD5.getMD5(password);
		password = MD5.getMD5(password);//??password??????????????????????????????????????md5????
		System.out.println("密码"+password);
		
		
		db_conn conn = new db_conn();
		
		String sql = "select * from admin_user where user = '"+id+"'";
															
		
		try {
			ResultSet res = conn.executeQuery(sql);
			if(res.next()) {
				String pwd = res.getString(2);
				if(password.equals(pwd)) {
					session.setAttribute("admin_id", id);
					resp.sendRedirect("frame");
				}else {
					System.out.println("ResultSet"+res);
					resp.sendRedirect("/admin/index.jsp");
				}
			}else {
				System.out.println("查询管理员表为空");
				resp.sendRedirect("/admin/index.jsp");
			}
		}catch(SQLException e) {
			System.out.println("异常信息"+e);
		}
	}
	
}
