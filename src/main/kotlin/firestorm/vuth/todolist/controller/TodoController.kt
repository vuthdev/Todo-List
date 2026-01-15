package firestorm.vuth.todolist.controller

import firestorm.vuth.todolist.model.Todo
import firestorm.vuth.todolist.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService
) {

    @GetMapping
    fun getAll(): ResponseEntity<List<Todo>> {
        return ResponseEntity.ok(todoService.findAll())
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Todo> {
        return ResponseEntity.ok(todoService.findById(id))
    }

    @PostMapping
    fun createTodo(@RequestBody request: Todo): ResponseEntity<Todo> {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(request))
    }

    @DeleteMapping
    fun deleteTodo(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(todoService.deleteTodo(id))
    }
}