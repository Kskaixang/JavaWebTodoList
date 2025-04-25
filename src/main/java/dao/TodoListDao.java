package dao;

import java.util.List;

import model.entity.Todo;


//(介面)
public interface TodoListDao { 
	//1.查詢所有 Todo資料
	List<Todo> findAllTodos();
	//2.查詢單筆 Todo資料
	Todo getTodo(Integer id);
	//3.新增    Todo 資料
	void addTodo(Todo todo);
	//4.修改指定 Todo completed 完成與否
	void updateTodoComplete(Integer id,Boolean completed);
	//5.修改指定 Todo Text 工作項目
	void updateTodoText(Integer id,String text);
	//6.刪除指定 Todo Text
	void deleteTodo(Integer id);	
}
