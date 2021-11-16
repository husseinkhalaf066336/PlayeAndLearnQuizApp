package com.eng_hussein_khalaf066336.plquizplaytolearn.repository;
import com.eng_hussein_khalaf066336.plquizplaytolearn.model.QuizListModel;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class QuizListRepository {
    private onFireStoreTaskComplete onFirestoreTaskComplete;
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    private CollectionReference reference=firebaseFirestore.collection("Quiz");

    public QuizListRepository(onFireStoreTaskComplete onFirestoreTaskComplete){
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }
    public void getQuizData(){
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                onFirestoreTaskComplete.quizDataLoaded(task.getResult()
                        .toObjects(QuizListModel.class));
            }else{
                onFirestoreTaskComplete.onError(task.getException());
            }
        });
    }
    public interface onFireStoreTaskComplete{
        void quizDataLoaded(List<QuizListModel> quizListModels);
        void onError(Exception e);
    }
}
