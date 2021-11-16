package com.eng_hussein_khalaf066336.plquizplaytolearn.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.eng_hussein_khalaf066336.plquizplaytolearn.adapter.QuizListAdapter;
import com.eng_hussein_khalaf066336.plquizplaytolearn.databinding.FragmentListBinding;

import com.eng_hussein_khalaf066336.plquizplaytolearn.viewmodel.QuizListViewModel;

public class ListFragment extends Fragment implements QuizListAdapter.OnItemClickedListener {
    private FragmentListBinding binding;
    private QuizListViewModel quizListViewModel;
    private NavController navController;
    private QuizListAdapter adapter;
    public ListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quizListViewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuizListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentListBinding.inflate(inflater,container,false);
        return binding.getRoot();    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= Navigation.findNavController(view);
        adapter = new QuizListAdapter(this);
        binding.listQuizRecyclerview.setHasFixedSize(true);
        binding.listQuizRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.listQuizRecyclerview.setAdapter(adapter);
        quizListViewModel.getQuizListLiveData().observe(getViewLifecycleOwner(), quizListModels -> {
            binding.quizListProgressbar.setVisibility(View.GONE);
            adapter.setQuizListModels(quizListModels);
            adapter.notifyDataSetChanged();
        });
    }
    @Override
    public void onItemClick(int position) {
        ListFragmentDirections.ActionListFragmentToDetailFragment2 action=ListFragmentDirections.actionListFragmentToDetailFragment2();
            action.setPostion(position);
            navController.navigate(action);
    }
}
