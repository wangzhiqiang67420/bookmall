package org.zdd.bookmall.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.zdd.bookmall.common.utils.BSResultUtil;
import org.zdd.bookmall.model.dao.custom.CustomMapper;
import org.zdd.bookmall.model.entity.Role;
import org.zdd.bookmall.model.entity.Store;
import org.zdd.bookmall.model.service.IMailService;
import org.zdd.bookmall.model.service.IStoreService;
import org.zdd.bookmall.common.pojo.BSResult;
import org.zdd.bookmall.model.entity.User;
import org.zdd.bookmall.model.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/user")
@CrossOrigin
public class UserController {


    @Autowired
    private IUserService userService;

    @Autowired
    private IMailService mailService;

    @Autowired
    private IStoreService storeService;

    @Autowired
    private CustomMapper customMapper;


    @Value("${mail.fromMail.addr}")
    private String from;

    @Value("${my.ip}")
    private String ip;

    private final String USERNAME_PASSWORD_NOT_MATCH = "用户名或密码错误";

    private final String USERNAME_CANNOT_NULL = "用户名不能为空";

    @RequestMapping("/loginnew")
    @ResponseBody
    public Map loginnew(@RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "password", required = false) String password,
                        HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            map.put("status","0");
            map.put("msg","用户名或密码不能为空");
            return map;
        }
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

                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                String url = "bookIndex";
                if (savedRequest != null) {
                    url = savedRequest.getRequestUrl();
                    if(url.contains(request.getContextPath())){
                        url = url.replace(request.getContextPath(),"");
                    }
                }
                if(StringUtils.isEmpty(url) || url.equals("/favicon.ico")){
                    url = "bookIndex";
                }
                map.put("status","1");
                map.put("msg",url);
                map.put("user",loginUser);
                map.put("sessionId",request.getSession().getId());
                //判断是否仅仅是普通用户
                List<Role> roles = customMapper.findRolesByUserId(loginUser.getUserId());
                if(roles.size() == 1 && roles.get(0).getCode().equals("customer")){
                    loginUser.setIdentity("ordinary");
                }

                Cookie uuid = new Cookie("uuid", "uuid=" + UUID.randomUUID());
                Cookie userId = new Cookie("userId", "userId=" + loginUser.getUserId());
                Cookie st = new Cookie("st", "st=" + new Date().getTime());
                response.addCookie(uuid);
                response.addCookie(userId);
                response.addCookie(st);
                map.put("uuid", UUID.randomUUID());
                map.put("userId",loginUser.getUserId());
                map.put("st",new Date().getTime());
                return map;
            } catch (UnknownAccountException | IncorrectCredentialsException uae) {
                map.put("status","0");
                map.put("msg",USERNAME_PASSWORD_NOT_MATCH);
            } catch (LockedAccountException lae) {
                map.put("status","0");
                map.put("msg","账户已被冻结");
            } catch (AuthenticationException ae) {
                map.put("status","0");
                map.put("msg","登录失败！");
            }
        } else {
            //用户已经登录
            map.put("status","1");
            map.put("msg","bookIndex");
        }
        return map;

    }


    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "password", required = false) String password,
                        HttpServletRequest request, Model model) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return "login";
        }
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


                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                String url = "/";
                if (savedRequest != null) {
                    url = savedRequest.getRequestUrl();
                    if(url.contains(request.getContextPath())){
                        url = url.replace(request.getContextPath(),"");
                    }
                }
                if(StringUtils.isEmpty(url) || url.equals("/favicon.ico")){
                    url = "/";
                }

                return "redirect:" + url;

            } catch (UnknownAccountException | IncorrectCredentialsException uae) {
                model.addAttribute("loginMsg", USERNAME_PASSWORD_NOT_MATCH);
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
            return "redirect:/index";
        }

    }

    @RequestMapping("/info")
    public String personInfo(){
        return "user_info";
    }

    /* @RequestMapping("/login1")
     public String login1(@RequestParam(value = "username", required = false) String username,
                          @RequestParam(value = "password", required = false) String password,
                          Model model, HttpServletRequest request) {

         if (StringUtils.isEmpty(username)) {
             model.addAttribute("loginMsg", USERNAME_CANNOT_NULL);
             return "login";
         }

         if (StringUtils.isEmpty(password)) {
             model.addAttribute("loginMsg", "密码不能为空");
             return "login";
         }

         BSResult<User> bsResult = userService.login(username, password);
         //登录校验失败
         if (bsResult.getData() == null) {
             model.addAttribute("loginMsg", bsResult.getMessage());
             return "login";
         }

         //登录校验成功，重定向到首页
         User user = bsResult.getData();
         //置密码为空
         user.setPassword("");
         request.getSession().setAttribute("user", user);
         return "redirect:/";
     }
     */
    //shiro框架帮我们注销
    @RequestMapping("/logout")
    @CacheEvict(cacheNames="authorizationCache",allEntries = true)
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/page/login";
    }

    @RequestMapping("/logoutnew")
    @CacheEvict(cacheNames="authorizationCache",allEntries = true)
    @ResponseBody
    public String logoutnew() {
        SecurityUtils.getSubject().logout();
        return "logoutnew";
    }

    /**
     * 注册 检验用户名是否存在
     *
     * @param username
     * @return
     */
    @RequestMapping("/checkUserExist")
    @ResponseBody
    public BSResult checkUserExist(String username) {
        if (StringUtils.isEmpty(username)) {
            return BSResultUtil.build(200, USERNAME_CANNOT_NULL, false);
        }

        return userService.checkUserExistByUsername(username);
    }

    /**
     * 注册，发激活邮箱
     *
     * @param user
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public Map register(@RequestBody User user) {
        Map map = new HashMap();
        BSResult isExist = checkUserExist(user.getUsername());
        //尽管前台页面已经用ajax判断用户名是否存在，
        // 为了防止用户不是点击前台按钮提交表单造成的错误，后台也需要判断
        if ((Boolean) isExist.getData()) {
            user.setNickname(user.getUsername());
            BSResult bsResult = userService.saveUser(user);
            User userNotActive = (User) bsResult.getData();
            BSResult bsResult2 = userService.activeUser(userNotActive.getCode());
            //获得未激活的用户
//            User userNotActive = (User) bsResult.getData();
//            try {
//                mailService.sendHtmlMail(user.getEmail(), "<dd书城>---用户激活---",
//                        "<html><body><a href='http://"+ip+"/user/active?activeCode=" + userNotActive.getCode() + "'>亲爱的" + user.getUsername() +
//                                "，请您点击此链接前往激活</a></body></html>");
//            } catch (Exception e) {
//                e.printStackTrace();
//                model.addAttribute("registerError", "发送邮件异常！请检查您输入的邮箱地址是否正确。");
//                return "fail";
//            }
            map.put("status", 1);
            return map;
        } else {
            map.put("status", 0);
            map.put("msg", "用户已存在");
            return map;
        }

    }

    @RequestMapping("/active")
    public String activeUser(String activeCode, Model model) {
        BSResult bsResult = userService.activeUser(activeCode);
        if (!StringUtils.isEmpty(bsResult.getData())) {
            model.addAttribute("username", bsResult.getData());
            return "active_success";
        } else {
            model.addAttribute("failMessage", bsResult.getMessage());
            return "fail";
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public BSResult updateUser(@RequestBody  User user){
        User loginUser = new User();
        loginUser.setNickname(user.getNickname());
        loginUser.setEmail(user.getEmail());
        loginUser.setDetailAddress(user.getDetailAddress());
        loginUser.setGender(user.getGender());
        loginUser.setUpdated(new Date());
        loginUser.setPhone(user.getPhone());
        loginUser.setIdentity(user.getIdentity());
        loginUser.setUserId(user.getUserId());
        BSResult bsResult = userService.updateUser(loginUser);
        return bsResult;
    }

    @RequestMapping("/password/{userId}")
    @ResponseBody
    public BSResult changePassword(@PathVariable("userId") int userId,String oldPassword,String newPassword){
        if(StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)){
            return BSResultUtil.build(400, "密码不能为空");
        }
        return userService.compareAndChange(userId,oldPassword,newPassword);
    }

}
