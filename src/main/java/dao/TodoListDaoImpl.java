package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.Todo;

//(實現類)
public class TodoListDaoImpl extends BaseDao implements TodoListDao{
	
	@Override
	public List<Todo> findAllTodos() {
		List<Todo> todos = new ArrayList<>();
		
		String sql = "select id, text, completed from todo order by id";
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)){
			//逐一尋訪 rs中的 每一筆紀錄, 並將每一筆紀錄轉成 Todo 物件並加入到todos中
			while(rs.next()) {
				Todo todo = new Todo();
				todo.setId(rs.getInt("id"));  //這筆紀錄 取得id欄位
				todo.setText(rs.getString("text"));
				todo.setCompleted(rs.getBoolean("completed"));
				
				todos.add(todo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return todos;
	}

	@Override
	public Todo getTodo(Integer id) {
		// 如果你寫 String sql= "select id, text, completed from todo where id=" + id;
		//可能會被攻擊 具有風險
		String sql= "select id, text, completed from todo where id=?";
		//因為有運算"?" 要用Prepar
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			//第一個問號 帶入傳參 (Integer id)
			pstmt.setInt(1,id);
			
			//因為上面已經放入prepareStatement(sql) 所以這邊不用寫
			//而又因為要有close方法 可以利用try共用外面的catch  ()機制用完自動關閉 
			try (ResultSet rs = pstmt.executeQuery();){
				//最多只有一筆 不需要用while  有就有 沒有就結束
				//的到rs中的紀錄 指可能是1筆 或是0筆   有回傳todo 沒有null
				if(rs.next()) {
					Todo todo = new Todo();
					todo.setId(rs.getInt("id"));  //這筆紀錄 取得id欄位
					todo.setText(rs.getString("text"));
					todo.setCompleted(rs.getBoolean("completed"));
					
					return todo;
				}
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public void addTodo(Todo todo) {
		//id會自增 不需要添加
		String sql = "insert into todo(text, completed) value(?, ?)";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1,todo.getText());
			pstmt.setBoolean(2,todo.getCompleted());
			//雖然叫做執行修改 其實也是執行新增 成功時回傳int 可以看源碼  查詢才用executeP
			int rowcount = pstmt.executeUpdate(); 
			System.out.println("新增 todo 筆數" + rowcount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateTodoComplete(Integer id, Boolean completed) {
		String sql = "update todo set completed=? where id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setBoolean(1,completed);
			pstmt.setInt(2,id);
			int rowcount = pstmt.executeUpdate(); 
			System.out.println("修改 todo completed筆數" + rowcount);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void updateTodoText(Integer id, String text) {
		String sql = "update todo set text=? where id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1,text);
			pstmt.setInt(2,id);
			int rowcount = pstmt.executeUpdate(); 
			//為什麼要說明筆數 是因為 雖然是一筆  但我們可以知道 有沒有成功 比如失敗為 修改 todo text筆數:0
			System.out.println("修改 todo text筆數:" + rowcount);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
		

	@Override
	public void deleteTodo(Integer id) {
		String sql = "delete form todo where id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){			
			pstmt.setInt(1,id);
			int rowcount = pstmt.executeUpdate(); 
			System.out.println("刪除 todo text筆數:" + rowcount);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}
	

}
