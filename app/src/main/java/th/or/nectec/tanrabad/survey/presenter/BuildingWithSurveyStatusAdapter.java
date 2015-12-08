/*
 * Copyright (c) 2015 NECTEC
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
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import th.or.nectec.tanrabad.domain.building.BuildingWithSurveyStatus;
import th.or.nectec.tanrabad.survey.R;

public class BuildingWithSurveyStatusAdapter extends RecyclerView.Adapter<BuildingWithSurveyStatusAdapter.ViewHolder> implements ListViewAdapter<BuildingWithSurveyStatus> {

    Context context;
    int buildingIcon;

    ArrayList<BuildingWithSurveyStatus> buildings = new ArrayList<>();
    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemLongClickListener onItemLongClickListener;

    public BuildingWithSurveyStatusAdapter(Context context, @DrawableRes int buildingIcon) {
        this.context = context;
        this.buildingIcon = buildingIcon;
    }

    @Override
    public void updateData(List<BuildingWithSurveyStatus> buildings) {
        this.buildings.clear();
        this.buildings.addAll(buildings);
        notifyDataSetChanged();
    }

    @Override
    public void clearData() {
        this.buildings.clear();
        notifyDataSetChanged();
    }

    @Override
    public BuildingWithSurveyStatus getItem(int i) {
        return buildings.get(i);
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
    public BuildingWithSurveyStatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_building, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BuildingWithSurveyStatusAdapter.ViewHolder holder, int position) {
        BuildingWithSurveyStatus buildingWithSurveyStatus = buildings.get(position);
        holder.buildingTextView.setText(buildingWithSurveyStatus.getBuilding().getName());
        holder.buildingIcon.setImageResource(buildingIcon);
        if (buildingWithSurveyStatus.isSurvey()) {
            holder.rootView.setEnabled(false);
            holder.surveyed.setVisibility(View.VISIBLE);
            //BackgroundSetter.set(holder.buildingIcon, R.drawable.bg_icon_highlight);
        } else {
            holder.rootView.setEnabled(true);
            holder.surveyed.setVisibility(View.INVISIBLE);
            // BackgroundSetter.set(holder.buildingIcon, R.drawable.bg_icon);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return buildings.size();
    }

    private void onItemHolderClick(ViewHolder itemHolder) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, itemHolder.itemView,
                    itemHolder.getAdapterPosition(), itemHolder.getItemId());
        }
    }

    private void onItemHolderLongClick(ViewHolder itemHolder) {
        if (onItemLongClickListener != null) {
            onItemLongClickListener.onItemLongClick(null, itemHolder.itemView,
                    itemHolder.getAdapterPosition(), itemHolder.getItemId());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView buildingTextView;
        ImageView buildingIcon;
        View surveyed;
        View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            buildingTextView = (TextView) itemView.findViewById(R.id.building_name);
            buildingIcon = (ImageView) itemView.findViewById(R.id.building_icon);
            surveyed = itemView.findViewById(R.id.surveyed);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemHolderClick(this);
        }

        @Override
        public boolean onLongClick(View view) {
            onItemHolderLongClick(this);
            return true;
        }
    }
}