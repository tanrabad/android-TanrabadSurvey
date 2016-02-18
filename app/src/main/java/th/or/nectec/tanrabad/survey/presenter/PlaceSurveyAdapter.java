/*
 * Copyright (c) 2016 NECTEC
 *   National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package th.or.nectec.tanrabad.survey.presenter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import th.or.nectec.tanrabad.entity.Place;
import th.or.nectec.tanrabad.survey.R;

import java.util.ArrayList;
import java.util.List;

public class PlaceSurveyAdapter extends RecyclerView.Adapter<PlaceSurveyAdapter.ViewHolder>
        implements ListViewAdapter<Place> {

    Context context;
    ArrayList<Place> places = new ArrayList<>();
    private FragmentManager fragmentManager;
    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemLongClickListener onItemLongClickListener;

    public PlaceSurveyAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void updateData(List<Place> places) {
        this.places.clear();
        this.places.addAll(places);
        notifyDataSetChanged();
    }

    @Override
    public void clearData() {
        this.places.clear();
        notifyDataSetChanged();
    }

    @Override
    public Place getItem(int position) {
        return places.get(position);
    }

    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_place_survey, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Place place = places.get(position);
        holder.placeTextView.setText(place.getName());
        holder.placeIcon.setImageResource(PlaceIconMapping.getPlaceIcon(place));
        holder.viewSurveyResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SurveyResultDialogFragment.newInstances(place).show(
                        fragmentManager, SurveyResultDialogFragment.FRAGMENT_TAG);
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    private void onItemHolderClick(ViewHolder itemHolder) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, itemHolder.itemView,
                    itemHolder.getAdapterPosition(), itemHolder.getItemId());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView placeTextView;
        ImageView placeIcon;
        Button viewSurveyResultButton;
        private PlaceSurveyAdapter adapter;

        public ViewHolder(View itemView, final PlaceSurveyAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
            placeTextView = (TextView) itemView.findViewById(R.id.place_name);
            placeIcon = (ImageView) itemView.findViewById(R.id.place_icon);
            viewSurveyResultButton = (Button) itemView.findViewById(R.id.view_survey_result_button);
        }

        @Override
        public void onClick(View view) {
            adapter.onItemHolderClick(this);
        }
    }
}