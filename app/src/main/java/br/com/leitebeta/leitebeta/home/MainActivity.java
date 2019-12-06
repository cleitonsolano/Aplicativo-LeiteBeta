package br.com.leitebeta.leitebeta.home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import br.com.leitebeta.leitebeta.LoginActivity;
import br.com.leitebeta.leitebeta.R;
import br.com.leitebeta.leitebeta.model.Usuario;
import br.com.leitebeta.leitebeta.perfil.PerfilActivity;

public class MainActivity extends AppCompatActivity {
    
    private TableLayout tlGridTable;
    private int[] colunasProducoes = {R.id.tvMes, R.id.tvCodigoProdutor, R.id.tvVolume};
    private int[] colunasPrecos = {R.id.tvMes, R.id.tvCodigoProdutor, R.id.tvPreco, R.id.tvRendimento};
    private int[] colunasQualidades = {R.id.tvMes, R.id.tvCodigoProdutor, R.id.tvGordura, R.id.tvProteina, R.id.tvLactose, R.id.tvEST, R.id.tvESD, R.id.tvCCS, R.id.tvCBT, R.id.tvCFU, R.id.tvUreia, R.id.tvCrioscopia};

    ArrayList<ArrayList<String>> lolProducao = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> lolPrecos = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> lolqualidade = new ArrayList<ArrayList<String>>();

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Usuario usuario;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.preco:
                    preencherTabelaTitulo(R.layout.table_row_precos, colunasPrecos);
                    preencherTabela(R.layout.table_row_precos, colunasPrecos, lolPrecos);
                    return true;
                case R.id.producao:
                    preencherTabelaTitulo(R.layout.table_row_producaos, colunasProducoes);
                    preencherTabela(R.layout.table_row_producaos, colunasProducoes, lolProducao);
                    return true;
                case R.id.qualidade:
                    preencherTabelaTitulo(R.layout.table_row_qualidades, colunasQualidades);
                    preencherTabela(R.layout.table_row_qualidades, colunasQualidades, lolqualidade);
                    return true;
            }
            return false;
        }

    };

    public void preencherTabelaTitulo(int idLayout, int[] colunas) {
        tlGridTable.removeAllViews();

        TableRow row2 = (TableRow) getLayoutInflater().inflate(idLayout, tlGridTable, false);
        for (int i = 0; i < colunas.length ; i ++) {
            TextView tvProdutor = (TextView) row2.findViewById(colunas[i]);
            tvProdutor.setTypeface(null, Typeface.BOLD);
        }
        tlGridTable.addView(row2);
    }

    public void preencherTabela(int idLayout, int[] colunas, ArrayList<ArrayList<String>> lol) {
        for (int j = 0; j < lol.size(); j++) {
            TableRow row2 = (TableRow) getLayoutInflater().inflate(idLayout, tlGridTable, false);

            for (int i = 0; i < colunas.length ; i ++) {
                TextView tvProdutor = (TextView) row2.findViewById(colunas[i]);
                if (lol.get(j).get(i) != null)
                    tvProdutor.setText(lol.get(j).get(i));
            }
            tlGridTable.addView(row2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLists();

        tlGridTable = (TableLayout) findViewById(R.id.tlGridTable);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        preencherTabelaTitulo(R.layout.table_row_precos, colunasPrecos);
        preencherTabela(R.layout.table_row_precos, colunasPrecos, lolPrecos);

        mAuth = FirebaseAuth.getInstance();

        String user_id = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    usuario = dataSnapshot.getValue(Usuario.class);
//                    preencherCampos(usuario);
                    //Faça o que quiser com o objeto.
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", databaseError.getMessage());
            }
        });
    }

    private void initLists() {
        Random gerador = new Random();
        int produtorid = gerador.nextInt(3)+1;

        String[] meses = {String.valueOf(gerador.nextInt(3)+1),String.valueOf(gerador.nextInt(3)+4),String.valueOf(gerador.nextInt(3)+7),String.valueOf(gerador.nextInt(3)+10)};

        readData(produtorid, meses);
        readData2(produtorid, meses);
        readData3(produtorid, meses);
    }

    private void readData(int number, String[] meses) {

        InputStream is = getResources().openRawResource(R.raw.tabelas);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            int i = 0, j = 0;
            String[] tokens;
            ArrayList<String> list = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                //set splitter
                if (i!=0) {
                    tokens = line.split(",");
                    if (tokens[0].equals(String.valueOf(number))){
                        list = new ArrayList<>();
                        list.add(meses[j]);
                        list.add(tokens[0]);
                        list.add(tokens[12]);
                        list.add(tokens[13]);
                        lolPrecos.add(list);
                        j++;
                    }
                }

                Log.d("MainActivity" ,"Just Created " +"tabelas");
                i++;
            }
        } catch (IOException e1) {
            Log.e("MainActivity", "Error" + line, e1);
            e1.printStackTrace();
        }

    }

    private void readData2(int number, String[] meses) {


        InputStream is = getResources().openRawResource(R.raw.tabelas);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            int i = 0, j = 0;
            String[] tokens;
            ArrayList<String> list = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                //set splitter
                tokens = line.split(",");
                if (tokens[0].equals(String.valueOf(number))){
                    list = new ArrayList<>();
                    list.add(meses[j]);
                    list.add(tokens[0]);
                    list.add(tokens[1]);
                    lolProducao.add(list);
                    j++;
                }

                Log.d("MainActivity" ,"Just Created " +"tabelas");
                i++;
            }
        } catch (IOException e1) {
            Log.e("MainActivity", "Error" + line, e1);
            e1.printStackTrace();
        }

    }

    private void readData3(int number, String[] meses) {

        InputStream is = getResources().openRawResource(R.raw.tabelas);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            int i = 0, j = 0;
            String[] tokens;
            ArrayList<String> list = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                //set splitter
                    tokens = line.split(",");
                if (tokens[0].equals(String.valueOf(number))){
                    list = new ArrayList<>();
                    list.add(meses[j]);
                    list.add(tokens[0]);
//                    list.add(tokens[1]);
                    list.add(tokens[2]);
                    list.add(tokens[3]);
                    list.add(tokens[4]);
                    list.add(tokens[5]);
                    list.add(tokens[6]);
                    list.add(tokens[7]);
                    list.add(tokens[8]);
                    list.add(tokens[9]);
                    list.add(tokens[10]);
                    list.add(tokens[11]);
                    list.add(tokens[12]);
                    list.add(tokens[13]);

                    lolqualidade.add(list);
                    j++;
                }

                Log.d("MainActivity" ,"Just Created " +"tabelas");
                i++;
            }
        } catch (IOException e1) {
            Log.e("MainActivity", "Error" + line, e1);
            e1.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.perfil:
                Intent intent = new Intent(this, PerfilActivity.class);
                intent.putExtra("usuario",usuario);
                startActivity(intent);
                break;
            case R.id.sair :
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        return true;
    }
}
