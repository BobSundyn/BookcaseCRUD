package br.com.bobsundyn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.bobsundyn.model.Livro;

public class MainActivity extends AppCompatActivity {

    Button btnCadastrar;
    ListView listLivros;
    ArrayList<Livro> livros = new ArrayList<>();
    LivroAdapter adapterLivros;
    FirebaseDatabase database;
    DatabaseReference referenceLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Bem-vindo! A leitura é muito importante, compartilhe seus livros conosco.", Toast.LENGTH_LONG).show();

        btnCadastrar = findViewById(R.id.btnCadastrar);
        listLivros = findViewById(R.id.listLivros);

        adapterLivros = new LivroAdapter(MainActivity.this, livros);
        listLivros.setAdapter(adapterLivros);

        database = FirebaseDatabase.getInstance();
        referenceLivros = database.getReference("livros");
        referenceLivros.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                livros.clear();
                for (DataSnapshot dataLivro : snapshot.getChildren()){
                    Livro livro = dataLivro.getValue(Livro.class);
                    livro.setKey(dataLivro.getKey());
                    livros.add(livro);
                }
                adapterLivros.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Erro ao consultar os livros!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCadastrarLivro = new Intent(MainActivity.this, CadastrarLivroActivity.class);
                startActivity(intentCadastrarLivro);
            }
        });

        listLivros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Livro livroSelecionado = livros.get(i);
                AlertDialog alerta = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Excluir")
                        .setMessage("Deseja excluir o livro " + livroSelecionado.titulo + "?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                referenceLivros.child(livroSelecionado.getKey()).removeValue();
                                Toast.makeText(MainActivity.this, "Livro removido com sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "Operação cancelada.", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                alerta.show();
                return true;
            }
        });

        listLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Livro livroSelecionado = livros.get(i);
                Intent intentCadastrar = new Intent(MainActivity.this, CadastrarLivroActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", livroSelecionado.getKey());
                intentCadastrar.putExtras(bundle);
                startActivity(intentCadastrar);
            }
        });
    }
}