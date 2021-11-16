package com.eng_hussein_khalaf066336.plquizplaytolearn.views;

import android.os.Bundle;

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
import android.widget.Toast;

import com.eng_hussein_khalaf066336.plquizplaytolearn.R;
import com.eng_hussein_khalaf066336.plquizplaytolearn.databinding.FragmentSplashScreenBinding;
import com.eng_hussein_khalaf066336.plquizplaytolearn.viewmodel.AuthViewModel;

public class SplashScreenFragment extends Fragment {
    private FragmentSplashScreenBinding binding;
    private AuthViewModel authViewModel;
    private NavController navController;
    public SplashScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authViewModel= new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);
        navController = Navigation.findNavController(view);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentSplashScreenBinding.inflate(inflater,container,false);
        binding.SplashScreenImageViewLogo.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        binding.SplashScreenLottieAnimationView4.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            if (authViewModel.getCurrentUser() != null){
                navController.navigate(R.id.action_splashScreenFragment_to_listFragment);

            }else{
                navController.navigate(R.id.action_splashScreenFragment_to_signInFragment);

            }
        }, 4500);
    }
}
