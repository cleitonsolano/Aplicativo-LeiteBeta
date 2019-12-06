package br.com.leitebeta.leitebeta.perfil;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.leitebeta.leitebeta.LoginActivity;
import br.com.leitebeta.leitebeta.R;
import br.com.leitebeta.leitebeta.cadastro.CadastroActivity;
import br.com.leitebeta.leitebeta.model.Usuario;

public class PerfilActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    private Usuario usuario;

    private TextInputEditText etNome;
    private TextInputLayout ilNome;
    private TextInputEditText etEmail;
    private TextInputLayout ilEmail;
    private TextInputEditText etTelefone;
    private TextInputLayout ilTelefone;
    private TextInputEditText etNomeFazenda;
    private TextInputLayout ilNomeFazenda;
    private TextInputEditText etCodigoProdutor;
    private TextInputLayout ilCodigoProdutor;
    private TextInputEditText etCep;
    private TextInputLayout ilCep;

    private Boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        initCampos();

        mAuth = FirebaseAuth.getInstance();

        String user_id = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        if (getIntent().getExtras() != null)
            usuario = getIntent().getParcelableExtra("usuario");

        if (usuario != null)
            preencherCampos(usuario);
        else {
            mDatabase.child(user_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        usuario = dataSnapshot.getValue(Usuario.class);
                        preencherCampos(usuario);
                        //Faça o que quiser com o objeto.
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("TAG", databaseError.getMessage());
                }
            });
        }
    }

    private void initCampos() {
        ilEmail = (TextInputLayout) findViewById(R.id.ilEmail);
        ilTelefone = (TextInputLayout) findViewById(R.id.ilTelefone);
        ilNome = (TextInputLayout) findViewById(R.id.ilNome);
        ilNomeFazenda = (TextInputLayout) findViewById(R.id.ilNomeFazenda);
        ilCodigoProdutor = (TextInputLayout) findViewById(R.id.ilCodigoProdutor);
        ilCep = (TextInputLayout) findViewById(R.id.ilCep);

        etEmail = (TextInputEditText) findViewById(R.id.etEmail);
        etTelefone = (TextInputEditText) findViewById(R.id.etTelefone);
        etNome = (TextInputEditText) findViewById(R.id.etNome);
        etNomeFazenda = (TextInputEditText) findViewById(R.id.etNomeFazenda);
        etCodigoProdutor = (TextInputEditText) findViewById(R.id.etCodigoProdutor);
        etCep = (TextInputEditText) findViewById(R.id.etCep);
    }

    public void preencherCampos(Usuario usuario) {
        if (usuario.getNome()!=null)
            etNome.setText(usuario.getNome());
        if (usuario.getCep() != 0)
            etCep.setText(usuario.getCep());
        if (usuario.getNomeFazenda()!=null)
            etNomeFazenda.setText(usuario.getNomeFazenda());
        if (usuario.getCodigoProdutor()!=null)
            etCodigoProdutor.setText(usuario.getCodigoProdutor());
        if (usuario.getTelefone()!=null)
            etTelefone.setText(usuario.getTelefone());
        if (usuario.getEmail()!=null)
            etEmail.setText(usuario.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.editar:
                edit = !edit;
                etNome.setEnabled(edit);
                etCep.setEnabled(edit);
                etNomeFazenda.setEnabled(edit);
                etCodigoProdutor.setEnabled(edit);
                etTelefone.setEnabled(edit);
//                etEmail.setEnabled(edit);
                invalidateOptionsMenu();
                break;
            case R.id.salvar :
                if (validar()) {
                    Toast.makeText(PerfilActivity.this, "Usuário editado com sucesso!!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(PerfilActivity.this, "Edição falhou!",
                            Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (edit) {
            menu.findItem(R.id.editar).setVisible(false);
            menu.findItem(R.id.salvar).setVisible(true);
        } else {
            menu.findItem(R.id.editar).setVisible(true);
            menu.findItem(R.id.salvar).setVisible(false);
        }

        return true;
    }

    private Boolean validar() {
        Boolean validacao = true;
//        if (etEmail.getText().toString().isEmpty()){
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
            Usuario usuario = new Usuario();
            if (!etNome.getText().toString().isEmpty())
                usuario.setNome(etNome.getText().toString());
            if (!etTelefone.getText().toString().isEmpty())
                usuario.setTelefone(etTelefone.getText().toString());
            if (!etEmail.getText().toString().isEmpty())
                usuario.setEmail(etEmail.getText().toString());
            if (!etCep.getText().toString().isEmpty())
                usuario.setCep(Integer.valueOf(etCep.getText().toString()));
            if (!etNomeFazenda.getText().toString().isEmpty())
                usuario.setNomeFazenda(etNomeFazenda.getText().toString());
            if (!etCodigoProdutor.getText().toString().isEmpty())
                usuario.setCodigoProdutor(etCodigoProdutor.getText().toString());

            String user_id = mAuth.getCurrentUser().getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("users").child(user_id).setValue(usuario);
            return true;
        }
        return false;
    }

    public void layoutError(TextInputLayout inputLayout, Boolean enable, String msg){
        inputLayout.setErrorEnabled(enable);
        inputLayout.setError(msg);
    }

}
