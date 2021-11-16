package com.eng_hussein_khalaf066336.plquizplaytolearn.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eng_hussein_khalaf066336.plquizplaytolearn.R;
import com.eng_hussein_khalaf066336.plquizplaytolearn.databinding.FragmentResultBinding;
import com.eng_hussein_khalaf066336.plquizplaytolearn.viewmodel.QuestionViewModel;

import java.util.HashMap;

public class ResultFragment extends Fragment {
    FragmentResultBinding binding;
    private QuestionViewModel questionViewModel;
    private NavController navController;
    private String quizId;

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuestionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentResultBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_resultFragment_to_listFragment);
            }
        });
        quizId = ResultFragmentArgs.fromBundle(getArguments()).getQuizId();

        questionViewModel.setQuizId(quizId);
        questionViewModel.getResults();
        questionViewModel.getResultMutableLiveData().observe(getViewLifecycleOwner(), stringLongHashMap -> {

            Long correct = stringLongHashMap.get("correct");
            Long wrong = stringLongHashMap.get("wrong");
            Long noAnswer = stringLongHashMap.get("notAnswered");

            binding.correctAnswerTv.setText(correct.toString());
            binding.wrongAnswersTv.setText(wrong.toString());
            binding.notAnsweredTv.setText(noAnswer.toString());

            Long total = correct + wrong + noAnswer;
            Long percent = (correct*100)/total;

            binding.resultPercentageTv.setText(String.valueOf(percent));
            binding.resultCoutProgressBar.setProgress(percent.intValue());

        });


    }
}
