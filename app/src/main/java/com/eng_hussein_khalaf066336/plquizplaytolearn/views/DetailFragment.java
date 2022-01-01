package com.eng_hussein_khalaf066336.plquizplaytolearn.views;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.eng_hussein_khalaf066336.plquizplaytolearn.R;
import com.eng_hussein_khalaf066336.plquizplaytolearn.databinding.FragmentDetailBinding;
import com.eng_hussein_khalaf066336.plquizplaytolearn.model.QuizListModel;
import com.eng_hussein_khalaf066336.plquizplaytolearn.viewmodel.QuizListViewModel;

public class DetailFragment extends Fragment {
    private FragmentDetailBinding binding;
    private QuizListViewModel quizListViewModel;
    private NavController navController;
    private int position;
    private String quizId;
    private long totalQueCount;
    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quizListViewModel=new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuizListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        position = DetailFragmentArgs.fromBundle(getArguments()).getPostion();
        quizListViewModel.getQuizListLiveData().observe(getViewLifecycleOwner(), quizListModels -> {
            QuizListModel quiz = quizListModels.get(position);
            binding.detailFragmentDifficulty.setText(quiz.getDifficulty());
            binding.detailFragmentTitle.setText(quiz.getTitle());
            binding.detailFragmentQuestions.setText(String.valueOf(quiz.getQuestions()));
            Glide.with(view).load(quiz.getImage()).into(binding.detailFragmentImage);
            Handler handler = new Handler();
            handler.postDelayed(() -> binding.detailProgressBar.setVisibility(View.GONE),1500);

            totalQueCount = quiz.getQuestions();
            quizId = quiz.getQuizId();
        });
        binding.startQuizBtn.setOnClickListener(v -> {
            DetailFragmentDirections.ActionDetailFragment2ToQuizFragment action =
                    DetailFragmentDirections.actionDetailFragment2ToQuizFragment();

            action.setQuizid(quizId);
            action.setTotalQueCount(totalQueCount);
            navController.navigate(action);
        });
    }
}
