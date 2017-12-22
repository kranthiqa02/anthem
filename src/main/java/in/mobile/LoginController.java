package in.mobile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController
{
    @RequestMapping("/index")
    public String login()
    {
        System.out.println("LoginController.login");
        return "login";
    }
    @RequestMapping("/getotp")
    public String sendOtp(@RequestParam String mobileNo, Model model, HttpSession session)
    {
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity <Login> response = restTemplate.getForEntity("http://localhost:8086/logins/sendotp?id=&mobileNo=" + mobileNo, Login.class);
            Login login = response.getBody();
            System.out.println("id" + login.getId());
            HttpStatus status = response.getStatusCode();
            if (status.is2xxSuccessful())
            {
                model.addAttribute("mobileNo", mobileNo);
                model.addAttribute("login", login);
                session.setAttribute("id", login.getId());
                return "enterotp";
            }
            else
            {
                return "error";
            }
        }
        catch (RestClientException e)
        {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("/validate")
    public String validateOtp(@RequestParam String mobileNo, @RequestParam Double otpNum, Model model)
    {
        Login login = new Login();
        model.addAttribute("login", login);
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity <Login> response = restTemplate.getForEntity("http://localhost:8086/logins/validateotp?mobileNo=" + mobileNo + "&otpNum=" + otpNum, Login.class);
            HttpStatus status = response.getStatusCode();
            if (status.is2xxSuccessful())
            {
                login = response.getBody();
                model.addAttribute("mobileNo", mobileNo);
                model.addAttribute("otpNum", otpNum);
                model.addAttribute("login", login);
                return "register";
            }
            else
            {
                model.addAttribute("error","Invalid Otp");
                model.addAttribute("mobileNo",mobileNo);
                return "enterotp";
            }
        }
        catch (Exception e)
        {
            model.addAttribute("error","Invalid Otp");
            model.addAttribute("mobileNo",mobileNo);
            return "enterotp";
        }
    }

    @RequestMapping("/register")
    public String registerByUser(Model model, @Valid Login login, BindingResult bindingResult, HttpSession session)
    {
        model.addAttribute("login", login);
        try
        {
            if(!bindingResult.hasErrors())
            {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity entity = new HttpEntity(login,headers);
                ResponseEntity<String> response = restTemplate.exchange("http://localhost:8086/logins/register", HttpMethod.POST, entity, String.class);
                HttpStatus status = response.getStatusCode();
                if (status.is2xxSuccessful())
                {
                    model.addAttribute("firstName", login.getFirstName());
                    model.addAttribute("id", login.getId());
                    model.addAttribute("lastName", login.getLastName());
                    model.addAttribute("emailAddress", login.getEmailAddress());
                    model.addAttribute("mobileNo", login.getMobileNo());
                    model.addAttribute("password", login.getPassword());
                    return "loginregsuccess";
                }
                else
                {
                    model.addAttribute("error", "Unsuccessfull Registration");
                    return "register";
                }
            }
            else
            {
                model.addAttribute("firstName", login.getFirstName());
                model.addAttribute("id", login.getId());
                model.addAttribute("lastName", login.getLastName());
                model.addAttribute("emailAddress", login.getEmailAddress());
                model.addAttribute("mobileNo", login.getMobileNo());
                model.addAttribute("password", login.getPassword());
                return "register";
            }
        }
        catch (RestClientException e)
        {
            e.printStackTrace();
            return "register";
        }
    }

    @GetMapping("/allclubsforuser")
    public String clubDetails1(Model model, HttpSession session)
    {
        Login login = new Login();
        model.addAttribute("login", login);
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("entered the all clubs users");
        ResponseEntity <Clubs[]> responseEntity = restTemplate.getForEntity("http://localhost:8086/allclubs", Clubs[].class);
        System.out.println("after rest template");
        Clubs[] clubs = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful())
        {
            model.addAttribute("clubs", clubs);
            model.addAttribute("userview", true);
            model.addAttribute("login", login);
            System.out.println("Id-" + Integer.parseInt(session.getAttribute("id").toString()));
            return "displayclubs";
        }
        else
        {
            return "error";
        }
    }

    @GetMapping("/alllogins")
    public String clubDetails(@RequestParam Integer id, Model model)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity <Login> responseEntity = restTemplate.getForEntity("http://localhost:8086/loginbyid?id=" + id, Login.class);
        Login login = responseEntity.getBody();
        model.addAttribute("login", login);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful())
        {
            model.addAttribute("login", login);
            return "viewmembers";
        }
        else
        {
            return "error";
        }
    }
}



