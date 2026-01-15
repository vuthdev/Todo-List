package firestorm.vuth.todolist.exception

class UserExistException(msg: String) : RuntimeException(msg)
class UserNotFoundException(msg: String) : RuntimeException(msg)