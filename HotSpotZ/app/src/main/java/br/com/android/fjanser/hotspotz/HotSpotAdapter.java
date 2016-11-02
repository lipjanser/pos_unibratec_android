package br.com.android.fjanser.hotspotz;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.android.fjanser.hotspotz.model.HotSpot;
import br.com.android.fjanser.hotspotz.R;

public class HotSpotAdapter extends RecyclerView.Adapter<HotSpotAdapter.VH> {

    private List<HotSpot> mHotSpots;
    private Context mContext;
    private OnHotSpotClickListener mHotSpotClickListener;

    public HotSpotAdapter(Context ctx, List<HotSpot> mMovies) {
        this.mContext = ctx;
        this.mHotSpots = mMovies;
    }

    public void setHotSpotClickListener(OnHotSpotClickListener mcl) {
        this.mHotSpotClickListener = mcl;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.item_hotspot, parent, false);
        final VH viewHolder = new VH(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewHolder.getAdapterPosition();
                if (mHotSpotClickListener != null){
                    mHotSpotClickListener.onHotSpotClick(view, mHotSpots.get(pos), pos);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        HotSpot hotSpot = mHotSpots.get(position);
        //Glide.with(mContext).load(movie.getPoster()).placeholder(R.drawable.ic_placeholder).into(holder.imageViewPoster);
        holder.textViewTitle.setText(hotSpot.getEndereco());
        holder.textViewYear.setText(hotSpot.getPais());
    }

    @Override
    public int getItemCount() {
        return mHotSpots.size();
    }

    class VH extends RecyclerView.ViewHolder {
        //ImageView imageViewPoster;
        TextView textViewTitle;
        TextView textViewYear;

        public VH(View itemView) {
            super(itemView);
            //imageViewPoster = (ImageView)itemView.findViewById(R.id.movie_item_image_poster);
            textViewTitle = (TextView) itemView.findViewById(R.id.hotspot_item_text_title);
            textViewYear = (TextView)itemView.findViewById(R.id.hotspot_item_text);

            //ViewCompat.setTransitionName(imageViewPoster, "capa");
            ViewCompat.setTransitionName(textViewTitle, "endereco");
            ViewCompat.setTransitionName(textViewYear, "pais");
        }
    }
}
