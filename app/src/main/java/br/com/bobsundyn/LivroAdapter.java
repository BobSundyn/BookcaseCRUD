package br.com.bobsundyn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.bobsundyn.model.Livro;


public class LivroAdapter extends BaseAdapter {

    Context context;
    ArrayList<Livro> livros;

    public LivroAdapter(Context context, ArrayList<Livro> livros) {
        this.context = context;
        this.livros = livros;
    }

    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int i) {
        return livros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.adapter_livro, viewGroup, false);
        }

        Livro livro = livros.get(i);

        TextView textTitulo = view.findViewById(R.id.textTitulo);
        TextView textAutor = view.findViewById(R.id.textAutor);
        TextView textEditora = view.findViewById(R.id.textEditora);
        ImageView imageLivro = view.findViewById(R.id.imageLivro);

        textTitulo.setText(livro.titulo);
        textAutor.setText(livro.autor);
        textEditora.setText(livro.editora);
        Picasso.get().load(livro.imagem).into(imageLivro);

        return view;
    }
}

