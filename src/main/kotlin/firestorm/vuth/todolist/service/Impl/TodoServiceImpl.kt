package firestorm.vuth.todolist.service.Impl

import firestorm.vuth.todolist.exception.TodoNotFoundException
import firestorm.vuth.todolist.model.Todo
import firestorm.vuth.todolist.repository.TodoRepo
import firestorm.vuth.todolist.service.TodoService
import org.springframework.stereotype.Service

@Service
class TodoServiceImpl(
    private val todoRepo: TodoRepo
): TodoService {
    override fun findAll(): List<Todo> =
        todoRepo.findAll()

    override fun findById(id: Long): Todo =
        todoRepo.findById(id).orElseThrow { TodoNotFoundException("Cannot find todo with id $id") }

    override fun createTodo(todo: Todo): Todo =
        todoRepo.save(todo)

    override fun updateTodo(id: Long, request: Todo): Todo {
        val todo = todoRepo.findById(id).orElseThrow { TodoNotFoundException("Cannot find todo with id $id") }

        todo.title = request.title
        todo.description = request.description
        todo.completed = request.completed

        return todoRepo.save(request)
    }

    override fun deleteTodo(id: Long) =
        todoRepo.deleteById(id)
}