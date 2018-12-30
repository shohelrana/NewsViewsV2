package info.shohelranabd.newsviewsv2.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import info.shohelranabd.newsviewsv2.R;
import info.shohelranabd.newsviewsv2.interfaces.ItemClickListener;
import info.shohelranabd.newsviewsv2.model.News;

/**
 * Created by Md. Shohel Rana on 30 December,2018
 */
class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener itemClickListener;

    @BindView(R.id.article_iamge_thumb)
    ImageView article_iamge_thumb;

    @BindView(R.id.article_title)
    TextView article_title;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private Context context;
    private News news;

    public NewsAdapter(Context context, News news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.news_layout, parent, false);
        Log.d("TopNewsAdapter", "OnCreateView...");
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Picasso.get()
                .setIndicatorsEnabled(true);
        Picasso.get()
                .load(news.getArticles().get(position).getUrlToImage())
                .resize(90, 90)
                .centerCrop()
                .placeholder(R.drawable.logo_fallback)
                .into(holder.article_iamge_thumb);

        holder.article_title.setText(news.getArticles().get(position).getTitle());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Log.d("TopNewsAdapter", "Clicked Pos: " + position);

                TextView dialog_title, dialog_description;
                ImageView dialog_image;

                String url = news.getArticles().get(position).getUrl();

                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.article_dialog_layout, null);

                dialog_title = dialogView.findViewById(R.id.dialog_title);
                dialog_description = dialogView.findViewById(R.id.dialog_description);
                dialog_image = dialogView.findViewById(R.id.dialog_image);

                dialog_title.setText(news.getArticles().get(position).getTitle());
                Picasso.get()
                        .load(news.getArticles().get(position).getUrlToImage())
                        .placeholder(R.drawable.logo_fallback)
                        .into(dialog_image);

                dialog_description.setText(news.getArticles().get(position).getDescription());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialogView);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog newsInfoAlertD = builder.create();
                newsInfoAlertD.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.getArticles().size();
    }
}
