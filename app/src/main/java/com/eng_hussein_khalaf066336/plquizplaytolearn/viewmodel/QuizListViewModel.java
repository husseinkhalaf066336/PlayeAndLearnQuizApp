package com.eng_hussein_khalaf066336.plquizplaytolearn.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eng_hussein_khalaf066336.plquizplaytolearn.model.QuizListModel;
import com.eng_hussein_khalaf066336.plquizplaytolearn.repository.QuizListRepository;

import java.util.List;

public class QuizListViewModel extends ViewModel implements QuizListRepository.onFireStoreTaskComplete {
    private MutableLiveData<List<QuizListModel>> quizListLiveData = new MutableLiveData<>();
    private QuizListRepository repository = new QuizListRepository(this);

    public QuizListViewModel(){
        repository.getQuizData();
    }

    public MutableLiveData<List<QuizListModel>> getQuizListLiveData() {
        return quizListLiveData;
    }

    @Override
    public void quizDataLoaded(List<QuizListModel> quizListModels) {
        quizListLiveData.setValue(quizListModels);
    }

    @Override
    public void onError(Exception e) {
        Log.d("ERROR", "onError: " + e.getMessage());
    }
}
