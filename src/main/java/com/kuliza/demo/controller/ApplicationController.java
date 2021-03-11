package com.kuliza.demo.controller;

import com.kuliza.demo.implementations.RiskData;
import com.kuliza.demo.model.*;
import com.kuliza.demo.repository.*;
import com.kuliza.demo.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Controller
public class ApplicationController {

    /*

        USER REPOSITORIES.....................(FOR ACCESSING THE DATABASE...)
     */
    @Autowired
    private UserRepository repo;

    @Autowired
    private RiskRepository riskRepo;

    @Autowired
    private UserPolicyRepository policyRepo;

    @Autowired
    private UserRiskLogsRepository userRiskSessionRepo;

    @Autowired
    private UserPolicyLogsRepository userPolicyLogsRepo;

    @Autowired
    private UserTestingLogsRepository userTestingLogsRepo;


    @Autowired
    private Risk_PolicyRepository risk_policyRepository;

    public ApplicationController() throws FileNotFoundException {
    }

            /*
                    HOMEPAGE FOR REGISTRATION AND LOGIN............
             */

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

             /*
                    FOR REGISTRATION..........
              */

    @RequestMapping("/logoutS")
    public String logo()
    {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        userDetails ud = new userDetails();
        model.addAttribute("user", ud);
        return "registration_form";
    }

            /*
                    AFTER SUCCESSFUL REGISTRATION.....
             */

    //public String uploadDirectory = "";

    @PostMapping("/processRegister")
    public String registerSuccessfull(userDetails userdetails) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newPassword = encoder.encode(userdetails.getUser_password());
        userdetails.setUser_password(newPassword);
        repo.save(userdetails);
//         CustomUserDetails cud = new CustomUserDetails(userdetails);
//         String userName = "" + cud.getUsername();

//         String path = "/home/kuliza-568/Downloads/demo/uploads";
//         File file = new File(path);
//         if (!file.exists()) {
//             if (file.mkdir()) uploadDirectory = uploadDirectory + "/" + createSubDirectory(path, userName);
//         } else {
//             System.out.println("Check");
//             uploadDirectory = uploadDirectory + "/" + createSubDirectory(path, userName);
//         }
        final File TEMP_DIRECTORY = new File(System.getProperty("user.dir")+"/uploads");
        String dir=userdetails.getUser_name();
        File newDirectory = new File(TEMP_DIRECTORY, dir);
        System.out.println(newDirectory.exists());
        if(!newDirectory.exists());
        newDirectory.mkdir();


        return "registerSuccesfully";
    }

    public String createSubDirectory(String path, String userName) {
        path = path + "/" + userName;
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println("SubDirectoryCreated");
            }
        }
        return path;
    }

            /*
                    APPLICATION DASHBOARD.......................
             */

    @GetMapping("/risk_policy")
    public String addRiskPolicy(Model model,@AuthenticationPrincipal UserDetails ud)
    {
        model.addAttribute("name",ud.getUsername());
        return "rp";
    }

            /*
                    ADDING THE RISK ..................
             */


    @GetMapping("/risk")
    public String addRiskDetails(Model model,@AuthenticationPrincipal UserDetails ud) {
        Risk_Details rd = new Risk_Details();
        model.addAttribute("riskObj", rd);
        model.addAttribute("name",ud.getUsername());
        return "addRisk";
    }

            /*
                    POST SUCCESSFUL ADDITION OF RISK ..................
             */

    @PostMapping("/riskSuccess")
    public String riskAddSuccessfully(@AuthenticationPrincipal UserDetails ud, Risk_Details riskdetails, Model model,RiskData rd) {

        UserRiskLogs url = new UserRiskLogs();
        userDetails userdetails1 = repo.findByUser_name(ud.getUsername());
        riskdetails.setUser(userdetails1);
        riskRepo.save(riskdetails);

        url.setRisk_id(riskdetails.getRisk_id());
        url.setRisk_title(riskdetails.getRisk_title());
        url.setDate(java.time.LocalDate.now().toString());
        url.setTime(java.time.LocalTime.now().toString());
        userRiskSessionRepo.save(url);


        UserPolicy up = new UserPolicy();
        List<Risk_Details> getRisk = riskRepo.findAllRisk(ud.getUsername());
        model.addAttribute("getRisk", getRisk);
        String name = ud.getUsername();
        model.addAttribute("addPresentRisk", up);
        model.addAttribute("addRiskData",rd);
        up.setUser_name(name);
        return "selectRisk";
    }

                /*
                    POLICY ADDED SUCCESSFULLY..........................
                 */

    @PostMapping("policyAddSuccesfully")
    public String policyAddS(@AuthenticationPrincipal UserDetails ud, Model model, UserPolicy up,RiskData rd) {
        String name = ud.getUsername();
        up.setUser_name(ud.getUsername());
        System.out.println("This is the present policy details"+up.getPolicy_name());
        policyRepo.save(up);

        System.out.println("This is the riskData of the new class"+rd.getRiskData());

        UserPolicyLogs upl = new UserPolicyLogs();
        upl.setDate(java.time.LocalDate.now() + "");
        upl.setTime(java.time.LocalTime.now() + "");
        upl.setPolicy_name(up.getPolicy_name());
        upl.setRisk_acc(rd.getRiskData());
        List<String> splittedRisk= Arrays.asList(rd.getRiskData().split(","));
        for(String s :splittedRisk)
        {
            Risk_Policy r_p=new Risk_Policy();
            r_p.setPolicy_name(up.getPolicy_name());
            r_p.setRisk_id(Long.parseLong(s));
            risk_policyRepository.save(r_p);
        }
        userPolicyLogsRepo.save(upl);

        return "rp";
    }

    @GetMapping("/policyDirect")
    public String addPolicyDirect(@AuthenticationPrincipal UserDetails ud, Model model, UserPolicy up, RiskData rd) {
        List<Risk_Details> getRisk = riskRepo.findAllRisk(ud.getUsername());
        model.addAttribute("getRisk", getRisk);
        String name = ud.getUsername();
        model.addAttribute("addPresentRisk", up);
        model.addAttribute("addRiskData",rd);
        up.setUser_name(name);

        return "selectRisk";
    }

    /*
            SENDING THE MAIL TO THE ADMIN .................................
     */
    @RequestMapping("/takeInput")
    public String takeUserInput(Model model) {
        return "upload";
    }

                /*
                    SHOWING ALL THE RISK TO THE USER......
                 */

    @RequestMapping("/showRisk")
    public String showRisk(Model model, @AuthenticationPrincipal UserDetails ud) {
        Risk_Details rd = new Risk_Details();
        List<Risk_Details> listRisk = riskRepo.findAllRisk(ud.getUsername());
        model.addAttribute("getAllRisk", listRisk);
        return "AllRisk";
    }

                /*
                    SHOWING ALL THE POLICIES............
                 */

    @RequestMapping("/showPolicies")
    public String showPolicies(Model model, @AuthenticationPrincipal UserDetails ud) {
        List<UserPolicy> listPolicy = policyRepo.getEveryPolicy(ud.getUsername());
        model.addAttribute("getAllPolicy", listPolicy);
        return "AllPolicies";
    }
    
    
    public static String uploadDirectory= System.getProperty("user.dir")+"/uploads";

    @RequestMapping("/upload")
    public String upload(Model model, @RequestParam("files") MultipartFile[] files, @AuthenticationPrincipal UserDetails ud) {
//         if (uploadDirectory.equals(""))
//             uploadDirectory = uploadDirectory + "/home/kuliza-568/Downloads/demo/uploads" + "/" + ud.getUsername();
// //                    uploadDirectory=uploadDirectory+"/"+ud.getUsername();
        
        
        
        String name=ud.getUsername();
        uploadDirectory=uploadDirectory+"/"+name;
        System.out.println(uploadDirectory);
        StringBuilder fileNames = new StringBuilder();
        
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("msg", "Successfully uploaded files " + fileNames.toString());
        return "uploadS";
    }


    @RequestMapping("/showResult")
    public String showR(Model model, @AuthenticationPrincipal UserDetails ud) {
        List<UserTestingLogs> userTestingLogs = userTestingLogsRepo.findAll();
        model.addAttribute("showRe", userTestingLogs);
        return "ShowResult";
    }

    @RequestMapping("/viewDetailedRiskInformation/{id}")
    public String viewDetailRiskInfo(Model model, Risk_Details rd,@PathVariable(value = "id")Long id) {
        List<Risk_Details> userRiskD = riskRepo.findAllKeyWords(id);
        model.addAttribute("showDetail", userRiskD);
        return "ViewDetailedInformation";
    }

    @RequestMapping("/viewDetailedPolicyInformation/{name}")
    public String viewDetailPolicyInfo(Model model,@PathVariable(value="name") String name)
    {
        List<Risk_Policy> list=risk_policyRepository.findByPolicyName(name);
        model.addAttribute("showDetail",list);
        return "ViewDetailedPolicyInfo";
    }

    @RequestMapping("/deleteRisk/{id}")
    public String deleteRisk(@PathVariable(value="id")Long id) {
        List<Risk_Policy> listRiskPolicy=risk_policyRepository.fetchPolicyName(id);
        risk_policyRepository.deleteId(id);
        riskRepo.deleteById(id);
        for(Risk_Policy rp:listRiskPolicy)
        {
            if(!risk_policyRepository.fetchByPolicyName(rp.getPolicy_name()))
            {
                policyRepo.deletebyName(rp.getPolicy_name());
            }
        }
        return "rp";
    }

    @RequestMapping("/deletePolicy/{name}")
    public String deletePolicy(@PathVariable(value="name") String name)
    {
        policyRepo.deletebyName(name);
        risk_policyRepository.deleteName(name);
        return "rp";
    }


}

