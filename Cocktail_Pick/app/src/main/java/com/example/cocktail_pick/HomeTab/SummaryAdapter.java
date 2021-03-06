package com.example.cocktail_pick.HomeTab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktail_pick.HomeTab.DetailRecipe.DetailRecipeActivity;
import com.example.cocktail_pick.R;
import com.example.cocktail_pick.Recipe;
import com.example.cocktail_pick.RecipeReceive;
import com.example.cocktail_pick.Tag;
import com.example.cocktail_pick.databinding.ActivityLoginBinding;
import com.example.cocktail_pick.databinding.ItemCustomImageBinding;
import com.example.cocktail_pick.databinding.ItemRecipeBinding;
import com.example.cocktail_pick.HomeTab.CustomHandler;

import java.util.ArrayList;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {

    Context context;
    ArrayList<RecipeReceive> recipes;
    ItemRecipeBinding binding;
    CustomHandler handler;

    ArrayList<Tag> tagList = new ArrayList<Tag>();


    public SummaryAdapter(Context context, ArrayList<RecipeReceive> recipes) {
        this.context = context;
        this.recipes = recipes;

        if(tagList.size()==0) {
            tagList.add(new Tag(5, "#941100","달달한"));
            tagList.add(new Tag(6, "#521B93","술맛이강한"));
            tagList.add(new Tag(7, "#d4fb79","상큼한"));
            tagList.add(new Tag(8, "#ffffff","깔끔한"));
            tagList.add(new Tag(9, "#008f00","가벼운"));
            tagList.add(new Tag(10, "#ff2600","탄산이있는"));
            tagList.add(new Tag(11, "#ff2600","도수가높은"));
            tagList.add(new Tag(12, "#005493","부드러운"));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        binding = ItemRecipeBinding.inflate(LayoutInflater.from(context), parent, false);
        SummaryAdapter.ViewHolder viewHolder = new SummaryAdapter.ViewHolder(binding);
        handler = new CustomHandler();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RecipeReceive recipe = recipes.get(position*2);
        holder.summary_recipe_name1.setText(recipe.getCocktailName());

        handler.setGlass(recipe.getGlass(), recipe.getIce(), recipe.getGarnishFirst(), recipe.getGarnishSecond(), recipe.getCocktailColor(), holder.firstImage);

//        holder.summary_image1.setImageResource(R.drawable.jack_danial); // TODO
        holder.summary_review1.setText(recipe.getIntro());
        holder.summary_tag_recycler_view1.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        ArrayList<Tag> tmp_tags = new ArrayList<>();
        for( int tag : recipe.getTags()) {
            tmp_tags.add(tagList.get(tag-5));
        }
        holder.summary_tag_recycler_view1.setAdapter(new RecipeTagAdapter(context, tmp_tags));

        // TODO Tag 받야와야 함
        holder.item_recipe1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailRecipeActivity.class);
                intent.putExtra("recipe", recipe);
                context.startActivity(intent);
            }
        });

        if (recipes.size()-1 == position*2) {
            holder.item_recipe2.setVisibility(View.INVISIBLE);
            return;
        }

        final RecipeReceive recipe2 = recipes.get(position*2+1);
        handler.setGlass(recipe2.getGlass(), recipe2.getIce(), recipe2.getGarnishFirst(), recipe2.getGarnishSecond(), recipe2.getCocktailColor(), holder.secondImage);

        holder.summary_recipe_name2.setText(recipe2.getCocktailName());
//        holder.summary_image2.setImageResource(R.drawable.jack_danial); // TODO
        holder.summary_review2.setText(recipe2.getIntro());

        holder.summary_tag_recycler_view2.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));

        ArrayList<Tag> tmp_tags2 = new ArrayList<>();
        for( int tag : recipe2.getTags()) {
            tmp_tags2.add(tagList.get(tag-5));
        }


        holder.summary_tag_recycler_view2.setAdapter(new RecipeTagAdapter(context, tmp_tags2));

        holder.item_recipe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailRecipeActivity.class);
                intent.putExtra("recipe", recipe2);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (recipes.size()+1)/2;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView summary_recipe_name1, summary_recipe_name2, summary_review1, summary_review2;
//        ConstraintLayout summary_image1, summary_image2;
        RecyclerView summary_tag_recycler_view1, summary_tag_recycler_view2;
        LinearLayout item_recipe1, item_recipe2;
        ItemCustomImageBinding firstImage;
        ItemCustomImageBinding secondImage;



        public ViewHolder(ItemRecipeBinding binding) {
            super(binding.getRoot());
            summary_recipe_name1 = binding.summaryRecipeName;
//            summary_image1 = itemView.findViewById(R.id.firstCustom);
            summary_review1 = binding.summaryReviewTextView;
            summary_tag_recycler_view1 = binding.summaryTagRecyclerView;
            item_recipe1 = binding.itemRecipe1;
            firstImage = binding.firstCustom;

            summary_recipe_name2 = binding.summaryRecipeName2;
//            summary_image2 = itemView.findViewById(R.id.secondCustom);
            summary_review2 = binding.summaryReviewTextView2;
            summary_tag_recycler_view2 = binding.summaryTagRecyclerView2;
            item_recipe2 = binding.itemRecipe2;
            secondImage = binding.secondCustom;
        }
    }
}
