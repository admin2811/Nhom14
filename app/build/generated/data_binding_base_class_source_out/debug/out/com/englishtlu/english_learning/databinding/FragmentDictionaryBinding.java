// Generated by view binder compiler. Do not edit!
package com.englishtlu.english_learning.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.englishtlu.english_learning.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentDictionaryBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final ScrollView main;

  @NonNull
  public final ImageView micIcon;

  @NonNull
  public final RecyclerView recycleMeanings;

  @NonNull
  public final RecyclerView recyclePhonetics;

  @NonNull
  public final SearchView searchView;

  @NonNull
  public final TextView textViewWord;

  private FragmentDictionaryBinding(@NonNull ScrollView rootView, @NonNull ScrollView main,
      @NonNull ImageView micIcon, @NonNull RecyclerView recycleMeanings,
      @NonNull RecyclerView recyclePhonetics, @NonNull SearchView searchView,
      @NonNull TextView textViewWord) {
    this.rootView = rootView;
    this.main = main;
    this.micIcon = micIcon;
    this.recycleMeanings = recycleMeanings;
    this.recyclePhonetics = recyclePhonetics;
    this.searchView = searchView;
    this.textViewWord = textViewWord;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentDictionaryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentDictionaryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_dictionary, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentDictionaryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ScrollView main = (ScrollView) rootView;

      id = R.id.mic_icon;
      ImageView micIcon = ViewBindings.findChildViewById(rootView, id);
      if (micIcon == null) {
        break missingId;
      }

      id = R.id.recycle_meanings;
      RecyclerView recycleMeanings = ViewBindings.findChildViewById(rootView, id);
      if (recycleMeanings == null) {
        break missingId;
      }

      id = R.id.recycle_phonetics;
      RecyclerView recyclePhonetics = ViewBindings.findChildViewById(rootView, id);
      if (recyclePhonetics == null) {
        break missingId;
      }

      id = R.id.search_view;
      SearchView searchView = ViewBindings.findChildViewById(rootView, id);
      if (searchView == null) {
        break missingId;
      }

      id = R.id.textView_word;
      TextView textViewWord = ViewBindings.findChildViewById(rootView, id);
      if (textViewWord == null) {
        break missingId;
      }

      return new FragmentDictionaryBinding((ScrollView) rootView, main, micIcon, recycleMeanings,
          recyclePhonetics, searchView, textViewWord);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
