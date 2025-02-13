package DNAnalyzer.core;

import java.util.Random;

import DNAnalyzer.utils.core.DNATools;

import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.util.Date;

/**
 * This class provides provides functionality for generating mutated DNA
 * sequences.
 */
public class DNAMutation {

  private static final char[] BASES = { 'A', 'T', 'C', 'G', 'N' };
  private static Random random = new Random();

  /**
   * Generates a list of 10 mutated DNA sequences based on the specified number of
   * base mutations and prints them to the console.
   * 
   * @param dnaString    Original DNA sequence to mutate
   * @param numMutations The number of base mutations to apply to each mutated
   *                     sequence
   */
  public static void generateAndWriteMutatedSequences(String dnaString, int numMutations, PrintStream out) {
    List<String> mutatedSequences = new ArrayList<>();

    out.println("\nMutating DNA sequence...");

    // Generate 10 mutated sequences
    for (int i = 0; i < 10; i++) {
      DNATools mutatedDna = new DNATools(mutate(dnaString, numMutations));
      mutatedSequences.add(mutatedDna.dna()); // Store the mutated DNA sequence to the list
    }

    // Dynamically generate the file name based on the current timestamp
    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String fileName = "mutated_dna_" + timestamp + ".fa";

    // Write the mutated sequences to a file
    try (FileWriter writer = new FileWriter(fileName)) {
      for (int i = 0; i < mutatedSequences.size(); i++) {
        writer.write(">mutation_" + (i + 1) + "\n");  // Write a header for each mutated sequence
        String mutatedSequence = mutatedSequences.get(i);
  
        // Write the sequence over multiple lines (e.g., 80 characters per line)
        for (int j = 0; j < mutatedSequence.length(); j += 80) {
          writer.write(mutatedSequence.substring(j, Math.min(j + 80, mutatedSequence.length())) + "\n");
        }
      }
      out.println("Mutated DNA sequences have been written to: " + fileName + "\n");
    } catch (IOException e) {
      out.println("Error writing to file: " + e.getMessage() + "\n");
    }
  }

  /**
   * Mutates a DNA sequence with a specified number of random base mutations.
   *
   * @param dnaString    Original DNA sequence to mutate
   * @param numMutations The number of mutations (substitutions) to apply to the
   *                     sequence
   * @return A new mutated DNA sequence
   */
  private static String mutate(String dnaString, int numMutations) {
    StringBuilder mutatedDna = new StringBuilder(dnaString);

    if (numMutations > mutatedDna.length()) {
      System.out.println("Warning: Number of requested mutations exceeds DNA length. Limiting to " + mutatedDna.length());
      numMutations = mutatedDna.length();
    }

    // Create a list of all possible positions
    List<Integer> availablePositions = new ArrayList<>();
    for (int i = 0; i < dnaString.length(); i++) {
      availablePositions.add(i);
    }

    // Perform mutations
    for (int i = 0; i < numMutations; i++) {
      // Select a random position from the available positions
      int randomPosition = random.nextInt(availablePositions.size());
      int position = availablePositions.remove(randomPosition);

      char originalBase = dnaString.charAt(position);
      char mutatedBase = getDifferentBase(originalBase);

      mutatedDna.setCharAt(position, mutatedBase);
    }

    return mutatedDna.toString();
  }

  /**
   * Returns a random base different from the original base.
   *
   * @param originalBase Original base to mutate from
   * @return A mutated base (different from the original)
   */
  private static char getDifferentBase(char originalBase) {
    int index = new String(BASES).indexOf(originalBase);

    // Pick a random index that isn’t the same as the original
    int newIndex = (index + 1 + random.nextInt(BASES.length - 1)) % BASES.length; 

    return BASES[newIndex];
  }
}
