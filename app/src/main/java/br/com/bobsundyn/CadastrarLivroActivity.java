package br.com.bobsundyn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.bobsundyn.model.Livro;

public class CadastrarLivroActivity extends AppCompatActivity {

    EditText textTitulo, textAutor, textEditora, textImagem;
    Button btnCadastrar;
    FirebaseDatabase database;
    DatabaseReference referenceLivros;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_livro);

        textTitulo = findViewById(R.id.textTitulo);
        textAutor = findViewById(R.id.textAutor);
        textEditora = findViewById(R.id.textEditora);
        textImagem = findViewById(R.id.textImagem);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        database = FirebaseDatabase.getInstance();
        referenceLivros = database.getReference("livros");

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            key = "";
            getSupportActionBar().setTitle("Cadastrar Livro");
        } else {
            key = bundle.getString("key");
            getSupportActionBar().setTitle("Alterar Livro");
            btnCadastrar.setText("Alterar");
            referenceLivros.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Livro livro = snapshot.getValue(Livro.class);
                    textTitulo.setText(livro.titulo);
                    textAutor.setText(livro.autor);
                    textEditora.setText(livro.editora);
                    textImagem.setText(livro.imagem);
                    getSupportActionBar().setSubtitle(livro.titulo);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CadastrarLivroActivity.this, "Erro ao consultar os dados do livro.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tituloDigitado = textTitulo.getText().toString();
                String autorDigitado = textAutor.getText().toString();
                String editoraDigitada = textEditora.getText().toString();
                String imagemDigitada = textImagem.getText().toString();
                Livro livro = new Livro(tituloDigitado, autorDigitado, editoraDigitada, imagemDigitada);
                if (key.equals("")) {
                    referenceLivros.push().setValue(livro);
                    Toast.makeText(CadastrarLivroActivity.this, "Livro cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    referenceLivros.child(key).setValue(livro);
                    Toast.makeText(CadastrarLivroActivity.this, "Livro alterado com sucesso!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}