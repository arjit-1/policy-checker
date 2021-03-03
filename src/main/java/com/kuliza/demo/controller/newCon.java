package com.kuliza.demo.controller;

import com.kuliza.demo.implementations.CheckRegex;
import com.kuliza.demo.model.Risk_Details;
import com.kuliza.demo.model.UserPolicy;
import com.kuliza.demo.model.UserTestingLogs;
import com.kuliza.demo.repository.RiskRepository;
import com.kuliza.demo.repository.UserPolicyRepository;
import com.kuliza.demo.repository.UserRepository;
import com.kuliza.demo.repository.UserTestingLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

@RestController
public class newCon
{

    @Autowired
    private UserRepository repo;

    @Autowired
    private RiskRepository riskRepo;

    @Autowired
    private UserPolicyRepository policyRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserTestingLogsRepository userTestingLogsRepo;



    String violatedPolicy="";
    String violatedRiks="";

    UserTestingLogs utl=new UserTestingLogs();

    @RequestMapping( "/check")
    public void checkForRisk(@AuthenticationPrincipal UserDetails ud) throws IOException
    {
        CheckRegex cr= new CheckRegex();
        HashMap<String,Integer> map=new HashMap<>();

        String dir="/home/kuliza-568/Downloads/demo/uploads/";
        File folder=new File(dir);
        File[] listOfFiles=folder.listFiles();
        if(listOfFiles.length>0)
        {

            for(int j=0;j<listOfFiles.length;j++)
            {
                String str1="";
                if(listOfFiles[j].isFile())
                {
                    FileInputStream fis=new FileInputStream(listOfFiles[j]);
                    InputStreamReader isr=new InputStreamReader(fis, Charset.defaultCharset());
                    BufferedReader br=new BufferedReader(isr);
                    String line;
                    while((line = br.readLine()) != null){
                        System.out.println(line);
                        str1=str1+line;
                    }
                    br.close();

                    System.out.println(".......");
                    System.out.println("Content of the testing docuement ");
                    List<String> words=Arrays.asList(str1.split(" "));
                    for(String s:words) System.out.println(s);
                    System.out.println(".......");


                    for(String s:words){
                        map.put(s,map.getOrDefault(s,0)+1);
                    }

                    List<UserPolicy> listPolicy=policyRepo.getEveryPolicy(ud.getUsername());

                    for(UserPolicy up1:listPolicy)
                    {
                        String getRisk=up1.getRisk_Data();
                        if(getRisk.length()==0)continue;
                        List<String> seperatedRisk= Arrays.asList(getRisk.split(","));

                        List<Long> riskId=new ArrayList<>();

                        for(String s:seperatedRisk)
                        {
                            riskId.add(Long.parseLong(s));
                        }

                        for(Long i:riskId)
                        {
                            List<Risk_Details> getKeyWords=riskRepo.findAllKeyWords(i);
                            for(Risk_Details rd:getKeyWords)
                            {
                                int cnt1=0;
                                String str=rd.getRisk_keyword();
                                String strRegex=rd.getRisk_regex();
                                List<String> riskKey= Arrays.asList(str.split(","));
                                List<String> regexKey=Arrays.asList(strRegex.split(","));
//
                                for(String s:riskKey)
                                {
                                    if(s.length()==0)continue;
                                    if(map.containsKey(s))cnt1+=map.get(s);

                                }
                                for(String s:regexKey)
                                {
                                    if(s.length()==0)continue;
                                    cnt1+=cr.checkR(s,map);
                                }

                                if(cnt1>=rd.getMatch_count()) {
                                    System.out.println("Risk With id "+rd.getRisk_id()+" exceed the match count thus policy with policy name "+up1.getPolicy_name()+" is violated");
                                    violatedPolicy=violatedPolicy+up1.getPolicy_name()+",";
                                    violatedRiks=violatedRiks+rd.getRisk_id()+",";

                                    utl.setRisk_id(violatedRiks);
                                    utl.setPolicy_name(violatedPolicy);
                                    utl.setDate(java.time.LocalDate.now()+"");
                                    utl.setTime(java.time.LocalTime.now()+"");
                                    userTestingLogsRepo.save(utl);
                                }
                                else System.out.println("No risk exceed the match count with risk id "+rd.getRisk_id());
                            }
                        }
                        System.out.println("Scanning for next policy...");
                        for(int i=0;i<4;i++) System.out.println(".");
                    }
                    System.out.println("NO policies remaining");
                    if(violatedPolicy!=null)
                    {
                        SimpleMailMessage smm=new SimpleMailMessage();
                        smm.setTo("anubhav.ranjan001@gmail.com");
                        smm.setSubject("Critical");
                        smm.setText("Some of the  policies have been Violated");
                        javaMailSender.send(smm);
                    }
                }

            }
        }
        System.out.println("Scanning another File");
    }
}

