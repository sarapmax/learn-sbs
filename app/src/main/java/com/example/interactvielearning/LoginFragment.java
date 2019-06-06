package com.example.interactvielearning;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        Button loginButton = rootView.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillboardFragment nextFrag = new BillboardFragment();

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }
}
