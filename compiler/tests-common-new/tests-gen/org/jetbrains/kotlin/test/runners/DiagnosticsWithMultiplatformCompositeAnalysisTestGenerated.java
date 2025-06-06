/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.test.runners;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.test.generators.GenerateCompilerTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("compiler/testData/diagnostics/testsWithMultiplatformCompositeAnalysis")
@TestDataPath("$PROJECT_ROOT")
public class DiagnosticsWithMultiplatformCompositeAnalysisTestGenerated extends AbstractDiagnosticsWithMultiplatformCompositeAnalysisTest {
  @Test
  public void testAllFilesPresentInTestsWithMultiplatformCompositeAnalysis() {
    KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("compiler/testData/diagnostics/testsWithMultiplatformCompositeAnalysis"), Pattern.compile("^(.*)\\.kts?$"), Pattern.compile("^(.+)\\.(reversed|partialBody|fir|ll|latestLV)\\.kts?$"), true);
  }

  @Nested
  @TestMetadata("compiler/testData/diagnostics/testsWithMultiplatformCompositeAnalysis/constVals")
  @TestDataPath("$PROJECT_ROOT")
  public class ConstVals {
    @Test
    public void testAllFilesPresentInConstVals() {
      KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("compiler/testData/diagnostics/testsWithMultiplatformCompositeAnalysis/constVals"), Pattern.compile("^(.*)\\.kts?$"), Pattern.compile("^(.+)\\.(reversed|partialBody|fir|ll|latestLV)\\.kts?$"), true);
    }

    @Test
    @TestMetadata("nonConstExpectConstActual.kt")
    public void testNonConstExpectConstActual() {
      runTest("compiler/testData/diagnostics/testsWithMultiplatformCompositeAnalysis/constVals/nonConstExpectConstActual.kt");
    }
  }

  @Nested
  @TestMetadata("compiler/testData/diagnostics/testsWithMultiplatformCompositeAnalysis/defaultArguments")
  @TestDataPath("$PROJECT_ROOT")
  public class DefaultArguments {
    @Test
    public void testAllFilesPresentInDefaultArguments() {
      KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("compiler/testData/diagnostics/testsWithMultiplatformCompositeAnalysis/defaultArguments"), Pattern.compile("^(.*)\\.kts?$"), Pattern.compile("^(.+)\\.(reversed|partialBody|fir|ll|latestLV)\\.kts?$"), true);
    }

    @Test
    @TestMetadata("constructor.kt")
    public void testConstructor() {
      runTest("compiler/testData/diagnostics/testsWithMultiplatformCompositeAnalysis/defaultArguments/constructor.kt");
    }

    @Test
    @TestMetadata("function.kt")
    public void testFunction() {
      runTest("compiler/testData/diagnostics/testsWithMultiplatformCompositeAnalysis/defaultArguments/function.kt");
    }
  }
}
