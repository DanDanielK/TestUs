package com.springbootquickstart.TestUs.interceptor;
import com.springbootquickstart.TestUs.dto.UserInfo;
import com.springbootquickstart.TestUs.model.MyUser;
import com.springbootquickstart.TestUs.model.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.springbootquickstart.TestUs.service.MyUserDetailService;

@Component
public class UserInterceptor implements HandlerInterceptor {


    @Autowired
    private MyUserDetailService userService;

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

// check if the current user principal is valid.
        if(auth != null && modelAndView != null && !modelAndView.getModelMap().containsKey("userInfo")){
            if(userService.findByEmail(auth.getName()).isEmpty()){
//                throw new UsernameNotFoundException("User not found "+auth.getName());
            }else {
                MyUser user = userService.findByEmail(auth.getName()).get();
                // inject user details into the current view
                UserInfo userInfo = new UserInfo();
                userInfo.setFullName(user.getFirstName() + " " + user.getLastName());
                modelAndView.getModelMap().addAttribute("userInfo", userInfo);
            }
        }


    }



}
