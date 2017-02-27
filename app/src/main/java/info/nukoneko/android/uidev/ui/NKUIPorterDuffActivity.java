package info.nukoneko.android.uidev.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import info.nukoneko.android.uidev.R;
import info.nukoneko.android.uidev.databinding.ItemActivityPorterDuffBinding;
import info.nukoneko.android.uidev.ui.common.NKPorterDuffView;

public final class NKUIPorterDuffActivity extends AppCompatActivity implements NKPorterDuffView.Listener {

    public static void startActivity(@NonNull Context context) {
        context.startActivity(new Intent(context, NKUIPorterDuffActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final RecyclerView recyclerView = new RecyclerView(this);
        setContentView(recyclerView);
        recyclerView.setBackgroundColor(Color.GRAY);
        recyclerView.setAdapter(new ViewAdapter(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    public int getColorBackGround() {
        return Color.GRAY;
    }

    @Override
    public int getColorCrossLine() {
        return Color.RED;
    }

    @Override
    public int getColorCenter() {
        return Color.GREEN;
    }

    @Override
    public int getColorCenterAnimation(float animationValue) {
        return Color.argb((int) (255 * animationValue), 0, 0, 255);
    }

    @SuppressWarnings("WeakerAccess")
    private static class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {
        private final ArrayList<PorterDuff.Mode> data;
        private final Context context;

        public ViewAdapter(@NonNull Context context) {
            this.context = context;
            this.data = new ArrayList<PorterDuff.Mode>(){{
                for (PorterDuff.Mode mode : PorterDuff.Mode.values()) {
                    add(mode);
                }
            }};
        }

        @Override
        public ViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_activity_porter_duff, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewAdapter.ViewHolder holder, int position) {
            final PorterDuff.Mode mode = data.get(position);
            holder.getBinding().text.setText(mode.name());
            holder.getBinding().porterDuffView.setPorterDuff(mode);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private final ItemActivityPorterDuffBinding binding;
            public ViewHolder(View itemView) {
                super(itemView);
                binding = DataBindingUtil.bind(itemView);
            }

            public ItemActivityPorterDuffBinding getBinding() {
                return binding;
            }
        }
    }
}
