package tn.pi.usermanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tn.pi.usermanagement.model.User;
import tn.pi.usermanagement.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users"; // users.html
    }

    // =========================
    // 2️⃣ Show create form
    // URL: http://localhost:8080/users/new
    // =========================
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form"; // user-form.html
    }

    // =========================
    // 3️⃣ Save or update user
    // =========================
    @PostMapping
    public String saveUser(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    // =========================
    // 4️⃣ Show edit form
    // URL: http://localhost:8080/users/edit/{id}
    // =========================
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));

        model.addAttribute("user", user);
        return "user-form"; // reuse same form
    }

    // =========================
    // 5️⃣ Delete user
    // URL: http://localhost:8080/users/delete/{id}
    // =========================
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
