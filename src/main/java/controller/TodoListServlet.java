package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dto.TodoDTO;
import service.TodoListService;
import service.TodoListServiceImpl;

//因為有不同種類的指派 所以用*字佔位符
//Spring boot MVC就是這種概念
@WebServlet("/todolist/*")
public class TodoListServlet extends HttpServlet{
	private TodoListService service = new TodoListServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String pathInfo = req.getPathInfo();		
		//resp.getWriter().print("pathInfo = " + pathInfo);
		String id = req.getParameter("id");
		String text = req.getParameter("text");
		String completed = req.getParameter("checked");
		switch (pathInfo) {
		// add我們在toPost做
		case "/": //顯示首頁
		case "/*":
			List<TodoDTO> todos = service.findAllTodos();
			RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/view/todolist.jsp");
			req.setAttribute("todos", todos);
			rd.forward(req, resp);
			return;	
			
		case "/update": //修改			
			//不會有同時改兩分的狀態 所以分開判斷 但使用者滑鼠 一次只會操做一個項目 所以雙return還在邏輯內
			if(completed != null) {
				//修改Todo.completed 完成狀態
				service.updateTodoComplete(Integer.parseInt(id), Boolean.parseBoolean(completed));
				resp.sendRedirect("/JavaWebTodoList/todolist/");
				return;
			}
			if(text != null) {
				//修改Todo.text 內容
				service.updateTodoText(Integer.parseInt(id), text);
				resp.sendRedirect("/JavaWebTodoList/todolist/");
				return;
			}			
			break;					
			
			
		case "/delete": 
			
			service.deleteTodo(Integer.parseInt(id));
			//重跑首頁 (我們自己指定的)
			resp.sendRedirect("/JavaWebTodoList/todolist/");
			return;	
			
		default: 			
			//錯誤路徑
			resp.getWriter().print("path error");
			return;
			
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if(!pathInfo.equals("/add")) {
			//錯誤路徑
			resp.getWriter().print("path error");
			return;
		} 
		//進行新增程序
		String text = req.getParameter("text");		
		//因為任務剛添加時還沒做完 先預設false
		Boolean completed = false;
		service.addTodo(text, completed);
		//重跑首頁 (我們自己指定的)
		resp.sendRedirect("/JavaWebTodoList/todolist/");
		
		
		//RequestDispatcher rd = req.getRequestDispatcher("/todolist" + pathInfo);
		
	}
	

}
