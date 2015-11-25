package com.grafixartist.marshmallowsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grafixartist.marshmallowsample.util.CustomRecyclerClickListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.main_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        titles = Arrays.asList(getResources().getStringArray(R.array.titles));

        recyclerView.setAdapter(new MainAdapter());


        recyclerView.addOnItemTouchListener(new CustomRecyclerClickListener(this, new CustomRecyclerClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        Intent i;

                        switch (position) {

                            case 0:
                                i = new Intent(MainActivity.this, CustomTabsActivity.class);
                                startActivity(i);
                                break;
                            case 1:
                                i = new Intent(MainActivity.this, PermissionActivity.class);
                                startActivity(i);
                                break;

                        }

                    }
                })
        );


    }


    class MainAdapter extends RecyclerView.Adapter<MainAdapter.TitleHolder> {


        @Override
        public TitleHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.main_list_item, parent, false);

            return new TitleHolder(v);
        }

        @Override
        public void onBindViewHolder(TitleHolder holder, int position) {

            holder.titleView.setText(titles.get(position));

        }

        @Override
        public int getItemCount() {
            return titles.size();
        }


        class TitleHolder extends RecyclerView.ViewHolder {
            TextView titleView;

            public TitleHolder(View itemView) {
                super(itemView);

                titleView = (TextView) itemView.findViewById(R.id.list_title);
            }
        }
    }
}
