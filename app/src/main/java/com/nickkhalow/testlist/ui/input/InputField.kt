package com.nickkhalow.testlist.ui.input

import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.nickkhalow.testlist.R
import com.nickkhalow.testlist.domain.input.ItemInputField
import com.nickkhalow.testlist.domain.input.storage.ItemInfoStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class InputField(
    private val itemInputField: ItemInputField,
    private val itemInfoStorage: ItemInfoStorage,
    private val scope: CoroutineScope,
    activity: AppCompatActivity
) {

    private val editText: TextInputEditText
    private val submitButton: Button

    init {
        editText = activity.findViewById(R.id.input_field_text)
        submitButton = activity.findViewById(R.id.submit_button)

        editText.setText(itemInfoStorage.text())
        editText.addTextChangedListener {
            itemInputField.update(it.toString())
        }
        submitButton.setOnClickListener {
            scope.launch {
                itemInputField.submit()
                itemInputField.clear()
                editText.setText(itemInfoStorage.text())
            }
        }
    }
}