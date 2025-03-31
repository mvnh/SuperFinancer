@file:OptIn(ExperimentalMaterial3Api::class)

package com.mvnh.feature_finances.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.mvnh.feature_finances.R
import com.mvnh.feature_finances.domain.model.Goal

@Composable
internal fun DropdownMenuGoalSelector(
    goals: List<Goal>,
    selectedGoalId: Int?,
    onGoalSelected: (Int) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedGoalName by rememberSaveable {
        mutableStateOf(goals.find { it.id == selectedGoalId }?.name ?: "")
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedGoalName,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = stringResource(id = R.string.to_goal)
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            goals.forEach { goal ->
                DropdownMenuItem(
                    text = { Text(goal.name) },
                    onClick = {
                        selectedGoalName = goal.name
                        onGoalSelected(goal.id)
                        expanded = false
                    }
                )
            }
        }
    }
}
