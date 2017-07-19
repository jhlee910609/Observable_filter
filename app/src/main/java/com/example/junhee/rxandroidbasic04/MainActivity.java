package com.example.junhee.rxandroidbasic04;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<String> data = new ArrayList<>();
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        adapter = new RecyclerAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emitData();
    }

    Observable<Integer> observable;
    Observable<String> observableZip;
    String months[] = {};

    public void emitData() {
        // 1월~ 12월까지의 String data를 DateFormatSymbols을 통해 얻는다.
        DateFormatSymbols dfs = new DateFormatSymbols();
        months = dfs.getMonths();

        /* create 메소드를 통해 observable 객체 만들기 */
        // 초당 1개의 데이터 발행한다.
        observable = Observable.create(emitter -> {
            for (int i = 0; i < 12; i++) {
                emitter.onNext(i);
                Thread.sleep(1000);
            }
            emitter.onComplete();
        });

        /* zip 메소드를 통해 observable 객체 만들기 */
        observableZip = Observable.zip(
                Observable.just("Junhee Lee", "Developer"),
                Observable.just("Heejin Song", "Marketer"),
                /*  zip은 다른 메소드와 달리 observable 객체 간의 조합이 가능하다.  */
                (item1, item2) -> "name : " + item1 + ", job : " + item2
        );
    }

    public void doMap(View view) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(item -> item.equals("May") ? false : true)
                .map(item -> ">> " + item + " <<")
                .subscribe(
                        item -> {
                            data.add(item);
                            /* 새로 업데이트된 항목만 갱신할 수 있도록 .notifyDataSetChanged(); 대신 아래 메소드를 활용했다. */
                            adapter.notifyItemInserted(data.size() - 1);
                        },
                        error -> Log.e("Error : ", error.getMessage()),
                        () -> Log.i("Complete", "Successfully completed!")

                );
    }

    public void doFlatmap(View view) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(item -> Observable.fromArray(new String[]{"name : " + months[item], "code : " + item}))
                .subscribe(
                        item -> {
                            data.add(item);
                            adapter.notifyItemInserted(data.size() - 1);
                        },
                        error -> Log.e("Error", error.getMessage()),
                        () -> Log.i("Complete", "Successfully complete")
                );
    }


    public void doZip(View view) {
        observableZip
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            data.add(item + "");
                            adapter.notifyItemInserted(data.size() - 1);
                        },
                        error -> Log.e("Error : ", error.getMessage()),
                        () -> Log.i("Complete", "Successfully completed!")


                );
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }
}

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    LayoutInflater inflater = null;
    List<String> data = null;

    public RecyclerAdapter(Context context, List<String> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Log.i("Refresh", " =========================== position : " + position);
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
