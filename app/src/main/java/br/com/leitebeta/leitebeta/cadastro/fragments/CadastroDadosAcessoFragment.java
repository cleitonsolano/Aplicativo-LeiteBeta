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
import android.widget.EditText;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import br.com.leitebeta.leitebeta.R;
import br.com.leitebeta.leitebeta.cadastro.CadastroActivity;
import br.com.leitebeta.leitebeta.model.Usuario;

/**
 * Created by Jonathas on 29/08/2017.
 */

public class CadastroDadosAcessoFragment extends Fragment implements BlockingStep {

    TextInputLayout ilEmail;
    TextInputLayout ilSenha;
    TextInputLayout ilConfirmarSenha;

    TextInputEditText etEmail;
    TextInputEditText etSenha;
    TextInputEditText etConfirmarSenha;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dados_acesso_fragment, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initCampos();
        initWatchers();
    }

    private void initCampos() {
        ilEmail = (TextInputLayout) getActivity().findViewById(R.id.ilEmail);
        ilSenha = (TextInputLayout) getActivity().findViewById(R.id.ilSenha);
        ilConfirmarSenha = (TextInputLayout) getActivity().findViewById(R.id.ilConfirmarSenha);

        etEmail = (TextInputEditText) getActivity().findViewById(R.id.etEmail);
        etSenha = (TextInputEditText) getActivity().findViewById(R.id.etSenha);
        etConfirmarSenha = (TextInputEditText) getActivity().findViewById(R.id.etConfirmarSenha);

    }

    private void initWatchers() {
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etEmail.getText().toString().isEmpty())
                    layoutError(ilEmail, true, "Campo Obrigatório");
                else
                    layoutError(ilEmail, false, "");
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        etSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etSenha.getText().toString().isEmpty())
                    layoutError(ilSenha, true, "Campo Obrigatório");
                else
                    layoutError(ilSenha, false, "");
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        etConfirmarSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etConfirmarSenha.getText().toString().isEmpty())
                    layoutError(ilConfirmarSenha, true, "Campo Obrigatório");
                else
                    layoutError(ilConfirmarSenha, false, "");
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });
    }

    public void layoutError(TextInputLayout inputLayout, Boolean enable, String msg){
        inputLayout.setErrorEnabled(enable);
        inputLayout.setError(msg);
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

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        Boolean validacao = true;

        if (etEmail.getText().toString().isEmpty()){
            validacao = false;
            layoutError(ilEmail, true, "Campo Obrigatório");
        } else {
            layoutError(ilEmail, false, "");
        }
        if (etSenha.getText().toString().isEmpty()){
            validacao = false;
            layoutError(ilSenha, true, "Campo Obrigatório");
        } else {
            layoutError(ilSenha, false, "");
        }
        if (etConfirmarSenha.getText().toString().isEmpty()){
            validacao = false;
            layoutError(ilConfirmarSenha, true, "Campo Obrigatório");
        } else {
            layoutError(ilConfirmarSenha, false, "");
        }

        if (validacao) {
            Usuario usuario = new Usuario(etEmail.getText().toString(), etSenha.getText().toString());
            ((CadastroActivity) getActivity()).setUser(usuario);
            callback.goToNextStep();
        }
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        Boolean validacao = true;

        if (etEmail.getText().toString().isEmpty()){
            validacao = false;
            layoutError(ilEmail, true, "Campo Obrigatório");
        } else {
            layoutError(ilEmail, false, "");
        }
        if (etSenha.getText().toString().isEmpty()){
            validacao = false;
            layoutError(ilSenha, true, "Campo Obrigatório");
        } else {
            layoutError(ilSenha, false, "");
        }
        if (etConfirmarSenha.getText().toString().isEmpty()){
            validacao = false;
            layoutError(ilConfirmarSenha, true, "Campo Obrigatório");
        } else {
            layoutError(ilConfirmarSenha, false, "");
        }
        if (!etSenha.getText().toString().equals(etConfirmarSenha.getText().toString())) {
            validacao = false;
            layoutError(ilConfirmarSenha, true, "Senha não confere");
        } else {
            layoutError(ilConfirmarSenha, false, "");
        }

        if (validacao) {
            Usuario usuario = new Usuario(etEmail.getText().toString(), etSenha.getText().toString());
            ((CadastroActivity) getActivity()).setUser(usuario);
            callback.complete();
        }
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }
}

