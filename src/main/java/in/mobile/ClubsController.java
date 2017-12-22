package in.mobile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import javax.validation.Valid;

@Controller
public class ClubsController
{
    @RequestMapping("/getregister")
    public String getEmptyClub(Model model)
    {
        Clubs clubs = new Clubs();
        model.addAttribute("clubs", clubs);
        model.addAttribute("categoryName", getCategories());
        model.addAttribute("stateId", getStates());
        return "clubregister";
    }

    private String[] getCategories()
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity("http://localhost:8086/clubs/categories", String[].class);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful())
        {
            return responseEntity.getBody();
        }
        else
        {
            return new String[0];
        }
    }

    private State[] getStates()
    {
        RestTemplate restTemplate1 = new RestTemplate();
        ResponseEntity<State[]> responseEntity1 = restTemplate1.getForEntity("http://localhost:8086/clubs/states", State[].class);
        HttpStatus statusCode = responseEntity1.getStatusCode();
        if (statusCode.is2xxSuccessful())
        {
            return responseEntity1.getBody();
        }
        else
        {
            return new State[0];
        }
    }

    private Category[] getSubcategories(String type)
    {
        RestTemplate restTemplate2 = new RestTemplate();
        ResponseEntity<Category[]> responseEntity2 = restTemplate2.getForEntity("http://localhost:8086/clubs/categories/subtypes?type="+type ,Category[].class);
        HttpStatus statusCode = responseEntity2.getStatusCode();
        if (statusCode.is2xxSuccessful())
        {
            return responseEntity2.getBody();
        }
        else
        {
            return new Category[0];
        }
    }

    @RequestMapping("/clubregister")
    public String registerByClub(Model model, @Valid Clubs clubs,BindingResult bindingResult) throws JsonProcessingException
    {
        System.out.println("ClubsController.registerByClub");
        System.out.println("clubs.getCategoryId() = " + clubs.getCategoryId());
        model.addAttribute("clubs", clubs);
        model.addAttribute("categoryName", getCategories());
        model.addAttribute("stateId", getStates());

        try
        {
            if (!bindingResult.hasErrors())
            {
                System.out.println("ClubsController.registerByClub");
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity entity = new HttpEntity(clubs,headers);
                ObjectMapper mapper = new ObjectMapper();
                System.out.println(mapper.writeValueAsString(clubs));
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<Clubs> response = restTemplate.exchange("http://localhost:8086/clubs", HttpMethod.POST, entity, Clubs.class);
                Clubs retClub = response.getBody();
                HttpStatus status = response.getStatusCode();
                if (status.is2xxSuccessful())
                {
                    model.addAttribute("name", retClub.getName());
                    model.addAttribute("categoryId",retClub.getCategory().getId());
                    model.addAttribute("description", retClub.getDescription());
                    model.addAttribute("addressLine1", retClub.getAddressLine1());
                    model.addAttribute("addressLine2", retClub.getAddressLine2());
                    model.addAttribute("addressLine3", retClub.getAddressLine3());
                    model.addAttribute("locality", retClub.getLocality());
                    model.addAttribute("city", retClub.getCity());
                    model.addAttribute("stateId", retClub.getStateId());
                    model.addAttribute("pincode", retClub.getPincode());
                    model.addAttribute("primaryContactNo", retClub.getPrimaryContactNo());
                    model.addAttribute("primaryEmailAddress", retClub.getPrimaryEmailAddress());
                    model.addAttribute("websiteAddress", retClub.getWebsiteAddress());
                    return "clubregsuccess";
                }
                else
                {
                    model.addAttribute("error", "Unsuccessfull Registration");
                    return "clubregister";
                }
            }
            else
            {
                model.addAttribute("name", clubs.getName());
                model.addAttribute("categoryId", clubs.getCategory().getId());
                model.addAttribute("description", clubs.getDescription());
                model.addAttribute("addressLine1", clubs.getAddressLine1());
                model.addAttribute("addressLine2", clubs.getAddressLine2());
                model.addAttribute("addressLine3", clubs.getAddressLine3());
                model.addAttribute("locality", clubs.getLocality());
                model.addAttribute("city", clubs.getCity());
                model.addAttribute("stateId", clubs.getStateId());
                model.addAttribute("pincode", clubs.getPincode());
                model.addAttribute("primaryContactNo", clubs.getPrimaryContactNo());
                model.addAttribute("primaryEmailAddress", clubs.getPrimaryEmailAddress());
                model.addAttribute("websiteAddress", clubs.getWebsiteAddress());
                return "clubregister";
            }
        }
        catch (RestClientException e)
        {
            e.printStackTrace();
            return "clubregister";
        }
    }

    @GetMapping("/allclubs")/* altrenate for @RequestMapping(method = RequestMethod.GET value="allclubs")*/
    public String clubDetails(Model model)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Clubs[]> responseEntity = restTemplate.getForEntity("http://localhost:8086/allclubs", Clubs[].class);
        Clubs[] clubs = responseEntity.getBody();
        Login home=new Login();
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful())
        {
            model.addAttribute("clubs", clubs);
            model.addAttribute("clubview",true);
            model.addAttribute("home", home);
            model.addAttribute("view",true);
            return "displayclubs";
        }
        else
        {
            return "error";
        }
    }

    @RequestMapping("/viewdetails")
    public String viewDetails(@RequestParam  Integer id, Model model)
    {
        System.out.println("id"+id);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Clubs> responseEntity = restTemplate.getForEntity("http://localhost:8086/getclubs?id=" + id, Clubs.class);
        Clubs clubs = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        if(statusCode.is2xxSuccessful())
        {
            model.addAttribute("clubs", clubs);
            model.addAttribute("categoryName", getCategories());
            model.addAttribute("categoryId",getSubcategories(clubs.getCategoryName()));
            model.addAttribute("stateId", getStates());
            model.addAttribute("viewonly",true);
            return "clubregister";
        }
        else
        {
            return "error";
        }
    }
}


