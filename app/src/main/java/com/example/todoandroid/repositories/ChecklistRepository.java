package com.example.todoandroid.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import models.Checklist;

public class ChecklistRepository {

    private final String TAG = this.getClass().getCanonicalName();
    private FirebaseFirestore db;
    private final String COLLECTION_NAME_CHECKLIST = "checklist";
    private final String COLLECTION_NAME_USERS = "users";

    public MutableLiveData<List<Checklist>> checklistItems = new MutableLiveData<List<Checklist>>();

    public ChecklistRepository(){
        this.db = FirebaseFirestore.getInstance();
    }

    public void addChecklistItem(String userID, Checklist checklist){
        try{
            db.collection(COLLECTION_NAME_USERS)
                    .document(userID)
                    .collection(COLLECTION_NAME_CHECKLIST)
                    .add(checklist)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "Checklist item added with ID : "+ documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error adding checklist document");
                        }
                    });

        }catch (Exception ex){
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }

    public void getAllChecklistItems(String userID){
        try{
            db.collection(COLLECTION_NAME_USERS)
                    .document(userID)
                    .collection(COLLECTION_NAME_CHECKLIST)
                    .orderBy("dateCreated", Query.Direction.DESCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                            if (error != null){
                                Log.e(TAG, "Listening to checklist changes FAILED", error);
                                return;
                            }

                            List<Checklist> tempChecklistItems = new ArrayList<>();

                            if (snapshot != null){
                                Log.d(TAG, "Current data : " + snapshot.getDocumentChanges());

                                for (DocumentChange documentChange : snapshot.getDocumentChanges()){

                                    Checklist checklist = documentChange.getDocument().toObject(Checklist.class);
                                    checklist.setId(documentChange.getDocument().getId());


                                    switch (documentChange.getType()){
                                        case ADDED:
                                            tempChecklistItems.add(checklist);
                                            break;
                                        case MODIFIED:
                                            break;
                                        case REMOVED:
                                            tempChecklistItems.remove(checklist);
                                            break;
                                    }
                                }

                                Log.e(TAG, tempChecklistItems.toString());
                                checklistItems.postValue(tempChecklistItems);

                            }else{
                                Log.e(TAG, "No changes in checklist");
                            }
                        }
                    });

        }catch(Exception ex){
            Log.e(TAG, ex.getLocalizedMessage());
            Log.e(TAG, ex.toString());
        }
    }

    public void deleteChecklist(String userID, String checklistID){
        db.collection(COLLECTION_NAME_USERS)
                .document(userID)
                .collection(COLLECTION_NAME_CHECKLIST)
                .document(checklistID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "Document deleted successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failure deleting document");
                    }
                });
    }

    public void updateChecklist(String userID, String checklistID, Boolean completion){
        db.collection(COLLECTION_NAME_USERS)
                .document(userID)
                .collection(COLLECTION_NAME_CHECKLIST)
                .document(checklistID)
                .update("completion", completion)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "Document updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failure updating document");
                    }
                });
    }

    //TASK : complete the subsequent calls for updateChecklist
    // method for updating the completion status.

}
