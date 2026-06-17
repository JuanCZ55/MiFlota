package com.supra.miflota.ui.revision;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supra.miflota.databinding.FragmentRevisionBinding;

public class RevisionFragment extends Fragment {
    private FragmentRevisionBinding binding;

    private RevisionViewModel viewModel;

    public static RevisionFragment newInstance() {
        return new RevisionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRevisionBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(RevisionViewModel.class);

        Bundle bundle = getArguments();

        viewModel.getRevisionMutable().observe(getViewLifecycleOwner(), checklistDiario -> {
            binding.tvIdRevision.setText(String.valueOf(checklistDiario.getIdChecklistDiario()));
            binding.swEstado.setChecked(checklistDiario.isEstado());
        });









        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}