package com.tourist.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.tourist.Entity.Role;
import com.tourist.Entity.User;
import com.tourist.Entity.User_Role;
import com.tourist.Respository.UserRepository;
import com.tourist.Service.RoleService;
import com.tourist.Service.UserRoleService;
import com.tourist.Service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired 
    private UserRepository userRespository;
    
    @GetMapping("/accounts")
    public String getUsers(Model model) {
        List<User> users = userService.getList();
        model.addAttribute("users", users);
        return "accounts";
    }
    
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User newUser, Model model) {
    	try {
    		 User existingUser = userRespository.findByUsername(newUser.getUsername());
    	        if (existingUser != null) {
    	            // Nếu tên người dùng đã tồn tại, thêm thông báo lỗi vào mô hình và quay lại trang đăng ký
    	            model.addAttribute("errorMessage", "Username already exists. Please choose a different username.");
    	            return "register"; // Thay đổi tên trang nếu cần
    	        }
    		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        	String rawPassword = newUser.getPassword();
        	String encodedPassword = encoder.encode(rawPassword);
        	newUser.setPassword(encodedPassword);
        	 userService.AddOrUpdate(newUser);
        	 Role role = roleService.findByRoleName("User");
             if (role == null) {
                 throw new RuntimeException("Role 'User' not found");
             }
        	
        	User_Role userRole = new User_Role();
            userRole.setUser(newUser);
            userRole.setRole(role);
            userRoleService.save(userRole);
            
            return "redirect:/accounts";
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		throw e;
    	}
    }  
    
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User newUser, Model model) {
    
    	try {
    		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        	String rawPassword = newUser.getPassword();
        	String encodedPassword = encoder.encode(rawPassword);
        	newUser.setPassword(encodedPassword);
        	userService.AddOrUpdate(newUser);
        	return "redirect:/accounts";
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		throw e;
    	}
    	
    }
    
    @PostMapping("/editUser/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
    	User user = userService.getUserById(id);
    	model.addAttribute("user", user);
    	return ("editUser");
    }
    @PostMapping("/bandUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
    	User user = userService.getUserById(id);
    	if (user.getEnabled()) {
    		user.setEnabled(false);
    	}
    	else {
    		user.setEnabled(true);
    	}
    	userService.AddOrUpdate(user);
    	return "redirect:/accounts";
    }
    private String saveFile(MultipartFile file) {
        try {
            // Generate a unique filename or use the original filename
            String filename = file.getOriginalFilename();
            Path path = Paths.get("uploads/" + filename);
            Files.write(path, file.getBytes());
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
   
    
}
