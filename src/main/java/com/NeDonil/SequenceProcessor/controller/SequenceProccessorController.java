package com.NeDonil.SequenceProcessor.controller;

import com.NeDonil.SequenceProcessor.service.SequenceProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("/process")
public class SequenceProccessorController {

    @Autowired
    private SequenceProcessorService service;

    @GetMapping String tmp(){
        return "good";
    }

    @PostMapping
    ResponseEntity process(@RequestBody Map<String,String> request){

        try {
            var action = request.get("action");
            var filename = request.get("filename");

            switch (action) {
                case "max":
                    return ResponseEntity.ok().body(service.maxNumber(filename));
                case "min":
                    return ResponseEntity.ok().body(service.minNumber(filename));
                case "average":
                    return ResponseEntity.ok().body(service.average(filename));
                case "median":
                    return ResponseEntity.ok().body(service.medianNumber(filename));
                case "increase_sequence":
                    return ResponseEntity.ok().body(service.increaseSequences(filename));
                case "decrease_sequence":
                    return ResponseEntity.ok().body(service.decreaseSequences(filename));
                default:
                    return ResponseEntity.badRequest().body("Unknown operation");
            }
        } catch(FileNotFoundException e){
            return ResponseEntity.badRequest().body("File not found");
        } catch(NullPointerException e){
            return ResponseEntity.badRequest().body("Action or filename is missing");
        }
    }

}
