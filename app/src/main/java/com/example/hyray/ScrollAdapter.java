package com.example.hyray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ScrollAdapter extends FragmentStatePagerAdapter {


    public ScrollAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new AllFragment(); //AllFragment at position 0
            case 1:
                return new ActiveFragment(); //AllFragment at position 1
            case 2:
                return new InactiveFragment(); //AllFragment at position 2
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
