package com.stormpath.idsite_demo.controllers;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.idsite.AccountResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RestrictedController {
    @Value("#{ @environment['stormpath.organization'] }")
    private String organization;

    @Value("#{ @environment['stormpath.show.organization.field'] }")
    private Boolean showOrganizationField;

    @Value("#{ @environment['stormpath.template.root'] }")
    private String templateRoot = "ro";

    @Autowired
    Application app;

    private static final String ID_SITE_CALLBACK = "/restricted/id_site_callback";

    @RequestMapping("/restricted/user")
    public String idSiteCallback(HttpServletRequest req, Model model) {
        AccountResult accountResult = app.newIdSiteCallbackHandler(req).getAccountResult();
        if (accountResult != null && accountResult.getAccount() != null) {
            Account account = accountResult.getAccount();
            model.addAttribute("account", account);

            return "restricted/user";
        }

        return "redirect:/login";
    }
}
