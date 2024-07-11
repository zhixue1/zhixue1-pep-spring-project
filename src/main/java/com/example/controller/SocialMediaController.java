package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    //user registration
    @PostMapping("register")
    public ResponseEntity<Account> postUserRegistrationHandler(@RequestBody Account account){
        Account newAccount = accountService.userRegistration(account);
        if(newAccount != null){
            return ResponseEntity.status(200).body(newAccount);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    //login
    @PostMapping("login")
    public ResponseEntity<Account> postLoginHandler(@RequestBody Account account){
        Account loginAccount = accountService.login(account);
        if(loginAccount != null){
            return ResponseEntity.status(200).body(loginAccount);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    //create new message
    @PostMapping("messages")
    public ResponseEntity<Message> postNewMessageHandler(@RequestBody Message message){
        Message newMessage = messageService.createNewMessage(message);
        if (newMessage != null){
            return ResponseEntity.status(200).body(newMessage);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    //get all messages
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessagesHandler(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    //get one message given message id
    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getOneMessageHandler(@PathVariable("message_id") Integer messageId){
        return ResponseEntity.status(200).body(messageService.getOneMessage(messageId));
    }

    //delete a message given message id
    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteOneMessageHandler(@PathVariable("message_id") Integer messageId){
        return ResponseEntity.status(200).body(messageService.deleteOneMessage(messageId));
    }

    //update message given message id
    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> patchOneMessageHandler(@PathVariable("message_id") Integer messageId, @RequestBody Message message){
        Integer updatedMessageCount = messageService.updateMessage(messageId, message);
        if(updatedMessageCount != null){
            return ResponseEntity.status(200).body(updatedMessageCount);
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    //get all messages from user given account id
    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllUserMessagesHandler(@PathVariable("account_id") Integer accountId){
        return ResponseEntity.status(200).body(messageService.getAllUserMessages(accountId));
    }

}
