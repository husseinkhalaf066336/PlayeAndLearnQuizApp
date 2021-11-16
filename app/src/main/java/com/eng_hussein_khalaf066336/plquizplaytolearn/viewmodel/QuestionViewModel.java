package com.eng_hussein_khalaf066336.plquizplaytolearn.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eng_hussein_khalaf066336.plquizplaytolearn.model.QuestionModel;
import com.eng_hussein_khalaf066336.plquizplaytolearn.repository.QuestionRepository;

import java.util.HashMap;
import java.util.List;

public class QuestionViewModel extends ViewModel implements QuestionRepository.OnQuestionLoad, QuestionRepository.OnResultAdded, QuestionRepository.OnResultLoad {

    private MutableLiveData<List<QuestionModel>> questionMutableLiveData;
    private QuestionRepository repository;
    private MutableLiveData<HashMap<String , Long>> resultMutableLiveData;

    public QuestionViewModel(){
        questionMutableLiveData = new MutableLiveData<>();
        resultMutableLiveData = new MutableLiveData<>();
        repository = new QuestionRepository(this,this,this);
    }
    public MutableLiveData<List<QuestionModel>> getQuestionMutableLiveData() {
        return questionMutableLiveData;
    }
    public MutableLiveData<HashMap<String, Long>> getResultMutableLiveData() {
        return resultMutableLiveData;
    }
    public void setQuizId(String quizId){
        repository.setQuizId(quizId);
    }
    public void addResults(HashMap<String , Object> resultMap){
        repository.addResults(resultMap);
    }

    public void getResults(){
        repository.getResults();
    }
    public void getQuestions(){
        repository.getQuestions();
    }
    @Override
    public void onLoad(List<QuestionModel> questionModels) {
        questionMutableLiveData.setValue(questionModels);
    }

    @Override
    public boolean onSubmit() {
        return true;
    }

    @Override
    public void onResultLoad(HashMap<String, Long> resultMap) {
        resultMutableLiveData.setValue(resultMap);

    }

    @Override
    public void onError(Exception e) {
        Log.d("QuizError", "onError: " + e.getMessage());
    }
}