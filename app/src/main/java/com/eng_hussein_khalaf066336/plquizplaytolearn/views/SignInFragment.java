package com.eng_hussein_khalaf066336.plquizplaytolearn.views;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eng_hussein_khalaf066336.plquizplaytolearn.R;
import com.eng_hussein_khalaf066336.plquizplaytolearn.databinding.FragmentSignInBinding;
import com.eng_hussein_khalaf066336.plquizplaytolearn.viewmodel.AuthViewModel;

public class SignInFragment extends Fragment {
    private FragmentSignInBinding binding;
    private AuthViewModel authViewModel;
    private NavController navController;

    public SignInFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().getTheme().applyStyle(R.style.Theme_MaterialComponents_Light_DarkActionBar, true);

        authViewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentSignInBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        binding.fragmentSignInButtonRegiste.setOnClickListener(v ->
                navController.navigate(R.id.action_signInFragment_to_signUpFragment));

        binding.fragmentSignInButtonSave.setOnClickListener(v -> {
            String email = binding.fragmentSignInEitTextEmail.getText().toString();
            String pass = binding.fragmentSignInEitTextPassword.getText().toString();

            if (!email.isEmpty() && !pass.isEmpty()) {
                authViewModel.signIn(email, pass);
                Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();

                authViewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
                    if (firebaseUser != null) {
                        navController.navigate(R.id.action_signInFragment_to_listFragment);}});
            }else {
                Toast.makeText(getContext(), "Please Enter Email and Pass", Toast.LENGTH_SHORT).show();
            }
        });
    }
}