package info.nukoneko.android.uidev.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import info.nukoneko.android.uidev.R;
import info.nukoneko.android.uidev.databinding.ActivityMainBinding;
import info.nukoneko.android.uidev.ui.porter_duff.NKUIPorterDuffActivity;

public final class MainActivity extends AppCompatActivity implements MainMenuViewAdapter.Listener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setAdapter(new MainMenuViewAdapter(this, new int[]{
            R.string.MainMenuItemPorterDuff
        }));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onClickItem(@StringRes int resourceId) {
        switch (resourceId) {
            case R.string.MainMenuItemPorterDuff:
                NKUIPorterDuffActivity.startActivity(this);
                break;
        }
    }
}
