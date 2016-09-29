package tiy.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;


@RestController
public class ToDoJSONController {
    User user;

    @Autowired
    ToDoRepository todos;

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/todo.json", method = RequestMethod.GET)
    public ArrayList<ToDo> getJsonTodos(HttpSession session) {
        user = (User)session.getAttribute("user");
        ArrayList<ToDo> toDoList = new ArrayList<ToDo>();
//        System.out.println("Inside getJsonTodos() method.");
        if (user != null) {
//            System.out.println("User not null bc I'm getting here!");
            Iterable<ToDo> allTodos = todos.findAllByUser(user);
            for (ToDo currentTodo : allTodos) {
                toDoList.add(currentTodo);
            }
        }
        return toDoList;
    }

    @RequestMapping(path = "/addToDo.json", method = RequestMethod.POST)
    public ArrayList<ToDo> addToDoJson(HttpSession session, @RequestBody ToDo todo) throws Exception {
        user = (User) session.getAttribute("user");
//        user = new User((String) session.getAttribute("username"), (String) session.getAttribute("password"));

        if (user == null) {
            throw new Exception("Unable to add game without an active user in the session");
        } if (todo.text != null) {
            todo.user = user;
            todo.isDone = false;

            todos.save(todo);
        }

        return getJsonTodos(session);
    }

    @RequestMapping(path = "/deleteToDo.json", method = RequestMethod.GET)
    public ArrayList<ToDo> deleteToDoJson(HttpSession session, int todoID) {
        ToDo todoToDelete = todos.findOne(todoID);
        todos.delete(todoToDelete);

        return getJsonTodos(session);
    }

    @RequestMapping(path = "/toggleToDo.json", method = RequestMethod.GET)
    public ArrayList<ToDo> toggleToDoJson(HttpSession session, int todoID) {
        ToDo todoToToggle = todos.findOne(todoID);
        todoToToggle.isDone = !todoToToggle.isDone;
        todos.save(todoToToggle);

        return getJsonTodos(session);
    }
}