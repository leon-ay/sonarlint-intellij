/*
 * SonarLint for IntelliJ IDEA
 * Copyright (C) 2015-2024 SonarSource
 * sonarlint@sonarsource.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonarlint.intellij.actions;

import com.intellij.openapi.project.Project;
import org.sonarlint.intellij.analysis.AnalysisCallback;
import org.sonarlint.intellij.analysis.AnalysisResult;
import org.sonarlint.intellij.common.util.SonarLintUtils;
import org.sonarlint.intellij.editor.CodeAnalyzerRestarter;

import static org.sonarlint.intellij.ui.UiUtils.runOnUiThread;

public class ShowReportCallable implements AnalysisCallback {
  private final Project project;

  public ShowReportCallable(Project project) {
    this.project = project;
  }

  @Override public void onError(Throwable e) {
    // nothing to do
  }

  @Override
  public void onSuccess(AnalysisResult analysisResult) {
    showReportTab(analysisResult);
  }

  private void showReportTab(AnalysisResult analysisResult) {
    runOnUiThread(project, () -> {
      SonarLintUtils.getService(project, SonarLintToolWindow.class).openReportTab(analysisResult);
      SonarLintUtils.getService(project, CodeAnalyzerRestarter.class).refreshOpenFiles();
    });
  }
}
