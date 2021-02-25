package com.kuliza.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.Date;
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
                private UserRiskSessionRepository userRiskSessionRepo;

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


            @GetMapping("/register")
            public String showRegistrationForm(Model model) {
                    userDetails ud=new userDetails();
                    model.addAttribute("user",ud);
                return "registration_form";
            }

            /*
                    AFTER SUCCESSFUL REGISTRATION.....
             */

            @PostMapping("/processRegister")
            public String registerSuccessfull(userDetails userdetails)

            {
                      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                      String newPassword = encoder.encode(userdetails.getUser_password());
                      userdetails.setUser_password(newPassword);
                      repo.save(userdetails);
                      return "registerSuccesfully";
            }

            /*
                    APPLICATION DASHBOARD.......................
             */

                       @GetMapping("/risk_policy")
                       public String addRiskPolicy()

                     {
                         return "rp";
                     }

            /*
                    ADDING THE RISK ..................
             */

                       @GetMapping("/risk")
                       public String addRiskDetails(Model model) {
                        Risk_Details rd=new Risk_Details();
                        model.addAttribute("riskObj",rd);
                        return "addRisk";
            }

            /*
                    POST SUCCESSFUL ADDITION OF RISK ..................
             */

                @PostMapping("/riskSuccess")
                public String riskAddSuccessfully(@AuthenticationPrincipal UserDetails ud, Risk_Details riskdetails, Model model)
                {

                    UserRiskLogs url=new UserRiskLogs();
                    userDetails userdetails1=  repo.findByUser_name(ud.getUsername());
                    riskdetails.setUser(userdetails1);

                    riskRepo.save(riskdetails);

                    url.setRisk_id(riskdetails.getRisk_id());
                    System.out.println("This is current risk id "+riskdetails.getRisk_id());

                    System.out.println("This is the risk logs risk id "+url.getRisk_id());

                    url.setRisk_title(riskdetails.getRisk_title());

                    System.out.println("This is the risk logs current risk title "+url.getRisk_title());
                    url.setDate(java.time.LocalDate.now().toString());
                    System.out.println("This is the risk logs current risk date "+url.getDate());
                    url.setTime(java.time.LocalTime.now().toString());

                    System.out.println("This is the risk logs current risk time "+url.getTime());
                    userRiskSessionRepo.save(url);

                    UserPolicy up=new UserPolicy();
                    List<Risk_Details> getRisk=riskRepo.findAllRisk(ud.getUsername());
                    model.addAttribute("getRisk",getRisk);
                    String name=ud.getUsername();
                    model.addAttribute("addPresentRisk",up);
                    up.setUser_name(name);

                    return "selectRisk";
                }

                /*
                    POLICY ADDED SUCCESSFULLY..........................
                 */

                @PostMapping("policyAddSuccesfully")
                public String policyAddS(@AuthenticationPrincipal UserDetails ud, Model model, UserPolicy up)
                {
                    String name=ud.getUsername();
                    up.setUser_name(ud.getUsername());
                    policyRepo.save(up);

                    return "rp";
                }

                @GetMapping("/policyDirect")
                public String addPolicyDirect(@AuthenticationPrincipal UserDetails ud,Model model,UserPolicy up)
                {
                    List<Risk_Details> getRisk=riskRepo.findAllRisk(ud.getUsername());
                    model.addAttribute("getRisk",getRisk);
                    String name=ud.getUsername();
                    model.addAttribute("addPresentRisk",up);
                    up.setUser_name(name);

                    return "selectRisk";
                }

                /*
                        SENDING THE MAIL TO THE ADMIN .................................
                 */

                public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";

                @RequestMapping("/takeInput")
                public String takeUserInput(Model model)
                {
                    return "upload";
                }

                @RequestMapping("/showRisk")
                public String showRisk(Model model, @AuthenticationPrincipal UserDetails ud)
                {
                    List<Risk_Details> listRisk=riskRepo.findAllRisk(ud.getUsername());
                    model.addAttribute("getAllRisk",listRisk);
                    return "AllRisk";
                }

                @RequestMapping("/upload")
                public String upload(Model model,@RequestParam("files") MultipartFile[] files) {
                    StringBuilder fileNames = new StringBuilder();
                    for (MultipartFile file : files) {
                        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
                        fileNames.append(file.getOriginalFilename()+" ");
                        try {
                            Files.write(fileNameAndPath, file.getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    model.addAttribute("msg", "Successfully uploaded files "+fileNames.toString());
                    return "uploadS";
                }






}

