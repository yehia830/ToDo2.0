package tiy.com;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ToDoRepository extends CrudRepository<ToDo, Integer> {
    ToDo findFirstByText(String text);
    List<ToDo> findByUserId(Integer userId);
    List<ToDo> findAllByUser(User user);
}