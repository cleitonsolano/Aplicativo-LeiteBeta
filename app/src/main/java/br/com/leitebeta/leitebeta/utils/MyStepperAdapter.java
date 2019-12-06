package br.com.leitebeta.leitebeta.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import br.com.leitebeta.leitebeta.cadastro.fragments.CadastroDadosAcessoFragment;
import br.com.leitebeta.leitebeta.cadastro.fragments.CadastroDadosFazendaFragment;
import br.com.leitebeta.leitebeta.cadastro.fragments.CadastroDadosPessoaisFragment;

/**
 * Created by Jonathas Eloi on 13/09/17.
 */

public class MyStepperAdapter extends AbstractFragmentStepAdapter {

    public MyStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        if (position == 0) {
            final CadastroDadosPessoaisFragment step = new CadastroDadosPessoaisFragment();
            Bundle b = new Bundle();
            b.putInt("CURRENT_STEP_POSITION_KEY", position);
            step.setArguments(b);
            return step;
        } else if (position == 1){
            final CadastroDadosFazendaFragment step = new CadastroDadosFazendaFragment();
            Bundle b = new Bundle();
            b.putInt("CURRENT_STEP_POSITION_KEY", position);
            step.setArguments(b);
            return step;
        } else{
            final CadastroDadosAcessoFragment step = new CadastroDadosAcessoFragment();
            Bundle b = new Bundle();
            b.putInt("CURRENT_STEP_POSITION_KEY", position);
            step.setArguments(b);
            return step;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return new StepViewModel.Builder(context)
                .setTitle("teste") //can be a CharSequence instead
                .create();
    }
}
