package com.pranav.myapplication;

// What is Lorem Ipsum?
// Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.

// Why do we use it?
// It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    int limit =8;

    private List<ProgressionModel> dataList = new ArrayList<>();

    public void setDataList(int position, ProgressionModel data) {
        if (dataList.size() <= position) {
            dataList.add(data);

        } else {
            dataList.set(position, data);

        }

        notifyDataSetChanged();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            MainJavaActivity.results.setVisibility(View.GONE);
            MainJavaActivity.resultlayout.setVisibility(View.GONE);
            MainJavaActivity.retest.setVisibility(View.GONE);
            return 0;
        }
        else if(dataList.size()-1<limit) {
            MainJavaActivity.results.setVisibility(View.GONE);
            MainJavaActivity.resultlayout.setVisibility(View.GONE);
            MainJavaActivity.retest.setVisibility(View.GONE);
            return dataList.size();
        }

        MainJavaActivity.results.setVisibility(View.VISIBLE);
        MainJavaActivity.resultlayout.setVisibility(View.VISIBLE);
        MainJavaActivity.retest.setVisibility(View.VISIBLE);

        MainJavaActivity.dspeed.setText(avgd(dataList));
        MainJavaActivity.uspeed.setText(avgu(dataList));

        return limit;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatTextView tvProgress;
        private final AppCompatTextView tvDownload;
        private final AppCompatTextView tvUpload;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvProgress = itemView.findViewById(R.id.tvProgress);
            tvDownload = itemView.findViewById(R.id.tvDownload);
            tvUpload = itemView.findViewById(R.id.tvUpload);
        }

        void bind(ProgressionModel progressionModel) {
            tvProgress.setText("" + progressionModel.getProgressTotal());
            tvDownload.setText("" + progressionModel.getDownloadSpeed());
            tvUpload.setText("" + progressionModel.getUploadSpeed());
        }
    }

    String avgd(List<ProgressionModel> a){
        BigDecimal sum = BigDecimal.valueOf(0.0);

        for(int i=0; i<limit; i++){
            Log.e("Speed",a.get(i).toString());
            sum = sum.add((a.get(i).getDownloadSpeed()).divide(BigDecimal.valueOf(10000.0)));
        }
        return (sum.divide(BigDecimal.valueOf(limit))).setScale(2, RoundingMode.CEILING).toString()+" Mbps";
    }

    String avgu(List<ProgressionModel> a){
        BigDecimal sum = BigDecimal.valueOf(0.0);

        for(int i=0; i<limit; i++){
            Log.e("Speed",a.get(i).toString());
            sum = sum.add((a.get(i).getUploadSpeed()).divide(BigDecimal.valueOf(10000.0)));
        }
        return (sum.divide(BigDecimal.valueOf(limit))).setScale(2, RoundingMode.CEILING).toString()+" Mbps";
    }
 }
// What is Lorem Ipsum?
// Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.

// Why do we use it?
// It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).
