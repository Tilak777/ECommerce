package com.project.ecommerce.ui.base;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.project.ecommerce.R;

public class BaseFragment extends Fragment {

    public NavController getMyNavController(){
        return Navigation.findNavController(requireActivity(), R.id.fragContainerView);
    }
    public NavController getMyNavController(View view){
        return Navigation.findNavController(view);
    }

    public void showMessage(String message){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

}
