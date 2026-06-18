package com.supra.miflota.ui.revision;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.supra.miflota.data.models.ChecklistDiario;

public class RevisionViewModel extends AndroidViewModel {
    private MutableLiveData<ChecklistDiario> revisionMutable;
    private MutableLiveData<String> mensajeError;

    public RevisionViewModel(@NonNull Application application) {
        super(application);
        revisionMutable = new MutableLiveData<>();
        mensajeError = new MutableLiveData<>();
    }

    public LiveData<ChecklistDiario> getRevisionMutable(){
        return revisionMutable;
    }

    public void cargarRevision(Bundle bundle){
        if (bundle == null || !bundle.containsKey("Revision")) {
            mensajeError.setValue("No se pudo cargar la información de la revisión.");
            return;
        }

        ChecklistDiario revision = (ChecklistDiario) bundle.getSerializable("Revision");
        if (revision == null) {
            mensajeError.setValue("No se pudo cargar la información de la revisión.");
            return;
        }

        revisionMutable.postValue(revision);
    }



}