package com.selimhocaoglu.linkmanager.controller;

import com.selimhocaoglu.linkmanager.dto.LinkCreateRequest;
import com.selimhocaoglu.linkmanager.dto.LinkResponse;
import com.selimhocaoglu.linkmanager.dto.LinkUpdateRequest;
import com.selimhocaoglu.linkmanager.dto.ResponseDTO;
import com.selimhocaoglu.linkmanager.service.LinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<LinkResponse>> createLink(@RequestBody LinkCreateRequest request){
        try{
            LinkResponse linkResponse = linkService.createLink(request);
            ResponseDTO<LinkResponse> response = new ResponseDTO<>("success", linkResponse, "Link created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (Exception e){
            ResponseDTO<LinkResponse> linkResponse = new ResponseDTO<>("error", null, "Failed to create link");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(linkResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<LinkResponse>> getLinkById(@PathVariable String id) {
        try {
            LinkResponse link = linkService.getLinkById(id);
            if (link != null) {
                ResponseDTO<LinkResponse> response = new ResponseDTO<>("success", link, "Link retrieved successfully");
                return ResponseEntity.ok(response);
            } else {
                ResponseDTO<LinkResponse> response = new ResponseDTO<>("error", null, "Link not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ResponseDTO<LinkResponse> response = new ResponseDTO<>("error", null, "Failed to retrieve link");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<LinkResponse>>> getAllLinks() {
        try {
            List<LinkResponse> links = linkService.getAllLinks();
            ResponseDTO<List<LinkResponse>> response = new ResponseDTO<>("success", links, "Links retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<LinkResponse>> response = new ResponseDTO<>("error", null, "Failed to retrieve links");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<LinkResponse>> updateLink(@PathVariable String id, @RequestBody LinkUpdateRequest request) {
        try {
            LinkResponse link = linkService.updateLink(id, request);
            ResponseDTO<LinkResponse> response = new ResponseDTO<>("success", link, "Link updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<LinkResponse> response = new ResponseDTO<>("error", null, "Failed to update link");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteLink(@PathVariable String id) {
        try {
            linkService.deleteLink(id);
            ResponseDTO<Void> response = new ResponseDTO<>("success", null, "Link deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            ResponseDTO<Void> response = new ResponseDTO<>("error", null, "Failed to delete link");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
