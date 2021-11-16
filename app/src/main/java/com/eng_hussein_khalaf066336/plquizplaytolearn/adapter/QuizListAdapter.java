package com.eng_hussein_khalaf066336.plquizplaytolearn.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eng_hussein_khalaf066336.plquizplaytolearn.R;
import com.eng_hussein_khalaf066336.plquizplaytolearn.databinding.EatchQuizBinding;
import com.eng_hussein_khalaf066336.plquizplaytolearn.model.QuizListModel;

import java.util.List;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.QuizListViewHolder> {

    private List<QuizListModel> quizListModels;
    private OnItemClickedListener onItemClickedListener;
    private LayoutInflater layoutInflater;


    public QuizListAdapter(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public void setQuizListModels(List<QuizListModel> quizListModels) {
        this.quizListModels = quizListModels;
    }

    @Override
    public QuizListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        EatchQuizBinding eatchQuizBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.eatch_quiz,parent,false);
        return new QuizListViewHolder(eatchQuizBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizListViewHolder holder, int position) {
        QuizListModel model = quizListModels.get(position);
        holder.bindTvShow(model);

//        holder.title.setText(model.getTitle());
//        Glide.with(holder.itemView).load(model.getImage()).into(holder.quizImage);
    }

    @Override
    public int getItemCount() {
        if (quizListModels == null){
            return 0;
        }else{
            return quizListModels.size();
        }
    }

    public class QuizListViewHolder extends RecyclerView.ViewHolder {
        private EatchQuizBinding eatchQuizBinding;
//        private TextView title ;
//        private ImageView quizImage;
        public QuizListViewHolder( EatchQuizBinding eatchQuizBinding) {
            super(eatchQuizBinding.getRoot());
            this.eatchQuizBinding=eatchQuizBinding;
//            title = itemView.findViewById(R.id.quizTitleList);
//            quizImage = itemView.findViewById(R.id.quizImageList);
//            itemView.setOnClickListener(v -> {
//                onItemClickedListener.onItemClick(getAdapterPosition());
//            });
        }
        public  void bindTvShow(QuizListModel quizListModel)
        {
            eatchQuizBinding.setQuiz(quizListModel);
            eatchQuizBinding.executePendingBindings();
            eatchQuizBinding.getRoot().setOnClickListener(v -> {
                onItemClickedListener.onItemClick(getAdapterPosition());
            });
        }
    }
    public interface OnItemClickedListener {
        void onItemClick(int position);
    }
}
