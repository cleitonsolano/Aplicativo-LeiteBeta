package br.com.leitebeta.leitebeta.cadastro.fragments;

import android.content.Intent;
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

public class CadastroDadosFazendaFragment extends Fragment implements BlockingStep {

    TextInputLayout ilNomeFazenda;
    TextInputLayout ilCodigoProdutor;
    TextInputLayout ilCep;

    TextInputEditText etNomeFazenda;
    TextInputEditText etCodigoProdutor;
    TextInputEditText etCep;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dados_fazenda_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initCampos();
        initWatchers();
    }

    private void initCampos() {
        ilNomeFazenda = (TextInputLayout) getActivity().findViewById(R.id.ilNomeFazenda);
        ilCodigoProdutor = (TextInputLayout) getActivity().findViewById(R.id.ilCodigoProdutor);
        ilCep = (TextInputLayout) getActivity().findViewById(R.id.ilCep);

        etNomeFazenda = (TextInputEditText) getActivity().findViewById(R.id.etNomeFazenda);
        etCodigoProdutor = (TextInputEditText) getActivity().findViewById(R.id.etCodigoProdutor);
        etCep = (TextInputEditText) getActivity().findViewById(R.id.etCep);
    }

    private void initWatchers() {
        etCep.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etCep.getText().toString().isEmpty())
                    layoutError(ilCep, true, "Campo Obrigatório");
                else
                    layoutError(ilCep, false, "");
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

        etCodigoProdutor.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etCodigoProdutor.getText().toString().isEmpty())
                    layoutError(ilCodigoProdutor, true, "Campo Obrigatório");
                else
                    layoutError(ilCodigoProdutor, false, "");
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

        etNomeFazenda.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etNomeFazenda.getText().toString().isEmpty())
                    layoutError(ilNomeFazenda, true, "Campo Obrigatório");
                else
                    layoutError(ilNomeFazenda, false, "");
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

//    public interface produtorlistener {
//        public void setProdutor(Usuario produtor);
//        public Usuario getProdutor();
//    }

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

//        if (etCep.getText().toString().isEmpty()){
//            validacao = false;
//            layoutError(ilCep, true, "Campo Obrigatório");
//        } else {
//            layoutError(ilCep, false, "");
//        }
//        if (etNomeFazenda.getText().toString().isEmpty()){
//            validacao = false;
//            layoutError(ilNomeFazenda, true, "Campo Obrigatório");
//        } else {
//            layoutError(ilNomeFazenda, false, "");
//        }
//        if (etCodigoProdutor.getText().toString().isEmpty()){
//            validacao = false;
//            layoutError(ilCodigoProdutor, true, "Campo Obrigatório");
//        } else {
//            layoutError(ilCodigoProdutor, false, "");
//        }

        if (validacao) {
            Usuario usuario = ((CadastroActivity)getActivity()).getUser();
            if (!etCep.getText().toString().isEmpty())
                usuario.setCep(Integer.valueOf(etCep.getText().toString()));
            if (!etNomeFazenda.getText().toString().isEmpty())
                usuario.setNomeFazenda(etNomeFazenda.getText().toString());
            if (!etCodigoProdutor.getText().toString().isEmpty())
                usuario.setCodigoProdutor(etCodigoProdutor.getText().toString());
            callback.goToNextStep();
        }
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }
}

