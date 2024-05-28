package com.englishtlu.english_learning.main.translate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.translate.model.ModelLanguage;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TranslateFragment extends Fragment {
    private EditText sourceLanguageEt;
    private TextView destinationLanguageTv;
    private Spinner sourceLanguageSpinner;
    private Spinner destinationLanguageSpinner;
    ImageView imageView;
    Uri imageUri;
    TextRecognizer textRecognizer;

    private Translator translator;
    private ProgressDialog progressDialog;
    private ArrayList<ModelLanguage> languageArrayList;
    private static final String TAG = "MAIN_TAG";
    private static final int REQUEST_PERMISSION_CODE = 1;

    private String sourceLanguageCode = "en";
    private String sourceLanguageTitle = "English";
    private String destinationLanguageCode = "ur";
    private String destinationLanguageTitle = "Urdu";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_translate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sourceLanguageSpinner = view.findViewById(R.id.sourceLanguageSpinner);
        destinationLanguageSpinner = view.findViewById(R.id.destinationLanguageSpinner);
        ImageButton languageSwitchBtn = view.findViewById(R.id.languageSwitchBtn);
        sourceLanguageEt = view.findViewById(R.id.sourceLanguageEt);
        destinationLanguageTv = view.findViewById(R.id.destinationLanguageTv);
        AppCompatButton translateBtn = view.findViewById(R.id.translateBtn);
        ImageView micIV = view.findViewById(R.id.idIVMic);
        imageView = view.findViewById(R.id.idIVCamera);
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        loadAvailableLanguages();

        sourceLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourceLanguageCode = languageArrayList.get(position).getLanguageCode();
                sourceLanguageTitle = languageArrayList.get(position).getLanguageTitle();
                sourceLanguageEt.setHint("Enter " + sourceLanguageTitle);

                // Chỉnh màu chữ cho mục đã chọn
                if (view != null && view instanceof TextView) {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        destinationLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                destinationLanguageCode = languageArrayList.get(position).getLanguageCode();
                destinationLanguageTitle = languageArrayList.get(position).getLanguageTitle();

                // Chỉnh màu chữ cho mục đã chọn
                if (view != null && view instanceof TextView) {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        languageSwitchBtn.setOnClickListener(v -> switchLanguages());

        translateBtn.setOnClickListener(v -> validateData());

        micIV.setOnClickListener(v -> {

            if (isSpeechRecognitionAvailable()) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something to translate");
                try {
                    startActivityForResult(intent, REQUEST_PERMISSION_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                showNoSpeechRecognitionDialog();
            }
        });

        imageView.setOnClickListener(v -> ImagePicker.with(TranslateFragment.this)
                .crop() // Crop image(Optional), Check Customization for more option
                .compress(1024) // Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080) // Final image resolution will be less than 1080 x 1080(Optional)
                .start());
    }

    private boolean isSpeechRecognitionAvailable() {
        PackageManager pm = requireActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        return activities.size() != 0;
    }

    private void showNoSpeechRecognitionDialog() {
        new AlertDialog.Builder(requireActivity())
                .setTitle("Speech Recognition Not Available")
                .setMessage("Speech recognition is not available on this device. Would you like to install Google Voice Search from the Play Store?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    String appPackageName = "com.google.android.googlequicksearchbox";
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void switchLanguages() {
        String tempCode = sourceLanguageCode;
        sourceLanguageCode = destinationLanguageCode;
        destinationLanguageCode = tempCode;

        String tempTitle = sourceLanguageTitle;
        sourceLanguageTitle = destinationLanguageTitle;
        destinationLanguageTitle = tempTitle;

        int sourcePosition = getLanguageIndex(sourceLanguageCode);
        int destinationPosition = getLanguageIndex(destinationLanguageCode);

        sourceLanguageSpinner.setSelection(sourcePosition);
        destinationLanguageSpinner.setSelection(destinationPosition);

        sourceLanguageEt.setHint("Enter " + sourceLanguageTitle);

        startTranslations();
    }

    private int getLanguageIndex(String languageCode) {
        for (int i = 0; i < languageArrayList.size(); i++) {
            if (languageArrayList.get(i).getLanguageCode().equals(languageCode)) {
                return i;
            }
        }
        return 0; // Return default position if not found
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PERMISSION_CODE && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && result.size() > 0) {
                String spokenText = result.get(0);
                sourceLanguageEt.setText(spokenText);
            }
        }

        if (resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                recognizeText();
            } else {
                Toast.makeText(getActivity(), "Image not selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void recognizeText() {
        if (imageUri != null) {
            try {
                InputImage inputImage = InputImage.fromFilePath(requireActivity(), imageUri);
                textRecognizer.process(inputImage)
                        .addOnSuccessListener(text -> {
                            String recognizeText = text.getText();
                            sourceLanguageEt.setText(recognizeText);
                        })
                        .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to recognize text due to " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Failed to prepare image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void validateData() {
        String sourceText = sourceLanguageEt.getText().toString().trim();
        if (sourceText.isEmpty()) {
            Toast.makeText(getActivity(), "Enter text to translate...", Toast.LENGTH_SHORT).show();
        } else {
            startTranslations();
        }
    }

    private void startTranslations() {
        progressDialog.setMessage("Processing language model...");
        progressDialog.show();

        TranslatorOptions translatorOptions = new TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguageCode)
                .setTargetLanguage(destinationLanguageCode)
                .build();

        translator = Translation.getClient(translatorOptions);

        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();

        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(unused -> {
                    progressDialog.setMessage("Translating...");
                    translator.translate(sourceLanguageEt.getText().toString().trim())
                            .addOnSuccessListener(translatedText -> {
                                progressDialog.dismiss();
                                destinationLanguageTv.setText(translatedText);
                            })
                            .addOnFailureListener(e -> {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Failed to translate due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Failed to download language model due to " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void loadAvailableLanguages() {
        languageArrayList = new ArrayList<>();
        List<String> languageCodeList = TranslateLanguage.getAllLanguages();

        for (String languageCode : languageCodeList) {
            String languageTitle = new Locale(languageCode).getDisplayLanguage();
            Log.d(TAG, "loadAvailableLanguages: languageCode: " + languageCode);
            Log.d(TAG, "loadAvailableLanguages: languageTitle: " + languageTitle);

            ModelLanguage modelLanguage = new ModelLanguage(languageCode, languageTitle);
            languageArrayList.add(modelLanguage);
        }

        ArrayAdapter<ModelLanguage> adapter = new ArrayAdapter<ModelLanguage>(requireActivity(), R.layout.spinner_item, languageArrayList) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setTextColor(getResources().getColor(R.color.black));
                return view;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setTextColor(getResources().getColor(R.color.black));
                return view;
            }
        };

        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        sourceLanguageSpinner.setAdapter(adapter);
        destinationLanguageSpinner.setAdapter(adapter);
    }
}
