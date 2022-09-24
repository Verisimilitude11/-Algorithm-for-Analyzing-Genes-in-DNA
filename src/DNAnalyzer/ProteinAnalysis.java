/*
 * Copyright © 2022 DNAnalyzer. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * You are entirely responsible for the use of this application, including any and all activities that occur.
 * While DNAnalyzer strives to fix all major bugs that may be either reported by a user or discovered while debugging,
 * they will not be held liable for any loss that the user may incur as a result of using this application, under any circumstances.
 *
 * For further inquiries, please contact DNAnalyzer@piyushacharya.com
 */

package DNAnalyzer;

import java.util.ArrayList;

/**
 * Prints properties of the proteins in the DNA.
 *
 * @author Piyush Acharya (@Verisimilitude11)
 * @version 1.2.1
 */
public class ProteinAnalysis {

  /**
   * Prints high coverage regions. High coverage regions are regions of a DNA sequence that code for
   * a protein and have a relatively high proportion of guanine and cytosine nucleotides to the 4
   * nucleotide bases (45-60% GC-content).
   *
   * @param geneList
   */
  public void printHighCoverageRegions(final ArrayList<String> geneList) {
    int count = 1;

    // print the list of genes with the highest GC content
    System.out.println("\nHigh coverage regions: ");
    System.out.println("----------------------------------------------------");

    final Properties p = new Properties();

    for (final String gene : geneList) {

      // High GC content range
      final float MIN_GC_CONTENT = 0.40f;
      final float MAX_GC_CONTENT = 0.60f;
      if ((p.getGCContent(gene) > MIN_GC_CONTENT) && (p.getGCContent(gene) < MAX_GC_CONTENT)) {
        System.out.println(count + ". " + gene);
        count++;
      }
    }
  }

  /**
   * Prints the longest protein in the DNA sequence along with its length. Longer genes are most
   * susceptible to disease implications and are especially linked to neurodevelopmental disorders
   * (e.g., autism).
   *
   * @see
   *     https://www.spectrumnews.org/opinion/viewpoint/length-matters-disease-implications-for-long-genes/
   * @category Properties
   * @param proteinList The list of proteins in the DNA sequence
   */
  public void printLongestProtein(final ArrayList<String> proteinList) {
    String longestGene = "";
    for (final String gene : proteinList) {
      if (gene.length() > longestGene.length()) {
        longestGene = gene;
      }
    }
    System.out.println("\nLongest gene (" + longestGene.length() + " nucleotides): " + longestGene);
  }
}
