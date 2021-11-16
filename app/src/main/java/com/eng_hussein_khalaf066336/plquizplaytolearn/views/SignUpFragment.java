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
import android.widget.Toast;

import com.eng_hussein_khalaf066336.plquizplaytolearn.R;
import com.eng_hussein_khalaf066336.plquizplaytolearn.databinding.FragmentSignUpBinding;
import com.eng_hussein_khalaf066336.plquizplaytolearn.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;

public class SignUpFragment extends Fragment {
    private FragmentSignUpBinding binding;
    private AuthViewModel authViewModel;
    private NavController navController;
    public SignUpFragment() {
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
        binding=FragmentSignUpBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        binding.fragmentSignUpButtonBackSignIn.setOnClickListener(v -> {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment);
        });

        binding.fragmentSignUpButtonSave.setOnClickListener(v -> {
            String email = binding.fragmentSignUpEitTextEmail.getText().toString();
            String pass = binding.fragmentSignUpEitTextPassword.getText().toString();

            if (!email.isEmpty() && !pass.isEmpty()) {
                authViewModel.signUp(email, pass);
                Toast.makeText(getContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                authViewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
                    @Override
                    public void onChanged(FirebaseUser firebaseUser) {
                        if (firebaseUser !=null){
                            navController.navigate(R.id.action_signUpFragment_to_signInFragment);
                        }
                    }
                });
            }else {
                Toast.makeText(getContext(), "Please Enter Email and Pass", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
