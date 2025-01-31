package DNAnalyzer.core;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides provides functionality for generating mutated DNA
 * sequences.
 */
public class DNAMutation {

  private static final char[] BASES = { 'A', 'T', 'C', 'G' };
  private static Random random = new Random();

  /**
   * Generates a list of 10 mutated DNA sequences based on the specified number of
   * base mutations and prints them to the console.
   * 
   * @param dnaString    Original DNA sequence to mutate
   * @param numMutations The number of base mutations to apply to each mutated
   *                     sequence
   */
  public static void generateMutatedSequences(String dnaString, int numMutations) {
    int numSequences = 10;

    for (int i = 0; i < numSequences; i++) {
      System.out.println(mutate(dnaString, numMutations));
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
