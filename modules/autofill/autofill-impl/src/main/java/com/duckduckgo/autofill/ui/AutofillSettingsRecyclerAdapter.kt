/*
 * Copyright (c) 2022 DuckDuckGo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duckduckgo.autofill.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.duckduckgo.autofill.domain.app.LoginCredentials
import com.duckduckgo.autofill.impl.R
import timber.log.Timber

class AutofillSettingsRecyclerAdapter(
    val onCredentialSelected: (credentials: LoginCredentials) -> Unit
) : Adapter<AutofillSettingsRecyclerAdapter.CredentialsViewHolder>() {

    private var credentials = listOf<LoginCredentials>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CredentialsViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_row_autofill_credentials, parent, false)
        return CredentialsViewHolder(root)
    }

    override fun onBindViewHolder(
        viewHolder: CredentialsViewHolder,
        position: Int
    ) {
        val credentials = credentials[position]
        viewHolder.textView.text = credentials.username
        viewHolder.root.setOnClickListener {
            Timber.i("selected %s", credentials.username)
            onCredentialSelected(credentials)
        }
    }

    fun updateLogins(list: List<LoginCredentials>) {
        credentials = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = credentials.size

    class CredentialsViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        val textView: TextView = root.findViewById(R.id.username)
    }
}