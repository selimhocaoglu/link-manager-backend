package com.selimhocaoglu.linkmanager.service;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.selimhocaoglu.linkmanager.dto.LinkCreateRequest;
import com.selimhocaoglu.linkmanager.dto.LinkResponse;
import com.selimhocaoglu.linkmanager.dto.LinkUpdateRequest;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class LinkService {
    private final Firestore firestore;

    public LinkService() throws IOException {
        // JSON dosyanızın yolunu belirtin
        String jsonPath = "src\\main\\resources\\link-manager-b14f3-firebase-adminsdk-x4mex-5de86659a6.json";

        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setCredentialsProvider(() -> GoogleCredentials.fromStream(new FileInputStream(jsonPath)))
                .build();
        this.firestore = firestoreOptions.getService();
    }



    public LinkResponse createLink(LinkCreateRequest request) throws ExecutionException, InterruptedException {
        CollectionReference links = firestore.collection("links");
        ApiFuture<DocumentReference> future = links.add(request); // DocumentReference döndürür
        DocumentReference documentReference = future.get();
        return new LinkResponse(documentReference.getId(), request.getUrl(), request.getDescription());
    }

    public LinkResponse getLinkById(String id) throws ExecutionException, InterruptedException{
        DocumentReference documentReference = firestore.collection("links").document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot documentSnapshot = future.get();
        if(documentSnapshot.exists()){
            return new LinkResponse(id, documentSnapshot.getString("url"), documentSnapshot.getString("description"));
        }
        else{
            return null; // add exception
        }
    }

    public List<LinkResponse> getAllLinks() throws ExecutionException, InterruptedException {
        List<LinkResponse> linkResponses = new ArrayList<>();
        CollectionReference links = firestore.collection("links");
        ApiFuture<QuerySnapshot> future = links.get(); // QuerySnapshot döndürür
        QuerySnapshot querySnapshot = future.get();
        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            LinkResponse linkResponse = new LinkResponse(
                    document.getId(),
                    document.getString("url"),
                    document.getString("description")
            );
            linkResponses.add(linkResponse);
        }
        return linkResponses;
    }

    public LinkResponse updateLink(String id, LinkUpdateRequest request) throws ExecutionException, InterruptedException{
        DocumentReference documentReference = firestore.collection("links").document(id);
        ApiFuture<WriteResult> future = documentReference.set(request);
        future.get(); // Ensure that write is successful
        return new LinkResponse(id, request.getUrl(), request.getDescription());
    }

    public void deleteLink(String id) throws ExecutionException, InterruptedException{
        DocumentReference documentReference = firestore.collection("links").document(id);
        ApiFuture<WriteResult> future = documentReference.delete();
        future.get();
    }

}
