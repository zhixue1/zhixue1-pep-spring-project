package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import com.example.entity.Message;

@Service
public class MessageService {

    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    //create new message
    public Message createNewMessage(Message message){
        if(message.getMessageText() != "" && message.getMessageText() != null && message.getMessageText().length() < 255 && accountRepository.findById(message.getPostedBy()).isPresent()){
            return messageRepository.save(message);
        } else {
            return null;
        }
    }

    //get all messages
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    //get one message given message id
    public Message getOneMessage(Integer messageId){
        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isPresent()){
            return message.get();
        } else {
            return null;
        }
    }

    //delete a message given message id
    public Integer deleteOneMessage(Integer messageId){
        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isPresent()){
            messageRepository.deleteById(messageId);
            return 1;
        } else {
            return null;
        }
    }

    //update message given message id
    public Integer updateMessage(Integer messageId, Message message){
        Optional<Message> oldMessage = messageRepository.findById(messageId);
        if(oldMessage.isPresent() && message.getMessageText() != "" && message.getMessageText() != null && message.getMessageText().length() <= 255){
            Message newMessage = oldMessage.get();
            newMessage.setMessageText(message.getMessageText());
            messageRepository.save(newMessage);
            return 1;
        } else {
            return null;
        }
    }

    //get all messages from user given account id
    public List<Message> getAllUserMessages(Integer accountId){
        return messageRepository.findAllByPostedBy(accountId);
    }


}
