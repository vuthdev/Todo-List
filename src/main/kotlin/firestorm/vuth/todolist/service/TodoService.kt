package firestorm.vuth.todolist.service

import firestorm.vuth.todolist.model.Todo

interface TodoService {
    fun findAll(): List<Todo>
    fun findById(id: Long): Todo
    fun createTodo(todo: Todo): Todo
    fun updateTodo(id: Long, todo: Todo): Todo
    fun deleteTodo(id: Long)
}