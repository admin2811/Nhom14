// Generated by view binder compiler. Do not edit!
package com.englishtlu.english_learning.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.englishtlu.english_learning.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentProfileBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView DocumentImage;

  @NonNull
  public final ConstraintLayout FAQ;

  @NonNull
  public final ImageView arrowHelp;

  @NonNull
  public final ImageView arrowProfile;

  @NonNull
  public final ImageView changePassword;

  @NonNull
  public final ImageView changePasswordImage;

  @NonNull
  public final TextView changePasswordTxt;

  @NonNull
  public final ConstraintLayout changePasswordandEmail;

  @NonNull
  public final ConstraintLayout dictionary;

  @NonNull
  public final ImageView dictionaryImage;

  @NonNull
  public final ImageView dictionaryIn;

  @NonNull
  public final TextView dictionaryTxt;

  @NonNull
  public final ConstraintLayout document;

  @NonNull
  public final ImageView documentIn;

  @NonNull
  public final TextView documentTxt;

  @NonNull
  public final TextView emailTxt;

  @NonNull
  public final ConstraintLayout flashCard;

  @NonNull
  public final ImageView flashCardImage;

  @NonNull
  public final ImageView flashCardIn;

  @NonNull
  public final TextView flashCardTxt;

  @NonNull
  public final TextView fullNameTxt;

  @NonNull
  public final ImageView help;

  @NonNull
  public final TextView helpTxt;

  @NonNull
  public final ImageView imageView4;

  @NonNull
  public final ImageView logout;

  @NonNull
  public final ImageView logoutImage;

  @NonNull
  public final TextView logoutTxt;

  @NonNull
  public final ConstraintLayout myProfile;

  @NonNull
  public final ImageView profile;

  @NonNull
  public final CircleImageView profileImage;

  @NonNull
  public final TextView profileTxt;

  @NonNull
  public final ConstraintLayout progress;

  @NonNull
  public final ProgressBar progressBar5;

  @NonNull
  public final ImageView progressImage;

  @NonNull
  public final ImageView progressIn;

  @NonNull
  public final TextView progressTxt;

  @NonNull
  public final ScrollView scrollView4;

  @NonNull
  public final ImageView send;

  @NonNull
  public final ImageView sendImage;

  @NonNull
  public final TextView sendTxt;

  @NonNull
  public final ConstraintLayout share;

  @NonNull
  public final ConstraintLayout signOut;

  @NonNull
  public final TextView textView8;

  private FragmentProfileBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView DocumentImage, @NonNull ConstraintLayout FAQ, @NonNull ImageView arrowHelp,
      @NonNull ImageView arrowProfile, @NonNull ImageView changePassword,
      @NonNull ImageView changePasswordImage, @NonNull TextView changePasswordTxt,
      @NonNull ConstraintLayout changePasswordandEmail, @NonNull ConstraintLayout dictionary,
      @NonNull ImageView dictionaryImage, @NonNull ImageView dictionaryIn,
      @NonNull TextView dictionaryTxt, @NonNull ConstraintLayout document,
      @NonNull ImageView documentIn, @NonNull TextView documentTxt, @NonNull TextView emailTxt,
      @NonNull ConstraintLayout flashCard, @NonNull ImageView flashCardImage,
      @NonNull ImageView flashCardIn, @NonNull TextView flashCardTxt, @NonNull TextView fullNameTxt,
      @NonNull ImageView help, @NonNull TextView helpTxt, @NonNull ImageView imageView4,
      @NonNull ImageView logout, @NonNull ImageView logoutImage, @NonNull TextView logoutTxt,
      @NonNull ConstraintLayout myProfile, @NonNull ImageView profile,
      @NonNull CircleImageView profileImage, @NonNull TextView profileTxt,
      @NonNull ConstraintLayout progress, @NonNull ProgressBar progressBar5,
      @NonNull ImageView progressImage, @NonNull ImageView progressIn,
      @NonNull TextView progressTxt, @NonNull ScrollView scrollView4, @NonNull ImageView send,
      @NonNull ImageView sendImage, @NonNull TextView sendTxt, @NonNull ConstraintLayout share,
      @NonNull ConstraintLayout signOut, @NonNull TextView textView8) {
    this.rootView = rootView;
    this.DocumentImage = DocumentImage;
    this.FAQ = FAQ;
    this.arrowHelp = arrowHelp;
    this.arrowProfile = arrowProfile;
    this.changePassword = changePassword;
    this.changePasswordImage = changePasswordImage;
    this.changePasswordTxt = changePasswordTxt;
    this.changePasswordandEmail = changePasswordandEmail;
    this.dictionary = dictionary;
    this.dictionaryImage = dictionaryImage;
    this.dictionaryIn = dictionaryIn;
    this.dictionaryTxt = dictionaryTxt;
    this.document = document;
    this.documentIn = documentIn;
    this.documentTxt = documentTxt;
    this.emailTxt = emailTxt;
    this.flashCard = flashCard;
    this.flashCardImage = flashCardImage;
    this.flashCardIn = flashCardIn;
    this.flashCardTxt = flashCardTxt;
    this.fullNameTxt = fullNameTxt;
    this.help = help;
    this.helpTxt = helpTxt;
    this.imageView4 = imageView4;
    this.logout = logout;
    this.logoutImage = logoutImage;
    this.logoutTxt = logoutTxt;
    this.myProfile = myProfile;
    this.profile = profile;
    this.profileImage = profileImage;
    this.profileTxt = profileTxt;
    this.progress = progress;
    this.progressBar5 = progressBar5;
    this.progressImage = progressImage;
    this.progressIn = progressIn;
    this.progressTxt = progressTxt;
    this.scrollView4 = scrollView4;
    this.send = send;
    this.sendImage = sendImage;
    this.sendTxt = sendTxt;
    this.share = share;
    this.signOut = signOut;
    this.textView8 = textView8;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.DocumentImage;
      ImageView DocumentImage = ViewBindings.findChildViewById(rootView, id);
      if (DocumentImage == null) {
        break missingId;
      }

      id = R.id.FAQ;
      ConstraintLayout FAQ = ViewBindings.findChildViewById(rootView, id);
      if (FAQ == null) {
        break missingId;
      }

      id = R.id.arrowHelp;
      ImageView arrowHelp = ViewBindings.findChildViewById(rootView, id);
      if (arrowHelp == null) {
        break missingId;
      }

      id = R.id.arrowProfile;
      ImageView arrowProfile = ViewBindings.findChildViewById(rootView, id);
      if (arrowProfile == null) {
        break missingId;
      }

      id = R.id.changePassword;
      ImageView changePassword = ViewBindings.findChildViewById(rootView, id);
      if (changePassword == null) {
        break missingId;
      }

      id = R.id.changePasswordImage;
      ImageView changePasswordImage = ViewBindings.findChildViewById(rootView, id);
      if (changePasswordImage == null) {
        break missingId;
      }

      id = R.id.changePasswordTxt;
      TextView changePasswordTxt = ViewBindings.findChildViewById(rootView, id);
      if (changePasswordTxt == null) {
        break missingId;
      }

      id = R.id.changePasswordandEmail;
      ConstraintLayout changePasswordandEmail = ViewBindings.findChildViewById(rootView, id);
      if (changePasswordandEmail == null) {
        break missingId;
      }

      id = R.id.dictionary;
      ConstraintLayout dictionary = ViewBindings.findChildViewById(rootView, id);
      if (dictionary == null) {
        break missingId;
      }

      id = R.id.dictionaryImage;
      ImageView dictionaryImage = ViewBindings.findChildViewById(rootView, id);
      if (dictionaryImage == null) {
        break missingId;
      }

      id = R.id.dictionaryIn;
      ImageView dictionaryIn = ViewBindings.findChildViewById(rootView, id);
      if (dictionaryIn == null) {
        break missingId;
      }

      id = R.id.dictionaryTxt;
      TextView dictionaryTxt = ViewBindings.findChildViewById(rootView, id);
      if (dictionaryTxt == null) {
        break missingId;
      }

      id = R.id.document;
      ConstraintLayout document = ViewBindings.findChildViewById(rootView, id);
      if (document == null) {
        break missingId;
      }

      id = R.id.documentIn;
      ImageView documentIn = ViewBindings.findChildViewById(rootView, id);
      if (documentIn == null) {
        break missingId;
      }

      id = R.id.documentTxt;
      TextView documentTxt = ViewBindings.findChildViewById(rootView, id);
      if (documentTxt == null) {
        break missingId;
      }

      id = R.id.emailTxt;
      TextView emailTxt = ViewBindings.findChildViewById(rootView, id);
      if (emailTxt == null) {
        break missingId;
      }

      id = R.id.flashCard;
      ConstraintLayout flashCard = ViewBindings.findChildViewById(rootView, id);
      if (flashCard == null) {
        break missingId;
      }

      id = R.id.flashCardImage;
      ImageView flashCardImage = ViewBindings.findChildViewById(rootView, id);
      if (flashCardImage == null) {
        break missingId;
      }

      id = R.id.flashCardIn;
      ImageView flashCardIn = ViewBindings.findChildViewById(rootView, id);
      if (flashCardIn == null) {
        break missingId;
      }

      id = R.id.flashCardTxt;
      TextView flashCardTxt = ViewBindings.findChildViewById(rootView, id);
      if (flashCardTxt == null) {
        break missingId;
      }

      id = R.id.fullNameTxt;
      TextView fullNameTxt = ViewBindings.findChildViewById(rootView, id);
      if (fullNameTxt == null) {
        break missingId;
      }

      id = R.id.help;
      ImageView help = ViewBindings.findChildViewById(rootView, id);
      if (help == null) {
        break missingId;
      }

      id = R.id.helpTxt;
      TextView helpTxt = ViewBindings.findChildViewById(rootView, id);
      if (helpTxt == null) {
        break missingId;
      }

      id = R.id.imageView4;
      ImageView imageView4 = ViewBindings.findChildViewById(rootView, id);
      if (imageView4 == null) {
        break missingId;
      }

      id = R.id.logout;
      ImageView logout = ViewBindings.findChildViewById(rootView, id);
      if (logout == null) {
        break missingId;
      }

      id = R.id.logoutImage;
      ImageView logoutImage = ViewBindings.findChildViewById(rootView, id);
      if (logoutImage == null) {
        break missingId;
      }

      id = R.id.logoutTxt;
      TextView logoutTxt = ViewBindings.findChildViewById(rootView, id);
      if (logoutTxt == null) {
        break missingId;
      }

      id = R.id.myProfile;
      ConstraintLayout myProfile = ViewBindings.findChildViewById(rootView, id);
      if (myProfile == null) {
        break missingId;
      }

      id = R.id.profile;
      ImageView profile = ViewBindings.findChildViewById(rootView, id);
      if (profile == null) {
        break missingId;
      }

      id = R.id.profile_Image;
      CircleImageView profileImage = ViewBindings.findChildViewById(rootView, id);
      if (profileImage == null) {
        break missingId;
      }

      id = R.id.profileTxt;
      TextView profileTxt = ViewBindings.findChildViewById(rootView, id);
      if (profileTxt == null) {
        break missingId;
      }

      id = R.id.progress;
      ConstraintLayout progress = ViewBindings.findChildViewById(rootView, id);
      if (progress == null) {
        break missingId;
      }

      id = R.id.progressBar5;
      ProgressBar progressBar5 = ViewBindings.findChildViewById(rootView, id);
      if (progressBar5 == null) {
        break missingId;
      }

      id = R.id.progressImage;
      ImageView progressImage = ViewBindings.findChildViewById(rootView, id);
      if (progressImage == null) {
        break missingId;
      }

      id = R.id.progressIn;
      ImageView progressIn = ViewBindings.findChildViewById(rootView, id);
      if (progressIn == null) {
        break missingId;
      }

      id = R.id.progressTxt;
      TextView progressTxt = ViewBindings.findChildViewById(rootView, id);
      if (progressTxt == null) {
        break missingId;
      }

      id = R.id.scrollView4;
      ScrollView scrollView4 = ViewBindings.findChildViewById(rootView, id);
      if (scrollView4 == null) {
        break missingId;
      }

      id = R.id.send;
      ImageView send = ViewBindings.findChildViewById(rootView, id);
      if (send == null) {
        break missingId;
      }

      id = R.id.sendImage;
      ImageView sendImage = ViewBindings.findChildViewById(rootView, id);
      if (sendImage == null) {
        break missingId;
      }

      id = R.id.sendTxt;
      TextView sendTxt = ViewBindings.findChildViewById(rootView, id);
      if (sendTxt == null) {
        break missingId;
      }

      id = R.id.share;
      ConstraintLayout share = ViewBindings.findChildViewById(rootView, id);
      if (share == null) {
        break missingId;
      }

      id = R.id.signOut;
      ConstraintLayout signOut = ViewBindings.findChildViewById(rootView, id);
      if (signOut == null) {
        break missingId;
      }

      id = R.id.textView8;
      TextView textView8 = ViewBindings.findChildViewById(rootView, id);
      if (textView8 == null) {
        break missingId;
      }

      return new FragmentProfileBinding((ConstraintLayout) rootView, DocumentImage, FAQ, arrowHelp,
          arrowProfile, changePassword, changePasswordImage, changePasswordTxt,
          changePasswordandEmail, dictionary, dictionaryImage, dictionaryIn, dictionaryTxt,
          document, documentIn, documentTxt, emailTxt, flashCard, flashCardImage, flashCardIn,
          flashCardTxt, fullNameTxt, help, helpTxt, imageView4, logout, logoutImage, logoutTxt,
          myProfile, profile, profileImage, profileTxt, progress, progressBar5, progressImage,
          progressIn, progressTxt, scrollView4, send, sendImage, sendTxt, share, signOut,
          textView8);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}