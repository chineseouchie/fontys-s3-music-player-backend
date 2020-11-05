package com.joey.musicplayer.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.joey.musicplayer.model.Account;
import com.joey.musicplayer.repository.AccountRepository;
import com.joey.musicplayer.util.Authorizer;
import com.joey.musicplayer.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


@RestController
@RequestMapping("account")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/login", produces = "application/x-www-form-urlencoded")
    public String Login(String username, String password) throws NoSuchAlgorithmException {
        Account account = accountRepository.findAccountByUsername(username);
        HashMap<String, Object> responseMessage = new HashMap<>();

        if (account == null ) {
            responseMessage.put("message", "Username or password did not match");
            responseMessage.put("success", false);

            return new JSONObject(responseMessage).toJSONString();
        }

        String databaseHash = account.getPassword();

        Authorizer auth = new Authorizer();
        String hashedPassword = auth.HashPassword(password, account.getSalt());

        if (auth.ValidatePassword(databaseHash, hashedPassword)) {
            final String jwt = jwtTokenUtil.generateToken(account);
            responseMessage.put("jwt", jwt);

            return new JSONObject(responseMessage).toJSONString();

        } else {
            HashMap<String, Object> data = new HashMap<>();
            data.put("message", "Something went wrong, try again");

            return new JSONObject(data).toJSONString();
        }
    }

    @PostMapping(value = "/register", produces = "application/x-www-form-urlencoded")
    public String Register(
            String username,
            String password,
            String email,
            String firstname,
            String lastname
    ) throws NoSuchAlgorithmException {
        Account account = accountRepository.findAccountByUsername(username);
        HashMap<String, Object> responseMessage = new HashMap<>();

        if (account != null) {
            responseMessage.put("message", "Username already exist");
            responseMessage.put("success", false);

            return new JSONObject(responseMessage).toJSONString();
        }

        // Hash + salt password
        Authorizer auth = new Authorizer();
        String salt = auth.GenerateSalt();
        String hashedPassword = auth.HashPassword(password, salt);

        // Create user
        account = new Account(username, firstname, lastname, hashedPassword, email, salt);
        accountRepository.save(account);

        // Create response message
        responseMessage.put("message", "Registration successful");
        responseMessage.put("success", true);

        return new JSONObject(responseMessage).toJSONString();
    }
}
