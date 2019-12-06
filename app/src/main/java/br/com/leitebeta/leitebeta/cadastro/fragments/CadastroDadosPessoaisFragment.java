package br.com.leitebeta.leitebeta.cadastro.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import br.com.leitebeta.leitebeta.R;
import br.com.leitebeta.leitebeta.cadastro.CadastroActivity;
import br.com.leitebeta.leitebeta.model.Usuario;

/**
 * Created by Jonathas on 29/08/2017.
 */

public class CadastroDadosPessoaisFragment extends Fragment implements BlockingStep {

    TextInputLayout ilNome;
    TextInputLayout ilTelefone;
    TextInputEditText etTelefone;
    TextInputEditText etNome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dados_pessoais_fragment, container, false);



        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initCampos();

        initWatchers();
    }

    private void initCampos() {
        ilNome = (TextInputLayout) getActivity().findViewById(R.id.ilNome);
        ilTelefone = (TextInputLayout) getActivity().findViewById(R.id.ilTelefone);

        etNome = (TextInputEditText) getActivity().findViewById(R.id.etNome);
        etTelefone = (TextInputEditText) getActivity().findViewById(R.id.etTelefone);
    }

    private void initWatchers() {
//        etNome.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(etNome.getText().toString().isEmpty())
//                    layoutError(ilNome, true, "Campo Obrigatório");
//                else
//                    layoutError(ilNome, false, "");
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                // TODO Auto-generated method stub
//            }
//        });

//        etTelefone.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(etTelefone.getText().toString().isEmpty())
//                    layoutError(ilTelefone, true, "Campo Obrigatório");
//                else
//                    layoutError(ilTelefone, false, "");
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                // TODO Auto-generated method stub
//            }
//        });
    }

    public void layoutError(TextInputLayout inputLayout, Boolean enable, String msg){
        inputLayout.setErrorEnabled(enable);
        inputLayout.setError(msg);
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        Boolean validacao = true;
//        if (etNome.getText().toString().isEmpty()){
//            validacao = false;
//            layoutError(ilNome, true, "Campo Obrigatório");
//        } else {
//            layoutError(ilNome, false, "");
//        }
//        if (etTelefone.getText().toString().isEmpty()){
//            validacao = false;
//            layoutError(ilTelefone, true, "Campo Obrigatório");
//        } else {
//            layoutError(ilTelefone, false, "");
//        }

        if (validacao) {
            Usuario usuario = ((CadastroActivity)getActivity()).getUser();
            if (!etNome.getText().toString().isEmpty())
                usuario.setNome(etNome.getText().toString());
            if (!etTelefone.getText().toString().isEmpty())
                usuario.setTelefone(etTelefone.getText().toString());
            callback.goToNextStep();
        }

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }


}

