package info.nukoneko.android.uidev.ui.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.nukoneko.android.uidev.R;
import info.nukoneko.android.uidev.databinding.ItemActivityMainBinding;

@SuppressWarnings("WeakerAccess")
public final class MainMenuViewAdapter extends RecyclerView.Adapter<MainMenuViewAdapter.ViewHolder> {
    public interface Listener {
        void onClickItem(@StringRes int resourceId);
    }

    final private @StringRes int[] data;
    final private Context context;
    public MainMenuViewAdapter(@NonNull final Context context, @StringRes int[] textResourceIds) {
        this.context = context;
        this.data = textResourceIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_activity_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int textResourceId = data[position];
        holder.getBinding().text.setText(context.getString(textResourceId));
        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof Listener) ((Listener) context).onClickItem(textResourceId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    @SuppressWarnings("WeakerAccess")
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemActivityMainBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ItemActivityMainBinding getBinding() {
            return binding;
        }
    }
}
