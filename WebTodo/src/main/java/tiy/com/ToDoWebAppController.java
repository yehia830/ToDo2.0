package tiy.com;

/**
 * Created by Yehia830 on 9/28/16.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ToDoWebAppController {
    @Autowired
    ToDoRepository todos;

    @Autowired
    UserRepository users;

    User user;
    boolean signUpTrue = false;
    boolean loginTrue = false;
    boolean initialChoice = true;


    @PostConstruct
    public void init() {
        if (users.count() == 0) {
            User user = new User();
            user.name = "yehia1234";
            user.password = "1234";
            users.save(user);
        } else if (users.count() == 1) {
            User user2 = new User();
            user2.name = "Dom";
            user2.password = "12345";
            users.save(user2);
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session, String username) {
        model.addAttribute("signUpTrue", signUpTrue);
        model.addAttribute("loginTrue", loginTrue);
        model.addAttribute("initialChoice", initialChoice);


        if (user != null) {
            List<ToDo> listOfTodos = new ArrayList<>();
            listOfTodos = todos.findByUserId(user.id);

            model.addAttribute("toDoItems", listOfTodos);


            model.addAttribute("user", session.getAttribute("user"));
        }



        return "home";

    }

    @RequestMapping(path="/sign-up-button", method = RequestMethod.POST)
    public String signUpButton(Model model, HttpSession session) {
        if (!loginTrue) {
            signUpTrue = true;


            initialChoice = false;

        }
        return "redirect:/";
    }

    @RequestMapping(path="/login-button", method = RequestMethod.POST)
    public String loginButton(Model model, HttpSession session) {
        if (!signUpTrue) {
            loginTrue = true;


            initialChoice = false;

        }
        return "redirect:/";
    }

    @RequestMapping(path="/sign-up", method = RequestMethod.POST)
    public String signUp(HttpSession session, String username, String password) throws Exception {
        System.out.println("In sign up method");
        signUpTrue = false;

        if (username != "" && password != "") {

            user = new User(username, password);
            users.save(user);

            session.setAttribute("user", user);
        } else {
            throw new Exception("Your username and password must not be blank!");
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws Exception {

        loginTrue = false;
        user = users.findFirstByName(username);


        if (user != null) {

            if (!password.equals(user.password)) {
                throw new Exception("Invalid password!");
            } else {

                session.setAttribute("user", user);
            }
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/add-todo", method = RequestMethod.POST)
    public String addToDo(HttpSession session, String todoText) {

        if (todoText != null) {

            ToDo todo = new ToDo(todoText, user);
            todos.save(todo);

        }
        return "redirect:/";
    }

    @RequestMapping(path = "/toggle", method = RequestMethod.GET)
    public String toggleToDo(Model model, Integer todoID) {
        if (todoID != null) {
            ToDo todo = todos.findOne(todoID);
            todo.isDone = !todo.isDone;
            todos.save(todo);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteToDo(Model model, Integer todoID) {
//        System.out.println("About to delete: " + todoID);
        if (todoID != null) {
            todos.delete(todoID);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        initialChoice = true;
        return "redirect:/";
    }
}