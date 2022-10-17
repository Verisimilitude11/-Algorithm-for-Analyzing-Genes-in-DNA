/*
 * Copyright © 2022 DNAnalyzer. Some rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * You are entirely responsible for the use of this application, including any and all activities that occur.
 * While DNAnalyzer strives to fix all major bugs that may be either reported by a user or discovered while debugging,
 * they will not be held liable for any loss that the user may incur as a result of using this application, under any circumstances.
 *
 * For further inquiries, please contact DNAnalyzer@piyushacharya.com
 */
package DNAnalyzer;

import java.util.*;

/**
 * Declares the codon data for the 20 amino acids
 *
 * @author Shubham Jain (@shubhwip)
 * @version 1.2.1
 * @see "https://en.wikipedia.org/wiki/DNA_and_RNA_codon_tables"
 */
public class CodonDataConstants {

  /** Private CodonDataConstants class constructor to avoid initialization */
  private CodonDataConstants() {}

  // Declares the start codon data for the 20 amino acids. Start and stop codons
  // indicate the start and stop of an amino acid. There are 20 different amino
  // acids. A protein consists of one or more chains of amino acids (called
  // polypeptides) whose sequence is encoded in a gene.
  protected static final List<String> Isoleucine = Arrays.asList("ATT", "ATC", "ATA");
  protected static final List<String> Leucine =
      Arrays.asList("CTT", "CTC", "CTA", "CTG", "TTA", "TTG");
  protected static final List<String> Valine = Arrays.asList("GTT", "GTC", "GTA", "GTG");
  protected static final List<String> Phenylalanine = Arrays.asList("TTT", "TTC");
  protected static final List<String> Methionine = Arrays.asList("ATG");
  protected static final List<String> Cysteine = Arrays.asList("TGT", "TGC");
  protected static final List<String> Alanine = Arrays.asList("GCT", "GCC", "GCA", "GCG");
  protected static final List<String> Glycine = Arrays.asList("GGT", "GGC", "GGA", "GGG");
  protected static final List<String> Proline = Arrays.asList("CCT", "CCC", "CCA", "CCG");
  protected static final List<String> Threonine = Arrays.asList("ACT", "ACC", "ACA", "ACG");
  protected static final List<String> Serine =
      Arrays.asList("TCT", "TCC", "TCA", "TCG", "AGT", "AGC");
  protected static final List<String> Tyrosine = Arrays.asList("TAT", "TAC");
  protected static final List<String> Tryptophan = Arrays.asList("TGG");
  protected static final List<String> Glutamine = Arrays.asList("CAA", "CAG");
  protected static final List<String> Asparagine = Arrays.asList("AAT", "AAC");
  protected static final List<String> Histidine = Arrays.asList("CAT", "CAC");
  protected static final List<String> GlutamicAcid = Arrays.asList("GAA", "GAG");
  protected static final List<String> AsparticAcid = Arrays.asList("GAT", "GAC");
  protected static final List<String> Lysine = Arrays.asList("AAA", "AAG");
  protected static final List<String> Arginine =
      Arrays.asList("CGT", "CGC", "CGA", "CGG", "AGA", "AGG");

  // Declares the stop codon data for the 20 amino acids. Note that the stop
  // codons are the same for all amino acids.
  protected static final List<String> Stop = Arrays.asList("TAA", "TAG", "TGA");

  // Block to put all AminoAcidNames and their respective codon data into an enum
  // map for faster
  // retrieval
  protected static EnumMap<AminoAcidNames, List<String>> CodonDataAcidMap =
      new EnumMap<>(
          Map.ofEntries(
              Map.entry(AminoAcidNames.ISOLEUCINE, Isoleucine),
              Map.entry(AminoAcidNames.LEUCINE, Leucine),
              Map.entry(AminoAcidNames.VALINE, Valine),
              Map.entry(AminoAcidNames.PHENYLALANINE, Phenylalanine),
              Map.entry(AminoAcidNames.METHIONINE, Methionine),
              Map.entry(AminoAcidNames.CYSTEINE, Cysteine),
              Map.entry(AminoAcidNames.ALANINE, Alanine),
              Map.entry(AminoAcidNames.GLYCINE, Glycine),
              Map.entry(AminoAcidNames.PROLINE, Proline),
              Map.entry(AminoAcidNames.THREONINE, Threonine),
              Map.entry(AminoAcidNames.SERINE, Serine),
              Map.entry(AminoAcidNames.TYROSINE, Tyrosine),
              Map.entry(AminoAcidNames.TRYPTOPHAN, Tryptophan),
              Map.entry(AminoAcidNames.GLUTAMINE, Glutamine),
              Map.entry(AminoAcidNames.ASPARAGINE, Asparagine),
              Map.entry(AminoAcidNames.HISTIDINE, Histidine),
              Map.entry(AminoAcidNames.GLUTAMIC_ACID, GlutamicAcid),
              Map.entry(AminoAcidNames.ASPARTIC_ACID, AsparticAcid),
              Map.entry(AminoAcidNames.LYSINE, Lysine),
              Map.entry(AminoAcidNames.ARGININE, Arginine),
              Map.entry(AminoAcidNames.STOP, Stop)));
}
