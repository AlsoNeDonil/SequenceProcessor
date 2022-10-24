package com.NeDonil.SequenceProcessor.controller;

import com.NeDonil.SequenceProcessor.service.SequenceProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/process")
public class SequenceProccessorController {

    @Autowired
    private SequenceProcessorService service;

    @GetMapping("/max")
    ResponseEntity maxNumber(@RequestParam String filename){
        try {
            return ResponseEntity.ok().body(service.maxNumber(filename));
        } catch(FileNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/min")
    ResponseEntity minNumber(@RequestParam String filename){
        try {
            return ResponseEntity.ok().body(service.minNumber(filename));
        } catch(FileNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/average")
    ResponseEntity averageNumber(@RequestParam String filename){
        try {
            return ResponseEntity.ok().body(service.average(filename));
        } catch(FileNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/increase_sequence")
    ResponseEntity increaseSequence(@RequestParam String filename){
        try {
            return ResponseEntity.ok().body(service.increaseSequences(filename));
        } catch(FileNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/decrease_sequence")
    ResponseEntity decreaseSequence(@RequestParam String filename){
        try {
            return ResponseEntity.ok().body(service.decreaseSequences(filename));
        } catch(FileNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
