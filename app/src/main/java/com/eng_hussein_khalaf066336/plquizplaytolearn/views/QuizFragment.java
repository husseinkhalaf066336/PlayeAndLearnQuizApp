package com.eng_hussein_khalaf066336.plquizplaytolearn.views;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eng_hussein_khalaf066336.plquizplaytolearn.R;
import com.eng_hussein_khalaf066336.plquizplaytolearn.databinding.FragmentQuizBinding;
import com.eng_hussein_khalaf066336.plquizplaytolearn.viewmodel.QuestionViewModel;

import java.util.HashMap;

public class QuizFragment extends Fragment implements View.OnClickListener {
    private FragmentQuizBinding binding;
    private QuestionViewModel questionViewModel;
    private NavController navController;
    private String quizId;
    private long totalQuestions;
    private int currentQueNo = 0;
    private boolean canAnswer = false;
    private long timer;
    private CountDownTimer countDownTimer;
    private int notAnswerd = 0;
    private int correctAnswer = 0;
    private int wrongAnswer = 0;
    private String answer = "";

    public QuizFragment() {
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
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        quizId = QuizFragmentArgs.fromBundle(getArguments()).getQuizid();
        totalQuestions = QuizFragmentArgs.fromBundle(getArguments()).getTotalQueCount();
        questionViewModel.setQuizId(quizId);
        questionViewModel.getQuestions();;
        binding.quizQuestionTv.setOnClickListener(this);
        binding.option1Btn.setOnClickListener(this);
        binding.option2Btn.setOnClickListener(this);
        binding.option3Btn.setOnClickListener(this);
        binding.nextQueBtn.setOnClickListener(this);
        binding.imageView3.setOnClickListener(v -> navController.navigate(R.id.action_quizFragment_to_listFragment));
        loadData();
    }

    private void loadData() {
        enableOptions();
        loadQuestions(1);
    }

    private void enableOptions() {
        binding.option1Btn.setVisibility(View.VISIBLE);
        binding.option1Btn.setVisibility(View.VISIBLE);
        binding.option1Btn.setVisibility(View.VISIBLE);

        //enable buttons , hide feedback tv , hide nextQuiz btn

        binding.option1Btn.setEnabled(true);
        binding.option1Btn.setEnabled(true);
        binding.option1Btn.setEnabled(true);

        binding.ansFeedbackTv.setVisibility(View.INVISIBLE);
        binding.nextQueBtn.setVisibility(View.INVISIBLE);
    }

    private void loadQuestions(int i) {
        currentQueNo = i;
        questionViewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), questionModels -> {
                binding.quizQuestionTv.setText(String.valueOf(currentQueNo) + ") " + questionModels.get(i - 1).getQuestion());
                binding.option1Btn.setText(questionModels.get(i - 1).getOption_a());
                binding.option2Btn.setText(questionModels.get(i - 1).getOption_b());
                binding.option3Btn.setText(questionModels.get(i - 1).getOption_c());
                timer = questionModels.get(i - 1).getTimer();
                answer = questionModels.get(i - 1).getAnswer();
                //todo set current que no, to que number tv
//                binding.quizQuestionTv.setText(String.valueOf(currentQueNo));
                startTimer();

        });
        canAnswer = true;
    }

    private void startTimer() {
        binding.countTimeQuiz.setText(String.valueOf(timer));
        binding.quizCoutProgressBar.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(timer * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // update time
                binding.countTimeQuiz.setText(millisUntilFinished / 1000 + "");

                Long percent = millisUntilFinished / (timer * 10);
                binding.quizCoutProgressBar.setProgress(percent.intValue());
            }
            @Override
            public void onFinish() {
                canAnswer = false;
                binding.ansFeedbackTv.setText("Times Up !! No answer selected");
                notAnswerd++;
                showNextBtn();
            }
        }.start();
    }

    private void showNextBtn() {
        if (currentQueNo == totalQuestions) {
            binding.nextQueBtn.setText("Submit");
            binding.nextQueBtn.setEnabled(true);
            binding.nextQueBtn.setVisibility(View.VISIBLE);
        } else {
            binding.nextQueBtn.setVisibility(View.VISIBLE);
            binding.nextQueBtn.setEnabled(true);
            binding.ansFeedbackTv.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.option1Btn:
                verifyAnswer(binding.option1Btn);
                break;
            case R.id.option2Btn:
                verifyAnswer(binding.option2Btn);
                break;
            case R.id.option3Btn:
                verifyAnswer(binding.option3Btn);
                break;
            case R.id.nextQueBtn:
                if (currentQueNo == totalQuestions) {
                    submitResults();
                } else {
                    currentQueNo++;
                    loadQuestions(currentQueNo);
                    resetOptions();
                }
                break;
        }
    }
    private void resetOptions(){
        binding.ansFeedbackTv.setVisibility(View.INVISIBLE);
        binding.nextQueBtn.setVisibility(View.INVISIBLE);
        binding.nextQueBtn.setEnabled(false);
        binding.option1Btn.setBackground(ContextCompat.getDrawable(getContext() , R.color.colorAccent));
        binding.option2Btn.setBackground(ContextCompat.getDrawable(getContext() , R.color.colorAccent));
        binding.option3Btn.setBackground(ContextCompat.getDrawable(getContext() , R.color.colorAccent));
    }

    private void submitResults() {
        HashMap<String , Object> resultMap = new HashMap<>();
        resultMap.put("correct" , correctAnswer);
        resultMap.put("wrong" , wrongAnswer);
        resultMap.put("notAnswered" , notAnswerd);
        questionViewModel.addResults(resultMap);
        QuizFragmentDirections.ActionQuizFragmentToResultFragment action=
                QuizFragmentDirections.actionQuizFragmentToResultFragment();
        action.setQuizId(quizId);
        navController.navigate(action);


    }
    private void verifyAnswer(Button button) {
        if (canAnswer) {
            if (answer.equals(button.getText())) {
                button.setBackground(ContextCompat.getDrawable(getContext(), R.color.green));
                correctAnswer++;
                binding.ansFeedbackTv.setText("Correct Answer");
            } else {
                button.setBackground(ContextCompat.getDrawable(getContext(), R.color.red));
                wrongAnswer++;
                binding.ansFeedbackTv.setText("Wrong Answer \nCorrect Answer :" + answer);
            }
        }
        canAnswer=false;
        countDownTimer.cancel();
        showNextBtn();
    }

}
