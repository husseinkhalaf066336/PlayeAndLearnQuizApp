package com.eng_hussein_khalaf066336.plquizplaytolearn.repository;
import com.eng_hussein_khalaf066336.plquizplaytolearn.model.QuestionModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class QuestionRepository {
    private FirebaseFirestore firebaseFirestore;
    private String quizId;
    private HashMap<String , Long> resultMap= new HashMap<>();
    private OnQuestionLoad onQuestionLoad;
    private OnResultAdded onResultAdded;
    private OnResultLoad onResultLoad;
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public  QuestionRepository(OnQuestionLoad onQuestionLoad , OnResultAdded onResultAdded,OnResultLoad onResultLoad ){
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.onQuestionLoad = onQuestionLoad;
        this.onResultAdded = onResultAdded;
        this.onResultLoad = onResultLoad;

    }
    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }
    public void getQuestions(){
        firebaseFirestore.collection("Quiz").document(quizId)
                .collection("questions").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        onQuestionLoad.onLoad(task.getResult().toObjects(QuestionModel.class));
                    }else{
                        onQuestionLoad.onError(task.getException());
                    }
                });
    }
    public void addResults(HashMap<String , Object> resultMap){
        firebaseFirestore.collection("Quiz").document(quizId)
                .collection("results").document(currentUserId)
                .set(resultMap).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        onResultAdded.onSubmit();
                    }else{
                        onResultAdded.onError(task.getException());
                    }
                });
    }

    public void getResults(){
        firebaseFirestore.collection("Quiz").document(quizId)
                .collection("results").document(currentUserId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        resultMap.put("correct" , task.getResult().getLong("correct"));
                        resultMap.put("wrong" , task.getResult().getLong("wrong"));
                        resultMap.put("notAnswered" , task.getResult().getLong("notAnswered"));
                        onResultLoad.onResultLoad(resultMap);
                    }else{
                        onResultLoad.onError(task.getException());
                    }
                });
    }
    public interface OnQuestionLoad{
        void onLoad(List<QuestionModel> questionModels);
        void onError(Exception e);
    }
    public interface OnResultAdded{
        boolean onSubmit();
        void onError(Exception e);
    }

    public interface OnResultLoad{
        void onResultLoad(HashMap<String , Long> resultMap);
        void onError(Exception e);
    }
}
