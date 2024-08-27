package com.tourist.Controller;




import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.tourist.Configs.JwtTokenUtil;
import com.tourist.Entity.CustomUserDetails;
import com.tourist.Entity.Role;
import com.tourist.Entity.User;
import com.tourist.Entity.User_Role;
import com.tourist.Service.RoleService;
import com.tourist.Service.UserRoleService;
import com.tourist.Service.UserService;
import com.tourist.Until.JwtResponse;
import com.tourist.Until.LoginRequest;
import com.tourist.Until.RegisterRequest;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;





@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
        	User user = userService.getUserByUsername(loginRequest.getUsername());
        	if(user == null) {
        		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại!");
        		
        	}
        	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        	String rawPassword = loginRequest.getPassword();
        	String encodedPasswordFromDB = user.getPassword(); // Mật khẩu đã mã hóa từ cơ sở dữ liệu

        	boolean isPasswordMatch = encoder.matches(rawPassword, encodedPasswordFromDB);
        	if(!isPasswordMatch) {
        		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mật khẩu sai!");
        	}
            // Thực hiện quá trình xác thực (authentication)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Lưu trữ đối tượng Authentication vào SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Lấy UserDetails sau khi xác thực thành công
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            List<String> roles = userDetails.getAuthorities().stream()
                                            .map(GrantedAuthority::getAuthority)
                                            .collect(Collectors.toList());
            String token = jwtTokenUtil.generateToken(username, roles);
            
//            Map<String, Object> responseBody = new HashMap<>();
//            responseBody.put("message", "Đăng nhập thành công!");
//            responseBody.put("token", token);

            // Trả về phản hồi với mã trạng thái 20
            // Trả về token trong phản hồi
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            // Log lỗi chung
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Kiểm tra xem username hoặc email đã tồn tại hay chưa
        if (userService.getUserByUsername(registerRequest.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email đã tồn tại!");
        }

        if (userService.getUserByEmail(registerRequest.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email đã được đăng kí!");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	String rawPassword = registerRequest.getPassword();
    	String encodedPassword = encoder.encode(rawPassword);
        // Tạo người dùng mới
        User user = new User();
        user.setUsername(registerRequest.getEmail());
        user.setPassword(encodedPassword); // Nhớ mã hóa mật khẩu trước khi lưu
        user.setEmail(registerRequest.getEmail());
        user.setEnabled(true);
        user.setFullName("nguyen van A");
        user.setTelephone("19001013");
        userService.AddOrUpdate(user);
        
        Role role = roleService.findByRoleName("User");
        if (role == null) {
            throw new RuntimeException("Role 'User' not found");
        }
   	
    	User_Role userRole = new User_Role();
       userRole.setUser(user);
       userRole.setRole(role);
       userRoleService.save(userRole);

       

        // Trả về token trong phản hồi
        return ResponseEntity.ok("register successful");
    }
    @GetMapping("/get/user")
    public ResponseEntity<?> getUser(HttpServletRequest request){
    	try {
    		 String jwt = request.getHeader("Authorization");
			 
		        if (jwt == null ) {
		            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token" );
		        }
		        if (jwt.startsWith("Bearer ")) {
		            jwt = jwt.substring(7);
		        }
		        Claims claims = jwtTokenUtil.getClaimsFromToken(jwt);
		        java.util.Date expiration = claims.getExpiration();
		        if(expiration.before(new java.util.Date())) {
		        	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token exprired");
		        }
		        String username = claims.getSubject(); // sub
	       
		        if (username == null) {
		            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
		        }
	
		        User user = userService.getUserByUsername(username);
		        if (user == null) {
		            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
		        }
		        Map<String, Object> responseBody = new HashMap<>();
		        user.setPassword("");
	            responseBody.put("user",user);
		        return ResponseEntity.ok(responseBody);
		        
    	}
    	catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("get user error");
    	}
    }
}