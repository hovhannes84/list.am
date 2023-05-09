package com.example.listam.controller;

import com.example.listam.entity.Comment;
import com.example.listam.entity.Item;
import com.example.listam.repository.CommentRepository;
import com.example.listam.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ItemRepository itemRepository;


    @PostMapping("/comments/add")
    public String addComment(@RequestParam("comment") String commentText, @RequestParam("item_id") int itemId) {
        Optional<Item> byId = itemRepository.findById(itemId);
        if (byId.isPresent()) {
            Item item = byId.get();
            Comment comment = new Comment();
            comment.setItem(item);
            comment.setComment(commentText);
            commentRepository.save(comment);
        }
        return "redirect:/items/" + itemId;
    }

    @GetMapping("/comments/remove")
    public String removeComment(@RequestParam("id") int id) {
        Optional<Comment> byId = commentRepository.findById(id);
        int itemID = byId.get().getItem().getId();
        commentRepository.deleteById(id);
        return "redirect:/items/"+itemID;
    }

}