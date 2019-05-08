package org.zdd.bookmall.web.controller.admin;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.zdd.bookmall.model.entity.Store;
import org.zdd.bookmall.model.entity.User;
import org.zdd.bookmall.model.service.IStoreService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    @Autowired
    private IStoreService storeService;


    @RequestMapping({"", "/", "/index"})
    @RequiresPermissions("system")
    public String adminIndex() {
        return "admin/index";
    }

    @RequestMapping("/adminLogin")
    public String adminLogin(@RequestParam(value = "username", required = false) String username,
                             @RequestParam(value = "password", required = false) String password,
                             HttpServletRequest request, Model model) {
        //未认证的用户
        Subject userSubject = SecurityUtils.getSubject();
        if (!userSubject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);

            token.setRememberMe(false);//禁止记住我功能
            try {

                //登录成功
                userSubject.login(token);
                User loginUser = (User) userSubject.getPrincipal();
                request.getSession().setAttribute("loginUser", loginUser);
                Store store = storeService.findStoreByUserId(loginUser.getUserId());
                request.getSession().setAttribute("loginStore", store);


//                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                String url = "/admin/index";
//                if (savedRequest != null) {
//                    url = savedRequest.getRequestUrl();
//                    if(url.contains(request.getContextPath())){
//                        url = url.replace(request.getContextPath(),"");
//                    }
//                }
                if(StringUtils.isEmpty(url) || url.equals("/favicon.ico")){
                    url = "/";
                }

                return "redirect:" + url;

            } catch (UnknownAccountException | IncorrectCredentialsException uae) {
                return "login";
            } catch (LockedAccountException lae) {
                model.addAttribute("loginMsg", "账户已被冻结！");
                return "login";
            } catch (AuthenticationException ae) {
                model.addAttribute("loginMsg", "登录失败！");
                return "login";
            }
        } else {
            //用户已经登录
            return "redirect:/admin/index";
        }
    }
}
