package service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import dao.TodoListDao;
import dao.TodoListDaoImpl;
import model.dto.TodoDTO;
import model.entity.Todo;

//(實現類)
public class TodoListServiceImpl implements TodoListService{
	private TodoListDao dao = new TodoListDaoImpl();
/*
	@Override
	public List<TodoDTO> findAllTodos() {
		List<Todo> todos = dao.findAllTodos();
		//List<Todo> 轉 List<TodoDTO>
		List<TodoDTO> todoDTOs = new ArrayList<>();
		for(Todo todo :  todos) {
			todoDTOs.add(transferToDTO(todo));
		}		
		return todoDTOs;
	}
	*/
	//我們可以用lambda寫
	@Override
	public List<TodoDTO> findAllTodos() {
		//map(this::transferToDTO)  因為這個方法就在 這個物件內 所以是this(等於TodoListServiceImpl) 
		//  :: 相當於你傳進來的每個todo元素   換句話說 todo -> transferToDTO(todo)
		return dao.findAllTodos().stream().map(this::transferToDTO).toList();
	}

	@Override
	public TodoDTO getTodo(Integer id) {
		return transferToDTO(dao.getTodo(id));
	}

	@Override
	public void addTodo(String text, Boolean completed) {
		//新增的時候 暫時放0沒關係  反正SQL傳的時候不會過去 
		Todo todo = new Todo(0, text, completed);
		dao.addTodo(todo);		
	}

	@Override
	public void updateTodoComplete(Integer id, Boolean completed) {
		dao.updateTodoComplete(id, completed);		
	}

	@Override
	public void updateTodoText(Integer id, String text) {
		dao.updateTodoText(id, text);		
	}

	@Override
	public void deleteTodo(Integer id) {
		dao.deleteTodo(id);		
	}
	
	//我們自新增的兩個私有方法
	//todo 轉換成 DTO
	private TodoDTO transferToDTO(Todo todo) {		
		return new TodoDTO(todo.getId(),todo.getText(),todo.getCompleted());
	}
	//DTO 轉換成 entity
	private Todo transferToEntity(TodoDTO todoDTO) {
		return new Todo(todoDTO.getId(),todoDTO.getText(),todoDTO.getCompleted());
	}

}
