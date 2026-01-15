package firestorm.vuth.todolist.repository

import firestorm.vuth.todolist.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepo: JpaRepository<Todo, Long> {
}