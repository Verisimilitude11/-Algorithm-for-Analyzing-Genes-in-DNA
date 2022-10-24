/*
 * Copyright © 2022 DNAnalyzer. Some rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * You are entirely responsible for the use of this application, including any and all activities that occur.
 * While DNAnalyzer strives to fix all major bugs that may be either reported by a user or discovered while debugging,
 * they will not be held liable for any loss that the user may incur as a result of using this application, under any circumstances.
 *
 * For further inquiries, please contact reach out to contact@dnanalyzer.live
 */

package DNAnalyzer;

import DNAnalyzer.codon.CodonFrame;
import DNAnalyzer.protein.ProteinAnalysis;
import DNAnalyzer.protein.ProteinFinder;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Optional.ofNullable;

/**
 * Provides functionality to analyze the DNA
 *
 * @param dna       then DNA to be analyzed
 * @param protein   the DNA sequence
 * @param aminoAcid name of amino acid
 */
public record DNAAnalysis(DNATools dna, String protein, String aminoAcid) {
    public DNAAnalysis isValidDna() {
        dna.isValid();
        return this;
    }

    public DNAAnalysis replaceDNA(final String input, final String replacement) {
        return new DNAAnalysis(dna.replace(input, replacement), protein, aminoAcid);
    }

    public DNAAnalysis reverseDna() {
        return new DNAAnalysis(dna.reverse(), protein, aminoAcid);
    }

    // Create protein list
    // Output the proteins, GC content, and nucleotide cnt found in the DNA
    public DNAAnalysis printProteins() {
        ofNullable(dna).map(DNATools::getDna).ifPresent(dna -> {
            Properties.printProteinList(getProteins(aminoAcid), aminoAcid);

            System.out.println("\nGC-content (genome): " + Properties.getGCContent(dna) + "\n");
            Properties.printNucleotideCount(dna);
        });
        return this;
    }

    //used as helper method for output-codons, used to generate reading frames
    public ReadingFrames configureReadingFrames(final int minCount, final int maxCount){
        final short READING_FRAME = 1;
        final String dna = this.dna.getDna();
        final ReadingFrames aap = new ReadingFrames(new CodonFrame(dna, READING_FRAME, minCount, maxCount));
        System.out.print("\n");
        aap.printCodonCounts();
        return aap;
    }

    //used as helper method for output codons, handles protein decisions
    public DNAAnalysis proteinSequence() {
        final String dna = this.dna.getDna();

        if (protein != null) {
            final Pattern p = Pattern.compile(protein);
            final Matcher m = p.matcher(dna);
            if (m.find()) {
                System.out.println(
                        "\nProtein sequence found at index " + m.start() + " in the DNA sequence.");
            } else {
                System.out.println("\nProtein sequence not found in the DNA sequence.");
            }
        }
        return this; 
    }

    // Output the number of codons based on the reading frame the user wants to look
    // at, and minimum and maximum filters
    public DNAAnalysis outputCodons(final int minCount, final int maxCount) {
        configureReadingFrames(minCount, maxCount);
        proteinSequence();

        return this;
    }

    public DNAAnalysis printLongestProtein() {
        ProteinAnalysis.printLongestProtein(getProteins(aminoAcid));
        return this;
    }

    private List<String> getProteins(final String aminoAcid) {
        return ProteinFinder.getProtein(dna.getDna(), aminoAcid);
    }
}
