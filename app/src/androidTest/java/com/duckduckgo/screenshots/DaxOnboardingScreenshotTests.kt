/*
 * Copyright (c) 2023 DuckDuckGo
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

package com.duckduckgo.screenshots

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dropbox.dropshots.Dropshots
import com.duckduckgo.app.onboarding.ui.OnboardingActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DaxOnboardingScreenshotTests {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(OnboardingActivity::class.java)

    @get:Rule
    val dropshots = Dropshots()

    @Before
    fun setup() {

    }

    @Test
    fun whenDaxOnboardingStartsMatchesActivityScreenshot() {
        activityScenarioRule.scenario.onActivity {
            dropshots.assertSnapshot(it, "DaxOnboarding")
        }
    }

}
