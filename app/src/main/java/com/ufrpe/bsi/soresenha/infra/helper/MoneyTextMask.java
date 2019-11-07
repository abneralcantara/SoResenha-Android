package com.ufrpe.bsi.soresenha.infra.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyTextMask implements TextWatcher {
    private final WeakReference<EditText> editTextWeakReference;

    public MoneyTextMask(EditText editText) {
        editTextWeakReference = new WeakReference<>(editText);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    //https://stackoverflow.com/questions/5107901/better-way-to-format-currency-input-edittext
    @Override
    public void afterTextChanged(Editable editable) {
        EditText editText = editTextWeakReference.get();
        if (editText == null) return;
        String s = editable.toString();
        if (s.isEmpty()) return;
        editText.removeTextChangedListener(this);
        BigDecimal parsed = BigDecimalUtil.fromBRLString(s);
        String formatted = NumberFormat.getCurrencyInstance(new Locale( "pt", "BR" )).format(parsed);
        editText.setText(formatted);
        editText.setSelection(formatted.length());
        editText.addTextChangedListener(this);
    }
}
