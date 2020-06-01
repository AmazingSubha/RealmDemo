package glistersoft.com.realmdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import glistersoft.com.realmdemo.R;
import glistersoft.com.realmdemo.TaskDB;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class RecyclerViewAdapter extends RealmRecyclerViewAdapter<TaskDB, RecyclerViewAdapter.RecyclerViewHolder> {

    public RecyclerViewAdapter(@Nullable OrderedRealmCollection<TaskDB> data) {
        super(data, true);
        setHasStableIds(true);//This is used for when your Realm database has a primary key
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        final TaskDB taskDB = getItem(position);
        holder.textView.setText(taskDB.getTask());
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
