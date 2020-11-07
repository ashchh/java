package com.chhillar.controller;

import com.chhillar.dao.EmployeeDaoImple;
import com.chhillar.entities.Employee;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import com.chhillar.dao.EmployeeDao;
import com.chhillar.dao.HR_ManagerDao;
import com.chhillar.entities.HR_Manager;
import java.text.ParseException;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class MainController {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private HR_ManagerDao hrmd;

    @RequestMapping(path = "/home")
    public String home(Model model, HttpSession session, SessionStatus sessionStatus) {
        EmployeeDaoImple employeeDaoImple = new EmployeeDaoImple();
        List<Employee> employee = employeeDao.getAllEmployee();
        model.addAttribute("employee", employee);
        session.setAttribute("status", "true");
        return "index";
    }

    @RequestMapping(value = {"/" , "login"} )
    public String homecalled() {
        return "login";
    }

    @RequestMapping(path = "/registration")
    public String registrationcalled() {
        return "RegistrationForm";
    }

    @RequestMapping(path= "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/handle-login", method = RequestMethod.POST)
    public String handlelogin(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletRequest request, Model model) {
        HR_Manager hr = hrmd.getLogin(email, password);
        request.getSession().setAttribute("msg", "Wrong Details");
        if (hr != null) {
            request.getSession().setAttribute("hr", hr);
            return "redirect:add-Employee";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/handle-registeruser", method = RequestMethod.POST)
    public RedirectView handleEmployee(@ModelAttribute HR_Manager hrm, HttpServletRequest request, Model model) throws ParseException {
        this.hrmd.register(hrm);
        RedirectView redirectView = new RedirectView();
        request.getSession().setAttribute("hr", hrm);
        redirectView.setUrl(request.getContextPath() + "/home");
        return redirectView;
    }

    @RequestMapping(path = "/add-Employee")
    public String addEmployee(Model model) {
        model.addAttribute("title", "Add employee");
        return "add_employee_form";
    }

    @RequestMapping(value = "/handle-employee", method = RequestMethod.POST)
    public RedirectView handleEmployee(@ModelAttribute Employee employee, HttpServletRequest request, Model model) throws ParseException {
        System.out.println(employee);
        int pid = employee.getPid();
        employee.setAge(employee.getDob());
        employee.setStatus("InActive");
        employeeDao.createEmployee(employee);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(request.getContextPath() + "/add-Employee");
        return redirectView;
    }

    @RequestMapping("/delete/{pid}")
    public RedirectView callDelete(@PathVariable("pid") int pid, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        employeeDao.deleteEmployee(pid);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(request.getContextPath() + "/home");
        return redirectView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RedirectView Delete(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        try {
            for (String id : request.getParameterValues("empId")) {
                employeeDao.deleteEmployee(Integer.parseInt(id));
                System.out.println("Delete");
            }
        } catch (Exception ex) {
            System.out.println("catch");
        }
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(request.getContextPath() + "/home");
        return redirectView;
    }

    @RequestMapping("/update/{pid}")
    public String callUpdate(@PathVariable("pid") int pid, HttpServletRequest request, Model model) {

        Employee employee = employeeDao.getEmployee(pid);
        model.addAttribute("employee", employee);
        return "update_form";
    }

    @RequestMapping(value = "/handle-employeeupdate/{pid}", method = RequestMethod.POST)
    public RedirectView handleUpdateEmployee(@ModelAttribute Employee employee, @PathVariable("pid") int pid, HttpServletRequest request) throws ParseException {

        employee.setAge(employee.getDob());
//        if(employee.getStatus().equals("select")){
//            employee.setStatus("InActive");
//        }
        employeeDao.updateEmployee(employee);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(request.getContextPath() + "/home");
        return redirectView;
    }
}
