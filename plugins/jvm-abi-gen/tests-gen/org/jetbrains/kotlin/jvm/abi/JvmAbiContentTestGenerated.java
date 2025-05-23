/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.jvm.abi;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TargetBackend;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.GenerateTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("plugins/jvm-abi-gen/testData/content")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class JvmAbiContentTestGenerated extends AbstractJvmAbiContentTest {
  private void runTest(String testDataFilePath) {
    KotlinTestUtils.runTest(this::doTest, TargetBackend.JVM_IR, testDataFilePath);
  }

  public void testAllFilesPresentInContent() {
    KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("plugins/jvm-abi-gen/testData/content"), Pattern.compile("^([^.]+)$"), null, TargetBackend.JVM_IR, false);
  }

  @TestMetadata("annotation")
  public void testAnnotation() {
    runTest("plugins/jvm-abi-gen/testData/content/annotation/");
  }

  @TestMetadata("annotationInstantiation")
  public void testAnnotationInstantiation() {
    runTest("plugins/jvm-abi-gen/testData/content/annotationInstantiation/");
  }

  @TestMetadata("anonymousAnnotationInstantiation")
  public void testAnonymousAnnotationInstantiation() {
    runTest("plugins/jvm-abi-gen/testData/content/anonymousAnnotationInstantiation/");
  }

  @TestMetadata("anonymousWhenMapping")
  public void testAnonymousWhenMapping() {
    runTest("plugins/jvm-abi-gen/testData/content/anonymousWhenMapping/");
  }

  @TestMetadata("class")
  public void testClass() {
    runTest("plugins/jvm-abi-gen/testData/content/class/");
  }

  @TestMetadata("dataClassNonPublicConstructorConsistentCopy")
  public void testDataClassNonPublicConstructorConsistentCopy() {
    runTest("plugins/jvm-abi-gen/testData/content/dataClassNonPublicConstructorConsistentCopy/");
  }

  @TestMetadata("dataClassNonPublicConstructorExposedCopy")
  public void testDataClassNonPublicConstructorExposedCopy() {
    runTest("plugins/jvm-abi-gen/testData/content/dataClassNonPublicConstructorExposedCopy/");
  }

  @TestMetadata("effectivelyPrivateAnnotation")
  public void testEffectivelyPrivateAnnotation() {
    runTest("plugins/jvm-abi-gen/testData/content/effectivelyPrivateAnnotation/");
  }

  @TestMetadata("innerClasses")
  public void testInnerClasses() {
    runTest("plugins/jvm-abi-gen/testData/content/innerClasses/");
  }

  @TestMetadata("internalClassAndCopyMethod")
  public void testInternalClassAndCopyMethod() {
    runTest("plugins/jvm-abi-gen/testData/content/internalClassAndCopyMethod/");
  }

  @TestMetadata("internalClassAndCopyMethodStrict")
  public void testInternalClassAndCopyMethodStrict() {
    runTest("plugins/jvm-abi-gen/testData/content/internalClassAndCopyMethodStrict/");
  }

  @TestMetadata("kt50005")
  public void testKt50005() {
    runTest("plugins/jvm-abi-gen/testData/content/kt50005/");
  }

  @TestMetadata("preserveDeclarationOrderKeepsClassIntact")
  public void testPreserveDeclarationOrderKeepsClassIntact() {
    runTest("plugins/jvm-abi-gen/testData/content/preserveDeclarationOrderKeepsClassIntact/");
  }

  @TestMetadata("whenMapping")
  public void testWhenMapping() {
    runTest("plugins/jvm-abi-gen/testData/content/whenMapping/");
  }
}
